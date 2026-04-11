package main.ui;

import enums.BloodType;
import enums.Gender;
import java.util.ArrayList;
import java.util.List;
import models.Appointment;
import models.Doctor;
import models.Invoice;
import models.MedicalRecord;
import models.Nurse;
import models.Patient;
import models.Room;
import services.HospitalService;

public class ConsoleApp {

    private final HospitalService service;
    private final Input input;
    private final Renderer renderer;

    public ConsoleApp(HospitalService service) {
        this.service = service;
        this.input = new Input();
        this.renderer = new Renderer();
    }

    public void run() {
        boolean running = true;
        while (running) {
            renderer.clearScreen();
            renderer.header("Hospital Management Console");
            renderer.menuItem(1, "Dashboard");
            renderer.menuItem(2, "Doctors");
            renderer.menuItem(3, "Patients & Medical Records");
            renderer.menuItem(4, "Nurses");
            renderer.menuItem(5, "Rooms & Admissions");
            renderer.menuItem(6, "Appointments");
            renderer.menuItem(7, "Invoices & Payments");
            renderer.menuItem(8, "Reports");
            renderer.menuItem(9, "Exit");

            int choice = input.readIntInRange("Choose an option: ", 1, 9);
            switch (choice) {
                case 1:
                    showDashboard();
                    break;
                case 2:
                    doctorsMenu();
                    break;
                case 3:
                    patientsMenu();
                    break;
                case 4:
                    nursesMenu();
                    break;
                case 5:
                    roomsMenu();
                    break;
                case 6:
                    appointmentsMenu();
                    break;
                case 7:
                    invoicesMenu();
                    break;
                case 8:
                    showReports();
                    break;
                case 9:
                    running = false;
                    break;
                default:
                    renderer.error("Invalid option.");
                    break;
            }
        }

        renderer.success("Thanks for using the Hospital Management Console.");
    }

    private void showDashboard() {
        renderer.clearScreen();
        renderer.header("Dashboard");

        int doctorsCount = service.getDoctors().size();
        int patientsCount = service.getPatients().size();
        int nursesCount = service.getNurses().size();
        int roomsCount = service.getRooms().size();
        int appointmentsCount = service.getAppointments().size();
        int invoicesCount = service.getInvoices().size();

        int occupiedRooms = 0;
        for (Room room : service.getRooms()) {
            if (room.isOccupied()) {
                occupiedRooms++;
            }
        }

        int pendingAppointments = 0;
        for (Appointment appointment : service.getAppointments()) {
            if (!appointment.isConfirmed()) {
                pendingAppointments++;
            }
        }

        int unpaidInvoices = 0;
        for (Invoice invoice : service.getInvoices()) {
            if (!invoice.isPaid()) {
                unpaidInvoices++;
            }
        }

        renderer.keyValue("Doctors", String.valueOf(doctorsCount));
        renderer.keyValue("Patients", String.valueOf(patientsCount));
        renderer.keyValue("Nurses", String.valueOf(nursesCount));
        renderer.keyValue("Rooms", String.valueOf(roomsCount));
        renderer.keyValue("Appointments", String.valueOf(appointmentsCount));
        renderer.keyValue("Invoices", String.valueOf(invoicesCount));
        renderer.keyValue("Occupied Rooms", String.valueOf(occupiedRooms));
        renderer.keyValue("Pending Appointments", String.valueOf(pendingAppointments));
        renderer.keyValue("Unpaid Invoices", String.valueOf(unpaidInvoices));

        input.pause();
    }

    private void doctorsMenu() {
        while (true) {
            renderer.clearScreen();
            renderer.header("Doctors");
            renderer.menuItem(1, "Add Doctor");
            renderer.menuItem(2, "List Doctors");
            renderer.menuItem(3, "Find Doctor By ID");
            renderer.menuItem(4, "Filter By Specialization");
            renderer.menuItem(5, "Back");

            int choice = input.readIntInRange("Choose an option: ", 1, 5);
            switch (choice) {
                case 1:
                    addDoctor();
                    break;
                case 2:
                    listDoctors();
                    break;
                case 3:
                    findDoctorById();
                    break;
                case 4:
                    filterDoctorsBySpecialization();
                    break;
                case 5:
                    return;
                default:
                    renderer.error("Invalid option.");
                    break;
            }
        }
    }

