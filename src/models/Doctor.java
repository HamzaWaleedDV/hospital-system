package models;

import java.util.ArrayList;
import java.util.List;
import enums.Gender;

public class Doctor extends Person {

    private String specialization;
    private double consultationFee;
    private List<String> bookedSlots;

    public Doctor(String id, String name, int age, Gender gender, String phone, String specialization, double fee) {
        super(id, name, age, gender, phone);
        
        this.specialization = specialization;
        this.consultationFee = fee;
        this.bookedSlots = new ArrayList<>();
    }

    public String getRole() {
        return "Doctor";
    }

    public void printInfo() {
        super.printInfo();
        System.out.println("The Specialization: " + specialization);
        System.out.println("The ConsultationFee: " + consultationFee);
    }

    public void schedule(String dateTime) {
        bookedSlots.add(dateTime);
    }

    public void cancel() {
        if (!bookedSlots.isEmpty()) {
            bookedSlots.remove(bookedSlots.size() - 1);
        }
    }

    public boolean isAvailable(String dateTime) {
        if (dateTime.equals("")) { 
            return false;
        }
        return !bookedSlots.contains(dateTime);
    }

    public String getSpecialization() {
        return specialization;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public List<String> getBookedSlots() {
        return bookedSlots;
    }
}