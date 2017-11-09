/*********************************************
 * Gmae ac
 */
package com.example.tingting.hw2.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tingting.hw2.R;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        boolean isStart;
        isStart = getIntent().getExtras().getBoolean("isStart");
        GameViewFragment gameViewFragment = (GameViewFragment)getFragmentManager().findFragmentById(R.id.game_frag);
        gameViewFragment.updateInfo(isStart);


    }
}
