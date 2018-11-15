package com.quizz.tguy.quizz.settings;

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
import android.widget.ImageButton;
import android.widget.TextView;

import com.quizz.tguy.quizz.MainMenu;
import com.quizz.tguy.quizz.R;
import com.quizz.tguy.quizz.room.RoomQuizz;
import com.quizz.tguy.quizz.room.RoomViewModel;

import java.util.List;

// EditQuizz : Activity that shows the list of question available for edition for a quizz
public class EditQuizz extends AppCompatActivity
{
    private RoomViewModel mQuizzViewModel;  // View model that allows access to the Room
    private RoomQuizz rq;

    // onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editquizz_layout);

        // Get the quizz to edit with the Intent extra
        mQuizzViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
        rq = mQuizzViewModel.getQuizzById(getIntent().getIntExtra("id", 0));

        // Set the title with the name of the quizz
        TextView title = findViewById(R.id.titleEditQuizz);
        title.append("\n" + rq.getTitle());

        // Create the RecyclerView that contains the list of questions
        final RecyclerView recyclerView = findViewById(R.id.answersList);
        final EditQuizzRecyclerAdapter adapter = new EditQuizzRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Observe any modification in the questions list and adapt the RecyclerView
        mQuizzViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
        mQuizzViewModel.getAllQuizz().observe(this, new Observer<List<RoomQuizz>>() {
            @Override
            public void onChanged(@Nullable final List<RoomQuizz> roomAllQuizz) {
                adapter.setQuestions(rq.getQuestions_list());
                adapter.setQuizzId(rq.getQuizz_id());
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

        // Set the addQuestion button behaviour
        ImageButton btn_addQuestion = findViewById(R.id.btn_addQuestion);
        btn_addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rq.addDefaultQuestion();            // Add a default question to the quizz
                mQuizzViewModel.updateQuizz(rq);    // Update the database
                Context context = v.getContext();   // Launch the EditQuestion activity
                Intent intent_quizz = new Intent(context, EditQuestion.class);
                intent_quizz.putExtra("qid", rq.getQuizz_id()); // Put the quizz id in the extras
                intent_quizz.putExtra("id", rq.getQuestions_list().size()-1); // Put the question id in the extras
                context.startActivity(intent_quizz);
            }
        });
    }

    // delQuestion : function called within the RecyclerView to delete a question in the questions list
    protected void delQuestion(int question) {
        rq.delQuestion(question);
        mQuizzViewModel.updateQuizz(rq);
    }

    // onRestart, restart the Activity to prevent wrong RecyclerView refreshes
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}

