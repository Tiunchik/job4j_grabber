/**
 * Package ru.job4j.parsersqlsite for
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
 * Class ParseLinksFromPage - 
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 27.02.2020
 */
public class ParseLinksFromPage implements CollectLinks {
    private static final Logger LOG = LogManager.getLogger(ParseLinksFromPage.class.getName());


    @Override
    public List<String> parsePage(String url) {
        List<String> answer = new ArrayList<>();
        String name;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements tags = doc.getElementsByClass("postslisttopic");
            for (var e : tags) {
                name = e.select("a").first().text();
                if (checkPatter(name)) {
                    answer.add(e.getElementsByTag("a").first().attr("href"));
                }
            }
        } catch (IOException ex) {
            LOG.error("main connect to site error", ex);
        }
        return answer;
    }

    public static boolean checkPatter(String text) {
        return text.matches(".*[Jj][Aa][Vv][Aa]+.*") & !text.matches(".*[Ss][Cc][Rr][Ii][Pp][Tt]+.*");
    }
}
