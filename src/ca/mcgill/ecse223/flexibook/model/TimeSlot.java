/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.sql.Time;
import java.util.*;

// line 21 "../../../../../DomainModel.ump"
// line 117 "../../../../../DomainModel.ump"
public class TimeSlot
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TimeSlot Attributes
  private Time startTime;
  private Time endTime;

  //TimeSlot Associations
  private Appointment appointment;
  private List<Calendar> calendar;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TimeSlot(Time aStartTime, Time aEndTime, Appointment aAppointment)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    if (aAppointment == null || aAppointment.getTimeSlot() != null)
    {
      throw new RuntimeException("Unable to create TimeSlot due to aAppointment. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    appointment = aAppointment;
    calendar = new ArrayList<Calendar>();
  }

  public TimeSlot(Time aStartTime, Time aEndTime, CustomerAccount aCustomerAccountForAppointment)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    appointment = new Appointment(this, aCustomerAccountForAppointment);
    calendar = new ArrayList<Calendar>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }
  /* Code from template association_GetOne */
  public Appointment getAppointment()
  {
    return appointment;
  }
  /* Code from template association_GetMany */
  public Calendar getCalendar(int index)
  {
    Calendar aCalendar = calendar.get(index);
    return aCalendar;
  }

  public List<Calendar> getCalendar()
  {
    List<Calendar> newCalendar = Collections.unmodifiableList(calendar);
    return newCalendar;
  }

  public int numberOfCalendar()
  {
    int number = calendar.size();
    return number;
  }

  public boolean hasCalendar()
  {
    boolean has = calendar.size() > 0;
    return has;
  }

  public int indexOfCalendar(Calendar aCalendar)
  {
    int index = calendar.indexOf(aCalendar);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCalendar()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addCalendar(Calendar aCalendar)
  {
    boolean wasAdded = false;
    if (calendar.contains(aCalendar)) { return false; }
    calendar.add(aCalendar);
    if (aCalendar.indexOfSchedule(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aCalendar.addSchedule(this);
      if (!wasAdded)
      {
        calendar.remove(aCalendar);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeCalendar(Calendar aCalendar)
  {
    boolean wasRemoved = false;
    if (!calendar.contains(aCalendar))
    {
      return wasRemoved;
    }

    int oldIndex = calendar.indexOf(aCalendar);
    calendar.remove(oldIndex);
    if (aCalendar.indexOfSchedule(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aCalendar.removeSchedule(this);
      if (!wasRemoved)
      {
        calendar.add(oldIndex,aCalendar);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCalendarAt(Calendar aCalendar, int index)
  {  
    boolean wasAdded = false;
    if(addCalendar(aCalendar))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCalendar()) { index = numberOfCalendar() - 1; }
      calendar.remove(aCalendar);
      calendar.add(index, aCalendar);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCalendarAt(Calendar aCalendar, int index)
  {
    boolean wasAdded = false;
    if(calendar.contains(aCalendar))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCalendar()) { index = numberOfCalendar() - 1; }
      calendar.remove(aCalendar);
      calendar.add(index, aCalendar);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCalendarAt(aCalendar, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Appointment existingAppointment = appointment;
    appointment = null;
    if (existingAppointment != null)
    {
      existingAppointment.delete();
    }
    ArrayList<Calendar> copyOfCalendar = new ArrayList<Calendar>(calendar);
    calendar.clear();
    for(Calendar aCalendar : copyOfCalendar)
    {
      aCalendar.removeSchedule(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "appointment = "+(getAppointment()!=null?Integer.toHexString(System.identityHashCode(getAppointment())):"null");
  }
}