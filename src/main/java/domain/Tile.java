package domain;

import java.io.Serializable;

public class Tile implements Serializable {
    private int snake = 0;
    private int ladder = 0;
    private int stepNo;

    public int getSnake() {
        return snake;
    }

    public void setSnake(int snake) {
        this.snake = snake;
    }

    public int getLadder() {
        return ladder;
    }

    public void setLadder(int ladder) {
        this.ladder = ladder;
    }

    public int getStepNo() {
        return stepNo;
    }

    public void setStepNo(int stepNo) {
        this.stepNo = stepNo;
    }
}
