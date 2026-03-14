package abstraction;

import enums.Gender;

public abstract class Person {

    private String id;
    private String name;
    private int age;
    private Gender gender;
    private String phone;

    public Person( String id, String name, int age, Gender gender, String phone ){
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
    }

    public abstract String getRole();

    public void printInfo(){
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println( "Phone: " + phone);
    }

    public String getID(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }

    public Gender getGender(){
        return gender;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone( String phone ){
        this.phone = phone;
    }

    public void setAge( int age ){
        this.age = age;
    }
}
