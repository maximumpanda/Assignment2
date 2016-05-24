package Core;

public class Student extends Person {
    private boolean hasSpecialNeeds = false;

    public Student(){}

    public Student(String id, String name, String address, boolean hasSpecialNeeds){
        this.setID(id);
        this.setName(name);
        this.setAddress(address);
        this.hasSpecialNeeds = hasSpecialNeeds;
    }
    public boolean GetSpecialNeedsStatus(){
        return this.hasSpecialNeeds;
    }
}
