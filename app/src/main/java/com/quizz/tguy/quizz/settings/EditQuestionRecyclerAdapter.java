package com.quizz.tguy.quizz.settings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quizz.tguy.quizz.R;

import java.util.List;

// EditQuestionRecyclerAdapter : adapter for the RecyclerView in the EditQuestion activity
public class EditQuestionRecyclerAdapter extends RecyclerView.Adapter<EditQuestionRecyclerAdapter.AnswerViewHolder> {

    // AnswerViewHolder : ViewHolder for each item of the RecyclerView
    class AnswerViewHolder extends RecyclerView.ViewHolder {
        private final TextView answerItemView;  // The item
        private int id;                         // The id of the answer

        private AnswerViewHolder(View itemView) {
            super(itemView);

            // Set updateAnswer button behaviour
            itemView.findViewById(R.id.btn_updateAnswer).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((EditQuestion)mContext).setAnswers(answerItemView.getText().toString(), id);
                }
            });

            // Set delAnswer button behaviour
            itemView.findViewById(R.id.btn_delAnswer).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((EditQuestion)mContext).delAnswer(id);
                }
            });

            // Get the TextView
            answerItemView = itemView.findViewById(R.id.answerEditText);
        }
    }

    private final LayoutInflater mInflater;
    private List<String> answers; // Cached copy of all answers
    private Context mContext;

    // Constructor
    EditQuestionRecyclerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    // onCreateViewHolder
    @Override
    public AnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.editquestion_recyclerview_item, parent, false);
        return new AnswerViewHolder(itemView);
    }

    // onBindViewHolder
    @Override
    public void onBindViewHolder(AnswerViewHolder holder, int position) {
        if (answers != null) {
            String current = answers.get(position); // Get the answer string
            holder.answerItemView.setText(current); // Set item with the answer string
            holder.id = position;                   // Set the id
        } else {
            // Covers the case of data not being ready yet.
            holder.answerItemView.setText("No answer");
        }
    }

    // setQuizzes : set a new list of answers and refresh the view
    protected void setAnswers(List<String> answersList){
        answers = answersList;
        notifyDataSetChanged();
    }

    // getItemCount
    @Override
    public int getItemCount() {
        if (answers != null) {
            return answers.size();
        }
        else {
            return 0;
        }
    }
}