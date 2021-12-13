package com.example.jula;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.anychart.AnyChartView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VotingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VotingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button buttonA;
    Button buttonB;
    Button buttonErgebnis;
    TextView aText;
    TextView bText;
    int zaehlerA;
    int zaehlerB;
    Toolbar toolbar;
    String[] Ergebnisse = {"A", "B"};
    int[] Ergebnis;
    AnyChartView anyChartView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
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
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_voting, container, false);
        aText = view.findViewById(R.id.countA) ;
        bText = view.findViewById(R.id.countB);
        buttonA = view.findViewById(R.id.btn_A);
        buttonB = view.findViewById(R.id.btn_B);
        buttonErgebnis = view.findViewById(R.id.buttonErgebniss);

        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ACount = aText.getText().toString().trim();
                zaehlerA = Integer.parseInt(ACount);
                zaehlerA++;
                aText.setText(String.valueOf(zaehlerA));
            }
        });

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String BCount = bText.getText().toString().trim();
                zaehlerB = Integer.parseInt(BCount);
                zaehlerB++;
                bText.setText(String.valueOf(zaehlerB));
            }
        });

        buttonErgebnis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              int []  Ergebnis = {zaehlerA, zaehlerB};
                Bundle bundle = new Bundle();
                bundle.putStringArray("Ergebnisse", Ergebnisse);
                bundle.putIntArray("Ergebnis", Ergebnis);
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.pieChartFragment, bundle);
            }
        });
        return view;
    }
    public void onClickButtonA(View view) {
        String ACount = aText.getText().toString().trim();
        zaehlerA = Integer.parseInt(ACount);
        zaehlerA++;
        aText.setText(String.valueOf(zaehlerA));
    }

    public void onClickButtonB(View view) {
        String BCount = bText.getText().toString().trim();
        zaehlerB = Integer.parseInt(BCount);
        zaehlerB++;
        bText.setText(String.valueOf(zaehlerB));
    }

    public void onClickButtonReset(View view){
        zaehlerA = 0 ;
        zaehlerB = 0;
        aText.setText(String.valueOf(zaehlerA));
        bText.setText(String.valueOf(zaehlerB));
    }
}