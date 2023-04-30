package com.example.hw1_game.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.example.hw1_game.Interfaces.CallBack_ZoomClick;
import com.example.hw1_game.R;
import com.google.android.material.button.MaterialButton;

public class ListFragment extends Fragment {

    private CallBack_ZoomClick callBack_ZoomClick;
    private MaterialButton list_BTN_zoom;

    public void setCallBack(CallBack_ZoomClick callBack_ZoomClick) {
        this.callBack_ZoomClick = callBack_ZoomClick;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        list_BTN_zoom.setOnClickListener(v -> {
            sendClicked();
        });
        return view;
    }


    private void sendClicked() {
        if(callBack_ZoomClick != null){
          //  callBack_SendClick.userNameChosen(list_ET_name.getText().toString());
        }
    }

    private void findViews(View view) {
       // list_ET_name = view.findViewById(R.id.list_ET_name);
        list_BTN_zoom = view.findViewById(R.id.list_BTN_zoom);
    }


}
