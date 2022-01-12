package com.example.jula;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.health.SystemHealthManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        System.out.println("this has been created");
        polls = new ArrayList<>();
        //polls.clear();
        View view = inflater.inflate(R.layout.fragment_main, parent, false);
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.refreshView);
        recyclerView= view.findViewById(R.id.recyclerView);

        myRef.keepSynced(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        FloatingActionButton flbt = view.findViewById(R.id.addPoll);
        flbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.addPollFragment);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                recyclerView.getAdapter().notifyDataSetChanged();
              //  recyclerView.setAdapter(new PollAdapter(view.getContext(), polls));
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
               List<DocumentSnapshot> doc = value.getDocuments();
               for (DocumentSnapshot snaps : doc){
                   //Map<String, Object> map = snaps.getData();
                   Poll poll = snaps.toObject(Poll.class);
                   polls.add(poll);

                   adapter.notifyDataSetChanged();

               }



            }
        });



        /**  pollListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Poll poll = childSnapshot.getValue(Poll.class);
                    poll.setUniqueID(childSnapshot.getKey());
                    polls.add(poll);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        myRef.addValueEventListener(pollListener);**/
        return view;
    }


    @Override
    public void onResume() {
        System.out.println("this has been resumed");
       // recyclerView.setAdapter(new PollAdapter(this.getContext(), polls));
        System.out.println(polls.size());
        super.onResume();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
       // recyclerView.setAdapter(new PollAdapter(this.getContext(), polls));
        System.out.println(polls.size());
        System.out.println("This has been restored");
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        System.out.println(polls.size());
        System.out.println("this has been destroyed");
        super.onDestroyView();


    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        // args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
}
