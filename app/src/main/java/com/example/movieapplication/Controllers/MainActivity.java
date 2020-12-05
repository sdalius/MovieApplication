package com.example.movieapplication.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapplication.Adapter.MoviesAdapter;
import com.example.movieapplication.R;
import com.example.movieapplication.ViewModels.MoviesViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    RecyclerView rvMovieList;
    LinearLayoutManager linearLayoutManager;
    MoviesAdapter moviesAdapter;
    MoviesViewModel moviesViewModel;
    TextView txtViewCurrPage;
    TextView txtViewName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayoutManager = new LinearLayoutManager(this);
        moviesViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);
        txtViewName = findViewById(R.id.txtViewName);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount!=null)
        {
            txtViewName.setText(signInAccount.getDisplayName());
        }
        rvMovieList = findViewById(R.id.rvMovieList);
        txtViewCurrPage = findViewById(R.id.txtViewCurrPage);
        txtViewCurrPage.setText("1");
        rvMovieList.hasFixedSize();
        rvMovieList.setLayoutManager(linearLayoutManager);
        moviesAdapter = new MoviesAdapter(this);
        rvMovieList.setAdapter(moviesAdapter);
        moviesViewModel.getMoviesFromAPI(1);
        moviesViewModel.getMoviesList().observe(this, movies -> moviesAdapter.setData(movies));
    }

    public void onClickNextPage(View view)
    {
        int currpage = moviesViewModel.getMoviesRepository().getPage();
        if (currpage < moviesViewModel.getMoviesRepository().getTotal_pages()) {
            currpage++;
            moviesViewModel.getMoviesFromAPI(currpage);
            txtViewCurrPage.setText(String.valueOf(currpage));
        }
    }

    public void onClickPreviousPage(View view)
    {
        int currpage = moviesViewModel.getMoviesRepository().getPage();
        if (currpage > 1) {
            currpage--;
            moviesViewModel.getMoviesFromAPI(currpage);
            txtViewCurrPage.setText(String.valueOf(currpage));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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
                startActivity(intent);
            case R.id.menuNeedToSee:
                Toast.makeText(this, "Going to history...", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(this,RemainderMovieController.class);
                startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);
    }
}