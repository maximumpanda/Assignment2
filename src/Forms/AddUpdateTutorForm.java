package Forms;

import Core.Assignment2;
import Core.Tutor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class AddUpdateTutorForm extends EditEntityFramework {

    public AddUpdateTutorForm(){
        this.entityDetailsPanel.remove(this.entityIDTextBox);
        this.entityIDLabel.setText("Tutor ID: T00" + (Assignment2.entityManager.tutors.length));
        setupForm();
        configure();
    }

    public AddUpdateTutorForm(String id)
    {
        this.entityDetailsPanel.remove(this.entityIDTextBox);
        setupForm();
        Tutor workTutor = Assignment2.entityManager.getTutorByID(id);
        this.entityIDLabel.setText("Tutor ID: " + workTutor.getID());
        this.entityNameTextBox.setText(workTutor.getName());
        this.entityAddressArea.setText(workTutor.getAddress());
        configure();
    }

    private void configure(){
        this.entityDetailsPanel.remove(studentHasSpecialNeedsCheckBox);
    }

    @Override
    public void buttonOKListener() {
        buttonOK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!entityNameTextBox.getText().equals("") && !entityAddressArea.getText().equals("")){
                    Tutor newTutor = new Tutor(
                            entityNameTextBox.getText(),
                            entityAddressArea.getText());
                    Assignment2.entityManager.addUpdateTutor(newTutor);
                    dispose();
                }
                else{
                    errorLabel.setText("Complete name and address");
                    errorLabel.setVisible(true);
                }
            }
        });
    }
}
