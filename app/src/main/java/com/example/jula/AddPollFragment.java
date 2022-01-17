package com.example.jula;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddPollFragment extends Fragment {


    FirebaseFirestore db = FirebaseFirestore.getInstance(); // Referenz auf die Firestore-Datenbank (Firebase)


    List<String> answers = new ArrayList<>(); // Liste, die die Antwortmöglichkeiten hält

   // Interaktionsmöglichkeiten im View die im Code verwendet werden.
    EditText pollTitle;
    EditText pollText;
    ImageButton addButton;
    EditText editAddAnswers;
    Button  sendButton;
    ImageButton deleteButton;
    ListView myListView;




    public AddPollFragment() {
        // wird zum Erstellen neuer Instanzen benötigt.
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_poll, container, false); //erstellen und Inflaten der View

        // die Interaktionsmöglichkeiten werden über findViewById initalisiert.
        myListView= (ListView) view.findViewById(R.id.listview_answers);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(),R.layout.answer_row,R.id.answer_element, answers);
        myListView.setAdapter(adapter);

        pollTitle = view.findViewById(R.id.editTitlePoll);
        pollText = view.findViewById(R.id.editPollText);
        addButton = view.findViewById(R.id.AddAnswer);
        editAddAnswers = (EditText) view.findViewById(R.id.editAddAnswers);
        deleteButton = view.findViewById(R.id.Delete_Answer);
        deleteButton.setEnabled(false);
        deleteButton.setAlpha(0.25F);
        sendButton = view.findViewById(R.id.sendButton);

       // OnClicklistener für den AddButton, zuständig für das Hinzufügen von Antwortmöglichkeiten in die Liste
        View.OnClickListener addListener = view1 -> {
            if (editAddAnswers.getText().toString().matches("")) { //wenn Texteingabefeld leer ist, zeige Toast-Meldung
                Toast.makeText(getContext(), "Bitte gib eine Antwortmöglichkeit ein!", Toast.LENGTH_LONG).show();
            } else {

               if (answers.contains(editAddAnswers.getText().toString())){ //Da Firebase nicht mehrfach den gleichen Wert speichern kann, muss dies abgefangen werden.
                   Toast.makeText(getContext()," Diese Antwortmöglichkeit gibt es schon!", Toast.LENGTH_LONG).show();
                }
               else{
                answers.add(editAddAnswers.getText().toString());
                deleteButton.setEnabled(true);
                deleteButton.setAlpha(1.00F);//aktivieren des deleteButtons um eingebene Antwort wieder löschen zu können.
                adapter.notifyDataSetChanged(); //Adapter wird benachrichtigt und aktualisiert die View

                   if (answers.size() == 4) { // Aufgrund einer Designentscheidung werden max. 4 Antworten zugelassen, wenn die Anzahl erreicht wird, wird der addButton deaktiviert.
                    addButton.setEnabled(false);
                }
                }
            }
            editAddAnswers.setText(""); //Zurücksetzen der Eingabe, damit Nutzer nicht alles wieder Löschen muss
        };
        addButton.setOnClickListener(addListener); //verbinden des Listeners mit dem Button

        //OnClickListener für den deleteButton, Stellen analog zum addListener werden übersprungen
        View.OnClickListener deleteListener = view1 -> {
            if (myListView.getCheckedItemPosition() == -1) {
                Toast.makeText(getActivity(), "Kein Eintrag ausgewählt", Toast.LENGTH_LONG).show();
            } else {
                answers.remove(myListView.getCheckedItemPosition()); //Die ausgewählte Antwort wird entfernt.
                adapter.notifyDataSetChanged();
                if (answers.size() <= 3) {
                    addButton.setEnabled(true); //wenn wieder weniger als 4 Antworten in der Liste sind, wird der addButton wieder aktiviert.
                }
                if (answers.isEmpty()) {
                    deleteButton.setEnabled(false);
                    deleteButton.setAlpha(0.25F);
                    //Umgekehrte Aktion zum addListener, ist die Antwortliste leer, wird der deleteButton deaktiviert
                }
            }
        };

        deleteButton.setOnClickListener(deleteListener);

        //Listener für den sendButton, zuständig für das Schreiben in die Datenbank
        View.OnClickListener sendListener = view1 -> {

            String pollTitleString = pollTitle.getText().toString();
            if (pollTitle.getText().toString().equals("")) { //prüfe ob ein Titel eingebenen wurde
                Toast.makeText(getActivity(), "Bitte gib einen Titel ein.", Toast.LENGTH_LONG).show();
            } else {
                String pollTextString = pollText.getText().toString(); //Beschreibung der Umfrage, ist optional
                {
                    if (answers.size() == 0) { //prüfe ob Antwortmöglichkeiten eingegeben wurden
                        Toast.makeText(getActivity(), "Bitte gib Antwortmöglichkeiten ein", Toast.LENGTH_LONG).show();
                    } else {
                        Map<String, Integer> answersgiven = new HashMap<>(); // Hashmap zum Speichern der Antworten wird angelegt und mit dem Default-Wert 0 bestückt. Keys sind die möglichen Antworten
                        for (int i = 0; i < answers.size(); i++) {
                            answersgiven.put(answers.get(i), 0);
                        }

                        //Methodeaufruf zum Anlegen einer Umfrage in der Datenbank. Hierarchie ist Polls->Umfrage1 (Unique-ID wird dafür erzeugt)->Einträge(welcher mit einem Classmapper aus den Feldern der Klasse Poll erzeugt werden)
                        db.collection("polls").add(new Poll(pollTitleString, pollTextString, answers, answersgiven, System.nanoTime()));

                        //Zurück zum Homebildschirm
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_home);

                    }
                }
            }
        };
        sendButton.setOnClickListener(sendListener);
        return view;


    }

}