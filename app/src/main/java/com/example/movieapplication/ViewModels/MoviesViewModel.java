package com.example.movieapplication.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movieapplication.Models.Movie;
import com.example.movieapplication.Models.MoviesRepository;
import com.example.movieapplication.MoviesAPI.MoviesAPIResponse;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {

    private MoviesRepository repository;

    public MoviesViewModel(Application app) {
        super(app);
        repository = MoviesRepository.getInstance(app);
    }

    public LiveData<List<Movie>> getMoviesList() {
        return repository.getMovieList();
    }

    public void getMoviesFromAPI(int page_num) {
        repository.getTrendingMoviesOfTheDay(page_num);
    }

    public MoviesAPIResponse getMoviesRepository (){
        return repository.getMoviesAPIResponse();
    }
}
