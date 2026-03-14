import java.util.ArrayList;

public class HospitalService {

    private ArrayList<Doctor> doctors = new ArrayList<>();
    private ArrayList<Patient> patients = new ArrayList<>();
    private ArrayList<Nurse> nurses = new ArrayList<>();
    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Appointment> appointments = new ArrayList<>();
    private ArrayList<Invoice> invoices = new ArrayList<>();

    public void addDoctor(Doctor doctor){
        doctors.add(doctor);
    }
    
    public Doctor findDoctorById(String id){

        for(int i = 0; i < doctors.size() ; i ++)
            if (doctors.get(i).getId().equals(id)) 
                return doctors.get(i);

        return null;
    }

    public ArrayList<Doctor> geDoctorsBySpecialization(String spec){

        ArrayList<Doctor> doctorsWithSpesficSpecialization = new ArrayList<>();

        for(int i = 0 ; i < doctors.size() ; i++)
            if (doctors.get(i).getSpecialization().equals(spec)) 
                doctorsWithSpesficSpecialization.add(doctors.get(i));

        return doctorsWithSpesficSpecialization;
    }

    public void addPatient(Patient patient){
        patients.add(patient);
    }

    public Patient findPatientById(String id){

        for(int i = 0 ; i < patients.size() ; i++)
            if (patients.get(i).getId().equals(id)) 
                return patients.get(i);

        return null;
    }

    public void addNurse(Nurse nurse){
        nurses.add(nurse);
    }

    public void addRoom(Room room){
        rooms.add(room);
    }

    public Room findAvailableRoom(String type){

        for(int i = 0 ; i < rooms.size() ; i++)
            if (rooms.get(i).getType().equals(type)) 
                return rooms.get(i);

        return null;
    }

    public Appointment createAppointment(String id, String doctorId, String patientId){

        Doctor doctor = findDoctorById(doctorId);
        Patient patient = findPatientById(patientId);

        if (doctor == null || patient == null) 
            return null;
        
        Appointment appointment = new Appointment(id, doctor, patient);
        appointments.add(appointment);

        return appointment;
    }

    public Invoice generateInvoice(String invoiceId, String patientId, double consultionFee, double roomCharges, double medicineCharges ){

        Patient patient = findPatientById(patientId);

        if (patient == null) 
            return null;

        Invoice invoice = new Invoice(invoiceId, patient, consultionFee, roomCharges, medicineCharges);
        invoices.add(invoice);

        return invoice;
    }

    public void printAllDoctors(){
        if (doctors.size() == 0) {
            System.out.println("There Is No Doctors Yet.");
            return;
        }
        else{
            for(int i = 0 ; i < doctors.size() ; i++){
                doctors.get(i).printInfo();
            }
        }
    }

        public void printAllPatients(){
        if (patients.size() == 0) {
            System.out.println("There Is No Patients Yet.");
            return;
        }
        else{
            for(int i = 0 ; i < patients.size() ; i++){
                patients.get(i).printInfo();
            }
        }
    }

        public void printAllRooms(){
        if (rooms.size() == 0) {
            System.out.println("There Is No Rooms Yet.");
            return;
        }
        else{
            for(int i = 0 ; i < rooms.size() ; i++){
                rooms.get(i).printInfo();
            }
        }
    }
}
