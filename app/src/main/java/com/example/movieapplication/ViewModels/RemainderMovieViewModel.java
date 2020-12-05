package com.example.movieapplication.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movieapplication.LocalDatabase.LocalMovie;
import com.example.movieapplication.Models.MoviesRepository;

import java.util.List;

public class RemainderMovieViewModel extends AndroidViewModel {
    private MoviesRepository repository;

    public RemainderMovieViewModel(Application app) {
        super(app);
        repository = MoviesRepository.getInstance(app);
    }

    public LiveData<List<LocalMovie>> getLocalMovies()
    {
        return repository.getLocalMovies();
    }

    public void clearAllMoviesFromLocal()
    {
        repository.clearAllMoviesFromLocal();
    }

}
