package com.example.netflix.Activity;

import static com.android.volley.Response.error;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.netflix.Adapter.FilmListAdapter;
import com.example.netflix.Domain.ListFilm;
import com.example.netflix.R;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterNewMovies,adapterUpComming;
    private RecyclerView recyclerViewNewMovies,recyclerViewUpcomming;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest,mStringRequest2;
    private ProgressBar loading1,loading2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intView();
        mRequestQueue = Volley.newRequestQueue(this); // Move the initialization here
        sendRequest1();
        sendRequest2();
    }

    private void sendRequest1() {
        mRequestQueue= Volley.newRequestQueue(this);
        loading1.setVisibility(View.VISIBLE);
        mStringRequest =new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1", response -> {
         Gson gson=new Gson();
         loading1.setVisibility(View.GONE);
            ListFilm items = gson.fromJson(response, ListFilm.class);
            if (items != null) {
                adapterNewMovies = new FilmListAdapter(items);
                recyclerViewNewMovies.setAdapter(adapterNewMovies);
            }
        }, error -> {
            Log.i("uilover", "sendRequest1: "+error.toString());
            loading1.setVisibility(View.GONE);
        });
        mRequestQueue.add(mStringRequest);
    }
    private void sendRequest2() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading2.setVisibility(View.VISIBLE);
        mStringRequest2 = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=3", response -> {
            Gson gson = new Gson();
            loading2.setVisibility(View.GONE);
            ListFilm items = gson.fromJson(response, ListFilm.class);
            adapterUpComming = new FilmListAdapter(items);
            recyclerViewUpcomming.setAdapter(adapterUpComming);
        }, error -> {
            loading2.setVisibility(View.GONE);
        });
        mRequestQueue.add(mStringRequest2); // Corrected: Add mStringRequest2 to the request queue
    }

    private void intView() {
        recyclerViewNewMovies=findViewById(R.id.view1);
        recyclerViewNewMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerViewUpcomming=findViewById(R.id.view2);
        recyclerViewUpcomming.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        loading1=findViewById(R.id.loading1);
        loading2=findViewById(R.id.loading2);
    }
}

