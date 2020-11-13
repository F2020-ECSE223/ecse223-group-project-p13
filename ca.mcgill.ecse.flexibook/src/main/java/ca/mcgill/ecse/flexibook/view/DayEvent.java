package ca.mcgill.ecse.flexibook.view;

import ca.mcgill.ecse.flexibook.controller.TOAppointmentCalendarItem;

public class DayEvent {
    private TOAppointmentCalendarItem appointment;
    private String startTime;
    private String endTime;
    private String username;
    public DayEvent(TOAppointmentCalendarItem item){
        startTime = item.getStartTime().toString();
        endTime = item.getEndTime().toString();
        username = item.getUsername();
        appointment = item;
    }
    public String getUsername() { return username;}
    public void setUsername(String username) { this.username = username; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public TOAppointmentCalendarItem getAppointment() { return appointment; }
    public void setAppointment(TOAppointmentCalendarItem appointment) { this.appointment = appointment; }
}

