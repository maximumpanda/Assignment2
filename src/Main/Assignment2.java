package Main;

import Forms.Form1;

import java.time.LocalDate;

public class Assignment2 {
    public static DaySchoolManager DaySchoolManager = new DaySchoolManager();
    public static EntityManager EntityManager = new EntityManager();

    public static void main(String[] args){
        GenerateEntities();
        GenerateDaySchools();
        Form1 fm = new Form1();

    }

    private static void GenerateDaySchools() {
        String[] ThemeValues = {"pirate", "ninja", "predator",
                                "alien", "schwartzeneger", "competative weight guessing",
                                "critical thinking for those less inclined"};
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            DaySchoolManager.AddDaySchool(new DaySchool(ThemeValues[i], today.plusDays(i)));
        }
    }
    private static void GenerateEntities(){
        String[] names = {"Ricky Gervais", "Syndey Show", "Joe Bruno", "Ryan Tomberg", "neil degrasse", "michael Feehan"};
        String[] addresses = {"old farm, givons grove, leatherhead, surrey, United Kingdom, KT229lz"};
        int IDCount = 1;
        for (int i = 1; i <=3;i++){
            EntityManager.AddStudent(new Student("S00" + i, names[i-1], addresses[0], false));
            EntityManager.AddTutor(new Tutor("T00" + Integer.toString(i) , names[i+2], addresses[0]));
        }
    }
}