    private void addDoctor() {
        renderer.section("Add Doctor");

        String id = input.readNonEmptyString("Doctor ID: ");
        if (service.findDoctorById(id) != null) {
            renderer.error("Doctor ID already exists.");
            input.pause();
            return;
        }

        String name = input.readNonEmptyString("Name: ");
        int age = input.readInt("Age: ", 1);
        Gender gender = input.readGender("Gender");
        String phone = input.readNonEmptyString("Phone: ");
        String specialization = input.readNonEmptyString("Specialization: ");
        double fee = input.readPositiveDouble("Consultation Fee: ");

        Doctor doctor = new Doctor(id, name, age, gender, phone, specialization, fee);
        service.addDoctor(doctor);
        renderer.success("Doctor added successfully.");
        input.pause();
    }

    private void listDoctors() {
        renderer.section("Doctors List");
        List<Doctor> doctors = service.getDoctors();
        if (doctors.isEmpty()) {
            renderer.warning("No doctors found.");
            input.pause();
            return;
        }

        List<String[]> rows = new ArrayList<>();
        for (Doctor doctor : doctors) {
            rows.add(new String[] {
                    doctor.getId(),
                    doctor.getName(),
                    doctor.getSpecialization(),
                    money(doctor.getConsultationFee()),
                    String.valueOf(doctor.getBookedSlots().size())
            });
        }

        renderer.table(new String[] { "ID", "Name", "Specialization", "Fee", "Booked Slots" }, rows);
        input.pause();
    }

    private void findDoctorById() {
        renderer.section("Find Doctor");
        String id = input.readNonEmptyString("Doctor ID: ");
        Doctor doctor = service.findDoctorById(id);
        if (doctor == null) {
            renderer.error("Doctor not found.");
            input.pause();
            return;
        }

        renderer.keyValue("ID", doctor.getId());
        renderer.keyValue("Name", doctor.getName());
        renderer.keyValue("Age", String.valueOf(doctor.getAge()));
        renderer.keyValue("Gender", doctor.getGender().name());
        renderer.keyValue("Phone", doctor.getPhone());
        renderer.keyValue("Specialization", doctor.getSpecialization());
        renderer.keyValue("Consultation Fee", money(doctor.getConsultationFee()));
        renderer.keyValue("Booked Slots", String.valueOf(doctor.getBookedSlots().size()));
        input.pause();
    }

    private void filterDoctorsBySpecialization() {
        renderer.section("Filter Doctors");
        String specialization = input.readNonEmptyString("Specialization: ");
        List<Doctor> doctors = service.getDoctorsBySpecialization(specialization);
        if (doctors.isEmpty()) {
            renderer.warning("No doctors found for this specialization.");
            input.pause();
            return;
        }

        List<String[]> rows = new ArrayList<>();
        for (Doctor doctor : doctors) {
            rows.add(new String[] {
                    doctor.getId(),
                    doctor.getName(),
                    doctor.getSpecialization(),
                    money(doctor.getConsultationFee())
            });
        }

        renderer.table(new String[] { "ID", "Name", "Specialization", "Fee" }, rows);
        input.pause();
    }

