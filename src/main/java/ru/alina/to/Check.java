package ru.alina.to;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import ru.alina.model.Summary;
import java.util.LinkedList;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Check {
    long count;
    int number;
    LinkedList<Summary> summaries;
    boolean exist;
    int rightAnswer;

    public Check() {
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

    public LinkedList<Summary> getSummaries() {
        return summaries;
    }

    public void setSummaries(LinkedList<Summary> summaries) {
        this.summaries = summaries;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public void remove() {
        summaries.removeFirst();
    }

    public Summary get() {
        return summaries.peekFirst();
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void incrementRightAnswer() {
        rightAnswer++;
    }

    public void reset() {
        this.exist=false;
        this.count=0;
        this.number=0;
        this.rightAnswer=0;
        this.summaries=null;
    }

    @Override
    public String toString() {
        return "Check{" +
                "count=" + count +
                ", number=" + number +
                '}';
    }
}
