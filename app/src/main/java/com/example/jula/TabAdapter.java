package com.example.jula;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class TabAdapter extends FragmentStateAdapter {

    ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    Context context;

    public TabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Context context) {
        super(fragmentManager, lifecycle);
        this.context=context;
        fragments.add(MainFragment.newInstance("Home"));
        fragments.add(CalenderFragment.newInstance("Calender bitch", "wat denn noch"));
        SharedPreferences sp = context.getSharedPreferences("loggedIn", Context.MODE_PRIVATE);
        if (sp.getBoolean("loggedIn", false)) {
            fragments.add(ChatFragment.newInstance("das ist der Chat", "ja wirklich"));
        }

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

       Fragment fragment;
        switch (position) {
            case 0:
                return MainFragment.newInstance("Home");
            case 1:
                fragment = CalenderFragment.newInstance("Kalender oder so?", "digga kp");
                break;
            default:
                fragment = MainFragment.newInstance("Home, sweet home");
        }/**
         FragmentTransaction ft = fragment.getChildFragmentManager().beginTransaction();
        //ft.replace(R.id.fragment_placeholder, fragment);
        ft.commit();**/
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
