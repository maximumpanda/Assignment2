package Main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public class DaySchool {
    public Session[] sessions = new Session[]{};
    private final String theme;
    private final LocalDate date;

    public DaySchool(String theme, LocalDate date) {
        this.theme = theme;
        this.date = date;
        generateSessions();
    }
    String[] getStudentsFromSession(int sessionID) {
        for (Session session : this.sessions) {
            if (sessionID == session.getID()) {
                return session.getStudentIDs();
            }
        }
        return null;
    }
    public String getTheme() {
        return this.theme;
    }
    public LocalDate getDate() {
        return this.date;
    }
    int getSessionIDByTime(LocalDateTime time) {
        for (Session session : this.sessions) {
            if (session.getTime().getHour() == time.getHour() && session.getTime().getMinute() == time.getMinute()) {
                return session.getID();
            }
        }
        return -1;
    }
    private void generateSessions() {
        LocalTime time = LocalTime.of(8, 0);
        for (int i = 1; i <= 6; i++) {
            this.sessions = Arrays.copyOf(this.sessions, this.sessions.length + 1);
            this.sessions[this.sessions.length - 1] = new Session(i, LocalTime.of(time.getHour() + i, 0), Assignment2.entityManager.tutors[i % 3].getID());
        }
    }
}
