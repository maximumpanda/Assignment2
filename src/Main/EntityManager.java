package Main;

import java.util.Arrays;

public class EntityManager {
    Tutor[] Tutors = new Tutor[]{};
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
            if (tutor.GetName().toLowerCase() == name.toLowerCase())
                return tutor;
        }
        return null;
    }

    public Student FindStudentByName(String name)
    {
        for (Student student: Students){
            if (student.GetName() == name)
                return student;
        }
        return null;
    }

    public Student FindStudentByID(String id){
        for (Student student: Students){
            if (student.GetID() == id) {
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
}
