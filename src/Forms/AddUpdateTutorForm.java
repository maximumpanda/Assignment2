package Forms;

import Main.Assignment2;
import Main.Tutor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by steven-pc on 3/21/2016.
 */
public class AddUpdateTutorForm extends EditEntityFramework {

    public AddUpdateTutorForm(){
        this.EntityIDLabel.setText("Tutor ID: T00" + (Assignment2.EntityManager.Tutors.length + 1));
        SetupForm();
        Configure();
    }

    public AddUpdateTutorForm(String id)
    {
        SetupForm();
        Tutor workTutor = Assignment2.EntityManager.FindTutorByID(id);
        this.EntityIDLabel.setText("Tutor ID: " + workTutor.GetID());
        this.EntityNameTextBox.setText(workTutor.GetName());
        this.EntityAddressArea.setText(workTutor.GetAddress());
        Configure();
    }

    private void Configure(){
        this.EntityDetailsPanel.remove(StudentHasSpecialNeedsCheckBox);
    }

    @Override
    public void ButtonOKListener() {
        buttonOK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!EntityNameTextBox.getText().equals("") && !EntityAddressArea.getText().equals("")){
                    Tutor newTutor = new Tutor("S00" + (Assignment2.EntityManager.Tutors.length + 1),
                            EntityNameTextBox.getText(),
                            EntityAddressArea.getText());
                    Assignment2.EntityManager.AddUpdateTutor(newTutor);
                    dispose();
                }
                else{
                    ErrorLabel.setText("Complete name and address");
                    ErrorLabel.setVisible(true);
                }
            }
        });
    }
}
