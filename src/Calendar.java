/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/


import java.sql.Date;
import java.util.*;

// line 25 "Untitled.ump"
// line 116 "Untitled.ump"
public class Calendar
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Calendar Attributes
  private Date date;

  //Calendar Associations
  private List<Day> days;
  private List<TimeSlot> schedule;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Calendar(Date aDate)
  {
    date = aDate;
    days = new ArrayList<Day>();
    schedule = new ArrayList<TimeSlot>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }
  /* Code from template association_GetMany */
  public Day getDay(int index)
  {
    Day aDay = days.get(index);
    return aDay;
  }

  public List<Day> getDays()
  {
    List<Day> newDays = Collections.unmodifiableList(days);
    return newDays;
  }

  public int numberOfDays()
  {
    int number = days.size();
    return number;
  }

  public boolean hasDays()
  {
    boolean has = days.size() > 0;
    return has;
  }

  public int indexOfDay(Day aDay)
  {
    int index = days.indexOf(aDay);
    return index;
  }
  /* Code from template association_GetMany */
  public TimeSlot getSchedule(int index)
  {
    TimeSlot aSchedule = schedule.get(index);
    return aSchedule;
  }

  public List<TimeSlot> getSchedule()
  {
    List<TimeSlot> newSchedule = Collections.unmodifiableList(schedule);
    return newSchedule;
  }

  public int numberOfSchedule()
  {
    int number = schedule.size();
    return number;
  }

  public boolean hasSchedule()
  {
    boolean has = schedule.size() > 0;
    return has;
  }

  public int indexOfSchedule(TimeSlot aSchedule)
  {
    int index = schedule.indexOf(aSchedule);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDays()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Day addDay(boolean aIsClosed, DailyHour aAvailability)
  {
    return new Day(aIsClosed, this, aAvailability);
  }

  public boolean addDay(Day aDay)
  {
    boolean wasAdded = false;
    if (days.contains(aDay)) { return false; }
    Calendar existingCalendar = aDay.getCalendar();
    boolean isNewCalendar = existingCalendar != null && !this.equals(existingCalendar);
    if (isNewCalendar)
    {
      aDay.setCalendar(this);
    }
    else
    {
      days.add(aDay);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDay(Day aDay)
  {
    boolean wasRemoved = false;
    //Unable to remove aDay, as it must always have a calendar
    if (!this.equals(aDay.getCalendar()))
    {
      days.remove(aDay);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDayAt(Day aDay, int index)
  {  
    boolean wasAdded = false;
    if(addDay(aDay))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDays()) { index = numberOfDays() - 1; }
      days.remove(aDay);
      days.add(index, aDay);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDayAt(Day aDay, int index)
  {
    boolean wasAdded = false;
    if(days.contains(aDay))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDays()) { index = numberOfDays() - 1; }
      days.remove(aDay);
      days.add(index, aDay);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDayAt(aDay, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSchedule()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addSchedule(TimeSlot aSchedule)
  {
    boolean wasAdded = false;
    if (schedule.contains(aSchedule)) { return false; }
    schedule.add(aSchedule);
    if (aSchedule.indexOfCalendar(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aSchedule.addCalendar(this);
      if (!wasAdded)
      {
        schedule.remove(aSchedule);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeSchedule(TimeSlot aSchedule)
  {
    boolean wasRemoved = false;
    if (!schedule.contains(aSchedule))
    {
      return wasRemoved;
    }

    int oldIndex = schedule.indexOf(aSchedule);
    schedule.remove(oldIndex);
    if (aSchedule.indexOfCalendar(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aSchedule.removeCalendar(this);
      if (!wasRemoved)
      {
        schedule.add(oldIndex,aSchedule);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addScheduleAt(TimeSlot aSchedule, int index)
  {  
    boolean wasAdded = false;
    if(addSchedule(aSchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSchedule()) { index = numberOfSchedule() - 1; }
      schedule.remove(aSchedule);
      schedule.add(index, aSchedule);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveScheduleAt(TimeSlot aSchedule, int index)
  {
    boolean wasAdded = false;
    if(schedule.contains(aSchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSchedule()) { index = numberOfSchedule() - 1; }
      schedule.remove(aSchedule);
      schedule.add(index, aSchedule);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addScheduleAt(aSchedule, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=days.size(); i > 0; i--)
    {
      Day aDay = days.get(i - 1);
      aDay.delete();
    }
    ArrayList<TimeSlot> copyOfSchedule = new ArrayList<TimeSlot>(schedule);
    schedule.clear();
    for(TimeSlot aSchedule : copyOfSchedule)
    {
      aSchedule.removeCalendar(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}