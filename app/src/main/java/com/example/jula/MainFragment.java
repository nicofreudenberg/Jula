package com.example.jula;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.health.SystemHealthManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SharedPreferences sp;
    RecyclerView recyclerView;
    List<Poll> polls;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://jula-dd20e-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference("poll");
    FirebaseFirestore db = FirebaseFirestore.getInstance();



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        super(R.layout.fragment_main);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {


        polls = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_main, parent, false);
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.refreshView);
        recyclerView= view.findViewById(R.id.recyclerView);

      //  myRef.keepSynced(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        FloatingActionButton flbt = view.findViewById(R.id.addPoll);
        flbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!view.getContext().getSharedPreferences("loggedIn", Context.MODE_PRIVATE).getBoolean("loggedIn", false)){
                    Toast.makeText(getActivity(), "Du musst dich erst einloggen, bevor du eine Umfrage anlegen kannst!",Toast.LENGTH_LONG).show();
                }else{
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.addPollFragment);
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //polls.clear();
                recyclerView.getAdapter().notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);


            }
        });

        PollAdapter adapter = new PollAdapter(view.getContext(), polls);
        recyclerView.setAdapter(adapter);
        ValueEventListener pollListener;

        CollectionReference dbr = db.collection("polls");
        dbr.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                //Poll poll = value.getValue(Poll.class);
                value.getQuery().orderBy("timestamp");
               List<DocumentSnapshot> doc = value.getDocuments();
               for (DocumentSnapshot snaps : doc){
                   //Map<String, Object> map = snaps.getData();
                   Poll poll = snaps.toObject(Poll.class);
                   polls.add(poll);

                   adapter.notifyDataSetChanged();

               }



            }
        });
        return view;
    }


    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
