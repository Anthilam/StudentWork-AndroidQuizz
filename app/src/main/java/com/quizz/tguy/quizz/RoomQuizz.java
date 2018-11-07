package com.quizz.tguy.quizz;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

// RoomQuizz : database structure for a Quizz

@Entity(tableName = "quizz_table")
public class RoomQuizz {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "quizz_id")
    private int quizz_id;

    @TypeConverters(RoomTypeConverters.class)
    private List<String> questions_list;

    @TypeConverters(RoomTypeConverters.class)
    private List<List<String>> answers_list;

    public RoomQuizz() { }

    @Ignore
    public RoomQuizz(int index) {
        this.quizz_id = index;
        questions_list = new ArrayList<>();
        answers_list = new ArrayList<>();
        questions_list.add("Yes or No ?");
        List<String> l = new ArrayList<>();
        l.add("Yes!");
        l.add("No..");
        answers_list.add(l);
    }

    public int getQuizz_id() {
        return quizz_id;
    }

    public String getStrQuizz_id() {
        return String.valueOf(quizz_id);
    }

    public List<String> getQuestions_list() {
        return questions_list;
    }

    public List<List<String>> getAnswers_list() {
        return answers_list;
    }

    public String getQuestions(int index) {
        return questions_list.get(index);
    }

    public List<String> getAnswers(int index) {
        return answers_list.get(index);
    }

    public void setQuizz_id(int quizz_id) {
        this.quizz_id = quizz_id;
    }

    public void setAnswers_list(List<List<String>> answers_list) {
        this.answers_list = answers_list;
    }

    public void setQuestions_list(List<String> questions_list) {
        this.questions_list = questions_list;
    }

    public void addQuestionWithAnswers(String question, List<String> answers){
        this.questions_list.add(question);
        this.answers_list.add(answers);
    }
}