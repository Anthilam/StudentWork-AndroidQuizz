package com.quizz.tguy.quizz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

// SettingsMenu : settings menu available in the main menu of the app
public class SettingsMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        // Set edit button behaviour
        Button btn_editquizz = findViewById(R.id.btn_editQuizz);
        btn_editquizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the edit quizz activity
                Intent int_editquizz = new Intent(getApplicationContext(), EditquizzMenu.class);
                startActivity(int_editquizz);
            }
        });

        // Set download button behaviour
        final Button btn_dlquizz = findViewById(R.id.btn_dlQuizz);
        btn_dlquizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Download a list of quizz from the url : https://dept-info.univ-fcomte.fr/joomla/images/CR0700/Quizzs.xml
                btn_dlquizz.setBackgroundColor(Color.RED);
            }
        });

        // Set the home button behaviour
        Button btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to the main menu
                Intent int_home = new Intent(getApplicationContext(), MainMenu.class);
                int_home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(int_home);
            }
        });
    }
}
