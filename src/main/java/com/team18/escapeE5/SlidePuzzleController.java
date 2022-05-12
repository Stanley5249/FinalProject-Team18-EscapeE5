package com.team18.escapeE5;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.Random;

public class SlidePuzzleController {
    @FXML
    private GridPane gridPane;
    @FXML
    private Button lastButton;

    private boolean running;
    private int colSize;
    private int rowSize;
    private int gridSize;
    private int click;

    @FXML
    protected void slideTiles(Event event) {
        if (running) {
            Button button = (Button) event.getTarget();
            System.out.printf("%-3s", button.getText());
            click++;
            if (slideTo(button, 0, -1)) {
                click++;
                System.out.println("Up");
            }
            else if (slideTo(button, 0, 1)) {
                click++;
                System.out.println("Down");
            }
            else if (slideTo(button, -1, 0)) {
                click++;
                System.out.println("Left");
            }
            else if (slideTo(button, 1, 0)) {
                click++;
                System.out.println("Right");
            }
            else System.out.println("Can't move");

            if (isComplete()) {
                lastButton.setVisible(true);
                System.out.println("Congratulations!");
                System.out.printf("The number of clicks: %s\n", click);
                running = false;
            }
        }
        else System.out.println("Already completed");
    }

    private boolean slideTo(Node target, int dx, int dy) {
        int col = GridPane.getColumnIndex(target);
        int row = GridPane.getRowIndex(target);
        if (dx < 0 && col == 0) return false;
        if (dy < 0 && row == 0) return false;
        if (dx > 0 && col == colSize - 1) return false;
        if (dy > 0 && row == rowSize - 1) return false;
        col += dx;
        row += dy;
        boolean canMove = true;
        for (Node node : gridPane.getChildren().subList(0, gridSize - 1))
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                canMove = slideTo(node, dx, dy);
                break;
            }
        if (canMove) {
            GridPane.setColumnIndex(target, col);
            GridPane.setRowIndex(target, row);
        }
        return canMove;
    }

    private boolean isComplete() {
        int col = 0, row = 0;
        int colSize = gridPane.getColumnCount();
        for (Node node : gridPane.getChildren().subList(0, gridSize - 1)) {
            if (GridPane.getColumnIndex(node) != col || GridPane.getRowIndex(node) != row)
                return false;
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        return true;
    }

    @FXML
    protected void initialize() {
        running = true;
        colSize = gridPane.getColumnCount();
        rowSize = gridPane.getRowCount();
        gridSize = gridPane.getChildren().size() - 1;
        lastButton.setVisible(false);
        click = 0;

        System.out.println("-------------Initialize--------------");

        // Generate sorted array
        int[] puzzle = new int[gridSize];
        for (int i = 0; i < gridSize - 1; i++) puzzle[i] = i;
        puzzle[gridSize - 1] = -1;

        // Random shuffle
        Random rand = new Random();
        for (int i = gridSize - 1; i > 0; i--) {
            int j = rand.nextInt(gridSize);
            int temp = puzzle[i];
            puzzle[i] = puzzle[j];
            puzzle[j] = temp;
        }
        System.out.println("Random shuffle:");
        showArray(puzzle);

        // Count number of inversion and find empty grid
        int inversion = 0, empty = 0;
        for (int i = 0; i < gridSize; i++) {
            if (puzzle[i] != -1) {
                for (int j = i + 1; j < gridSize; j++) {
                    if (puzzle[j] != -1 && puzzle[j] < puzzle[i]) inversion++;
                }
            }
            else empty = i;
        }
        System.out.printf("inversion: %d, empty: %s\n", inversion, empty);

        // Determine whether it is solvable
        boolean isSolvable;
        if (colSize % 2 == 0 && (rowSize - empty / colSize) % 2 != 0) isSolvable = inversion % 2 == 0;
        else isSolvable = inversion % 2 != 0;
        System.out.printf("Is solvable: %s\n", isSolvable);

        // Convert to solvable
        if (!isSolvable) {
            if (puzzle[0] != -1 && puzzle[1] != -1) {
                int temp = puzzle[0];
                puzzle[0] = puzzle[1];
                puzzle[1] = temp;
            }
            else {
                int temp = puzzle[puzzle.length - 1];
                puzzle[puzzle.length - 1] = puzzle[puzzle.length - 2];
                puzzle[puzzle.length - 2] = temp;
            }
            System.out.println("Convert to solvable:");
            showArray(puzzle);
        }

        // Arrange the puzzle.
        for (int i = 0; i < gridSize; i++) {
            if (puzzle[i] == -1) continue;
            Node node = gridPane.getChildren().get(puzzle[i]);
            GridPane.setColumnIndex(node, i % colSize);
            GridPane.setRowIndex(node, i / colSize);
        }
        System.out.println("---------------Finish----------------");
    }

    private void showArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.printf("%-4s", array[i] + 1);
            if ((i + 1) % colSize == 0) System.out.println();
        }
    }
}