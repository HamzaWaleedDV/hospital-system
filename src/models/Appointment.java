package models;

import interfaces.Printable;
import interfaces.Schedulable;

public class Appointment implements Schedulable, Printable {

    private String appointmentId;
    private Doctor doctor;   
    private Patient patient; 
    private String dateTime;
    private boolean isConfirmed;
    private String notes;

    public Appointment(String appointmentId, Doctor doctor, Patient patient) {
        this.appointmentId = appointmentId;
        this.doctor = doctor;
        this.patient = patient;
        this.isConfirmed = false;
        this.dateTime = "";
        this.notes = "";
    }

    @Override
    public void schedule(String dateTime) {
        if (doctor.isAvailable(dateTime)) {
            this.dateTime = dateTime;
            this.isConfirmed = true;
            doctor.schedule(dateTime); 
            System.out.println("Appointment [" + appointmentId + "] confirmed!");
        } else {
            System.out.println("Cannot schedule. Doctor not available.");
        }
    }

    @Override
    public void cancel() {
        if (isConfirmed) {
            doctor.cancel();
            this.isConfirmed = false;
            System.out.println("Appointment [" + appointmentId + "] has been cancelled.");
        } else {
            System.out.println("Appointment is not confirmed yet.");
        }
    }

    @Override
    public boolean isAvailable(String dateTime) {
        return doctor.isAvailable(dateTime);
    }

    @Override
    public void printInfo() {
        System.out.println("=====================================");
        System.out.println("Appointment ID : " + appointmentId);
        System.out.println("Doctor         : " + doctor.getName() + " (" + doctor.getSpecialization() + ")");
        System.out.println("Patient        : " + patient.getName());
        System.out.println("Date & Time    : " + (dateTime.isEmpty() ? "Not Set" : dateTime));
        System.out.println("Status         : " + (isConfirmed ? "Confirmed" : "Pending"));
        if (!notes.isEmpty()) System.out.println("Notes          : " + notes);
        System.out.println("=====================================");
    }

    public void setNotes(String notes) { this.notes = notes; }
    public String getAppointmentId()   { return appointmentId; }
    public Doctor getDoctor()          { return doctor; }
    public Patient getPatient()        { return patient; }
    public boolean isConfirmed()       { return isConfirmed; }
    public String getDateTime()        { return dateTime; }
}
