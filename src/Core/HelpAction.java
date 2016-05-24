package Core;

import javax.swing.border.Border;

public abstract class HelpAction {
    public Border DefaultBorder;

    public HelpAction(){

    }

    public HelpAction(Border defaultBorder){
        this.DefaultBorder = defaultBorder;
    }

    public abstract void doAction();
    public abstract void undoAction();
}

