package com.example.bookreviewapp.presentation;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookreviewapp.R;
import com.example.bookreviewapp.domain.model.Book;
import com.example.bookreviewapp.presentation.viewmodel.BookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BookDetailActivity extends AppCompatActivity {
    private BookViewModel viewModel;
    private String bookId;
    private FloatingActionButton favoriteFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        // Get book ID from intent
        bookId = getIntent().getStringExtra("book_id");
        if (bookId == null) {
            finish();
            return;
        }

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views
        ImageView bookImage = findViewById(R.id.bookImage);
        TextView bookTitle = findViewById(R.id.bookTitle);
        TextView bookAuthor = findViewById(R.id.bookAuthor);
        RatingBar bookRating = findViewById(R.id.bookRating);
        TextView bookDescription = findViewById(R.id.bookDescription);
        favoriteFab = findViewById(R.id.favoriteFab);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(BookViewModel.class);

        // Observe book data
        viewModel.getBookById(bookId).observe(this, book -> {
            if (book != null) {
                bookTitle.setText(book.getTitle());
                bookAuthor.setText(book.getAuthor());
                bookRating.setRating((float) book.getRating());
                bookDescription.setText(book.getDescription());
                updateFavoriteButton(book.isFavorite());
            }
        });

        // Setup favorite button
        favoriteFab.setOnClickListener(v -> {
            Book currentBook = viewModel.getBookById(bookId).getValue();
            if (currentBook != null) {
                viewModel.toggleFavorite(currentBook);
            }
        });
    }

    private void updateFavoriteButton(boolean isFavorite) {
        favoriteFab.setImageResource(isFavorite ? 
            android.R.drawable.btn_star_big_on : android.R.drawable.btn_star);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 