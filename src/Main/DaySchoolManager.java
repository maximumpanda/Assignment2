package Main;

import java.time.LocalDate;
import java.util.Arrays;

public class DaySchoolManager {
    public DaySchool[] DaySchools = new DaySchool[]{};

    public DaySchool GetDaySchoolByDate(LocalDate date){
        for (DaySchool daySchool: DaySchools){
            if (daySchool.GetDate().equals(date)){
                return daySchool;
            }
        }
        return null;
    }

    public DaySchool GetDaySchoolByTheme(String theme) {
        for (DaySchool daySchool: DaySchools){
            if (daySchool.GetTheme().equals(theme)){
                return daySchool;
            }
        }
        return null;
    }

    public void AddDaySchool(DaySchool newDaySchool){
        DaySchools = Arrays.copyOf(DaySchools, DaySchools.length +1);
        DaySchools[DaySchools.length -1] = newDaySchool;
    }
}
