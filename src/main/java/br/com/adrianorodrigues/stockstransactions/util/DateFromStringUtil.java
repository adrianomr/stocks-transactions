package br.com.adrianorodrigues.stockstransactions.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateFromStringUtil {

    private DateFromStringUtil() {
    }

    public static Date getDate(String data) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        return sdf1.parse(data);
    }

    public static LocalDate getLocalDate(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/[uuuu][uu]");
        return LocalDate.parse(data, formatter);
    }
}