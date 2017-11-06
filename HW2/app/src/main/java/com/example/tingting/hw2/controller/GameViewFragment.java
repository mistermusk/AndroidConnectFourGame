package com.example.tingting.hw2.controller;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tingting.hw2.R;

public class GameViewFragment extends Fragment {
    TextView tv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.game_view_layout, container, false);
        tv = view.findViewById(R.id.textView);

        return view;
    }

    public void updateInfo(boolean isStart)
    {
        if(isStart == true)
        {
            // reset the game
            tv.setText("start...");

        }
        else
        {
            // resume
            tv.setText("resume...");
        }
    }
}
