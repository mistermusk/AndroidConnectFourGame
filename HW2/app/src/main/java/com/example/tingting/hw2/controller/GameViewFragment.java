package com.example.tingting.hw2.controller;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tingting.hw2.R;

import static android.content.ContentValues.TAG;


public class GameViewFragment extends Fragment {
    GridView gridview;
    GridView gridviewBlank;
    TextView tv;
    // dummy string array to create grid view on touch
    static String[] numbers;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_view_layout, container, false);
        tv = view.findViewById(R.id.textView);
        gridview = view.findViewById(R.id.gridView);
        gridviewBlank = view.findViewById(R.id.gridViewBlank);
        numbers = createNumGrid();



        gridview.setAdapter(new ImageAdapter(getActivity()));


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, numbers);

        gridviewBlank.setAdapter(adapter);

        gridviewBlank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "" + position, Toast.LENGTH_SHORT).show();
            }
        });



//        gridView.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//
//                int x = (int)event.getX();
//                int y = (int)event.getY();
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_UP:
//                        Log.i("TAG", "location");
//
//                }
//                return false;
//            }
//        });




        return view;
    }

    public void updateInfo(boolean isStart)
    {
        if(isStart == true)
        {
            // reset the game
            tv.setText("Start a new game");
        }
        else
        {
            // resume
            tv.setText("Resume the previous game");
            // get the cell[][] and current player info from text file

        }
    }


    public String[] createNumGrid()
    {
        String[] nums = new String[42];
        for (int i = 0; i < 42; ++i)
        {
            nums[i] = "";
        }

        return nums;
    }


}
