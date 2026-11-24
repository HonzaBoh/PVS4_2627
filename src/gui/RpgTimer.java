package gui;

import javax.swing.*;
import java.awt.*;

public class RpgTimer extends JFrame {

    private int playerHP = 100;
    private int monsterHP = 100;

    private int playerCooldown = 0;
    private int monsterCooldown = 3;

    private JLabel playerCooldownLabel;
    private JLabel monsterCooldownLabel;

    private JLabel playerHPLabel;
    private JLabel monsterHPLabel;

    private JButton quickAttackButton;
    private JButton heavyAttackButton;
    private JButton specialAttackButton;

    private Timer playerTimer;
    private Timer monsterTimer;

    public RpgTimer() {
        setTitle("Mini RPG Duel");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(1, 2, 10, 10));

        createPlayerPanel();
        createMonsterPanel();

        createTimers();
    }

    private void createPlayerPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 5, 5));

        JLabel nameLabel = new JLabel("Knight", JLabel.CENTER);
        playerCooldownLabel = new JLabel("Cooldown: 0 s", JLabel.CENTER);
        JLabel imageLabel = new JLabel(new ImageIcon("knight.png"));
        playerHPLabel = new JLabel("HP: 100 / 100", JLabel.CENTER);

        // Attack buttons panel
        JPanel attackPanel = new JPanel(new GridLayout(1, 3, 5, 5));

        quickAttackButton = new JButton("Quick attack");
        heavyAttackButton = new JButton("Heavy attack");
        specialAttackButton = new JButton("Special attack");

        quickAttackButton.addActionListener(e -> attack(10, 1));
        heavyAttackButton.addActionListener(e -> attack(25, 3));
        specialAttackButton.addActionListener(e -> attack(45, 5));

        attackPanel.add(quickAttackButton);
        attackPanel.add(heavyAttackButton);
        attackPanel.add(specialAttackButton);

        panel.add(nameLabel);
        panel.add(playerCooldownLabel);
        panel.add(imageLabel);
        panel.add(playerHPLabel);
        panel.add(attackPanel);

        add(panel);
    }

    private void createMonsterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 5, 5));

        JLabel nameLabel = new JLabel("Monster", JLabel.CENTER);
        monsterCooldownLabel = new JLabel("Cooldown: 3 s", JLabel.CENTER);

        JLabel imageLabel = new JLabel(new ImageIcon("monster.png"));
        monsterHPLabel = new JLabel("HP: 100 / 100", JLabel.CENTER);

        panel.add(nameLabel);
        panel.add(monsterCooldownLabel);
        panel.add(imageLabel);
        panel.add(monsterHPLabel);

        // Empty rows to keep both columns aligned
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(new JLabel());

        add(panel);
    }

    private void createTimers() {
        playerTimer = new Timer(1000, e -> {
            playerCooldown--;

            playerCooldownLabel.setText("Cooldown: " + playerCooldown + " s");

            if (playerCooldown <= 0) {
                playerTimer.stop();
                setAttackButtonsEnabled(true);
            }
        });
        monsterTimer = new Timer(1000, e -> {
            monsterCooldown--;

            if (monsterCooldown <= 0) {
                playerHP = Math.max(0, playerHP - 15);
                monsterCooldown = 3;

                playerHPLabel.setText("HP: " + playerHP + " / 100");
            }

            monsterCooldownLabel.setText("Cooldown: " + monsterCooldown + " s");

            checkGameOver();
        });
    }

    private void attack(int damage, int cooldown) {
        if (!monsterTimer.isRunning()) {
            monsterTimer.start();
        }

        monsterHP = Math.max(0, monsterHP - damage);
        monsterHPLabel.setText("HP: " + monsterHP + " / 100");

        playerCooldown = cooldown;
        playerCooldownLabel.setText("Cooldown: " + playerCooldown + " s");

        setAttackButtonsEnabled(false);
        playerTimer.start();

        checkGameOver();
    }

    private void setAttackButtonsEnabled(boolean enabled) {
        quickAttackButton.setEnabled(enabled);
        heavyAttackButton.setEnabled(enabled);
        specialAttackButton.setEnabled(enabled);
    }

    private void checkGameOver() {
        if (playerHP > 0 && monsterHP > 0) {
            return;
        }

        playerTimer.stop();
        monsterTimer.stop();

        setAttackButtonsEnabled(false);

        if (monsterHP <= 0) {
            JOptionPane.showMessageDialog(this, "You win!");
        } else {
            JOptionPane.showMessageDialog(this, "You lose!");
        }
    }

    public static void main(String[] args) {
        new RpgTimer().setVisible(true);

        // Monster utoci kazde 3s za 10hp
        //   BONUS: // Sance 33% na jedovy utok, ktery pusobi
        //   BONUS: // 5s a kazdou sekundu ubira 5hp

        // Quick attack zautoci za 5hp a ma Cooldown 2s
        // Heavy attack zautoci za 10hp a ma Cooldown 3s
        // Special attack zautoci za 20hp a ma cooldown 5s
        //   BONUS: // Kriticka sance 40% na 2x dmg

        // Vyhrava ten, kdo prezije
        // Ohlasite messageBoxem
    }
}