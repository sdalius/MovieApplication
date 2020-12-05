package com.example.movieapplication.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapplication.Adapter.RemainderMoviesAdapter;
import com.example.movieapplication.R;
import com.example.movieapplication.ViewModels.RemainderMovieViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class RemainderMovieController extends AppCompatActivity {


    RemainderMoviesAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager llm;
    RemainderMovieViewModel remainderMovieViewModel;
    Button btnClear;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remaindermovies);
        btnClear = findViewById(R.id.btnClear);
        recyclerView = findViewById(R.id.rvLocalMovieList);
        remainderMovieViewModel = new ViewModelProvider(this).get(RemainderMovieViewModel.class);
        llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        adapter = new RemainderMoviesAdapter(RemainderMovieController.this);
        adapter.setData(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        remainderMovieViewModel.getLocalMovies().observe(this, movies -> adapter.setData(movies));
        btnClear.setOnClickListener(v -> {
            remainderMovieViewModel.clearAllMoviesFromLocal();
            adapter.notifyDataSetChanged();
        });
    }

    /* Menu */
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
                this.startActivity(intent);
                return true;
            case R.id.menuNeedToSee:
                Toast.makeText(this, "Going to added movies...", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(this,RemainderMovieController.class);
                this.startActivity(intent1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
