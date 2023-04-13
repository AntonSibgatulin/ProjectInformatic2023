package ru.antonsibgatulin.training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.mail.javamail.JavaMailSender;
import ru.antonsibgatulin.database.DatabaseConnection;
import ru.antonsibgatulin.hibernateUtils.HibernateUtils;
import ru.antonsibgatulin.informatic.configLoader.InformaticLoaderConfig;

@SpringBootApplication
public class TrainingApplication {

	@Autowired
	public JavaMailSender javaMailSender;

	public static InformaticLoaderConfig informaticLoaderConfig = null;
	public static DatabaseConnection databaseConnection = null;
	public static void main(String[] args) {
    	
    	
    	informaticLoaderConfig = new InformaticLoaderConfig();
    	
    	HibernateUtils.getSessionFactory().openSession();
    	
    	databaseConnection = new DatabaseConnection();
    	
    	
        SpringApplication.run(TrainingApplication.class, args);
    }

}
