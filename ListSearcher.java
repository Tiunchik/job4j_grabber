/**
 * Package ru.job4j.parsersqlsite for
 *
 * @author Maksim Tiunchik
 */
package ru.job4j.parsersqlsite;

import java.util.List;

/**
 * Interface ListSearcher - 
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 27.02.2020
 */
public interface ListSearcher {

    void collectUrls(String url);

    List<String> pollTenUrls();

}
