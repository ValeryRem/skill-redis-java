package main.service;

import main.entity.*;
import main.repository.*;
import main.response.GeneralResponse;
import main.response.TagResponse;
import main.response.UserResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GetService {

    private final PostRepository postRepository;
    private final Tag2PostRepository tag2PostRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final HttpSession session;
    private final CommentRepository commentRepository;
    private final PostVoteRepository postVoteRepository;
    private final AuthService authService;
    private final GlobalSettingsRepository globalSettingsRepository;
    private ResponseEntity<?> responseEntity;

    public GetService(PostRepository postRepository, Tag2PostRepository tag2PostRepository,
                      TagRepository tagRepository,
                      UserRepository userRepository, HttpSession session, CommentRepository commentRepository,
                      PostVoteRepository postVoteRepository, AuthService authService,
                      GlobalSettingsRepository globalSettingsRepository) {
        this.postRepository = postRepository;
        this.tag2PostRepository = tag2PostRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.session = session;
        this.commentRepository = commentRepository;
        this.postVoteRepository = postVoteRepository;
        this.authService = authService;
        this.globalSettingsRepository = globalSettingsRepository;
    }

    public ResponseEntity<?> getPosts(Integer offset, Integer limit, String mode) {
        if (offset > limit) {
            return new ResponseEntity<>("Wrong input parameters!", HttpStatus.BAD_REQUEST);
        }

        List<Post> posts = getOrderedPosts(offset, limit, mode);
        List<Map<String, Object>> postMapList = new ArrayList<>();

        for (Post post : posts) {
            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("id", post.getPostId());
            responseMap.put("timestamp", post.getTimestamp().getTime() / 1000);
            int userId = post.getUserId();
            User user = userRepository.getOne(userId);
            Map<String, Object> userMap = new LinkedHashMap<>();
                   userMap.put("id", userId);
                   userMap.put("name", user.getName());
            responseMap.put("user", userMap);
            responseMap.put("title", post.getTitle());
            responseMap.put("announce", post.getAnnounce());
            responseMap.put("likeCount", extractLikeCount(post));
            responseMap.put("dislikeCount", extractDislikeCount(post));
            responseMap.put("commentCount", commentRepository.getCommentCountByPostId(post.getPostId()));
            responseMap.put("viewCount", post.getViewCount());
            postMapList.add(responseMap);
        }

        GeneralResponse generalResponse = new GeneralResponse();
        generalResponse.setCount(posts.size());
        generalResponse.setPosts(getOffsetLimitOutput(postMapList, offset, limit));
        return ResponseEntity.ok(generalResponse);
    }

    private List<Post> getOrderedPosts(Integer offset, Integer limit, String mode) {
        Page<Post> posts;
        PageRequest pageRequest = PageRequest.of(offset, limit);
        switch (mode) {
            case "popular":
                posts = postRepository.getPopularPosts(pageRequest);
                break;
            case "best":
                posts = postRepository.getBestPosts(pageRequest);
                break;
            case "early":
                posts = postRepository.getEarlyPosts(pageRequest);
                break;
            default:
                posts = postRepository.getRecentPosts(pageRequest);
                break;
        }
        return posts.stream().filter(p -> p.isActive() == 1).collect(Collectors.toList());
    }

    public ResponseEntity<?> getPostsBySearch(Integer offset, Integer limit, String query) {
        if (offset > limit) {
            return new ResponseEntity<>("Wrong input parameters!", HttpStatus.BAD_REQUEST);
        }

        GeneralResponse generalResponse = new GeneralResponse();
        List<Map<String, Object>> postMapList = new ArrayList<>();
        List<Post> posts = postRepository.findByTextContaining(query.trim());
        int count = posts.size();
        if(count == 0) {
            return new ResponseEntity<>("No posts with this text!", HttpStatus.NOT_FOUND);
        }

        posts.forEach(post -> {
            Map<String, Object> responseMap = new LinkedHashMap<>();
            Map<String, Object> userMap = new LinkedHashMap<>();
            responseMap.put("id", post.getPostId());
            responseMap.put("timestamp", post.getTimestamp().getTime() / 1000);
            Optional<User> userOptional = userRepository.findById(post.getUserId());
            userOptional.ifPresent(user -> userMap.put("id", user.getUserId()));
            userOptional.ifPresent(user -> userMap.put("name", user.getName()));
            responseMap.put("user", userMap);
            responseMap.put("title", post.getTitle());
            responseMap.put("announce", post.getAnnounce());
            responseMap.put("likeCount", extractLikeCount(post));
            responseMap.put("dislikeCount", extractDislikeCount(post));
            responseMap.put("commentCount", commentRepository.getCommentCountByPostId(post.getPostId()));
            responseMap.put("viewCount", post.getViewCount());
            postMapList.add(responseMap);
        });
        generalResponse.setCount(count);
        generalResponse.setPosts(getOffsetLimitOutput(postMapList, offset, limit));
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    public ResponseEntity<?> getPostsByDate(Integer offset, Integer limit, String date) {
        if (offset > limit) {
            return new ResponseEntity<>("Wrong input parameters!", HttpStatus.BAD_REQUEST);
        }

        var posts = postRepository.findAllActivePosts();
        int count = 0;
        List<Map<String, Object>> postMapList = new ArrayList<>();
        for (Post post : posts) {
            if (post.getTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString()
                    .equals(date)) {
                count++;
                Map<String, Object> responseMap = getMapOfPostElements(post);
                Map<String, Object> userMap = new LinkedHashMap<>();
                Optional<User> userOptional = userRepository.findById(post.getUserId());
                userOptional.ifPresent(user -> userMap.put("name", user.getName()));
                userOptional.ifPresent(user -> userMap.put("id", user.getUserId()));
                responseMap.put("user", userMap);
                postMapList.add(responseMap);
            }
        }
        GeneralResponse generalResponse = new GeneralResponse();
        generalResponse.setCount(count);
        generalResponse.setPosts(getOffsetLimitOutput(postMapList, offset, limit));
        responseEntity = new ResponseEntity<>(generalResponse, HttpStatus.OK);
        return responseEntity;
    }

    public ResponseEntity<?> getPostsByTag(Integer offset, Integer limit, String tagName) {
        if (offset > limit) {
            return new ResponseEntity<>("Wrong input parameters!", HttpStatus.BAD_REQUEST);
        }
        Optional<Tag> tagOptional = tagRepository.findTagByName(tagName);
        if (tagOptional.isEmpty()) {
            responseEntity = new ResponseEntity<>("No tag " + tagName + " is registered.",
                    HttpStatus.NO_CONTENT);
        } else {
            Tag tag = tagOptional.get();
            int tagId = tag.getId();
            List<Post> posts = new ArrayList<>();
            List<Integer> postsIdList = tag2PostRepository.findPostIdByTagId(tagId);
            postsIdList.forEach(id -> posts.add(postRepository.getOne(id))
            );
            List<Map<String, Object>> postMapList = new ArrayList<>();
            for (Post post : posts) {
                Map<String, Object> responseMap = getMapOfPostElements(post);
                Map<String, Object> userMap = new LinkedHashMap<>();
                userMap.put("id", post.getUserId());
                Optional<User> userOptional = userRepository.findById(post.getUserId());
                userOptional.ifPresent(user -> userMap.put("name", user.getName()));
                responseMap.put("user", userMap);
                postMapList.add(responseMap);
            }
            GeneralResponse generalResponse = new GeneralResponse();
            generalResponse.setCount(posts.size());
            generalResponse.setPosts(getOffsetLimitOutput(postMapList, offset, limit));
            responseEntity = new ResponseEntity<>(generalResponse, HttpStatus.OK);
        }

        return responseEntity;
    }

    public ResponseEntity<?> getMyPosts(Integer offset, Integer limit) {
        if (offset > limit) {
            return new ResponseEntity<>("Wrong input parameters!", HttpStatus.BAD_REQUEST);
        }

        if (!authService.isUserAuthorized()) {
            return new ResponseEntity<>("User not authorized", HttpStatus.NOT_FOUND);
        }

        GeneralResponse generalResponse = new GeneralResponse();
        List<Map<String, Object>> postMapList = new ArrayList<>();
        int userId = authService.getUserId();
        List<Post> myPosts = (List<Post>) postRepository.findAllPostsByUserId(userId);
        int count = 0;
        for (Post post : myPosts) {
            Map<String, Object> responseMap = getMapOfPostElements(post);
            Map<String, Object> userMap = new LinkedHashMap<>();
            userMap.put("id", post.getUserId());
            userMap.put("name", userRepository.getOne(userId).getName());
            responseMap.put("user", userMap);
            postMapList.add(responseMap);
            count++;
            generalResponse.setCount(count);
            generalResponse.setPosts(getOffsetLimitOutput(postMapList, offset, limit));
            responseEntity = new ResponseEntity<>(generalResponse, HttpStatus.OK);
        }
        return responseEntity;
    }

    private Map<String, Object> getMapOfPostElements(Post post) {
        Map<String, Object> responseMap = new LinkedHashMap<>();
        responseMap.put("id", post.getPostId());
        responseMap.put("timestamp", post.getTimestamp().getTime() / 1000);
        responseMap.put("title", post.getTitle());
        responseMap.put("announce", post.getAnnounce());
        responseMap.put("likeCount", extractLikeCount(post));
        responseMap.put("dislikeCount", extractDislikeCount(post));
        responseMap.put("commentCount", commentRepository.getCommentCountByPostId(post.getPostId()));
        responseMap.put("viewCount", post.getViewCount());
        return responseMap;
    }

    public ResponseEntity<?> getPostById(Integer postId) {
        if (postRepository.findById(postId).isEmpty()) {
            return new ResponseEntity<>("Post with ID = " + postId + " not found.", HttpStatus.NOT_FOUND);
        }

        List<Map<String, Object>> commentsMapList = new ArrayList<>();
        Map<String, Object> responseMap = new LinkedHashMap<>();
        var post = postRepository.getOne(postId);
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);
        Map<String, Object> userMap = new LinkedHashMap<>();
        responseMap.put("id", post.getPostId());
        responseMap.put("timestamp", post.getTimestamp().getTime() / 1000);
        responseMap.put("active", post.getIsActive());

        User user = userRepository.getOne(post.getUserId());
        userMap.put("name", user.getName());
        userMap.put("id", user.getUserId());

        responseMap.put("user", userMap);
        responseMap.put("title", post.getTitle());
        responseMap.put("announce", post.getAnnounce());
        responseMap.put("text", post.getText());
        responseMap.put("likeCount", extractLikeCount(post));
        responseMap.put("dislikeCount", extractDislikeCount(post));
        responseMap.put("viewCount", post.getViewCount());

        List<PostComment> postCommentList = commentRepository.findCommentsByPostId(postId);
        postCommentList.forEach(c -> {
            Map<String, Object> commentMap = new LinkedHashMap<>();
            commentMap.put("id", c.getCommentId());
            commentMap.put("timestamp", c.getTime().getTime() / 1000);
            commentMap.put("text", c.getText());
            User commentUser = userRepository.findById(c.getUserId()).orElseThrow();
            UserResponse userResponse = new UserResponse();
            userResponse.setId(commentUser.getUserId());
            userResponse.setName(commentUser.getName());
            userResponse.setPhoto(commentUser.getPhoto());
            commentMap.put("user", userResponse);
            commentsMapList.add(commentMap);
            responseMap.put("comments", commentsMapList);
        });
        if (post.getIsActive() == 1 && post.getModerationStatus().equals(ModerationStatus.ACCEPTED) &&
                post.getTimestamp().getTime() < Timestamp.valueOf(LocalDateTime.now()).getTime()) {
            var tagsIdList = tag2PostRepository.findTagIdsByPostId(postId);
            List<String> tagNames = new ArrayList<>();

            if (!tagsIdList.isEmpty()) {
                tagNames = tagsIdList.stream()
                        .map(t -> tagRepository.getOne(t).getTagName())
                        .collect(Collectors.toList());
            }
            responseMap.put("tags", tagNames);
        }
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    public ResponseEntity<?> getPostsForModeration(Integer offset, Integer limit, String status) {
        if (offset > limit) {
            return new ResponseEntity<>("Wrong input parameters!", HttpStatus.BAD_REQUEST);
        }

        if (!authService.isUserAuthorized()) {
            return new ResponseEntity<>("User is UNAUTHORIZED.", HttpStatus.UNAUTHORIZED);
        }
        User user = userRepository.getOne(authService.getUserId());
        List<Map<String, Object>> posts = new ArrayList<>();
        int count;
        var postList = postRepository.findAll();
        var postsFiltered = postList.stream()
                .filter(
                        p -> p.isActive() == 1 && p.getModerationStatus().equals(ModerationStatus.NEW) && status
                                .equals("new"))
                .collect(Collectors.toList());
        count = postsFiltered.size();
        for (Post post : postsFiltered) {
            Map<String, Object> responseMap = new LinkedHashMap<>();
            Map<String, Object> userMap = new LinkedHashMap<>();
            int commentCountByPost = commentRepository.getCommentCountByPostId(post.getPostId());
            responseMap.put("id", post.getPostId());
            responseMap.put("timestamp", post.getTimestamp().getTime() / 1000);
            responseMap.put("active", post.getIsActive());
            responseMap.put("title", post.getTitle());
            responseMap.put("announce", post.getAnnounce());
            responseMap.put("likeCount", extractLikeCount(post));
            responseMap.put("dislikeCount", extractDislikeCount(post));
            responseMap.put("commentCount", commentCountByPost);
            responseMap.put("viewCount", post.getViewCount());
            userMap.put("name", user.getName());
            userMap.put("id", user.getUserId());
            responseMap.put("user", userMap);
            posts.add(responseMap);
        }
        GeneralResponse generalResponse = new GeneralResponse();
        generalResponse.setCount(count);
        generalResponse.setPosts(getOffsetLimitOutput(posts, offset, limit));
        responseEntity = new ResponseEntity<>(generalResponse, HttpStatus.OK);
        return responseEntity;
    }

    private List<Map<String, Object>> getOffsetLimitOutput(List<Map<String, Object>> list,
                                                           Integer offset, Integer limit) {
        List<Map<String, Object>> listResult;
        if (offset > list.size() || offset > limit) {
            return new ArrayList<>();
        }
        if (limit + offset <= list.size()) {
            listResult = list.subList(offset, offset + limit);
        } else {
            listResult = list.subList(offset, list.size());
        }
        return listResult;
    }

    public ResponseEntity<?> getTag(String query) {
        Map<String, List<TagResponse>> tagsResponseMap;
        List<String> tagsList;
        if (query.contains(",")) {
            tagsList = List.of(query.split(",")); //Разбиваем строку запроса на теги по запятым
            List<String> tagsCleaned = tagsList.stream().map(String::trim).collect(Collectors.toList());
            tagsResponseMap = getTagResponsesMap(tagsCleaned);
        } else {
            tagsList = List.of(query);
            tagsResponseMap = getTagResponsesMap(tagsList);
        }
        return new ResponseEntity<>(tagsResponseMap, HttpStatus.OK);
    }

    public ResponseEntity<?> getTag() {
        List<Tag> tags = tagRepository.findAll();
        List<String> tagNames = tags.stream().map(Tag::getTagName).collect(Collectors.toList());
        Map<String, List<TagResponse>> tagsMap = getTagResponsesMap(tagNames);
        return new ResponseEntity<>(tagsMap, HttpStatus.OK);
    }

    private Map<String, List<TagResponse>> getTagResponsesMap(List<String> tagNameList) {
        List<Post> postList = (List<Post>) postRepository.findAllActivePosts();
        var count = getCount();
        List<Integer> postsPerTagList = new ArrayList<>();
        for (String t : tagNameList) {
            postsPerTagList.add((int) postList.stream().filter(p -> p.getText().contains(t)).count());
        }
        int maxPostsPerTag = postsPerTagList.stream().max(Comparator.naturalOrder()).orElse(count);
        List<Double> partialWeights = postsPerTagList.stream().map(t -> (double) t / maxPostsPerTag)
                .collect(Collectors.toList());
        List<TagResponse> tagResponseList = new ArrayList<>();
        for (int i = 0; i < partialWeights.size(); i++) {
            tagResponseList.add(new TagResponse(tagNameList.get(i), partialWeights.get(i)));
        }
        return Map.of("tags", tagResponseList);
    }

    public ResponseEntity<?> getMyStatistics() {
        Integer myId = authService.getUserId();
        if (authService.isUserAuthorized()) {
            LinkedHashMap<String, Object> map;
            map = getUserStatistics(myId);
            responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>("The user is not authorized!", HttpStatus.UNAUTHORIZED);
        }
        return responseEntity;
    }

    private LinkedHashMap<String, Object> getUserStatistics(Integer userId) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return map;
        } else {
            int myPostsCount = postRepository.findAllPostsByUserId(userId).size();
            map.put("postsCount", myPostsCount);
            int myPostsLikeCount = (int) postVoteRepository.findAllPostVotesByUserId(userId).stream().
                    filter(pv -> pv.getValue() == 1).
                    count();
            map.put("likesCount", myPostsLikeCount);
            int myPostsDislikeCount = (int) postVoteRepository.findAllPostVotesByUserId(userId).stream().
                    filter(pv -> pv.getValue() == -1).
                    count();
            map.put("dislikesCount", myPostsDislikeCount);
            int viewMyPostsCount = postVoteRepository.findAllPostVotesByUserId(userId).size();
            map.put("viewsCount", viewMyPostsCount);
            List<Timestamp> localDates = postRepository.findAllPostsByUserId(userId).stream().
                    map(Post::getTimestamp).collect(Collectors.toList());
            Timestamp minLocalDate = localDates.stream()
                    .min(Comparator.naturalOrder()).get();
            map.put("firstPublication", minLocalDate.getTime() / 1000);
            return map;
        }
    }

    /*
    STATISTICS_IS_PUBLIC - если включен этот режим, статистика блога должна быть доступна по запросу GET /api/statistics/all
    для всех групп пользователей. Если режим выключен, по запросу GET /api/statistics/all только модераторам отдавать
    данные статистики. Пользователям и гостям блога необходимо возвращать статус 401.
     */
    public ResponseEntity<?> getAllStatistics() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("postsCount", getCount());
        int likeCount = (int) postVoteRepository.findAll().stream().filter(p -> p.getValue() == 1)
                .count();
        map.put("likesCount", likeCount);
        int disLikeCount = (int) postVoteRepository.findAll().stream().filter(p -> p.getValue() == -1)
                .count();
        map.put("dislikesCount", disLikeCount);
        List<Integer> list = postRepository.findAll().stream().
                map(Post::getViewCount).
                collect(Collectors.toList());
        int viewCount = list.stream().
                reduce(Integer::sum).orElse(0);
        map.put("viewsCount", viewCount);
        List<Timestamp> localDates = postRepository.findAll().stream().
                map(Post::getTimestamp).collect(Collectors.toList());
        Timestamp minLocalDate = localDates.stream()
                .min(Comparator.naturalOrder()).get();
        map.put("firstPublication", minLocalDate.getTime() / 1000);
        if (globalSettingsRepository.findAll().stream().
                findAny().
                orElse(new GlobalSettings()).
                isStatisticsIsPublic()) { // if STATISTICS_IS_PUBLIC = true
            responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
        } else { // if STATISTICS_IS_PUBLIC = false
            int userId = authService.getUserId();
            User user = userRepository.getOne(userId);
            if (user.getIsModerator()) {
                responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
            } else {
                responseEntity = new ResponseEntity<>("Access forbidden!", HttpStatus.FORBIDDEN);
            }
        }
        return responseEntity;
    }

    public ResponseEntity<?> getApiCalendar(Integer year) {
        List<Integer> years;
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        List<Timestamp> timestamps;
        Map<String, Object> responseMap = new LinkedHashMap<>();
        Map<String, Integer> posts = new LinkedHashMap<>();
        int postCountAtDate;
        List<Post> postsList = postRepository.findAllActivePosts().stream()
//                .filter(p -> p.isActive() == 1 && p.getModerationStatus().equals(ModerationStatus.ACCEPTED))
                .sorted(Comparator.comparing(Post::getTimestamp))
                .collect(Collectors.toList());
        years = postsList.stream()
                .map(p -> convertTimeToYear(p.getTimestamp()))
                .distinct()
                .collect(Collectors.toList());
        if (year > 1970 && year <= convertTimeToYear(currentTimestamp)) {
            timestamps = postRepository.findAll().stream()
                    .map(Post::getTimestamp)
                    .filter(t_stamp -> convertTimeToYear(t_stamp).equals(year))
                    .distinct()
                    .collect(Collectors.toList());
        } else {
            int currentYear = LocalDate.now().getYear();
            timestamps = postsList.stream().
                    map(Post::getTimestamp).
                    filter(t_stamp -> convertTimeToYear(t_stamp).equals(currentYear)).
                    distinct().
                    collect(Collectors.toList());
        }
        timestamps.sort(Comparator.naturalOrder());
        for (Timestamp d : timestamps) {
            postCountAtDate = (int) postsList.stream()
                    .filter(p -> (p.getTimestamp().toInstant()
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDate()).equals(d.toInstant()
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDate()))
                    .count();
            posts.put(String.valueOf(d.toInstant()
                    .atZone(ZoneId.of("UTC"))
                    .toLocalDate()), postCountAtDate);
        }
        responseMap.put("years", years);
        responseMap.put("posts", posts);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    public Integer getCount() {
        int count;
        try {
            List<Post> postList = (List<Post>) postRepository.findAllActivePosts();
            count = postList.size();
        } catch (NullPointerException ex) {
            count = 0;
        }
        return count;
    }

    private Integer extractLikeCount(Post post) {
        return postVoteRepository.findCountVotesByPostId(post.getPostId(), 1).orElse(0);
    }

    private Integer extractDislikeCount(Post post) {
        return postVoteRepository.findCountVotesByPostId(post.getPostId(), -1).orElse(0);
    }

    public Integer convertTimeToYear(Timestamp time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC+3"));
        cal.setTimeInMillis(time.getTime());
        String curTime = String.valueOf(cal.get(Calendar.YEAR));
        return Integer.parseInt(curTime);
    }

    public HttpEntity<byte[]> getPhoto(String folder, String dir1, String dir2, String dir3,
                                       String filename)
            throws IOException {
        String source = folder + "/" + dir1 + "/" + dir2 + "/" + dir3 + "/" + filename;
        byte[] image = org.apache.commons.io.FileUtils.readFileToByteArray(new File(source));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(image.length);
        return new HttpEntity<>(image, headers);
    }
}