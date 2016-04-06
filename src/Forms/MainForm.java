package Forms;

import Main.Assignment2;
import Main.DaySchool;
import Main.Session;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MainForm extends JFrame {

    private JList<String> daySchoolList;
    private JPanel rootPanel;
    JButton studentsBtn;
    private JButton tutorsBtn;
    JTextField searchTextBox;
    private JButton searchBtn;
    private JLabel themeLabel;
    private JLabel dateLabel;
    private JList<String> sessionList;
    private JPanel daySchoolDataPanel;
    private JLabel sessionTimeLabel;
    private JLabel sessionTutorLabel;
    private JList<String> studentsList;
    JButton addStudentBtn;
    private JButton removeStudentBtn;
    private JButton helpBtn;

    private EntityInspectFramework entityInspectForm = null;
    private EditEntityFramework entityEditForm = null;
    private HelpFramework helpForm = null;

    public MainForm(){
        setContentPane(rootPanel);
        pack();
        this.setLocationRelativeTo(null);
        searchBtn.setMargin(new java.awt.Insets(1,1,1,1));
        addStudentBtn.setMargin(new java.awt.Insets(1,1,1,1));
        removeStudentBtn.setMargin(new java.awt.Insets(1,1,1,1));
        studentsBtn.setMargin(new java.awt.Insets(1,1,1,1));
        tutorsBtn.setMargin(new java.awt.Insets(1,1,1,1));
        helpBtn.setMargin(new java.awt.Insets(1,1,1,1));
        this.daySchoolList.setCellRenderer(new WhiteGreyCellRenderer());
        this.sessionList.setCellRenderer(new WhiteGreyCellRenderer());
        this.studentsList.setCellRenderer(new WhiteGreyCellRenderer());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        daySchoolList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setVisible(true);
        updateDaySchoolList();
        daySchoolList.repaint();
        listListeners();
        buttonListeners();
        textBoxListeners();
        additionalListeners();
        daySchoolList.setSelectedIndex(0);
    }

    private void updateDaySchoolList(){
        int selectedIndex = daySchoolList.getSelectedIndex();
        int selectedSession = sessionList.getSelectedIndex();
        DefaultListModel<String> listValues = new DefaultListModel<>();
        for (DaySchool daySchool: Assignment2.daySchoolManager.daySchools){
            listValues.addElement(daySchool.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + daySchool.getTheme());
        }
        daySchoolList.setModel(listValues);
        daySchoolList.setSelectedIndex(selectedIndex);
        sessionList.setSelectedIndex(selectedSession);
    }

    private void buttonListeners(){
        searchBtn.addActionListener(e->{
            SearchSessionForStudentsForm ssfsf = new SearchSessionForStudentsForm();
            ssfsf.setVisible(true);
        });

        studentsBtn.addActionListener(e -> {
            if (entityInspectForm == null || !entityInspectForm.isVisible() ||
                    entityEditForm == null || !entityEditForm.isVisible()){
                entityInspectForm = new StudentsForm();
                entityInspectForm.setVisible(true);
            }

        });
        tutorsBtn.addActionListener(e-> {
            if (entityInspectForm == null || !entityInspectForm.isVisible() ||
                    entityEditForm == null || !entityEditForm.isVisible()){
                entityInspectForm = new TutorsForm();
                entityInspectForm.setVisible(true);
            }
        });

        removeStudentBtn.addActionListener(e->{
            int index = studentsList.getSelectedIndex();
            if (index != -1) {
                String studentID = Assignment2.entityManager.getStudentByName(studentsList.getModel().getElementAt(index)).getID();
                String daySchoolDate = dateLabel.getText();
                String[] dateSplit = daySchoolDate.split("/");
                String sessionTime = sessionTimeLabel.getText();
                for (int x = 0; x< Assignment2.daySchoolManager.daySchools.length; x++){
                    if (Assignment2.daySchoolManager.daySchools[x].getDate().equals(LocalDate.parse(dateSplit[2] + "-" + dateSplit[1] + "-" + dateSplit[0]))){
                        for (int y = 0; y < Assignment2.daySchoolManager.daySchools[x].sessions.length; y++){
                            if (Assignment2.daySchoolManager.daySchools[x].sessions[y].getTime().equals(LocalTime.parse(sessionTime))){
                                Assignment2.daySchoolManager.daySchools[x].sessions[y].removeStudentByID(studentID);
                                DefaultListModel<String> listModel = new DefaultListModel<>();
                                for (String sID: Assignment2.daySchoolManager.daySchools[x].sessions[y].getStudentIDs()){
                                    listModel.addElement(Assignment2.entityManager.getStudentByID(sID).getName());
                                }
                                studentsList.setModel(listModel);
                                studentsList.repaint();
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        });

        addStudentBtn.addActionListener(e ->{
            if (entityInspectForm == null || !entityInspectForm.isVisible() ||
                    entityEditForm == null || !entityEditForm.isVisible()){
                String[] dateSplit = this.dateLabel.getText().split("/");
                entityEditForm = new SearchStudentForm(LocalDate.parse(dateSplit[2] + "-" + dateSplit[1] + "-"+ dateSplit[0]), LocalTime.parse(this.sessionTimeLabel.getText()));
                entityEditForm.setVisible(true);
            }
        });

        helpBtn.addActionListener(e->{
            if (helpForm == null || !helpForm.isVisible()){
                helpForm = new Form1HelpForm();
                helpForm.setVisible(true);
            }
        });
    }

    private void listListeners(){
        daySchoolList.addListSelectionListener(e -> {
            int index = daySchoolList.getSelectedIndex();
            if (index != -1) {
                String selectedValue = daySchoolList.getModel().getElementAt(index);
                String[] dateString = selectedValue.split(" ");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                DaySchool workDaySchool = Assignment2.daySchoolManager.getDaySchoolByDate(LocalDate.parse(dateString[0], formatter));
                dateLabel.setText(workDaySchool.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                themeLabel.setText(String.format("<html><div WIDTH=%d>%s</div><html>", daySchoolDataPanel.getWidth() / 2, workDaySchool.getTheme()));
                DefaultListModel<String> listModel = new DefaultListModel<>();
                for (Session session : workDaySchool.sessions) {
                    listModel.addElement(session.getID() + " - " + session.getTime().toString() + " - " + Assignment2.entityManager.getTutorByID(session.getTutorID()).getName());
                }
                sessionList.setModel(listModel);
                sessionList.repaint();
                sessionList.setSelectedIndex(0);
            }
        });
        sessionList.addListSelectionListener(e->{
            int index = sessionList.getSelectedIndex();
            if (index == -1) index = 0;
            String selectedValue = sessionList.getModel().getElementAt(index);
            String[] values = selectedValue.split(" - ");
            DaySchool workDaySchool = Assignment2.daySchoolManager.getDaySchoolByDate(LocalDate.parse(this.dateLabel.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            for (Session session: workDaySchool.sessions)
            {
                if (session.getTime().toString().equals(values[1]))
                {
                    this.sessionTimeLabel.setText(session.getTime().toString());
                    this.sessionTutorLabel.setText(Assignment2.entityManager.getTutorByID(session.getTutorID()).getName());
                    DefaultListModel<String> listModel = new DefaultListModel<>();
                    for (String studentID: session.getStudentIDs()){
                        String studentName = Assignment2.entityManager.getStudentByID(studentID).getName();
                        if (studentName != null) listModel.addElement(studentName);
                    }
                    studentsList.setModel(listModel);
                    studentsList.repaint();
                    studentsList.setSelectedIndex(0);
                }
            }
        });
    }

    private void textBoxListeners(){
        searchTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String IDPartial = searchTextBox.getText();
                DefaultListModel<String> listModel = new DefaultListModel<>();
                if (IDPartial.length() >= 1) {
                    for (DaySchool daySchool : Assignment2.daySchoolManager.daySchools) {
                        if (daySchool.getTheme().contains(IDPartial)) listModel.addElement(daySchool.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + daySchool.getTheme());
                        if (daySchool.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).contains(IDPartial)) listModel.addElement(daySchool.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + daySchool.getTheme());
                    }
                }
                daySchoolList.setModel(listModel);
                daySchoolList.repaint();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String IDPartial = searchTextBox.getText();
                DefaultListModel<String> listModel = new DefaultListModel<>();
                if (IDPartial.length() == 0) {
                    for (DaySchool daySchool : Assignment2.daySchoolManager.daySchools) {
                        listModel.addElement(daySchool.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + daySchool.getTheme());
                    }
                } else {
                    for (DaySchool daySchool : Assignment2.daySchoolManager.daySchools) {
                        if (daySchool.getTheme().contains(IDPartial)) {
                            listModel.addElement(daySchool.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + daySchool.getTheme());
                        }
                        else if (daySchool.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).contains(IDPartial))
                        {
                            listModel.addElement(daySchool.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + daySchool.getTheme());
                        }
                    }
                }
                daySchoolList.setModel(listModel);
                daySchoolList.repaint();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        searchTextBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                searchTextBox.setText("");
            }
        });
    }

    private void additionalListeners(){
        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                if (entityInspectForm == null || !entityInspectForm.isVisible()){
                    updateDaySchoolList();
                }
                else {
                    entityInspectForm.toFront();
                }
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });
    }
}