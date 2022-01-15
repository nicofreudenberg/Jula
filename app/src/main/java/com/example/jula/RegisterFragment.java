package com.example.jula;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class RegisterFragment extends Fragment { //RegisterFragment, welches Momentan analog zum LoginFragment agiert, der einzige Unterschied ist das Designö.

    SharedPreferences sp;

    public RegisterFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        Button loginButton = (Button) view.findViewById(R.id.loginbutton);
        Button registerButton = (Button) view.findViewById(R.id.registerbutton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp = getContext().getSharedPreferences("loggedIn", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.putBoolean("loggedIn", true);
                editor.apply();
                getActivity().recreate();
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_home);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                      Navigation.findNavController(view).navigate(R.id.loginFragment);
            }
        });
        return view;
    }

}