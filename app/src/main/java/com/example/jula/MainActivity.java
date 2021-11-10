package com.example.jula;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = new Toolbar(this);


        List<Post> posts = new ArrayList<Post>();

        for (int i = 0; i < 50; i++) {
            posts.add(new Post("animal #", "peter"));
            //posts.add(new Post("Peter", "ist Peter cool?", "das ist ein link"));

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
            //    RecyclerView recyclerView = new RecyclerView(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new PostsAdapter(this, posts));


        }
    }
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


