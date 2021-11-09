package com.example.jula;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Post> posts;

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
        recyclerView.setAdapter(new PostsAdapter(posts));
    }



    /**public void onResume() {
        super.onResume();
        double longitude = 0.0, latitude = 0.0;
        Intent intent = new Intent (this, MainActivity.class) ;
        startActivity(intent);
        LocationFinder finder = new LocationFinder(this);
        if (finder.canGetLocation()) {
            latitude = finder.getLatitude();
            longitude = finder.getLongitude();
            Toast.makeText(this, "lat-lng" + latitude +"-" + longitude, Toast.LENGTH_LONG).show();
        }
    }**/


}