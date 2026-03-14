package models ;


public class Invoice  implements Printable {
    private String invoiced;
    private Patient patient;
    private double consultationFee;
    private double roomCharges;
    private double medicationCharges;
    private boolean isPaid;
    private double totalPaid;

    Invoice(String invoiced, Patient patient, double consultationFee, double roomCharges, double medicationCharges) {
        this.invoiced = invoiced;
        this.patient = patient;
        this.consultationFee = consultationFee;
        this.roomCharges = roomCharges;
        this.medicationCharges = medicationCharges;
        this.isPaid = false;
    }

    public void calculateTotal() {
         totalPaid = consultationFee + roomCharges + medicationCharges;
    }

    public double getTotal() {
        return totalPaid ;
    }

    public void pay() {
        isPaid = true;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public String getInvoicedId() {
        return patient.getId().toString();
    }
    public void printinfo() {
        System.out.println("Invoice for: " + patient.getName() + " (ID: " + patient.getId() + ")\n");

        System.out.println("Consultation Fee: $" + consultationFee + "\n");

        System.out.println("Room Charges: $" + roomCharges + "\n");

        System.out.println("Medication Charges: $" + medicationCharges + "\n");

        System.out.println("Total Amount: $" + getTotal() + "\n");

        System.out.println("Payment Status: " + (isPaid ? "Paid" : "Unpaid") + "\n");
    }


}
