package Core;

import java.util.Arrays;

public class EntityManager {
    public Tutor[] tutors = new Tutor[]{};
    public Student[] students = new Student[]{};

    public Tutor getTutorByID(String id){
        for (Tutor tutor: tutors){
            if (tutor.getID().equals(id)){
                return tutor;
            }
        }
        return null;
    }
    public Tutor getTutorByName(String name){
        for (Tutor tutor: tutors){
            if (tutor.getName().equals(name))
                return tutor;
        }
        return null;
    }
    public Student getStudentByName(String name){
        for (Student student: this.students){
            if (student.getName().equals(name))
                return student;
        }
        return null;
    }
    public Student getStudentByID(String id){
        for (Student student: this.students){
            if (student.getID().equals(id)) {
                return student;
            }
        }
        return null;
    }
    public void addTutor(Tutor newTutor){
        tutors = Arrays.copyOf(tutors, tutors.length +1);
        tutors[tutors.length-1] = newTutor;

    }
    public void addStudent(Student newStudent){
        this.students = Arrays.copyOf(this.students, this.students.length +1);
        this.students[this.students.length -1] = newStudent;
    }
    public void addUpdateStudent(Student newStudent){
        boolean StudentUpdated = updateStudent(newStudent);
        if (!StudentUpdated) addStudent(newStudent);
    }
    public void addUpdateTutor(Tutor newTutor){
        boolean TutorUpdated = updateTutor(newTutor);
        if (!TutorUpdated) addTutor(newTutor);
    }
    public void removeStudentByID(String studentID){
        Student[] newStudentsArray = new Student[]{};
        for (Student student: students){
            if (!student.getID().equals(studentID)){
                newStudentsArray = Arrays.copyOf(newStudentsArray, newStudentsArray.length +1);
                newStudentsArray[newStudentsArray.length -1] = student;
            }
        }
        students = newStudentsArray;
    }
    private boolean updateStudent(Student newStudent){
        for (int i = 0; i < this.students.length; i++){
            if (this.students[i].getID().equals(newStudent.getID())){
                this.students[i] = newStudent;
                return true;
            }
        }
        return false;
    }
    private boolean updateTutor(Tutor newTutor){
        for (int i = 0; i < tutors.length; i++){
            if (tutors[i].getID().equals(newTutor.getID())){
                tutors[i] = newTutor;
                return true;
            }
        }
        return false;
    }
}
