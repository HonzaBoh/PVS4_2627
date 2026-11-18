package gui.avatars;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TableMenu extends JFrame {
    DefaultTableModel model;

    TableMenu(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 600);
        this.setLayout(new BorderLayout());
        this.setResizable(false);


        String[] column = {"Name", "Surname", "Shoe Size", "Salary", "Avatar Path"};
        model = new DefaultTableModel(column, 0);
        JTable table = new JTable(model);
        table.setEnabled(false);
        JScrollPane sp = new JScrollPane(table);
        JLabel infoLabel = new JLabel("Showing info about " + MainMenu.personData.size() + " persons");
        infoLabel.setFont(new Font("MV Boli", Font.BOLD, 21));
        this.add(infoLabel, BorderLayout.NORTH);

        loadDataIntoTable(MainMenu.personData);

        this.add(sp, BorderLayout.CENTER);
    }

    void loadDataIntoTable(List<Person> data){
        for(Person person:data){
            model.addRow(person.returnAsTableRow());
        }
    }
}
