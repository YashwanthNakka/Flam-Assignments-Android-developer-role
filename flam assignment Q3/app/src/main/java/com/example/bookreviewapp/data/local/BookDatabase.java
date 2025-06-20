package com.example.bookreviewapp.data.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {BookEntity.class}, version = 1)
public abstract class BookDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "book_database";
    private static BookDatabase instance;

    public abstract BookDao bookDao();

    public static synchronized BookDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                context.getApplicationContext(),
                BookDatabase.class,
                DATABASE_NAME
            ).build();
        }
        return instance;
    }
} 