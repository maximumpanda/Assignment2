package Core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DaySchoolManager {

    public DaySchool[] daySchools = new DaySchool[]{};
    public DaySchool getDaySchoolByDate(LocalDate date){
        for (DaySchool daySchool: daySchools){
            if (daySchool.getDate().equals(date)){
                return daySchool;
            }
        }
        return null;
    }
    public DaySchool getDaySchoolByTheme(String theme) {
        for (DaySchool daySchool: daySchools){
            if (daySchool.getTheme().equals(theme)){
                return daySchool;
            }
        }
        return null;
    }
    public void addDaySchool(DaySchool newDaySchool){
        daySchools = Arrays.copyOf(daySchools, daySchools.length +1);
        daySchools[daySchools.length -1] = newDaySchool;
    }
    public List<int[][]> getSessionsByStudentID(String studentID){
        List<int[][]> result = new ArrayList<>();
        for (DaySchool daySchool: Assignment2.daySchoolManager.daySchools){
            for (Session session: daySchool.sessions){
                for (String id: session.getStudentIDs()){
                    if (id.equals(studentID)){
                        result.add(new int[][]{{daySchool.id, session.getID()}});
                    }
                }
            }
        }
        return result;
    }
    public void RemoveStudentFromAllSessions(String studentID){
        for (DaySchool daySchool: Assignment2.daySchoolManager.daySchools){
            for (Session session: daySchool.sessions){
                for (String id: session.getStudentIDs()){
                    if (id.equals(studentID)){
                        session.removeStudentByID("S003");
                    }
                }
            }
        }
    }
}
