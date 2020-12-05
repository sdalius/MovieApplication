package com.example.movieapplication.MoviesAPI;

import com.example.movieapplication.Models.Movie;

import java.util.ArrayList;

public class MoviesAPIResponse {
    private int page;
    ArrayList<Movie> results;
    private int total_pages;

    public ArrayList<Movie> getResults() {
        return results;
    }

    public int getTotal_pages()
    {
        return total_pages;
    }

    public int getPage()
    {
        return page;
    }
}
