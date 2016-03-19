package Main;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Random;

public class Session {
    private int SessionID;
    private LocalTime Time;
    private String TutorID = GenerateTutorId();
    private String[] StudentIDs = GenerateStudents();

    public Session(){}

    public Session(int sessionID, LocalTime time, String tutorID){
        this.SessionID = sessionID;
        this.Time = time;
        this.TutorID = tutorID;
        GenerateStudents();
        GenerateTutorId();
    }

    int GetSessionID (){
        return this.SessionID;
    }

    public LocalTime GetTime() {
        return this.Time;
    }
    public String GetTutorID(){
        return this.TutorID;
    }
    public String[] GetStudentIDs(){
        return this.StudentIDs;
    }
    void AddStudent(String newStudentID) {
        StudentIDs = Arrays.copyOf(StudentIDs, StudentIDs.length + 1);
        StudentIDs[StudentIDs.length - 1] = newStudentID;
    }
    void SetTutor(String newTutorID){
        this.TutorID = newTutorID;
    }

    private String[] GenerateStudents(){
        Random rand = new Random();
        String[] studentIDs = {};
        int count = rand.nextInt(Assignment2.EntityManager.Students.length) +1;
        for (int i =0; i <count; i++){
            studentIDs = Arrays.copyOf(studentIDs, studentIDs.length +1);
            studentIDs[i] = Assignment2.EntityManager.Students[i].GetID();
        }
        return studentIDs;
    }

    private String GenerateTutorId(){
        Random rand = new Random();
        int count = rand.nextInt(Assignment2.EntityManager.Tutors.length-1);
        return Assignment2.EntityManager.FindTutorByID("T00" + Integer.toString(count +1)).GetID();
    }
}
