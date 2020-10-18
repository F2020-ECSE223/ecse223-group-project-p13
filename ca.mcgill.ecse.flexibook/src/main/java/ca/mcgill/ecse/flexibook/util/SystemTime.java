package ca.mcgill.ecse.flexibook.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SystemTime {
    //Idk yet but useful in the future
    private static LocalDateTime date =  LocalDateTime.now();
    public static LocalDateTime getDate(){
        return date;
    }
    public static LocalDateTime setTime(String s){
        date = LocalDateTime.parse(s,DateTimeFormatter.ofPattern("uuuu-MM-dd+kk:mm"));
        return date;
    }
}

