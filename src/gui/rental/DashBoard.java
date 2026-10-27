package gui.rental;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DashBoard extends JFrame {
    public static ArrayList<Rent> rents = new ArrayList<>();

    // Tabulka
    private JTable table;
    private static DefaultTableModel model;

    public DashBoard(){
        setTitle("Dashboard");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // North
        JLabel title = new JLabel("Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Consolas", Font.BOLD, 18));

        // Center
        String[] headers = new String[]{"Customer name", "Room number", "Ski type", "Discount", "Duration (Days)"};
        model = new DefaultTableModel(headers, 0);
        table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);

        rents.add(new Rent("Karel", 222, false, SkiType.CROSSCOUNTRY, 6));
        rents.add(new Rent("Pepa", 245, true, SkiType.SNOWBOARD, 9));

        // TODO lépe:
        reloadTable();

        // South
        JPanel buttonPanel = new JPanel();

        JButton formButton = new JButton("New rent");

        JButton detailButton = new JButton("Detail");
        JButton deleteButton = new JButton("Delete");
        JButton editButton = new JButton("Edit");
        JButton saveButton = new JButton("Save");

        deleteButton.addActionListener(e -> delete(table.getSelectedRow()));
        formButton.addActionListener(e -> openForm());
        saveButton.addActionListener(e -> save());
        editButton.addActionListener(e -> edit(table.getSelectedRow()));
        // TODO: dodělat zbytek tlačítek

        buttonPanel.add(formButton);
        buttonPanel.add(detailButton);
        buttonPanel.add(editButton);

        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);

        add(title, BorderLayout.NORTH);
        add(pane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }



    static void reloadTable(){
        model.setRowCount(0);
        for(Rent rent : rents){
            model.addRow(rent.returnAsTableRow());
        }
    }

    private void edit(int rowNum){
        if (rowNum < 0){
            JOptionPane.showMessageDialog(this, "Row isn't selected" ,"Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Rent r = rents.get(rowNum);
        RentalForm form = new RentalForm(r);
        form.setVisible(true);
    }

    private void delete(int rowNum){
        rents.remove(rowNum);
        reloadTable();
    }

    private void save() {
        // TODO: uložit ArrayList rents
    }

    private void openForm() {
        // TODO: otevřít formulář, získat z něj Rent objekt a nacpat do ArrayListu
        RentalForm form =  new RentalForm();
//        rents.add(new Rent(...))
        form.setVisible(true);
    }

    public static void main(String[] args) {
        new DashBoard().setVisible(true);
    }

}