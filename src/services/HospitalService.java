package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import models.Appointment;
import models.Doctor;
import models.Invoice;
import models.MedicalRecord;
import models.Nurse;
import models.Patient;
import models.Room;

public class HospitalService {

    private final ArrayList<Doctor> doctors = new ArrayList<>();
    private final ArrayList<Patient> patients = new ArrayList<>();
    private final ArrayList<Nurse> nurses = new ArrayList<>();
    private final ArrayList<Room> rooms = new ArrayList<>();
    private final ArrayList<Appointment> appointments = new ArrayList<>();
    private final ArrayList<Invoice> invoices = new ArrayList<>();

    public void addDoctor(Doctor doctor) {
        if (doctor != null) {
            doctors.add(doctor);
        }
    }

    public Doctor findDoctorById(String id) {
        if (id == null) {
            return null;
        }

        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getId().equals(id)) {
                return doctors.get(i);
            }
        }

        return null;
    }

    public ArrayList<Doctor> geDoctorsBySpecialization(String spec) {
        return getDoctorsBySpecialization(spec);
    }

    public ArrayList<Doctor> getDoctorsBySpecialization(String spec) {
        ArrayList<Doctor> doctorsWithSpecificSpecialization = new ArrayList<>();

        if (spec == null) {
            return doctorsWithSpecificSpecialization;
        }

        String normalizedSpec = spec.trim();
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getSpecialization().equalsIgnoreCase(normalizedSpec)) {
                doctorsWithSpecificSpecialization.add(doctors.get(i));
            }
        }

        return doctorsWithSpecificSpecialization;
    }

    public void addPatient(Patient patient) {
        if (patient != null) {
            patients.add(patient);
        }
    }

    public Patient findPatientById(String id) {
        if (id == null) {
            return null;
        }

        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getId().equals(id)) {
                return patients.get(i);
            }
        }

        return null;
    }

    public void addNurse(Nurse nurse) {
        if (nurse != null) {
            nurses.add(nurse);
        }
    }

    public Nurse findNurseById(String id) {
        if (id == null) {
            return null;
        }

        for (int i = 0; i < nurses.size(); i++) {
            if (nurses.get(i).getId().equals(id)) {
                return nurses.get(i);
            }
        }

        return null;
    }

    public void addRoom(Room room) {
        if (room != null) {
            rooms.add(room);
        }
    }

    public Room findRoomByNumber(int roomNumber) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomNumber() == roomNumber) {
                return rooms.get(i);
            }
        }

        return null;
    }

    public Room findAvailableRoom(String type) {
        String normalizedType = type == null ? "" : type.trim();

        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            boolean matchingType = normalizedType.isEmpty() || room.getType().equalsIgnoreCase(normalizedType);
            if (matchingType && !room.isOccupied()) {
                return room;
            }
        }

        return null;
    }

    public boolean admitPatientToRoom(int roomNumber, String patientId) {
        Room room = findRoomByNumber(roomNumber);
        Patient patient = findPatientById(patientId);
        if (room == null || patient == null) {
            return false;
        }

        return room.admitPatient(patient);
    }

    public boolean dischargePatientFromRoom(int roomNumber) {
        Room room = findRoomByNumber(roomNumber);
        if (room == null) {
            return false;
        }
        return room.dischargePatient();
    }

    public Appointment createAppointment(String id, String doctorId, String patientId) {
        if (id == null || id.trim().isEmpty() || findAppointmentById(id) != null) {
            return null;
        }

        Doctor doctor = findDoctorById(doctorId);
        Patient patient = findPatientById(patientId);

        if (doctor == null || patient == null) {
            return null;
        }

        Appointment appointment = new Appointment(id.trim(), doctor, patient);
        appointments.add(appointment);

        return appointment;
    }

    public Appointment findAppointmentById(String appointmentId) {
        if (appointmentId == null) {
            return null;
        }

        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getAppointmentId().equals(appointmentId)) {
                return appointments.get(i);
            }
        }

        return null;
    }

    public boolean scheduleAppointment(String appointmentId, String dateTime) {
        Appointment appointment = findAppointmentById(appointmentId);
        if (appointment == null || dateTime == null || dateTime.trim().isEmpty()) {
            return false;
        }

        appointment.schedule(dateTime);
        return appointment.isConfirmed() && appointment.getDateTime().equals(dateTime.trim());
    }

    public boolean cancelAppointment(String appointmentId) {
        Appointment appointment = findAppointmentById(appointmentId);
        if (appointment == null || !appointment.isConfirmed()) {
            return false;
        }

        appointment.cancel();
        return !appointment.isConfirmed();
    }

    public boolean setAppointmentNotes(String appointmentId, String notes) {
        Appointment appointment = findAppointmentById(appointmentId);
        if (appointment == null) {
            return false;
        }

        appointment.setNotes(notes == null ? "" : notes.trim());
        return true;
    }

    public Invoice generateInvoice(String invoiceId, String patientId, double consultationFee, double roomCharges,
            double medicineCharges) {
        if (invoiceId == null || invoiceId.trim().isEmpty() || findInvoiceById(invoiceId) != null) {
            return null;
        }

        if (consultationFee < 0 || roomCharges < 0 || medicineCharges < 0) {
            return null;
        }

        Patient patient = findPatientById(patientId);

        if (patient == null) {
            return null;
        }

        Invoice invoice = new Invoice(invoiceId.trim(), patient, consultationFee, roomCharges, medicineCharges);
        invoices.add(invoice);

        return invoice;
    }

    public Invoice findInvoiceById(String invoiceId) {
        if (invoiceId == null) {
            return null;
        }

        for (int i = 0; i < invoices.size(); i++) {
            if (invoices.get(i).getInvoicedId().equals(invoiceId)) {
                return invoices.get(i);
            }
        }

        return null;
    }

    public boolean markInvoiceAsPaid(String invoiceId) {
        Invoice invoice = findInvoiceById(invoiceId);
        if (invoice == null || invoice.isPaid()) {
            return false;
        }
        invoice.pay();
        return true;
    }

    public boolean addMedicalRecordToPatient(String patientId, MedicalRecord record) {
        Patient patient = findPatientById(patientId);
        if (patient == null || record == null) {
            return false;
        }
        patient.addMedicalRecord(record);
        return true;
    }

    public List<Doctor> getDoctors() {
        return Collections.unmodifiableList(new ArrayList<>(doctors));
    }

    public List<Patient> getPatients() {
        return Collections.unmodifiableList(new ArrayList<>(patients));
    }

    public List<Nurse> getNurses() {
        return Collections.unmodifiableList(new ArrayList<>(nurses));
    }

    public List<Room> getRooms() {
        return Collections.unmodifiableList(new ArrayList<>(rooms));
    }

    public List<Appointment> getAppointments() {
        return Collections.unmodifiableList(new ArrayList<>(appointments));
    }

    public List<Invoice> getInvoices() {
        return Collections.unmodifiableList(new ArrayList<>(invoices));
    }

    public void printAllDoctors() {
        if (doctors.isEmpty()) {
            System.out.println("There Is No Doctors Yet.");
            return;
        }

        for (int i = 0; i < doctors.size(); i++) {
            doctors.get(i).printInfo();
        }
    }

    public void printAllPatients() {
        if (patients.isEmpty()) {
            System.out.println("There Is No Patients Yet.");
            return;
        }

        for (int i = 0; i < patients.size(); i++) {
            patients.get(i).printInfo();
        }
    }

    public void printAllRooms() {
        if (rooms.isEmpty()) {
            System.out.println("There Is No Rooms Yet.");
            return;
        }

        for (int i = 0; i < rooms.size(); i++) {
            rooms.get(i).printInfo();
        }
    }
}
