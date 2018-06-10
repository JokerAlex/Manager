package com.manager.controller;


import com.manager.pojo.User;
import com.manager.service.LoginService;
import com.manager.views.LoginView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import javax.swing.*;

@Controller
public class LoginController {

    private LoginView loginView;
    private LoginService loginService;
    private ApplicationContext context;

    @Autowired
    public LoginController(LoginView loginView, LoginService loginService) {
        this.loginView = loginView;
        this.loginService = loginService;
    }

    public void initController() {
        loginView.getConfirmButton().addActionListener(e -> doLogin());
    }

    private void doLogin() {
        String userName = loginView.getUserNameText().getText();
        String password = new String(loginView.getPasswordField().getPassword());
        if (userName == null || userName.equals("")) {
            JOptionPane.showMessageDialog(null, "用户名不能为空", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else if (password.equals("")) {
            JOptionPane.showMessageDialog(null, "密码不能为空", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {
            User user = loginService.checkUser(userName, password);
            if (user != null) {
                loginView.setVisible(false);
                MainController mainController = context.getBean(MainController.class);
                //初始化页面和事件监听
                mainController.initView();
                mainController.initController();
            } else {
                JOptionPane.showMessageDialog(null, "用户名或密码错误", "提示", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }
}
