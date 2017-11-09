/******************************************************
* Main function for game view, it will listen to user's
 * clicking and drop disks accordingly. It will also
 * display the winner and save current game state if there's any.
 *******************************************************/
package com.example.tingting.hw2.controller;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


import com.example.tingting.hw2.R;
import com.example.tingting.hw2.model.Board;

import java.util.ArrayList;

import static com.example.tingting.hw2.model.Board.Turn.FIRST;
import static com.example.tingting.hw2.model.Board.Turn.SECOND;


public class GameViewFragment extends Fragment {
    GridView gridview;
    GridView gridviewBlank;
    TextView tv;
    private Board board;
    // dummy string array to create grid view on touch
    static String[] numbers;
    // data array in the case of resume
    ArrayList<Integer> dataRow = new ArrayList<Integer>();
    ArrayList<Integer> dataColumn = new ArrayList<Integer>();

    public static ImageAdapter imageAdapter;

    int ROW= 6;
    int COL = 7;
//    final ImageAdapter imageAdapter = new ImageAdapter(getActivity());


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_view_layout, container, false);

        tv = view.findViewById(R.id.textView);
        gridview = view.findViewById(R.id.gridView);
        gridviewBlank = view.findViewById(R.id.gridViewBlank);
        numbers = createNumGrid();

        // reload the saved state automatically
        updateInfo(false);


        // main game board set images
//        imageAdapter = new ImageAdapter(getActivity());
        gridview.setAdapter(new ImageAdapter(getActivity()));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, numbers);

        // whole board blank grid to do on click
        gridviewBlank.setAdapter(adapter);
        gridviewBlank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                int column = position % COL;

                // init alert
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

                // check if play is legal
                int row = board.lastAvailableRow(column);
                int turn = getTurn();

                // invalid move
                if(row == -1) {
                    alertDialog.setTitle("Warning");
                    alertDialog.setMessage("Invalid move!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                // valid move
                else
                {
                    board.occupyCell(row, column);
                    // change image
                    imageAdapter.changeImage(5-row, column, turn);
                    gridview.setAdapter(imageAdapter);
                }

                // check win
                if (board.checkWin())
                {
                    printLog();
                    alertDialog.setTitle("Result");
                    alertDialog.setMessage("Player " + turn + " wins!");
                    alertDialog.show();
                }


                // check if the board is full or not, if so, tie
                if(board.checkFull())
                {
                    alertDialog.setTitle("Result");
                    alertDialog.setMessage("It is a tie!");
//                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
                    alertDialog.show();
                }

                // if no winner, alter play's turn
                board.alterTurn();


                // save current state to file
                saveText(row+"", column+"");
            }
        });
        return view;
    }

    // update text view box and the model value
    public void updateInfo(boolean isStart)
    {

        board = new Board(ROW,COL);
        imageAdapter = new ImageAdapter(getActivity());

        if(isStart == false)
        {
            // resume
            // get the data for row and column
            getEntries();
            int size = dataRow.size();
            System.out.println("dataRow: " + size);

            // if number of disks is even, it is player 1's turn
            if(size%2 == 0)
            {
                board.turn = FIRST;
            }
            else
            {
                board.turn = SECOND;
            }

            // populate the board
            // set adapter
//            ImageAdapter imageAdapter = new ImageAdapter(getActivity());
            gridview.setAdapter(new ImageAdapter(getActivity()));
            gridview.setAdapter(imageAdapter);

            for(int i = 0; i < size; ++i)
            {
                int turn;

                if(i % 2 == 0)
                {
                    turn = 1;
                }
                else
                {
                    turn = 2;
                }
                int row = dataRow.get(i);
                int col = dataColumn.get(i);
                if(turn == 1)
                {
                    board.turn = FIRST;
                }
                else
                {
                    board.turn = SECOND;
                }
                board.occupyCell(row, col);
                imageAdapter.changeImage(5-row,col,turn);
                gridview.setAdapter(imageAdapter);
            }
        }
        else
       {
//           PrintWriter pw = new PrintWriter("file.txt");
//           pw.close();

           File dir = getActivity().getFilesDir();
           File file = new File(dir, "file.txt");
           file.delete();
//           board.reset();
       }
    }


    // get current play's turn, return int
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

    public void saveText(String row, String column)
    {
        try{
            // open output for writing
            OutputStreamWriter out=new OutputStreamWriter(getActivity().openFileOutput("file.txt", Context.MODE_APPEND));

            out.write(row);
            out.write('\n');
            out.write(column);
            out.write('\n');
            out.close();
        }
        catch(java.io.IOException e)
        {
            Toast.makeText(getActivity(),"Sorry Text couldn't be added", Toast.LENGTH_SHORT).show();
        }
    }

    public void getEntries()
    {
        // open the file for reading we have to surround it with a try
        try {
            InputStream inStream = getActivity().openFileInput("file.txt");//open the text file for reading

            // if file the available for reading
            if (inStream != null) {

                // prepare the file for reading
                InputStreamReader inputReader = new InputStreamReader(inStream);
                BufferedReader buffReader = new BufferedReader(inputReader);

                String line = "";

                int count = 0;
                while (( line = buffReader.readLine()) != null) {
                    //buffered reader reads only one line at a time, hence we give a while loop to read all till the text is null
                    int dataInt = Integer.parseInt(line);
                    if(count % 2 == 0)
                    {
                        dataRow.add(dataInt);
                    }
                    else
                    {
                        dataColumn.add(dataInt);
                    }
                    ++count;
                }}
            }

        //now we have to surround it with a catch statement for exceptions
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printLog()
    {
        for(int i = 0; i < 6; ++i)
        {
            for(int j = 0; j < 7; ++j)
            {
                if(board.cells[i][j].empty)
                {
                    Log.i("TAG", "F");
                }
                else
                {
                    Log.i("TAG", "T");
                }
            }
            Log.i("TAG", "new");
        }
    }
}


