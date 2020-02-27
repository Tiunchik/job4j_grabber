/**
 * Package ru.job4j.parsersqlsite for
 *
 * @author Maksim Tiunchik
 */
package ru.job4j.parsersqlsite;

import java.util.Date;
import java.util.List;

/**
 * Interface CollectAdInfo - 
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 27.02.2020
 */
public interface CollectAdInfo {

    Node checkPage(String url, Date date);

}
