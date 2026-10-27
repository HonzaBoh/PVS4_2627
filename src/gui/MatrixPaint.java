package gui;

import javax.swing.*;
import java.awt.*;

public class MatrixPaint extends JFrame {

    private JPanel matrix;
    private JLabel[][] labels;
    private final int DIMENSION = 20;
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

            // překreslit pozadí všech labelu na modrou
            for (int i = 0; i < DIMENSION; i++) {
                for (int j = 0; j < DIMENSION; j++) {
                    Thread.sleep(16); // 16.7ms = 60fps
                    labels[i][j].setBackground(Color.BLUE);
                    matrix.paintImmediately(0, 0, getWidth(), getHeight());
                }
            }

            paintButton.setText("Finished drawing");

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        new MatrixPaint().setVisible(true);
    }
}
