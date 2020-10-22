package ca.mcgill.ecse.flexibook.controller;
import java.sql.Date;
import java.sql.Time;
public class TOAppointmentCalendarItem {
	Date date;
	Time startTime;
	Time endTime;
	int index;
	public TOAppointmentCalendarItem(Date d, Time t1, Time t2) {
		this.date=d;
		this.startTime=t1;
		this.endTime=t2;
	}
}
