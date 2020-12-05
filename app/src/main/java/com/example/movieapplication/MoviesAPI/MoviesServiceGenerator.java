package com.example.movieapplication.MoviesAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesServiceGenerator {
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static MoviesAPI moviesAPI = retrofit.create(MoviesAPI.class);

    public static MoviesAPI getMoviesAPI()
    {
        return moviesAPI;
    }


}
