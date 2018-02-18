package cc.akkaha.egg.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class DateUtils {

    public static String currentDate() {
        return DateFormatUtils.format(new Date(), "yyyy-MM-dd");
    }
}
