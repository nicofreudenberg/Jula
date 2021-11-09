package com.example.jula;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private ArrayList<Post> posts = new ArrayList<Post>();

    public PostsAdapter(ArrayList<Post> posts, MainActivity mainActivity) {
        this.posts=posts;
    }
    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.post_row, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder viewHolder, int position) {
       viewHolder.title.setText(posts.get(position).getTitle());
       viewHolder.text.setText(posts.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        View itemView;

        private TextView title = itemView.findViewById(R.id.title);
        private TextView text = itemView.findViewById(R.id.textinrow);




        public ViewHolder(@NonNull View view) {
            super(view);

           // textView = (TextView) textView.findViewById(R.id.textView);
        }

    }


    }

