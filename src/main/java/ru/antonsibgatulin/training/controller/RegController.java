package ru.antonsibgatulin.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.antonsibgatulin.configure.Config;
import ru.antonsibgatulin.training.TrainingApplication;
import ru.antonsibgatulin.user.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Pattern;

@Controller

public class RegController {

    @Autowired
    public JavaMailSender javaMailSender;

    void sendEmailWithAttachment(String mail,Integer code,String id) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(mail);

        helper.setSubject("Подтверждение регистрации в проекте \"Информатик\"");

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText(PageController.getPage(Config.mails).replaceAll("jjjcode",""+code).replaceAll("jjjid",id), true);

        //helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

        javaMailSender.send(msg);

    }
    public void sendMessage(Integer code){

    }

    @RestController
    class RestControllerReg{

        @PostMapping({"/checklogin"})
        public String checkLogin(@RequestParam(name = "login",defaultValue = "") String login){

            if(callsignNormal(login)==false){
                return "invalid";
            }

            User user = TrainingApplication.databaseConnection.getUserByLogin(login);
            if(user==null){
                return "ok";
            }
            else{
                return "exist";
            }

        }



    }
    @GetMapping({"/regin"})
    public String reg(@RequestParam(name = "login",defaultValue = "") String login,
                      @RequestParam(name = "mail",defaultValue = "") String mail,
                      @RequestParam(name = "name",defaultValue = "") String name,
                      @RequestParam(name = "surname",defaultValue = "") String surname,
                      @RequestParam(name = "password",defaultValue = "") String password,
                      HttpServletResponse response) throws MessagingException, IOException {

        if(callsignNormal(login)==false){

            return "redirect:/reg";
        }
        if(callsignNormal(password)==false ){

            return "redirect:/reg";
        }
        if(checkMail(mail)==false){

            return "redirect:/reg";
        }

        User user1 = TrainingApplication.databaseConnection.getUserByLogin(login);
        if(user1!=null){
            return "redirect:/";
        }
        else{


            if(surname.length()<3 || name.length()<3){
                return "redirect:/";
            }



            User user = new User();
            user.setId(0);
            user.setLogin(login);
            user.setMail(mail);
            user.setName(name);
            user.setSurname(surname);
            user.setTypeUser(0);
            user.setPassword(password);
            user.setCode(new Random().nextInt(1000000));
            user.setBan(0);
            user.setTimelastonline(System.currentTimeMillis());
            user.setTimeofregistration(user.getTimelastonline());
            TrainingApplication.databaseConnection.saveUser(user);
            sendEmailWithAttachment(mail,user.getCode(),user.getLogin());

            User userids = AuthController.checkAuthUser(login,password);

            if(userids!=null){
                Cookie cookieLogin = new Cookie("login",login);
                Cookie passwordCookie = new Cookie("password",password);
                cookieLogin.setMaxAge(60*60*24*365*10);
                passwordCookie.setMaxAge(60*60*24*365*10);
                response.addCookie(cookieLogin);
                response.addCookie(passwordCookie);
                return "redirect:/?email=notif_accept_code";
            }

        }

        return "redirect:/?email=notif_accept_code";
    }

    @GetMapping({"regAccept"})
    public String regAccept(@RequestParam(name = "code")Integer code,@RequestParam(name = "id") String login){

            User user = TrainingApplication.databaseConnection.getUserByLogin(login);
            if(user!=null){
                System.out.println(user.getCode()+" "+code);
                if(user.getCode() - code == 0){
                    user.setCode(0);
                    TrainingApplication.databaseConnection.updateUser(user);
                    return "accept";
                }
            }


        return "redirect:/";
    }

    @GetMapping({"/reg"})
    public String getReg(Model model, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password) {

        if(AuthController.checkAuth(login,password)==true){
            return "redirect:/";
        }
        PageController.addHeadersDefault(model,null);


        return "reg/index";
    }


    private boolean callsignNormal(String nick) {
        Pattern pattern = Pattern.compile("[a-zA-Z]\\w{3,20}");
        return pattern.matcher(nick).matches();
    }

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public boolean checkMail(   String emailAddress) {

       String  regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
       return patternMatches(emailAddress,regexPattern);
    }


}
