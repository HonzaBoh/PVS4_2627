package gui.rental;

import javax.swing.*;
import java.awt.*;

public class RentalForm extends JFrame {

    private final JTextField nameField;
    private final JTextField roomField;
    private final JCheckBox studentDiscountCheckBox;
    private final JRadioButton skisButton;
    private final JRadioButton snowboardButton;
    private final JRadioButton crossCountryButton;
    private final ButtonGroup equipmentGroup;
    private final JSlider daysSlider;

    private boolean editMode = false;
    private Rent reservation;

    public RentalForm() {
        setTitle("Ski Rental");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(640, 420);
        setLocationRelativeTo(null);

        //NORTH cast
        JLabel titleLabel = new JLabel(
                "Mountain Equipment Rental",
                SwingConstants.CENTER //zarovnani jde rovnou do konstrukturu labelu
        );
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 18));

        add(titleLabel, BorderLayout.NORTH);


        //cast formulare - CENTER
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        formPanel.add(new JLabel("Customer name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Room number:"));
        roomField = new JTextField();
        formPanel.add(roomField);

        formPanel.add(new JLabel("Equipment:"));

        JPanel equipmentPanel = new JPanel(new FlowLayout());

        skisButton = new JRadioButton("Skis");
        snowboardButton = new JRadioButton("Snowboard");
        crossCountryButton = new JRadioButton("Cross-country skis");

        equipmentGroup = new ButtonGroup();
        equipmentGroup.add(skisButton);
        equipmentGroup.add(snowboardButton);
        equipmentGroup.add(crossCountryButton);

        //neco by mohlo byt vybrano defaultne
        skisButton.setSelected(true);

        equipmentPanel.add(skisButton);
        equipmentPanel.add(snowboardButton);
        equipmentPanel.add(crossCountryButton);

        formPanel.add(equipmentPanel);


        // Student discount
        formPanel.add(new JLabel("Student discount:"));

        //muze jit na stranu nebo doprostred, slusne dat na panel
        JPanel discountPanel = new JPanel(new FlowLayout());
        studentDiscountCheckBox = new JCheckBox();
        discountPanel.add(studentDiscountCheckBox);
        formPanel.add(discountPanel);


        // dny se sliderem
        formPanel.add(new JLabel("Rental days:"));
        daysSlider = new JSlider(1, 14, 3);
        daysSlider.setMajorTickSpacing(1);// napisy
        daysSlider.setPaintTicks(true);
        daysSlider.setPaintLabels(true);
        formPanel.add(daysSlider);

        add(formPanel, BorderLayout.CENTER);

        //ovladaci tlacitka dole - SOUTH
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton rentButton = new JButton("Rent");
        JButton clearButton = new JButton("Clear");

        buttonPanel.add(rentButton);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.SOUTH);


        //magie zde :)
        //uz toho je dost, takze ten kod by neuskodilo trochu krajet
        rentButton.addActionListener(e -> rent());
        clearButton.addActionListener(e -> clearForm());
    }

    public RentalForm(Rent reservation){
        this();
        this.reservation = reservation;
        nameField.setText(reservation.getCustomerName());
        roomField.setText(String.valueOf(reservation.getRoomNumber()));

        if (reservation.getSkiType().equals(SkiType.SNOWBOARD)){
            snowboardButton.setSelected(true);
        }
        if (reservation.getSkiType().equals(SkiType.CROSSCOUNTRY)){
            crossCountryButton.setSelected(true);
        }
        if (reservation.getSkiType().equals(SkiType.SKI)){
            skisButton.setSelected(true);
        }

        if (reservation.isStudentDiscount()){
            studentDiscountCheckBox.setSelected(true);
        }
        daysSlider.setValue(reservation.getRentalDays());
        editMode = true;
    }

    private void rent() {
        //NE: String += errMsg
        //Idealne taky ne: List.add(errMsg)
        StringBuilder errors = new StringBuilder();

        if (nameField.getText().isBlank()) {
            errors.append("- Customer name cannot be empty.\n");
        }
        int roomNumber = 0;
        //pokusy o parsovani/cteni cisel
        try {

            roomNumber = Integer.parseInt(roomField.getText());
            if (roomNumber < 100 || roomNumber > 699) {
                errors.append("- Room number must be between 100 and 699.\n");
            }
        } catch (NumberFormatException e) {

            errors.append("- Room number must be a number.\n");
        }


        // sleva + bezky nelze
        if (studentDiscountCheckBox.isSelected() && crossCountryButton.isSelected()) {
            errors.append("- Student discount does not apply to cross-country skis.\n");
        }
        // bezky max na 7 dni
        if (crossCountryButton.isSelected() && daysSlider.getValue() > 7) {
            errors.append("- Cross-country skis can be rented for a maximum of 7 days.\n");
        }
        // jsou tam chyby
        if (!errors.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Following errors occurred:\n"
                            + errors,
                    "Invalid rental",
                    JOptionPane.ERROR_MESSAGE
            );
            return; //nastaly chyby, nema cenu pokracovat
        }

        //tady uz vim, ze veci uvnitr jsou OK k vytvoreni rezervace

        //edituju?
        //ano -> uz mam rezervaci a jen menim
        //ne -> vytvarim novou rezervaci
        if (editMode){
            reservation.setCustomerName(nameField.getText());
            reservation.setRoomNumber(Integer.parseInt(roomField.getText()));
            reservation.setStudentDiscount(studentDiscountCheckBox.isSelected());
            reservation.setSkiType(getEquipmentType());
            reservation.setRentalDays(daysSlider.getValue());
            //nebude fungovat:
//            reservation = new Rent(
//                    nameField.getText(),
//                    Integer.parseInt(roomField.getText()),
//                    studentDiscountCheckBox.isSelected(),
//                    getEquipmentType(),
//                    daysSlider.getValue()
//            );
            //nefunguje, jelikoz promenna by neukazovala do listu
        } else {
            Rent rent = new Rent(
                    nameField.getText(),
                    Integer.parseInt(roomField.getText()),
                    studentDiscountCheckBox.isSelected(),
                    getEquipmentType(),
                    daysSlider.getValue()
            );
            DashBoard.rents.add(rent);
        }

        JOptionPane.showMessageDialog(
                this,
                "Rental succesfully created",
                "Rental",
                JOptionPane.INFORMATION_MESSAGE
        );
        //aktualizuj tabulku vsech rezervaci
        DashBoard.reloadTable();
        dispose();
    }


    private SkiType getEquipmentType() {
        if (snowboardButton.isSelected()) {
            return SkiType.SNOWBOARD;
        }
        if (crossCountryButton.isSelected()) {
            return SkiType.CROSSCOUNTRY;
        }
        return SkiType.SKI;
    }


    private void clearForm() {

        nameField.setText("");
        roomField.setText("");

        studentDiscountCheckBox.setSelected(false);

        equipmentGroup.clearSelection();
        skisButton.setSelected(true);

        daysSlider.setValue(3);
    }


    public static void main(String[] args) {

        RentalForm form = new RentalForm();
        form.setVisible(true);
    }
}