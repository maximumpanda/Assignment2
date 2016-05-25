package Testing;

import Core.Assignment2;
import Core.Student;

import java.util.List;
import java.util.Random;

public class UnitTests {

    public static void Start(){
        System.out.println("----Starting Unit Tests----");
        CreateNewStudent();
        FindStudentByID();
        AddStudentToSession();
        FindStudentSessions();
        RemoveStudentFromAllSessions();
        RemoveStudentFromEntityManager();
        System.out.println("----Testing Complete----");
    }

    private static boolean CreateNewStudent(){
        System.out.println(PadTitle("CreateNewStudent"));
        int CurrentStudentCount = Assignment2.entityManager.students.length;
        Assignment2.entityManager.addStudent(new Student("test", "test1, test2, test3, test4, test5, address", true));
        boolean result = true;
        if (!CheckStudentCount(CurrentStudentCount)) result = false;
        if (!CheckStudentName(CurrentStudentCount)) result = false;
        if (!CheckStudentAddress(CurrentStudentCount)) result = false;
        if (!CheckStudentSpecialNeedsStatus(CurrentStudentCount)) result = false;
        if (!result) {
            System.out.println("Failed: CreateNewStudent");
            return false;
        }
        System.out.println("Success: CreateNewStudent");
        return true;
    }
    private static boolean FindStudentByID(){
        System.out.println(PadTitle("FindStudentByID"));
        Student testStudent = Assignment2.entityManager.getStudentByID("S003");
        if (!testStudent.getName().equals("test")){
            System.out.println("failed: FindStudentByID");
            return false;
        }
        System.out.println("Success: FindStudentByID");
        return true;
    }
    private static boolean AddStudentToSession(){
        System.out.println(PadTitle("AddStudentToSession"));
        boolean result = false;
        Random rand = new Random();
        int dayschool = rand.nextInt(6 +1);
        int session = rand.nextInt(5 +1);
        Assignment2.daySchoolManager.daySchools[dayschool].sessions[session].addStudent("S003");
        String[] ids = Assignment2.daySchoolManager.daySchools[dayschool].sessions[session].getStudentIDs();
        for(String id: ids  ){
            if (id == "S003"){
                result = true;
            }
        }
        if (!result){
            System.out.println("Failed: AddStudentToSession");
            return result;
        }
        System.out.println("Success: AddStudentToSession");
        return result;
    }
    private static boolean FindStudentSessions(){
        System.out.println(PadTitle("FindStudentSessions"));
        List<int[][]> result = Assignment2.daySchoolManager.getSessionsByStudentID("S003");
        if (result.size() != 1){
            System.out.println("Failed: FindStudentSessions");
            return false;
        }
        System.out.println("Success: FindStudentSessions");
        return true;
    }
    private static boolean RemoveStudentFromAllSessions(){
        System.out.println(PadTitle("RemoveStudentFromAllSessions"));
        List<int[][]> sessions = Assignment2.daySchoolManager.getSessionsByStudentID("S003");
        Assignment2.daySchoolManager.RemoveStudentFromAllSessions("S003");
        List<int[][]> newSessions = Assignment2.daySchoolManager.getSessionsByStudentID("S003");
        if (newSessions.size() != 0){
            System.out.println("Failed: RemoveStudentFromAllSessions");
            return false;
        }
        System.out.println("Success: RemoveStudentFromAllSessions");
        return true;
    }
    private static boolean RemoveStudentFromEntityManager(){
        System.out.println(PadTitle("RemoveStudentFromEntityManager"));
        int oldCount = Assignment2.entityManager.students.length;
        Assignment2.entityManager.removeStudentByID("S003");
        int newCount = Assignment2.entityManager.students.length;
        if (newCount != oldCount-1){
            System.out.println("Failed: RemoveStudentFromEntityManager");
            return false;
        }
        System.out.println("Success: RemoveStudentFromEntityManager");
        return true;
    }

    private static boolean CheckStudentCount(int oldStudentCount){
        if (Assignment2.entityManager.students.length == oldStudentCount) {
            System.out.println("failed: new student not added to Students[]");
            return false;
        }
        return true;
    }

    private static boolean CheckStudentName(int location){
        if (!Assignment2.entityManager.students[location].getName().equals("test")){
            System.out.println( "failed: Name check");
            return false;
        }
        return true;
    }

    private static boolean CheckStudentAddress(int location){
        if (!Assignment2.entityManager.students[location].getAddress().equals("test1, test2, test3, test4, test5, address")){
            System.out.println( "failed: address check");
            return false;
        }
        return true;
    }

    private static boolean CheckStudentSpecialNeedsStatus(int location){
        if (!Assignment2.entityManager.students[location].GetSpecialNeedsStatus()) {
            System.out.println( "failed: special needs check");
            return false;
        }
        return true;
    }

    private static String PadTitle(String title){
        while (title.length() < 35) title = title + "-";
        return title;
    }

}

