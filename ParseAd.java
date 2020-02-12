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
import java.util.Date;

/**
 * Класс ParseAdDate - парсит сообщения, их содержимое и последнюю дату сообщения в обьявлении
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since -
 */
public class ParseAd {
    private static final Logger LOG = LogManager.getLogger(ParseAd.class.getName());

    private Document link;

    ParseAd(String url) {
        try {
            link = Jsoup.connect(url).get();
        } catch (IOException e) {
            LOG.error("Connection error", e);
        }
    }


    public Date getAdDate() {
        Date date;
        String allList;
        Elements title = link.getElementsByClass("sort_options");
        if (title.size() > 0) {
            allList = title.last().getElementsByTag("a").last().attr("href");
            try {
                Document newLink = Jsoup.connect(allList).get();
                title = newLink.getElementsByClass("msgFooter");
                allList = title.last().text();
                allList = allList.substring(0, allList.indexOf(","));
                date = Util.parseDate(allList);
                return date;
            } catch (IOException ex) {
                LOG.error("Date exception", ex);
            }
        }
        title = link.getElementsByClass("msgFooter");
        allList = title.last().text();
        allList = allList.substring(0, allList.indexOf(","));
        date = Util.parseDate(allList);
        return date;
    }

    public String getAdDescr() {
        String answer = "";
        answer = link.head().select("meta[name=description]").attr("content");
        answer = answer.substring(answer.indexOf("/ Работа /") + 10);
        return answer;
    }
}
