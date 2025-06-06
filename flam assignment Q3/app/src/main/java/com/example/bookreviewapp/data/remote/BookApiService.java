package com.example.bookreviewapp.data.remote;

import com.example.bookreviewapp.data.remote.model.BookResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BookApiService {
    @GET("books")
    Call<BookResponse> getBooks();

    @GET("books/{id}")
    Call<BookResponse> getBookById(@Path("id") String id);
} 