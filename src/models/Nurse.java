package models;

import enums.Gender;

public class Nurse extends Person  {

    private String department;
    private String shift;
    
    public Nurse(String id,int age,String name,Gender gender,String phone_number,String department,String shift){
        super(id,name,age,gender,phone_number);
        this.department = department;
        this.shift = shift;
    }
    public String getRole(){
        return "Nurse";
    }

    @Override
    public void printInfo(){
        super.printInfo();
        System.out.println("Department : " + department);
        System.out.println("Shift : " + shift);
    }

    public void printinfo(){
        System.out.println("Department : " + department);
        System.out.println("Shift : " + shift);
    }
    public String getDepartment(){
        return department;
    }
    public String getShift(){
        return shift;
    }
    public void setShift(String shift){
        this.shift = shift;
    }
}
