package com.example.movieapplication.Models;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.List;

public class Movie {
    private int id;
    private boolean adult;
    private List<Genres> genres;
    private List<Integer> genre_ids;
    private String original_title;
    private String title;
    private String overview;
    private Date release_date;
    private String media_type;
    private String poster_path;
    private String imdb_id;

    public Movie(int id, boolean adult, List<Genres>  genres, String original_title, String overview, Date release_date, String media_type, String poster_path, String imdb_id,List<Integer> genre_ids, String title) {
        this.id = id;
        this.adult = adult;
        this.genres = genres;
        this.original_title = original_title;
        this.overview = overview;
        this.release_date = release_date;
        this.media_type = media_type;
        this.poster_path = poster_path;
        this.imdb_id =imdb_id;
        this.genre_ids = genre_ids;
        this.title = title;
    }

    public boolean isAdult() {
        return adult;
    }

    public List<Genres>  getGenres() {
        return genres;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public String getOverview() {
        return overview;
    }

    public Date getReleaseDate() {
        return release_date;
    }

    public String getPoster_path()
    {
        String finale =  "https://image.tmdb.org/t/p/original/" + poster_path;
        return finale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public String getTitle() {
        return title;
    }

    @NonNull
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", adult=" + adult +
                ", genres=" + genres +
                ", genre_ids=" + genre_ids +
                ", original_title='" + original_title + '\'' +
                ", title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", release_date=" + release_date +
                ", media_type='" + media_type + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", imdb_id='" + imdb_id + '\'' +
                '}';
    }
}
