package com.example.jula;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    //Interface zum globalen Speichern von Werten, hier benutzt um den LoggedIn -Zustand zu speichern.
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Stellt unteres Menü zum Navigieren her
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //Verbindet navController mit Container in der MainActivity worin die Fragments angezeigt werden.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(navView, navController);

        //erzeugen einer Toolbar und ersetzen der ActionBar. Toolbar unterstützt die Menüfunktionen.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Abfrage der Prefences, ob Zustand eingeloggt ist. Es wird mit dem Standardwert false abgefragt, was bedeutet, wenn es den Eintrag loggedIn nicht gibt, wird false zurückgegeben
        //und automatisch das Menü für nicht eingeloggt benutzt. Andererseits natürlich das Menü für den eingeloggten Zustand.
        sp = getSharedPreferences("loggedIn", MODE_PRIVATE);
        if (sp.getBoolean("loggedIn", false)) {
            navView.getMenu().clear();
            navView.inflateMenu(R.menu.bottom_nav_menu_logged_in);
        } else {
            navView.getMenu().clear();
            navView.inflateMenu(R.menu.bottom_nav_menu_not_logged_in);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Gleiches prüfen auf den eingeloggten Zustand. Menü (hinter den 3 Punkten + Login/Logout-Button) ist unterschiedlich für die jeweiligen Zustände
        if (sp.getBoolean("loggedIn", false)) {
            getMenuInflater().inflate(R.menu.menu_logged_in, menu);

        } else {
            getMenuInflater().inflate(R.menu.menu_not_logged_in, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Switch-Case für alle(beide Zustände) möglichen Aktionen im oberen Menü (3-Punkte)
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
                sp.edit().clear().commit(); //löschen der Prefences resultiert in ausgeloggten Zustand
                this.recreate(); //Activty wird neu aufgebaut um Menüs anzupasen
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //wenn der Zurück-Button gedrückt wird, soll das zuletzt angezeigte Framgment aufgerufen werden.

        Navigation.findNavController(this, R.id.nav_host_fragment_activity_main).popBackStack();
    }
}


