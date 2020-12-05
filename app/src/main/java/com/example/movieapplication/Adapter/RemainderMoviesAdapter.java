package com.example.movieapplication.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapplication.LocalDatabase.LocalMovie;
import com.example.movieapplication.Models.MoviesRepository;
import com.example.movieapplication.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class RemainderMoviesAdapter extends RecyclerView.Adapter<RemainderMoviesAdapter.ViewHolder> {

    private List<LocalMovie> movieList;
    private Activity context;

    public RemainderMoviesAdapter(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_main,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        LocalMovie data = movieList.get(position);
        holder.txtViewTitle.setText(movieList.get(position).getTitle());
        try {
            holder.txtViewReleaseDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(movieList.get(position).getRelease_date()));
        }
        catch (NullPointerException ne)
        {
            holder.txtViewReleaseDate.setText(R.string.UnknownValue);
        }

        Glide.with(holder.txtViewImgPath.getContext()).load(movieList.get(position).getImgpath()).into(holder.txtViewImgPath);
        holder.txtViewGenres.setText(data.getGenres());
        holder.btDelete.setOnClickListener(v -> {
            MoviesRepository.getInstance(context.getApplication()).removeMovieFromDB(movieList.get(position));
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setData(List<LocalMovie> newList)
    {
        movieList = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtViewTitle,txtViewReleaseDate,txtViewGenres;
        ImageView btDelete,txtViewImgPath;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewTitle = itemView.findViewById(R.id.txtViewTitle);
            txtViewReleaseDate = itemView.findViewById(R.id.txtViewReleaseDate);
            txtViewGenres = itemView.findViewById(R.id.txtViewGenre);
            txtViewImgPath = itemView.findViewById(R.id.txtViewImgPath);
            btDelete = itemView.findViewById(R.id.bt_delete);
        }
    }
}
