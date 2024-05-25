package com.versalles.emrms.UI;

import com.versalles.emrms.Exceptions.BlankJTextField;
import com.versalles.emrms.Exceptions.NumberDoesntMatchException;
import com.versalles.emrms.manager.PatientManager;
import com.versalles.emrms.models.Doctor;
import com.versalles.emrms.utils.DataPersistence;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Patient {
    public JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JTabbedPane tabbedPane3;
    private JTextField txtRequestDateAppointment;
    private JTextField txtRequestTimeAppointment;
    private JButton btnRequest;
    private JLabel lblCurrentDoctor;
    private JTextField txtRecoveryAddress;
    private JTextField txtRecoveryPhoneNumber;
    private JPasswordField txtRecoveryPassword;
    private JButton btnRecovery;
    private com.versalles.emrms.models.Patient currentPatient;
    public Patient(com.versalles.emrms.models.Patient currentPatient){
        this.currentPatient = currentPatient;
        PatientManager patientManager = new PatientManager(currentPatient, DataPersistence.loadDoctors());
        btnRequest.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTextField[] args = {
                        txtRequestDateAppointment,
                        txtRequestTimeAppointment
                };
                try{
                    String[] ID = java.util.UUID.randomUUID().toString().split("-");

                    checker(args);
                    StringBuilder recovery = new StringBuilder();

                    String[] split = args[0].getText().split("/");
                    for (String s : split) {
                        if (0 < Integer.parseInt(s) && Integer.parseInt(s) > 31) {
                            throw new NumberDoesntMatchException("La fecha es negativa, o superior a 31, o no se puede convertir a numero");
                        }
                        recovery.append(s);
                    }
                    split = args[1].getText().split(":");
                    if(0 < Integer.parseInt(split[0]) && Integer.parseInt(split[0]) > 24){
                        throw new NumberDoesntMatchException("La hora es negativa, o superior a 24 horas, o no se puede convertir a numero");
                    }
                    if(0 < Integer.parseInt(split[1]) && Integer.parseInt(split[1]) > 60){
                        throw new NumberDoesntMatchException("Los minutos son negativos, o superior a 60 minutos, o no se puede convertir a numero");
                    }
                    String temp = "Date : " + args[0].getText() + " Time: " + args[1].getText();
                    Doctor assignedDoctor = patientManager.getDoctorList().getCurrent();
                    lblCurrentDoctor.setText(assignedDoctor.getName());
                    patientManager.requestAppointment(ID[0], temp, assignedDoctor);
                    JOptionPane.showMessageDialog(null, "Se ha creado con exito su appointment");

                }catch (Exception ex){
                    if(ex instanceof NumberDoesntMatchException)
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "NumberDoesntMatchException", JOptionPane.INFORMATION_MESSAGE);
                    else if (ex instanceof NumberFormatException)
                        JOptionPane.showMessageDialog(null, "No se ha ingresado un numero " + ex.getMessage(), "Number Format Exception", JOptionPane.INFORMATION_MESSAGE);
                    else if(ex instanceof BlankJTextField)
                        JOptionPane.showMessageDialog(null, "Error en el: " + args[((BlankJTextField) ex).getIndex()].getName(),
                                "EmptyTextField Error", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        tabbedPane3.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(tabbedPane3.getSelectedIndex() == 1){

                }
            }
        });
        btnRecovery.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTextField[] args = {
                        txtRecoveryPassword,
                        txtRecoveryAddress,
                        txtRecoveryPhoneNumber
                };
                try{
                    checker(args);
                    Long.parseLong(args[2].getText());
                    if(!(args[2].getText().startsWith("3") && args[2].getText().length() == 10)){
                        throw new NumberDoesntMatchException("El telefono ingresado no es valido");
                    }

                    patientManager.updatePersonalInformation(args[1].getText(), args[2].getText(), args[0].getText());
                }catch (Exception ex){
                    switch (ex) {
                        case NumberDoesntMatchException numberDoesntMatchException ->
                                JOptionPane.showMessageDialog(null, ex.getMessage(), "NumberDoesntMatchException", JOptionPane.INFORMATION_MESSAGE);
                        case NumberFormatException numberFormatException ->
                                JOptionPane.showMessageDialog(null, "No se ha ingresado un numero " + ex.getMessage(), "Number Format Exception", JOptionPane.INFORMATION_MESSAGE);
                        case BlankJTextField blankJTextField ->
                                JOptionPane.showMessageDialog(null, "Error en el: " + args[blankJTextField.getIndex()].getName(),
                                        "EmptyTextField Error", JOptionPane.INFORMATION_MESSAGE);
                        default -> JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            }
        });
    }
    void checker(JTextField @NotNull [] args) throws BlankJTextField {
        for (int i = 0; i < args.length; i++) {
            if (args[i].getText().isBlank()) {
                throw new BlankJTextField(i);
            }
        }
    }
}
