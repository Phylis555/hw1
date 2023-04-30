package com.example.hw1_game.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.hw1_game.R;

public class MapFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
      //  map_LBL_title = view.findViewById(R.id.map_LBL_title);
    }








}
