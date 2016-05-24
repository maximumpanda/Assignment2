package Forms;

import Core.Assignment2;
import Core.HelpAction;
import Core.HelpEntry;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import java.awt.Color;

class Form1HelpForm extends HelpFramework {

    public Form1HelpForm(){
        setupForm();
    }

    @Override
    void generateHelpEntries() {
        helpEntries.add(
                new HelpEntry(
                "Get a list of students by Day school theme and and session number <u>(Requirement)</u>",
                "<p> Click the search Button and entered the Day School <b>theme</b> and <b>Session number</b></p>" +
                    "<p>Or</p>" +
                    "<p>Filter the Day Schools by theme using the marked Text field, then select the requested session. to the right should show a list of registered students in the order they have registered.</p>",
                new HelpAction(Assignment2.Form1.searchTextBox.getBorder()) {
                    @Override
                    public void undoAction() {
                        Assignment2.Form1.searchTextBox.setBorder(DefaultBorder);
                    }

                    @Override
                    public void doAction() {
                        Assignment2.Form1.searchTextBox.setBorder(BorderFactory.createLineBorder(Color.RED));
                    }
                }));
        helpEntries.add(new HelpEntry(
                "Find the number of sessions a student is registered for <u>(Requirement)</u>",
                "<p>Click the <b>Manage Students</b> button and search for the student by entering their <b>Student ID</b> into the <b>text field</b> (ex: S001).<p>in the student details to the right is a list of sessions, the total count is displayed next to <b>Sessions Total:</b></p>",
                new HelpAction() {
                    @Override
                    public void doAction() {

                    }

                    @Override
                    public void undoAction() {

                    }
                }));
        helpEntries.add(new HelpEntry(
                "Add a Student to a Session <u>(Requirement)</u>",
                "<p>Find the desired session by selecting the Day School it belongs to.</p><p>Click the marked button and Enter the <b>Student ID</b> of the student you want to register to the session</p>",
                new HelpAction(Assignment2.Form1.addStudentBtn.getBorder()) {
                    @Override
                    public void doAction() {
                        Assignment2.Form1.addStudentBtn.setBorder(BorderFactory.createLineBorder(Color.RED));
                    }

                    @Override
                    public void undoAction() {
                        Assignment2.Form1.addStudentBtn.setBorder(DefaultBorder);
                    }
                }
        ));
        helpEntries.add(new HelpEntry(
                "Find if a student has a special need <u>(Requirement)</u>",
                "<p>Click the <b>Manage Students</b> button.</p><p>Find the student by searching for their <b>student ID</b> using the <b>Text Field</b> (example: S001)</p><p>the student's special needs status should be displayed to the right</p>",
                new HelpAction(Assignment2.Form1.studentsBtn.getBorder()) {
                    @Override
                    public void doAction() {
                        Assignment2.Form1.studentsBtn.setBorder(BorderFactory.createLineBorder(Color.RED));
                    }

                    @Override
                    public void undoAction() {
                        Assignment2.Form1.studentsBtn.setBorder(DefaultBorder);
                    }
                }
        ));
        helpEntries.add(new HelpEntry(
                "Find Tutor of a specific session <u>(Requirement)</u>",
                "<p>Find the Session by selecting the appropriate Day school and navigating to the sessions list to the right. when you select the desired session, the Tutor Name will be displayed below</p>",
                new HelpAction() {
                    @Override
                    public void doAction() {

                    }

                    @Override
                    public void undoAction() {

                    }
                }
        ));
        helpEntries.add(new HelpEntry(
                "How to Filter Day Schools by theme or date?",
                "<p>Click the marked Text Box and type in either a <b>theme</b> or a <b>date</b>.</p>",
                new HelpAction(Assignment2.Form1.searchTextBox.getBorder()) {
            @Override
            public void undoAction() {
                Assignment2.Form1.searchTextBox.setBorder(DefaultBorder);
            }

            @Override
            public void doAction() {
                Assignment2.Form1.searchTextBox.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
        }));

    }

    @Override
    void populateQuestionsList() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (HelpEntry entry: helpEntries){
            listModel.addElement(entry.question);
        }
        this.questionsList.setModel(listModel);
    }
}
