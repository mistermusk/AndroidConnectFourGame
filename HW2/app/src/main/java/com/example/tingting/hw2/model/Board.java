/************************************************************
 * Offers an interface allowing the user to work with the game object to create,
 * read (review), update (play), and delete (restart) it. Persistence of the game state is
 * required for the in-progress game. Therefore, the game state should be saved at
 * appropriate times while playing and should be able to reload it again on app start

 * Contains the state information of the current player’s turn and state of the grid.
 * Offers a method to drop a disk in one of the columns.
 *************************************************************/

package com.example.tingting.hw2.model;

import java.io.Serializable;
import java.util.Observable;


public class Board extends Observable implements Serializable {
    private int numCols;
    private int numRows;
    public boolean hasWinner;

    // make game contiguous, set cells as public static
    public Cell[][] cells;

    public enum Turn
    {
        FIRST, SECOND
    }

    // make turn contiguous
    public Turn turn;

    // constructor
    public Board(int rows, int cols)
    {
        numCols = cols;
        numRows = rows;
        cells = new Cell[rows][cols];
        reset();
    }

    // reset the game
    public void reset()
    {
        hasWinner = false;
        turn = Turn.FIRST;
        for (int row = 0; row < numRows; ++row)
        {
            for (int col = 0; col < numCols; ++col)
            {
                cells[row][col] = new Cell();
            }
        }
    }

    // check the current available row if user put disk into col
    public int lastAvailableRow(int col)
    {
        for (int row = numRows - 1; row >= 0; row--)
        {
            if (cells[row][col].empty)
            {
                return row;
            }
        }
        return -1;
    }

    // set cell to be occupied when player puts a disk into it
    public void occupyCell(int row, int col)
    {
        cells[row][col].setPlayer(turn);
    }

    // check if the board is full or not
    public boolean checkFull()
    {
        for(int i = 0; i < numRows; ++i)
        {
            for(int j = 0; j < numCols; ++j)
            {
                if(cells[i][j].empty)
                {
                    return false;
                }
            }
        }
        return true;
    }
    // change the turn of the player
    public void alterTurn() {
        if (turn == Turn.FIRST)
        {
            turn = Turn.SECOND;
        }
        else
        {
            turn = Turn.FIRST;
        }
    }

    // check the contiguous of the current plays' disks
    private boolean isContiguous(Turn player, int dirX, int dirY, int row, int col, int count)
    {
        if (count >= 4)
        {
            return true;
        }
        if (col < 0 || col >= numCols || row < 0 || row >= numRows)
        {
            return false;
        }
        Cell cell = cells[row][col];
        if (cell.player == player)
        {
            return isContiguous(player, dirX, dirY, row + dirX, col + dirY, count + 1);
        }
        else
        {
            return isContiguous(player, dirX, dirY, row + dirX, col + dirY, 0);
        }
    }


    // check for the win for players
    public boolean checkWin() {
        for (int row = 0; row < numRows; row++) {
            if (isContiguous(turn, 0, 1, row, 0, 0) || isContiguous(turn, 1, 1, row, 0, 0) || isContiguous(turn, -1, 1, row, 0, 0)) {
                hasWinner = true;
                return true;
            }
        }
        for (int col = 0; col < numCols; col++) {
            if (isContiguous(turn, 1, 0, 0, col, 0) || isContiguous(turn, 1, 1, 0, col, 0) || isContiguous(turn, -1, 1, numCols - 1, col, 0)) {
                hasWinner = true;
                return true;
            }
        }
        return false;
    }



}
