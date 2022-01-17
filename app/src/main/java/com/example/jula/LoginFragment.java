package com.example.jula;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


public class LoginFragment extends Fragment { //Fragment für den Login

  //Deklaration der benötigten Buttons
  Button loginButton;
  Button registerButton;
  //Interface zum globalen Speichern von Werten, hier benutzt um den LoggedIn -Zustand zu speichern.
  SharedPreferences sp;


    public LoginFragment() {
        //wird zum Erstelln neuer Instanzen benötigt.
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false); //Erzeugen und inflaten der View

        //Initialisierung der Buttons über findByViewId
        loginButton = (Button) view.findViewById(R.id.loginbutton);
        registerButton = (Button) view.findViewById(R.id.registerbutton);

        //OnclickListener für den LoginButton
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Die Preferences  für loggedIn werden erzeugt.
                sp = getContext().getSharedPreferences("loggedIn", Context.MODE_PRIVATE);
                //Preferences müssen über einen Editor verändert werden. Es wird der wert für loggedIn auf true gesetzt. Dieser wird in der MainActvity abgefragt
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.putBoolean("loggedIn", true);
                editor.apply();
                //Neuerzeugen der MainActivity, um eingeloggten Zustand abzubilden, anschließend wird dorthin navigiert.

                Intent loggedIn = new Intent (getActivity(), MainActivity.class);
                loggedIn.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                startActivity(loggedIn);



                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_home);
            }
        });

        //Listener für registerButton, mit dem man vom Login-Fenster auf das Registrierenfenster kommt.
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.registerFragment);
            }
        });
        return view;
    }
}