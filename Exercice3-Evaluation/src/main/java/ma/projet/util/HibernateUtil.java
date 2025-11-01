package ma.projet.util;

import java.io.InputStream;
import java.util.Properties;
import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties props = new Properties();
                try (InputStream in = HibernateUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
                    if (in != null) props.load(in);
                }

                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
                registryBuilder.applySetting("hibernate.connection.driver_class", props.getProperty("jdbc.driverClassName"));
                registryBuilder.applySetting("hibernate.connection.url", props.getProperty("jdbc.url"));
                registryBuilder.applySetting("hibernate.connection.username", props.getProperty("jdbc.username"));
                registryBuilder.applySetting("hibernate.connection.password", props.getProperty("jdbc.password"));
                registryBuilder.applySetting("hibernate.dialect", props.getProperty("hibernate.dialect"));
                registryBuilder.applySetting("hibernate.hbm2ddl.auto", props.getProperty("hibernate.hbm2ddl.auto", "update"));
                registryBuilder.applySetting("hibernate.show_sql", props.getProperty("hibernate.show_sql", "false"));
                registryBuilder.applySetting("hibernate.format_sql", props.getProperty("hibernate.format_sql", "false"));
                registryBuilder.applySetting("hibernate.current_session_context_class", props.getProperty("hibernate.current_session_context_class", "thread"));

                StandardServiceRegistry registry = registryBuilder.build();
                MetadataSources sources = new MetadataSources(registry)
                        .addAnnotatedClass(Homme.class)
                        .addAnnotatedClass(Femme.class)
                        .addAnnotatedClass(Mariage.class);
                sessionFactory = sources.buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                throw new RuntimeException("Failed to build SessionFactory", e);
            }
        }
        return sessionFactory;
    }
}
