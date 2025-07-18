package com.devPro.interfaces;

import java.time.format.DateTimeFormatter;

public interface IFormatDate {
    default String formatDay(String date){
        String [] parts = date.split("[-/]");
        String day = parts[0];
        String month = parts[1];
        String year = parts[2];
        if(day.length()==1)day="0"+day;
        if(month.length()==1)month="0"+month;
        return day+"-"+month+"-"+year;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
}
