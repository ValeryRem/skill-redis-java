package main;

import main.response.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class Storage {
    private static int currentId = 1;

    private static final ConcurrentHashMap<Integer, Book> books = new ConcurrentHashMap<>();

    public static List<Book> getAllBooks()
    {
        ArrayList<Book> booksList = new ArrayList<>();
        booksList.addAll(books.values());
        return booksList;
    }

    public static int addBook(Book book)
    {
        int id = currentId++;
        book.setId(id);
        books.put(id, book);
        return id;
    }

    public static Book getBook(int bookId)
    {
        if(books.containsKey(bookId)) {
            return books.get(bookId);
        }
        return null;
    }
}
