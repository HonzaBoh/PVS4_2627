package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ColorTickerTimer extends JFrame {

    JLabel colorDisplay;
    JTextField[] colors;
    Random r;
    public ColorTickerTimer() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Color switching");
        setLocationRelativeTo(null);
        r = new Random();

        setLayout(new FlowLayout());
        colors = new JTextField[3];
        for (int i = 0; i < 3; i++) {
            colors[i] = new JTextField(" ");
            colors[i].setPreferredSize(new Dimension(100,70 ));
            colors[i].setFont(new Font("Consolas", Font.PLAIN, 28));
            colors[i].setHorizontalAlignment(SwingConstants.CENTER);
            add(colors[i]);
        }

        colorDisplay = new JLabel(" ");
        colorDisplay.setOpaque(true);
        colorDisplay.setPreferredSize(new Dimension(200, 140));
        colorDisplay.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        add(colorDisplay);

        JButton run = new JButton("Start");
        run.addActionListener(e -> {
            Timer t = new Timer(500, a -> {
                getRandomColor();
            });
            t.start();

            run.setEnabled(false);
        }
        );
        add(run);
        pack();
    }

    void setColor(){
        colorDisplay.setBackground(new Color(
                Integer.parseInt(colors[0].getText().strip()),
                Integer.parseInt(colors[1].getText().trim()),
                Integer.parseInt(colors[2].getText().strip())
                ));
    }


    void getRandomColor(){
        colorDisplay.setBackground(new Color(
               r.nextInt(256),
               r.nextInt(256),
               r.nextInt(256)
        ));
    }

    public static void main(String[] args) {
        new ColorTickerTimer().setVisible(true);
    }
}
