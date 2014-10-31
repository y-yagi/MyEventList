package xyz.yyagi.myeventlist.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Created by yaginuma on 14/10/31.
 */
public class Util {
    private static final String TAG = Util.class.getSimpleName();

    static public int convertDateTimeStrToInt(String time) {
        DateTimeZone zone = DateTimeZone.forID("Asia/Tokyo");
        DateTimeFormatter timeParser = ISODateTimeFormat.dateTimeNoMillis().withZone(zone);
        DateTime dateTime;
        // FIXME: atndのAPIが"null"を返すケースがあるので対応。本来はもっと上の層で対応すべき。
        if (time == "null") {
            return 0;
        }

        dateTime = timeParser.parseDateTime(time);
        return (int)(dateTime.getMillis() / 1000L);
    }

    static public DateTime convertIntToDateTime(int time) {
        DateTimeZone zone = DateTimeZone.forID("Asia/Tokyo");
        return new DateTime(time * 1000L, zone);
    }
}
