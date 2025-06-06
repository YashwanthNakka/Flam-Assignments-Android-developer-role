package com.example.bookreviewapp.domain.repository;

import com.example.bookreviewapp.domain.model.Book;
import java.util.List;
import androidx.lifecycle.LiveData;

public interface BookRepository {
    LiveData<List<Book>> getAllBooks();
    LiveData<Book> getBookById(String id);
    LiveData<List<Book>> getFavoriteBooks();
    void toggleFavorite(Book book);
    void refreshBooks();
} 