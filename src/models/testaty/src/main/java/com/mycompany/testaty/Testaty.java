package com.mycompany.testaty;

public class Testaty {
    public static void main(String[] args) {
      
        Doctor doc = new Doctor("18", "Dr. Mohamed", 20, "Male", "01012345678", " Medicine", 300.0);

        System.out.println("-^%-- Doctor Info ---%^");
        doc.printInfo(); 

        System.out.println("\n--**- Scheduling -**-**-");
        doc.schedule("2026-05-10 10:00 AM");
        System.out.println("Booked Slots: " + doc.getBookedSlots());


   
    }
}