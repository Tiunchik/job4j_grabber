/**
 * Пакет ru.job4j.parsersqlsite для
 *
 * @author Maksim Tiunchik
 */
package ru.job4j.parsersqlsite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;

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
        Config config = new Config();
        Properties prop = config.getConfig();
        String pattern  = prop.getProperty("cron.time");
        try {
            SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
            Scheduler sched = schedFact.getScheduler();
            sched.start();
            JobDetail job = newJob(SiteParserStart.class)
                    .build();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(cronSchedule(pattern))
                    .build();
            sched.scheduleJob(job, trigger);
        } catch (Exception e) {
            LOG.error("Schedule error", e);
        }
    }
}
