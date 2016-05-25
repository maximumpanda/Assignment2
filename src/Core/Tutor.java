package Core;

public class Tutor extends Person {

    public Tutor(){}

    public Tutor(String name, String address){
        this.setID(GenerateID());
        this.setName(name);
        this.setAddress(address);
    }

    private String GenerateID(){
        String id = Integer.toString(Assignment2.entityManager.tutors.length);
        while (id.length() < 3) id = "0" + id;
        return "T" + id;
    }
}
