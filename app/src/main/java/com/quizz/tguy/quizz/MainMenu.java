package com.quizz.tguy.quizz;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

// MainMenu : main activity of the app
public class MainMenu extends AppCompatActivity {

    // View Model to access the Room
    private RoomViewModel mQuizzViewModel;

    // onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu_layout);

        // Set settings button behaviour
        Button btn_settings = findViewById(R.id.btn_settings);
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start settings activity
                Intent int_settings = new Intent(getApplicationContext(), SettingsMenu.class);
                startActivity(int_settings);
            }
        });

        // Fill the main RecyclerView that contains the quizz list
        RecyclerView recyclerView = findViewById(R.id.quizzList);
        final RoomRecyclerAdapter adapter = new RoomRecyclerAdapter(this);
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
    }
}
