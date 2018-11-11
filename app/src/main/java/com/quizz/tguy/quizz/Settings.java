package com.quizz.tguy.quizz;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

// Settings : settings menu available in the main menu of the app
public class Settings extends AppCompatActivity {
    // View Model to access the Room
    private RoomViewModel mQuizzViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        // Fill the main RecyclerView that contains the quizz list
        RecyclerView recyclerView = findViewById(R.id.quizzListSettings);
        final SettingsRecyclerAdapter adapter = new SettingsRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Observe any modification in the quizz list and adapt the RecyclerView
        mQuizzViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
        mQuizzViewModel.getAllQuizz().observe(this, new Observer<List<RoomQuizz>>() {
            @Override
            public void onChanged(@Nullable final List<RoomQuizz> roomAllQuizz) {
                adapter.setQuizzes(roomAllQuizz);
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

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
