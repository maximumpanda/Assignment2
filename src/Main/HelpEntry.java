package Main;

public class HelpEntry {
    public static int id;
    public final String question;
    public final String answer;
    public final HelpAction action;

    public HelpEntry(String question, String answer, HelpAction action){
        this.question = String.format("<html><div WIDTH=%d>%s</div><html>", 215, question);
        this.answer = String.format("<html><div><b>%s</b><div WIDTH=%d>%s</div></div><html>",question , 2, answer);
        this.action = action;
    }
}
