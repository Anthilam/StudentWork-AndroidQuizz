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

    @ColumnInfo(name = "questions")
    @TypeConverters(RoomTypeConverters.class)
    private List<String> questions_list;

    @ColumnInfo(name = "answers")
    @TypeConverters(RoomTypeConverters.class)
    private List<List<String>> answers_list;

    @ColumnInfo(name = "right_answer")
    @TypeConverters(RoomTypeConverters.class)
    private List<Integer> good_answer_list;

    public RoomQuizz() { }

    @Ignore
    public RoomQuizz(int index) {
        quizz_id = index;
        questions_list = new ArrayList<>();
        answers_list = new ArrayList<>();
        good_answer_list = new ArrayList<>();
        addDefaultQuestion();
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

    public void setAnswerInAListOfAnswer(String answer, int index, int pos) {
        this.answers_list.get(index).set(pos, answer);
    }

    public void setQuestions_list(List<String> questions_list) {
        this.questions_list = questions_list;
    }

    public void setQuestionInAListOfQuestions(String question, int index) {
        this.questions_list.set(index, question);
    }

    public void addQuestionWithAnswers(String question, List<String> answers, int rightAnswer){
        this.questions_list.add(question);
        this.answers_list.add(answers);
        this.good_answer_list.add(rightAnswer);
    }

    public void addDefaultQuestion() {
        List<String> l = new ArrayList<>();
        l.add("Oui");
        l.add("Non");
        addQuestionWithAnswers("Oui ou Non ?", l, 0);
    }

    public List<Integer> getGood_answer_list() {
        return good_answer_list;
    }

    public void setGood_answer_list(List<Integer> good_answer_list) {
        this.good_answer_list = good_answer_list;
    }

    public int getGood_answer(int index) {
        return good_answer_list.get(index);
    }

    public void addAnswerToList(String answer, int list) {
        this.answers_list.get(list).add(answer);
    }

    public void delAnswerFromList(int answer, int list) {
        this.answers_list.get(list).remove(answer);
    }

    public void delQuestion(int question) {
        this.questions_list.remove(question);
        this.answers_list.remove(question);
        this.good_answer_list.remove(question);
    }

    public void setGood_answer(int index, int goodAnswer) {
        this.good_answer_list.set(index, goodAnswer);
    }
}