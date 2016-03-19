package Main;

public class Person {
    private String ID;
    private String Name;
    private String Address;

    public Person(){}

    public Person(String id){
        this.ID = id;
    }

    public Person(String id, String name, String address){
        this.ID = id;
        this.Name = name;
        this.Address = address;
    }

    public String GetAddress(){
        return this.Address;
    }
    public String GetID(){
        return this.ID;
    }
    public String GetName(){
        return this.Name;
    }
    void SetAddress(String address){
        this.Address = address;
    }
    void SetID(String id){
        this.ID = id;
    }
    void SetName(String name){
        this.Name = name;
    }
}
