package com.example.jula;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;


public class VotingFragment extends Fragment { //Fragment, in dem man in der Umfrage abstimmen kann

    Poll poll; //Umfrageobjekt
    SharedPreferences sp; //bekannte SharedPrefences, zum Setzen des Wertes um anzuzeigen, dass an der Umfrage teilgenommen wurde
    FirebaseFirestore db = FirebaseFirestore.getInstance(); //Datenbankreferenz

    public VotingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            poll = (Poll) getArguments().getSerializable("poll"); //Übergebenes serialisiertes Pollobjekt wird auf auf Pollobjekt gecasted
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_voting, container, false); //erzeugen und inflaten der View
        sp = PreferenceManager.getDefaultSharedPreferences(view.getContext()); //initalisieren der SP

        //Initalsieren der im Code benötigten Interaktionen
        RadioGroup rg = view.findViewById(R.id.radioGroup);
        TextView pollTitle = view.findViewById(R.id.pollTitle);
        TextView polLText = view.findViewById(R.id.pollText);
        //Beschriftungen einfügen
        pollTitle.setText(poll.getTitle());
        polLText.setText(poll.getText());

        //Lesen der Antwortmöglichkeiten aus demObjekt
        List<String> answerOptions = poll.getAnswerOptions();
        int i = 0;

        for (String answersOption : answerOptions) {

            RadioButton rb = new RadioButton(view.getContext());
            rb.setId(i); //RadioButtons erhalten eine ID von 0-3, der Einfachheit beim späteren Abfragen halber.
            i++;
            rb.setText(answersOption);
            rg.addView(rb);
        }
        Button resultButtonVotingFragment = view.findViewById(R.id.resultButtonVotingFragment);
        //Initalisieren und Erzeugen des OnClickListeners wenn man von der Abstimmungsseite ohne Abstimmung direkt auf die Ergebnisse möchte.
        resultButtonVotingFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle(); //Pollobjekt wird wieder verpackt und an PieChartFragment übergeben
                bundle.putSerializable("poll", poll);
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.pieChartFragment, bundle);
            }
        });

        //Initialisieren und Erzeugen des OnClickListeners für das Abstimmen
        Button voteButtonVotingFragment = view.findViewById(R.id.voteButtonVotingFragment);
        voteButtonVotingFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rg.getCheckedRadioButtonId() != -1) { //prüfen ob RadioButton gewählt wurde
                    int checked_ID = rg.getCheckedRadioButtonId();
                    String checkedText = poll.getAnswerOptions().get(checked_ID); //Text wird benötigt um Wert in der Datenbank, dessen Key der checkedText ist, upzudaten.

                    //setzen der Preferences, dass in der Umfrage abgestimmt wurde
                    SharedPreferences.Editor editor = sp.edit();

                    editor.putBoolean(poll.getTitle(), true);
                    editor.putBoolean(checkedText, true);
                    editor.apply();
                    editor.commit();

                    //Holen der Refenrenz auf die Collection
                    CollectionReference dbref = db.collection("polls");

                    dbref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() { //erzeugen eines OnCompleteListener zum Erzeugen eines QuerySnapshots
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            QuerySnapshot qsnap = task.getResult(); //es wird eine Task erzeugt. Mit dieser Task kann ein QuerySnapshot erstellt werden, um die richtige Umfrage zu finden

                            List<DocumentSnapshot> documentsList = qsnap.getDocuments(); //Über den QuerySnapshot wird wieder eine Liste der vorhanden Dokumente (Umfragen) erstellt.

                            for (int i = 0; i < documentsList.size(); i++) {
                                DocumentSnapshot document = documentsList.get(i);

                                if (document.get("title").equals(poll.getTitle())) { //Liste wird durchlaufen, bis die Umfrage gefunden wird, auf die abgestimmt wurde. Titel = key in der Datenbank
                                    Poll poll = document.toObject(Poll.class); //Mapping des Dokuments auf ein PollObjekt
                                    Map<String, Integer> answers = poll.getAnswers(); //die Antworten werden aus dem Objekt auf eine neue Map initialisiert.

                                    //für die gewählte Antwort (checkedText ist der key der Map und gleichzeitig die Antwort, die auch den Radiobutton repräsentiert)
                                    // wird der Value um 1 inkrementiert
                                    answers.put(checkedText, answers.get(checkedText) + 1);

                                    //neue Map (mit neuem Wert) wird im Pollobjekt gesetzt.
                                    poll.setAnswers(answers);

                                    // DocumentReference auf die Umfrage wird erzeugt, mit der die Umfrage geupdated werden kann.
                                    DocumentReference docRef = dbref.document(document.getId());
                                    docRef.update("answers", answers); //In dem Dokument (Umfrage) wird das field answers mit der neuen Map ersetzt

                                    Bundle bundle = new Bundle(); //Pollobjekt wird wieder in einem Bundle Serialisiert und dem PiechartFragment übergeben
                                    bundle.putSerializable("poll", poll);
                                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.pieChartFragment, bundle);
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Bitte tätige eine Auswahl!", Toast.LENGTH_SHORT).show(); //sollte keine Antwort gewählt worden sein, wird das hier abgefangen
                }
            }
        });
        return view;
    }
}