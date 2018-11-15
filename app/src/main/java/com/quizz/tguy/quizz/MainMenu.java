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
import android.widget.ImageButton;

import com.quizz.tguy.quizz.room.RoomQuizz;
import com.quizz.tguy.quizz.room.RoomViewModel;
import com.quizz.tguy.quizz.settings.Settings;

import java.util.List;

// MainMenu : Main Activity of the application
public class MainMenu extends AppCompatActivity {
    private RoomViewModel mQuizzViewModel;  // View model that allows access to the Room

    // onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu_layout);

        // Set the settings button behaviour
        ImageButton btn_settings = findViewById(R.id.btn_settings);
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Settings activity
                Intent int_settings = new Intent(getApplicationContext(), Settings.class);
                startActivity(int_settings);
            }
        });

        // Create the RecyclerView that contains the list of quizz
        RecyclerView recyclerView = findViewById(R.id.quizzList);
        final MainMenuRecyclerAdapter adapter = new MainMenuRecyclerAdapter(this);
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

    // onRestart : restart the Activity to prevent wrong RecyclerView refreshes
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
