package com.quizz.tguy.quizz;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// Quizz : a quizz available in the main menu of the app
public class Quizz extends AppCompatActivity {

    // View Model to access the Room
    private RoomViewModel mQuizzViewModel;

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

        // Get the current quizz with the intent extra
        mQuizzViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
        final RoomQuizz rq = mQuizzViewModel.getQuizzById(getIntent().getIntExtra("id", 0));
        nbQuestions = rq.getQuestions_list().size();
        nbAnswers = rq.getAnswers_list().size();

        // Set the title
        TextView title = findViewById(R.id.quizzID);
        title.append(" " + rq.getStrQuizz_id());

        // Set the question
        final TextView question = findViewById(R.id.question);
        if (nbQuestions > 0)
        {
            question.setText(rq.getQuestions(trackID));
        }

        // Set the list of answers
        RecyclerView recyclerView = findViewById(R.id.answers);
        final AnswersRecyclerAdapter adapter = new AnswersRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (nbAnswers > 0)
        {
            adapter.setAnswers(rq.getAnswers(trackID));
        }

        // Set the home button behaviour
        final Button btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to the main menu activity
                Intent int_home = new Intent(getApplicationContext(), MainMenu.class);
                int_home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(int_home);
            }
        });

        // Set the validation button behaviour
        final Button btn_ok = findViewById(R.id.btn_validate);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When we validate our answer, get to the next question/answers
                if (selectedAnswer == rq.getRight_answer(trackID))
                {
                    score++;
                }

                trackID++;
                // While we have questions
                if (trackID < nbAnswers && trackID < nbQuestions)
                {
                    question.setText(rq.getQuestions(trackID));
                    adapter.setAnswers(rq.getAnswers(trackID));
                    selectedAnswer = -1;
                }
                // If we are out of questions
                else {
                    question.setText(score + "/" + nbQuestions); // Display the score

                    // Set a new button behaviour
                    btn_ok.setText("Menu");
                    btn_ok.setOnClickListener(new View.OnClickListener() {
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

    // setSelectedAnswer
    public void setSelectedAnswer(int nAnswer) {
        selectedAnswer = nAnswer;
    }

    // getSelectedAnswer
    public int getSelectedAnswer() {
        return selectedAnswer;
    }
}

