package com.versalles.emrms.manager;

import com.versalles.emrms.models.Doctor;
import com.versalles.emrms.models.Patient;
import com.versalles.emrms.structures.CircularList;
import com.versalles.emrms.structures.DoublyLinkedList;
import com.versalles.emrms.utils.DataPersistence;
import com.versalles.emrms.utils.ReadableDataPersistence;

/**
 *
 * @author JUANM
 */
public class AdminManager {

    private CircularList<Doctor> doctorList;
    private DoublyLinkedList<Patient> patientList;

    public AdminManager() {
        this.doctorList = DataPersistence.loadDoctors();
        this.patientList = DataPersistence.loadPatients();
    }

    public void addDoctor(String id, String name, String password, String address, String phoneNumber, String specialization) {
        if (isIdUnique(id)) {
            Doctor doctor = new Doctor(id, name, password, address, phoneNumber, specialization);
            doctorList.add(doctor);
            DataPersistence.saveDoctor(doctor);
            ReadableDataPersistence.saveDoctor(doctor);
        } else {
            System.out.println("Doctor ID already exists.");
        }
    }

    public void deleteDoctor(String doctorId) {
        int index = findDoctorIndexById(doctorId);
        if (index != -1) {
            doctorList.remove(index);
            DataPersistence.deleteDoctor(doctorId);
            ReadableDataPersistence.deleteDoctor(doctorId);
        } else {
            System.out.println("Doctor not found.");
        }
    }

    public void addPatient(String id, String name, String password, String address, String phoneNumber, int age) {
        if (isIdUnique(id)) {
            Patient patient = new Patient(id, name, password, address, phoneNumber, age);
            patientList.add(patient);
            DataPersistence.savePatient(patient);
            ReadableDataPersistence.savePatient(patient);
        } else {
            System.out.println("Patient ID already exists.");
        }
    }

    public void deletePatient(String patientId) {
        int index = findPatientIndexById(patientId);
        if (index != -1) {
            patientList.remove(index);
            DataPersistence.deletePatient(patientId);
            ReadableDataPersistence.deletePatient(patientId);
        } else {
            System.out.println("Patient not found.");
        }
    }

    public void updatePassword(String userId, String newPassword) {
        int doctorIndex = findDoctorIndexById(userId);
        if (doctorIndex != -1) {
            Doctor doctor = doctorList.get(doctorIndex);
            doctor.setPassword(newPassword);
            DataPersistence.saveDoctor(doctor);
            ReadableDataPersistence.saveDoctor(doctor);
        } else {
            int patientIndex = findPatientIndexById(userId);
            if (patientIndex != -1) {
                Patient patient = patientList.get(patientIndex);
                patient.setPassword(newPassword);
                DataPersistence.savePatient(patient);
                ReadableDataPersistence.savePatient(patient);
            } else {
                System.out.println("User not found.");
            }
        }
    }

    private boolean isIdUnique(String id) {
        return findDoctorIndexById(id) == -1 && findPatientIndexById(id) == -1;
    }

    private int findDoctorIndexById(String doctorId) {
        for (int i = 0; i < doctorList.size(); i++) {
            if (doctorList.get(i).getId().equals(doctorId)) {
                return i;
            }
        }
        return -1;
    }

    private int findPatientIndexById(String patientId) {
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getId().equals(patientId)) {
                return i;
            }
        }
        return -1;
    }

    public CircularList<Doctor> getDoctorList() {
        return doctorList;
    }

    public DoublyLinkedList<Patient> getPatientList() {
        return patientList;
    }
}
