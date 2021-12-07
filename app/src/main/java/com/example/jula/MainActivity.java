package com.example.jula;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MainActivity extends AppCompatActivity {



    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  getSharedPreferences("loggedIn", MODE_PRIVATE).edit().clear().commit(); //ausloggen, beim Appstart
        init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    //    getSupportFragmentManager().clearBackStack("lol");
      //  init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (sp.getBoolean("loggedIn", false)) {

            getMenuInflater().inflate(R.menu.menu_logged_in, menu);
        } else {

            getMenuInflater().inflate(R.menu.menu_not_logged_in, menu);
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.register_settings:


                Navigation.findNavController(this, R.id.nav_host_fragment_activity_main).navigate(R.id.registerFragment);


                return true;
            case R.id.login_settings:
                Navigation.findNavController(this, R.id.nav_host_fragment_activity_main).navigate(R.id.loginFragment);


                return true;
            case R.id.profile:

                Navigation.findNavController(this, R.id.nav_host_fragment_activity_main).navigate(R.id.profileFragment);
                return true;
            case R.id.awards:

                Navigation.findNavController(this, R.id.nav_host_fragment_activity_main).navigate(R.id.awardFragment);
                return true;
            case R.id.logout:

                sp.edit().clear().commit();

                init();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void init() {

        BottomNavigationView navView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        NavigationUI.setupWithNavController(navView, navController);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sp = getSharedPreferences("loggedIn", MODE_PRIVATE);
        if (sp.getBoolean("loggedIn", false)) {

            navView.getMenu().clear();
            navView.inflateMenu(R.menu.bottom_nav_menu_logged_in);
        } else {

            navView.getMenu().clear();
            navView.inflateMenu(R.menu.bottom_nav_menu_not_logged_in);
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


