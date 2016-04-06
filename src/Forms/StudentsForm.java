package Forms;

import Main.Assignment2;
import Main.Student;

import javax.swing.DefaultListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

class StudentsForm extends EntityInspectFramework {

    private EditEntityFramework addUpdateStudentForm = null;

    public StudentsForm(){
        this.initializeEntityInspectFramework();
    }

    @Override
    public void populateEntityList() {
        DefaultListModel<String> listValues = new DefaultListModel<>();
        for (Student student: Assignment2.entityManager.students){
            listValues.addElement(student.getName());
        }
        entityList.setModel(listValues);
    }

    @Override
    public void textBoxListeners() {
        searchTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String IDPartial = searchTextBox.getText();
                DefaultListModel<String> listModel = new DefaultListModel<>();
                if (IDPartial.length() >= 1) {
                    for (Student student : Assignment2.entityManager.students) {
                        if (student.getID().contains(IDPartial)) listModel.addElement(student.getName());
                    }
                }
                entityList.setModel(listModel);
                entityList.repaint();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String IDPartial = searchTextBox.getText();
                DefaultListModel<String> listModel = new DefaultListModel<>();
                if (IDPartial.length() == 0) {
                    for (Student student : Assignment2.entityManager.students) {
                        listModel.addElement(student.getName());
                    }
                } else {
                    for (Student student : Assignment2.entityManager.students) {
                        if (student.getID().contains(IDPartial)) {
                            listModel.addElement(student.getName());
                        }
                    }
                }
                entityList.setModel(listModel);
                entityList.repaint();
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
        searchTextBox.addKeyListener(new KeyAdapter() {
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
    public void listListener() {
        entityList.addListSelectionListener(e -> {
            if (entityList.getSelectedValue() != null) {
                Student tempStudent = Assignment2.entityManager.getStudentByName(entityList.getSelectedValue());
                this.entityIDLabel.setText(tempStudent.getID());
                this.nameLabel.setText(tempStudent.getName());
                String[] splitAddress = tempStudent.getAddress().split(", ");
                this.addressStreetLabel.setText(splitAddress[0] + ", " + splitAddress[1]);
                this.addressCityLabel.setText(splitAddress[3] + ", " + splitAddress[4]);
                this.addressCountryLabel.setText(splitAddress[5]);
                if (tempStudent.GetSpecialNeedsStatus()) this.specialNeedsLabel.setText("Yes");
                else this.specialNeedsLabel.setText("No");
                DefaultListModel<String> listValues = new DefaultListModel<>();
                for (int x = 0; x < Assignment2.daySchoolManager.daySchools.length; x++) {
                    for (int y = 0; y < 6; y++) {
                        if (arrayContainsInt(Assignment2.daySchoolManager.daySchools[x].sessions[y].getStudentIDs(), tempStudent.getID())) {
                            listValues.addElement(Assignment2.daySchoolManager.daySchools[x].getDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) +
                                    " " + Assignment2.daySchoolManager.daySchools[x].sessions[y].getTime());
                        }
                    }
                }
                sessionList.setModel(listValues);
                sessionList.repaint();
                this.entitySessionsCountLabel.setText(Integer.toString(sessionList.getModel().getSize()));
            }
        });
    }

    @Override
    public void addEntityButtonFunction() {
        if (this.addUpdateStudentForm == null || !this.addUpdateStudentForm.isVisible()){
            addUpdateStudentForm = new AddUpdateStudentForm();
            addUpdateStudentForm.setVisible(true);
        }

    }

    @Override
    public void editEntityButtonFunction() {
        if (this.addUpdateStudentForm == null || !this.addUpdateStudentForm.isVisible()){
            addUpdateStudentForm = new AddUpdateStudentForm(entityIDLabel.getText());
            addUpdateStudentForm.setVisible(true);
        }
    }

    @Override
    DefaultListModel<String> windowFocusGainedUpdateList() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Student student : Assignment2.entityManager.students){
            listModel.addElement(student.getName());
        }
        return listModel;
    }

    private boolean arrayContainsInt(final String[] array, String check){
        return Arrays.stream(array).anyMatch(i -> i.equals(check));
    }
}