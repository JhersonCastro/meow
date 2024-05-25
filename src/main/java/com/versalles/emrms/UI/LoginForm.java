package com.versalles.emrms.UI;

import com.versalles.emrms.manager.UserSession;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginForm {
    public JPanel mainPanel;
    private JButton btnLogin;
    private JTextField txtId;
    private JTextField txtPassword;

    public LoginForm() {
        UserSession session = new UserSession();
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                session.Refresh();
                session.checkCredentials(txtId.getText(), txtPassword.getText());
            }
        });
    }
}
