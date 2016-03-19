package Forms;

import Main.Assignment2;
import Main.Student;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class StudentForm extends JFrame {
    private JPanel panel1;
    private JList list1;
    private JLabel AddressStreetLabel;
    private JLabel AddressCityLabel;
    private JLabel AddressCountryLabel;
    private JLabel StudentIDLabel;
    private JLabel NameLabel;
    private JLabel SpecialNeedsLabel;
    private JList list2;
    private JLabel DateLabel;
    private JLabel TimeLabel;
    private JLabel ThemeLabel;
    private JLabel TutorLabel;
    private JFormattedTextField StudentSearchTextBox;

    public StudentForm(){
        setContentPane(panel1);
        pack();
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultListModel listValues = new DefaultListModel();
        for (Student student: Assignment2.EntityManager.Students){
            listValues.addElement(student.GetName());
        }
        list1.setModel(listValues);
        ListListener();
        list1.setSelectedIndex(0);
        list2.setSelectedIndex(0);
        TextBoxListeners();
        StudentSearchTextBox.setText("Enter Student ID");
    }

    private void TextBoxListeners(){
        StudentSearchTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String studentIDPartial = StudentSearchTextBox.getText();
                if (studentIDPartial.length() >= 1) {
                    DefaultListModel listModel = new DefaultListModel();
                    for (Student student : Assignment2.EntityManager.Students) {
                        if (studentIDPartial.length() > 1) {
                            if (student.GetID().contains(studentIDPartial)) {
                                listModel.addElement(student.GetName());
                            }
                        } else {
                            if (student.GetID().charAt(student.GetID().length()-1) == studentIDPartial.charAt(0)) {
                                listModel.addElement(student.GetName());
                            }
                        }
                        list1.setModel(listModel);
                        list1.repaint();
                    }
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String studentIDPartial = StudentSearchTextBox.getText();
                if (studentIDPartial.length() == 0){
                    DefaultListModel listModel = new DefaultListModel();
                    for (Student student: Assignment2.EntityManager.Students){
                        listModel.addElement(student.GetName());
                    }
                    list1.setModel(listModel);
                    list1.repaint();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        StudentSearchTextBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                StudentSearchTextBox.setText("");
            }
        });
        StudentSearchTextBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) || c == KeyEvent.VK_S)) {
                    e.consume();
                }
            }
        });
    }

    private void ListListener(){
        list1.addListSelectionListener(e -> {
            if (list1.getSelectedValue() != null) {
                Student tempStudent = Assignment2.EntityManager.FindStudentByName(list1.getSelectedValue().toString());
                this.StudentIDLabel.setText(tempStudent.GetID());
                this.NameLabel.setText(tempStudent.GetName());
                String[] splitAddress = tempStudent.GetAddress().split(",");
                this.AddressStreetLabel.setText(splitAddress[0] + "," + splitAddress[1]);
                this.AddressCityLabel.setText(splitAddress[3] + "," + splitAddress[4]);
                this.AddressCountryLabel.setText(splitAddress[5]);
                this.SpecialNeedsLabel.setText(Boolean.toString(tempStudent.GetSpecialNeedsStatus()));
                DefaultListModel listValues = new DefaultListModel();
                for (int x = 0; x < Assignment2.DaySchoolManager.DaySchools.length; x++) {
                    for (int y = 0; y < 7; y++) {
                        if (ArrayContainsInt(Assignment2.DaySchoolManager.DaySchools[x].Sessions[y].GetStudentIDs(), tempStudent.GetID())) {
                            listValues.addElement(Assignment2.DaySchoolManager.DaySchools[x].GetDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + " " + Assignment2.DaySchoolManager.DaySchools[x].Sessions[y].GetTime());
                        }
                    }
                }
                list2.setModel(listValues);
                list2.repaint();
            }
        });
        list2.addListSelectionListener(e -> {
            int index = list2.getSelectedIndex();
            if (index == -1){
                index = 0;
                list2.setSelectedIndex(index);
            }
            String text = list2.getModel().getElementAt(index).toString();
            String[] dateSplit = text.split(" ");
            LocalDate date = LocalDate.parse(dateSplit[0], DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            String[] timeSplit = dateSplit[1].split(":");
            LocalTime time = LocalTime.of(Integer.parseInt(timeSplit[0]), Integer.parseInt(timeSplit[1]));
            this.DateLabel.setText(dateSplit[0]);
            this.TimeLabel.setText(dateSplit[1]);
            int daySchoolIndex = -1;

            for (int x = 0; x < Assignment2.DaySchoolManager.DaySchools.length; x++){
                LocalDate tempDate = Assignment2.DaySchoolManager.DaySchools[x].GetDate();
                if (tempDate.getDayOfMonth() == date.getDayOfMonth() && tempDate.getMonth() == date.getMonth() && tempDate.getYear() == date.getYear()) {
                    daySchoolIndex = x;
                    break;
                }
            }
            if (daySchoolIndex != -1){
                int sessionIndex = -1;
                for (int y = 0; y < Assignment2.DaySchoolManager.DaySchools[daySchoolIndex].Sessions.length; y++){
                    if (Assignment2.DaySchoolManager.DaySchools[daySchoolIndex].Sessions[y].GetTime() == time) {
                        sessionIndex = y;
                        break;
                    }
                }
                if (sessionIndex != -1)
                {
                    this.ThemeLabel.setText(Assignment2.DaySchoolManager.DaySchools[daySchoolIndex].GetTheme());
                    this.TutorLabel.setText(Assignment2.EntityManager.FindTutorByID(Assignment2.DaySchoolManager.DaySchools[daySchoolIndex].Sessions[sessionIndex].GetTutorID()).GetName());
                }
                else{
                    this.DateLabel.setText("error");
                    this.TimeLabel.setText("error");
                    this.ThemeLabel.setText("error");
                    this.TutorLabel.setText("error");
                }
            }
            else {
                this.DateLabel.setText("error");
                this.TimeLabel.setText("error");
                this.ThemeLabel.setText("error");
                this.TutorLabel.setText("error");
            }

        });
    }

    private boolean ArrayContainsInt(final String[] array, String check){
        return Arrays.stream(array).anyMatch(i -> i == check);
    }
}
