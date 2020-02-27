/**
 * Package ru.job4j.parsersqlsite for
 *
 * @author Maksim Tiunchik
 */
package ru.job4j.parsersqlsite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class Node -
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 27.02.2020
 */
class Node {

    Node(String name, String desc, String link) {
        this.name = name;
        this.description = desc;
        this.link = link;
    }

    String name;
    String description;
    String link;
}
