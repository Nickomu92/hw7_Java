package com.nikomu.Helpers;

import com.nikomu.Entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Connection {

    public static Session getSessionFactory() {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(PhoneNumber.class)
                .buildSessionFactory();

        return factory.getCurrentSession();
    }
}
