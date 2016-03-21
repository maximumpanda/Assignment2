package Forms;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by steven-pc on 3/20/2016.
 */
public abstract class EditEntityFramework extends JFrame {
    public JLabel EntityIDLabel;
    public JTextField EntityNameTextBox;
    public JTextArea EntityAddressArea;
    public JCheckBox StudentHasSpecialNeedsCheckBox;
    public JButton buttonOK;
    public JButton buttonCancel;
    public JLabel ErrorLabel;
    public JPanel panel1;
    public JPanel EntityDetailsPanel;

    public void SetupForm() {
        setContentPane(panel1);
        pack();
        this.setLocationRelativeTo(null);
        this.ErrorLabel.setVisible(false);
        ButtonOKListener();
        buttonCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
    }

    public abstract void ButtonOKListener();
}
