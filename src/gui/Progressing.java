package gui;

import javax.swing.*;
import java.awt.*;

public class Progressing extends JFrame {

    JProgressBar bar;

    Progressing(){
        setSize(420,420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        bar = new JProgressBar(0,500);
        bar.setFont(new Font("Consolas", Font.BOLD, 28));
        bar.setForeground(Color.magenta);
        bar.setBackground(new Color(0xFF8941));

        bar.setStringPainted(true);
        bar.setBounds(0,0, getWidth(), 60);
        bar.setValue(50);

        add(bar);
    }

    public void fill(){
        int counter = 500;
        bar.setValue(500);
        while (counter > 0){
            try {
                Thread.sleep(86);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            counter--;
            bar.setValue(counter);
        }
    }

    public static void main(String[] args) {
        Progressing p = new Progressing();
        p.setVisible(true);
        p.fill();
    }

}
