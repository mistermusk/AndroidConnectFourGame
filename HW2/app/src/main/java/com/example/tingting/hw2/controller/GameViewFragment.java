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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tingting.hw2.R;
import com.example.tingting.hw2.model.Board;

import static android.content.ContentValues.TAG;
import static com.example.tingting.hw2.model.Board.Turn.FIRST;


public class GameViewFragment extends Fragment {
    GridView gridview;
    GridView gridviewBlank;
    TextView tv;
    private Board board;

    // dummy string array to create grid view on touch
    static String[] numbers;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_view_layout, container, false);

        tv = view.findViewById(R.id.textView);
        gridview = view.findViewById(R.id.gridView);
        gridviewBlank = view.findViewById(R.id.gridViewBlank);
        numbers = createNumGrid();
        board = new Board(7,6);
        board.reset();
        // clean the board or not


        // main game board
        final ImageAdapter imageAdapter = new ImageAdapter(getActivity());
        gridview.setAdapter(new ImageAdapter(getActivity()));
//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                View p = (View)v.getParent();
//                p.performClick();
//                Toast.makeText(getActivity().getApplicationContext(), "column " + position%7,
//                        Toast.LENGTH_SHORT).show();
//            }
//        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, numbers);

        // upper blank grid
        gridviewBlank.setAdapter(adapter);
        gridviewBlank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                int column = position % 7;
//                Toast.makeText(getActivity().getApplicationContext(),
//                        "column " + column, Toast.LENGTH_SHORT).show();



                // check if play is legal
                int row = board.lastAvailableRow(column);
                // invalid move
                if( row == -1)
                {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Invalid move!", Toast.LENGTH_SHORT).show();
                }
                // valid move
                else
                {
                    board.occupyCell(row, column);
                    int turn = getTurn();
                    // change image
                    imageAdapter.changeImage(5-row, column, turn);
                    gridview.setAdapter(imageAdapter);
                }

                // check win
                if (board.checkWin())
                {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "player" + getTurn() + "wins!", Toast.LENGTH_SHORT).show();
                }

                // if no winner, alter play's turn
                board.alterTurn();
            }
        });
        return view;
    }

    // update text view box and the model value
    public void updateInfo(boolean isStart)
    {
        if(isStart == true)
        {
            // reset the game
            tv.setText("Welcome!");
        }
        else
        {
            // resume
            tv.setText("Welcome back!");
            // get the cell[][] and current player info from text file

        }
    }


    // get current play's trun, return int
    public int getTurn()
    {
        if(board.turn == FIRST)
        {
            return 1;
        }
        else
        {
            return 2;
        }
    }
    // dummy grid populated by empty string
    public String[] createNumGrid()
    {
        String[] nums = new String[200];
        for (int i = 0; i < 200; ++i)
        {
            nums[i] = "";
        }
        return nums;
    }
}
