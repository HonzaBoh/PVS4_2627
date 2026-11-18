package gui.avatars;

import javax.swing.*;

public class Person {
    ImageIcon avatar;
    String avatarPath;
    String name, surname;
    int shoeSize, salary;

    public Person(String name, String surname, int shoeSize, int salary, String avatarPath) {
        this.avatarPath = avatarPath;
        this.avatar = new ImageIcon(avatarPath);
        this.name = name;
        this.surname = surname;
        this.shoeSize = shoeSize;
        this.salary = salary;
    }

    /**
     * @return data objektu tak, aby sla vlozit do JTable
     */
    public String[] returnAsTableRow(){
        return new String[]{name, surname, String.valueOf(shoeSize), String.valueOf(salary), avatarPath};
    }

}
