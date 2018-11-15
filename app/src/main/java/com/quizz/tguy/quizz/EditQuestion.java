package com.quizz.tguy.quizz;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

// EditQuestion : Activity that allows the complete edition of a question and its answers
public class EditQuestion extends AppCompatActivity
{
    private RoomViewModel mQuizzViewModel;  // View model that allows access to the Room
    private RoomQuizz rq;
    private int question_id;

    // onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editquestion_layout);

        // Get the currently edited quizz with the Intent extra
        mQuizzViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
        rq = mQuizzViewModel.getQuizzById(getIntent().getIntExtra("qid", 0));
        question_id = getIntent().getIntExtra("id", 0);

        // Set the title with the question id
        TextView title = findViewById(R.id.titleEditQuestion);
        title.append(" " + (question_id +1));

        // Set the first EditText with the question
        final EditText question = findViewById(R.id.questionEdit);
        question.setText(rq.getQuestion(question_id));

        // Set the second EditText with the id of the good answer
        final EditText goodAnswer = findViewById(R.id.goodAnswerEdit);
        goodAnswer.setText(""+rq.getGoodAnswer(question_id));

        // Create the RecyclerView that contains the list of answers
        final RecyclerView recyclerView = findViewById(R.id.answersList);
        final EditQuestionRecyclerAdapter adapter = new EditQuestionRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Observe any modification in the answers list and adapt the RecyclerView
        mQuizzViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
        mQuizzViewModel.getAllQuizz().observe(this, new Observer<List<RoomQuizz>>() {
            @Override
            public void onChanged(@Nullable final List<RoomQuizz> roomAllQuizz) {
                adapter.setAnswers(rq.getAnswers(question_id));
                recyclerView.scrollToPosition(adapter.getItemCount()-1); // Scroll to the bottom of the RecyclerView
            }
        });

        // Set the home button behaviour
        ImageButton btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_home = new Intent(getApplicationContext(), MainMenu.class);
                int_home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(int_home);
            }
        });

        // Set the updateQuestion button behaviour
        ImageButton btn_updateQuestion = findViewById(R.id.btn_updateQuestion);
        btn_updateQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the question in the database
                rq.setQuestionInAListOfQuestions(question.getText().toString(), question_id);
                mQuizzViewModel.updateQuizz(rq);
            }
        });

        // Set the addAnswer button behaviour
        ImageButton btn_addAnswer = findViewById(R.id.btn_addAnswer);
        btn_addAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add an answer in the database
                rq.addAnswerToList("Default", question_id);
                mQuizzViewModel.updateQuizz(rq);
            }
        });

        // Set the setGoodAnswer button behaviour
        ImageButton btn_setGoodAnswer = findViewById(R.id.btn_setGoodAnswer);
        btn_setGoodAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the good answer in the database
                rq.setGoodAnswer(question_id, Integer.parseInt(goodAnswer.getText().toString()));
                mQuizzViewModel.updateQuizz(rq);
            }
        });
    }

    // setAnswers : function called within the RecyclerView to update the answers list
    protected void setAnswers(String answer, int index) {
        rq.setAnswerInAListOfAnswer(answer, question_id, index);
        mQuizzViewModel.updateQuizz(rq);
    }

    // delAnswer : function called within the RecyclerView to delete an answer in the answers list
    protected void delAnswer(int answer) {
        rq.delAnswerFromList(answer, question_id);
        mQuizzViewModel.updateQuizz(rq);
    }

    // onRestart : restart the Activity to prevent wrong RecyclerView refreshes
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}

