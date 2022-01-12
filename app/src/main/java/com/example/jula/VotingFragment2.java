package com.example.jula;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VotingFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VotingFragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Serializable mParam3;

    public VotingFragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VotingFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static VotingFragment2 newInstance(String param1, String param2, Poll param3) {
        VotingFragment2 fragment = new VotingFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        //args.putSerializable("poll", param3);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getSerializable("poll");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_voting2, container, false);
        ConstraintLayout myLayout = (ConstraintLayout) view.findViewById(R.id.moin) ;
        Poll poll = (Poll) mParam3;
        TextView textinrow = view.findViewById(R.id.textinrow2);
        textinrow.setText(poll.getText());
        TextView title = view.findViewById(R.id.Title2);
        //title.setText(poll.getTitle());
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams( ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
      //for(int i =0;  i< poll.getAnsweroptions().length; i++){
        //  TextView answers = new TextView(view.getContext());
          //answers.setLayoutParams(lp);
          //myLayout.addView(answers);

        //  answers.setText(poll.getAnsweroptions()[i].toString());
        //}



        return view;
    }
}