package com.quizz.tguy.quizz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

// SettingsRecyclerAdapter : adapter for the RecyclerView in the Settings activity
public class SettingsRecyclerAdapter extends RecyclerView.Adapter<SettingsRecyclerAdapter.QuizzViewHolder> {

    // QuizzViewHolder : ViewHolder for each item of the RecyclerView
    class QuizzViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView quizzItemView;   // The item
        private int id;                         // The id of the item
        private int listpos;                    // The position of the item in the list

        private QuizzViewHolder(View itemView) {
            super(itemView);

            // Get the TextView
            quizzItemView = itemView.findViewById(R.id.quizzItemView);

            // Add an onClick listener for each item of the RecyclerView
            itemView.setOnClickListener(this);

            // Set the delQuizz button behaviour
            itemView.findViewById(R.id.btn_delQuizz).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Settings)mContext).delQuizz(listpos);
                }
            });
        }

        // onClick
        @Override
        public void onClick(View v) {
            // Start the EditQuizz activity when the user touch a quizz
            Context context = v.getContext();
            Intent intent_quizz = new Intent(context, EditQuizz.class);
            intent_quizz.putExtra("id", id); // Put the id of the quizz in the extras
            context.startActivity(intent_quizz);
        }
    }

    private final LayoutInflater mInflater;
    private List<RoomQuizz> allQuizz; // Cached copy of all quizzes
    private Context mContext;

    // Constructor
    SettingsRecyclerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    // onCreateViewHolder
    @Override
    public QuizzViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.settings_recyclerview_item, parent, false);
        return new QuizzViewHolder(itemView);
    }

    // onBindViewHolder
    @Override
    public void onBindViewHolder(QuizzViewHolder holder, int position) {
        if (allQuizz != null) {
            RoomQuizz current = allQuizz.get(position);
            holder.quizzItemView.setText(current.getTitle());   // Set text with quizz id
            holder.id = current.getQuizz_id();                  // Set the id
            holder.listpos = position;                          // Set the position
        } else {
            // Covers the case of data not being ready yet.
            holder.quizzItemView.setText("No quizz");
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