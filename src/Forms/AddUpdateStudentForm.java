package Forms;

import Core.Assignment2;
import Core.Student;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class AddUpdateStudentForm extends EditEntityFramework {

    public AddUpdateStudentForm(){
        this.entityDetailsPanel.remove(this.entityIDTextBox);
        this.entityIDLabel.setText("Student ID: S00" + (Assignment2.entityManager.students.length));
        setupForm();
    }

    public AddUpdateStudentForm(String id){
        this.entityDetailsPanel.remove(this.entityIDTextBox);
        setupForm();
        Student workStudent = Assignment2.entityManager.getStudentByID(id);
        this.entityIDLabel.setText("Student ID: " + workStudent.getID());
        this.entityNameTextBox.setText(workStudent.getName());
        this.entityAddressArea.setText(workStudent.getAddress());
        this.studentHasSpecialNeedsCheckBox.setSelected(workStudent.GetSpecialNeedsStatus());
    }

    @Override
    public void buttonOKListener() {
        buttonOK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!entityNameTextBox.getText().equals("") && !entityAddressArea.getText().equals("")){
                    Student newStudent = new Student(
                            entityNameTextBox.getText(),
                            entityAddressArea.getText(),
                            studentHasSpecialNeedsCheckBox.isSelected());
                    Assignment2.entityManager.addUpdateStudent(newStudent);
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
