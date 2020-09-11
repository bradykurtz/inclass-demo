package edu.weber.dao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.flywaydb.core.Flyway;

@WebListener
public class FlywayListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce)  {
        Flyway flyway = Flyway.configure()
            .dataSource(DatabaseConnection.getDataSource())
            .load();
        System.out.println("Starting FlyWay Migration");
        flyway.migrate();
        System.out.println("Finished FlyWay Migration");
    }
}
