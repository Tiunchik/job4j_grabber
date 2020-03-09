package ru.job4j.grabber;

import org.junit.Test;

public class ParseLinksFromPageTest {

    @Test
    public void manualTest() {
        CollectLinks link = new ParseLinksFromPage();
        link.parsePage("https://www.sql.ru/forum/job/9").forEach(System.out::println);
    }

}