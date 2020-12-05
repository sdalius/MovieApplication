package com.example.movieapplication.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapplication.Controllers.MovieDetails;
import com.example.movieapplication.Models.Movie;
import com.example.movieapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    List<Movie> movieList = new ArrayList<>();
    Activity context;

    public MoviesAdapter(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycleview_movies,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SimpleDateFormat"})
    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder holder, int position) {
        holder.txtViewMovieName.setText(movieList.get(position).getTitle());
        holder.txtViewGenres.setText(R.string.GenresValue );
        for( int gnr : movieList.get(position).getGenre_ids())
        {
            holder.txtViewGenres.append(gnr + ", ");
        }
        try {
            holder.txtViewReleaseDate.setText(context.getResources().getString(R.string.ReleaseDateF) +  new SimpleDateFormat("dd/MM/yyyy").format(movieList.get(position).getReleaseDate()));
        }
        catch (NullPointerException ne)
        {
            holder.txtViewReleaseDate.setText(context.getResources().getString(R.string.ReleaseDateF) + R.string.UnknownValue);
        }

        Glide.with(holder.imgPoster.getContext()).load(movieList.get(position).getPoster_path()).into(holder.imgPoster);
        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(v.getContext(), MovieDetails.class);
            intent.putExtra("movieId",movieList.get(position).getId());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setData(List<Movie> newList)
    {
        movieList = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtViewMovieName;
        TextView txtViewGenres;
        TextView txtViewReleaseDate;
        ImageView imgPoster;

      public ViewHolder(View itemView) {
            super(itemView);
            txtViewMovieName = itemView.findViewById(R.id.txtViewMovieName);
            txtViewGenres = itemView.findViewById(R.id.txtviewGenres);
            txtViewReleaseDate = itemView.findViewById(R.id.txtViewReleaseDate);
            imgPoster = itemView.findViewById(R.id.imgPoster);
        }
    }
}
