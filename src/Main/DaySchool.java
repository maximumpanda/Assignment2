package Main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public class DaySchool {
    public Session[] Sessions = new Session[]{};
    private String Theme;
    private LocalDate Date;

    public DaySchool() {
        GenerateSessions();
    }

    public DaySchool(String theme, LocalDate date) {
        this.Theme = theme;
        this.Date = date;
        GenerateSessions();
    }

    String[] GetStudentsFromSession(int sessionID) {
        for (Session session : Sessions) {
            if (sessionID == session.GetSessionID()) {
                return session.GetStudentIDs();
            }
        }
        return null;
    }

    public String GetTheme() {
        return this.Theme;
    }

    public LocalDate GetDate() {
        return this.Date;
    }

    int GetSessionIDByTime(LocalDateTime time) {
        for (Session session : Sessions) {
            if (session.GetTime().getHour() == time.getHour() && session.GetTime().getMinute() == time.getMinute()) {
                return session.GetSessionID();
            }
        }
        return -1;
    }

    private void GenerateSessions() {
        LocalTime time = LocalTime.of(8, 0);
        for (int i = 0; i <= 6; i++) {
            Sessions = Arrays.copyOf(Sessions, Sessions.length + 1);
            Sessions[Sessions.length - 1] = new Session(i, LocalTime.of(time.getHour() + i, 0), Assignment2.EntityManager.Tutors[i % 3].GetID());
        }
    }
}
