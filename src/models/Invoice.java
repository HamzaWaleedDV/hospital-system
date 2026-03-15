package models;

public class Invoice implements Printable {
    private String invoicedId;
    private Patient patient;
    private double consultationFee;
    private double roomCharges;
    private double medicationCharges;
    private boolean isPaid;

    Invoice(String invoicedId, Patient patient, double consultationFee, double roomCharges, double medicationCharges) {
        this.patient = patient;
        this.consultationFee = consultationFee;
        this.roomCharges = roomCharges;
        this.medicationCharges = medicationCharges;
        this.isPaid = false;
    }

    public double calculateTotal() {
        return consultationFee + roomCharges + medicationCharges;
    }

    public double getTotal() {
        return calculateTotal();
    }

    public void pay() {
        isPaid = true;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public String getInvoicedId() {
        return invoicedId;
    }

    public void printInfo() {
        System.out.println("Invoice for: " + patient.getName() + " (ID: " + patient.getId() + ")\n");

        System.out.println("Consultation Fee: $" + consultationFee + "\n");

        System.out.println("Room Charges: $" + roomCharges + "\n");

        System.out.println("Medication Charges: $" + medicationCharges + "\n");

        System.out.println("Total Amount: $" + getTotal() + "\n");

        System.out.println("Payment Status: " + (isPaid ? "Paid" : "Unpaid") + "\n");
    }

}
