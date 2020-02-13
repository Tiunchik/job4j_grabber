/**
 * Пакет ru.job4j.parsersqlsite для
 *
 * @author Maksim Tiunchik
 */
package ru.job4j.parsersqlsite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;

import java.util.Properties;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Класс TimeStart - стартовый класс, запускает приложение сразу и каждые 12 часов
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 12.02.2020
 */
public class TimeStart {
    private static final Logger LOG = LogManager.getLogger(TimeStart.class.getName());

    public static void main(String[] args) {
        String conf;
        if (args.length > 0) {
            conf = args[0];
        } else {
            conf = "site.properties";
        }
        Config config = new Config();
        Properties prop = config.getConfig(conf);
        String pattern = prop.getProperty("cron.time");
        try {
            SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
            Scheduler sched = schedFact.getScheduler();
            JobDataMap map = new JobDataMap();
            map.put("jdbc.driver", prop.getProperty("jdbc.driver"));
            map.put("jdbc.url", prop.getProperty("jdbc.url"));
            map.put("jdbc.username", prop.getProperty("jdbc.username"));
            map.put("jdbc.password", prop.getProperty("jdbc.password"));
            JobDetail job = newJob(SiteParserStart.class)
                    .setJobData(map)
                    .build();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(cronSchedule(pattern))
                    .build();
            sched.scheduleJob(job, trigger);
            sched.start();
        } catch (Exception e) {
            LOG.error("Schedule error", e);
        }
    }
}
