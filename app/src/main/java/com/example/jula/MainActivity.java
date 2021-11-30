package com.example.jula;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    public static ViewPager2 viewPager;
    SharedPreferences sp;


    public void fragmentHandling(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.viewPager, fragment);
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSharedPreferences("loggedIn", MODE_PRIVATE).edit().clear().commit(); //ausloggen, beim Appstart

    }

    @Override
    protected void onResume() {

        super.onResume();
        init();
    }

    public void init() {

        FragmentManager fm = getSupportFragmentManager();
        TabAdapter tabAdapter = new TabAdapter(fm, getLifecycle(), this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(tabAdapter);
        viewPager.setNestedScrollingEnabled(true);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        sp = getSharedPreferences("loggedIn", MODE_PRIVATE);


        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                {

                    switch (position) {
                        case 0:
                            tab.setText("Home");
                            break;
                        case 1:
                            tab.setText("Kalender");
                            break;
                        case 2:
                            if (sp.getBoolean("loggedIn", false)) {
                                tab.setText("Chat");
                                break;
                            }

                    }
                }
            }

        }).attach();
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
      if(sp.getBoolean("loggedIn", false)){
          getMenuInflater().inflate(R.menu.menu_logged_in, menu);
      }else{
          getMenuInflater().inflate(R.menu.menu_not_logged_in, menu);
      }



        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.register_settings:

                Intent register = new Intent(this, RegisterActivity.class);
                startActivity(register);
                return true;
            case R.id.login_settings:

                Intent login = new Intent(this, RegisterActivity.class);
                startActivity(login);
                return true;
            case R.id.profile:

                Intent showProfile = new Intent(this, RegisterActivity.class);
                startActivity(showProfile);
                return true;
            case R.id.awards:

                Intent showAwareds = new Intent(this, RegisterActivity.class);
                startActivity(showAwareds);
                return true;
            case R.id.logout:

               sp.edit().clear().commit();
               init();
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


