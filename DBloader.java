/**
 * Package ru.job4j.parsersqlsite for
 *
 * @author Maksim Tiunchik
 */
package ru.job4j.parsersqlsite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Interface DBloader - 
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 27.02.2020
 */
public interface DBloader {

    void makeConnection(Properties config);

    void createDB();

    void createTable();

    void upload(List<Node> exm);

    void dropTable();

    boolean check();

    List<String> print();

    void close();
}
