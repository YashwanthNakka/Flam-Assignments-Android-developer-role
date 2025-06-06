package com.example.bookreviewapp.presentation.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bookreviewapp.data.local.BookDatabase;
import com.example.bookreviewapp.data.repository.BookRepositoryImpl;
import com.example.bookreviewapp.domain.model.Book;
import com.example.bookreviewapp.domain.repository.BookRepository;

import java.util.List;

public class BookViewModel extends AndroidViewModel {
    private final BookRepository repository;
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public BookViewModel(Application application) {
        super(application);
        BookDatabase database = BookDatabase.getInstance(application);
        repository = new BookRepositoryImpl(database.bookDao());
    }

    public LiveData<List<Book>> getAllBooks() {
        return repository.getAllBooks();
    }

    public LiveData<Book> getBookById(String id) {
        return repository.getBookById(id);
    }

    public LiveData<List<Book>> getFavoriteBooks() {
        return repository.getFavoriteBooks();
    }

    public void toggleFavorite(Book book) {
        repository.toggleFavorite(book);
    }

    public void refreshBooks() {
        isLoading.setValue(true);
        repository.refreshBooks();
        isLoading.setValue(false);
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }
} 