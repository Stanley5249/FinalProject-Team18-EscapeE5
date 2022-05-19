package com.team18.escapeE5;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Random;

public class footballController extends AnimationTimer {
    @FXML
    private Circle ball;

    private double radius;
    private double ballX;
    private double ballY;
    private double ballRotate;
    private double dx = 0;
    private double dy = 0;

    @FXML
    private Label score;

    private int click;

    @Override
    public void handle(long now) {
        if ((ballX - radius <= 0 && dx < 0) || (ballX + radius >= ball.getScene().getWidth() && dx > 0)) dx *= -0.8;
        else dx *= 0.999;
        if (ballY + radius >= ball.getScene().getHeight() && dy > 0) {
            dy *= -0.8;
            ball.setOnMouseClicked(null);
            ball.getParent().setOnKeyPressed(this::waitReset);
        }
        else if (ballY - radius <= 0 && dy < 0) dy *= -0.8;
        else dy *= 0.999;
        if (ballY + radius < ball.getScene().getHeight()) {
            dy += 0.4;
        }
        else dx *= 0.99;
        ballX += dx;
        ballY += dy;
        ballRotate += dx * 0.45;
        ball.setLayoutX(ballX);
        ball.setLayoutY(ballY);
        ball.setRotate(ballRotate);
        score.setText(String.valueOf(click));
    }

    private void clickToStart(MouseEvent mouseEvent) {
        start();
        onMouseClick(mouseEvent);
        ball.setOnMouseClicked(this::onMouseClick);
    }

    private void onMouseClick(MouseEvent mouseEvent) {
        double x = -Math.sin(mouseEvent.getX() / radius / 2 * Math.PI) * 6;
        double y = -(mouseEvent.getY() / radius + 10);
        setBallMomentum(x, y);
    }

    private void pressToStart(KeyEvent keyEvent) {
        start();
        onKeyPressed(keyEvent);
        ball.setOnKeyPressed(this::onKeyPressed);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case W -> {
                Random random = new Random();
                double x = Math.sin(random.nextDouble(-0.1, 0.1) * Math.PI) * 6;
                double y = random.nextDouble(-11, -10);
                setBallMomentum(x, y);
            }
            case S -> {
                Random random = new Random();
                double x = Math.sin(random.nextDouble(-0.2, 0.2) * Math.PI) * 6;
                double y = random.nextDouble(-10, -9);
                setBallMomentum(x, y);
            }
            case A -> {
                Random random = new Random();
                double x = Math.sin(random.nextDouble(-0.5, -0.2) * Math.PI) * 6;
                double y = random.nextDouble(-10.5, -9.5);
                setBallMomentum(x, y);
            }
            case D -> {
                Random random = new Random();
                double x = Math.sin(random.nextDouble(0.2, 0.5) * Math.PI) * 6;
                double y = random.nextDouble(-10.5, -9.5);
                setBallMomentum(x, y);
            }
            case SPACE -> reset();
        }
    }

    private void setBallMomentum(double x, double y) {
        dx = x;
        dy = y;
        click ++;
        System.out.printf("clicks: %d, (%4.1f, %4.1f)\n", click, dx, dy);
    }

    private void waitReset(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE) reset();
    }

    private void reset() {
        stop();
        ball.setOnMouseClicked(this::clickToStart);
        ball.getParent().setOnKeyPressed(this::pressToStart);
        ballX = 150.0;
        ballY = 336.0;
        ballRotate = 0;
        dx = 0;
        dy = 0;
        ball.setLayoutX(ballX);
        ball.setLayoutY(ballY);
        ball.setRotate(ballRotate);
        click = 0;
        score.setText(String.valueOf(click));
        System.out.println("Reset");
    }

    @FXML
    protected void initialize() {
        Image image = new Image("football.png");
        ball.setFill(new ImagePattern(image));
        radius = ball.getRadius();
        reset();
    }

}