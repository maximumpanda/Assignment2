package Forms;

import Main.Assignment2;
import Main.Tutor;

import javax.swing.DefaultListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.format.DateTimeFormatter;

class TutorsForm extends EntityInspectFramework {

    public TutorsForm(){
        this.initializeEntityInspectFramework();
        configure();
    }

    private void configure(){
        this.entityDetailsPanel.remove(specialNeedsLabelStatic);
        this.entityDetailsPanel.remove(specialNeedsLabel);
        this.sessionDetailsPanel.remove(sessionTutorLabelStatic);
        this.sessionDetailsPanel.remove(sessionTutorLabel);
    }

    @Override
    public void populateEntityList() {
        DefaultListModel<String> listValues = new DefaultListModel<>();
        for (Tutor tutor: Assignment2.entityManager.tutors){
            listValues.addElement(tutor.getName());
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
                    for (Tutor tutor : Assignment2.entityManager.tutors) {
                        if (tutor.getID().contains(IDPartial)) listModel.addElement(tutor.getName());
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

                    for (Tutor tutor : Assignment2.entityManager.tutors) {
                        listModel.addElement(tutor.getName());
                    }
                } else {
                    for (Tutor tutor : Assignment2.entityManager.tutors) {
                        if (tutor.getID().contains(IDPartial)) {
                            listModel.addElement(tutor.getName());
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
                if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) || c == KeyEvent.VK_T)) {
                    e.consume();
                }
            }
        });
    }

    @Override
    public void listListener() {
        entityList.addListSelectionListener(e -> {
            if (entityList.getSelectedValue() != null) {
                Tutor tempTutor = Assignment2.entityManager.getTutorByName(entityList.getSelectedValue());
                this.entityIDLabel.setText(tempTutor.getID());
                this.nameLabel.setText(tempTutor.getName());
                String[] splitAddress = tempTutor.getAddress().split(", ");
                this.addressStreetLabel.setText(splitAddress[0] + ", " + splitAddress[1]);
                this.addressCityLabel.setText(splitAddress[3] + ", " + splitAddress[4]);
                this.addressCountryLabel.setText(splitAddress[5]);
                DefaultListModel<String> listValues = new DefaultListModel<>();
                for (int x = 0; x < Assignment2.daySchoolManager.daySchools.length; x++){
                    for (int y =0; y < 6; y++){
                        if (Assignment2.daySchoolManager.daySchools[x].sessions[y].getTutorID().equals(tempTutor.getID())){
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
        if (this.editEntityForm == null || !this.editEntityForm.isVisible()) {
            editEntityForm = new AddUpdateTutorForm();
            editEntityForm.setVisible(true);
        }
    }

    @Override
    public void editEntityButtonFunction() {
        if (this.editEntityForm == null || !this.editEntityForm.isVisible()) {
            editEntityForm = new AddUpdateTutorForm(entityIDLabel.getText().replace("Tutor ID: ", ""));
            editEntityForm.setVisible(true);
        }
    }

    @Override
    DefaultListModel<String> windowFocusGainedUpdateList() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Tutor tutor : Assignment2.entityManager.tutors){
            listModel.addElement(tutor.getName());
        }
        return listModel;
    }
}
