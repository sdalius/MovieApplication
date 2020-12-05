package com.example.movieapplication.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movieapplication.Models.Movie;
import com.example.movieapplication.Models.MoviesRepository;

public class ViewModelMovieDetails extends AndroidViewModel {

        private MoviesRepository repository;

        public ViewModelMovieDetails(Application app) {
            super(app);
            repository = MoviesRepository.getInstance(app);
        }

        public LiveData<Movie> getMovieAccordingToID()
        {
            return repository.getMovie();
        }

        public void getsMovieAccordingToID(int id) {
            repository.getMovieAccordingToID(id);
        }

        public void addMovieToLocal(Movie movie)
        {
            repository.addMovieToLocal(movie);
        }
}
