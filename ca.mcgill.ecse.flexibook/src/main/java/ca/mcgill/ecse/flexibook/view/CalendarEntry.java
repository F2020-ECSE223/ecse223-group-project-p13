package ca.mcgill.ecse.flexibook.view;

import com.jfoenix.controls.JFXButton;

import java.time.LocalDate;

public class CalendarEntry extends JFXButton {
    private LocalDate date;
    public CalendarEntry(String date){
        super(date);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
