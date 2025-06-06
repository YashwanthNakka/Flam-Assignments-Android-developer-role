package com.example.bookreviewapp.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {
    @Query("SELECT * FROM books")
    LiveData<List<BookEntity>> getAllBooks();

    @Query("SELECT * FROM books WHERE id = :bookId")
    LiveData<BookEntity> getBookById(String bookId);

    @Query("SELECT * FROM books WHERE isFavorite = 1")
    LiveData<List<BookEntity>> getFavoriteBooks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBooks(List<BookEntity> books);

    @Update
    void updateBook(BookEntity book);
} 