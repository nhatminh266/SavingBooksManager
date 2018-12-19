/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.hibernate.model;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


/**
 *
 * @author nhatminh266
 */
public class HibernateUtil {
    
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;
    private static final ThreadLocal<Session> threadLocal;
    
    static {
    try {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(SavingsType.class);
        configuration.addAnnotatedClass(Position.class);
        configuration.addAnnotatedClass(Branch.class);
        configuration.addAnnotatedClass(Employee.class);
        configuration.addAnnotatedClass(SavingsBook.class);
        configuration.addAnnotatedClass(InterestDetail.class);
        configuration.addAnnotatedClass(Deposit.class);
        configuration.addAnnotatedClass(Withdraw.class);
        
        serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        threadLocal = new ThreadLocal<Session>();

    } catch(HibernateException t){
        throw new ExceptionInInitializerError(t);
    }
    }
    
    public static Session getSession() {
        Session session = threadLocal.get();
        if(session == null){
            session = sessionFactory.openSession();
        threadLocal.set(session);
        }
        return session;
    }
    
    
    public static void closeSession() {
        Session session = threadLocal.get();
        if(session != null){
            session.close();
        threadLocal.set(null);
        }

    }
    
    
    public static void closeSessionFactory() {
        sessionFactory.close();
        StandardServiceRegistryBuilder.destroy(serviceRegistry);
    }
    
   
}
