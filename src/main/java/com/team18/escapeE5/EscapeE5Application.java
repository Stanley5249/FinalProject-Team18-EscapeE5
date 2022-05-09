package com.team18.escapeE5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EscapeE5Application extends Application {
    public static Stage stage;
    public static Scene menuScene;
    public static Scene mainGameScene;
    public static Scene miniGameScene;
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader1 = new FXMLLoader(EscapeE5Application.class.getResource("menu.fxml"));
        FXMLLoader fxmlLoader2 = new FXMLLoader(EscapeE5Application.class.getResource("mainGame.fxml"));
        FXMLLoader fxmlLoader3 = new FXMLLoader(EscapeE5Application.class.getResource("slidingPuzzle.fxml"));
        menuScene = new Scene(fxmlLoader1.load());
        mainGameScene = new Scene(fxmlLoader2.load());
        miniGameScene = new Scene(fxmlLoader3.load());
        stage = primaryStage;
        stage.setTitle("逃離工五之施北北叫我寫作業");
        stage.setScene(menuScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}