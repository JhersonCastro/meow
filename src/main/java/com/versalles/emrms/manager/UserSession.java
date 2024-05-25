package com.versalles.emrms.manager;

import com.versalles.emrms.UI.Admin;
import com.versalles.emrms.models.Doctor;
import com.versalles.emrms.models.Patient;
import com.versalles.emrms.models.User;
import com.versalles.emrms.structures.CircularList;
import com.versalles.emrms.structures.DoublyLinkedList;
import com.versalles.emrms.utils.MyHashMap;
import com.versalles.emrms.utils.Searching;

import javax.swing.*;
import java.util.Comparator;
import java.util.HashMap;

import static com.versalles.emrms.utils.DataPersistence.*;

/**
 *
 * @author JUANM
 */
public class UserSession {
    private MyHashMap<String, String> credentialsDoctor = loadAllCredentialsDoctor();
    private MyHashMap<String, String> credentialsPatient = loadAllCredentialsPatient();
    public void Refresh() {
        credentialsDoctor = loadAllCredentialsDoctor();
        credentialsPatient = loadAllCredentialsPatient();
    }
    public  void checkCredentials(String ID, String password) {
        if (password.equals(credentialsDoctor.get(ID))) {
            Doctor currentDoctor = loadDoctor(ID);
            SwingUtilities.invokeLater(() -> {
                com.versalles.emrms.UI.Doctor doctorForm = new com.versalles.emrms.UI.Doctor(currentDoctor);
                JDialog dialog = new JDialog();
                dialog.setTitle("Doctor's Menu");
                dialog.setContentPane(doctorForm.mainPanel);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.pack();
                dialog.setModal(true);
                dialog.setVisible(true);
            });
        }
        else if(password.equals(credentialsPatient.get(ID))) {
            Patient currentPatient = loadPatient(ID);
            SwingUtilities.invokeLater(() -> {
                com.versalles.emrms.UI.Patient patientForm = new com.versalles.emrms.UI.Patient(currentPatient);
                JDialog dialog = new JDialog();
                dialog.setTitle("Patient's Menu");
                dialog.setContentPane(patientForm.mainPanel);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.pack();
                dialog.setModal(true);
                dialog.setVisible(true);
            });
        }
        else if(ID.equals("Admin") && password.equals("Admin")) {
            SwingUtilities.invokeLater(() -> {
                com.versalles.emrms.UI.Admin adminForm = new com.versalles.emrms.UI.Admin();
                JDialog dialog = new JDialog();
                dialog.setTitle("Admin's Menu");
                dialog.setContentPane(adminForm.mainPanel);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.pack();
                dialog.setModal(true);
                dialog.setVisible(true);
            });
        }
        else
            JOptionPane.showMessageDialog(null, "Wrong credentials");
    }










}
