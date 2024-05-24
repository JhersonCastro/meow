package com.versalles.emrms.utils;

import java.io.*;
import java.time.format.DateTimeFormatter;
import com.versalles.emrms.models.*;
import com.versalles.emrms.structures.ListInterface;
import com.versalles.emrms.structures.Queue;

/**
 *
 * @author JUANM
 */
public class ReadableDataPersistence {

    private static final String DATA_STORAGE_DIR = "DataStorage/";
    private static final String DOCTORS_DIR = DATA_STORAGE_DIR + "ReadableData/doctors/";
    private static final String PATIENTS_DIR = DATA_STORAGE_DIR + "ReadableData/patients/";
    private static final String SHARED_DIR = DATA_STORAGE_DIR + "ReadableData/shared/";

    static {
        new File(DOCTORS_DIR).mkdirs();
        new File(PATIENTS_DIR).mkdirs();
        new File(SHARED_DIR).mkdirs();
    }

    public static void saveDoctor(Doctor doctor) {
        try (PrintWriter out = new PrintWriter(new FileWriter(DOCTORS_DIR + doctor.getId() + ".txt"))) {
            out.println("Doctor Information:");
            out.println("--------------------");
            out.println("ID: " + doctor.getId());
            out.println("Name: " + doctor.getName());
            out.println("Password: " + doctor.getPassword());
            out.println("Address: " + doctor.getAddress());
            out.println("Phone Number: " + doctor.getPhoneNumber());
            out.println("Specialization: " + doctor.getSpecialization());
            out.println();
            out.println("Patients:");
            out.println("---------");
            for (int i = 0; i < doctor.getPatients().size(); i++) {
                Patient patient = doctor.getPatients().get(i);
                out.println((i + 1) + ". " + patient.getId() + " - " + patient.getName());
            }
            out.println();
            out.println("Appointments:");
            out.println("-------------");
            for (int i = 0; i < doctor.getAppointments().size(); i++) {
                Appointment appointment = doctor.getAppointments().get(i);
                out.println((i + 1) + ". Appointment ID: " + appointment.getAppointmentId() + "  Date: " + appointment.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        + "  Time: " + appointment.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")) + "  Patient ID: " + appointment.getPatientId()
                        + "  Notes: " + appointment.getNotes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePatient(Patient patient) {
        try (PrintWriter out = new PrintWriter(new FileWriter(PATIENTS_DIR + patient.getId() + ".txt"))) {
            out.println("Patient Information:");
            out.println("---------------------");
            out.println("ID: " + patient.getId());
            out.println("Name: " + patient.getName());
            out.println("Password: " + patient.getPassword());
            out.println("Address: " + patient.getAddress());
            out.println("Phone Number: " + patient.getPhoneNumber());
            out.println("Age: " + patient.getAge());
            out.println();
            out.println("Appointments:");
            out.println("-------------");
            for (int i = 0; i < patient.getAppointments().size(); i++) {
                Appointment appointment = patient.getAppointments().get(i);
                out.println((i + 1) + ". Appointment ID: " + appointment.getAppointmentId() + "  Date: " + appointment.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        + "  Time: " + appointment.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")) + "  Doctor ID: " + appointment.getDoctorId()
                        + "  Notes: " + appointment.getNotes());
            }
            out.println();
            out.println("Medical Information:");
            out.println("---------------------");
            MedicalInformation mi = patient.getMedicalInformation();
            if (mi != null) {
                out.println("Patient ID: " + mi.getPatientId());
                out.println("Diagnosis: " + mi.getDiagnosis());
                out.println("Treatment: " + mi.getTreatment());
                out.println("Notes: " + mi.getNotes());
                out.println("Current Medications: ");
                for (int i = 0; i < mi.getCurrentMedications().size(); i++) {
                    out.println((i + 1) + ". " + mi.getCurrentMedications().get(i));
                }
                out.println("Medical History: ");
                for (int i = 0; i < mi.getMedicalHistory().size(); i++) {
                    out.println((i + 1) + ". " + mi.getMedicalHistory().get(i));
                }
            } else {
                out.println("No medical information available.");
            }
            out.println();
            out.println("Triage:");
            out.println("-------");
            Triage triage = patient.getTriage();
            if (triage != null) {
                out.println("Priority Level: " + triage.getPriorityLevel());
                out.println("Description: " + triage.getDescription());
            } else {
                out.println("No triage information available.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveDoctors(ListInterface<Doctor> doctors) {
        try (PrintWriter out = new PrintWriter(new FileWriter(SHARED_DIR + "doctors.txt"))) {
            out.println("Doctors List:");
            out.println("-------------");
            for (int i = 0; i < doctors.size(); i++) {
                Doctor doctor = doctors.get(i);
                out.println((i + 1) + ". ID: " + doctor.getId() + "  Name: " + doctor.getName() + "  Specialization: " + doctor.getSpecialization());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePatients(ListInterface<Patient> patients) {
        try (PrintWriter out = new PrintWriter(new FileWriter(SHARED_DIR + "patients.txt"))) {
            out.println("Patients List:");
            out.println("--------------");
            for (int i = 0; i < patients.size(); i++) {
                Patient patient = patients.get(i);
                out.println((i + 1) + ". ID: " + patient.getId() + "  Name: " + patient.getName() + "  Age: " + patient.getAge());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveAppointments(Queue<Appointment> appointments) {
        try (PrintWriter out = new PrintWriter(new FileWriter(SHARED_DIR + "appointments.txt"))) {
            out.println("Appointments List:");
            out.println("------------------");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            for (int i = 0; i < appointments.size(); i++) {
                Appointment appointment = appointments.get(i);
                out.println((i + 1) + ". Appointment ID: " + appointment.getAppointmentId() + "  Date: " + appointment.getDateTime().format(formatter)
                        + "  Patient ID: " + appointment.getPatientId() + "  Doctor ID: " + appointment.getDoctorId() + "  Notes: " + appointment.getNotes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void deleteDoctor(String doctorId) {
        File doctorFile = new File(DOCTORS_DIR + doctorId + ".txt");
        if (doctorFile.exists()) {
            doctorFile.delete();
        }
    }

    public static void deletePatient(String patientId) {
        File patientFile = new File(PATIENTS_DIR + patientId + ".txt");
        if (patientFile.exists()) {
            patientFile.delete();
        }
    }
}
