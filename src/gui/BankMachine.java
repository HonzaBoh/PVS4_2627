package gui;

import javax.swing.*;
import java.awt.*;

public class BankMachine {

    public static void main(String[] args) {
        new InputWindow().setVisible(true);
    }

}
class InputWindow extends JFrame{

    public InputWindow() throws HeadlessException {
        setSize(700,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new FlowLayout());

        JButton getValuesButton = new JButton("Go");
        getValuesButton.setPreferredSize(new Dimension(150,100));
        JTextField inputField = new JTextField("Value");

        getValuesButton.addActionListener(e ->{
            String tfValue = inputField.getText();
            boolean number = true;
            for (char a : tfValue.toCharArray()){
                if (!Character.isDigit(a)){
                    number = false;
                    break;
                }
            }
            if (number)
                new ResultWindow(Integer.parseInt(inputField.getText())).setVisible(true);
            else
                JOptionPane.showMessageDialog(null, "Zadej cislo", "Chyba", JOptionPane.ERROR_MESSAGE);
        });

        inputField.setFont(new Font("Consolas", Font.BOLD, 26));
        inputField.setPreferredSize(new Dimension(500,100));
        inputField.setHorizontalAlignment(SwingConstants.CENTER);

        add(inputField);
        add(getValuesButton);

        this.pack();

    }
} class ResultWindow extends JFrame{

    static final int[] NOMINALS = {5000, 2000, 1000, 500, 200, 100, 50, 20, 10, 5, 2, 1};
    int number;
    static void getNominals(int number, JPanel gridPanel){
        System.out.println("Number: " + number);
        for (int i = 0, count = 0; i < NOMINALS.length; i++, count = 0) {
            while (number >= NOMINALS[i]){
                number -= NOMINALS[i];
                count++;
            }
            gridPanel.add(new BankTile(NOMINALS[i], count));
            System.out.println(count + "x " + NOMINALS[i]);
        }
    }

    ResultWindow(int value){
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        number = value;

        JLabel totalValue = new JLabel(String.valueOf(number));
        totalValue.setPreferredSize(new Dimension(400,100));
        totalValue.setHorizontalAlignment(SwingConstants.CENTER);
        totalValue.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        totalValue.setFont(new Font("Consolas", Font.BOLD, 32));

        add(totalValue, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3,4, 3,3));
        gridPanel.setPreferredSize(new Dimension(400,300));

        getNominals(number, gridPanel);

        add(gridPanel, BorderLayout.CENTER);
        pack();
    }
}
class BankTile extends JPanel{

    BankTile(int value, int amount){
        setLayout(new GridLayout(2,1));
        JLabel valueLabel = new JLabel(String.valueOf(value));
        JLabel amountLabel = new JLabel(amount + "x");

        valueLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        amountLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        amountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        valueLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        amountLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        setOpaque(true);

        if (amount > 0){
            setBackground(Color.green);
        } else {
            setBackground(Color.red);
        }

        add(amountLabel);
        add(valueLabel);
    }
}

