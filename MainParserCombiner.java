/**
 * Пакет ru.job4j.parsersqlsite для
 *
 * @author Maksim Tiunchik
 */
package ru.job4j.parsersqlsite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Класс MainParserCombiner - в данном класе собраны все методы для парсинга сайта в целом
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 12.02.2020
 */
public class MainParserCombiner {
    private static final Logger LOG = LogManager.getLogger(MainParserCombiner.class.getName());

    public List<ParseSite.Node> start(String url, Date border) {
        List<ParseSite.Node> answer = new ArrayList<>();
        String e = url;
        ParseSite mainParser = new ParseSite();
        ParseLists listSearch = new ParseLists();
        while (!e.equals("")) {
            answer.addAll(mainParser.checkSQL(e, border));
            e = listSearch.nextList(e);
            if (mainParser.lastlist) {
                break;
            }
        }
        return answer;
    }
}
