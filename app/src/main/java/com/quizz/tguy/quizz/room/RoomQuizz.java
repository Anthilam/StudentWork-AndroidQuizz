package com.quizz.tguy.quizz.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

// RoomQuizz : database structure for a quizz
@Entity(tableName = "quizz_table")
public class RoomQuizz {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "quizz_id")
    private int quizz_id;   // Primary key : id of the quizz

    @ColumnInfo(name = "title")
    private String title;   // Title of the quizz

    @ColumnInfo(name = "questions")
    @TypeConverters(RoomTypeConverters.class)
    private List<String> questions_list;    // The list of questions of the quizz

    @ColumnInfo(name = "answers")
    @TypeConverters(RoomTypeConverters.class)
    private List<List<String>> answers_list;    // The list of answers for each questions of the quizz

    @ColumnInfo(name = "good_answer")
    @TypeConverters(RoomTypeConverters.class)
    private List<Integer> goodAnswers_list; // The list of good answers for each questions of the quizz

    // Constructor
    public RoomQuizz() {
        questions_list = new ArrayList<>();
        answers_list = new ArrayList<>();
        goodAnswers_list = new ArrayList<>();
        setTitle("Quizz par défaut");
        addDefaultQuestion();
    }

    // Constructor
    @Ignore
    public RoomQuizz(String title) {
        questions_list = new ArrayList<>();
        answers_list = new ArrayList<>();
        goodAnswers_list = new ArrayList<>();
        setTitle(title);
    }

    // getQuizz_id : get the id of the quizz
    public int getQuizz_id() {
        return quizz_id;
    }

    // getQuestions_list : get the list of questions of the quizz
    public List<String> getQuestions_list() {
        return questions_list;
    }

    // getAnswers_list : get the list of answers of the quizz
    public List<List<String>> getAnswers_list() {
        return answers_list;
    }

    // getQuestion : get a single question in the list of questions
    public String getQuestion(int index) {
        return questions_list.get(index);
    }

    // getAnswers : get a set of answers for a specific question in the list of answers
    public List<String> getAnswers(int index) {
        return answers_list.get(index);
    }

    // setQuizz_id : set the id of the quizz
    public void setQuizz_id(int quizz_id) {
        this.quizz_id = quizz_id;
    }

    // setAnswers_list : set the list of answers of the quizz
    public void setAnswers_list(List<List<String>> answers_list) {
        this.answers_list = answers_list;
    }

    // setAnswerInAListOfAnswer : set a specific answer in the list of answers
    public void setAnswerInAListOfAnswer(String answer, int index, int pos) {
        this.answers_list.get(index).set(pos, answer);
    }

    // setQuestions_list : set the list of questions of the quizz
    public void setQuestions_list(List<String> questions_list) {
        this.questions_list = questions_list;
    }

    // setQuestionInAListOfQuestions : set a specific question in the list of questions
    public void setQuestionInAListOfQuestions(String question, int index) {
        this.questions_list.set(index, question);
    }

    // addQuestionWithAnswers : add a question with its answers and good answer
    public void addQuestionWithAnswers(String question, List<String> answers, int goodanswer){
        this.questions_list.add(question);
        this.answers_list.add(answers);
        this.goodAnswers_list.add(goodanswer);
    }

    // addDefaultQuestion : add a default question in the quizz
    public void addDefaultQuestion() {
        List<String> l = new ArrayList<>();
        l.add("Oui");
        l.add("Non");
        addQuestionWithAnswers("Oui ou Non ?", l, 0);
    }

    // getGoodAnswers_list : get the list of good answers of the quizz
    public List<Integer> getGoodAnswers_list() {
        return goodAnswers_list;
    }

    // setGoodAnswers_list : set the list of good answers of the quizz
    public void setGoodAnswers_list(List<Integer> good_answer_list) {
        this.goodAnswers_list = good_answer_list;
    }

    // getGoodAnswer : get a single good answer in the good answers list
    public int getGoodAnswer(int index) {
        return goodAnswers_list.get(index);
    }

    // addAnswerToList : add an answer to the list of answers
    public void addAnswerToList(String answer, int list) {
        this.answers_list.get(list).add(answer);
    }

    // delAnswerFromList : delete an answer from the list of answers
    public void delAnswerFromList(int answer, int list) {
        this.answers_list.get(list).remove(answer);
    }

    // delQuestion : delete a question from the list of questions
    public void delQuestion(int question) {
        this.questions_list.remove(question);       // Delete the question
        this.answers_list.remove(question);         // Delete the answers list
        this.goodAnswers_list.remove(question);     // Delete the good answers list
    }

    // setGoodAnswer : set a good answer in the list of good answers
    public void setGoodAnswer(int index, int goodAnswer) {
        this.goodAnswers_list.set(index, goodAnswer);
    }

    // getTitle : get the title of the quizz
    public String getTitle() {
        return this.title;
    }

    // setTitle : set the title of the quizz
    public void setTitle(String title) {
        this.title = title;
    }

    // getAnswersCount : get the numbers of answers of a question
    public int getAnswersCount(int index) {
        return this.answers_list.get(index).size();
    }
}