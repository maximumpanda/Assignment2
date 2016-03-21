package Forms;

import Main.Assignment2;
import Main.Student;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class StudentsForm extends EntityInspectFramework {

    private AddUpdateStudentForm addUpdateStudentForm = null;

    public StudentsForm(){
        this.InitializeEntityInspectFramework();
    }

    @Override
    public void AdditionalListeners() {
        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                if (addUpdateStudentForm == null || !addUpdateStudentForm.isVisible()){
                    DefaultListModel<String> listModel = new DefaultListModel<String>();
                    for (Student student : Assignment2.EntityManager.Students){
                        listModel.addElement(student.GetName());
                    }
                    EntityList.setModel(listModel);
                }
                else {
                    addUpdateStudentForm.toFront();
                }
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });
    }

    @Override
    public void PopulateEntityList() {
        DefaultListModel<String> listValues = new DefaultListModel<String>();
        for (Student student: Assignment2.EntityManager.Students){
            listValues.addElement(student.GetName());
        }
        EntityList.setModel(listValues);
    }

    @Override
    public void TextBoxListeners() {
        SearchTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String IDPartial = SearchTextBox.getText();
                DefaultListModel listModel = new DefaultListModel();
                if (IDPartial.length() >= 1) {
                    for (Student student : Assignment2.EntityManager.Students) {
                        if (student.GetID().contains(IDPartial)) listModel.addElement(student.GetName());
                    }
                }
                EntityList.setModel(listModel);
                EntityList.repaint();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String IDPartial = SearchTextBox.getText();
                DefaultListModel listModel = new DefaultListModel();
                if (IDPartial.length() == 0) {
                    for (Student student : Assignment2.EntityManager.Students) {
                        listModel.addElement(student.GetName());
                    }
                } else {
                    for (Student student : Assignment2.EntityManager.Students) {
                        if (student.GetID().contains(IDPartial)) {
                            listModel.addElement(student.GetName());
                        }
                    }
                }
                EntityList.setModel(listModel);
                EntityList.repaint();
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
        SearchTextBox.addKeyListener(new KeyAdapter() {
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
    public void ListListener() {
        EntityList.addListSelectionListener(e -> {
            if (EntityList.getSelectedValue() != null) {
                Student tempStudent = Assignment2.EntityManager.FindStudentByName(EntityList.getSelectedValue().toString());
                this.EntityIDLabel.setText(tempStudent.GetID());
                this.NameLabel.setText(tempStudent.GetName());
                String[] splitAddress = tempStudent.GetAddress().split(", ");
                this.AddressStreetLabel.setText(splitAddress[0] + ", " + splitAddress[1]);
                this.AddressCityLabel.setText(splitAddress[3] + ", " + splitAddress[4]);
                this.AddressCountryLabel.setText(splitAddress[5]);
                if (tempStudent.GetSpecialNeedsStatus()) this.SpecialNeedsLabel.setText("Yes");
                else this.SpecialNeedsLabel.setText("No");
                DefaultListModel listValues = new DefaultListModel();
                for (int x = 0; x < Assignment2.DaySchoolManager.DaySchools.length; x++) {
                    for (int y = 0; y < 7; y++) {
                        if (ArrayContainsInt(Assignment2.DaySchoolManager.DaySchools[x].Sessions[y].GetStudentIDs(), tempStudent.GetID())) {
                            listValues.addElement(Assignment2.DaySchoolManager.DaySchools[x].GetDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) +
                                    " " + Assignment2.DaySchoolManager.DaySchools[x].Sessions[y].GetTime());
                        }
                    }
                }
                SessionList.setModel(listValues);
                SessionList.repaint();
            }
        });
    }

    @Override
    public void AddEntityButtonFunction() {
        if (this.addUpdateStudentForm == null || !this.addUpdateStudentForm.isVisible()){
            addUpdateStudentForm = new AddUpdateStudentForm();
            addUpdateStudentForm.setVisible(true);
        }

    }

    @Override
    public void EditEntityButtonFunction() {
        if (this.addUpdateStudentForm == null || !this.addUpdateStudentForm.isVisible()){
            addUpdateStudentForm = new AddUpdateStudentForm(EntityIDLabel.getText());
            addUpdateStudentForm.setVisible(true);
        }
    }

    private boolean ArrayContainsInt(final String[] array, String check){
        return Arrays.stream(array).anyMatch(i -> i.equals(check));
    }
}