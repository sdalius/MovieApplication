package com.example.movieapplication.LocalDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {LocalMovie.class},version = 1,exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MoviesDB extends RoomDatabase {
    private static MoviesDB database;
    /* Since ASync is deprecated in API 30 , i found another solution:
    https://stackoverflow.com/questions/46482423/android-room-asynctask
     */
    public static final ExecutorService dbWriteExecutor =
            Executors.newFixedThreadPool(2);

    public synchronized static MoviesDB getInstance(Context context)
    {
        if(database==null)
        {
            database= Room.databaseBuilder(context.getApplicationContext(), MoviesDB.class,"Movies_DB")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
    public abstract MovieDao movieDao();
}
