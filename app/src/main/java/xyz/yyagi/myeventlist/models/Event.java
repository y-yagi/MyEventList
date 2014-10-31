package xyz.yyagi.myeventlist.models;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import xyz.yyagi.myeventlist.utils.Util;

/**
 * Created by yaginuma on 14/10/31.
 */
public class Event {
    private Long id;
    /** Not-null value. */
    private String title;
    /** Not-null value. */
    private String detail;
    /** Not-null value. */
    private String url;
    private int start;
    private int end;

    public Event() {
    }

    public Event(Long id) {
        this.id = id;
    }

    public Event(Long id, String title, String detail, String url, int start, int end) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.url = url;
        this.start = start;
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getTitle() {
        return title;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Not-null value. */
    public String getDetail() {
        return detail;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /** Not-null value. */
    public String getUrl() {
        return url;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setUrl(String url) {
        this.url = url;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    // KEEP METHODS
    public String toString() {
        String str = this.getFormatDates();
        str += ("\n" + title + " " + detail);
        return str;
    }

    public String getFormatDates() {
        DateTime startTime = Util.convertIntToDateTime(start);
        DateTime endTime = Util.convertIntToDateTime(end);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        String str = fmt.print(startTime);

        fmt = DateTimeFormat.forPattern("HH:mm");
        str += "~" ;

        if (end != 0) {
            str += fmt.print(endTime);
        }
        return str;
    }
    // KEEP METHODS END
}
