package com.quizz.tguy.quizz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

// MainMenuRecyclerAdapter : adapter for the RecyclerView in the MainMenu activity
public class MainMenuRecyclerAdapter extends RecyclerView.Adapter<MainMenuRecyclerAdapter.QuizzViewHolder> {

    // QuizzViewHolder : ViewHolder for each item of the RecyclerView
    class QuizzViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView quizzItemView;   // The item
        private int id;                         // The id of the quizz

        private QuizzViewHolder(View itemView) {
            super(itemView);

            // Add an onClick listener for each item of the RecyclerView
            itemView.setOnClickListener(this);
            quizzItemView = itemView.findViewById(R.id.quizzItemView);
        }

        // onClick
        @Override
        public void onClick(View v) {
            // Start the quizz selected by the user
            Context context = v.getContext();
            Intent intent_quizz = new Intent(context, Quizz.class);
            intent_quizz.putExtra("id", id); // Put the id of the quizz in the extras
            context.startActivity(intent_quizz);
        }
    }

    private final LayoutInflater mInflater;
    private List<RoomQuizz> allQuizz; // Cached copy of all quizzes

    // Constructor
    MainMenuRecyclerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    // onCreateViewHolder
    @Override
    public QuizzViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.mainmenu_recyclerview_item, parent, false);
        return new QuizzViewHolder(itemView);
    }

    // onBindViewHolder
    @Override
    public void onBindViewHolder(QuizzViewHolder holder, int position) {
        if (allQuizz != null) {
            RoomQuizz current = allQuizz.get(position);
            holder.quizzItemView.setText(current.getTitle());   // Set text with the title of the quizz
            holder.id = allQuizz.get(position).getQuizz_id();   // Set the id
        } else {
            // Covers the case of data not being ready yet.
            holder.quizzItemView.setText("No quizz");
        }
    }

    // setQuizzes : set a new list of quizzes and refresh the view
    protected void setQuizzes(List<RoomQuizz> quizzes){
        allQuizz = quizzes;
        notifyDataSetChanged();
    }

    // getItemCount
    @Override
    public int getItemCount() {
        if (allQuizz != null) {
            return allQuizz.size();
        }
        else {
            return 0;
        }
    }
}