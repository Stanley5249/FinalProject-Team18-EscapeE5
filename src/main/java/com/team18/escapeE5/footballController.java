package com.team18.escapeE5;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class footballController extends AnimationTimer {
    @FXML
    private Circle ball;

    private double radius;
    private double ballX;
    private double ballY;
    private double ballRotate;
    private double dx = 0;
    private double dy = 0;
    private double dr = 0;

    private static final int spf = (int) (1.0 / 60 * 1e9);
    private long animationStart;

    @Override
    public void handle(long now) {
        if (now - animationStart > spf) {
            animationStart = now;
            dx *= 0.99;
            dy *= 0.99;
            dx = (ballX - radius <= 0 && dx < 0) || (ballX + radius >= ball.getScene().getWidth() && dx > 0) ? -dx : dx;
            dy = (ballY - radius <= 0 && dy < 0) || (ballY + radius >= ball.getScene().getHeight() && dy > 0) ? 1 - dy : 1 + dy;
            if (ballY + radius > ball.getScene().getHeight() && dy > 0) {
                dx *= 0.99;
                dy = 0;
            }
            dr = dx * 0.5;
            moveBall();
        }
    }

    @FXML
    protected void clickToStart(MouseEvent mouseEvent) {
        start();
        System.out.println("start");
        onSoccerClick(mouseEvent);
        ball.setOnMouseClicked(this::onSoccerClick);
    }

    @FXML
    protected void onSoccerClick(MouseEvent mouseEvent) {
        dx = Math.sin(mouseEvent.getX() / radius) * -15;
        dy = (mouseEvent.getY() / radius + 9) * -2;
        System.out.printf("%.1f %.1f\n", dx, dy);
    }

    @FXML
    protected void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE) reset();
    }

    private void moveBall() {
        ballX += dx;
        ballY += dy;
        ballRotate += dr;
        ball.setLayoutX(ballX);
        ball.setLayoutY(ballY);
        ball.setRotate(ballRotate);
    }

    private void reset() {
        stop();
        ball.setOnMouseClicked(this::clickToStart);
        ballX = 150.0;
        ballY = 336.0;
        ballRotate = 0;
        dx = 0;
        dy = 0;
        dr = 0;
        ball.setLayoutX(ballX);
        ball.setLayoutY(ballY);
        ball.setRotate(ballRotate);
    }

    @FXML
    protected void initialize() {
        Image image = new Image("football.png");
        ball.setFill(new ImagePattern(image));
        radius = ball.getRadius();
        reset();
    }

}