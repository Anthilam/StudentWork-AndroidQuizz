package com.quizz.tguy.quizz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

// SettingsRecyclerAdapter : adapter for the RecyclerView in the main menu
public class EditQuizzRecyclerAdapter extends RecyclerView.Adapter<EditQuizzRecyclerAdapter.QuestionsViewHolder> {

    // QuestionsViewHolder : ViewHolder for each item of the RecyclerView
    class QuestionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView quizzItemView;
        private int id;

        private QuestionsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this); // Add an onClick listener for every item of the RecyclerView
            quizzItemView = itemView.findViewById(R.id.quizzItemView);

            itemView.findViewById(R.id.btn_delQ).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((EditQuizz)mContext).delQuestion(id);
                }
            });
        }

        // onClick
        @Override
        public void onClick(View v) {
            // Start the quizz selected by the user
            Context context = v.getContext();
            Intent intent_quizz = new Intent(context, EditQuestion.class);
            intent_quizz.putExtra("qid", qid); // Put the id of the quizz
            intent_quizz.putExtra("id", id);
            context.startActivity(intent_quizz);
        }
    }

    private final LayoutInflater mInflater;
    private List<String> questions; // Cached copy of all quizzes
    private Context mContext;
    private int qid;

    // Constructor
    EditQuizzRecyclerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    // onCreateViewHolder
    @Override
    public QuestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.editquizz_recyclerview_item, parent, false);
        return new QuestionsViewHolder(itemView);
    }

    // onBindViewHolder
    @Override
    public void onBindViewHolder(QuestionsViewHolder holder, int position) {
        if (questions != null) {
            String current = questions.get(position);
            holder.quizzItemView.setText("Question "+ (position+1) + " : " + current); // Set text with quizz id
            holder.id = position;
        } else {
            // Covers the case of data not being ready yet.
            holder.quizzItemView.setText("No ID");
        }
    }

    // setQuizzes : set a new list of quizzes and refresh the view
    void setQuestions(List<String> questionsList){
        questions = questionsList;
        notifyDataSetChanged();
    }

    void setQuizzId(int qid) {
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