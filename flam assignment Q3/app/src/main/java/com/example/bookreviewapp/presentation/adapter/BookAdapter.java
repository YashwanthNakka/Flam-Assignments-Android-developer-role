package com.example.bookreviewapp.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreviewapp.R;
import com.example.bookreviewapp.domain.model.Book;

public class BookAdapter extends ListAdapter<Book, BookAdapter.BookViewHolder> {
    private final OnBookClickListener listener;

    public interface OnBookClickListener {
        void onBookClick(Book book);
    }

    public BookAdapter(OnBookClickListener listener) {
        super(new DiffUtil.ItemCallback<Book>() {
            @Override
            public boolean areItemsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
                return oldItem.equals(newItem);
            }
        });
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = getItem(position);
        holder.bind(book, listener);
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        private final ImageView thumbnail;
        private final TextView title;
        private final TextView author;
        private final ImageButton favoriteButton;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.bookThumbnail);
            title = itemView.findViewById(R.id.bookTitle);
            author = itemView.findViewById(R.id.bookAuthor);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
        }

        public void bind(Book book, OnBookClickListener listener) {
            title.setText(book.getTitle());
            author.setText(book.getAuthor());
            favoriteButton.setImageResource(book.isFavorite() ? 
                android.R.drawable.btn_star_big_on : android.R.drawable.btn_star);

            itemView.setOnClickListener(v -> listener.onBookClick(book));
            favoriteButton.setOnClickListener(v -> {
                book.setFavorite(!book.isFavorite());
                favoriteButton.setImageResource(book.isFavorite() ? 
                    android.R.drawable.btn_star_big_on : android.R.drawable.btn_star);
            });
        }
    }
} 