package com.example.movieapplication.Controllers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.movieapplication.Models.Genres;
import com.example.movieapplication.Models.Movie;
import com.example.movieapplication.R;
import com.example.movieapplication.ViewModels.ViewModelMovieDetails;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class MovieDetails extends AppCompatActivity {

    ImageView imgPoster;
    ViewModelMovieDetails viewModelMovieDetails;
    TextView txtviewNameOfTheMovie;
    TextView txtViewMovieDescription;
    TextView txtViewIsAdults;
    TextView txtViewReleaseDate;
    ImageView imgViewImdb;
    TextView txtViewGenre;
    Button btnAddToReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetails);
        imgPoster = findViewById(R.id.imgPoster);
        txtviewNameOfTheMovie = findViewById(R.id.txtviewNameOfTheMovie);
        txtViewMovieDescription = findViewById(R.id.txtViewMovieDescription);
        txtViewMovieDescription.setMovementMethod(new ScrollingMovementMethod());
        txtViewIsAdults = findViewById(R.id.txtViewIsAdults);
        txtViewReleaseDate = findViewById(R.id.txtViewReleaseDate);
        imgViewImdb = findViewById(R.id.imgViewImdb);
        txtViewGenre = findViewById(R.id.txtViewGenre);
        btnAddToReminder = findViewById(R.id.btnAddToReminder);

        viewModelMovieDetails = new ViewModelProvider(this).get(ViewModelMovieDetails.class);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            viewModelMovieDetails.getsMovieAccordingToID(bundle.getInt("movieId"));
        }
        viewModelMovieDetails.getMovieAccordingToID().observe(this, this::getMovieFromApiAndSetData);
    }
    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    public void getMovieFromApiAndSetData(Movie movie)
    {
        txtviewNameOfTheMovie.setText(getString(R.string.Title) + movie.getTitle());
        txtViewMovieDescription.setText(getString(R.string.Description) + "\n" + movie.getOverview());
        txtViewIsAdults.setText(getString(R.string.forAdults) + movie.isAdult());
        txtViewReleaseDate.setText(getString(R.string.ReleaseDateF) + new SimpleDateFormat("dd/MM/yyyy").format(movie.getReleaseDate()));
        imgViewImdb.setOnClickListener( v ->
                goToUrl ( "https://www.imdb.com/title/" + movie.getImdb_id() + "/")
        );
        txtViewGenre.setText(getResources().getString(R.string.GenresValue) + "\n");
        for( Genres gnr : movie.getGenres())
        {
            txtViewGenre.append(gnr.getName() + ", ");
        }
        Glide.with(imgPoster.getContext()).load(movie.getPoster_path()).into(imgPoster);
        btnAddToReminder.setOnClickListener(v -> {
            try{
                viewModelMovieDetails.addMovieToLocal(movie);
                Toast.makeText(this, "Movie was successfully added to the list", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Log.i("Exception", Objects.requireNonNull(e.getMessage()));
                Toast.makeText(this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
    /* MENU */
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuLogout:
                Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this,LoginController.class);
                startActivity(intent);
            case R.id.menuNeedToSee:
                Toast.makeText(this, "Going to history...", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(this,RemainderMovieController.class);
                startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);
    }
}
