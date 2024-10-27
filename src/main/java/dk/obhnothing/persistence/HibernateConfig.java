package dk.obhnothing.persistence;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import dk.obhnothing.persistence.ent.Ability;
import dk.obhnothing.persistence.ent.Form;
import dk.obhnothing.persistence.ent.Move;
import dk.obhnothing.persistence.ent.Pokemon;
import dk.obhnothing.persistence.ent.Sprite;
import dk.obhnothing.persistence.ent.Type;
import dk.obhnothing.security.entities.Role;
import dk.obhnothing.security.entities.User;
import jakarta.persistence.EntityManagerFactory;

public class HibernateConfig {
    private static EntityManagerFactory emf;
    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null)
            throw new RuntimeException("No EntityManagerFactory Instance");
        return emf;
    }

    private static void getAnnotationConfiguration(Configuration configuration) {
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Role.class);
        configuration.addAnnotatedClass(Pokemon.class);
        configuration.addAnnotatedClass(Ability.class);
        configuration.addAnnotatedClass(Move.class);
        configuration.addAnnotatedClass(Sprite.class);
        configuration.addAnnotatedClass(Form.class);
        configuration.addAnnotatedClass(Type.class);
    }

    public static void Init(Mode mode) {
        try {
            Configuration configuration = new Configuration();
            Properties props = new Properties();
            props = setBaseProperties(props);

            if (mode == Mode.DEV)
                props = setDevProperties(props);
            else if (mode == Mode.TEST)
                props = setTestProperties(props);
            else if (mode == Mode.DEPLOY)
                props = setDeployedProperties(props);

            System.out.println(props);

            configuration.setProperties(props);
            getAnnotationConfiguration(configuration);

            ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder().
                applySettings(configuration.getProperties()).build();
            SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);
            emf = sf.unwrap(EntityManagerFactory.class);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Properties setBaseProperties(Properties props){
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.current_session_context_class", "thread");
        props.put("hibernate.show_sql", "false");
        props.put("hibernate.format_sql", "false");
        props.put("hibernate.use_sql_comments", "false");
        return props;
    }

    private static Properties setDevProperties(Properties props){
        props.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/" + System.getenv("DB_NAME"));
        props.setProperty("hibernate.connection.username", System.getenv("DB_USER"));
        props.setProperty("hibernate.connection.password", System.getenv("DB_PW"));
        return props;
    }

    private static Properties setDeployedProperties(Properties props){
        props.setProperty("hibernate.connection.url", System.getenv("DB_CONN_STR"));
        props.setProperty("hibernate.connection.username", System.getenv("DB_USER"));
        props.setProperty("hibernate.connection.password", System.getenv("DB_PW"));
        return props;
    }

    private static Properties setTestProperties(Properties props) {
        props.put("hibernate.connection.driver_class", "org.testcontainers.jdbc.ContainerDatabaseDriver");
        props.put("hibernate.connection.url", "jdbc:tc:postgresql:15.3-alpine3.18:///test_db");
        props.put("hibernate.connection.username", "postgres");
        props.put("hibernate.connection.password", "postgres");
        props.put("hibernate.archive.autodetection", "class");
        props.put("hibernate.show_sql", "false");
        props.put("hibernate.hbm2ddl.auto", "create-drop");
        return props;
    }

    public enum Mode {
        DEV, TEST, DEPLOY
    }

}

