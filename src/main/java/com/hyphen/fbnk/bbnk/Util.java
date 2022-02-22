package com.hyphen.fbnk.bbnk;

import java.util.Calendar;

public class Util {
    public static String getCurDtTm(){
        int yyyy, mm, dd, hh, min, ss;
        Calendar cal = Calendar.getInstance();

        yyyy    = cal.get(Calendar.YEAR);
        mm 		= cal.get(Calendar.MONTH);
        dd 		= cal.get(Calendar.DATE);
        hh 	    = cal.get(Calendar.HOUR_OF_DAY);
        min 	= cal.get(Calendar.MINUTE);
        ss 	    = cal.get(Calendar.SECOND);

        StringBuffer sb = new StringBuffer();
        sb.append(yyyy).append(mm < 9 ? "0" : "").append(mm + 1).append(dd < 10 ? "0" : "").append(dd).append(hh < 10 ? "0" : "").append(hh);
        sb.append(min < 10 ? "0" : "").append(min).append(ss < 10 ? "0" : "").append(ss);

        return sb.toString();
    }


}
