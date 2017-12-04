package movingballsfx;

import java.util.Random;
import javafx.scene.paint.Color;

public class Ball {

    private int xPos, yPos, speed;
    private int minX, maxX;
    private Color color;
    private int minCsX;
    private int maxCsX;
    private boolean inCs;

    public Ball(int minX, int maxX, int minCsX, int maxCsX, int yPos, Color color) {
        this.xPos = minX;
        this.yPos = yPos;
        this.minX = minX;
        this.maxX = maxX;
        this.minCsX = minCsX;
        this.maxCsX = maxCsX;
        this.speed = 50; //+ (new Random()).nextInt(5);
        this.color = color;
    }

    public void move() {
        xPos++;
        if (xPos > maxX) {
            xPos = minX;
        }
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public Color getColor() {
        return color;
    }

    public int getSpeed() {
        return speed;
    }

    public void setInCs(boolean inCs) {
        this.inCs = inCs;
    }

    public boolean getInCs() { return inCs; }

    public boolean isEnteringCs() {
        return xPos == minCsX;
    }
    
    public boolean isLeavingCs() {
        return xPos == maxCsX;
    }

}
