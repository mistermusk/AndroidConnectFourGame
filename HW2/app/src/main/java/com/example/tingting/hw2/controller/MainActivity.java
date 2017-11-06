package com.example.tingting.hw2.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tingting.hw2.R;

public class MainActivity extends AppCompatActivity implements  MainViewFragment.OnMainButtonClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void buttonSelected(boolean isStart)
    {
        GameViewFragment gameViewFragment = (GameViewFragment)getFragmentManager().findFragmentById(R.id.game_frag);
        if(gameViewFragment == null)
        {
            // display is small
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("isStart", true);
            startActivity(intent);
        }
        else
        {
            // display is big, display both
            gameViewFragment.updateInfo(isStart);
        }



    }
}
