package com.example.hw1_game.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw1_game.Adapters.ScoreAdapter;
import com.example.hw1_game.Interfaces.CallBack_ZoomClick;
import com.example.hw1_game.Interfaces.RecyclerViewInterface;
import com.example.hw1_game.Logic.GameManager;
import com.example.hw1_game.Model.Score;
import com.example.hw1_game.Model.ScoreList;
import com.example.hw1_game.R;

import com.example.hw1_game.Utilities.MySP;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

public class ListFragment extends Fragment implements RecyclerViewInterface {

    private CallBack_ZoomClick callBack_ZoomClick;
    private ShapeableImageView score_IMG_marker;
    private  ScoreList scorelistFromJson;
    private RecyclerView main_LST_scores;

    public void setCallBack(CallBack_ZoomClick callBack_ZoomClick) {
        this.callBack_ZoomClick = callBack_ZoomClick;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews();
        return view;
    }

    private void initViews() {

        String fromSP =  MySP.getInstance().getString(GameManager.SCORE_OBJ,"");

        if(!fromSP.isEmpty())
            scorelistFromJson = new Gson().fromJson(fromSP,ScoreList.class );
        else
            scorelistFromJson = new ScoreList();
        Log.d("My Array", scorelistFromJson.toString());
        ScoreAdapter scoreAdapter = new ScoreAdapter(getContext(), scorelistFromJson.getScores(),this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_scores.setLayoutManager(linearLayoutManager);
        main_LST_scores.setAdapter(scoreAdapter);
    }

    private void findViews(View view) {
        score_IMG_marker = view.findViewById(R.id.score_IMG_marker);
        main_LST_scores = view.findViewById(R.id.main_LST_scores);
    }

    @Override
    public void onItemClick(int position) {
        if(callBack_ZoomClick !=null)
        {
            Score sc = scorelistFromJson.getScores().get(position);
            callBack_ZoomClick.locationPin(sc.getLatitude(),sc.getLongitude());
        }
    }

}
