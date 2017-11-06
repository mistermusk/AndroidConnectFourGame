/*********************************************
 * Unit cell class for individual cell in the board
 **************************************************/

package com.example.tingting.hw2.model;


public class Cell {
    public boolean empty;
    public Board.Turn player;

    // constructor
    public Cell(){empty = true;}

    public void setPlayer(Board.Turn player)
    {
        this.player = player;
        empty = false;
    }
}
