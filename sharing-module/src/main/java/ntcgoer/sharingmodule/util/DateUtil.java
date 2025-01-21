package ntcgoer.sharingmodule.util;

import java.util.Date;

public class DateUtil {
    public Date getDateByMinusHour(int hour) {
        return new Date(System.currentTimeMillis() - (long) hour * 60 * 60 * 1000);
    }

    public Date getDateByPlusDay(int day){
        return new Date(System.currentTimeMillis() + (long) day * 24*60*60*1000);
    }
}
