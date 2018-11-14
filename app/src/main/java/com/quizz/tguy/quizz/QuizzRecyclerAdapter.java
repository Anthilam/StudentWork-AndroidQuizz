package com.quizz.tguy.quizz;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

// QuizzRecyclerAdapter : adapter for the RecyclerView in the Quizz activity
public class QuizzRecyclerAdapter extends RecyclerView.Adapter<QuizzRecyclerAdapter.AnswersViewHolder> {

    // AnswersViewHolder : ViewHolder for each item of the RecyclerView
    class AnswersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView answersItemView; // The item
        private int nAnswer;                    // The id of answer

        private AnswersViewHolder(View itemView) {
            super(itemView);

            // Add an onClick listener for each item of the RecyclerView
            itemView.setOnClickListener(this);
            answersItemView = itemView.findViewById(R.id.answersItemView);
        }

        // onClick
        @Override
        public void onClick(View v) {
            if (mContext instanceof Quizz) {
                // Set the selected answer and refresh the view
                ((Quizz)mContext).setSelectedAnswer(nAnswer);
                notifyDataSetChanged();
            }
        }
    }

    private final LayoutInflater mInflater;
    private List<String> allAnswers; // Cached copy of answers
    private Context mContext;

    // Constructor
    QuizzRecyclerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    // onCreateViewHolder
    @Override
    public QuizzRecyclerAdapter.AnswersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.quizz_recyclerview_item, parent, false);
        return new AnswersViewHolder(itemView);
    }

    // onBindViewHolder
    @Override
    public void onBindViewHolder(QuizzRecyclerAdapter.AnswersViewHolder holder, int position) {
        if (allAnswers != null) {
            String current = allAnswers.get(position);
            holder.answersItemView.setText(current);    // Set the answer
            holder.nAnswer = position;                  // Set the id of the answer
        }
        else {
            // Covers the case of data not being ready yet.
            holder.answersItemView.setText("No answer");
        }

        // Set the selected answer with a visible color, in this case, green
        if (position == ((Quizz)mContext).getSelectedAnswer()) {
            holder.answersItemView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.quizz_item_background_green));
        }
        // Otherwise, set the other answers with a gray color
        else {
            holder.answersItemView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.quizz_item_background_grey));
        }
    }

    // setAnswers : set a new list of answers and refresh the view
    protected void setAnswers(List<String> answers){
        allAnswers = answers;
        notifyDataSetChanged();
    }

    // getItemCount
    @Override
    public int getItemCount() {
        if (allAnswers != null) {
            return allAnswers.size();
        }
        else {
            return 0;
        }
    }
}
