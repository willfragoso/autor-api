package com.exemplo.autor.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {

    public static Date getDateFromString(String dataString, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(dataString);
    }

}
