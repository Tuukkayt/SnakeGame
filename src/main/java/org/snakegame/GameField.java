package org.snakegame;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class GameField extends GridPane{


    private final Random rand;
    private int pixels;
    private ArrayList<SnakePart> snakeParts;
    private int moveX;
    private int moveY;
    private int positionX;
    private int positionY;
    private boolean gameOver;
    private Food apple;
    private Food goldenApple;



    public GameField(int pixels) {
        setStyle("-fx-background-color: black; -fx-grid-lines-visible: true; -fx-border-color: gray;" +
                "-fx-border-width: 5");
        gameOver=false;
        apple = new Food();
        apple.setFill(Color.RED);
        goldenApple = new Food();
        goldenApple.setFill(Color.YELLOW);
        rand = new Random();
        moveX=1;
        moveY=0;
        this.pixels = pixels;
        snakeParts=new ArrayList<>();
        positionX = this.pixels/2;
        positionY = this.pixels/2;
    }


    public void initGameField(){
        for(int row=0; row<this.pixels; row++){
            RowConstraints rowCon = new RowConstraints(10);
            getRowConstraints().add(rowCon);
        }
        for(int col=0; col<this.pixels; col++){
            ColumnConstraints colCon = new ColumnConstraints(10);
            getColumnConstraints().add(colCon);
        }

        placeFood(apple);
        initSnake(3);
    }

    public void initSnake(int len){
        for (int i=0; i<len; i++){
            if (i==0){
                snakeParts.add(new SnakePart(positionX,positionY));
                snakeParts.get(0).setFill(Color.GREENYELLOW);
                continue;
            }
            snakeParts.add(new SnakePart(positionX-i, positionY));
            add(snakeParts.get(i), snakeParts.get(i).getXPosition(), snakeParts.get(i).getYPosition());
        }
    }

    public void placeFood(Food f){

        start: while (true) {
            f.setFoodX(rand.nextInt(this.pixels));
            f.setFoodY(rand.nextInt(this.pixels));
            for (SnakePart snakePart : snakeParts) {
                if (snakePart.getYPosition() == f.getFoodY() && snakePart.getXPosition() == f.getFoodX()) {
                    continue start;
                }
            }
            add(f, f.getFoodX(), f.getFoodY());
            break;
        }
    }


    public void moveSnake(){
        checkFood();
        updateHead();
        checkBorderCollision();
        checkSelfDestruction();
        for(int i=1; i< snakeParts.size(); i++){
            getChildren().remove(snakeParts.get(i));
            add(snakeParts.get(i), snakeParts.get(i-1).getOldX(),snakeParts.get(i-1).getOldY());
            snakeParts.get(i).setPosition(snakeParts.get(i-1).getOldX(),snakeParts.get(i-1).getOldY());
        }
    }

    public void checkFood(){
        boolean foodEaten = false;
        if(positionX == apple.getFoodX() && positionY == apple.getFoodY()){
            foodEaten=true;
            growSnake(1);
        }else if(positionX == goldenApple.getFoodX() && positionY==goldenApple.getFoodY()){
            foodEaten = true;
            growSnake(2);
        }
        if(foodEaten){
            getChildren().remove(apple);
            getChildren().remove(goldenApple);
            if(rand.nextInt(5)==1){
                placeFood(goldenApple);
            }else{
                placeFood(apple);
            }
        }
    }

    public void growSnake(int len){
        for (int i = 1; i<=len; i++){
            int oldX = snakeParts.get(snakeParts.size()-1).getOldX();
            int oldY = snakeParts.get(snakeParts.size()-1).getOldY();

            snakeParts.add(new SnakePart(oldX, oldY));
            add(snakeParts.get(snakeParts.size()-1), oldX, oldY);
        }

    }

    public void updateHead(){
        getChildren().remove(snakeParts.get(0));
        positionX+=moveX;
        positionY+=moveY;
        add(snakeParts.get(0), positionX, positionY);
        snakeParts.get(0).setPosition(positionX, positionY);
    }



    public void checkBorderCollision(){
        try{
            if(moveY==-1 && positionY<0){
                killSnake();
            }else if(moveY==1 && positionY==this.pixels){
                killSnake();
            }else if(moveX == 1 && positionX==this.pixels){
                killSnake();
            }else if(moveX == -1 && positionX<0){
                killSnake();
            }
        }catch (Exception e){
            killSnake();
        }
    }

    public void checkSelfDestruction(){
        for(int i=1; i<snakeParts.size(); i++){
            if(positionX==snakeParts.get(i).getXPosition() && positionY==snakeParts.get(i).getYPosition()){
                killSnake();
            }
        }
    }

    public void killSnake(){
        setStyle("-fx-background-color: black; -fx-grid-lines-visible: true; -fx-border-color: red;" +
                "-fx-border-width: 5");
        this.gameOver=true;
    }

    public boolean getGameOver(){
        return this.gameOver;
    }

    public int getScore(){
        return snakeParts.size()-3;
    }

    public int getMoveX(){
        return moveX;
    }
    public int getMoveY() {
        return moveY;
    }
    public void setMoveX(int moveX) {
        this.moveX = moveX;
    }
    public void setMoveY(int moveY) {
        this.moveY = moveY;
    }
}

