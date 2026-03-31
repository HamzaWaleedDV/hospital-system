package models;

import enums.BloodType;
import enums.Gender;
import java.util.ArrayList;
import java.util.List;

public class Patient extends Person{

    private BloodType bloodType;
    private List<MedicalRecord> medicalHistory;

    public Patient (String id , String name , int age , Gender gender , String phone , BloodType bloodType ){

      super( id , name , age , gender , phone);
      this.bloodType = bloodType;
      this.medicalHistory = new ArrayList<>();
    }

     @Override
    public String getRole(){
         return "patient";

    }
       @Override
    public void printInfo(){

        super.printInfo();
        System.out.println("Blood type : " + bloodType);
        printMedicalHistory();
    }
    public void addMedicalRecord(MedicalRecord record){

        medicalHistory.add(record);
    }

  public void printMedicalHistory() {
        System.out.println("Medical History:");
        for (MedicalRecord record : medicalHistory) {
            System.out.println(record);
        }}
    

    public BloodType getBloodType(){

        return bloodType;
    }

    public List<MedicalRecord> getMedicalHistory() {
        return medicalHistory;
    }





}
