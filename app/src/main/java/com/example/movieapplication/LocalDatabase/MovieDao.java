package com.example.movieapplication.LocalDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieDao {

    @Insert(onConflict = REPLACE)
    void insertMovie(LocalMovie movie);

    @Delete
    void deleteMovie(LocalMovie movie);

    @Query("SELECT * FROM movies_table")
    LiveData<List<LocalMovie>> getAllData();

    @Query("DELETE FROM movies_table")
    void clearAllMovies();
}
