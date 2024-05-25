package com.versalles.emrms.utils;

import java.io.*;

import com.versalles.emrms.models.*;
import com.versalles.emrms.structures.*;

/**
 *
 * @author JUANM
 */
public class DataPersistence {

    public static final String DATA_STORAGE_DIR = "DataStorage/";
    public static final String DOCTORS_DIR = DATA_STORAGE_DIR + "SerializedData/doctors/";
    public static final String PATIENTS_DIR = DATA_STORAGE_DIR + "SerializedData/patients/";
    public static final String SHARED_DIR = DATA_STORAGE_DIR + "SerializedData/shared/";

    static {
        new File(DOCTORS_DIR).mkdirs();
        new File(PATIENTS_DIR).mkdirs();
        new File(SHARED_DIR).mkdirs();
    }

    public static void saveDoctor(Doctor doctor) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DOCTORS_DIR + doctor.getId() + ".ser"))) {
            oos.writeObject(doctor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Doctor loadDoctor(String doctorId) {
        Doctor doctor = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DOCTORS_DIR + doctorId + ".ser"))) {
            doctor = (Doctor) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return doctor;
    }

    public static void savePatient(Patient patient) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATIENTS_DIR + patient.getId() + ".ser"))) {
            oos.writeObject(patient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Patient loadPatient(String patientId) {
        Patient patient = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATIENTS_DIR + patientId + ".ser"))) {
            patient = (Patient) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return patient;
    }

    public static void deleteDoctor(String doctorId) {
        File doctorFile = new File(DOCTORS_DIR + doctorId + ".ser");
        if (doctorFile.exists()) {
            doctorFile.delete();
        }
    }

    public static void deletePatient(String patientId) {
        File patientFile = new File(PATIENTS_DIR + patientId + ".ser");
        if (patientFile.exists()) {
            patientFile.delete();
        }
    }
    
    
    
    public static void saveDoctors(ListInterface<Doctor> doctors) {
        for (int i = 0; i < doctors.size(); i++) {
            saveDoctor(doctors.get(i));
        }
    }

    public static CircularList<Doctor> loadDoctors() {
        CircularList<Doctor> doctorList = new CircularList<>();
        File folder = new File(DOCTORS_DIR);
        for (File file : folder.listFiles()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Doctor doctor = (Doctor) ois.readObject();
                doctorList.add(doctor);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return doctorList;
    }

    public static void savePatients(ListInterface<Patient> patients) {
        for (int i = 0; i < patients.size(); i++) {
            savePatient(patients.get(i));
        }
    }

    public static DoublyLinkedList<Patient> loadPatients() {
        DoublyLinkedList<Patient> patientList = new DoublyLinkedList<>();
        File folder = new File(PATIENTS_DIR);
        for (File file : folder.listFiles()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Patient patient = (Patient) ois.readObject();
                patientList.add(patient);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return patientList;
    }

    public static void saveAppointments(Queue<Appointment> appointments) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SHARED_DIR + "appointments.ser"))) {
            oos.writeObject(appointments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Queue<Appointment> loadAppointments() {
        Queue<Appointment> appointments = new Queue<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SHARED_DIR + "appointments.ser"))) {
            appointments = (Queue<Appointment>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return appointments;
    }
    public  static MyHashMap<String, String> loadAllCredentials(){
        MyHashMap<String, String> credentials = new MyHashMap<>();

        File[] doctorFiles = new File(DOCTORS_DIR).listFiles();
        if (doctorFiles != null) {
            for (File file : doctorFiles) {
                if (file.isFile() && file.getName().endsWith(".ser")) {
                    Doctor doctor = loadDoctor(file.getName().replace(".ser", ""));
                    if (doctor != null) {
                        credentials.put(doctor.getId(), doctor.getPassword());
                    }
                }
            }
        }
        File[] patientFiles = new File(PATIENTS_DIR).listFiles();
        if (patientFiles != null) {
            for (File file : patientFiles) {
                if (file.isFile() && file.getName().endsWith(".ser")) {
                    Patient patient = loadPatient(file.getName().replace(".ser", ""));
                    if (patient != null) {
                        credentials.put(patient.getId(), patient.getPassword());
                    }
                }
            }
        }
        return credentials;
    }
    public static MyHashMap<String, String> loadAllCredentialsDoctor() {
        MyHashMap<String, String> credentials = new MyHashMap<>();

        File[] doctorFiles = new File(DOCTORS_DIR).listFiles();
        if (doctorFiles != null) {
            for (File file : doctorFiles) {
                if (file.isFile() && file.getName().endsWith(".ser")) {
                    Doctor doctor = loadDoctor(file.getName().replace(".ser", ""));
                    if (doctor != null) {
                        credentials.put(doctor.getId(), doctor.getPassword());
                    }
                }
            }
        }
        return credentials;
    }
    public static MyHashMap<String, String> loadAllCredentialsPatient() {
        MyHashMap<String, String> credentials = new MyHashMap<>();

        File[] patientFiles = new File(PATIENTS_DIR).listFiles();
        if (patientFiles != null) {
            for (File file : patientFiles) {
                if (file.isFile() && file.getName().endsWith(".ser")) {
                    Patient patient = loadPatient(file.getName().replace(".ser", ""));
                    if (patient != null) {
                        credentials.put(patient.getId(), patient.getPassword());
                    }
                }
            }
        }
        return credentials;
    }

}
