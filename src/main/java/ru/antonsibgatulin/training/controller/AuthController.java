package ru.antonsibgatulin.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.antonsibgatulin.database.DatabaseConnection;
import ru.antonsibgatulin.training.TrainingApplication;
import ru.antonsibgatulin.user.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

    public static boolean checkAuth(String login,String password){
        if (login.equals("NULL") || password.equals("NULL") ) {
            return false;
        }
        DatabaseConnection databaseConnection = TrainingApplication.databaseConnection;
        User user = null;

        if(login.contains("@")) {
            user = databaseConnection.getUserByMail(login,password);
        }else{
            user = databaseConnection.getUserByLogin(login,password);
        }
        if(user == null){
            return false;
        }
        else{
            return true;
        }

    }


    public static User checkAuthUser(String login,String password){
        DatabaseConnection databaseConnection = TrainingApplication.databaseConnection;
        User user = null;
        if (login.equals("NULL") || password.equals("NULL") ) {
            return null;
        }
            if(login.contains("@")) {
           user = databaseConnection.getUserByMail(login,password);
        }else{
            user = databaseConnection.getUserByLogin(login,password);
        }
        return user;
    }

    @GetMapping({"/auth", "/auth.php", "/auth.html"})
    public String auth(Model model, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password) {

            if(checkAuth(login,password)==true){
                return "redirect:/";
            }
        PageController.addHeadersDefault(model,null);
        return "auth/index";
    }


    @PostMapping("/auth")
    public String auth(HttpServletResponse response, @RequestParam(name = "login") String login , @RequestParam(name = "password") String password){

       User user = checkAuthUser(login,password);

       if(user.getBan()==0) {
           if (user != null) {
               Cookie cookieLogin = new Cookie("login", login);
               Cookie passwordCookie = new Cookie("password", password);
               cookieLogin.setMaxAge(60 * 60 * 24 * 365 * 10);
               passwordCookie.setMaxAge(60 * 60 * 24 * 365 * 10);
               response.addCookie(cookieLogin);
               response.addCookie(passwordCookie);
               TrainingApplication.databaseConnection.setOnline(user);

               return "redirect:/";
           }
       }
        return "auth/index";
    }


}
