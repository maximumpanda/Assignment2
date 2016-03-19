package Forms;

import Main.Assignment2;
import Main.DaySchool;

import javax.swing.*;
import java.time.format.DateTimeFormatter;

public class Form1 extends JFrame {

    private JList list1;
    private JPanel RootPanel;
    private JButton button1;
    private JLabel label1;

    public Form1(){
        setContentPane(RootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultListModel listValues = new DefaultListModel();
        for (DaySchool daySchool: Assignment2.DaySchoolManager.DaySchools){
            listValues.addElement(daySchool.GetDate().format(DateTimeFormatter.ofPattern("MM/dd/yyy")));
        }
        list1.setModel(listValues);
        list1.setSelectedIndex(0);
        list1.repaint();
        setVisible(true);
        //StartRefreshing();
        list1.addListSelectionListener(e -> label1.setText(Assignment2.DaySchoolManager.DaySchools[list1.getSelectedIndex()].GetTheme()));
        button1.addActionListener(e -> {
            StudentForm sf = new StudentForm();
            sf.setVisible(true);
        });
    }
}