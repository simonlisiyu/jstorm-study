package test;

import com.lsy.jstorm.utils.DateUtils;
import org.apache.hadoop.hdfs.protocol.proto.HdfsProtos;
import org.apache.hadoop.util.Time;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lisiyu on 2016/11/11.
 */
public class DateTest {
    public static void main(String[] args) {
//        Date today = new Date();
//        System.out.println(Calendar.getInstance().get(Calendar.YEAR));
//        SimpleDateFormat year = new SimpleDateFormat("yyyy");
//
//        String msg = "Nov 14 18:33:29" + " " + year.format(today);
//
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd hh:mm:ss yyyy", Locale.ENGLISH);
//            System.out.println(sdf.parse(msg));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd hh:mm:ss yyyy", Locale.ENGLISH);
//        System.out.println(System.currentTimeMillis());
//        System.out.println(sdf.format(System.currentTimeMillis()));
//        System.out.println(sdf.format(1479377406863L));

        try {
            Date date = DateUtils.strToDate("2016-11-17 15:34:24");
            System.out.println(date);
            Long l = date.getTime();
            System.out.println(l);
            Timestamp ts = DateUtils.dateToTimestamp(date);
            System.out.println(ts);
            System.out.println(ts.getTime());
//            System.out.println(ts.getDateTime());
//            System.out.println(ts.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
