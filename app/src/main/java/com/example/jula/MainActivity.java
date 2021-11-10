package com.example.jula;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
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


            posts.add(new Post("Bundestagswahl 2021", "Bist du zufrieden mit dem Wahlergebnis?"));
            posts.add(new Post("Pizza oder Döner?", "Was schmeckt besser??"));
            posts.add(new Post ("noch ein Beispiel", "damit man scrollen zeigen kann"));
            posts.add(new Post ("dafür brauch man", "noch eins"));

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            //    RecyclerView recyclerView = new RecyclerView(this);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(new PostsAdapter(this, posts));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    mLayoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);



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


