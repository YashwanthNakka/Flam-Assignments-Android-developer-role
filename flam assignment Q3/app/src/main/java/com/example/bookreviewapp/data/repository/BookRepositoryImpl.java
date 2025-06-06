package com.example.bookreviewapp.data.repository;

import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;

import com.example.bookreviewapp.data.local.BookDao;
import com.example.bookreviewapp.data.local.BookEntity;
import com.example.bookreviewapp.data.remote.BookApiService;
import com.example.bookreviewapp.data.remote.model.BookResponse;
import com.example.bookreviewapp.domain.model.Book;
import com.example.bookreviewapp.domain.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookRepositoryImpl implements BookRepository {
    private final BookDao bookDao;
    private final BookApiService apiService;
    private final ExecutorService executorService;
    private final Handler mainHandler;

    public BookRepositoryImpl(BookDao bookDao) {
        this.bookDao = bookDao;
        this.executorService = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper());

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.example.com/") // Replace with your mock API URL
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        this.apiService = retrofit.create(BookApiService.class);
    }

    @Override
    public LiveData<List<Book>> getAllBooks() {
        return Transformations.map(bookDao.getAllBooks(), this::mapToDomainBooks);
    }

    @Override
    public LiveData<Book> getBookById(String id) {
        return Transformations.map(bookDao.getBookById(id), this::mapToDomainBook);
    }

    @Override
    public LiveData<List<Book>> getFavoriteBooks() {
        return Transformations.map(bookDao.getFavoriteBooks(), this::mapToDomainBooks);
    }

    @Override
    public void toggleFavorite(Book book) {
        executorService.execute(() -> {
            BookEntity entity = mapToEntity(book);
            entity.setFavorite(!entity.isFavorite());
            bookDao.updateBook(entity);
        });
    }

    @Override
    public void refreshBooks() {
        apiService.getBooks().enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<BookEntity> entities = mapToEntities(response.body().getBooks());
                    executorService.execute(() -> bookDao.insertBooks(entities));
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                // Handle error
            }
        });
    }

    private List<Book> mapToDomainBooks(List<BookEntity> entities) {
        List<Book> books = new ArrayList<>();
        for (BookEntity entity : entities) {
            books.add(mapToDomainBook(entity));
        }
        return books;
    }

    private Book mapToDomainBook(BookEntity entity) {
        return new Book(
            entity.getId(),
            entity.getTitle(),
            entity.getAuthor(),
            entity.getDescription(),
            entity.getThumbnailUrl(),
            entity.getRating()
        );
    }

    private List<BookEntity> mapToEntities(List<BookResponse.BookDto> dtos) {
        List<BookEntity> entities = new ArrayList<>();
        for (BookResponse.BookDto dto : dtos) {
            entities.add(new BookEntity(
                dto.getId(),
                dto.getTitle(),
                dto.getAuthor(),
                dto.getDescription(),
                dto.getThumbnailUrl(),
                dto.getRating(),
                false
            ));
        }
        return entities;
    }

    private BookEntity mapToEntity(Book book) {
        return new BookEntity(
            book.getId(),
            book.getTitle(),
            book.getAuthor(),
            book.getDescription(),
            book.getThumbnailUrl(),
            book.getRating(),
            book.isFavorite()
        );
    }
} 