    private void patientsMenu() {
        while (true) {
            renderer.clearScreen();
            renderer.header("Patients & Medical Records");
            renderer.menuItem(1, "Add Patient");
            renderer.menuItem(2, "List Patients");
            renderer.menuItem(3, "Find Patient By ID");
            renderer.menuItem(4, "Add Medical Record");
            renderer.menuItem(5, "View Medical History");
            renderer.menuItem(6, "Back");

            int choice = input.readIntInRange("Choose an option: ", 1, 6);
            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    listPatients();
                    break;
                case 3:
                    findPatientById();
                    break;
                case 4:
                    addMedicalRecord();
                    break;
                case 5:
                    viewMedicalHistory();
                    break;
                case 6:
                    return;
                default:
                    renderer.error("Invalid option.");
                    break;
            }
        }
    }

    private void addPatient() {
        renderer.section("Add Patient");

        String id = input.readNonEmptyString("Patient ID: ");
        if (service.findPatientById(id) != null) {
            renderer.error("Patient ID already exists.");
            input.pause();
            return;
        }

        String name = input.readNonEmptyString("Name: ");
        int age = input.readInt("Age: ", 1);
        Gender gender = input.readGender("Gender");
        String phone = input.readNonEmptyString("Phone: ");
        BloodType bloodType = input.readBloodType("Blood Type");

        Patient patient = new Patient(id, name, age, gender, phone, bloodType);
        service.addPatient(patient);
        renderer.success("Patient added successfully.");
        input.pause();
    }

    private void listPatients() {
        renderer.section("Patients List");
        List<Patient> patients = service.getPatients();
        if (patients.isEmpty()) {
            renderer.warning("No patients found.");
            input.pause();
            return;
        }

        List<String[]> rows = new ArrayList<>();
        for (Patient patient : patients) {
            rows.add(new String[] {
                    patient.getId(),
                    patient.getName(),
                    String.valueOf(patient.getAge()),
                    patient.getBloodType().getLabel(),
                    String.valueOf(patient.getMedicalHistory().size())
            });
        }

        renderer.table(new String[] { "ID", "Name", "Age", "Blood Type", "Records" }, rows);
        input.pause();
    }

    private void findPatientById() {
        renderer.section("Find Patient");
        String id = input.readNonEmptyString("Patient ID: ");
        Patient patient = service.findPatientById(id);
        if (patient == null) {
            renderer.error("Patient not found.");
            input.pause();
            return;
        }

        renderer.keyValue("ID", patient.getId());
        renderer.keyValue("Name", patient.getName());
        renderer.keyValue("Age", String.valueOf(patient.getAge()));
        renderer.keyValue("Gender", patient.getGender().name());
        renderer.keyValue("Phone", patient.getPhone());
        renderer.keyValue("Blood Type", patient.getBloodType().getLabel());
        renderer.keyValue("Medical Records", String.valueOf(patient.getMedicalHistory().size()));
        input.pause();
    }

    private void addMedicalRecord() {
        renderer.section("Add Medical Record");
        String patientId = input.readNonEmptyString("Patient ID: ");
        Patient patient = service.findPatientById(patientId);
        if (patient == null) {
            renderer.error("Patient not found.");
            input.pause();
            return;
        }

        String recordId = input.readNonEmptyString("Record ID: ");
        if (medicalRecordIdExists(patient, recordId)) {
            renderer.error("Record ID already exists for this patient.");
            input.pause();
            return;
        }

        String diagnosis = input.readNonEmptyString("Diagnosis: ");
        String prescription = input.readNonEmptyString("Prescription: ");
        String date = input.readNonEmptyString("Date (e.g., 2026-04-11): ");
        String doctorName = input.readNonEmptyString("Doctor Name: ");

        MedicalRecord record = new MedicalRecord(recordId, diagnosis, prescription, date, doctorName);
        boolean added = service.addMedicalRecordToPatient(patientId, record);
        if (added) {
            renderer.success("Medical record added successfully.");
        } else {
            renderer.error("Could not add medical record.");
        }
        input.pause();
    }

    private void viewMedicalHistory() {
        renderer.section("Medical History");
        String patientId = input.readNonEmptyString("Patient ID: ");
        Patient patient = service.findPatientById(patientId);
        if (patient == null) {
            renderer.error("Patient not found.");
            input.pause();
            return;
        }

        List<MedicalRecord> history = patient.getMedicalHistory();
        if (history.isEmpty()) {
            renderer.warning("No medical records found.");
            input.pause();
            return;
        }

        renderer.info("Patient: " + patient.getName() + " (" + patient.getId() + ")");
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + ". " + history.get(i));
        }
        input.pause();
    }

    private boolean medicalRecordIdExists(Patient patient, String recordId) {
        List<MedicalRecord> history = patient.getMedicalHistory();
        for (MedicalRecord record : history) {
            if (record.getRecordID().equals(recordId)) {
                return true;
            }
        }
        return false;
    }

    private void nursesMenu() {
        while (true) {
            renderer.clearScreen();
            renderer.header("Nurses");
            renderer.menuItem(1, "Add Nurse");
            renderer.menuItem(2, "List Nurses");
            renderer.menuItem(3, "Update Nurse Shift");
            renderer.menuItem(4, "Find Nurse By ID");
            renderer.menuItem(5, "Back");

            int choice = input.readIntInRange("Choose an option: ", 1, 5);
            switch (choice) {
                case 1:
                    addNurse();
                    break;
                case 2:
                    listNurses();
                    break;
                case 3:
                    updateNurseShift();
                    break;
                case 4:
                    findNurseById();
                    break;
                case 5:
                    return;
                default:
                    renderer.error("Invalid option.");
                    break;
            }
        }
    }

    private void addNurse() {
        renderer.section("Add Nurse");

        String id = input.readNonEmptyString("Nurse ID: ");
        if (service.findNurseById(id) != null) {
            renderer.error("Nurse ID already exists.");
            input.pause();
            return;
        }

        String name = input.readNonEmptyString("Name: ");
        int age = input.readInt("Age: ", 1);
        Gender gender = input.readGender("Gender");
        String phone = input.readNonEmptyString("Phone: ");
        String department = input.readNonEmptyString("Department: ");
        String shift = input.readNonEmptyString("Shift: ");

        Nurse nurse = new Nurse(id, age, name, gender, phone, department, shift);
        service.addNurse(nurse);
        renderer.success("Nurse added successfully.");
        input.pause();
    }

    private void listNurses() {
        renderer.section("Nurses List");
        List<Nurse> nurses = service.getNurses();
        if (nurses.isEmpty()) {
            renderer.warning("No nurses found.");
            input.pause();
            return;
        }

        List<String[]> rows = new ArrayList<>();
        for (Nurse nurse : nurses) {
            rows.add(new String[] {
                    nurse.getId(),
                    nurse.getName(),
                    nurse.getDepartment(),
                    nurse.getShift(),
                    nurse.getPhone()
            });
        }

        renderer.table(new String[] { "ID", "Name", "Department", "Shift", "Phone" }, rows);
        input.pause();
    }

    private void updateNurseShift() {
        renderer.section("Update Nurse Shift");
        String id = input.readNonEmptyString("Nurse ID: ");
        Nurse nurse = service.findNurseById(id);
        if (nurse == null) {
            renderer.error("Nurse not found.");
            input.pause();
            return;
        }

        String oldShift = nurse.getShift();
        String newShift = input.readNonEmptyString("New Shift: ");
        nurse.setShift(newShift);
        renderer.success("Shift updated from '" + oldShift + "' to '" + newShift + "'.");
        input.pause();
    }

    private void findNurseById() {
        renderer.section("Find Nurse");
        String id = input.readNonEmptyString("Nurse ID: ");
        Nurse nurse = service.findNurseById(id);
        if (nurse == null) {
            renderer.error("Nurse not found.");
            input.pause();
            return;
        }

        renderer.keyValue("ID", nurse.getId());
        renderer.keyValue("Name", nurse.getName());
        renderer.keyValue("Age", String.valueOf(nurse.getAge()));
        renderer.keyValue("Gender", nurse.getGender().name());
        renderer.keyValue("Phone", nurse.getPhone());
        renderer.keyValue("Department", nurse.getDepartment());
        renderer.keyValue("Shift", nurse.getShift());
        input.pause();
    }

    private void roomsMenu() {
        while (true) {
            renderer.clearScreen();
            renderer.header("Rooms & Admissions");
            renderer.menuItem(1, "Add Room");
            renderer.menuItem(2, "List Rooms");
            renderer.menuItem(3, "Find Available Room By Type");
            renderer.menuItem(4, "Admit Patient To Room");
            renderer.menuItem(5, "Discharge Patient From Room");
            renderer.menuItem(6, "Back");

            int choice = input.readIntInRange("Choose an option: ", 1, 6);
            switch (choice) {
                case 1:
                    addRoom();
                    break;
                case 2:
                    listRooms();
                    break;
                case 3:
                    findAvailableRoomByType();
                    break;
                case 4:
                    admitPatientToRoom();
                    break;
                case 5:
                    dischargePatientFromRoom();
                    break;
                case 6:
                    return;
                default:
                    renderer.error("Invalid option.");
                    break;
            }
        }
    }

    private void addRoom() {
        renderer.section("Add Room");
        int roomNumber = input.readInt("Room Number: ", 1);
        if (service.findRoomByNumber(roomNumber) != null) {
            renderer.error("Room number already exists.");
            input.pause();
            return;
        }

        String type = input.readNonEmptyString("Room Type: ");
        double pricePerNight = input.readPositiveDouble("Price Per Night: ");

        Room room = new Room(roomNumber, type, pricePerNight);
        service.addRoom(room);
        renderer.success("Room added successfully.");
        input.pause();
    }

    private void listRooms() {
        renderer.section("Rooms List");
        List<Room> rooms = service.getRooms();
        if (rooms.isEmpty()) {
            renderer.warning("No rooms found.");
            input.pause();
            return;
        }

        List<String[]> rows = new ArrayList<>();
        for (Room room : rooms) {
            String occupant = room.getCurrentPatient() == null ? "-" : room.getCurrentPatient().getName();
            rows.add(new String[] {
                    String.valueOf(room.getRoomNumber()),
                    room.getType(),
                    money(room.getPricePerNight()),
                    room.isOccupied() ? "Occupied" : "Available",
                    occupant
            });
        }

        renderer.table(new String[] { "Room #", "Type", "Rate", "Status", "Current Patient" }, rows);
        input.pause();
    }

    private void findAvailableRoomByType() {
        renderer.section("Find Available Room");
        String type = input.readNonEmptyString("Room Type: ");
        Room room = service.findAvailableRoom(type);
        if (room == null) {
            renderer.warning("No available rooms found for this type.");
            input.pause();
            return;
        }

        renderer.keyValue("Room Number", String.valueOf(room.getRoomNumber()));
        renderer.keyValue("Type", room.getType());
        renderer.keyValue("Rate", money(room.getPricePerNight()));
        renderer.keyValue("Status", room.isOccupied() ? "Occupied" : "Available");
        input.pause();
    }

    private void admitPatientToRoom() {
        renderer.section("Admit Patient");
        int roomNumber = input.readInt("Room Number: ", 1);
        Room room = service.findRoomByNumber(roomNumber);
        if (room == null) {
            renderer.error("Room not found.");
            input.pause();
            return;
        }

        if (room.isOccupied()) {
            String currentPatient = room.getCurrentPatient() == null ? "Unknown" : room.getCurrentPatient().getName();
            renderer.warning("Room is already occupied by " + currentPatient + ".");
            input.pause();
            return;
        }

        String patientId = input.readNonEmptyString("Patient ID: ");
        if (service.findPatientById(patientId) == null) {
            renderer.error("Patient not found.");
            input.pause();
            return;
        }

        boolean admitted = service.admitPatientToRoom(roomNumber, patientId);
        if (admitted) {
            renderer.success("Patient admitted successfully.");
        } else {
            renderer.error("Unable to admit patient to room.");
        }
        input.pause();
    }

    private void dischargePatientFromRoom() {
        renderer.section("Discharge Patient");
        int roomNumber = input.readInt("Room Number: ", 1);
        Room room = service.findRoomByNumber(roomNumber);
        if (room == null) {
            renderer.error("Room not found.");
            input.pause();
            return;
        }

        if (!room.isOccupied()) {
            renderer.warning("Room is already available.");
            input.pause();
            return;
        }

        boolean discharged = service.dischargePatientFromRoom(roomNumber);
        if (discharged) {
            renderer.success("Patient discharged successfully.");
        } else {
            renderer.error("Unable to discharge patient.");
        }
        input.pause();
    }

    private void appointmentsMenu() {
        while (true) {
            renderer.clearScreen();
            renderer.header("Appointments");
            renderer.menuItem(1, "Create Appointment");
            renderer.menuItem(2, "Schedule Appointment");
            renderer.menuItem(3, "Add/Edit Appointment Notes");
            renderer.menuItem(4, "Cancel Appointment");
            renderer.menuItem(5, "List Appointments");
            renderer.menuItem(6, "View Appointment Details");
            renderer.menuItem(7, "Back");

            int choice = input.readIntInRange("Choose an option: ", 1, 7);
            switch (choice) {
                case 1:
                    createAppointment();
                    break;
                case 2:
                    scheduleAppointment();
                    break;
                case 3:
                    editAppointmentNotes();
                    break;
                case 4:
                    cancelAppointment();
                    break;
                case 5:
                    listAppointments();
                    break;
                case 6:
                    viewAppointmentDetails();
                    break;
                case 7:
                    return;
                default:
                    renderer.error("Invalid option.");
                    break;
            }
        }
    }

    private void createAppointment() {
        renderer.section("Create Appointment");
        String appointmentId = input.readNonEmptyString("Appointment ID: ");
        if (service.findAppointmentById(appointmentId) != null) {
            renderer.error("Appointment ID already exists.");
            input.pause();
            return;
        }

        String doctorId = input.readNonEmptyString("Doctor ID: ");
        if (service.findDoctorById(doctorId) == null) {
            renderer.error("Doctor not found.");
            input.pause();
            return;
        }

        String patientId = input.readNonEmptyString("Patient ID: ");
        if (service.findPatientById(patientId) == null) {
            renderer.error("Patient not found.");
            input.pause();
            return;
        }

        Appointment appointment = service.createAppointment(appointmentId, doctorId, patientId);
        if (appointment == null) {
            renderer.error("Unable to create appointment.");
        } else {
            renderer.success("Appointment created successfully.");
        }
        input.pause();
    }

    private void scheduleAppointment() {
        renderer.section("Schedule Appointment");
        String appointmentId = input.readNonEmptyString("Appointment ID: ");
        Appointment appointment = service.findAppointmentById(appointmentId);
        if (appointment == null) {
            renderer.error("Appointment not found.");
            input.pause();
            return;
        }

        String dateTime = input.readNonEmptyString("Date & Time (e.g., 2026-04-15 10:00): ");
        boolean scheduled = service.scheduleAppointment(appointmentId, dateTime);
        if (scheduled) {
            renderer.success("Appointment scheduled successfully.");
        } else {
            renderer.error("Scheduling failed. Doctor might be unavailable.");
        }
        input.pause();
    }

    private void editAppointmentNotes() {
        renderer.section("Appointment Notes");
        String appointmentId = input.readNonEmptyString("Appointment ID: ");
        Appointment appointment = service.findAppointmentById(appointmentId);
        if (appointment == null) {
            renderer.error("Appointment not found.");
            input.pause();
            return;
        }

        String notes = input.readOptionalString("Notes (leave blank to clear): ");
        boolean updated = service.setAppointmentNotes(appointmentId, notes);
        if (updated) {
            renderer.success("Appointment notes updated.");
        } else {
            renderer.error("Could not update notes.");
        }
        input.pause();
    }

    private void cancelAppointment() {
        renderer.section("Cancel Appointment");
        String appointmentId = input.readNonEmptyString("Appointment ID: ");
        Appointment appointment = service.findAppointmentById(appointmentId);
        if (appointment == null) {
            renderer.error("Appointment not found.");
            input.pause();
            return;
        }

        if (!appointment.isConfirmed()) {
            renderer.warning("Appointment is already pending/not confirmed.");
            input.pause();
            return;
        }

        boolean cancelled = service.cancelAppointment(appointmentId);
        if (cancelled) {
            renderer.success("Appointment cancelled.");
        } else {
            renderer.error("Unable to cancel appointment.");
        }
        input.pause();
    }

    private void listAppointments() {
        renderer.section("Appointments List");
        List<Appointment> appointments = service.getAppointments();
        if (appointments.isEmpty()) {
            renderer.warning("No appointments found.");
            input.pause();
            return;
        }

        List<String[]> rows = new ArrayList<>();
        for (Appointment appointment : appointments) {
            rows.add(new String[] {
                    appointment.getAppointmentId(),
                    appointment.getDoctor().getName(),
                    appointment.getPatient().getName(),
                    appointment.getDateTime().isEmpty() ? "-" : appointment.getDateTime(),
                    appointment.isConfirmed() ? "Confirmed" : "Pending",
                    shorten(appointment.getNotes(), 28)
            });
        }

        renderer.table(new String[] { "ID", "Doctor", "Patient", "Date/Time", "Status", "Notes" }, rows);
        input.pause();
    }

    private void viewAppointmentDetails() {
        renderer.section("Appointment Details");
        String appointmentId = input.readNonEmptyString("Appointment ID: ");
        Appointment appointment = service.findAppointmentById(appointmentId);
        if (appointment == null) {
            renderer.error("Appointment not found.");
            input.pause();
            return;
        }

        renderer.keyValue("ID", appointment.getAppointmentId());
        renderer.keyValue("Doctor", appointment.getDoctor().getName() + " (" + appointment.getDoctor().getId() + ")");
        renderer.keyValue("Patient", appointment.getPatient().getName() + " (" + appointment.getPatient().getId() + ")");
        renderer.keyValue("Date & Time", appointment.getDateTime().isEmpty() ? "Not set" : appointment.getDateTime());
        renderer.keyValue("Status", appointment.isConfirmed() ? "Confirmed" : "Pending");
        renderer.keyValue("Notes", appointment.getNotes().isEmpty() ? "-" : appointment.getNotes());
        input.pause();
    }

    private void invoicesMenu() {
        while (true) {
            renderer.clearScreen();
            renderer.header("Invoices & Payments");
            renderer.menuItem(1, "Generate Invoice");
            renderer.menuItem(2, "List Invoices");
            renderer.menuItem(3, "View Invoice Details");
            renderer.menuItem(4, "Mark Invoice As Paid");
            renderer.menuItem(5, "Back");

            int choice = input.readIntInRange("Choose an option: ", 1, 5);
            switch (choice) {
                case 1:
                    generateInvoice();
                    break;
                case 2:
                    listInvoices();
                    break;
                case 3:
                    viewInvoiceDetails();
                    break;
                case 4:
                    markInvoiceAsPaid();
                    break;
                case 5:
                    return;
                default:
                    renderer.error("Invalid option.");
                    break;
            }
        }
    }

    private void generateInvoice() {
        renderer.section("Generate Invoice");
        String invoiceId = input.readNonEmptyString("Invoice ID: ");
        if (service.findInvoiceById(invoiceId) != null) {
            renderer.error("Invoice ID already exists.");
            input.pause();
            return;
        }

        String patientId = input.readNonEmptyString("Patient ID: ");
        if (service.findPatientById(patientId) == null) {
            renderer.error("Patient not found.");
            input.pause();
            return;
        }

        double consultationFee = input.readPositiveDouble("Consultation Fee: ");
        double roomCharges = input.readPositiveDouble("Room Charges: ");
        double medicationCharges = input.readPositiveDouble("Medication Charges: ");

        Invoice invoice = service.generateInvoice(invoiceId, patientId, consultationFee, roomCharges, medicationCharges);
        if (invoice == null) {
            renderer.error("Unable to generate invoice.");
        } else {
            renderer.success("Invoice generated successfully.");
        }
        input.pause();
    }

    private void listInvoices() {
        renderer.section("Invoices List");
        List<Invoice> invoices = service.getInvoices();
        if (invoices.isEmpty()) {
            renderer.warning("No invoices found.");
            input.pause();
            return;
        }

        List<String[]> rows = new ArrayList<>();
        for (Invoice invoice : invoices) {
            rows.add(new String[] {
                    invoice.getInvoicedId(),
                    invoice.getPatient().getName(),
                    money(invoice.getConsultationFee()),
                    money(invoice.getRoomCharges()),
                    money(invoice.getMedicationCharges()),
                    money(invoice.getTotal()),
                    invoice.isPaid() ? "Paid" : "Unpaid"
            });
        }

        renderer.table(new String[] { "ID", "Patient", "Consult", "Room", "Medication", "Total", "Status" }, rows);
        input.pause();
    }

    private void viewInvoiceDetails() {
        renderer.section("Invoice Details");
        String invoiceId = input.readNonEmptyString("Invoice ID: ");
        Invoice invoice = service.findInvoiceById(invoiceId);
        if (invoice == null) {
            renderer.error("Invoice not found.");
            input.pause();
            return;
        }

        renderer.keyValue("Invoice ID", invoice.getInvoicedId());
        renderer.keyValue("Patient", invoice.getPatient().getName() + " (" + invoice.getPatient().getId() + ")");
        renderer.keyValue("Consultation Fee", money(invoice.getConsultationFee()));
        renderer.keyValue("Room Charges", money(invoice.getRoomCharges()));
        renderer.keyValue("Medication Charges", money(invoice.getMedicationCharges()));
        renderer.keyValue("Total", money(invoice.getTotal()));
        renderer.keyValue("Status", invoice.isPaid() ? "Paid" : "Unpaid");
        input.pause();
    }

    private void markInvoiceAsPaid() {
        renderer.section("Mark Invoice As Paid");
        String invoiceId = input.readNonEmptyString("Invoice ID: ");
        Invoice invoice = service.findInvoiceById(invoiceId);
        if (invoice == null) {
            renderer.error("Invoice not found.");
            input.pause();
            return;
        }

        if (invoice.isPaid()) {
            renderer.warning("Invoice is already paid.");
            input.pause();
            return;
        }

        boolean paid = service.markInvoiceAsPaid(invoiceId);
        if (paid) {
            renderer.success("Invoice marked as paid.");
        } else {
            renderer.error("Could not update invoice.");
        }
        input.pause();
    }

    private void showReports() {
        renderer.clearScreen();
        renderer.header("Operational Reports");

        renderer.section("Entity Counts");
        renderer.keyValue("Doctors", String.valueOf(service.getDoctors().size()));
        renderer.keyValue("Patients", String.valueOf(service.getPatients().size()));
        renderer.keyValue("Nurses", String.valueOf(service.getNurses().size()));
        renderer.keyValue("Rooms", String.valueOf(service.getRooms().size()));
        renderer.keyValue("Appointments", String.valueOf(service.getAppointments().size()));
        renderer.keyValue("Invoices", String.valueOf(service.getInvoices().size()));

        List<Room> occupiedRooms = new ArrayList<>();
        for (Room room : service.getRooms()) {
            if (room.isOccupied()) {
                occupiedRooms.add(room);
            }
        }

        List<Appointment> pendingAppointments = new ArrayList<>();
        for (Appointment appointment : service.getAppointments()) {
            if (!appointment.isConfirmed()) {
                pendingAppointments.add(appointment);
            }
        }

        List<Invoice> unpaidInvoices = new ArrayList<>();
        for (Invoice invoice : service.getInvoices()) {
            if (!invoice.isPaid()) {
                unpaidInvoices.add(invoice);
            }
        }

        renderer.section("Occupied Rooms");
        if (occupiedRooms.isEmpty()) {
            renderer.info("No occupied rooms.");
        } else {
            List<String[]> rows = new ArrayList<>();
            for (Room room : occupiedRooms) {
                String patientName = room.getCurrentPatient() == null ? "-" : room.getCurrentPatient().getName();
                rows.add(new String[] {
                        String.valueOf(room.getRoomNumber()),
                        room.getType(),
                        patientName
                });
            }
            renderer.table(new String[] { "Room #", "Type", "Patient" }, rows);
        }

        renderer.section("Pending Appointments");
        if (pendingAppointments.isEmpty()) {
            renderer.info("No pending appointments.");
        } else {
            List<String[]> rows = new ArrayList<>();
            for (Appointment appointment : pendingAppointments) {
                rows.add(new String[] {
                        appointment.getAppointmentId(),
                        appointment.getDoctor().getName(),
                        appointment.getPatient().getName(),
                        appointment.getNotes().isEmpty() ? "-" : shorten(appointment.getNotes(), 30)
                });
            }
            renderer.table(new String[] { "ID", "Doctor", "Patient", "Notes" }, rows);
        }

        renderer.section("Unpaid Invoices");
        if (unpaidInvoices.isEmpty()) {
            renderer.info("No unpaid invoices.");
        } else {
            List<String[]> rows = new ArrayList<>();
            for (Invoice invoice : unpaidInvoices) {
                rows.add(new String[] {
                        invoice.getInvoicedId(),
                        invoice.getPatient().getName(),
                        money(invoice.getTotal())
                });
            }
            renderer.table(new String[] { "Invoice ID", "Patient", "Amount" }, rows);
        }

        input.pause();
    }

    private String money(double value) {
        return String.format("%.2f EGP", value);
    }

    private String shorten(String text, int maxLength) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        if (text.length() <= maxLength) {
            return text;
        }
        if (maxLength <= 3) {
            return text.substring(0, maxLength);
        }
        return text.substring(0, maxLength - 3) + "...";
    }
}
