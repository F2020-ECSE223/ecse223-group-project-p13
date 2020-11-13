/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.flexibook.controller;
import java.sql.Date;
import java.sql.Time;
import java.util.*;

// line 10 "../../../../../TransferObjects.ump"
public class TOAppointmentCalendarItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOAppointmentCalendarItem Attributes
  private String description;
  private Date date;
  private Time startTime;
  private Time endTime;
  private boolean available;
  private String username;
  private String mainService;
  private List<String> chosenItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOAppointmentCalendarItem(String aDescription, Date aDate, Time aStartTime, Time aEndTime, boolean aAvailable, String aUsername, String aMainService)
  {
    description = aDescription;
    date = aDate;
    startTime = aStartTime;
    endTime = aEndTime;
    available = aAvailable;
    username = aUsername;
    mainService = aMainService;
    chosenItems = new ArrayList<String>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

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

  public boolean setAvailable(boolean aAvailable)
  {
    boolean wasSet = false;
    available = aAvailable;
    wasSet = true;
    return wasSet;
  }

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setMainService(String aMainService)
  {
    boolean wasSet = false;
    mainService = aMainService;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetMany */
  public boolean addChosenItem(String aChosenItem)
  {
    boolean wasAdded = false;
    wasAdded = chosenItems.add(aChosenItem);
    return wasAdded;
  }

  public boolean removeChosenItem(String aChosenItem)
  {
    boolean wasRemoved = false;
    wasRemoved = chosenItems.remove(aChosenItem);
    return wasRemoved;
  }

  public String getDescription()
  {
    return description;
  }

  public Date getDate()
  {
    return date;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public boolean getAvailable()
  {
    return available;
  }

  public String getUsername()
  {
    return username;
  }

  public String getMainService()
  {
    return mainService;
  }
  /* Code from template attribute_GetMany */
  public String getChosenItem(int index)
  {
    String aChosenItem = chosenItems.get(index);
    return aChosenItem;
  }

  public String[] getChosenItems()
  {
    String[] newChosenItems = chosenItems.toArray(new String[chosenItems.size()]);
    return newChosenItems;
  }

  public int numberOfChosenItems()
  {
    int number = chosenItems.size();
    return number;
  }

  public boolean hasChosenItems()
  {
    boolean has = chosenItems.size() > 0;
    return has;
  }

  public int indexOfChosenItem(String aChosenItem)
  {
    int index = chosenItems.indexOf(aChosenItem);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "," +
            "available" + ":" + getAvailable()+ "," +
            "username" + ":" + getUsername()+ "," +
            "mainService" + ":" + getMainService()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null");
  }
}