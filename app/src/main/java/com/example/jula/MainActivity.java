package com.example.jula;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.FragmentManager;

import androidx.viewpager2.widget.ViewPager2;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;



import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    public static ViewPager2 viewPager;

   /** public void fragmentHandling(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, fragment);
        ft.commit();
    }**/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        TabAdapter tabAdapter  = new TabAdapter(fm, getLifecycle());
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(tabAdapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Kalender"));
        //tabLayout.TabLayoutMediator(tabLayout, viewPager) { tab, position ->
          //      tab.text = tabTitles[position]
        //}.attach()
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_not_logged_in, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.register_settings:

             //  viewPager.setCurrentItem(RegisterFragment.newInstance);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}


/**
 * public void onResume() {
 * super.onResume();
 * double longitude = 0.0, latitude = 0.0;
 * Intent intent = new Intent (this, MainActivity.class) ;
 * startActivity(intent);
 * LocationFinder finder = new LocationFinder(this);
 * if (finder.canGetLocation()) {
 * latitude = finder.getLatitude();
 * longitude = finder.getLongitude();
 * Toast.makeText(this, "lat-lng" + latitude +"-" + longitude, Toast.LENGTH_LONG).show();
 * }
 * }
 **/


