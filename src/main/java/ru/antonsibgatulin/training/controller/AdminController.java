package ru.antonsibgatulin.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.antonsibgatulin.configure.Config;
import ru.antonsibgatulin.database.DatabaseConnection;
import ru.antonsibgatulin.directory.DirectoryModel;
import ru.antonsibgatulin.informatic.InformationModel;
import ru.antonsibgatulin.teory.TheoryModel;
import ru.antonsibgatulin.training.TrainingApplication;
import ru.antonsibgatulin.typeoftask.TypeoftaskModel;
import ru.antonsibgatulin.user.User;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {


    @GetMapping({"/admin", "/admin/index.html", "/admin/index.php"})
    public String getPage(Model model, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password) {

        User user = AuthController.checkAuthUser(login, password);
        if (user == null || user.getTypeUser() < 2) {
            return "redirect:/";
        }
        PageController.addHeaders(model);
        PageController.addLeftPanel(model, user);
        return "admin/index";
    }

    @RestController
    class RestControllerAdmin {

        @PostMapping({"/admin/removetype"})
        public String deleteedit(Model model, @RequestParam(name = "id", defaultValue = "0") Integer id, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password) {
            User user = AuthController.checkAuthUser(login, password);
            if (user == null || user.getTypeUser() == 0) {
                return "redirect:/";
            }

            if (user.getTypeUser() < 1) {
                return "Access denied.Your level is lower then need for it";
            }

            if (id == 0) {
                return "Не правильно введены данные";
            }
            TrainingApplication.databaseConnection.removeTypeOfTask(id);
            return "ok";
        }


        @PostMapping({"/admin/edittype"})
        public String edit(Model model, @RequestParam(name = "id", defaultValue = "0") Integer id, @RequestParam(name = "type", defaultValue = "0") Integer type, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password, @RequestParam(name = "text", defaultValue = "") String text) {
            User user = AuthController.checkAuthUser(login, password);
            if (user == null || user.getTypeUser() == 0) {
                return "redirect:/";
            }

            if (user.getTypeUser() < 1) {
                return "Access denied.Your level is lower then need for it";
            }

            if (text.length() == 0 || type == 0) {
                return "Неверно введены данные!";
            }

            TypeoftaskModel typeoftaskModel = new TypeoftaskModel();
            typeoftaskModel.setId(id);
            typeoftaskModel.setType(type);
            typeoftaskModel.setName(text);
            typeoftaskModel.setLink("");
            typeoftaskModel.setHide(0);
            if (typeoftaskModel.getId() == 0) {
                TrainingApplication.databaseConnection.saveTypeOfTask(typeoftaskModel);

            } else {
                TrainingApplication.databaseConnection.updateTypeOfTask(typeoftaskModel);

            }


            return "ok";
        }


        @GetMapping("/admin/delete")
        public String deleted(Model model, @RequestParam("type") String task, @RequestParam(name = "id") Integer id, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password) {
            DatabaseConnection databaseConnection = TrainingApplication.databaseConnection;
            User user = AuthController.checkAuthUser(login, password);
            if (user == null || user.getTypeUser() == 0) {
                return "redirec:/";
            }
            if (user.getTypeUser() < 2) {
                return "Access denied.Your level " + user.getTypeUser() + " but minimal level 2";

            }
            if (id > 0) {

                databaseConnection.deleteInformationModelById(id);
                return "ok";

            }
            return "";
        }


        @PostMapping({"/admin/createtask"})
        public String string(Model model, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password, @RequestParam(name = "text", defaultValue = "") String text, @RequestParam(name = "solution", defaultValue = "") String solution, @RequestParam(name = "answer", defaultValue = "") String answer, @RequestParam(name = "type", defaultValue = "1") Integer type, @RequestParam(name = "author", defaultValue = "") String author) {
            User user = AuthController.checkAuthUser(login, password);
            if (user == null || user.getTypeUser() == 0) {
                return "redirect:/";
            }

            if (user.getTypeUser() < 2) {
                return "Access denied.Your level is lower then need for it";
            }


            if (text.contains("<script>") || text.contains("&lt;script7gt;") || solution.contains("<script>") || solution.contains("&lt;script7gt;")) {
                return "tag <script> Access denied.";
            }


            InformationModel informationModel = new InformationModel();
            informationModel.setTexts(text);
            informationModel.setSolution(solution);
            informationModel.setComments(0L);
            informationModel.setOutput_data_json(answer);
            informationModel.setDislike(0L);
            informationModel.setLikes(0L);
            informationModel.setViews(0L);
            informationModel.setTime_of_public(System.currentTimeMillis());
            informationModel.setLang("PYTHON");
            informationModel.setAuthor(author);
            informationModel.setId(0);
            informationModel.setInput_data_json("");
            informationModel.setType(type);

            /* Integer id = */
            TrainingApplication.databaseConnection.saveTask(informationModel);
            //InformationModel.setId(id);
            //	System.out.println(informationModel.getId());
            return "ok;" + informationModel.getId();

        }


        @PostMapping({"/admin/deletetheory"})
        public String deletetheory(Model model, @RequestParam(name = "id") Integer id, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password) {
            User user = AuthController.checkAuthUser(login, password);
            if (user == null || user.getTypeUser() == 0) {
                return "redirect:/";
            }

            if (user.getTypeUser() < 1) {
                return "Access denied.Your level is lower then need for it";
            }

            DatabaseConnection databaseConnection = TrainingApplication.databaseConnection;
            databaseConnection.deleteTheory(id);

            return "ok";

        }


        @PostMapping({"/admin/deletedirectory"})
        public String deleteDirectory(Model model, @RequestParam(name = "id") Integer id, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password) {
            User user = AuthController.checkAuthUser(login, password);
            if (user == null || user.getTypeUser() == 0) {
                return "redirect:/";
            }

            if (user.getTypeUser() < 1) {
                return "Access denied.Your level is lower then need for it";
            }

            DatabaseConnection databaseConnection = TrainingApplication.databaseConnection;
            databaseConnection.deleteDirectory(id);

            return "ok";

        }


        @PostMapping({"/admin/updatetheory"})
        public String updatetheory(Model model, @RequestParam(name = "id",defaultValue = "0") Integer id, @RequestParam(name = "name") String name, @RequestParam(name = "description") String description, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password) {
            User user = AuthController.checkAuthUser(login, password);
            if (user == null || user.getTypeUser() == 0) {
                return "redirect:/";
            }

            if (user.getTypeUser() < 1) {
                return "Access denied.Your level is lower then need for it";
            }

            DatabaseConnection databaseConnection = TrainingApplication.databaseConnection;
            TheoryModel theoryModel = new TheoryModel();
            theoryModel.setId(id);
            theoryModel.setName(name);
            theoryModel.setDescription(description);
            theoryModel.setTime(System.currentTimeMillis());
            if(id==0) {
                databaseConnection.saveTheory(theoryModel);
            }else{
                databaseConnection.updateTheory(theoryModel);
            }

            return "ok";

        }







        @PostMapping({"/admin/updatedirectory"})
        public String updateDirectory(Model model, @RequestParam(name = "id",defaultValue = "0") Integer id, @RequestParam(name = "name") String name, @RequestParam(name = "description") String description, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password) {
            User user = AuthController.checkAuthUser(login, password);
            if (user == null || user.getTypeUser() == 0) {
                return "redirect:/";
            }

            if (user.getTypeUser() < 1) {
                return "Access denied.Your level is lower then need for it";
            }

            DatabaseConnection databaseConnection = TrainingApplication.databaseConnection;
            DirectoryModel theoryModel = new DirectoryModel();
            theoryModel.setId(id);
            theoryModel.setName(name);
            theoryModel.setDescription(description);
            theoryModel.setTime(System.currentTimeMillis());
            if(id==0) {
                databaseConnection.saveDirectory(theoryModel);
            }else{
                databaseConnection.updateDirectory(theoryModel);
            }

            return "ok";

        }



        @PostMapping({"/admin/edittask"})
        public String edit(Model model, @RequestParam(name = "id") Integer id, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password, @RequestParam(name = "text", defaultValue = "") String text, @RequestParam(name = "solution", defaultValue = "") String solution, @RequestParam(name = "answer", defaultValue = "") String answer, @RequestParam(name = "type", defaultValue = "1") Integer type, @RequestParam(name = "author", defaultValue = "") String author) {
            User user = AuthController.checkAuthUser(login, password);
            if (user == null || user.getTypeUser() == 0) {
                return "redirect:/";
            }

            if (user.getTypeUser() < 1) {
                return "Access denied.Your level is lower then need for it";
            }


            if (text.contains("<script>") || text.contains("&lt;script7gt;") || solution.contains("<script>") || solution.contains("&lt;script7gt;")) {
                return "tag <script> Access denied.";
            }


            InformationModel informationModel = new InformationModel();
            informationModel.setTexts(text);
            informationModel.setSolution(solution);
            informationModel.setComments(0L);
            informationModel.setOutput_data_json(answer);
            informationModel.setDislike(0L);
            informationModel.setLikes(0L);
            informationModel.setViews(0L);
            informationModel.setTime_of_public(System.currentTimeMillis());
            informationModel.setLang("PYTHON");
            informationModel.setAuthor(author);
            informationModel.setId(id);
            informationModel.setInput_data_json("");
            informationModel.setType(type);

            /* Integer id = */
            TrainingApplication.databaseConnection.updateTask(informationModel);
            //InformationModel.setId(id);
            //	System.out.println(informationModel.getId());
            return "ok;" + informationModel.getId();

        }

    }












    @GetMapping({"/admin/search", "/admin/search/"})
    public String search(Model model, @RequestParam(name = "id", defaultValue = "1") String str, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password) {
        User user = AuthController.checkAuthUser(login, password);
        if (user == null || user.getTypeUser() == 0) {
            return "redirect:/";
        }
        Integer id = null;

        try {
            id = Integer.valueOf(str);
        } catch (Exception e) {

        }


        DatabaseConnection databaseConnection = TrainingApplication.databaseConnection;


        if (id == null) {
            List<InformationModel> list = databaseConnection.getInformationModelList(str);

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

            }
        }
        return "admin/search";
    }

    @GetMapping({"/admin/create"})
    public String create(Model model, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password) {

        User user = AuthController.checkAuthUser(login, password);
        if (user == null || user.getTypeUser() == 0) {
            return "redirect:/";
        }
        if (user.getTypeUser() < 2) {
            return "redirect:/admin";
        }
        PageController.addHeadersDefault(model, user);
        PageController.addAllTypesOfTask(model);
        return "/admin/create";
    }


    @GetMapping({"/admin/edit"})
    public String edit(Model model, @RequestParam(name = "id") Integer id, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password) {

        User user = AuthController.checkAuthUser(login, password);
        if (user == null || user.getTypeUser() == 0) {
            return "redirect:/";
        }
        if (user.getTypeUser() < 1) {
            return "redirect:/admin";
        }
        PageController.addHeadersDefault(model, user);
        PageController.addAllTypesOfTask(model);
        InformationModel informationModel = TrainingApplication.databaseConnection.getInformationModel(id);
        model.addAttribute("object", informationModel);
        model.addAttribute("id", id);
        return "/admin/edit";
    }


    @GetMapping({"/admin/edittype"})
    public String edittype(Model model, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password) {

        User user = AuthController.checkAuthUser(login, password);
        if (user == null || user.getTypeUser() == 0) {
            return "redirect:/";
        }
        if (user.getTypeUser() < 2) {
            return "redirect:/admin";
        }
        PageController.addHeadersDefault(model, user);
        PageController.addAllTypesOfTask(model);


        return "/admin/edittype";
    }


    @GetMapping({"/admin/theory"})
    public String getTeory(Model model, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password) {

        User user = AuthController.checkAuthUser(login, password);
        if (user == null || user.getTypeUser() == 0) {
            return "redirect:/";
        }
        if (user.getTypeUser() < 1) {
            return "redirect:/admin";
        }
        PageController.addHeadersDefault(model, user);
        DatabaseConnection databaseConnection = TrainingApplication.databaseConnection;
        List<TheoryModel> list = databaseConnection.getTheoryModelList();
        model.addAttribute("list", list);


        return "/admin/theory";

    }


    @GetMapping({"/admin/directory"})
    public String getDirectory(Model model, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password) {

        User user = AuthController.checkAuthUser(login, password);
        if (user == null || user.getTypeUser() == 0) {
            return "redirect:/";
        }
        if (user.getTypeUser() < 1) {
            return "redirect:/admin";
        }
        PageController.addHeadersDefault(model, user);
        DatabaseConnection databaseConnection = TrainingApplication.databaseConnection;
        List<DirectoryModel> list = databaseConnection.getDirectoryModelList();
        model.addAttribute("list", list);


        return "/admin/directory";

    }

    @GetMapping({"/admin/createdirectory"})
    public String getCreateDirectory(Model model, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password) {

        User user = AuthController.checkAuthUser(login, password);
        if (user == null || user.getTypeUser() == 0) {
            return "redirect:/";
        }
        if (user.getTypeUser() < 1) {
            return "redirect:/admin";
        }

        PageController.addHeadersDefault(model, user);


        return "/admin/createdirectory";

    }


    @GetMapping({"/admin/editdirectory"})
    public String getDirectory(Model model, @RequestParam(name = "id") Integer id, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password) {

        User user = AuthController.checkAuthUser(login, password);
        if (user == null || user.getTypeUser() == 0) {
            return "redirect:/";
        }
        if (user.getTypeUser() < 1) {
            return "redirect:/admin";
        }

        PageController.addHeadersDefault(model, user);
        DatabaseConnection databaseConnection = TrainingApplication.databaseConnection;
        DirectoryModel theoryModel = databaseConnection.getDirectoryById(id);

        model.addAttribute("object", theoryModel);

        return "/admin/editdirectory";
    }



    @GetMapping({"/admin/edittheory"})
    public String getTheory(Model model, @RequestParam(name = "id") Integer id, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password) {

        User user = AuthController.checkAuthUser(login, password);
        if (user == null || user.getTypeUser() == 0) {
            return "redirect:/";
        }
        if (user.getTypeUser() < 1) {
            return "redirect:/admin";
        }

        PageController.addHeadersDefault(model, user);
        DatabaseConnection databaseConnection = TrainingApplication.databaseConnection;
        TheoryModel theoryModel = databaseConnection.getTheoryById(id);

        model.addAttribute("object", theoryModel);

        return "/admin/edittheory";
    }

    @GetMapping({"/admin/createtheory"})
    public String getCreateTheory(Model model, @CookieValue(name = "login", defaultValue = "NULL") String login, @CookieValue(name = "password", defaultValue = "NULL") String password) {

        User user = AuthController.checkAuthUser(login, password);
        if (user == null || user.getTypeUser() == 0) {
            return "redirect:/";
        }
        if (user.getTypeUser() < 1) {
            return "redirect:/admin";
        }

        PageController.addHeadersDefault(model, user);


        return "/admin/createtheory";

    }

}
