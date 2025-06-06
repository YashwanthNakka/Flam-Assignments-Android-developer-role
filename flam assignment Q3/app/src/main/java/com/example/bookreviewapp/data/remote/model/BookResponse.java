package com.example.bookreviewapp.data.remote.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BookResponse {
    @SerializedName("books")
    private List<BookDto> books;

    public List<BookDto> getBooks() {
        return books;
    }

    public static class BookDto {
        @SerializedName("id")
        private String id;

        @SerializedName("title")
        private String title;

        @SerializedName("author")
        private String author;

        @SerializedName("description")
        private String description;

        @SerializedName("thumbnailUrl")
        private String thumbnailUrl;

        @SerializedName("rating")
        private double rating;

        // Getters
        public String getId() { return id; }
        public String getTitle() { return title; }
        public String getAuthor() { return author; }
        public String getDescription() { return description; }
        public String getThumbnailUrl() { return thumbnailUrl; }
        public double getRating() { return rating; }
    }
} 