package com.example.movieapplication.MoviesAPI;

import com.example.movieapplication.Models.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesAPI {

    // link :  https://api.themoviedb.org/3/trending/movie/day?api_key=cfd97eca14890f727cbaf186b3b2b1cd&page=1
    @GET("/3/trending/movie/day?api_key=cfd97eca14890f727cbaf186b3b2b1cd")
    Call<MoviesAPIResponse> getTrendingMoviesOfTheDay(@Query(value="page" , encoded=true) int page_num);



    @GET("/3/movie/{movie_id}?api_key=cfd97eca14890f727cbaf186b3b2b1cd")
    Call<Movie> getMovieAccordingToID(@Path("movie_id") int movie_id);
}
