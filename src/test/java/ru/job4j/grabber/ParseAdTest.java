package ru.job4j.grabber;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseAdTest {

    @Test
    public void checkPage() throws ParseException {
        CollectAdInfo ad = new ParseAd();
        SimpleDateFormat dateformay = new SimpleDateFormat("dd/MM/yyyy");
        Date tempDate = dateformay.parse("15/02/2019");
        Node e = ad.checkPage("https://www.sql.ru/forum/1232070/ishhu-rabotu-dlya-java-razrabotchika", tempDate);
        System.out.println(e.name);
        System.out.println(e.description);
        System.out.println(e.link);
    }
}