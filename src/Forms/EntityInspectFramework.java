package Forms;

import Main.Assignment2;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by steven-pc on 3/20/2016.
 */
public abstract class EntityInspectFramework extends JFrame {
    public JPanel panel1;
    public JList EntityList;
    public JLabel AddressStreetLabel;
    public JLabel AddressCityLabel;
    public JLabel AddressCountryLabel;
    public JLabel EntityIDLabel;
    public JLabel NameLabel;
    public JLabel SpecialNeedsLabel;
    public JList SessionList;
    public JLabel SessionDateLabel;
    public JLabel SessionTimeLabel;
    public JLabel SessionThemeLabel;
    public JLabel SessionTutorLabel;
    public JFormattedTextField SearchTextBox;
    public JButton AddEntityBtn;
    public JLabel SpecialNeedsLabelStatic;
    public JLabel SessionTutorLabelStatic;
    public JButton EditEntityBtn;
    public JPanel EntityDetailsPanel;
    public JPanel SessionDetailsPanel;

    public void InitializeEntityInspectFramework(){
        setContentPane(panel1);
        pack();
        this.setLocationRelativeTo(null);
        AddEntityBtn.setMargin(new java.awt.Insets(1,1,1,1));
        EntityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        SessionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        PopulateEntityList();
        ListListener();
        TextBoxListeners();
        SelectionListener();
        AddEntityBtnListener();
        EditEntityBtnListener();
        AdditionalListeners();
        EntityList.setSelectedIndex(0);
        SessionList.setSelectedIndex(0);
    }

    public abstract void AdditionalListeners();

    public abstract void PopulateEntityList();

    public abstract void TextBoxListeners();

    public abstract void ListListener();

    public abstract void AddEntityButtonFunction();

    public abstract void EditEntityButtonFunction();

    private void SelectionListener(){
        SessionList.addListSelectionListener(e -> {
            int index = SessionList.getSelectedIndex();
            if (index == -1){
                index = 0;
                SessionList.setSelectedIndex(index);
            }
            ListModel model = SessionList.getModel();
            if (model.getSize() >= index +1) {
                String text = model.getElementAt(index).toString();
                String[] dateSplit = text.split(" ");
                LocalDate date = LocalDate.parse(dateSplit[0], DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                String[] timeSplit = dateSplit[1].split(":");
                LocalTime time = LocalTime.of(Integer.parseInt(timeSplit[0]), Integer.parseInt(timeSplit[1]));
                this.SessionDateLabel.setText(dateSplit[0]);
                this.SessionTimeLabel.setText(dateSplit[1]);
                int daySchoolIndex = -1;

                for (int x = 0; x < Assignment2.DaySchoolManager.DaySchools.length; x++) {
                    LocalDate tempDate = Assignment2.DaySchoolManager.DaySchools[x].GetDate();
                    if (tempDate.getDayOfMonth() == date.getDayOfMonth() && tempDate.getMonth() == date.getMonth() && tempDate.getYear() == date.getYear()) {
                        daySchoolIndex = x;
                        break;
                    }
                }
                if (daySchoolIndex != -1) {
                    int sessionIndex = -1;
                    for (int y = 0; y < Assignment2.DaySchoolManager.DaySchools[daySchoolIndex].Sessions.length; y++) {
                        if (Assignment2.DaySchoolManager.DaySchools[daySchoolIndex].Sessions[y].GetTime() == time) {
                            sessionIndex = y;
                            break;
                        }
                    }
                    if (sessionIndex != -1) {
                        this.SessionThemeLabel.setText(Assignment2.DaySchoolManager.DaySchools[daySchoolIndex].GetTheme());
                        this.SessionTutorLabel.setText(Assignment2.EntityManager.FindTutorByID(Assignment2.DaySchoolManager.DaySchools[daySchoolIndex].Sessions[sessionIndex].GetTutorID()).GetName());
                    } else {
                        this.SessionDateLabel.setText("error");
                        this.SessionTimeLabel.setText("error");
                        this.SessionThemeLabel.setText("error");
                        this.SessionTutorLabel.setText("error");
                    }
                } else {
                    this.SessionDateLabel.setText("error");
                    this.SessionTimeLabel.setText("error");
                    this.SessionThemeLabel.setText("error");
                    this.SessionTutorLabel.setText("error");
                }
            }
        });

    }

    private void AddEntityBtnListener(){
        AddEntityBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AddEntityButtonFunction();
            }
        });
    }

    private void EditEntityBtnListener(){
        EditEntityBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {EditEntityButtonFunction();}
        });
    }

}
