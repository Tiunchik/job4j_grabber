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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс ParseAdDate - парсит сообщения, их содержимое и последнюю дату сообщения в обьявлении
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since -
 */
public class ParseAd implements CollectAdInfo {
    private static final Logger LOG = LogManager.getLogger(ParseAd.class.getName());

    private final static Map<String, String> DATEMAP = new HashMap<>();
    private Document link;

    private static void startMap() {
        DATEMAP.put("янв", "01");
        DATEMAP.put("фев", "02");
        DATEMAP.put("мар", "03");
        DATEMAP.put("апр", "04");
        DATEMAP.put("май", "05");
        DATEMAP.put("июн", "06");
        DATEMAP.put("июл", "07");
        DATEMAP.put("авг", "08");
        DATEMAP.put("сен", "09");
        DATEMAP.put("окт", "10");
        DATEMAP.put("ноя", "11");
        DATEMAP.put("дек", "12");
    }

    @Override
    public Node checkPage(String url, Date date) {
        try {
            link = Jsoup.connect(url).get();
            Date innerdate = getAdDate();
            if (innerdate.compareTo(date) > 0) {
                return new Node(link.title(), getAdDescr(), url);
            }
        } catch (IOException e) {
            LOG.error("Connection error", e);
        }
        return null;
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
                date = parseDate(allList);
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

    public static Date parseDate(String text) {
        String[] set = text.split(" ");
        if (set.length == 1) {
            if (set[0].equals("вчера")) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                return cal.getTime();
            }
            if (set[0].equals("сегодня")) {
                Calendar cal = Calendar.getInstance();
                return cal.getTime();
            }
        } else {
            if (DATEMAP.isEmpty()) {
                startMap();
            }
            if (set[0].length() == 1) {
                set[0] = "0" + set[0];
            }
            if (set[2].length() == 2) {
                set[2] = "20" + set[2];
            }
            Date date = null;
            try {
                date = new SimpleDateFormat("dd-MM-yyyy")
                        .parse(set[0] + "-" + DATEMAP.get(set[1]) + "-" + set[2]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }
        return null;
    }

}
