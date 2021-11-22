package com.example.jula;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        List<Post> posts = new ArrayList<Post>();


            posts.add(new Post("Bundestagswahl 2021", "Bist du zufrieden mit dem Wahlergebnis?"));


            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(new PostsAdapter(this, posts));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),mLayoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle("Beispielstandort");




    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.register_settings:
                Intent registerIntent = new Intent (this, RegisterActivity.class);

                startActivity(registerIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
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


