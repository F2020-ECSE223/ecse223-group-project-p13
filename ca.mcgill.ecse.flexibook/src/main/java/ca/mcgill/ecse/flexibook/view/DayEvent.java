package ca.mcgill.ecse.flexibook.view;

import ca.mcgill.ecse.flexibook.controller.TOAppointmentCalendarItem;

public class DayEvent {
    private String startTime;
    private String endTime;
    private String username;
    public DayEvent(TOAppointmentCalendarItem item){
        startTime = item.getStartTime().toString();
        endTime = item.getEndTime().toString();
        username = "Jawn";
    }
    public String getUsername() { return username;}
    public void setUsername(String username) { this.username = username; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
}

