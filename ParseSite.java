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
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Класс ParseThisPage -
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since -
 */
public class ParseSite {
    private static final Logger LOG = LogManager.getLogger(ParseSite.class.getName());

    public boolean lastlist = false;

    public List<Node> checkSQL(String url, Date date) {
        List<Node> answer = new ArrayList<>();
        String name;
        String description;
        String link;
        Date innerDate;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements tags = doc.getElementsByClass("postslisttopic");
            for (var e : tags) {
                name = e.select("a").first().text();
                if (!Util.checkPatter(name)) {
                    continue;
                }
                link = e.getElementsByTag("a").first().attr("href");
                ParseAd temp = new ParseAd(link);
                innerDate = temp.getAdDate();
                if (innerDate.compareTo(date) > 0) {
                    description = temp.getAdDescr();
                    answer.add(new Node(name, description, link));
                } else {
                    lastlist = true;
                }
            }
        } catch (IOException ex) {
            LOG.error("main connect to site error", ex);
        }
        return answer;
    }


    static class Node {

        Node(String name, String desc, String link) {
            this.name = name;
            this.description = desc;
            this.link = link;
        }

        String name;
        String description;
        String link;
    }
}
