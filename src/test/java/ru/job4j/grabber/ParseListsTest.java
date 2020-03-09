package ru.job4j.grabber;

import org.junit.Assert;
import org.junit.Test;

public class ParseListsTest {

    @Test
    public void collectUrls() {
        ParseLists temp = new ParseLists();
        temp.collectUrls("https://www.sql.ru/forum/job/1");
        Assert.assertTrue(temp.pollTenUrls().size() > 19);
    }
}