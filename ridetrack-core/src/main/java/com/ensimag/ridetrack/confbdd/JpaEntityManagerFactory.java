package com.ensimag.ridetrack.confbdd;

import com.ensimag.ridetrack.model.TrackUserModel;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

public class JpaEntityManagerFactory {
    private final String BD_URL ="jdbc:mysql://localhost:80/ridetrackbdd";
    private final String BD_USER = "root";
    private final String BD_MDP = "";

    public EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    protected EntityManagerFactory getEntityManagerFactory() {
        PersistenceUnitInfo persistenceUnitInfo = getPersistenceUnitInfo(getClass().getSimpleName());
        Map<String, Object> configuration = new HashMap<>();
        return new EntityManagerFactoryBuilderImpl(new PersistenceUnitInfoDescriptor(persistenceUnitInfo), configuration)
                .build();
    }

    protected PersistenceUnitInfoImpl getPersistenceUnitInfo(String name){
        return new PersistenceUnitInfoImpl(name, getEntityCLassNames(),getProperties());
    }

    protected List<String> getEntityCLassNames(){
        return Arrays.asList(getEntities())
                .stream()
                .map(Class::getName)
                .collect(Collectors.toList());
    }

    protected Properties getProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.id.new_generator_mappings", false);
        properties.put("hibernate.connection.datasource", getMysqlDataSource());
        return properties;
    }

    protected Class[] getEntities(){
        return new Class[]{
                TrackUserModel.class
        };
    }
    protected DataSource getMysqlDataSource(){
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setURL(BD_URL);
            mysqlDataSource.setUser(BD_USER);
            mysqlDataSource.setPassword(BD_MDP);
            return mysqlDataSource;
    }
}
