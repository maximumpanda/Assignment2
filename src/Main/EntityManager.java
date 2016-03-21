package Main;

import java.util.Arrays;

public class EntityManager {
    public Tutor[] Tutors = new Tutor[]{};
    public Student[] Students = new Student[]{};

    public Tutor FindTutorByID(String id){
        for (Tutor tutor: Tutors){
            if (tutor.GetID().equals(id)){
                return tutor;
            }
        }
        return null;
    }

    public Tutor FindTutorByName(String name)
    {
        for (Tutor tutor: Tutors){
            if (tutor.GetName().equals(name))
                return tutor;
        }
        return null;
    }

    public Student FindStudentByName(String name)
    {
        for (Student student: Students){
            if (student.GetName().equals(name))
                return student;
        }
        return null;
    }

    public Student FindStudentByID(String id){
        for (Student student: Students){
            if (student.GetID().equals(id)) {
                return student;
            }
        }
        return null;
    }

    public void AddTutor(Tutor newTutor){
        Tutors = Arrays.copyOf(Tutors, Tutors.length +1);
        Tutors[Tutors.length-1] = newTutor;

    }
    public void AddStudent(Student newStudent){
        Students = Arrays.copyOf(Students, Students.length +1);
        Students[Students.length -1] = newStudent;
    }

    public void AddUpdateStudent(Student newStudent){
        boolean StudentUpdated = UpdateStudent(newStudent);
        if (!StudentUpdated) AddStudent(newStudent);
    }

    public void AddUpdateTutor(Tutor newTutor){
        boolean TutorUpdated = UpdateTutor(newTutor);
        if (!TutorUpdated) UpdateTutor(newTutor);
    }

    private boolean UpdateStudent(Student newStudent){
        for (int i = 0; i < Students.length; i++){
            if (Students[i].GetID().equals(newStudent.GetID())){
                Students[i] = newStudent;
                return true;
            }
        }
        return false;
    }

    private boolean UpdateTutor(Tutor newTutor){
        for (int i =0; i < Tutors.length; i++){
            if (Tutors[i].GetID().equals(newTutor.GetID())){
                Tutors[i] = newTutor;
                return true;
            }
        }
        return false;
    }
}
