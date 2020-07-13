package main;

import main.response.Book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class BookController {
//    @Autowired
//    private BookRepository bookRepository;

    @GetMapping("/books/")
    public List<Book> list()
    {
        return Storage.getAllBooks();
//        Iterable<Book> bookIterable = bookRepository.findAll();
//        ArrayList<Book> books = new ArrayList<>();
//        for(Book book : bookIterable) {
//            books.add(book);
//        }
//        return books;
    }

    @PostMapping("/books/")
    public int add(Book book)
    {
        return Storage.addBook(book);
    }

    @GetMapping("/books/{id}")
    public String get(@PathVariable int id)
    {
        return Objects.requireNonNull(Storage.getBook(id)).getName();
    }
}
