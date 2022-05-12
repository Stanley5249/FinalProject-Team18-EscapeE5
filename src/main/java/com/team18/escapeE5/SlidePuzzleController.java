package com.team18.escapeE5;

import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.util.Random;

public class SlidePuzzleController {
    @FXML
    private GridPane gridPane;
    @FXML
    private Button bt16;

    private boolean running;
    private int colSize;
    private int rowSize;
    private int gridSize;
    private int empty;
    private int click;
    private int timer;
    public AnimationTimer animationTimer = new AnimationTimer() {
        private long temp;
        @Override
        public void handle(long now) {
            if (now - temp > 1000000000L) {
                timer++;
                temp = now;
            }
        }
    };

    @FXML
    protected void initialize() {
        System.out.println("Slide Puzzle");
        colSize = gridPane.getColumnCount();
        rowSize = gridPane.getRowCount();
        gridSize = gridPane.getChildren().size() - 1;
        GridPane.setColumnIndex(bt16, colSize - 1);
        GridPane.setRowIndex(bt16, rowSize - 1);
        reset();
    }

    @FXML
    protected void onButtonClick(Event event) {
        if (running) {
            Button button = (Button) event.getTarget();
            System.out.printf("%-3s", button.getText());
            if (moveTo(button, 0, -1)) {
                click++;
                System.out.println("Up");
            }
            else if (moveTo(button, 0, 1)) {
                click++;
                System.out.println("Down");
            }
            else if (moveTo(button, -1, 0)) {
                click++;
                System.out.println("Left");
            }
            else if (moveTo(button, 1, 0)) {
                click++;
                System.out.println("Right");
            }
            else System.out.println("Invalid");
            puzzleCompletedTest();
        }
    }

    @FXML
    protected void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE) reset();
        else if (keyEvent.getCode() == KeyCode.T) System.out.println(timer());
        else {
            int col = empty % colSize;
            int row = empty / colSize;
            if (keyEvent.getCode() == KeyCode.W || keyEvent.getCode() == KeyCode.UP) {
                Node node = getNodeFromGrid(col, row + 1);
                if (node != null && moveTo(node, 0, -1)) {
                    click++;
                    System.out.printf("%-3sUp\n", ((Button) node).getText());
                }
                else System.out.println("X  Invalid");
            }
            else if (keyEvent.getCode() == KeyCode.S || keyEvent.getCode() == KeyCode.DOWN) {
                Node node = getNodeFromGrid(col, row - 1);
                if (node != null && moveTo(node, 0, 1)) {
                    click++;
                    System.out.printf("%-3sDown\n", ((Button) node).getText());
                }
                else System.out.println("X  Invalid");
            }
            else if (keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.LEFT) {
                Node node = getNodeFromGrid(col + 1, row);
                if (node != null && moveTo(node, -1, 0)) {
                    click++;
                    System.out.printf("%-3sLeft\n", ((Button) node).getText());
                }
                else System.out.println("X  Invalid");
            }
            else if (keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT) {
                Node node = getNodeFromGrid(col - 1, row);
                if (node != null && moveTo(node, 1, 0)) {
                    click++;
                    System.out.printf("%-3sRight\n", ((Button) node).getText());
                }
                else System.out.println("X  Invalid");
            }
        }
    }

    private boolean moveTo(Node target, int dx, int dy) {
        int col = GridPane.getColumnIndex(target);
        int row = GridPane.getRowIndex(target);

        if (dx < 0 && col == 0) return false;
        if (dy < 0 && row == 0) return false;
        if (dx > 0 && col == colSize - 1) return false;
        if (dy > 0 && row == rowSize - 1) return false;

        boolean canMove;
        if (col + dx == empty % colSize && row + dy == empty / colSize) canMove = true;
        else {
            Node node = getNodeFromGrid(col + dx, row + dy);
            if (node == null) canMove = true;
            else canMove = moveTo(node, dx, dy);
        }

        if (canMove) {
            GridPane.setColumnIndex(target, col + dx);
            GridPane.setRowIndex(target, row + dy);
            empty = col + row * colSize;
        }
        return canMove;
    }

    private Node getNodeFromGrid(int col, int row) {
        if (col < 0 || col >= colSize || row < 0 || row >= rowSize) return null;
        for (Node node : gridPane.getChildren().subList(0, gridSize - 1)) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    private void puzzleCompletedTest() {
        int col = 0, row = 0;
        for (Node node : gridPane.getChildren().subList(0, gridSize - 1)) {
            if (GridPane.getColumnIndex(node) != col || GridPane.getRowIndex(node) != row) return;
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        animationTimer.stop();
        running = false;
        bt16.setVisible(true);
        System.out.println("Congratulations!");
        System.out.println("--------Statistics--------");
        System.out.printf("The number of clicks: %s\n", click);
        System.out.printf("Time spent: %s\n", timer());
    }

    private void showPuzzle(int[] puzzle) {
        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] == -1) System.out.print("X   ");
            else System.out.printf("%-4s", puzzle[i] + 1);
            if ((i + 1) % colSize == 0) System.out.println();
        }
    }

    private String timer() {
        return String.format("%02dm%02ds", timer / 60, timer % 60);
    }

    @FXML
    private void reset() {
        System.out.println("--------Initialize--------");

        // Reset variables
        running = true;
        bt16.setVisible(false);
        click = 0;
        timer = 0;

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
        System.out.println("Random puzzle:");
        showPuzzle(puzzle);

        // Count number of inversion and find empty grid
        int inversion = 0;
        for (int i = 0; i < gridSize; i++) {
            if (puzzle[i] != -1) {
                for (int j = i + 1; j < gridSize; j++) {
                    if (puzzle[j] != -1 && puzzle[j] < puzzle[i]) inversion++;
                }
            }
            else empty = i;
        }
        System.out.printf("Inversion: %d, Empty: %s\n", inversion, empty + 1);

        // Determine whether it is solvable
        boolean isSolvable;
        if (colSize % 2 == 0 && (rowSize - empty / colSize) % 2 != 0) isSolvable = inversion % 2 == 0;
        else isSolvable = inversion % 2 != 0;
        System.out.printf("Solvable: %s\n", isSolvable);

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
            System.out.println("----------Convert---------");
            System.out.println("New puzzle:");
            showPuzzle(puzzle);
        }

        // Arrange the puzzle.
        for (int i = 0; i < gridSize; i++) {
            if (puzzle[i] == -1) continue;
            Node node = gridPane.getChildren().get(puzzle[i]);
            GridPane.setColumnIndex(node, i % colSize);
            GridPane.setRowIndex(node, i / colSize);
        }
        System.out.println("----------Finish----------");

        animationTimer.start();
    }
}