package ru.antonsibgatulin.database;


import org.hibernate.Query;
import org.hibernate.Session;

import ru.antonsibgatulin.directory.DirectoryModel;
import ru.antonsibgatulin.hibernateUtils.HibernateUtils;
import ru.antonsibgatulin.informatic.InformationModel;
import ru.antonsibgatulin.option.OptionModel;
import ru.antonsibgatulin.taskofoption.TaskOfOptionModel;
import ru.antonsibgatulin.teory.TheoryModel;
import ru.antonsibgatulin.typeoftask.TypeoftaskModel;
import ru.antonsibgatulin.user.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {


    public DatabaseConnection() {

    }



    public InformationModel getInformationModel(Integer id) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM InformationModel U WHERE U.id = :id");
        query.setInteger("id", id);
        InformationModel InformationModel = (InformationModel) query.uniqueResult();
        session.getTransaction().commit();
        return InformationModel;


    }


    public List<InformationModel> getInformationModelList(String id) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM InformationModel U WHERE U.texts like :id");

        query.setString("id", "%" + id + "%");
        List<InformationModel> list = (List<InformationModel>) query.list();
        //InformationModel InformationModel =(InformationModel) query.uniqueResult();
        session.getTransaction().commit();
        return list;


    }



    public List<InformationModel> search(String id) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM InformationModel U WHERE U.texts like :id ORDER BY rand()");

        query.setString("id", "%" + id + "%");
        query.setMaxResults(50);
        List<InformationModel> list = (List<InformationModel>) query.list();
        //InformationModel InformationModel =(InformationModel) query.uniqueResult();
        session.getTransaction().commit();
        return list;


    }

    public User getUserByMail(String mail, String password) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM User u WHERE u.mail = :mail AND u.password = :password");
        query.setString("mail", mail);
        query.setString("password", password);
        User user = (User) query.uniqueResult();
        session.getTransaction().commit();
        return user;
    }

    public User getUserByLogin(String login, String password) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM User u WHERE u.login = :login AND u.password = :password");
        query.setString("login", login);
        query.setString("password", password);
        User user = (User) query.uniqueResult();
        session.getTransaction().commit();
        return user;
    }

    public int deleteInformationModelById(Integer id) {


        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE from InformationModel WHERE id = :id");
        query.setInteger("id", id);
        int i = query.executeUpdate();
        session.getTransaction().commit();


        return i;
    }


    public List<TypeoftaskModel> getAllTypesOfTask() {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM TypeoftaskModel t");


        List<TypeoftaskModel> list = (List<TypeoftaskModel>) query.list();
        //InformationModel InformationModel =(InformationModel) query.uniqueResult();
        session.getTransaction().commit();
        return list;

    }

    public void saveTask(InformationModel informationModel) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        //Integer id = (Integer) session.save("id",InformationModel);
        session.save(informationModel);
        session.flush();
        session.getTransaction().commit();


        //System.out.println(informationModel.getId()+" ");


        //	return id;


    }


    public void updateTask(InformationModel informationModel) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        //Integer id = (Integer) session.save("id",InformationModel);
        session.update(informationModel);
        session.flush();
        session.getTransaction().commit();


    }


    public void saveTypeOfTask(TypeoftaskModel typeoftaskModel) {

        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        //Integer id = (Integer) session.save("id",InformationModel);
        session.save(typeoftaskModel);
        session.flush();
        session.getTransaction().commit();


    }

    public void updateTypeOfTask(TypeoftaskModel typeoftaskModel) {

        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        //Integer id = (Integer) session.save("id",InformationModel);
        session.saveOrUpdate(typeoftaskModel);
        session.flush();
        session.getTransaction().commit();


    }


    public OptionModel getOptionModelById(Integer id) {

        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM OptionModel o WHERE o.id = :id");
        query.setInteger("id", id);
        OptionModel optionModel = (OptionModel) query.uniqueResult();
        session.flush();
        session.getTransaction().commit();
        return optionModel;

    }


    public List<TaskOfOptionModel> getTaskOfOptionModelList(Integer id) {

        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM TaskOfOptionModel o WHERE o.id_option = :id");
        query.setInteger("id", id);
        List<TaskOfOptionModel> list = (List<TaskOfOptionModel>)query.list();
        session.flush();
        session.getTransaction().commit();
        return list;

    }


    public void createOptionModel(OptionModel optionModel) {


        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(optionModel);
        session.flush();
        session.getTransaction().commit();


    }


    public List<InformationModel> generateRandom() {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM InformationModel i ORDER BY rand()");
        query.setMaxResults(15);

        List<InformationModel> list = (List<InformationModel>) query.list();

        session.flush();
        session.getTransaction().commit();
        return list;


    }

    public List<InformationModel> generateRandom(Integer[] task) {
        String id =task.length>0? " WHERE ":"";

        for(int i = 0;i<task.length;i++){
            if(i==0){
                id+="i.type = "+task[i];

            }else{
                id+=" OR i.type = "+task[i];
            }
        }

        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM InformationModel i "+id+" ORDER BY rand()");
        query.setMaxResults(task.length*5<15?15:task.length*5);

        List<InformationModel> list = (List<InformationModel>) query.list();

        session.flush();
        session.getTransaction().commit();
        return list;


    }

    public void saveListTaskOfOption(List<TaskOfOptionModel> taskOfOptionModels) {

        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        for (TaskOfOptionModel taskOfOptionModel : taskOfOptionModels) {

            session.save(taskOfOptionModel);
        }

        session.flush();
        session.getTransaction().commit();
    }


    public List<TaskOfOptionModel> generateTypeOfTaskArray(List<InformationModel> list, OptionModel optionModel) {
        List<TaskOfOptionModel> list1 = new ArrayList<>();
        for (InformationModel informationModel : list) {
            TaskOfOptionModel taskOfOptionModel = new TaskOfOptionModel();
            taskOfOptionModel.setId(0L);
            taskOfOptionModel.setId_option(optionModel.getId());
            taskOfOptionModel.setId_task(informationModel.getId());
            list1.add(taskOfOptionModel);
        }
        return list1;

    }


    public OptionModel createOptionRandom() {
        OptionModel optionModel = new OptionModel();
        optionModel.setId(0);
        optionModel.setTime(System.currentTimeMillis());
        optionModel.setType(0);
        optionModel.setTask("def");
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(optionModel);
        session.flush();
        session.getTransaction().commit();
        return optionModel;

    }



    public List<InformationModel> getOptionList(Integer id){
        OptionModel optionModel = getOptionModelById(id);
        if(optionModel==null){
            return null;
        }
         List<TaskOfOptionModel> taskOfOptionModelList = getTaskOfOptionModelList(optionModel.id);
        List<InformationModel> getByIds = getInformationModelByIds(taskOfOptionModelList);
        return  getByIds;

    }

    public   List<InformationModel> getInformationModelByIds(List<TaskOfOptionModel> taskOfOptionModels){
        String str = "";
        for (TaskOfOptionModel taskOfOptionModel:taskOfOptionModels){
            if(str.length()==0){
                str += " o.id = " + taskOfOptionModel.getId_task();

            }else {
                str += " OR o.id = " + taskOfOptionModel.getId_task();
            }
        }

        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from InformationModel o WHERE "+str);
        List<InformationModel> list =( List<InformationModel> ) query.list();
        session.flush();
        session.getTransaction().commit();

        return list;

    }

    public void removeTypeOfTask(Integer id) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE from TypeoftaskModel WHERE id = :id");
        query.setInteger("id", id);
        int i = query.executeUpdate();
        session.flush();
        session.getTransaction().commit();


        //return i;
    }


    public List<TheoryModel> getTheoryModelList(){
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM TheoryModel t ");


        List<TheoryModel> list = (List<TheoryModel>) query.list();
        //InformationModel InformationModel =(InformationModel) query.uniqueResult();
        session.getTransaction().commit();
        return list;
    }

    public TheoryModel getTheoryById(Integer id) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM TheoryModel o WHERE o.id = :id");
        query.setInteger("id", id);
        TheoryModel theoryModel = (TheoryModel) query.uniqueResult();
        session.flush();
        session.getTransaction().commit();
        return theoryModel;
    }

    public void deleteTheory(Integer id) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE from TheoryModel WHERE id = :id");
        query.setInteger("id", id);
        int i = query.executeUpdate();
        session.flush();
        session.getTransaction().commit();
    }

    public void saveTheory(TheoryModel theoryModel) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(theoryModel);
        session.flush();
        session.getTransaction().commit();
    }

    public void updateTheory(TheoryModel theoryModel) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.update(theoryModel);
        session.flush();
        session.getTransaction().commit();
    }




    public List<DirectoryModel> getDirectoryModelList(){
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM DirectoryModel t ");


        List<DirectoryModel> list = (List<DirectoryModel>) query.list();
        //InformationModel InformationModel =(InformationModel) query.uniqueResult();
        session.getTransaction().commit();
        return list;
    }


    public DirectoryModel getDirectoryById(Integer id) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM DirectoryModel o WHERE o.id = :id");
        query.setInteger("id", id);
        DirectoryModel directoryModel = (DirectoryModel) query.uniqueResult();
        session.flush();
        session.getTransaction().commit();
        return directoryModel;
    }

    public void deleteDirectory(Integer id) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE from DirectoryModel WHERE id = :id");
        query.setInteger("id", id);
        int i = query.executeUpdate();
        session.flush();
        session.getTransaction().commit();
    }

    public void saveDirectory(DirectoryModel directoryModel) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(directoryModel);
        session.flush();
        session.getTransaction().commit();
    }

    public void updateDirectory(DirectoryModel d) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.update(d);
        session.flush();
        session.getTransaction().commit();
    }



    public User getUserByLogin(String login){
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM User u WHERE u.login = :login");
        query.setString("login", login);
        User user = (User) query.uniqueResult();
        session.getTransaction().commit();
        return user;
    }

    public void saveUser(User user) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(user);
        session.flush();
        session.getTransaction().commit();
    }

    public void updateUser(User user) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.update(user);
        session.flush();
        session.getTransaction().commit();
    }



    public void setOnline(User user){
        if(user==null)return;
        user.setTimelastonline(System.currentTimeMillis());
        updateUser(user);
    }
}

