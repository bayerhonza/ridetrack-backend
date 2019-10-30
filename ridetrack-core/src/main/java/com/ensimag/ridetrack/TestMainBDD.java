package com.ensimag.ridetrack;

import com.ensimag.ridetrack.confbdd.JpaEntityManagerFactory;
import com.ensimag.ridetrack.daos.JpaUserDao;
import com.ensimag.ridetrack.daos.TrackDao;
import com.ensimag.ridetrack.groupe.TrackUser;
import com.ensimag.ridetrack.model.TrackUserModel;

import java.util.List;
import java.util.Optional;

public class TestMainBDD {
    private static JpaUserDao jpaUserDao;

    public static void main(String[] args) {
        TrackUserModel usr1 = getUser(2446);
        System.out.println(usr1);
        updateUser(usr1, new String[]{"nom","prenom","pseudo","email"});
        deleteUser(getUser(1));
        getAllUser().forEach(user -> System.out.println(user.getEmail()));
    }
    private static class JpaUserDaoHolder{
        private static final JpaUserDao jpauserdao=new JpaUserDao(new JpaEntityManagerFactory().getEntityManager());
    }

    public static TrackDao getJpaUserDao(){
        return JpaUserDaoHolder.jpauserdao;
    }

    public static TrackUserModel getUser(long id){
        Optional<TrackUserModel> u = getJpaUserDao().get(id);
        return null;
        //return u.orElseGet(()-> {new TrackUserModel("nom n existe pas","prenom n existe pas","pseudo n existe pas", "email n existe pas");});
    }

    public static List<TrackUserModel> getAllUser(){
        return getJpaUserDao().getAll();
    }

    private static void updateUser(TrackUserModel u,String[] p ) {
        getJpaUserDao().update(u, p);
    }

    public static void saveUser(TrackUserModel u){
        getJpaUserDao().save(u);
    }

    public static void deleteUser(TrackUserModel u){
        getJpaUserDao().delete(u);
    }

}
