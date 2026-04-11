package main.bootstrap;

import enums.BloodType;
import enums.Gender;
import models.Appointment;
import models.Doctor;
import models.Invoice;
import models.MedicalRecord;
import models.Nurse;
import models.Patient;
import models.Room;
import services.HospitalService;

public final class DemoDataSeeder {

    private DemoDataSeeder() {
    }

    public static void seed(HospitalService service) {
        if (service == null) {
            return;
        }

        if (!service.getDoctors().isEmpty() || !service.getPatients().isEmpty() || !service.getRooms().isEmpty()) {
            return;
        }

        Doctor doctor1 = new Doctor("DOC-001", "Dr. Omar Adel", 45, Gender.MALE, "01010000001", "Cardiology", 900);
        Doctor doctor2 = new Doctor("DOC-002", "Dr. Salma Tarek", 39, Gender.FEMALE, "01010000002", "Neurology", 1100);
        Doctor doctor3 = new Doctor("DOC-003", "Dr. Hana Mostafa", 34, Gender.FEMALE, "01010000003", "Pediatrics", 650);
        service.addDoctor(doctor1);
        service.addDoctor(doctor2);
        service.addDoctor(doctor3);

        Patient patient1 = new Patient("PAT-001", "Mina Nabil", 29, Gender.MALE, "01020000001", BloodType.O_POSITIVE);
        Patient patient2 = new Patient("PAT-002", "Laila Samir", 53, Gender.FEMALE, "01020000002", BloodType.A_NEGATIVE);
        Patient patient3 = new Patient("PAT-003", "Youssef Hany", 9, Gender.MALE, "01020000003", BloodType.B_POSITIVE);
        service.addPatient(patient1);
        service.addPatient(patient2);
        service.addPatient(patient3);

        service.addMedicalRecordToPatient("PAT-001",
                new MedicalRecord("MR-001", "Hypertension", "Lifestyle changes + monitoring", "2026-01-08", "Dr. Omar Adel"));
        service.addMedicalRecordToPatient("PAT-001",
                new MedicalRecord("MR-002", "Migraine", "Pain control plan", "2026-02-12", "Dr. Salma Tarek"));

        Nurse nurse1 = new Nurse("NUR-001", 31, "Nora Ahmed", Gender.FEMALE, "01030000001", "Emergency", "Morning");
        Nurse nurse2 = new Nurse("NUR-002", 40, "Ibrahim Khaled", Gender.MALE, "01030000002", "ICU", "Night");
        service.addNurse(nurse1);
        service.addNurse(nurse2);

        Room room1 = new Room(101, "Single", 700);
        Room room2 = new Room(102, "Double", 900);
        Room room3 = new Room(201, "ICU", 2200);
        service.addRoom(room1);
        service.addRoom(room2);
        service.addRoom(room3);

        service.admitPatientToRoom(102, "PAT-002");

        Appointment appointment1 = service.createAppointment("APT-001", "DOC-001", "PAT-001");
        if (appointment1 != null) {
            service.scheduleAppointment("APT-001", "2026-04-12 10:00");
            service.setAppointmentNotes("APT-001", "Follow-up blood pressure review.");
        }

        Appointment appointment2 = service.createAppointment("APT-002", "DOC-003", "PAT-003");
        if (appointment2 != null) {
            service.scheduleAppointment("APT-002", "2026-04-13 12:30");
        }

        Appointment appointment3 = service.createAppointment("APT-003", "DOC-002", "PAT-002");
        if (appointment3 != null) {
            service.setAppointmentNotes("APT-003", "Pending MRI report before confirmation.");
        }

        Invoice invoice1 = service.generateInvoice("INV-001", "PAT-002", 1100, 1800, 350);
        if (invoice1 != null) {
            service.markInvoiceAsPaid("INV-001");
        }

        service.generateInvoice("INV-002", "PAT-001", 900, 0, 120);
    }
}
