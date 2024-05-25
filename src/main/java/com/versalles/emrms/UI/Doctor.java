package com.versalles.emrms.UI;

import com.versalles.emrms.Exceptions.BlankJTextField;
import com.versalles.emrms.Exceptions.NumberDoesntMatchException;
import com.versalles.emrms.Exceptions.UserDoesntExist;
import com.versalles.emrms.manager.DoctorManager;
import com.versalles.emrms.models.Patient;
import com.versalles.emrms.structures.ListInterface;
import com.versalles.emrms.utils.DataPersistence;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Doctor {
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JButton undoButton;
    private JButton redoButton;
    private JTabbedPane tabbedPane3;
    private JTabbedPane tabbedPane4;
    private JTextField txtNameRegPatient;
    private JTextField txtIDRegPatient;
    private JTextField txtAddressRegPatient;
    private JTextField txtPhoneNumberRegPatient;
    private JPasswordField txtPasswordRegPatient;
    private JTextField txtAgeRegPatient;
    private JButton btnSaveRegPatient;
    private JButton btnUpdate;
    private JButton btnDeletePatient;
    private JPanel UpdatePatientPanel;
    private JButton USEWITHTHEBUFFERButton;
    private JTabbedPane tabbedPane5;
    private JTabbedPane tabbedPane6;
    private JTabbedPane tabbedPane7;
    private JTextField txtBufferID;
    private JButton btnSaveBufferID;
    private JButton USETHEBUFFERIDButton;
    private JTextField txtAddDateAppointment;
    private JTextField txtAddTimeAppointment;
    private JTextField txtAddAppointmentID;
    private JTextArea txtAreaAddNotesAppointment;
    private JButton btnAddAppointment;
    private JButton btnUpdateAppointment;
    private JButton btnDeleteAppointment;
    private JTextField txtAppointmentIDSearch;
    private JButton confirmButton;
    private JTextField txtRecoverySpecialization;
    private JTextArea txtAreaNotesUpd;
    private JTextField txtUpdTimeAppointments;
    private JTextField txtUpdateDateAppointments;
    private JTextField txtAppointmentIDSearchByUpd;
    public JPanel mainPanel;
    private JTextField txtUpdName;
    private JTextField txtUpdAddress;
    private JTextField txtUpdPhoneNumber;
    private JTextField txtUpdAge;
    private JButton btnUpdatePatient;
    private JScrollPane viewPortScrollPatient;
    private JPanel viewPanel;
    private JScrollPane viewPortAppointments;
    private JPasswordField txtRecoveryPassword;
    private JTextField txtRecoveryAddress;
    private JTextField txtRecoveryPhoneNumber;
    private JButton btnRecovery;
    private String BufferID;

    private com.versalles.emrms.models.Doctor currentDoctor;
    public Doctor(com.versalles.emrms.models.Doctor Doctor) {
        currentDoctor = Doctor;

        DoctorManager doctorManager = new DoctorManager(currentDoctor);
        btnSaveBufferID.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                BufferID = txtBufferID.getText();
                if (doctorManager.findPatientById(BufferID) == null) {
                    BufferID = "";
                    JOptionPane.showMessageDialog(null, "No se encontro el paciente con ese id");
                    UpdatePatientPanel.setVisible(false);
                    UpdatePatientPanel.setEnabled(false);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Se encontro el paciente con ese id");
                    UpdatePatientPanel.setVisible(true);
                    UpdatePatientPanel.setEnabled(true);
                }
            }
        });
        //region patient
        btnSaveRegPatient.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTextField[] args = {
                        txtIDRegPatient,
                        txtNameRegPatient,
                        txtPasswordRegPatient,
                        txtAgeRegPatient,
                        txtPhoneNumberRegPatient,
                        txtAgeRegPatient
                };
                try{
                    checker(args);
                    int age = Integer.parseInt(args[5].getText());
                    Long.parseLong(args[4].getText());
                    if(age < 0)
                        throw new NumberDoesntMatchException("La edad no puede ser negativa");
                    if(!(args[4].getText().startsWith("3") && args[4].getText().length() == 10))
                        throw new NumberDoesntMatchException("El telefono ingresado no es valido");

                    doctorManager.registerNewPatient(args[0].getText(), args[1].getText(), args[2].getText(), args[3].getText(), args[4].getText(), age);
                    DataPersistence.saveDoctor(currentDoctor);
                    JOptionPane.showMessageDialog(null, "Se ha creado con exito!", "EmptyTextField Error", JOptionPane.INFORMATION_MESSAGE);
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
        btnDeletePatient.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    doctorManager.deletePatient(BufferID);
                    DataPersistence.saveDoctor(currentDoctor);
                } catch (UserDoesntExist ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        btnUpdatePatient.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTextField[] args = {
                        txtUpdName,
                        txtUpdAddress,
                        txtUpdPhoneNumber,
                        txtUpdAge
                };
                try{
                    checker(args);
                    int age = Integer.parseInt(args[3].getText());
                    Long.parseLong(args[2].getText());

                    if(age < 0)
                        throw new NumberDoesntMatchException("La edad no puede ser negativa");
                    if(!(args[2].getText().startsWith("3") && args[2].getText().length() == 10))
                        throw new NumberDoesntMatchException("El telefono ingresado no es valido");

                    doctorManager.updatePatientRecord(BufferID, args[0].getText(), args[1].getText(), args[2].getText(), age);
                    DataPersistence.saveDoctor(currentDoctor);
                    JOptionPane.showMessageDialog(null, "Se ha actualizado con exito!", "EmptyTextField Error", JOptionPane.INFORMATION_MESSAGE);
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
        //region view
        tabbedPane2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                DataPersistence.saveDoctor(currentDoctor);
                if(tabbedPane2.getSelectedIndex() == 2){
                    JPanel panelContenido = new JPanel();
                    ListInterface<Patient> tempPatient = doctorManager.viewPatientList();

                    panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
                    for (int i = 0; i < tempPatient.size(); i++){
                        JLabel lblPatient = new JLabel(tempPatient.get(i).toString());
                        panelContenido.add(lblPatient);
                    }
                    viewPortScrollPatient.setViewportView(panelContenido);
                }
            }
        });
        USEWITHTHEBUFFERButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try{
                   com.versalles.emrms.models.Patient patient =  doctorManager.searchPatient(BufferID).get(0);
                   JOptionPane.showMessageDialog(null, patient.toString());
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "No se encontro el paciente, o no hay nada en el buffer");
                }
            }
        });
        //endregion
        //endregion
        //region Appointment
        btnAddAppointment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (BufferID.isEmpty()){
                    JOptionPane.showMessageDialog(null, "NO SE PUEDE UTILIZAR SIN EL BUFFER ID");
                    return;
                }
                try {
                    JTextField args[] = {
                            txtAddAppointmentID,
                            txtAddDateAppointment,
                            txtAddTimeAppointment
                    };
                    checker(args);
                    StringBuilder recovery = new StringBuilder();
                    String[] split = args[1].getText().split("/");
                    for (String s : split) {
                        if (0 < Integer.parseInt(s) && Integer.parseInt(s) > 31) {
                            throw new NumberDoesntMatchException("La fecha es negativa, o superior a 31, o no se puede convertir a numero");
                        }
                        recovery.append(s);
                    }
                    split = args[2].getText().split(":");
                    if(0 < Integer.parseInt(split[0]) && Integer.parseInt(split[0]) > 24){
                        throw new NumberDoesntMatchException("La hora es negativa, o superior a 24 horas, o no se puede convertir a numero");
                    }
                    if(0 < Integer.parseInt(split[1]) && Integer.parseInt(split[1]) > 60){
                        throw new NumberDoesntMatchException("Los minutos son negativos, o superior a 60 minutos, o no se puede convertir a numero");
                    }
                    String temp = "Date : " + args[1].getText() + " Time: " + args[2].getText();
                    doctorManager.addAppointment(args[0].getText(), BufferID, temp, txtAreaAddNotesAppointment.getText());
                    DataPersistence.saveDoctor(currentDoctor);

                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        btnUpdateAppointment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try{
                    JTextField[] args = {
                            txtAppointmentIDSearchByUpd,
                            txtUpdateDateAppointments,
                            txtUpdTimeAppointments
                    };
                checker(args);
                StringBuilder recovery = new StringBuilder();
                String[] split = args[1].getText().split("/");
                for (String s : split) {
                    if (0 < Integer.parseInt(s) && Integer.parseInt(s) > 31)
                        throw new NumberDoesntMatchException("La fecha es negativa, o superior a 31, o no se puede convertir a numero");

                    recovery.append(s);
                }
                split = args[2].getText().split(":");

                if(0 < Integer.parseInt(split[0]) && Integer.parseInt(split[0]) > 24)
                    throw new NumberDoesntMatchException("La hora es negativa, o superior a 24 horas, o no se puede convertir a numero");
                if(0 < Integer.parseInt(split[1]) && Integer.parseInt(split[1]) > 60)
                    throw new NumberDoesntMatchException("Los minutos son negativos, o superior a 60 minutos, o no se puede convertir a numero");

                recovery.append("   ===   ");
                recovery.append(split[0]);
                recovery.append(split[1]);

                String temp = "Date : " + args[1].getText() + " Time: " + args[2].getText();

                    doctorManager.updateAppointment(args[0].getText(), temp, txtAreaNotesUpd.getText());
                DataPersistence.saveDoctor(currentDoctor);

                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        btnDeleteAppointment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try{
                    doctorManager.deleteAppointment(txtAppointmentIDSearchByUpd.getText());
                    DataPersistence.saveDoctor(currentDoctor);
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        tabbedPane3.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                DataPersistence.saveDoctor(currentDoctor);
                if(tabbedPane3.getSelectedIndex() == 2){
                    JPanel panelContenido1 = new JPanel();

                    panelContenido1.setLayout(new BoxLayout(panelContenido1, BoxLayout.Y_AXIS));
                    for(int i = 0; i< currentDoctor.getAppointments().size(); i++){
                        JLabel lblPatient1 = new JLabel(currentDoctor.getAppointments().get(i).toString());
                        panelContenido1.add(lblPatient1);
                    }
                    viewPortAppointments.setViewportView(panelContenido1);

                }
            }
        });
        confirmButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try{
                    com.versalles.emrms.models.Appointment temp = doctorManager.findAppointmentById(txtAppointmentIDSearch.getText());
                    JOptionPane.showMessageDialog(null, temp.toString());
                }catch(Exception ex) {
                    JOptionPane.showMessageDialog(null, "No se encontro el appointment o el appointment id es nulo");
                }
            }
        });
        //endregion

        btnRecovery.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTextField[] args ={
                        txtRecoverySpecialization,
                        txtRecoveryPassword,
                        txtRecoveryAddress,
                        txtRecoveryPhoneNumber
                };
                try{
                    checker(args);
                    Long.parseLong(args[3].getText());
                    if(!(args[3].getText().startsWith("3") && args[3].getText().length() == 10)){
                        throw new NumberDoesntMatchException("El telefono ingresado no es valido");
                    }

                    doctorManager.updatePersonalInformation(args[2].getText(), args[3].getText(), args[1].getText(), args[0].getText());
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
    }
    void checker(JTextField @NotNull [] args) throws  BlankJTextField {
        for (int i = 0; i < args.length; i++) {
            if (args[i].getText().isBlank()) {
                throw new BlankJTextField(i);
            }
        }
    }

}
