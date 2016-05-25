package Forms.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Standardization {

    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String FormatDateTime(LocalDate unformatted){
        return unformatted.format(dateFormatter);
    }

    public static LocalDate GenerateLocalDate(String[] dateValues){
        return LocalDate.parse(dateValues[2] + "-" + dateValues[1] + "-" + dateValues[0]);
    }
}
