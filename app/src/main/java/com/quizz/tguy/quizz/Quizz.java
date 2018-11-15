package com.quizz.tguy.quizz;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

// Quizz : Activity representing a playable quizz
public class Quizz extends AppCompatActivity {
    private RoomViewModel mQuizzViewModel;  // View model that allows access to the Room

    private int nbQuestions = 0;
    private int nbAnswers = 0;
    private int trackID = 0; // Progress tracking
    private int selectedAnswer = -1;
    private int score = 0;

    // onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizz_layout);

        // Get the currently played quizz with the Intent extra
        mQuizzViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
        final RoomQuizz rq = mQuizzViewModel.getQuizzById(getIntent().getIntExtra("id", 0));

        nbQuestions = rq.getQuestions_list().size(); // Get the number of questions
        nbAnswers = rq.getAnswers_list().size(); // Get the number of answers

        // Set the title with the name of the quizz
        TextView title = findViewById(R.id.quizzID);
        title.setText(rq.getTitle());

        // Set the first question
        final TextView question = findViewById(R.id.question);
        if (nbQuestions > 0)
        {
            question.setText(rq.getQuestion(trackID));
        }

        // Create the RecyclerView that contains the list of answers
        final RecyclerView recyclerView = findViewById(R.id.answers);
        final QuizzRecyclerAdapter adapter = new QuizzRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        int a = Math.round(rq.getAnswersCount(trackID)/2) > 4 ? 4 : Math.round(rq.getAnswersCount(trackID)/2);

        recyclerView.setLayoutManager(new GridLayoutManager(this, a));
        if (nbAnswers > 0)
        {
            adapter.setAnswers(rq.getAnswers(trackID));
        }

        // Set the home button behaviour
        final ImageButton btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_home = new Intent(getApplicationContext(), MainMenu.class);
                int_home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(int_home);
            }
        });

        // Set the validation button behaviour
        final Button btn_validate = findViewById(R.id.btn_validate);
        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When we validate our answer, go to the next question
                if (selectedAnswer == rq.getGoodAnswer(trackID)) {
                    score++;
                }

                trackID++;

                // While we have questions
                if (trackID < nbAnswers && trackID < nbQuestions) {
                    question.setText(rq.getQuestion(trackID));
                    adapter.setAnswers(rq.getAnswers(trackID));
                    selectedAnswer = -1;
                }
                // If we are out of questions
                else {
                    question.setText("Score final :\n"+ score + "/" + nbQuestions); // Display the score

                    // Hide the recycler view
                    recyclerView.setVisibility(View.INVISIBLE);

                    // Set a new button behaviour
                    btn_validate.setText("Menu");
                    btn_validate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Return to the main menu activity
                            Intent int_home = new Intent(getApplicationContext(), MainMenu.class);
                            int_home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(int_home);
                        }
                    });
                }
            }
        });
    }

    // setSelectedAnswer : function called within the RecyclerView to set the selected answer
    protected void setSelectedAnswer(int nAnswer) {
        selectedAnswer = nAnswer;
    }

    // getSelectedAnswer : function called within the RecyclerView to get the selected answer
    protected int getSelectedAnswer() {
        return selectedAnswer;
    }
}

