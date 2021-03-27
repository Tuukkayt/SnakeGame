package org.snakegame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SnakePart extends Rectangle {


    private int xPosition;
    private int yPosition;
    private int oldX;
    private int oldY;

    public SnakePart(int x, int y){
        super(10,10, Color.GREEN);
        setStyle("-fx-stroke-width: 1 ; -fx-stroke: black" );
        xPosition=x;
        oldX=x;
        yPosition=y;
        oldY=y;
    }

    public void setPosition(int x, int y){
        oldX = xPosition;
        oldY = yPosition;

        xPosition=x;
        yPosition=y;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public int getOldY() {
        return oldY;
    }

    public int getOldX() {
        return oldX;
    }
}
