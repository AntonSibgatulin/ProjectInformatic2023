package ru.antonsibgatulin.hibernateUtils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class HibernateUtils {
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public static void shutdown(){
        sessionFactory.close();
    }

    public static SessionFactory sessionFactory = buildSessionFactory();

    public static SessionFactory buildSessionFactory(){
        return new Configuration().configure(new File("hibernate.cfg.xml")).buildSessionFactory();
    }

}
