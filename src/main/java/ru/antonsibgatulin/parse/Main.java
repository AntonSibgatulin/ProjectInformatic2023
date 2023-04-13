package ru.antonsibgatulin.parse;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.antonsibgatulin.database.DatabaseConnection;
import ru.antonsibgatulin.hibernateUtils.HibernateUtils;
import ru.antonsibgatulin.informatic.InformationModel;
import ru.antonsibgatulin.option.OptionModel;
import ru.antonsibgatulin.taskofoption.TaskOfOptionModel;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static String getTextFromUrl(String urlstring) throws IOException {
        URL url = new URL(urlstring);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String all = "";
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            all += inputLine;
        in.close();
        return all;

    }

    public static void main1(String... args) throws IOException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        HibernateUtils.getSessionFactory().openSession();

        Scanner scanner = new Scanner(System.in);
        Long integer = 11983555L;//Long.valueOf(scanner.nextLine());
        JSONObject jsonObject = new JSONObject(getTextFromUrl("https://inf-ege.sdamgia.ru/api?type=get_test&id=" + integer + "&protocolVersion=1"));
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int i = 2; i < jsonArray.length(); i++) {
            Integer id = jsonArray.getInt(i);
            JSONObject jsonObject1 = new JSONObject(getTextFromUrl("https://inf-ege.sdamgia.ru/api?type=get_task&data=" + id + "&protocolVersion=1")).getJSONObject("data");
            String body = jsonObject1.getString("body");
            String solution = jsonObject1.getString("solution");
            String answer = jsonObject1.getString("answer");
            Integer task = jsonObject1.getInt("task");
            if (task == 15) {
                task = 5;

            }
            String str_split = "<b>Приведём другое решение на языке Python.</b>";
            if (solution.contains(str_split) == false)
                continue;

            String[] split = solution.split(str_split);
            String end_solution = solution.replace(split[0] + str_split, "").replaceAll("<p>", "\n")
                    .replaceAll("<div class=\"source_code lang_python\"> ", "<code>").replaceAll("</div></div>", "</code>");
            System.out.println(
                    end_solution
            );

            InformationModel informationModel = new InformationModel();
            informationModel.setTexts(body);
            informationModel.setSolution("Решим задание с помощью языка программирования Python:\n" + end_solution);
            informationModel.setComments(0L);
            informationModel.setOutput_data_json(answer);
            informationModel.setDislike(0L);
            informationModel.setLikes(0L);
            informationModel.setViews(0L);
            informationModel.setTime_of_public(System.currentTimeMillis());
            informationModel.setLang("PYTHON");
            informationModel.setAuthor("Антон Сибгатулин");
            informationModel.setId(id);
            informationModel.setInput_data_json("");
            informationModel.setType(task);

            databaseConnection.saveTask(informationModel);

            System.out.println("Saved " + informationModel.getId());


        }

    }

    public static void main2(String... args) {
          DatabaseConnection databaseConnection = new DatabaseConnection();
       /* List<InformationModel> list = databaseConnection.generateRandom();

        OptionModel optionModel = databaseConnection.createOptionRandom();
        List<TaskOfOptionModel> taskOfOptionModelList = databaseConnection.generateTypeOfTaskArray(list, optionModel);
        databaseConnection.saveListTaskOfOption(taskOfOptionModelList);


         */

             /*  for(InformationModel informationModel:list){
            System.out.println(informationModel.getId());
        }

       */
        List<InformationModel> list = databaseConnection.getOptionList(4);
        for(InformationModel informationModel:list){
            System.out.println(informationModel.getId());
        }





    }


    public static void main(String... args) throws IOException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        HibernateUtils.getSessionFactory().openSession();

        Scanner scanner = new Scanner(System.in);
        Long integer = 12035214L;//Long.valueOf(scanner.nextLine());
        JSONObject jsonObject = new JSONObject(getTextFromUrl("https://inf-ege.sdamgia.ru/api?type=get_test&id=" + integer + "&protocolVersion=1"));
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); i++) {
            Integer id = jsonArray.getInt(i);
            JSONObject jsonObject1 = new JSONObject(getTextFromUrl("https://inf-ege.sdamgia.ru/api?type=get_task&data=" + id + "&protocolVersion=1")).getJSONObject("data");
            String body = jsonObject1.getString("body");
            String solution = jsonObject1.getString("solution");
            String answer = jsonObject1.getString("answer");
            Integer task = jsonObject1.getInt("task");
            if (task == 15) {
                task = 5;

            }
            if (task == 6) {
                task = 6;

            }
            if (task == 8) {
                task = 7;

            }
            if(task == 19){
                task = 15;
            }
            if(task == 20){
                task = 16;
            }
            if(task == 21){
                task = 17;
            }


            if(task == 23){
                task = 18;
            }
            if(task == 16){
                task = 19;
            }
            String str_split = "<div class=\"source_code lang_python\">";
            if (solution.contains(str_split) == false)
                continue;
            /*
            String[] split = solution.split(str_split);
            String end_solution = solution.replace(split[0], "").replaceAll("<p>", "\n").replaceAll("<p class=\"left_margin\">","\n")
                    .replaceAll("<div class=\"source_code lang_python\">", "<code>").replaceAll("</div></div>", "</code>");
            System.out.println(
                    end_solution
            );
            String str = end_solution.replace(end_solution.replace(end_solution.split("</code>")[0],""),"")+"</code>";


             */


            int p1 = solution.indexOf(str_split) + str_split.length();
            int p2 = solution.indexOf("</div>", p1);

            String say = "<code>"+solution.substring(p1, p2)+"</code>";solution.substring(p2+6,solution.length()-1);


            say = (say.replaceAll("<p>","\n"));



            InformationModel informationModel = new InformationModel();
            informationModel.setTexts(body);
            informationModel.setSolution("Решим задание с помощью языка программирования Python:\n" + say);
            informationModel.setComments(0L);
            informationModel.setOutput_data_json(answer);
            informationModel.setDislike(0L);
            informationModel.setLikes(0L);
            informationModel.setViews(0L);
            informationModel.setTime_of_public(System.currentTimeMillis());
            informationModel.setLang("PYTHON");
            informationModel.setAuthor("Антон Сибгатулин");
            informationModel.setId(id);
            informationModel.setInput_data_json("");
            informationModel.setType(task);

            databaseConnection.saveTask(informationModel);

            System.out.println("Saved " + informationModel.getId());


        }

    }

}
