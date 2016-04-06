package Main;

import Forms.MainForm;

import java.time.LocalDate;

public class Assignment2 {
    public static final DaySchoolManager daySchoolManager = new DaySchoolManager();
    public static final EntityManager entityManager = new EntityManager();
    public static MainForm Form1;

    public static void main(String[] args){
        generateEntities();
        generateDaySchools();
        Form1 = new MainForm();

    }

    private static void generateDaySchools() {
        String[] ThemeValues = {"pirate", "ninja", "predator",
                                "alien", "schwartzeneger", "competative weight guessing",
                                "critical thinking for those less inclined"};
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            daySchoolManager.addDaySchool(new DaySchool(ThemeValues[i], today.plusDays(i)));
        }
    }
    private static void generateEntities(){
        String[] names = {"Ricky Gervais", "Syndey Show", "Joe Bruno", "Ryan Tomberg", "neil degrasse", "michael Feehan"};
        String[] addresses = {"old farm, givons grove, leatherhead, surrey, United Kingdom, KT229lz"};
        for (int i = 1; i <=3;i++){
            entityManager.addStudent(new Student("S00" + i, names[i-1], addresses[0], false));
            entityManager.addTutor(new Tutor("T00" + Integer.toString(i) , names[i+2], addresses[0]));
        }
    }
}
