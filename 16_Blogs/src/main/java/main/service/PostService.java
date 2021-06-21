package main.service;

import main.requests.*;
import main.response.ErrorsResponse;
import main.response.ResultResponse;
import main.entity.*;
import main.repository.*;
import org.jsoup.Jsoup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@Service
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final PostVoteRepository postVoteRepository;
    private final TagRepository tagRepository;
    private final Tag2PostRepository tag2PostRepository;
    private final CommentRepository commentRepository;
    private final GlobalSettingsRepository globalSettingsRepository;

    public PostService(UserRepository userRepository, PostRepository postRepository, AuthService authService,
                       PostVoteRepository postVoteRepository, TagRepository tagRepository, Tag2PostRepository tag2PostRepository,
                       CommentRepository commentRepository, GlobalSettingsRepository globalSettingsRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.authService = authService;
        this.postVoteRepository = postVoteRepository;
        this.tagRepository = tagRepository;
        this.tag2PostRepository = tag2PostRepository;
        this.commentRepository = commentRepository;
        this.globalSettingsRepository = globalSettingsRepository;
    }

    public ResponseEntity<?> postApiModeration(Integer id, String decision) {
        ResultResponse resultResponse = new ResultResponse(false);

        if (!authService.isUserAuthorized()) {
            return new ResponseEntity<>(resultResponse, HttpStatus.UNAUTHORIZED);
        }

        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            return new ResponseEntity<>(resultResponse, HttpStatus.NOT_FOUND);
        }
        Post post = optionalPost.get();
        if (decision.equals("accept")) {
            post.setModerationStatus(ModerationStatus.ACCEPTED);
            postRepository.save(post);
            resultResponse = new ResultResponse(true);
            return new ResponseEntity<>(resultResponse, HttpStatus.OK);
        } else if (decision.equals("decline")) {
            post.setModerationStatus(ModerationStatus.DECLINED);
            postRepository.save(post);
            return new ResponseEntity<>(resultResponse, HttpStatus.NOT_MODIFIED);
        }
        return ResponseEntity.ok(new ResultResponse(true));
    }


    public ResponseEntity<?> postLikeDislike(LikeRequest likeRequest, Integer value) {
        PostVote postVote;
        int userId = authService.getUserId();
        int post_id = likeRequest.getPost_id();
        Post post = postRepository.getOne(likeRequest.getPost_id());
        Optional<PostVote> postVoteOptional = postVoteRepository.getOneByPostAndUser(post_id, userId);
        if (!authService.isUserAuthorized()) {
            return ResponseEntity.ok(new ResultResponse(false));
        }

        if (post.getUserId().equals(userId)) {
            return ResponseEntity.ok(new ResultResponse(false));
        }

        if (postVoteOptional.isPresent()) {
            if (postVoteOptional.get().getValue().equals(value)) {
                return new ResponseEntity<>("This vote is doubled! Not acceptable.", HttpStatus.OK);
            } else {
                postVote = postVoteOptional.get();
                postVote.setValue(value);
                postVoteRepository.save(postVote);
                return new ResponseEntity<>(new ResultResponse(true), HttpStatus.OK);
            }
        }
        PostVote newPostVote = createPostVote(post_id, value, userId);
        postVoteRepository.save(newPostVote);
        return new ResponseEntity<>(new ResultResponse(true), HttpStatus.OK);
    }

    private PostVote createPostVote(Integer postId, Integer value, Integer userId) {
        PostVote postVote = new PostVote();
        postVote.setPost(postRepository.getOne(postId));
        postVote.setPostId(postId);
        postVote.setTime(Timestamp.valueOf(now()));
        postVote.setUserId(userId);
        postVote.setValue(value);
        return postVote;
    }

    public ResponseEntity<?> postPost(PostRequest postRequest) {
        int userId = authService.getUserId();
        System.out.println("userId: "+ userId);
        User user = userRepository.getOne(userId);
        Map<String, String> errors = checkTexts(postRequest.getTitle(), postRequest.getText());
        Map<String, Object> responseMap = new LinkedHashMap<>();

        if (!errors.isEmpty()) {
            responseMap.put("result", false);
            responseMap.put("errors", errors);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        }

        if (!authService.isUserAuthorized()) {
            return new ResponseEntity<>("User UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }

        Post post = new Post();
        post.setIsActive(postRequest.getActive());
        Timestamp currentTimestamp = Timestamp.valueOf(LocalDateTime.now());

        if (postRequest.getTimestamp() <= currentTimestamp.getTime() / 1000) {
            post.setTimestamp(currentTimestamp);
        } else {
            post.setTimestamp(new Timestamp(postRequest.getTimestamp() * 1000));
        }

        post.setUserId(userId);
        post.setViewCount(0);
        post.setTitle(postRequest.getTitle());
        post.setText(cleanedOffHtml(postRequest.getText()));

        if (globalSettingsRepository.findAll().stream().findAny().orElse(new GlobalSettings())
                .isPostPremoderation()) {
            if (!user.getIsModerator() || postRequest.getActive() != 1) { // if the user is not moderator
                post.setModerationStatus(ModerationStatus.NEW);
            }
            if (user.getIsModerator() && postRequest.getActive() == 1) {
                post.setModerationStatus(ModerationStatus.ACCEPTED);
            }
        } else {
            post.setModerationStatus(ModerationStatus.ACCEPTED);
        }

        postRepository.save(post);
        processTags(postRequest.getTags(), post, postRequest.getTitle(), postRequest.getText());
        return new ResponseEntity<>(new ResultResponse(true), HttpStatus.OK);
    }

    private void processTags(List<String> tags, Post post, String title, String text) {
        post.setModerationStatus(ModerationStatus.ACCEPTED);
        for (String tag : tags) {
            if (!tagRepository.findAll().stream()
                    .map(Tag::getTagName)
                    .collect(Collectors.toList())
                    .contains(tag)) {
                if (title.contains(tag) || text.contains(tag)) {
                    Tag tagNew = new Tag(tag);
                    tagRepository.save(tagNew);
                    Tag2Post tag2Post = new Tag2Post(post.getPostId(), tagNew.getId());
                    tag2PostRepository.save(tag2Post);
                }
            }
        }
    }

    public ResponseEntity<?> putPost(int id, PutPostRequest putPostRequest) {
        if (!authService.isUserAuthorized()) {
            return new ResponseEntity<>("User UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }
        Map<String, Object> responseMap = new LinkedHashMap<>();
        Map<String, String> errors = checkTexts(putPostRequest.getTitle(), putPostRequest.getText());
        if (!errors.isEmpty()) {
            ErrorsResponse errorsResponse = new ErrorsResponse(errors);
            responseMap.put("result", String.valueOf(false));
            responseMap.put("errors", errorsResponse);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        }
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isEmpty()) {
            return new ResponseEntity<>(new ResultResponse(false), HttpStatus.NOT_FOUND);
        }
        Post post = postOptional.get();
        User user = userRepository.getOne(post.getUserId());
        post.setText(cleanedOffHtml(putPostRequest.getText()));
        post.setTitle(putPostRequest.getTitle());
        post.setActive(putPostRequest.getActive());
        long currentTime = Instant.now().toEpochMilli();
        if (putPostRequest.getTimestamp() <= currentTime) {
            post.setTimestamp(new Timestamp(currentTime));
        }
        if (!user.getIsModerator()) {
            post.setModerationStatus(ModerationStatus.NEW);
        }
        postRepository.save(post);
        processTags(putPostRequest.getTags(), post, putPostRequest.getTitle(),
                putPostRequest.getText());
        return new ResponseEntity<>(new ResultResponse(true), HttpStatus.OK);
    }

    private Map<String, String> checkTexts(String title, String text) {
        Map<String, String> errors = new LinkedHashMap<>();
        if (title.length() < 3) {
            errors.put("title", "Заголовок слишком короткий");
        } else if (title.length() > 100) {
            errors.put("title", "Заголовок слишком длинный!");
        }
        if (text.length() < 30) {
            errors.put("text", "Текст публикации слишком короткий");
        } else if (text.length() > 1000) {
            errors.put("text", "Текст публикации слишком длинный!");
        }
        return errors;
    }

    public ResponseEntity<?> postComment(CommentRequest commentRequest) {
        boolean result = true;
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Map<String, String> errors = new LinkedHashMap<>();
        if (!authService.isUserAuthorized()) {
            errors.put("errors", "User is unauthorized!");
            map.put("result", new ResultResponse(false));
            map.put("errors", errors);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        Integer userId = authService.getUserId();
        Integer postId = commentRequest.getPost_id();
        System.out.println("postId="+postId); /////
        PostComment postComment = new PostComment();
        if (commentRequest.getText().length() < 10 || commentRequest.getText().length() > 300) {
            result = false;
            errors.put("text", "Text's length is out of limit!");
        }
        if (result) {
            if (commentRequest.getParent_id() != null) {
                postComment.setParent_id(commentRequest.getParent_id());
            }

            postComment.setPost_id(postId);
            postComment.setPost(postRepository.getOne(postId));
            postComment.setText(commentRequest.getText());
            postComment.setTime(Timestamp.valueOf(now()));
            postComment.setUserId(userId);
            commentRepository.save(postComment);
            map.put("id", postComment.getCommentId());
        } else {
            map.put("result", new ResultResponse(false));
            map.put("errors", errors);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public String cleanedOffHtml(String input) {
        return Jsoup.parse(input).text();
    }

}
