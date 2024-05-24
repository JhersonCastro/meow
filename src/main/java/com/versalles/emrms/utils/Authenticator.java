package com.versalles.emrms.utils;

import java.io.File;
import com.versalles.emrms.models.Doctor;
import com.versalles.emrms.models.Patient;
/**
 *
 * @author JUANM
 */
public class Authenticator {
    private MyHashMap<String, String> credentials;

    public Authenticator() {
        this.credentials = DataPersistence.loadAllCredentials();
    }

    public String authenticate(String id, String password) {
        if (credentials.containsKey(id)) {
            if (credentials.get(id).equals(password)) {
                File doctorFile = new File(DataPersistence.DOCTORS_DIR + id + ".ser");
                File patientFile = new File(DataPersistence.PATIENTS_DIR + id + ".ser");

                if (doctorFile.exists()) {
                    Doctor doctor = DataPersistence.loadDoctor(id);
                    return "Authentication successful. Welcome, Dr. " + doctor.getName() + "!";
                } else if (patientFile.exists()) {
                    Patient patient = DataPersistence.loadPatient(id);
                    return "Authentication successful. Welcome, " + patient.getName() + "!";
                } else {
                    return "User data not found.";
                }
            } else {
                return "Incorrect password";
            }
        } else {
            return "User not registered";
        }
    }
}
