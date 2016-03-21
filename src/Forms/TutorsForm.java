package Forms;

import Main.Assignment2;
import Main.Student;
import Main.Tutor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;

/**
 * Created by steven-pc on 3/20/2016.
 */
public class TutorsForm extends EntityInspectFramework {

    private AddUpdateTutorForm addUpdateTutorsForm = null;

    public TutorsForm(){
        this.InitializeEntityInspectFramework();
        Configure();
    }

    private void Configure(){
        this.EntityDetailsPanel.remove(SpecialNeedsLabelStatic);
        this.EntityDetailsPanel.remove(SpecialNeedsLabel);
        this.SessionDetailsPanel.remove(SessionTutorLabelStatic);
        this.SessionDetailsPanel.remove(SessionTutorLabel);
    }

    @Override
    public void AdditionalListeners() {
        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                if (addUpdateTutorsForm == null || !addUpdateTutorsForm.isVisible()){
                    DefaultListModel<String> listModel = new DefaultListModel<String>();
                    for (Tutor tutor : Assignment2.EntityManager.Tutors){
                        listModel.addElement(tutor.GetName());
                    }
                    EntityList.setModel(listModel);
                }
                else {
                    addUpdateTutorsForm.toFront();
                }
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });

    }

    @Override
    public void PopulateEntityList() {
        DefaultListModel listValues = new DefaultListModel();
        for (Tutor tutor: Assignment2.EntityManager.Tutors) listValues.addElement(tutor.GetName());
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
                    for (Tutor tutor : Assignment2.EntityManager.Tutors) {
                        if (tutor.GetID().contains(IDPartial)) listModel.addElement(tutor.GetName());
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

                    for (Tutor tutor : Assignment2.EntityManager.Tutors) {
                        listModel.addElement(tutor.GetName());
                    }
                } else {
                    for (Tutor tutor : Assignment2.EntityManager.Tutors) {
                        if (tutor.GetID().contains(IDPartial)) {
                            listModel.addElement(tutor.GetName());
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
                if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) || c == KeyEvent.VK_T)) {
                    e.consume();
                }
            }
        });
    }

    @Override
    public void ListListener() {
        EntityList.addListSelectionListener(e -> {
            if (EntityList.getSelectedValue() != null) {
                Tutor tempTutor = Assignment2.EntityManager.FindTutorByName(EntityList.getSelectedValue().toString());
                this.EntityIDLabel.setText(tempTutor.GetID());
                this.NameLabel.setText(tempTutor.GetName());
                String[] splitAddress = tempTutor.GetAddress().split(", ");
                this.AddressStreetLabel.setText(splitAddress[0] + ", " + splitAddress[1]);
                this.AddressCityLabel.setText(splitAddress[3] + ", " + splitAddress[4]);
                this.AddressCountryLabel.setText(splitAddress[5]);
                DefaultListModel listValues = new DefaultListModel();
                for (int x = 0; x < Assignment2.DaySchoolManager.DaySchools.length; x++){
                    for (int y =0; y <7; y++){
                        if (Assignment2.DaySchoolManager.DaySchools[x].Sessions[y].GetTutorID().equals(tempTutor.GetID())){
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
        if (this.addUpdateTutorsForm == null || !this.addUpdateTutorsForm.isVisible()) {
            addUpdateTutorsForm = new AddUpdateTutorForm();
            addUpdateTutorsForm.setVisible(true);
        }
    }

    @Override
    public void EditEntityButtonFunction() {
        if (this.addUpdateTutorsForm == null || !this.addUpdateTutorsForm.isVisible()) {
            addUpdateTutorsForm = new AddUpdateTutorForm(EntityIDLabel.getText().replace("Tutor ID: ", ""));
            addUpdateTutorsForm.setVisible(true);
        }
    }
}
