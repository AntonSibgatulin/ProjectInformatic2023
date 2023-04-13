package ru.antonsibgatulin.training.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import ru.antonsibgatulin.configure.Config;
import ru.antonsibgatulin.database.DatabaseConnection;
import ru.antonsibgatulin.directory.DirectoryModel;
import ru.antonsibgatulin.informatic.InformationModel;
import ru.antonsibgatulin.informatic.InformaticOperationModel;
import ru.antonsibgatulin.option.OptionModel;
import ru.antonsibgatulin.taskofoption.TaskOfOptionModel;
import ru.antonsibgatulin.teory.TheoryModel;
import ru.antonsibgatulin.training.TrainingApplication;
import ru.antonsibgatulin.typeoftask.TypeoftaskModel;
import ru.antonsibgatulin.user.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static ru.antonsibgatulin.training.TrainingApplication.databaseConnection;

@Controller
public class PageController {

    public static String getPage(String name) {
        //File file = new File(getClass().getResource(name).getFile());
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:" + name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String str = null;
            String f = "";
            while ((str = bufferedReader.readLine()) != null) {
                f += str;
            }

            return f;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addHeader(Model model) {

        String header = getPage(Config.header);

        model.addAttribute("header", header);


    }

    public static void addAllTypesOfTask(Model model) {
        List<TypeoftaskModel> typeoftaskModelList = databaseConnection.getAllTypesOfTask();
        model.addAttribute("types", typeoftaskModelList);

    }

    public static void addStandartHeader(Model model) {

        String header = getPage(Config.standart_headr);

        model.addAttribute("standart_header", header);


    }

    public static void addLeftPanel(Model model, User user) {
        if (user == null || user.getTypeUser() == 0) {
            if (user != null) {
                String panel = getPage(Config.leftPanel);

                model.addAttribute("left_panel", panel);
            } else {
                String panel = getPage(Config.leftPanelAuth);

                model.addAttribute("left_panel", panel);
            }
        } else {
            String left_panel = getPage(Config.leftPanelAdmin);
            model.addAttribute("left_panel", left_panel);

        }
    }

    public static void addHeaders(Model model) {
        addHeader(model);
        addStandartHeader(model);
    }

    public static void addHeadersDefault(Model model, User user) {
        addHeader(model);
        addStandartHeader(model);
        addLeftPanel(model, user);
        model.addAttribute("user", user);
        databaseConnection.setOnline(user);
    }


    @GetMapping({"/index.html", "/", "/index.php"})
    public String index(Model model, @RequestParam(name = "email", defaultValue = "") String option, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password) {
        User user = AuthController.checkAuthUser(login, password);
        if (user != null) {
            if (user.getCode() != 0 && !option.equals("notif_accept_code")) {
                return "redirect:/?email=notif_accept_code";
            } else {

            }
        }

        addHeadersDefault(model, user);
        addAllTypesOfTask(model);
        addAlert(option, model, user);
        return "index";
    }

    public static void addAlert(String option, Model model, User user) {
        if (option.equals("notif_accept_code") && user != null && user.getCode() != 0) {
            model.addAttribute("script", "<script>alert(\"На почту отправлено письмо для окончания регистрации!\")</script>");
        }
    }


    //@ResponseBody
    @RequestMapping({"/task"})
    public String getTask(@CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password, @RequestParam(name = "id", defaultValue = "") String q, Model model) {
        User user = AuthController.checkAuthUser(login, password);

        if (q == null || q.isEmpty() || q.equals("")) {
            //return "redirect:/";
        } else {
            // System.out.println(q);
        }
        Integer id = null;
        try {
            id = Integer.valueOf(q);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (id == null) {
            return "redirect:/";
        }


        InformationModel InformationModel = databaseConnection.getInformationModel(Integer.valueOf(q));
        if (InformationModel == null) {
            return "redirect:/";
        }
        InformaticOperationModel informaticOperationModel = TrainingApplication.informaticLoaderConfig.hashMap.get(InformationModel.getLang());
        if (informaticOperationModel != null) {
            model.addAttribute("buttons", informaticOperationModel.buttons);
        }
        if (informaticOperationModel != null) {
            model.addAttribute("functions", informaticOperationModel.functions);
        }
        addHeadersDefault(model, user);

        model.addAttribute("id", InformationModel.getId());
        model.addAttribute("type", InformationModel.getType());
        model.addAttribute("object", InformationModel.toString());

        model.addAttribute("answer", InformationModel.output_data_json);


        model.addAttribute("object", InformationModel);

        model.addAttribute("json", InformationModel.getJSON().toString());
        // System.out.println(InformationModel.getId()+" "+InformationModel.getText().replaceAll("\n","<div class=\"new-wrap\"></div>"));

        return "task";
    }


    @GetMapping({"/editor", "/editor/index", "/editor/index.html", "/editor/index.php"})
    public String editor(@CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password, @RequestParam(name = "id", defaultValue = "") String q, Model model) {
        User user = AuthController.checkAuthUser(login, password);
        addHeadersDefault(model, user);
        InformaticOperationModel informaticOperationModel = TrainingApplication.informaticLoaderConfig.hashMap.get("PYTHON");
        if (informaticOperationModel != null) {
            model.addAttribute("buttons", informaticOperationModel.buttons);
        }
        if (informaticOperationModel != null) {
            model.addAttribute("functions", informaticOperationModel.functions);
        }
        return "editor";
    }


    @GetMapping({"/exit", "/exit/index", "/exit/", "/exit/index.php"})
    public String exit(HttpServletResponse response, Model model, @RequestHeader(value = "referer", required = false) final String referer) {

        Cookie cookie = new Cookie("login", "");
        cookie.setMaxAge(0); // Don't set to -1 or it will become a session cookie!
        response.addCookie(cookie);


        Cookie cookie2 = new Cookie("password", "");
        cookie2.setMaxAge(0); // Don't set to -1 or it will become a session cookie!
        response.addCookie(cookie2);

        try {
            URL url = new URL(referer);
            System.out.println(url.getProtocol() + " " + url.getAuthority() + " " + url.getPath() + " " + url.getQuery() + " " + url.getRef());
            return "redirect:" + (referer.replace(url.getProtocol() + "://" + url.getAuthority(), ""));

        } catch (MalformedURLException e) {

            return "redirect:/";
        }

    }


    @GetMapping({"/option"})
    public String option(@RequestParam(name = "svet", defaultValue = "false") Boolean svet, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password, @RequestParam(name = "id", defaultValue = "") String q, Model model) {
        User user = AuthController.checkAuthUser(login, password);


        if (q == null || q.isEmpty() || q.equals("")) {
            //return "redirect:/";
        } else {
            // System.out.println(q);
        }
        Integer id = null;
        try {
            id = Integer.valueOf(q);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (id == null) {
            return "redirect:/";
        }


        addHeadersDefault(model, user);
        List<InformationModel> list = databaseConnection.getOptionList(id);
        OptionModel optionModel = databaseConnection.getOptionModelById(id);
        model.addAttribute("list", list);
        model.addAttribute("id", id);
        model.addAttribute("option", optionModel);
        model.addAttribute("svet", svet);


        return "option";
    }

    @GetMapping({"/generate"})
    public String getGenerate(@CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password, @RequestParam(name = "id", defaultValue = "") String q, Model model) {
        User user = AuthController.checkAuthUser(login, password);

        /*List<InformationModel> list = databaseConnection.generateRandom();

        OptionModel optionModel = databaseConnection.createOptionRandom();
        List<TaskOfOptionModel> taskOfOptionModelList = databaseConnection.generateTypeOfTaskArray(list, optionModel);
        databaseConnection.saveListTaskOfOption(taskOfOptionModelList);
           */

        // return "redirect:/option?id="+optionModel.getId();
        addStandartHeader(model);
        addAllTypesOfTask(model);
        addHeadersDefault(model, user);
        return "generate";
    }


    @PostMapping({"/generate"})
    public String postGenerate(@RequestParam(value = "tasks[]",defaultValue = "") Integer[] tasks){

        if (tasks.length==0){
            List<InformationModel> list = databaseConnection.generateRandom();

            OptionModel optionModel = databaseConnection.createOptionRandom();
            List<TaskOfOptionModel> taskOfOptionModelList = databaseConnection.generateTypeOfTaskArray(list, optionModel);
            databaseConnection.saveListTaskOfOption(taskOfOptionModelList);


            return "redirect:/option?id="+optionModel.getId();
        }

        List<InformationModel> list = databaseConnection.generateRandom(tasks);

        OptionModel optionModel = databaseConnection.createOptionRandom();

        List<TaskOfOptionModel> taskOfOptionModelList = databaseConnection.generateTypeOfTaskArray(list, optionModel);
        databaseConnection.saveListTaskOfOption(taskOfOptionModelList);


        return "redirect:/option?id="+optionModel.getId();
    }


    @GetMapping({"/theorys"})
    public String theorys(@CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password, @RequestParam(name = "id", defaultValue = "") String q, Model model) {
        User user = AuthController.checkAuthUser(login, password);
        addHeadersDefault(model, user);
        DatabaseConnection databaseConnection = TrainingApplication.databaseConnection;
        List<TheoryModel> list = databaseConnection.getTheoryModelList();
        model.addAttribute("list", list);

        return "theorys";


    }

    @GetMapping({"/theory"})
    public String getTheory(@CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password, @RequestParam(name = "id", defaultValue = "") String q, Model model) {
        User user = AuthController.checkAuthUser(login, password);


        if (q == null || q.isEmpty() || q.equals("")) {
            //return "redirect:/";
        } else {
            // System.out.println(q);
        }
        Integer id = null;
        try {
            id = Integer.valueOf(q);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (id == null) {
            return "redirect:/";
        }


        addHeadersDefault(model, user);
        TheoryModel theoryModel = databaseConnection.getTheoryById(id);
        model.addAttribute("object", theoryModel);


        return "theory";
    }


    /////////////////////////////////////////////////////////////////////


    @GetMapping({"/directorys"})
    public String directorys(@CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password, @RequestParam(name = "id", defaultValue = "") String q, Model model) {
        User user = AuthController.checkAuthUser(login, password);
        addHeadersDefault(model, user);
        DatabaseConnection databaseConnection = TrainingApplication.databaseConnection;
        List<DirectoryModel> list = databaseConnection.getDirectoryModelList();
        model.addAttribute("list", list);

        return "directorys";


    }

    @GetMapping({"/directory"})
    public String getDirectory(@CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password, @RequestParam(name = "id", defaultValue = "") String q, Model model) {
        User user = AuthController.checkAuthUser(login, password);


        if (q == null || q.isEmpty() || q.equals("")) {
            //return "redirect:/";
        } else {
            // System.out.println(q);
        }
        Integer id = null;
        try {
            id = Integer.valueOf(q);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (id == null) {
            return "redirect:/";
        }


        addHeadersDefault(model, user);
        DirectoryModel theoryModel = databaseConnection.getDirectoryById(id);
        model.addAttribute("object", theoryModel);


        return "theory";
    }


    @GetMapping({"/search", "/search/"})
    public String search(Model model, @RequestParam(name = "id", defaultValue = "1") String str, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password) {
        User user = AuthController.checkAuthUser(login, password);

        Integer id = null;

        try {
            id = Integer.valueOf(str);
        } catch (Exception e) {

        }


        DatabaseConnection databaseConnection = TrainingApplication.databaseConnection;


        if (id == null) {
            List<InformationModel> list = databaseConnection.search(str);

            model.addAttribute("list", list);
        } else {
            if (id > 0) {
                InformationModel InformationModel = databaseConnection.getInformationModel(id);
                List<InformationModel> list = new ArrayList<InformationModel>();

                if (InformationModel != null) {
                    list.add(InformationModel);
                }
                model.addAttribute("list", list);
            } else {
                List<InformationModel> list = databaseConnection.getOptionList(Math.abs(id));
                model.addAttribute("list", list);

            }
        }
        return "/search";
    }


}
