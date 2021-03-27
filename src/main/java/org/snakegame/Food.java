package org.snakegame;

import javafx.scene.shape.Rectangle;

public class Food extends Rectangle {
    private int foodX;
    private int foodY;

    public Food(){
        super(10,10);

    }

    public void setFoodX(int x){
        foodX = x;
    }

    public void setFoodY(int y) {
        foodY = y;
    }

    public int getFoodY() {
        return foodY;
    }

    public int getFoodX() {
        return foodX;
    }


}