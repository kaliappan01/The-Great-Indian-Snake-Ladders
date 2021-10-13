package domain;

import java.io.Serializable;

public class Player implements Serializable, Comparable {
    private String name;
    private int stepNo;
    private int completed = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStepNo() {
        return stepNo;
    }

    public void setStepNo(int stepNo) {
        this.stepNo = stepNo;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    @Override
    public int compareTo(Object o) {
        Player fellowPlayer = (Player) o;
        if (stepNo < ((Player) o).stepNo) {
            return 1;
        } else {
            return -1;
        }
    }
}

