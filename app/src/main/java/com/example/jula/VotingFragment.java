package com.example.jula;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.anychart.AnyChartView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.internal.NavigationMenu;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VotingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VotingFragment extends Fragment {
    private Serializable param;
    Poll poll;
    SharedPreferences sp;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public VotingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VotingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VotingFragment newInstance(String param1, String param2) {
        VotingFragment fragment = new VotingFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            poll = (Poll) getArguments().getSerializable("poll");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_voting, container, false);
        sp= PreferenceManager.getDefaultSharedPreferences(view.getContext());
        sp.edit().clear().commit();
        RadioGroup rg = view.findViewById(R.id.radioGroup);
        TextView pollTitle = view.findViewById(R.id.pollTitle);
        TextView polLText = view.findViewById(R.id.pollText);
        pollTitle.setText(poll.getTitle());
        polLText.setText(poll.getText());

        if(poll.getAnswerOptions()!=null){
            List<String> answers = poll.getAnswerOptions();
            int i=0;

            for (String answersOptions : answers) {

                RadioButton rb = new RadioButton(view.getContext());
                rb.setId(i);
                i++;
                rb.setText(answersOptions);
                rg.addView(rb);
            }

            Button voteButtonVotingFragment = view.findViewById(R.id.voteButtonVotingFragment);
            voteButtonVotingFragment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int checked_ID = rg.getCheckedRadioButtonId();
                    String checkedText = poll.getAnswerOptions().get(checked_ID);
                    SharedPreferences.Editor editor = sp.edit();

                    editor.putBoolean(poll.getTitle(), true);
                    editor.putBoolean(checkedText, true);
                    editor.apply();
                    editor.commit();
                    CollectionReference dbref = db.collection("polls");

                    dbref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            QuerySnapshot qsnap = task.getResult();

                            List<DocumentSnapshot> documentsList = qsnap.getDocuments();

                            for (int i = 0; i < documentsList.size(); i++) {
                                DocumentSnapshot document = documentsList.get(i);

                                if (document.get("title").equals(poll.getTitle())) {
                                    Poll poll = document.toObject(Poll.class);

                                    poll.getAnswers().put(checkedText, poll.getAnswers().get(checkedText) + 1);
                                    DocumentReference docRef = dbref.document(document.getId());
                                    List<DocumentSnapshot> doc = qsnap.getDocuments();

                                    docRef.update("answers", poll.getAnswers());

                                }
                            }
                        }

                    });
                    closeFragment();


                }
            });
        }


        return view;
    }
    public void closeFragment() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent); // start same activity
        getActivity().finish(); // destroy older activity
        getActivity().overridePendingTransition(0, 0);
    }
}