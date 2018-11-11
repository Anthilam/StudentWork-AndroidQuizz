package com.quizz.tguy.quizz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

// RoomRecyclerAdaptater : adapter for the RecyclerView in the main menu
public class EditQuizzRecyclerAdapter extends RecyclerView.Adapter<EditQuizzRecyclerAdapter.QuizzViewHolder> {

    // QuizzViewHolder : ViewHolder for each item of the RecyclerView
    class QuizzViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView quizzItemView;
        private int id;

        private QuizzViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this); // Add an onClick listener for every item of the RecyclerView

            quizzItemView = itemView.findViewById(R.id.quizzItemView);
        }

        // onClick
        @Override
        public void onClick(View v) {
            // Start the quizz selected by the user
            Context context = v.getContext();
            Intent intent_quizz = new Intent(context, EditQuizz.class);
            intent_quizz.putExtra("id", id); // Put the id of the quizz
            context.startActivity(intent_quizz);
        }
    }

    private final LayoutInflater mInflater;
    private List<RoomQuizz> allQuizz; // Cached copy of all quizzes

    // Constructor
    EditQuizzRecyclerAdapter(Context context) {
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
            holder.quizzItemView.setText(current.getStrQuizz_id()); // Set text with quizz id
            holder.id = allQuizz.get(position).getQuizz_id(); // Set the id for the extra in the intent
        } else {
            // Covers the case of data not being ready yet.
            holder.quizzItemView.setText("No ID");
        }
    }

    // setQuizzes : set a new list of quizzes and refresh the view
    void setQuizzes(List<RoomQuizz> quizzes){
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