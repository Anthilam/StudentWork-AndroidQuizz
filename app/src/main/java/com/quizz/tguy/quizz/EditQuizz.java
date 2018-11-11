package com.quizz.tguy.quizz;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

// EditQuizz : quizz edition menu available in the settings of the app
public class EditQuizz extends AppCompatActivity
{
    // View Model to access the Room
    private RoomViewModel mQuizzViewModel;
    private RoomQuizz rq;

    // onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editquizz_layout);

        // Get the current quizz with the intent extra
        mQuizzViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
        rq = mQuizzViewModel.getQuizzById(getIntent().getIntExtra("id", 0));

        // Set the title
        TextView title = findViewById(R.id.titleModifQuizz);
        title.append(" " + rq.getStrQuizz_id());

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

        Button btn_addQ = findViewById(R.id.btn_addQ);
        btn_addQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the quizz selected by the user
                rq.addDefaultQuestion();
                mQuizzViewModel.updateQuizz(rq);
                Context context = v.getContext();
                Intent intent_quizz = new Intent(context, EditQuestion.class);
                intent_quizz.putExtra("qid", rq.getQuizz_id()); // Put the id of the quizz
                intent_quizz.putExtra("id", rq.getQuestions_list().size()-1);
                context.startActivity(intent_quizz);
            }
        });

        // Fill the main RecyclerView that contains the question list
        RecyclerView recyclerView = findViewById(R.id.questionsList);
        final EditQuizzRecyclerAdapter adapter = new EditQuizzRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Observe any modification in the question list and adapt the RecyclerView
        mQuizzViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
        mQuizzViewModel.getAllQuizz().observe(this, new Observer<List<RoomQuizz>>() {
            @Override
            public void onChanged(@Nullable final List<RoomQuizz> roomAllQuizz) {
                adapter.setQuestions(rq.getQuestions_list());
                adapter.setQuizzId(rq.getQuizz_id());
            }
        });
    }

    void delQuestion(int question) {
        rq.delQuestion(question);
        mQuizzViewModel.updateQuizz(rq);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}

