package com.versalles.emrms.UI;

import com.versalles.emrms.Exceptions.BlankJTextField;
import com.versalles.emrms.Exceptions.NumberDoesntMatchException;
import com.versalles.emrms.Exceptions.UserDoesntExist;
import com.versalles.emrms.manager.AdminManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Admin {
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JTextField txtNamePatient;
    private JTextField txtIDPatient;
    private JTextField txtAddressPatient;
    private JTextField txtPhoneNumberPatient;
    private JPasswordField txtPasswordPatient;
    private JTextField txtAgePatient;
    private JButton btnSavePatient;
    private JTextField txtDeletePatient;
    private JButton btnDeletePatient;
    private JTextField txtRecoveryID;
    private JTextField txtRecoveryNewPassword;
    private JButton btnRecoveryUpdate;
    private JButton btnDocSave;
    private JTextField txtNameDoctor;
    private JTextField txtIDDoctor;
    private JPasswordField txtPasswordDoctor;
    private JTextField txtAddressDoctor;
    private JTextField txtPhoneNumberDoctor;
    private JTextField txtSpecializationDoctor;
    public JPanel mainPanel;
    private JScrollPane lblReport;
    private JTextField txtDeleteDoctor;
    private JButton btnDeleteDoctor;

    public Admin() {
        AdminManager adminManager = new AdminManager();
        //region Reports
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        for (int i = 0; i <= 100; i++){
            JLabel lblPatient = new JLabel("Patient " + i);
            panelContenido.add(lblPatient);
        }
        lblReport.setViewportView(panelContenido);
        //endregion
        //region Doctor
        btnDocSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTextField[] args = {
                        txtIDDoctor,
                        txtNameDoctor,
                        txtPasswordDoctor,
                        txtAddressDoctor,
                        txtPhoneNumberDoctor,
                        txtSpecializationDoctor
                };
                try{
                    checker(args);
                    adminManager.addDoctor(args[0].getText(), args[1].getText(), args[2].getText(), args[3].getText(), args[4].getText(), args[5].getText());
                    JOptionPane.showMessageDialog(null, "Se ha creado con exito!", "EmptyTextField Error", JOptionPane.INFORMATION_MESSAGE);
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Error en el: " + args[Integer.parseInt(ex.getMessage())].getName(), "EmptyTextField Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        btnDeleteDoctor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    adminManager.deleteDoctor(txtDeleteDoctor.getText());
                } catch (UserDoesntExist ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "User Doesnt Match Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        //endregion
        //region Patients
        btnSavePatient.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTextField[] args = {
                        txtIDPatient,
                        txtNamePatient,
                        txtPasswordPatient,
                        txtAddressPatient,
                        txtPhoneNumberPatient,
                        txtAgePatient
                };
                try{
                    checker(args);
                    int age = Integer.parseInt(args[5].getText());
                    Long.parseLong(args[4].getText());
                    if(age < 0)
                        throw new NumberDoesntMatchException("La edad no puede ser negativa");
                    if(!(args[4].getText().startsWith("3") && args[4].getText().length() == 10))
                        throw new NumberDoesntMatchException("El telefono ingresado no es valido");

                    adminManager.addPatient(args[0].getText(), args[1].getText(), args[2].getText(), args[3].getText(), args[4].getText(), age);
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
                try{
                    adminManager.deletePatient(txtDeletePatient.getText());
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "User doesnt exists", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        //endregion
        //region Recovery
        btnRecoveryUpdate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    adminManager.updatePassword(txtRecoveryID.getText(), txtRecoveryNewPassword.getText());
                } catch (UserDoesntExist ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "User Doesnt Match Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        //endregion

    }

    void checker(JTextField @NotNull [] args) throws  BlankJTextField {
        for (int i = 0; i < args.length; i++) {
            if (args[i].getText().isBlank()) {
                throw new BlankJTextField(i);
            }
        }
    }
}
