package com.versalles.emrms;

import com.versalles.emrms.UI.Admin;
import com.versalles.emrms.UI.LoginForm;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm();
            JDialog dialog = new JDialog();
            dialog.setTitle("Iniciar sesion");
            dialog.setContentPane(loginForm.mainPanel);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.pack();
            dialog.setModal(true); // Establecer el diálogo como modal
            dialog.setVisible(true);


        });
    }
}