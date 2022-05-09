package com.team18.escapeE5;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class SlidingPuzzleController {
    @FXML
    private GridPane gridPane;
    private int maxCol;
    private int maxRow;

    @FXML
    protected void initialize() {
        maxCol = gridPane.getColumnCount() - 1;
        maxRow = gridPane.getRowCount() - 1;
    }

    @FXML
    protected void move(Event event) {
        Button button = (Button) event.getTarget();
        System.out.println(button.getText());
        int col = GridPane.getColumnIndex(button);
        int row = GridPane.getRowIndex(button);
        if (canMoveUp(col, row)) System.out.println("Up");
        else if (canMoveDown(col, row)) System.out.println("Down");
        else if (canMoveLeft(col, row)) System.out.println("Left");
        else if (canMoveRight(col, row)) System.out.println("Right");
        else System.out.println("False");

    }

    private boolean canMoveUp(int col, int row) {
        if (row == 0) return false;
        row--;
        for (Node node : gridPane.getChildren())
            if (node instanceof Button)
                if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row)
                    return canMoveUp(col, row);
        return true;
    }

    private boolean canMoveDown(int col, int row) {
        if (row == maxRow) return false;
        row++;
        for (Node node : gridPane.getChildren())
            if (node instanceof Button)
                if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row)
                    return canMoveDown(col, row);
        return true;
    }

    private boolean canMoveLeft(int col, int row) {
        if (col == 0) return false;
        col--;
        for (Node node : gridPane.getChildren())
            if (node instanceof Button)
                if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row)
                    return canMoveLeft(col, row);
        return true;
    }

    private boolean canMoveRight(int col, int row) {
        if (col == maxCol) return false;
        col++;
        for (Node node : gridPane.getChildren())
            if (node instanceof Button)
                if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row)
                    return canMoveRight(col, row);
        return true;
    }
}