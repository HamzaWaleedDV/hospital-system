package hospital;
interface Printable {
    void printInfo();
}
class Document {
protected String recordID;
public Document(String recordID) {
    this.recordID = recordID;
    }

    
}
public class MedicalRecord extends Document implements Printable {
private String diagnosis;
    private String prescription;
    private String date;
    private String doctorName;
    public MedicalRecord(String recordID, String diagnosis, String prescription, String date, String doctorName) {
        super (recordID);
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.date = date;
        this.doctorName = doctorName;
    }
     super(recordID); 
     this.diagnosis = diagnosis;
     this.prescription = prescription;
        this.date = date;
        this.doctorName = doctorName;

   @Override
    public void printInfo() {
        System.out.println("Record ID: " + recordID);
        System.out.println("Diagnosis: " + diagnosis);
        System.out.println("Prescription: " + prescription);
        System.out.println("Date: " + date);
        System.out.println("Doctor Name: " + doctorName);
    }
    public String getRecordID() {
        return recordID;
    }public String getDiagnosis() {
        return diagnosis;
    }public String getPrescription() {
        return prescription;
    }public String getDate() {
        return date;
    }public String getDoctorName() {
        return doctorName;
    }

}
