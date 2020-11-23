package ca.mcgill.ecse.flexibook.view;

import ca.mcgill.ecse.flexibook.controller.TOAppointmentCalendarItem;

public class DayEvent {
    private TOAppointmentCalendarItem appointment;
    private String startTime;
    private String endTime;
    private String username;
    private String date;
    private String service;


    public DayEvent(TOAppointmentCalendarItem item){
        startTime = item.getStartTime().toString();
        endTime = item.getEndTime().toString();
        username = item.getUsername();
        appointment = item;
        date = item.getDate().toString();
        service = item.getMainService();
    }

    public String getService() { return service;}
    public void setService(String service) { this.service = service; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getUsername() { return username;}
    public void setUsername(String username) { this.username = username; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public TOAppointmentCalendarItem getAppointment() { return appointment; }
    public void setAppointment(TOAppointmentCalendarItem appointment) { this.appointment = appointment; }
}

