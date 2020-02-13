/**
 * Пакет ru.job4j.parsersqlsite для
 *
 * @author Maksim Tiunchik
 */
package ru.job4j.parsersqlsite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.psqltrackering.TrackerSQL;

import java.io.InputStream;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Класс Config - конфиг для старта приложеия
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 12.02.2020
 */
public class Config {
    private static final Logger LOG = LogManager.getLogger(Config.class.getName());

    public Properties getConfig(String name) {
        try (InputStream in = TrackerSQL.class.getClassLoader()
                .getResourceAsStream(name)) {
            Properties config = new Properties();
            config.load(in);
            return config;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
