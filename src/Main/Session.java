package Main;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Session {
    private final int sessionID;
    private final LocalTime time;
    private String tutorID = generateTutorId();
    private String[] studentIDs = generateStudents();

    public Session(int sessionID, LocalTime time, String tutorID){
        this.sessionID = sessionID;
        this.time = time;
        this.tutorID = tutorID;
        generateStudents();
        generateTutorId();
    }

    public int getID(){
        return this.sessionID;
    }
    public LocalTime getTime() {
        return this.time;
    }
    public String getTutorID(){
        return this.tutorID;
    }
    public String[] getStudentIDs(){
        return this.studentIDs;
    }
    public void removeStudentByID(String studentID){
        List<String> list = new ArrayList<>();
        for (String s: studentIDs){
            if (!s.equals(studentID)) list.add(s);
        }
        studentIDs = new String[list.size()];
        studentIDs = list.toArray(studentIDs);
    }
    public void addStudent(String newStudentID) {
        if (!Arrays.asList(studentIDs).contains(newStudentID)) {
            studentIDs = Arrays.copyOf(studentIDs, studentIDs.length + 1);
            studentIDs[studentIDs.length - 1] = newStudentID;
        }
    }
    public void setTutor(String newTutorID){
        this.tutorID = newTutorID;
    }

    private String[] generateStudents(){
        Random rand = new Random();
        String[] studentIDs = {};
        int count = rand.nextInt(Assignment2.entityManager.students.length) +1;
        for (int i =0; i <count; i++){
            studentIDs = Arrays.copyOf(studentIDs, studentIDs.length +1);
            studentIDs[i] = Assignment2.entityManager.students[i].getID();
        }
        return studentIDs;
    }
    private String generateTutorId(){
        Random rand = new Random();
        int count = rand.nextInt(Assignment2.entityManager.tutors.length-1);
        return Assignment2.entityManager.getTutorByID("T00" + Integer.toString(count +1)).getID();
    }
}
