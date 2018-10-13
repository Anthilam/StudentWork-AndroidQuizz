package com.quizz.tguy.quizz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SettingsMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        Button btn_editquizz = findViewById(R.id.btn_editQuizz);
        Button btn_dlquizz = findViewById(R.id.btn_dlQuizz);
        Button btn_home = findViewById(R.id.btn_home);

        btn_editquizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_editquizz = new Intent(getApplicationContext(), EditquizzMenu.class);
                startActivity(int_editquizz);
            }
        });

        btn_dlquizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_dlquizz = new Intent(getApplicationContext(), DLquizzMenu.class);
                startActivity(int_dlquizz);
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_home = new Intent(getApplicationContext(), MainMenu.class);
                int_home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(int_home);
            }
        });
    }
}
