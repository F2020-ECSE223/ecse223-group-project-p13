package ca.mcgill.ecse.flexibook.util;

import java.time.LocalDateTime;

public class SystemTime {
    //Idk yet but useful in the future
    private static LocalDateTime date =  LocalDateTime.now();
    public static LocalDateTime getDate(){
        return date;
    }
}

