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
import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * Класс UploadToBD - клсс для работы с SQL сервевом
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 12.02.2020
 */
public class UploadToBD {
    private static final Logger LOG = LogManager.getLogger(UploadToBD.class.getName());

    UploadToBD() {

    }

    UploadToBD(Properties config) {
        makeConnection(config);
        createDB();
        createTable();
    }

    private Connection connection;

    public void makeConnection(Properties config) {
        try {
            Class.forName(config.getProperty("jdbc.driver"));
            this.connection = DriverManager.getConnection(
                    config.getProperty("jdbc.url"),
                    config.getProperty("jdbc.username"),
                    config.getProperty("jdbc.password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void createDB() {
        try (Statement st = connection.createStatement()) {
            st.execute("SELECT 'CREATE DATABASE sqldb'"
                    + " WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'sqldb')");
        } catch (SQLException e) {
            LOG.error("create db error", e);
        }
    }

    public void createTable() {
        try (Statement st = connection.createStatement()) {
            st.execute("create table if not exists sqltable (id serial primary key, names varchar(200), alltext text, links varchar(200))");
        } catch (SQLException e) {
            LOG.error("create table error", e);
        }
    }

    public void upload(List<ParseSite.Node> exm) {
        try (PreparedStatement pst = connection.prepareStatement("insert into sqltable (names, alltext, links) values (?,?,?)");
             PreparedStatement checkst = connection.prepareCall("select count(names) as namecount from sqltable where names=?")) {
            for (var e : exm) {
                checkst.setString(1, e.name);
                try (ResultSet res = checkst.executeQuery();
                     PreparedStatement del = connection.prepareStatement("delete from sqltable where name=?")) {
                    res.next();
                    if (res.getInt("namecount") > 0) {
                        del.setString(1, e.name);
                        del.executeUpdate();
                    }
                }
                pst.setString(1, e.name);
                pst.setString(2, e.description);
                pst.setString(3, e.link);
            }
        } catch (SQLException e) {
            LOG.error("insert into sqltable error", e);
        }
    }

    public void dropTable() {
        try (Statement st = connection.createStatement()) {
            st.execute("drop table if exists sqltable");
        } catch (SQLException e) {
            LOG.error("drop table error", e);
        }
    }

    public boolean check() {
        try (Statement st = connection.createStatement()) {
            try (ResultSet res = st.executeQuery("select count(id) as idcount from sqltable")) {
                res.next();
                return res.getInt("idcount") > 0;
            }
        } catch (SQLException e) {
            LOG.error("check table error", e);
        }
        return false;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOG.error("close error", e);
        }
    }
}
