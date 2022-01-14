package com.example.jula;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO
//Zurück Button in den Stellen wo es muss
//Jula in Apptitel
public class PollAdapter extends RecyclerView.Adapter<PollAdapter.ViewHolder> {

    private List<Poll> polls;
    private LayoutInflater mInflater;
    SharedPreferences sp;


    PollAdapter(Context context, List<Poll> data) {
        this.mInflater = LayoutInflater.from(context);
        this.polls = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.post_row, parent, false);
        sp= PreferenceManager.getDefaultSharedPreferences(view.getContext());


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Poll poll = polls.get(position);
        holder.text.setText(poll.getText());
        holder.title.setText(poll.getTitle());


        if (sp.getBoolean(poll.getTitle(), false)) {
            holder.voteButton.setEnabled(false);
        }
        holder.voteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!view.getContext().getSharedPreferences("loggedIn", Context.MODE_PRIVATE).getBoolean("loggedIn", false)) {
                    Toast.makeText(view.getContext(), "Du musst dich erst einloggen, bevor du an einer Umfrage teilnehmen kannst!", Toast.LENGTH_SHORT).show();

                }else{
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("poll", polls.get(holder.getAdapterPosition()));
                    polls.clear();
                    Navigation.findNavController((Activity) holder.itemView.getContext(), R.id.nav_host_fragment_activity_main).navigate(R.id.votingFragment, bundle);
                }

            }
        });

        holder.results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("poll", polls.get(holder.getAdapterPosition()));
                Navigation.findNavController((Activity) holder.itemView.getContext(), R.id.nav_host_fragment_activity_main).navigate(R.id.pieChartFragment, bundle);
            }
        });
        }

    @Override
    public int getItemCount() {
        return polls.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView title, text;
        RadioButton answer1,answer2,answer3,answer4;
        RadioGroup rg;
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

