package com.example.jula;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;


public class PieChartFragment extends Fragment {


     AnyChartView anyChartView; //View zum Erstellen des Tortediagramms
     Poll poll; // Poll-Objekt, welches die Daten enthält

    public PieChartFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) { //Beim Aufruf über navigate wird ein Bundle mitgegeben welches das Poll-Objekt enthält. Es wird hier deserialisiert und auf ein Poll-Objekt gecasted.
            poll = (Poll) getArguments().getSerializable("poll");
       }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false); //erzeugen und inflaten der View
        anyChartView = (AnyChartView) view.findViewById(R.id.any_chart_view); //initialsieren des AnyCharts
        Pie pie = AnyChart.pie(); //erzeugen eines Tortiagramms
        List<DataEntry> dataEntries = new ArrayList<>(); //Liste der Antwortmöglichkeiten
        for(int i=0; i<poll.getAnswerOptions().size(); i++) {
            //für jede Antwortmöglichkeit, die sich in der Liste answerOptions im Poll-Objekt befindet, wird die entsprechende
            // Antwortzahl aus der HashMap answers genommen und in ein ValueDataEntry eingefügt, welches die Grundlage des Tortendiagramms ist
            dataEntries.add(new ValueDataEntry(poll.getAnswerOptions().get(i), poll.getAnswers().get(poll.getAnswerOptions().get(i))));
        }
        pie.data(dataEntries); //die DataEntries Liste dem Tortendiagramm übergeben.
        anyChartView.setChart(pie); //darstellen des Diagramms

        return view ;
    }

}