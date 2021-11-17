package ru.alina.to;

import ru.alina.model.Summary;

import java.util.Queue;

public class Review {

    private Queue<Summary> summaries;
    private String topicName;

    private int number;
    private long count;
    private int rightAnswer;
    private int rightAnswerChecked;
    private long countChecked;
    private int rightAnswerUnchecked;
    private long countUnchecked;


    public Review() {
    }

    public Review(Queue<Summary> summaries, String topicName, long countUnchecked, long countChecked) {
        this.summaries = summaries;
        this.count = summaries.size();
        this.countUnchecked = countUnchecked;
        this.countChecked = countChecked;
        this.number=1;
        this.topicName = topicName;
        this.rightAnswer = 0;
        this.rightAnswerChecked = 0;
        this.rightAnswerUnchecked = 0;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSummaries(Queue<Summary> summaries) {
        this.summaries = summaries;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getRightAnswerChecked() {
        return rightAnswerChecked;
    }

    public int getRightAnswerUnchecked() {
        return rightAnswerUnchecked;
    }

    public long getCountChecked() {
        return countChecked;
    }

    public void setCountChecked(long countChecked) {
        this.countChecked = countChecked;
    }

    public long getCountUnchecked() {
        return countUnchecked;
    }

    public void setCountUnchecked(long countUnchecked) {
        this.countUnchecked = countUnchecked;
    }

    public Summary get() {
        return summaries.peek();
    }

    public void remove() {
        summaries.remove();
    }

    public Summary poll() {
        return summaries.poll();
    }

    public void incrementRightAnswerChecked() {
        this.rightAnswerChecked++;
    }

    public void incrementRightAnswerUnchecked() {
        this.rightAnswerUnchecked++;
    }

    public void incrementNumber() { this.number++; }

    public boolean isEmpty() {
        return summaries.size()<=0;
    }

    public long getCountRightAnswer() {
        return rightAnswerChecked+rightAnswerUnchecked;
    }

    @Override
    public String toString() {
        return "Review{" +
                "summaries=" + summaries +
                ", topicName='" + topicName + '\'' +
                ", number=" + number +
                ", count=" + count +
                ", rightAnswer=" + rightAnswer +
                ", rightAnswerChecked=" + rightAnswerChecked +
                ", countChecked=" + countChecked +
                ", rightAnswerUnchecked=" + rightAnswerUnchecked +
                ", countUnchecked=" + countUnchecked +
                '}';
    }
}
