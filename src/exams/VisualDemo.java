package exams;

import javax.swing.*;
import java.awt.*;

public class VisualDemo extends JFrame {

    public static void main(String[] args) {
       new VisualDemo().setVisible(true);
    }

    public VisualDemo() {
        this.setTitle("task");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1200, 700));

        // NORTH panel na hledani
        JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        north.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        JTextField tf = new JTextField(28); //muzu nastavit takhle misto sirky
        JButton btn = new JButton("Go");
        north.add(new JLabel("Query:"));
        north.add(tf);
        north.add(btn);

        // WEST radiobuttons, 1 sloupec, trochu oddelene mezerama
        JPanel west = new JPanel(new GridLayout(0, 1, 6, 6));
        west.setPreferredSize(new Dimension(220, 10));
        ButtonGroup group = new ButtonGroup();
        String[] options = {"Option A", "Option B", "Option C", "Option D", "Option E"};
        for (int i = 0; i < options.length; i++) {
            JRadioButton rb = new JRadioButton(options[i], i == 0);//defaultne zaskrtne prvni
            group.add(rb);
            west.add(rb);
        }

        // CENTER - dlazdice, labely 10x11, mezery pro lepsi viditelnost
        JPanel grid = new JPanel(new GridLayout(10, 11, 8, 8));

        int rows = 10, cols = 11;
        for (int i = 1; i <= rows * cols; i++) {
            grid.add(makeTile("Tile " + i));
        }

        // SOUTH - label vpravo dole
        JLabel results = new JLabel("110 results.");
        results.setFont(results.getFont().deriveFont(Font.ITALIC, 12f));
        results.setHorizontalAlignment(SwingConstants.RIGHT);

        // hazim do frame
        this.add(north, BorderLayout.NORTH);
        this.add(west, BorderLayout.WEST);
        this.add(grid, BorderLayout.CENTER);
        this.add(results, BorderLayout.SOUTH);

        this.pack();
        this.setLocationByPlatform(true);
        this.setVisible(true);
    }


    private static JLabel makeTile(String title) {
        JLabel lbl = new JLabel(title, SwingConstants.CENTER);
        lbl.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lbl.setBackground(Color.white);
        lbl.setOpaque(true);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 13f));
        return lbl;
    }

}
