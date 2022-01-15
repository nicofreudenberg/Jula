package com.example.jula;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {


    FloatingActionButton flbt; //Button zum Hinzufügen von Umfragen
    RecyclerView recyclerView; //View, in der die Umfragen dargestellt werden
    List<Poll> polls = new ArrayList<>(); //Liste der Umfragen
    FirebaseFirestore db = FirebaseFirestore.getInstance(); //Instanz der Datenbank

    public MainFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, parent, false); //erzeugen und Inflaten der View

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.refreshView); //erzeugen des SwipeRefreshLayouts (runterziehen = aktualisieren)
        recyclerView= view.findViewById(R.id.recyclerView); //initalisieren des RecyclerView

        //RecyclerView wird eingerichtet. Benötigt wird ein LayoutManager und DividerItemDecoration (Design, Devider sind zwischen den CardViews)
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        //Initialisieren des FloatingActionButtons
        flbt = view.findViewById(R.id.addPoll);
        //ClickListener für den FloatingActionButton
        flbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Prüfen, ob man eingeloggt ist, sonst darf man keine Umfrage hinzufügen.
                if(!view.getContext().getSharedPreferences("loggedIn", Context.MODE_PRIVATE).getBoolean("loggedIn", false)){
                    Toast.makeText(getActivity(), "Du musst dich erst einloggen, bevor du eine Umfrage anlegen kannst!",Toast.LENGTH_LONG).show();
                }else{
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.addPollFragment);
                }
            }
        });
        //OnRefreshListener für den Fall, dass der User nach unten wischt um die Umfragen zu aktualisieren


        PollAdapter adapter = new PollAdapter(view.getContext(), polls); //erzeugen des Adapters, siehe Polladapter
        recyclerView.setAdapter(adapter);

        //Abruf der Daten aus der Datenbank. Es wird eine Query erstellt, die die Daten aus der Collection polls abruft und absteigend nach der Erstellungszeit sortiert (neueste oben)
        Query dbr = db.collection("polls").orderBy("timestamp", Query.Direction.DESCENDING);
        dbr.addSnapshotListener(new EventListener<QuerySnapshot>() { //die Query bekommt einen SnapShotListener. Der SnapShot erhält die Werte
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
               List<DocumentSnapshot> doc = value.getDocuments(); //Die Documents (in der Firebasewelt sind das die  einzelnen Umfragen), werden in eine Liste hinzugefügt.

               for (DocumentSnapshot snaps : doc){ //Diese Liste wird durchlaufen

                   Poll poll = snaps.toObject(Poll.class);
                   //Für jeden DocumentSnapshot (beinhaltet die tatsächlichen Daten der Umfrage) werden die Daten auf ein Poll-Objekt mit einer
                   // Mapping-Methode gemappt. Da die Felder und Keys der Daten übereinstimmen, lassen sich die Documents und Poll-Objekte aufeinander mappen.
                   polls.add(poll); //Poll-Objekt wird der Liste hinzugefügt und der RecylcerViewAdapter über neue Daten informiert.
               }
                adapter.notifyDataSetChanged();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //bei Refresh wird über Navigation eine neue Instanz des MainFragment erzeugt, welcher die Daten neu abruft.

                Navigation.findNavController(view).navigate(R.id.navigation_home);
                swipeRefreshLayout.setRefreshing(false); //SpinningWheel ausstellen
            }
        });
        return view;
    }
}
