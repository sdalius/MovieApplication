package com.example.movieapplication.LocalDatabase;

import androidx.room.TypeConverter;

import java.util.Date;

/* Source: https://stackoverflow.com/questions/50313525/room-using-date-field */
public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}