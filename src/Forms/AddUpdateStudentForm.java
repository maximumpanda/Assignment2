package Forms;

import Main.Assignment2;
import Main.Student;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddUpdateStudentForm extends EditEntityFramework {

    public AddUpdateStudentForm(){
        this.EntityIDLabel.setText("Student ID: S00" + (Assignment2.EntityManager.Students.length + 1));
        SetupForm();
    }

    public AddUpdateStudentForm(String id){
        SetupForm();
        Student workStudent = Assignment2.EntityManager.FindStudentByID(id);
        this.EntityIDLabel.setText("Student ID: " + workStudent.GetID());
        this.EntityNameTextBox.setText(workStudent.GetName());
        this.EntityAddressArea.setText(workStudent.GetAddress());
    }

    @Override
    public void ButtonOKListener() {
        buttonOK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!EntityNameTextBox.getText().equals("") && !EntityAddressArea.getText().equals("")){
                    Student newStudent = new Student(EntityIDLabel.getText().replace("Student ID: ", ""),
                            EntityNameTextBox.getText(),
                            EntityAddressArea.getText(),
                            StudentHasSpecialNeedsCheckBox.isSelected());
                    Assignment2.EntityManager.AddUpdateStudent(newStudent);
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
