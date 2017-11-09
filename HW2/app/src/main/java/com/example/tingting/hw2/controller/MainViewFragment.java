/**************************************
 * fragment for main view. It will give info on
 * whether the user wants to resume or restart the game
 */
package com.example.tingting.hw2.controller;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tingting.hw2.R;

public class MainViewFragment extends Fragment{
    Button startButton;
    Button resumeButton;
    OnMainButtonClickListener onMainButtonClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.main_view_layout, container, false);
        startButton = view.findViewById(R.id.start);
        resumeButton = view.findViewById(R.id.resume);
        startButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                boolean isStart = true;
                onMainButtonClickListener.buttonSelected(isStart);
            }

        });

        resumeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                boolean isStart = false;
                onMainButtonClickListener.buttonSelected(isStart);
            }

        });
        return view;

    }
    public interface OnMainButtonClickListener
    {
         public void buttonSelected(boolean isStart);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try
        {
            onMainButtonClickListener = (OnMainButtonClickListener) activity;
        }
        catch(Exception e){}

    }
}
