package com.quizz.tguy.quizz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RoomRecyclerAdapter extends RecyclerView.Adapter<RoomRecyclerAdapter.QuizzViewHolder> {

    class QuizzViewHolder extends RecyclerView.ViewHolder {
        private final TextView quizzItemView;

        private QuizzViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    // Passer l'id du quizz dans l'intent
                    Intent intent_quizz = new Intent(context, Quizz.class);
                    context.startActivity(intent_quizz);
                }
            });

            quizzItemView = itemView.findViewById(R.id.quizzItemView);
        }
    }

    private final LayoutInflater mInflater;
    private List<RoomQuizz> allQuizz; // Cached copy of words

    RoomRecyclerAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public QuizzViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new QuizzViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuizzViewHolder holder, int position) {
        if (allQuizz != null) {
            RoomQuizz current = allQuizz.get(position);
            holder.quizzItemView.setText(current.getStrQuizz_id());
        } else {
            // Covers the case of data not being ready yet.
            holder.quizzItemView.setText("No ID");
        }
    }

    void setQuizzes(List<RoomQuizz> quizzes){
        allQuizz = quizzes;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (allQuizz != null)
            return allQuizz.size();
        else return 0;
    }
}