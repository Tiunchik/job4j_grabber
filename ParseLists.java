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
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс ParseLists - парсит страницы на форуме, постараеться нчать с 1. если она не была 1-й
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 12.02.2020
 */
public class ParseLists implements ListSearcher {
    private static final Logger LOG = LogManager.getLogger(ParseLists.class.getName());

    private TreeSet<String> lists = new TreeSet<>();
    private List<String> temp = new ArrayList<>();

    @Override
    public void collectUrls(String url) {
        lists.add(url);
        int indexfinish = lists.size() + 100;
        while (lists.size() < indexfinish) {
            int check = lists.size();
            String tempUrl;
            tempUrl = check == 1 ? lists.last() : temp.get(temp.size() - 2);
            try {
                Document doc = Jsoup.connect(tempUrl).get();
                Element all = doc.getElementsByClass("sort_options").last();
                temp.addAll(all.getElementsByTag("a").eachAttr("href"));
                lists.addAll(temp);
            } catch (IOException e) {
                LOG.error("search lists error", e);
            }
            if (check == lists.size()) {
                break;
            }
        }
    }

    @Override
    public List<String> pollTenUrls() {
        List<String> answer = new ArrayList<>();
        for (int index = 0; index < 20; index++) {
            answer.add(lists.pollFirst());
        }
        return answer;
    }
}
