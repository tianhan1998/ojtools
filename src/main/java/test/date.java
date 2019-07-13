package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class date {
    public static void main(String[] args) throws ParseException {
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String formate_date=simpleDateFormat.format(date.getTime());
        System.out.println(formate_date);
        date=simpleDateFormat.parse(formate_date);
        System.out.println(date);
    }
}
