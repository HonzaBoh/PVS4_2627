package gui;

import javax.swing.*;
import java.awt.*;

public class ScreenSaver extends JFrame {
    public ScreenSaver(){
        Canvas panel = new Canvas();
        add(panel);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    public static void main(String[] args) {
        new ScreenSaver().setVisible(true);
    }

}
class Canvas extends JPanel{
    final int CANVAS_WIDTH = 1000;
    final int CANVAS_HEIGHT = 550;
    Image bouncing;
    int x = 0;
    int y = 0;
    int xVelocity = 2;
    int yVelocity = 4;

    Canvas(){
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        setBackground(Color.black);

        bouncing = new ImageIcon("dvd.png").getImage();
        Timer t = new Timer(10, e -> doBouncing());
        t.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

//        g2d.drawLine(0,0, 500,60);
        g2d.drawImage(bouncing, x,y, null);
    }

    void doBouncing(){
        if (x >= CANVAS_WIDTH - bouncing.getWidth(null) || x < 0){
            xVelocity *= -1;
        }
        x += xVelocity;

        if (y >= CANVAS_HEIGHT - bouncing.getHeight(null) || y < 0){
            yVelocity *= -1;
        }

        y += yVelocity;
        repaint();
    }
}
