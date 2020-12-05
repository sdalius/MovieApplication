package com.example.movieapplication.Models;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapplication.LocalDatabase.LocalMovie;
import com.example.movieapplication.LocalDatabase.MoviesDB;
import com.example.movieapplication.MoviesAPI.MoviesAPI;
import com.example.movieapplication.MoviesAPI.MoviesAPIResponse;
import com.example.movieapplication.MoviesAPI.MoviesServiceGenerator;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {

    private MutableLiveData<Movie> singleMovie;
    private MutableLiveData<List<Movie>> movies;
    private MoviesAPIResponse moviesApiResponse;
    private static MoviesRepository instance;
    MoviesDB moviesDB;

    private MoviesRepository(Application app)
    {
        movies = new MutableLiveData<>();
        singleMovie = new MutableLiveData<>();
        moviesApiResponse = new MoviesAPIResponse();
        moviesDB = MoviesDB.getInstance(app);
    }

    public static synchronized MoviesRepository getInstance(Application app){
        if(instance == null)
        {
            instance = new MoviesRepository(app);
        }
        return instance;
    }

    public LiveData<List<Movie>> getMovieList() {
        return movies;
    }

    public MoviesAPIResponse getMoviesAPIResponse (){
        return moviesApiResponse;
    }
//TODO Remake this so only the MoviesAPIResponse should be retrieved from api(Optimization)
    public void getTrendingMoviesOfTheDay(int page_num) {
        MoviesAPI moviesAPI = MoviesServiceGenerator.getMoviesAPI();
        Call<MoviesAPIResponse> call = moviesAPI.getTrendingMoviesOfTheDay(page_num);
        call.enqueue(new Callback<MoviesAPIResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesAPIResponse> call, @NonNull Response<MoviesAPIResponse> response) {
                if (response.isSuccessful()) {
                    Log.i("Retrofit", "We have been successful lol");
                    if (response.body() != null) {
                        moviesApiResponse = response.body();
                        movies.setValue(response.body().getResults());
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<MoviesAPIResponse> call, @NonNull Throwable t) {
                Log.i("Retrofit", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getMovieAccordingToID(int movie_id) {
        MoviesAPI moviesAPI = MoviesServiceGenerator.getMoviesAPI();
        Call<Movie> call = moviesAPI.getMovieAccordingToID(movie_id);
        Log.i("getmovieaccordingtoID" , String.valueOf(movie_id));
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                if (response.isSuccessful()) {
                    Log.i("Retrofit", "We have been successful lol(GetMovieAccordingToID)");
                    if (response.body() != null) {
                        singleMovie.setValue(response.body());
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                Log.i("Retrofit", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public LiveData<Movie> getMovie()
    {
        return singleMovie;
    }





    /* Local db stuff */
    public LiveData<List<LocalMovie>> getLocalMovies() {
        return moviesDB.movieDao().getAllData();
    }

    public void addMovieToLocal(Movie movie)
    {
        StringBuilder genrestoString = new StringBuilder();
        for(Genres gr : movie.getGenres())
        {
            genrestoString.append(gr.getName()).append(", ");
        }
        genrestoString = new StringBuilder(genrestoString.substring(0, genrestoString.length() - 1));
        String finalGenrestoString = genrestoString.toString();
        MoviesDB.dbWriteExecutor.execute(() -> moviesDB.movieDao().insertMovie(new LocalMovie(movie.getTitle(),movie.getReleaseDate(), finalGenrestoString,movie.getPoster_path())));
    }

    public void removeMovieFromDB(LocalMovie localMovie) {
        MoviesDB.dbWriteExecutor.execute(() -> moviesDB.movieDao().deleteMovie(localMovie));

    }

    public void clearAllMoviesFromLocal() {
        MoviesDB.dbWriteExecutor.execute(() -> moviesDB.movieDao().clearAllMovies());
    }
}