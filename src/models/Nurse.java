package models;
public class Nurse extends Person  {

    private String department;
    private String shift;
    
    public Nurse(int id,int age,String name,String gender,String phone_number,String department,String shift){
        super(id,age,name,gender,phone_number);
        this.department = department;
        this.shift = shift;
    }
    public String getRole(){
        return "Nurse";
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
