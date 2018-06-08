package com.manager.main;

import com.manager.controller.LoginController;
import com.manager.controller.Maincontroller;
import com.manager.views.MainView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //获取上下文对象
        ApplicationContext context =new ClassPathXmlApplicationContext("applicationContext.xml");

        //LoginController loginController = context.getBean(LoginController.class);
        //loginController.initController();
        Maincontroller maincontroller = context.getBean(Maincontroller.class);
        maincontroller.initview();
    }
}
