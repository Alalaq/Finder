package ru.kpfu.itis.listeners;

import ru.kpfu.itis.datasource.SimpleDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Alisher Khabibullin (Alalaq) <alisher.khabibullin@gmail.com>
 */


@WebListener()
public class InitListener implements ServletContextListener {
    private SimpleDataSource dataSource;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("C:\\Users\\muzik\\Desktop\\Semester_work\\src\\main\\resources\\db.properties"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        this.dataSource = new SimpleDataSource(properties.getProperty("db.url"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password"));

        sce.getServletContext().setAttribute("dataSource", dataSource);
    }
}
