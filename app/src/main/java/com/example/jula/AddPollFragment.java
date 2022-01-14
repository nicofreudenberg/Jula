package com.example.jula;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPollFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPollFragment extends Fragment {


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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(),R.layout.answer_row,R.id.answer_element, answers);
        myListView.setAdapter(adapter);
        EditText pollTitle = view.findViewById(R.id.editTitlePoll);
        EditText pollText = view.findViewById(R.id.editPollText);
        Button addButton = view.findViewById(R.id.AddAnswer);
        EditText editAddAnswers = (EditText) view.findViewById(R.id.editAddAnswers);


        View.OnClickListener addListener = view1 -> {
            if (editAddAnswers.getText().toString().matches("")) {
                Toast.makeText(getContext(), "Bitte gib eine Antwortmöglichkeit ein!", Toast.LENGTH_LONG).show();
            } else {
                System.out.println(editAddAnswers.getText().toString());
                answers.add(editAddAnswers.getText().toString());
                deleteButton.setEnabled(true);
                adapter.notifyDataSetChanged();
                if (answers.size() == 4) {
                    addButton.setEnabled(false);
                }
            }
            editAddAnswers.setText("");
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

            String pollTitleString = pollTitle.getText().toString();
            if (pollTitle.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Bitte gib einen Titel ein.", Toast.LENGTH_LONG).show();
            } else {

                String pollTextString = pollText.getText().toString();

                {
                    if (answers.size() == 0) {
                        Toast.makeText(getActivity(), "Bitte gib Antwortmöglichkeiten ein", Toast.LENGTH_LONG).show();
                    } else {
                        Map<String, Integer> answersgiven = new HashMap<>();
                        for (int i = 0; i < answers.size(); i++) {

                            answersgiven.put(answers.get(i), 0);
                        }

                        db.collection("polls").add(new Poll(pollTitleString, pollTextString, answers, answersgiven, System.nanoTime()));
                        System.out.println(System.currentTimeMillis());
                        System.out.println(System.nanoTime());


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