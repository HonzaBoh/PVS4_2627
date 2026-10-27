package gui;

import javax.swing.*;
import java.awt.*;

public class MatrixPaint extends JFrame {

    private JPanel matrix;
    private JLabel[][] labels;
    private final int DIMENSION = 30;
    private JButton paintButton;

    public MatrixPaint() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(600, 600);

        paintButton = new JButton("Draw");
        matrix = new JPanel(new GridLayout(DIMENSION, DIMENSION, 5, 5));
        labels = new JLabel[DIMENSION][DIMENSION];

        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                labels[i][j] = new JLabel();
                labels[i][j].setOpaque(true);
                matrix.add(labels[i][j]);
            }
        }

        paintButton.addActionListener(e -> redraw());

        add(paintButton, BorderLayout.NORTH);
        add(matrix, BorderLayout.CENTER);
    }

    private void redraw() {
        try {
            paintButton.setText("Drawing...");
            paintButton.setEnabled(false);


            renderOnion(Color.BLACK);
            renderLinear(Color.WHITE);
            renderBackwards(Color.BLACK);
            renderCols(Color.WHITE);


            // paintButton.setText("Finished drawing");

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private void renderLinear(Color color) throws InterruptedException {
        // překreslit pozadí všech labelu na modrou
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                Thread.sleep(16); // 16.7ms = 60fps
                labels[i][j].setBackground(color);
                matrix.paintImmediately(0, 0, getWidth(), getHeight());
            }
        }
    }

    private void renderBackwards(Color color) throws InterruptedException {
        // překreslit pozadí všech labelu na modrou
        for (int i = DIMENSION - 1; i >= 0; i--) {
            for (int j = DIMENSION - 1; j >= 0; j--) {
                Thread.sleep(16); // 16.7ms = 60fps
                labels[i][j].setBackground(color);
                matrix.paintImmediately(0, 0, getWidth(), getHeight());
            }
        }
    }

    private void renderCols(Color color) throws InterruptedException {
        // překreslit pozadí všech labelu na modrou
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                Thread.sleep(16); // 16.7ms = 60fps
                labels[j][i].setBackground(color);
                matrix.paintImmediately(0, 0, getWidth(), getHeight());
            }
        }
    }

    private void renderOnion(Color color) throws InterruptedException {
        int top = 0, left = 0, bottom = DIMENSION - 1, right = DIMENSION - 1;

        while (top <= bottom && left <= right) {

            for (int col = left; col <= right; col++) {
                Thread.sleep(16); // 16.7ms = 60fps
                labels[top][col].setBackground(color);
                matrix.paintImmediately(0, 0, getWidth(), getHeight());
            }
            top++;

            for (int row = top; row <= bottom; row++) {
                Thread.sleep(16); // 16.7ms = 60fps
                labels[row][right].setBackground(color);
                matrix.paintImmediately(0, 0, getWidth(), getHeight());
            }
            right--;

            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    Thread.sleep(16); // 16.7ms = 60fps
                    labels[bottom][col].setBackground(color);
                    matrix.paintImmediately(0, 0, getWidth(), getHeight());
                }
                bottom--;
            }

            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    Thread.sleep(16); // 16.7ms = 60fps
                    labels[row][left].setBackground(color);
                    matrix.paintImmediately(0, 0, getWidth(), getHeight());
                }
                left++;
            }

        }


    }


    public static void main(String[] args) {
        new MatrixPaint().setVisible(true);
    }
}
