package Main;

public class Student extends Person {
    private boolean HasSpecialNeeds = false;

    public Student(){}

    public Student(String id, String name, String address, boolean hasSpecialNeeds){
        this.SetID(id);
        this.SetName(name);
        this.SetAddress(address);
        this.HasSpecialNeeds = hasSpecialNeeds;
    }
    public boolean GetSpecialNeedsStatus(){
        return this.HasSpecialNeeds;
    }
    void SetSpecialNeedsStatus(boolean status){
        this.HasSpecialNeeds = status;
    }
}
