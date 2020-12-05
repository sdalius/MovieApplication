package com.example.movieapplication.LocalDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "movies_table")
public class LocalMovie implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name= "title")
    public String title;
    @ColumnInfo(name= "release_date")
    public Date release_date;
    @ColumnInfo(name= "genres")
    public String genres;
    @ColumnInfo(name= "imgpath")
    public String imgpath;

    public LocalMovie(String title, Date release_date, String genres, String imgpath) {
        this.title = title;
        this.release_date = release_date;
        this.genres = genres;
        this.imgpath = imgpath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public String getGenres() {
        return genres;
    }

    public String getImgpath() {
        return imgpath;
    }
}
