/**
 * Пакет ru.job4j.parsersqlsite для
 *
 * @author Maksim Tiunchik
 */
package ru.job4j.parsersqlsite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


import java.util.*;


/**
 * Класс MainParserCombiner - в данном класе собраны все методы для парсинга сайта в целом
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 12.02.2020
 */
public class Parser implements Job {
    private static final Logger LOG = LogManager.getLogger(Parser.class.getName());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap temp = jobExecutionContext.getJobDetail().getJobDataMap();
        Properties prop = new Properties();
        Calendar cal = Calendar.getInstance();
        Date date;
        prop.putAll(temp);
        DBloader sqlbase = new UploadToBD();
        sqlbase.makeConnection(prop);
        sqlbase.createDB();
        sqlbase.createTable();
        if (sqlbase.check()) {
            cal.add(Calendar.DATE, -1);
            date = cal.getTime();
        } else {
            cal.set(Calendar.DAY_OF_YEAR, 1);
            date = cal.getTime();
        }
        String url = "https://www.sql.ru/forum/job/1";
        ListSearcher seacher = new ParseLists();
        CollectLinks links = new ParseLinksFromPage();
        CollectAdInfo getIngo = new ParseAd();

        start(url, date, seacher,
                links, getIngo, sqlbase);
    }

    public void start(String url, Date date, ListSearcher seacher,
                      CollectLinks links, CollectAdInfo getIngo, DBloader dbuser) {
        seacher.collectUrls(url);
        List<String> circleArray = seacher.pollTenUrls();
        while (!circleArray.isEmpty()) {
            ParsePart onethread = ParsePart.getExemple(circleArray, links, getIngo, date, dbuser);
            onethread.run();
        }
    }
}
