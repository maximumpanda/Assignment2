package Core;

public class Student extends Person {
    private boolean hasSpecialNeeds = false;

    public Student(){}

    public Student(String name, String address, boolean hasSpecialNeeds){
        this.setID(GenerateID());
        this.setName(name);
        this.setAddress(address);
        this.hasSpecialNeeds = hasSpecialNeeds;
    }

    private String GenerateID(){
        String id = Integer.toString(Assignment2.entityManager.students.length);
        while (id.length() < 3) id = "0" + id;
        return "S" + id;
    }
    public boolean GetSpecialNeedsStatus(){
        return this.hasSpecialNeeds;
    }
}
