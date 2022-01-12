package com.example.jula;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PieChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PieChartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AnyChartView anyChartView;
     Poll poll;

    public PieChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PieChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PieChartFragment newInstance(String [] param1, Poll param2) {
        PieChartFragment fragment = new PieChartFragment();
        Bundle args = new Bundle();
        args.putStringArray("Ergebnisse", param1);
        args.putSerializable("poll", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String [] Ergebnisse = getArguments().getStringArray("Ergebnisse");
            int [] Ergebnis = getArguments().getIntArray("Ergebnis");
            poll = (Poll) getArguments().getSerializable("poll");



        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);
        anyChartView = (AnyChartView) view.findViewById(R.id.any_chart_view);
         setupPieChart(view);
        return view ;
    }

    public void setupPieChart(View view) {
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
    //    for (int i = 0; i < getArguments().getStringArray("Ergebnisse").length; i++) {
      //      dataEntries.add(new ValueDataEntry(getArguments().getStringArray("Ergebnisse")[i], getArguments().getIntArray("Ergebnis")[i]));
       for(int i=0; i<poll.getAnswerOptions().size(); i++) {
           dataEntries.add(new ValueDataEntry(poll.getAnswerOptions().get(i), poll.getAnswers().get(poll.getAnswerOptions().get(i))));
       }
        pie.data(dataEntries);
        AnyChartView anyChartView = (AnyChartView) view.findViewById(R.id.any_chart_view);

        anyChartView.setChart(pie);
    }
}