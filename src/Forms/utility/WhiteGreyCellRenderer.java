package Forms.utility;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import java.awt.Color;
import java.awt.Component;

public class WhiteGreyCellRenderer extends DefaultListCellRenderer {

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        Component c = super.getListCellRendererComponent(list, value, index,isSelected,cellHasFocus);
        if (index % 2 ==0){
            c.setBackground(Color.white);
        }
        else {
            c.setBackground(new Color(240,240,240));
        }
        return c;
    }
}
