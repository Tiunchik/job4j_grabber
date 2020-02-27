/**
 * Package ru.job4j.parsersqlsite for
 *
 * @author Maksim Tiunchik
 */
package ru.job4j.parsersqlsite;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Interface ParsePart -
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 27.02.2020
 */
public class ParsePart implements Runnable {

    CollectLinks links;
    CollectAdInfo getIngo;
    LinkedList<String> urls;
    DBloader dbuser;
    Date date;

    private ParsePart(List<String> urls, CollectLinks links, CollectAdInfo getIngo, Date date, DBloader dbuser) {
        this.links = links;
        this.getIngo = getIngo;
        this.urls = new LinkedList<>(urls);
        this.dbuser = dbuser;
        this.date = date;
    }

    static ParsePart getExemple(List<String> urls, CollectLinks links, CollectAdInfo getIngo, Date date, DBloader dbuser) {
        return new ParsePart(urls, links, getIngo, date, dbuser);
    }

    @Override
    public void run() {
        List<String> urlforAd = new ArrayList<>();
        for (var e : urls) {
            urlforAd.addAll(links.parsePage(e));
        }
        List<Node> nodeList = new ArrayList<>();
        for (var e : urlforAd) {
            nodeList.add(getIngo.checkPage(e, date));
        }
        nodeList = nodeList.parallelStream().filter(Objects::nonNull).collect(Collectors.toList());
        dbuser.upload(nodeList);
    }
}
