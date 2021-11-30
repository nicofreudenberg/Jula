package com.example.jula;

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

    public TabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        fragments.add(MainFragment.newInstance("Home"));
        fragments.add(new RegisterFragment());

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

       /** Fragment fragment;
        switch (position) {
            case 0:
                return MainFragment.newInstance("Home");
            case 1:
                fragment = new RegisterFragment();
                break;
            default:
                fragment = new MainFragment();
        }
        FragmentTransaction ft = fragment.getChildFragmentManager().beginTransaction();
        //ft.replace(R.id.fragment_placeholder, fragment);
        ft.commit(); **/
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
