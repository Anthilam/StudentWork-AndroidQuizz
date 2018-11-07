package com.quizz.tguy.quizz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

// AnswersRecyclerAdapter : adapter for the RecyclerView in a quizz
public class AnswersRecyclerAdapter extends RecyclerView.Adapter<AnswersRecyclerAdapter.AnswersViewHolder> {

    // AnswersViewHolder : ViewHolder for each item of the RecyclerView
    class AnswersViewHolder extends RecyclerView.ViewHolder {
        private final TextView answersItemView;

        private AnswersViewHolder(View itemView) {
            super(itemView);
            answersItemView = itemView.findViewById(R.id.answersItemView);
        }
    }

    private final LayoutInflater mInflater;
    private List<String> allAnswers; // Cached copy of answers

    // Constructor
    AnswersRecyclerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    // onCreateViewHolder
    @Override
    public AnswersRecyclerAdapter.AnswersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.quizz_recyclerview_item, parent, false);
        return new AnswersViewHolder(itemView);
    }

    // onBindViewHolder
    @Override
    public void onBindViewHolder(AnswersRecyclerAdapter.AnswersViewHolder holder, int position) {
        if (allAnswers != null) {
            String current = allAnswers.get(position);
            holder.answersItemView.setText(current); // Set answer text
        }
        else {
            // Covers the case of data not being ready yet.
            holder.answersItemView.setText("No answer");
        }
    }

    // setAnswers : set a new list of answers and refresh the view
    void setAnswers(List<String> answers){
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
