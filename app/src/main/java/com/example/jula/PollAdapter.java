package com.example.jula;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class PollAdapter extends RecyclerView.Adapter<PollAdapter.ViewHolder> {

    private List<Poll> polls; //bekannte Liste, die die Umfragen enthält
    private LayoutInflater mInflater;
    SharedPreferences sp; //bekannte SharedPrefences


    PollAdapter(Context context, List<Poll> data) { //Konstruktur des Adapters
        this.mInflater = LayoutInflater.from(context);
        this.polls = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.post_row, parent, false);//Erzeugen und Inflaten des ViewHolders
        sp= PreferenceManager.getDefaultSharedPreferences(view.getContext()); //die SharedPrefences werden hier für das aktivieren / deaktivieren der Buttons benöigt, damit man auch nur einmal abstimmen kann
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Poll poll = polls.get(position); //die Position ist gleich mit dem Index der Umfragen in der Liste, daher kann so die entsprechende Umfrage geholt werden.
        holder.text.setText(poll.getText()); //im ViewHolder werden Text und Titel mit den entsprechenden Werten aus dem Objekt bestückt.
        holder.title.setText(poll.getTitle());



        if (sp.getBoolean(poll.getTitle(), false)) {
            holder.voteButton.setEnabled(false); //wenn man schon abgestimmt hat, darf man nicht nochmal abstimmen. In den Prefences werden als Key der App-Titel genutzt, die true sind wenn schon abgestimmt wurde
        }
        //ClickListener für den Votebutton
        holder.voteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!view.getContext().getSharedPreferences("loggedIn", Context.MODE_PRIVATE).getBoolean("loggedIn", false)) {
                    //loggedIn muss geprüft werden, ob Benutzer eingeloggt ist, sonst darf er an keiner Umfrage teilnehmen
                    Toast.makeText(view.getContext(), "Du musst dich erst einloggen, bevor du an einer Umfrage teilnehmen kannst!", Toast.LENGTH_SHORT).show();

                }else{
                    //Das Poll-Objekt wird in ein Bundle serialisiert und per navigate dem VotingFragment übergeben.
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("poll", polls.get(holder.getAdapterPosition()));
                    polls.clear();
                    Navigation.findNavController((Activity) holder.itemView.getContext(), R.id.nav_host_fragment_activity_main).navigate(R.id.votingFragment, bundle);
                }

            }
        });

        //OnClickListener für results
        holder.results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Poll-Objekt wird in einem Bundle serialisiert und dem pieChartFragment übergeben.
                Bundle bundle = new Bundle();
                bundle.putSerializable("poll", polls.get(holder.getAdapterPosition()));
                Navigation.findNavController((Activity) holder.itemView.getContext(), R.id.nav_host_fragment_activity_main).navigate(R.id.pieChartFragment, bundle);

            }
        });

        }

    @Override
    public int getItemCount() {
        return polls.size();
    } //wird benötigt für die Anzahl der Durchläufe um alle Umfragen darzustellen.

    public class ViewHolder extends RecyclerView.ViewHolder  {
        //ViewHolder Klasse, die die einzelen Umfragn darstellt
        TextView title, text;
        Button results, voteButton;


        ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textinrow);
            title= itemView.findViewById(R.id.title);
            results = itemView.findViewById(R.id.results);
            voteButton = itemView.findViewById(R.id.voteButton);
        }
    }
}

