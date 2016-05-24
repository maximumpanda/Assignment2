package Core;

import java.time.LocalDate;
import java.util.Arrays;

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
}
