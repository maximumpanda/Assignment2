package Forms;

import Main.Assignment2;
import Main.DaySchool;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Form1 extends JFrame {

    private JList<String> DaySchoolList;
    private JPanel RootPanel;
    private JButton StudentsBtn;
    private JLabel label1;
    private JButton TutorsBtn;
    private JTextField SearchTextBox;
    private JButton AddDaySchoolBtn;
    private JLabel ThemeLabel;
    private JLabel DateLabel;

    private EntityInspectFramework entityInspectFramework = null;

    public Form1(){
        setContentPane(RootPanel);
        pack();
        this.setLocationRelativeTo(null);
        AddDaySchoolBtn.setMargin(new java.awt.Insets(1,1,1,1));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DaySchoolList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setVisible(true);
        UpdateDaySchoolList();
        DaySchoolList.repaint();
        ListListeners();
        ButtonListeners();
        TextBoxListeners();
        AdditionalListeners();
        DaySchoolList.setSelectedIndex(0);
    }

    private void UpdateDaySchoolList(){
        DefaultListModel<String> listValues = new DefaultListModel<String>();
        for (DaySchool daySchool: Assignment2.DaySchoolManager.DaySchools){
            listValues.addElement(daySchool.GetDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + daySchool.GetTheme());
        }
        DaySchoolList.setModel(listValues);
    }

    private void ButtonListeners(){
        StudentsBtn.addActionListener(e -> {
            entityInspectFramework = new StudentsForm();
            entityInspectFramework.setVisible(true);
        });
        TutorsBtn.addActionListener(e-> {
            entityInspectFramework = new TutorsForm();
            entityInspectFramework.setVisible(true);
        });
    }

    private void ListListeners(){
        DaySchoolList.addListSelectionListener(e -> {
            int index = DaySchoolList.getSelectedIndex();
            if ( index == -1)
            {
                index = 0;
            }
            String selectedValue = DaySchoolList.getModel().getElementAt(index).toString();
            String[] dateString = selectedValue.split(" ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DaySchool workDaySchool = Assignment2.DaySchoolManager.GetDaySchoolByDate(LocalDate.parse(dateString[0], formatter));
            DateLabel.setText(workDaySchool.GetDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            ThemeLabel.setText(workDaySchool.GetTheme());
        });
    }

    private void TextBoxListeners(){
        SearchTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String IDPartial = SearchTextBox.getText();
                DefaultListModel<String> listModel = new DefaultListModel<String>();
                if (IDPartial.length() >= 1) {
                    for (DaySchool daySchool : Assignment2.DaySchoolManager.DaySchools) {
                        if (daySchool.GetTheme().contains(IDPartial)) listModel.addElement(daySchool.GetDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + daySchool.GetTheme());
                        if (daySchool.GetDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).contains(IDPartial)) listModel.addElement(daySchool.GetDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + daySchool.GetTheme());
                    }
                }
                DaySchoolList.setModel(listModel);
                DaySchoolList.repaint();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String IDPartial = SearchTextBox.getText();
                DefaultListModel<String> listModel = new DefaultListModel<String>();
                if (IDPartial.length() == 0) {
                    for (DaySchool daySchool : Assignment2.DaySchoolManager.DaySchools) {
                        listModel.addElement(daySchool.GetDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + daySchool.GetTheme());
                    }
                } else {
                    for (DaySchool daySchool : Assignment2.DaySchoolManager.DaySchools) {
                        if (daySchool.GetTheme().contains(IDPartial)) {
                            listModel.addElement(daySchool.GetDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + daySchool.GetTheme());
                        }
                        else if (daySchool.GetDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).contains(IDPartial))
                        {
                            listModel.addElement(daySchool.GetDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + daySchool.GetTheme());
                        }
                    }
                }
                DaySchoolList.setModel(listModel);
                DaySchoolList.repaint();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        SearchTextBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SearchTextBox.setText("");
            }
        });
    }

    private void AdditionalListeners(){
        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                if (entityInspectFramework == null || !entityInspectFramework.isVisible()){
                    UpdateDaySchoolList();
                }
                else {
                    entityInspectFramework.toFront();
                }
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });
    }
}