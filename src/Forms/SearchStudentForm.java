package Forms;

import Core.Assignment2;
import Core.Student;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;

class SearchStudentForm extends EditEntityFramework {

    private final LocalDate DaySchoolDate;
    private final LocalTime SessionTime;

    SearchStudentForm(LocalDate daySchoolDate, LocalTime sessionTime){
        this.DaySchoolDate = daySchoolDate;
        this.SessionTime = sessionTime;
        this.entityDetailsPanel.remove(this.entityIDLabel);
        this.setupForm();
        this.entityNameTextBox.setEnabled(false);
        this.entityNameTextBox.setEditable(false);
        this.entityNameTextBox.setDisabledTextColor(Color.black);
        this.entityAddressArea.setEnabled(false);
        this.entityAddressArea.setEditable(false);
        this.entityAddressArea.setDisabledTextColor(Color.black);
        this.studentHasSpecialNeedsCheckBox.setEnabled(false);
        textBoxListeners();
    }

    private void textBoxListeners(){
        this.entityIDTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String partialID = entityIDTextBox.getText();
                Student workStudent = Assignment2.entityManager.getStudentByID(partialID);
                if (workStudent == null) return;
                entityNameTextBox.setText(workStudent.getName());
                entityAddressArea.setText(workStudent.getAddress());
                studentHasSpecialNeedsCheckBox.setSelected(workStudent.GetSpecialNeedsStatus());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                entityNameTextBox.setText("");
                entityAddressArea.setText("");
                studentHasSpecialNeedsCheckBox.setSelected(false);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        this.entityIDTextBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) || c == KeyEvent.VK_S)) {
                    e.consume();
                }

            }
        });
    }

    @Override
    void buttonOKListener() {
        buttonOK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Assignment2.entityManager.getStudentByID(entityIDTextBox.getText())!= null) {
                    for (int x = 0; x < Assignment2.daySchoolManager.daySchools.length; x++) {
                        if (Assignment2.daySchoolManager.daySchools[x].getDate().equals(DaySchoolDate)) {
                            for (int y = 0; y < Assignment2.daySchoolManager.daySchools[x].sessions.length; y++) {
                                if (Assignment2.daySchoolManager.daySchools[x].sessions[y].getTime().equals(SessionTime)) {
                                    Assignment2.daySchoolManager.daySchools[x].sessions[y].addStudent(entityIDTextBox.getText());
                                    dispose();
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
