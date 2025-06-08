package com.example.afinal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerCustomAdapter extends RecyclerView.Adapter<RecyclerCustomAdapter.ViewHolder> {

    public ArrayList<Score> localDataset;

    public RecyclerCustomAdapter(ArrayList<Score> dataset) {
        localDataset = dataset;
    }

    @NonNull
    @Override
    public RecyclerCustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.custom_list, viewGroup, false);
        return new ViewHolder(contactView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.gmail.setText(localDataset.get(position).getFootballName());
        viewHolder.score.setText("" + localDataset.get(position).getScore());

        // צבע שונה לכל שורה לסירוגין
        if (position % 2 == 0)
            viewHolder.linearLayout.setBackgroundColor(Color.parseColor("#4CAF50"));
        else
            viewHolder.linearLayout.setBackgroundColor(Color.parseColor("#FFC8E4A9"));
    }

    @Override
    public int getItemCount() {
        return localDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public final TextView gmail;
        public final TextView score;
        public final LinearLayout linearLayout;

        public ViewHolder(@NonNull View view) {
            super(view);
            view.setOnClickListener(this::select);
            view.setOnLongClickListener(this);
            gmail = view.findViewById(R.id.gmail);
            score = view.findViewById(R.id.score);
            linearLayout = view.findViewById(R.id.layout);
        }

        private void select(View view) {
            Toast.makeText(itemView.getContext(), "Click " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onLongClick(View v) {
            Toast.makeText(itemView.getContext(), "Item " + (getAdapterPosition() + 1) + " removed", Toast.LENGTH_SHORT).show();
            localDataset.remove(getAdapterPosition());
            notifyDataSetChanged();
            return true;
        }
    }
}
