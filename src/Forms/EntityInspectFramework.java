package Forms;

import Forms.utility.WhiteGreyCellRenderer;
import Core.Assignment2;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

abstract class EntityInspectFramework extends JFrame {
    private JPanel panel1;
    JList<String> entityList;
    JLabel addressStreetLabel;
    JLabel addressCityLabel;
    JLabel addressCountryLabel;
    JLabel entityIDLabel;
    JLabel nameLabel;
    JLabel specialNeedsLabel;
    JList<String> sessionList;
    private JLabel sessionDateLabel;
    private JLabel sessionTimeLabel;
    private JLabel sessionThemeLabel;
    JLabel sessionTutorLabel;
    JFormattedTextField searchTextBox;
    private JButton addEntityBtn;
    JLabel specialNeedsLabelStatic;
    JLabel sessionTutorLabelStatic;
    private JButton editEntityBtn;
    JPanel entityDetailsPanel;
    JPanel sessionDetailsPanel;
    JLabel entitySessionsCountLabel;

    EditEntityFramework editEntityForm = null;

    void initializeEntityInspectFramework(){
        setContentPane(panel1);
        pack();
        this.setLocationRelativeTo(null);
        addEntityBtn.setMargin(new java.awt.Insets(1,1,1,1));
        entityList.setCellRenderer(new WhiteGreyCellRenderer());
        sessionList.setCellRenderer(new WhiteGreyCellRenderer());
        entityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sessionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        populateEntityList();
        listListener();
        textBoxListeners();
        selectionListener();
        AddEntityBtnListener();
        EditEntityBtnListener();
        windowFocusListener();
        entityList.setSelectedIndex(0);
        sessionList.setSelectedIndex(0);
    }

    abstract void populateEntityList();

    abstract void textBoxListeners();

    abstract void listListener();

    abstract void addEntityButtonFunction();

    abstract void editEntityButtonFunction();

    abstract DefaultListModel<String> windowFocusGainedUpdateList();

    private void windowFocusListener(){
        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                if (editEntityForm == null || !editEntityForm.isVisible()){
                    int indexPosition = entityList.getSelectedIndex();
                    entityList.setModel(windowFocusGainedUpdateList());
                    entityList.setSelectedIndex(indexPosition);
                }
                else {
                    editEntityForm.toFront();
                }
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });
    }

    private void selectionListener(){
        sessionList.addListSelectionListener(e -> {
            int index = sessionList.getSelectedIndex();
            if (index == -1){
                index = 0;
                sessionList.setSelectedIndex(index);
            }
            ListModel model = sessionList.getModel();
            if (model.getSize() >= index +1) {
                String text = model.getElementAt(index).toString();
                String[] dateSplit = text.split(" ");
                LocalDate date = LocalDate.parse(dateSplit[0], DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                String[] timeSplit = dateSplit[1].split(":");
                LocalTime time = LocalTime.of(Integer.parseInt(timeSplit[0]), Integer.parseInt(timeSplit[1]));
                this.sessionDateLabel.setText(dateSplit[0]);
                this.sessionTimeLabel.setText(dateSplit[1]);
                int daySchoolIndex = -1;

                for (int x = 0; x < Assignment2.daySchoolManager.daySchools.length; x++) {
                    LocalDate tempDate = Assignment2.daySchoolManager.daySchools[x].getDate();
                    if (tempDate.getDayOfMonth() == date.getDayOfMonth() && tempDate.getMonth() == date.getMonth() && tempDate.getYear() == date.getYear()) {
                        daySchoolIndex = x;
                        break;
                    }
                }
                if (daySchoolIndex != -1) {
                    int sessionIndex = -1;
                    for (int y = 0; y < Assignment2.daySchoolManager.daySchools[daySchoolIndex].sessions.length; y++) {
                        if (Assignment2.daySchoolManager.daySchools[daySchoolIndex].sessions[y].getTime() == time) {
                            sessionIndex = y;
                            break;
                        }
                    }
                    if (sessionIndex != -1) {
                        this.sessionThemeLabel.setText(Assignment2.daySchoolManager.daySchools[daySchoolIndex].getTheme());
                        this.sessionTutorLabel.setText(Assignment2.entityManager.getTutorByID(Assignment2.daySchoolManager.daySchools[daySchoolIndex].sessions[sessionIndex].getTutorID()).getName());
                    } else {
                        this.sessionDateLabel.setText("error");
                        this.sessionTimeLabel.setText("error");
                        this.sessionThemeLabel.setText("error");
                        this.sessionTutorLabel.setText("error");
                    }
                } else {
                    this.sessionDateLabel.setText("error");
                    this.sessionTimeLabel.setText("error");
                    this.sessionThemeLabel.setText("error");
                    this.sessionTutorLabel.setText("error");
                }
            }
        });

    }

    private void AddEntityBtnListener(){
        addEntityBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addEntityButtonFunction();
            }
        });
    }

    private void EditEntityBtnListener(){
        editEntityBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editEntityButtonFunction();}
        });
    }

}
