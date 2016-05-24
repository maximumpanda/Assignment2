package Forms;

import Core.Assignment2;
import Core.DaySchool;
import Core.Session;
import Core.Student;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SearchSessionForStudentsForm extends JFrame {
    private JTextField daySchoolThemeTextBox;
    private JTextField sessionIDTextBox;
    private JList<String> studentsList;
    private JPanel rootPanel;

    private final List<Session> sessions = new ArrayList<>();

    public SearchSessionForStudentsForm(){
        setContentPane(rootPanel);
        this.pack();
        this.setLocationRelativeTo(Assignment2.Form1);
        textBoxListeners();
    }

    private void textBoxListeners(){
        this.daySchoolThemeTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                FindSessions();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                FindSessions();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {}

            private void FindSessions(){
                sessions.clear();
                String partialTheme = daySchoolThemeTextBox.getText();
                if (partialTheme.length() > 0){
                    for (DaySchool dayschool: Assignment2.daySchoolManager.daySchools){
                        if (dayschool.getTheme().equals(partialTheme)){
                            Collections.addAll(sessions, dayschool.sessions);
                        }
                    }
                }
            }
        });

        this.sessionIDTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                GetStudents();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                GetStudents();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }

            private void GetStudents(){
                String partialSessionID = sessionIDTextBox.getText();
                DefaultListModel<String> listModel = new DefaultListModel<>();
                sessions.stream().filter(session -> partialSessionID.equals(Integer.toString(session.getID()))).forEach(session -> {
                    for (String studentID : session.getStudentIDs()) {
                        Student workStudent = Assignment2.entityManager.getStudentByID(studentID);
                        listModel.addElement(workStudent.getID() + ": " + workStudent.getName());
                    }
                });
                studentsList.setModel(listModel);
            }
        });
    }
}
