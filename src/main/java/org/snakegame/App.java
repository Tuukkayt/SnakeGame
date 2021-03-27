package org.snakegame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class App extends Application {

    private GameField gameField;
    private Label scoreLabel;
    private Timeline gameLoop;
    private boolean gameOver;
    private Scene snakeGame;
    private VBox gameScreen;
    private Stage stage;

    @Override
    public void start(Stage s) {

        gameField = new GameField(30);
        gameField.initGameField();
        gameOver = gameField.getGameOver();
        scoreLabel=new Label();
        gameScreen = new VBox(scoreLabel, gameField);
        snakeGame = new Scene(gameScreen);
        stage=s;
        stage.setTitle("Snake Game");
        stage.setScene(snakeGame);
        stage.show();

        double FPS = 0.1;


        gameLoop = new Timeline(new KeyFrame(Duration.seconds(FPS), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                snakeGame.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if(gameField.getMoveX() == 0 && (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT)){
                            gameField.setMoveX(1);
                            gameField.setMoveY(0);
                        } else if(gameField.getMoveX() == 0 && (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT)){
                            gameField.setMoveX(-1);
                            gameField.setMoveY(0);
                        }else if(gameField.getMoveY() == 0 && (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP)){
                            gameField.setMoveX(0);
                            gameField.setMoveY(-1);
                        }else if(gameField.getMoveY() == 0 && (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN)){
                            gameField.setMoveX(0);
                            gameField.setMoveY(1);
                        }
                    }
                });

                try{
                    if(isGameOver()){
                        stopGame();
                    }else{
                        gameField.moveSnake();
                        updateScore();
                    }
                }catch (Exception e){
                    gameField.killSnake();
                    stopGame();
                }
            }
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();

    }

    private void stopGame(){
        gameLoop.stop();
        gameOverView();
    }

    private boolean isGameOver(){
        gameOver = gameField.getGameOver();
        return gameOver;
    }

    private void updateScore(){
        scoreLabel.setText("Your score: " + gameField.getScore());
    }

    public void restartGame(){
        gameField = new GameField(30);
        gameField.initGameField();
        gameOver = gameField.getGameOver();
        scoreLabel=new Label();
        gameScreen = new VBox(scoreLabel, gameField);
        snakeGame = new Scene(gameScreen);
        stage.setTitle("Snake Game");
        stage.setScene(snakeGame);
        stage.show();
        gameLoop.play();
    }

    public void gameOverView(){
        Stage gameOverStage = new Stage();
        BorderPane gameOverScreen = new BorderPane();
        gameOverScreen.setPrefHeight(200);
        gameOverScreen.setPrefWidth(200);
        Label gameOver = new Label("Game Over!\nFinal score: "+ gameField.getScore());
        gameOver.setTextFill(Color.BLACK);
        gameOver.setFont(Font.font("Serif", FontWeight.BOLD, 30));
        gameOverScreen.setCenter(gameOver);

        gameOverScreen.setPadding(new Insets(0, 0, 10, 0));

        Button restart = new Button("New Game");
        Button exitGame = new Button("Exit Game");
        HBox buttonBoxGameOver = new HBox(3.0);
        buttonBoxGameOver.getChildren().addAll(restart, exitGame);
        gameOverScreen.setBottom(buttonBoxGameOver);
        buttonBoxGameOver.setAlignment(Pos.BOTTOM_CENTER);

        gameOverStage.setTitle("Game Over!");
        gameOverStage.setScene(new Scene(gameOverScreen, 300, 300));
        gameOverStage.show();

        gameOverStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {
                Platform.exit();
                System.exit(0);
            }
        });


        exitGame.setOnAction((ActionEvent e) -> {
            Platform.exit();
            System.exit(0);
        });


        restart.setOnAction((ActionEvent e) -> {
            gameOverStage.close();
            restartGame();
        });
    }

    public static void main(String[] args) {
        launch();
    }

}