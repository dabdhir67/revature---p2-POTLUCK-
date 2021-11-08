package com.revature.utilities;

import com.revature.models.Chef;
import com.revature.models.Recipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class SessionUtil {
    private static final Logger logger  = LogManager.getLogger(SessionUtil.class);
    private static final SessionFactory sessionFactory;

    static {
        logger.info("Building Session Factory...");
        Properties settings = new Properties();
        settings.put(Environment.DRIVER, System.getenv("DB_DRIVER"));
        settings.put(Environment.URL, System.getenv("DB_URL"));
        settings.put(Environment.USER, System.getenv("DB_USER"));
        settings.put(Environment.PASS, System.getenv("DB_PASS"));
        settings.put(Environment.DIALECT, System.getenv("DB_DIALECT"));

        settings.put(Environment.HBM2DDL_AUTO, "update");

        Configuration configuration = new Configuration()
                .setProperties(settings)
                .addAnnotatedClass(Chef.class)
                .addAnnotatedClass(Recipe.class);

        sessionFactory = configuration.buildSessionFactory();
        logger.info("Session Factory Successfully Instantiated.");
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public static void closeFactory() {
        sessionFactory.close();
    }
}
