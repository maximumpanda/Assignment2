package Forms;

import Forms.utility.WhiteGreyCellRenderer;
import Core.Assignment2;
import Core.HelpEntry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.CardLayout;
import java.awt.Point;
import java.util.ArrayList;

abstract class HelpFramework extends JFrame {

    private JPanel cards;
    JList<String> questionsList;
    JPanel answerPanel;
    JScrollPane questionPanel;
    private JButton backBtn;
    private JTextPane answerTextPane;
    private final CardLayout cardsLayout = (CardLayout) cards.getLayout();

    final java.util.List<HelpEntry> helpEntries = new ArrayList<>();

    private int selectedIndex;

    abstract void generateHelpEntries();

    abstract void populateQuestionsList();

    void setupForm() {
        setContentPane(cards);
        this.pack();
        Point location = Assignment2.Form1.getLocation();
        this.setLocation((location.x + Assignment2.Form1.getWidth()) - 14, location.y);
        this.answerTextPane.setContentType("text/html");
        this.questionsList.setCellRenderer(new WhiteGreyCellRenderer());
        generateHelpEntries();
        populateQuestionsList();
        listListeners();
        buttonListeners();
    }

    private void listListeners() {
        this.questionsList.addListSelectionListener(e -> {
            this.selectedIndex = questionsList.getSelectedIndex();
            if (selectedIndex != -1) {
                cardsLayout.last(cards);
                answerTextPane.setText(helpEntries.get(selectedIndex).answer);
                answerTextPane.setCaretPosition(0);
                helpEntries.get(selectedIndex).action.doAction();
            }
        });
    }

    private void buttonListeners(){
        this.backBtn.addActionListener(e->{
            this.helpEntries.get(this.selectedIndex).action.undoAction();
            this.cardsLayout.first(cards);
            answerTextPane.setText("");
            this.questionsList.clearSelection();
        });
    }
}
