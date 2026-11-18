package gui.reservations;

import javax.swing.*;
import java.awt.*;

public class ReservationForm extends JFrame {

    private JTextField nameField;
    private JTextField roomField;
    private JRadioButton singleButton;
    private JRadioButton doubleButton;
    private JRadioButton apartmentButton;

    private ButtonGroup roomTypeGroup;

    private JCheckBox extraBedCheckBox;

    private JSlider daysSlider;


    public ReservationForm() {
        setTitle("Hotel Reservation");
        setSize(600, 430);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Hotel Reservation",SwingConstants.CENTER);

        titleLabel.setFont(new Font("Consolas", Font.BOLD, 18));

        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel nameLabel = new JLabel("Guest name:");

        nameField = new JTextField();

        formPanel.add(nameLabel);
        formPanel.add(nameField);

        JLabel typeLabel = new JLabel("Room type:");

        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        singleButton = new JRadioButton("Single");
        doubleButton = new JRadioButton("Double");
        apartmentButton = new JRadioButton("Apartment");

        roomTypeGroup = new ButtonGroup();

        roomTypeGroup.add(singleButton);
        roomTypeGroup.add(doubleButton);
        roomTypeGroup.add(apartmentButton);

        singleButton.setSelected(true);

        typePanel.add(singleButton);
        typePanel.add(doubleButton);
        typePanel.add(apartmentButton);

        formPanel.add(typeLabel);
        formPanel.add(typePanel);

        JLabel daysLabel = new JLabel("Days:");

        daysSlider = new JSlider(0, 80, 20);

        daysSlider.setMajorTickSpacing(20);
        daysSlider.setMinorTickSpacing(5);
        daysSlider.setPaintTicks(true);
        daysSlider.setPaintLabels(true);

        formPanel.add(daysLabel);
        formPanel.add(daysSlider);

        JLabel roomLabel = new JLabel("Room number:");

        roomField = new JTextField();
        formPanel.add(roomLabel);
        formPanel.add(roomField);

        JLabel extraBedLabel = new JLabel("Extra bed:");

        JPanel extraBedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        extraBedCheckBox = new JCheckBox();
        extraBedPanel.add(extraBedCheckBox);

        formPanel.add(extraBedLabel);
        formPanel.add(extraBedPanel);
        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton reserveButton = new JButton("Reserve");
        JButton clearButton = new JButton("Clear");
        buttonPanel.add(reserveButton);
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.SOUTH);

        reserveButton.addActionListener(e -> reserve());
        clearButton.addActionListener(e -> clearForm());
    }

    private void reserve() {

        StringBuilder errors = new StringBuilder();
        if (nameField.getText().isBlank()) {

            errors.append(
                    "- Guest name cannot be empty.\n"
            );
        }

        int roomNumber = 0;
        try {
            roomNumber = Integer.parseInt(roomField.getText());

            if (roomNumber < 100 || roomNumber > 799) {
                errors.append(
                        "- Room number must be between 100 and 799.\n"
                );
            }

        } catch (NumberFormatException e) {
            errors.append(
                    "- Room number must be a number.\n"
            );
        }

        if (extraBedCheckBox.isSelected() && singleButton.isSelected()) {
            errors.append(
                    "- Extra bed is not available for Single rooms.\n"
            );
        }

        if (apartmentButton.isSelected()
                && daysSlider.getValue() < 7) {

            errors.append(
                    "- Apartments must be reserved for at least 7 days.\n"
            );
        }

        if (!errors.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Following errors occurred:\n" + errors,
                    "Invalid reservation",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Reservation successfully created.\n\n"
                        + "Guest: " + nameField.getText()
                        + "\nRoom type: " + getRoomType()
                        + "\nDays: " + daysSlider.getValue()
                        + "\nRoom number: " + roomNumber
                        + "\nExtra bed: "
                        + (extraBedCheckBox.isSelected()
                        ? "Yes"
                        : "No"),
                "Reservation",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private String getRoomType() {

        if (doubleButton.isSelected()) {
            return "Double";
        }

        if (apartmentButton.isSelected()) {
            return "Apartment";
        }

        return "Single";
    }

    private void clearForm() {

        nameField.setText("");
        roomField.setText("");

        singleButton.setSelected(true);

        extraBedCheckBox.setSelected(false);

        daysSlider.setValue(20);
    }


    public static void main(String[] args) {

        ReservationForm window = new ReservationForm();

        window.setVisible(true);
    }
}