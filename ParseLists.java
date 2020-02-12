/**
 * Пакет ru.job4j.parsersqlsite для
 *
 * @author Maksim Tiunchik
 */
package ru.job4j.parsersqlsite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

/**
 * Класс ParseLists - парсит страницы на форуме, постараеться нчать с 1. если она не была 1-й
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 12.02.2020
 */
public class ParseLists {
    private static final Logger LOG = LogManager.getLogger(ParseLists.class.getName());

    private HashSet<String> lists = new HashSet<>();

    public String nextList(String thisList) {
        lists.add(thisList);
        try {
            Document doc = Jsoup.connect(thisList).get();
            Element all = doc.getElementsByClass("sort_options").last();
            for (var e : all.getElementsByTag("a").eachAttr("href")) {
                if (!lists.contains(e)) {
                    return e;
                }
            }
        } catch (IOException e) {
            LOG.error("nextList error", e);
        }
        return "";
    }
}
