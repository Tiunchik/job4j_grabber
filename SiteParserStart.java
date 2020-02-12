/**
 * Пакет ru.job4j.parsersqlsite для
 *
 * @author Maksim Tiunchik
 */
package ru.job4j.parsersqlsite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Класс SiteParserStart - полный набор для работы приложения, запускаеться через таймер-триггер-класс
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 12.02.2020
 */
public class SiteParserStart implements Job {
    private static final Logger LOG = LogManager.getLogger(SiteParserStart.class.getName());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Calendar cal = Calendar.getInstance();
        Date date;
        Config config = new Config();
        Properties prop = config.getConfig();
        UploadToBD sqlbase = new UploadToBD();
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
        MainParserCombiner parse = new MainParserCombiner();
        List<ParseSite.Node> list = parse.start("https://www.sql.ru/forum/job/1", date);
        sqlbase.upload(list);
    }
}
