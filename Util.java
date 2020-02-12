/**
 * Пакет ru.job4j.parsersqlsite для
 *
 * @author Maksim Tiunchik
 */
package ru.job4j.parsersqlsite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс UnderstanDate - парси текст в дату и имеет метод проверки названия заявки на работу
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 12.02.2020
 */
public class Util {
    private static final Logger LOG = LogManager.getLogger(Util.class.getName());

    private final static Map<String, String> DATEMAP = new HashMap<>();

    private static void startMap() {
        DATEMAP.put("янв", "01");
        DATEMAP.put("фев", "02");
        DATEMAP.put("мар", "03");
        DATEMAP.put("апр", "04");
        DATEMAP.put("май", "05");
        DATEMAP.put("июн", "06");
        DATEMAP.put("июл", "07");
        DATEMAP.put("авг", "08");
        DATEMAP.put("сен", "09");
        DATEMAP.put("окт", "10");
        DATEMAP.put("ноя", "11");
        DATEMAP.put("дек", "12");
    }

    public static Date parseDate(String text) {
        String[] set = text.split(" ");
        if (set.length == 1) {
            if (set[0].equals("вчера")) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                return cal.getTime();
            }
            if (set[0].equals("сегодня")) {
                Calendar cal = Calendar.getInstance();
                return cal.getTime();
            }
        } else {
            if (DATEMAP.isEmpty()) {
                startMap();
            }
            if (set[0].length() == 1) {
                set[0] = "0" + set[0];
            }
            if (set[2].length() == 2) {
                set[2] = "20" + set[2];
            }
            Date date = null;
            try {
                date = new SimpleDateFormat("dd-MM-yyyy")
                        .parse(set[0] + "-" + DATEMAP.get(set[1]) + "-" + set[2]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }
        return null;
    }

    public static boolean checkPatter(String text) {
        return text.matches(".*[Jj][Aa][Vv][Aa]+.*") & !text.matches(".*[Ss][Cc][Rr][Ii][Pp][Tt]+.*");
    }
}
