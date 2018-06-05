package com.manager.views;


import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class LoginView extends JFrame {

    JLabel labelUserName;
    JLabel labelPassword;
    JTextField textFieldUserName;
    JPasswordField passwordField;
    JButton buttonLogin;


    public LoginView() throws HeadlessException {
    }

    public void init() {

        labelUserName = new JLabel("用户名：");
        labelPassword = new JLabel("密  码：");
        textFieldUserName = new JTextField();
        passwordField = new JPasswordField();
        buttonLogin = new JButton("登录");

        Dimension dimension = new Dimension(100,20);
        textFieldUserName.setPreferredSize(dimension);
        passwordField.setPreferredSize(dimension);


        JPanel panelName = new JPanel();
        panelName.add(labelUserName);
        panelName.add(textFieldUserName);

        JPanel panelPass = new JPanel();
        panelPass.add(labelPassword);
        panelPass.add(passwordField);

        JPanel panelButton = new JPanel();
        panelButton.add(buttonLogin);


        //设置显示
        this.setLayout(new GridLayout(3,1));
        this.add(panelName);
        this.add(panelPass);
        this.add(panelButton);
        this.setTitle("欢迎登录");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(300,200);
        this.setLocationRelativeTo(null);
    }

    public JLabel getLabelUserName() {
        return labelUserName;
    }

    public void setLabelUserName(JLabel labelUserName) {
        this.labelUserName = labelUserName;
    }

    public JLabel getLabelPassword() {
        return labelPassword;
    }

    public void setLabelPassword(JLabel labelPassword) {
        this.labelPassword = labelPassword;
    }

    public JTextField getTextFieldUserName() {
        return textFieldUserName;
    }

    public void setTextFieldUserName(JTextField textFieldUserName) {
        this.textFieldUserName = textFieldUserName;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public JButton getButtonLogin() {
        return buttonLogin;
    }

    public void setButtonLogin(JButton buttonLogin) {
        this.buttonLogin = buttonLogin;
    }

}
