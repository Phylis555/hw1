package com.example.hw1_game.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.example.hw1_game.Logic.GameManager;
import com.example.hw1_game.Model.ScoreList;
import com.example.hw1_game.R;
import com.example.hw1_game.Utilities.MySP;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap gMap;
    FrameLayout map;
    ArrayList<LatLng>markersList = new ArrayList<LatLng>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        findViews(view);
        // Get the map fragment and initialize the map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        loadData();
        return view;
    }

    private void loadData()
    {
        String fromSP =  MySP.getInstance().getString(GameManager.SCORE_OBJ,"");
        ScoreList scorelistFromJson;
        if(!fromSP.isEmpty())
            scorelistFromJson = new Gson().fromJson(fromSP,ScoreList.class );
        else
            scorelistFromJson = new ScoreList();
        for(int i=0; i< scorelistFromJson.getScores().size(); i++)
        {
            markersList.add(new LatLng(scorelistFromJson.getScores().get(i).getLatitude(), scorelistFromJson.getScores().get(i).getLongitude()));
        }
    }
    private void findViews(View view) {
       map = view.findViewById(R.id.map);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        for(int i=0; i<markersList.size();i++)
        {
            gMap.addMarker(new MarkerOptions().position(markersList.get(i)).title("Score"));
            gMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            gMap.moveCamera(CameraUpdateFactory.newLatLng(markersList.get(i)));
        }
    }

    public void zoomInOnLocation(double latitude, double longitude) {
        LatLng location = new LatLng(latitude, longitude);
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
    }

}



