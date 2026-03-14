

public class MedicalRecord {
    private String recordID;
    private String diagnosis;
    private String prescription;
    private String data;
    private String doctorName;
    public MedicalRecord(String recordID, String diagnosis, String prescription, String data, String doctorName) {
        this.recordID = recordID;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.data = data;
        this.doctorName = doctorName;
    }
    public void printInfo() {
        System.out.println("Record ID: " + recordID);
        System.out.println("Diagnosis: " + diagnosis);
        System.out.println("Prescription: " + prescription);
        System.out.println("Data: " + data);
        System.out.println("Doctor Name: " + doctorName);
    }
    public String getRecordID() {
        return recordID;
    }public String getDiagnosis() {
        return diagnosis;
    }public String getPrescription() {
        return prescription;
    }public String getData() {
        return data;
    }public String getDoctorName() {
        return doctorName;
    }

}
