package com.quizz.tguy.quizz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

// SettingsRecyclerAdapter : adapter for the RecyclerView in the main menu
public class EditQuestionRecyclerAdapter extends RecyclerView.Adapter<EditQuestionRecyclerAdapter.QuestionsViewHolder> {

    // QuestionsViewHolder : ViewHolder for each item of the RecyclerView
    class QuestionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView questionItemView;
        private int id;

        private QuestionsViewHolder(View itemView) {
            super(itemView);
            itemView.findViewById(R.id.btn_update).setOnClickListener(this); // Add an onClick listener for every item of the RecyclerView
            questionItemView = itemView.findViewById(R.id.editText);
        }

        // onClick
        @Override
        public void onClick(View v) {
            ((EditQuestion)mContext).setAnswers(questionItemView.getText().toString(), id);
        }
    }

    private final LayoutInflater mInflater;
    private List<String> answers; // Cached copy of all quizzes
    private Context mContext;

    // Constructor
    EditQuestionRecyclerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    // onCreateViewHolder
    @Override
    public QuestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.editquestion_recyclerview_item, parent, false);
        return new QuestionsViewHolder(itemView);
    }

    // onBindViewHolder
    @Override
    public void onBindViewHolder(QuestionsViewHolder holder, int position) {
        if (answers != null) {
            String current = answers.get(position);
            holder.questionItemView.setText(current); // Set text with quizz id
            holder.id = position;
        } else {
            // Covers the case of data not being ready yet.
            holder.questionItemView.setText("No ID");
        }
    }

    // setQuizzes : set a new list of quizzes and refresh the view
    void setAnswers(List<String> answersList){
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