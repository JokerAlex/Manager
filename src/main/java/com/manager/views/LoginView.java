package com.manager.views;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class LoginView extends JFrame {
    private JLabel userNameLabel;
    private JLabel passLabel;


    private JTextField userNameText;
    private JPasswordField passwordField;

    private JButton confirmButton;


    public LoginView(){
        init();
    }

    private void init(){

        Dimension dimensionLabel = new Dimension(60,30);
        Dimension dimensionText = new Dimension(150,30);

        userNameLabel = new JLabel("用户名：");
        userNameLabel.setPreferredSize(dimensionLabel);
        userNameText = new JTextField();
        userNameText.setPreferredSize(dimensionText);

        passLabel = new JLabel("密  码：");
        passLabel.setPreferredSize(dimensionLabel);
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(dimensionText);


        confirmButton = new JButton("登录");

        JPanel userNameP = new JPanel();
        userNameP.add(userNameLabel);
        userNameP.add(userNameText);

        JPanel passP = new JPanel();
        passP.add(passLabel);
        passP.add(passwordField);


        JPanel userButotnP = new JPanel();
        userButotnP.add(confirmButton);

        this.setLayout(new FlowLayout());
        this.add(userNameP);
        this.add(passP);
        this.add(userButotnP);
        this.setTitle("欢迎登录");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(300,200);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public JTextField getUserNameText() {
        return userNameText;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public static void main(String[] args) {
        new ProductChangeView();
    }
}
