package com.quizz.tguy.quizz.settings;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quizz.tguy.quizz.R;

import java.util.List;

// EditQuizzRecyclerAdapter : adapter for the RecyclerView in the EditQuizz activity
public class EditQuizzRecyclerAdapter extends RecyclerView.Adapter<EditQuizzRecyclerAdapter.QuestionViewHolder> {

    // QuestionViewHolder : ViewHolder for each item of the RecyclerView
    class QuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView questionItemView;    // The item
        private int id;                             // The id of the question

        private QuestionViewHolder(View itemView) {
            super(itemView);

            // Add an onClick listener for every item of the RecyclerView
            itemView.setOnClickListener(this);
            questionItemView = itemView.findViewById(R.id.quizzItemView);

            // Set the delQuestion button behaviour
            itemView.findViewById(R.id.btn_delQuestion).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((EditQuizz)mContext).delQuestion(id);
                }
            });
        }

        // onClick
        @Override
        public void onClick(View v) {
            // Launch the EditQuestion activity
            Context context = v.getContext();
            Intent intent_quizz = new Intent(context, EditQuestion.class);
            intent_quizz.putExtra("qid", qid);  // Put the id of the quizz in the extras
            intent_quizz.putExtra("id", id);    // Put the id of the question in the extras
            context.startActivity(intent_quizz);
        }
    }

    private final LayoutInflater mInflater;
    private List<String> questions; // Cached copy of all questions
    private Context mContext;
    private int qid;

    // Constructor
    EditQuizzRecyclerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    // onCreateViewHolder
    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.editquizz_recyclerview_item, parent, false);
        return new QuestionViewHolder(itemView);
    }

    // onBindViewHolder
    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        if (questions != null) {
            String current = questions.get(position);
            // Set the TextView with the question
            holder.questionItemView.setText("Question "+ (position+1) + " : " + current);
            holder.id = position; // Set the id
        } else {
            // Covers the case of data not being ready yet.
            holder.questionItemView.setText("No question");
        }
    }

    // setQuizzes : set a new list of questions and refresh the view
    protected void setQuestions(List<String> questionsList){
        questions = questionsList;
        notifyDataSetChanged();
    }

    // setQuizzId : set the quizz id
    protected void setQuizzId(int qid) {
        this.qid = qid;
    }

    // getItemCount
    @Override
    public int getItemCount() {
        if (questions != null) {
            return questions.size();
        }
        else {
            return 0;
        }
    }
}