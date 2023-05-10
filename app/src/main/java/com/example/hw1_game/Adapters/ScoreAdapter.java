package com.example.hw1_game.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hw1_game.Interfaces.RecyclerViewInterface;
import com.example.hw1_game.Model.Score;

import com.example.hw1_game.R;

import com.google.android.material.textview.MaterialTextView;
import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>{

    private final RecyclerViewInterface recyclerViewInterface;
    private Context context;
    private ArrayList<Score> scores;

    public ScoreAdapter(Context context, ArrayList<Score> scores,RecyclerViewInterface recyclerViewInterface ){
        this.context = context;
        this.scores = scores;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("passed VT:", "" + viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item, parent, false);
        ScoreViewHolder scoreViewHolder = new ScoreViewHolder(view);
        return scoreViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        Score score = getItem(position);
        holder.score_LBL.setText(" Score: "+score.getScore());
       holder.location_LBL.setText("["+score.getLatitude() + "," + score.getLongitude()+"]");

    }

    @Override
    public int getItemCount() {
        return this.scores == null ? 0 : scores.size();
    }

    private Score getItem(int position) {
        return this.scores.get(position);
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder {


            private MaterialTextView score_LBL;
            private MaterialTextView location_LBL;
            public ScoreViewHolder(@NonNull View itemView) {
                super(itemView);

                score_LBL = itemView.findViewById(R.id.score_LBL);
                location_LBL = itemView.findViewById(R.id.location_LBL);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(recyclerViewInterface != null)
                        {
                            int pos = getAdapterPosition();

                            if( pos != RecyclerView.NO_POSITION)
                            {
                                recyclerViewInterface.onItemClick(pos);
                            }
                        }

                    }
                });
            }
        }
    }

