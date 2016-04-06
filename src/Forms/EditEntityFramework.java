package Forms;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

abstract class EditEntityFramework extends JFrame {
    JTextField entityIDTextBox;
    JTextField entityNameTextBox;
    JTextArea entityAddressArea;
    JCheckBox studentHasSpecialNeedsCheckBox;
    JButton buttonOK;
    private JButton buttonCancel;
    JLabel errorLabel;
    private JPanel panel1;
    JPanel entityDetailsPanel;
    JLabel entityIDLabel;

    void setupForm() {
        setContentPane(panel1);
        pack();
        this.setLocationRelativeTo(null);
        this.errorLabel.setVisible(false);
        buttonOKListener();
        buttonCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
    }

    abstract void buttonOKListener();
}
