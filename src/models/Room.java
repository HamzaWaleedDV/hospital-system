package models;

public class Room implements Printable {
    private final int roomNumber;
    private final String type;
    private double pricePerNight;
    private boolean isOccupied;
    private Patient currentPatient;

    public Room(int roomNumber, String type, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.isOccupied = false;
    }

    public boolean admitPatient(Patient patient) {
        if (isOccupied || patient == null) {
            return false;
        }

        this.currentPatient = patient;
        this.isOccupied = true;
        return true;
    }

    public void dischargePatient() {
        this.currentPatient = null;
        this.isOccupied = false;
    }

    public void printInfo() {
        System.out.println("========================================");
        System.out.printf("Room Details   : #%d [%s]%n", roomNumber, type);
        System.out.printf("Nightly Rate   : %.2f EGP%n", pricePerNight);

        if (isOccupied && currentPatient != null) {
            System.out.printf("Current Occupant: %s%n", currentPatient.getName());
        } else {
            System.out.println("Current Occupant: None (Available)");
        }

        System.out.println("========================================");
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public Patient getCurrentPatient() {
        return currentPatient;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }
}