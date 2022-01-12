package com.example.jula;

import android.animation.StateListAnimator;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.firestore.FirebaseFirestore;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPollFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPollFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://jula-dd20e-default-rtdb.europe-west1.firebasedatabase.app/");

   DatabaseReference myRef = database.getReference("poll");
   FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<String> answers = new ArrayList<String>();

    Button deleteButton;
    Button sendButton;





    public AddPollFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPollFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPollFragment newInstance(String param1, String param2) {
        AddPollFragment fragment = new AddPollFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_poll, container, false);
        ListView myListView = (ListView) view.findViewById(R.id.listview_answers);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                view.getContext(), // Die aktuelle Umgebung (diese Activity)
                R.layout.answer_row, // Die ID des Zeilenlayouts (der XML-Layout Datei)
                R.id.answer_element,   // Die ID eines TextView-Elements im Zeilenlayout
                answers);
        myListView.setAdapter(adapter);
        EditText pollTitle = view.findViewById(R.id.editTitlePoll);
        EditText pollText = view.findViewById(R.id.editPollText);
        Button addButton = view.findViewById(R.id.AddAnswer);
        EditText editAddAnswers = (EditText) view.findViewById(R.id.editAddAnswers);




        View.OnClickListener addListener = view1 -> {
            if(editAddAnswers.getText().toString().matches("")) {
                Toast.makeText(getContext(), "Bitte gib eine Antwortmöglichkeit ein!", Toast.LENGTH_LONG).show();
            }else{
                System.out.println(editAddAnswers.getText().toString());
                answers.add(editAddAnswers.getText().toString());
                deleteButton.setEnabled(true);
                adapter.notifyDataSetChanged();
            if (answers.size() == 4) {
                addButton.setEnabled(false);
            }
            }

        };
        addButton.setOnClickListener(addListener);

        deleteButton = view.findViewById(R.id.Delete_Answer);
        View.OnClickListener deleteListener = view1 -> {
            if (myListView.getCheckedItemPosition() == -1) {
                Toast.makeText(getActivity(), "Kein Eintrag ausgewählt", Toast.LENGTH_LONG).show();
            } else {

                System.out.println(myListView.getCheckedItemPosition());

                answers.remove(myListView.getCheckedItemPosition());


                adapter.notifyDataSetChanged();
                if (answers.size() <= 3) {
                    addButton.setEnabled(true);
                }

                if (answers.isEmpty()) {
                    deleteButton.setEnabled(false);
                }
            }
        };

        deleteButton.setOnClickListener(deleteListener);

        sendButton = view.findViewById(R.id.sendButton);

        View.OnClickListener sendListener = view1 -> {
           /** Map<String, Object> poll = new HashMap<>();
            poll.put("title", pollTitle.getText().toString());
            poll.put("text", pollText.getText().toString());
            poll.put("answerOptions", answers);
            db.collection("poll").add(poll);
**/
            String pollTitleString = pollTitle.getText().toString();
            if (pollTitle.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Bitte gib einen Titel ein.", Toast.LENGTH_LONG).show();
            } else {

                String pollTextString = pollText.getText().toString();
                if (pollText.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Bitte gib eine Beschreibung ein", Toast.LENGTH_LONG).show();
                } else {
                    if(answers.size()==0){
                        Toast.makeText(getActivity(), "Bitte gib Antwortmöglichkeiten ein", Toast.LENGTH_LONG).show();
                    }else {
                        Map<String, Integer> answersgiven = new HashMap<>();
                        for (int i = 0; i < answers.size(); i++) {

                            answersgiven.put(answers.get(i), 0);
                        }

                        db.collection("polls").add(new Poll(pollTitleString, pollTextString, answers, answersgiven));// {{


                        //{
                        //  for (int i = 0; i <= answers.size(); i++) {
                        ////  Map<String, Integer> answerMap = new HashMap<>();
                        //answerMap.put(answer, 0);
                        //    put("Answer" + i, answerMap);
                        //}
                        //}}));
                        //  for (String answer : answers){
                        //  put(answer)
                        // put(answer, 0);
                        //  }

                        //}}));
                        // DatabaseReference pushedPoll = myRef.push();


                        //pushedPoll.setValue((new Poll(pollTitle.getText().toString(), pollText.getText().toString(), new HashMap<String, Integer>() {{
                        //  for (String answer : answers){
                        //    put(answer, 0);
                        //}
                        //  }})));

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent); // start same activity
                        getActivity().finish(); // destroy older activity
                        getActivity().overridePendingTransition(0, 0);
                    }
                }
            }
        };
        sendButton.setOnClickListener(sendListener);
        return view;



    }

}