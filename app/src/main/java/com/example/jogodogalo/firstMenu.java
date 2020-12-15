package com.example.jogodogalo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class firstMenu extends AppCompatActivity {

    // Declare an intent variable
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_menu);

        // Set layout full screen (without action bar)
        getSupportActionBar().hide();

        // Get button from layout
        Button singlePlayer = (Button)findViewById(R.id.buttonS);
        Button multiPlayer = (Button)findViewById(R.id.buttonM);

        // Create an intent to open MainActivity
        intent = new Intent(this,MainActivity.class);
        // Set button event's
        singlePlayer.setOnClickListener(this::singlePlayer);
        multiPlayer.setOnClickListener(this::multiPlayer);
    }

    private void singlePlayer(View v){
        // Set extra with value to indicate it is single player
        intent.putExtra("IS_SINGLE_PLAYER",1);
        startActivity(intent);
    }
    private void multiPlayer(View v) {
        // Set extra with null value
        intent.putExtra("IS_SINGLE_PLAYER",0);
        startActivity(intent);
    }


}