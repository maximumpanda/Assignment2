package Core;

public class Person {
    private String id;
    private String name;
    private String address;

    public String getAddress(){
        return this.address;
    }
    public String getID(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    void setAddress(String address){
        this.address = address;
    }
    void setID(String id){
        this.id = id;
    }
    void setName(String name){
        this.name = name;
    }
}
