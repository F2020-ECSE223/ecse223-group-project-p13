/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.sql.Time;
import java.util.*;

// line 62 "../../../../../DomainModel.ump"
// line 145 "../../../../../DomainModel.ump"
public class DailyHour
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DailyHour Attributes
  private Time openTime;
  private Time endTime;
  private Time lunchBreakStart;
  private Time lunchBreakEnd;

  //DailyHour Associations
  private List<Day> day;
  private Business business;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DailyHour(Time aOpenTime, Time aEndTime, Time aLunchBreakStart, Time aLunchBreakEnd, Business aBusiness)
  {
    openTime = aOpenTime;
    endTime = aEndTime;
    lunchBreakStart = aLunchBreakStart;
    lunchBreakEnd = aLunchBreakEnd;
    day = new ArrayList<Day>();
    if (aBusiness == null || aBusiness.getTimeTable() != null)
    {
      throw new RuntimeException("Unable to create DailyHour due to aBusiness. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    business = aBusiness;
  }

  public DailyHour(Time aOpenTime, Time aEndTime, Time aLunchBreakStart, Time aLunchBreakEnd, String aNameForBusiness, String aAddressForBusiness, String aPhoneNumberForBusiness, String aEmailAddressForBusiness, OwnerAccount aOwnerForBusiness)
  {
    openTime = aOpenTime;
    endTime = aEndTime;
    lunchBreakStart = aLunchBreakStart;
    lunchBreakEnd = aLunchBreakEnd;
    day = new ArrayList<Day>();
    business = new Business(aNameForBusiness, aAddressForBusiness, aPhoneNumberForBusiness, aEmailAddressForBusiness, this, aOwnerForBusiness);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOpenTime(Time aOpenTime)
  {
    boolean wasSet = false;
    openTime = aOpenTime;
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

  public boolean setLunchBreakStart(Time aLunchBreakStart)
  {
    boolean wasSet = false;
    lunchBreakStart = aLunchBreakStart;
    wasSet = true;
    return wasSet;
  }

  public boolean setLunchBreakEnd(Time aLunchBreakEnd)
  {
    boolean wasSet = false;
    lunchBreakEnd = aLunchBreakEnd;
    wasSet = true;
    return wasSet;
  }

  public Time getOpenTime()
  {
    return openTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public Time getLunchBreakStart()
  {
    return lunchBreakStart;
  }

  public Time getLunchBreakEnd()
  {
    return lunchBreakEnd;
  }
  /* Code from template association_GetMany */
  public Day getDay(int index)
  {
    Day aDay = day.get(index);
    return aDay;
  }

  public List<Day> getDay()
  {
    List<Day> newDay = Collections.unmodifiableList(day);
    return newDay;
  }

  public int numberOfDay()
  {
    int number = day.size();
    return number;
  }

  public boolean hasDay()
  {
    boolean has = day.size() > 0;
    return has;
  }

  public int indexOfDay(Day aDay)
  {
    int index = day.indexOf(aDay);
    return index;
  }
  /* Code from template association_GetOne */
  public Business getBusiness()
  {
    return business;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDay()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Day addDay(boolean aIsClosed, Calendar aCalendar)
  {
    return new Day(aIsClosed, aCalendar, this);
  }

  public boolean addDay(Day aDay)
  {
    boolean wasAdded = false;
    if (day.contains(aDay)) { return false; }
    DailyHour existingAvailability = aDay.getAvailability();
    boolean isNewAvailability = existingAvailability != null && !this.equals(existingAvailability);
    if (isNewAvailability)
    {
      aDay.setAvailability(this);
    }
    else
    {
      day.add(aDay);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDay(Day aDay)
  {
    boolean wasRemoved = false;
    //Unable to remove aDay, as it must always have a availability
    if (!this.equals(aDay.getAvailability()))
    {
      day.remove(aDay);
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
      if(index > numberOfDay()) { index = numberOfDay() - 1; }
      day.remove(aDay);
      day.add(index, aDay);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDayAt(Day aDay, int index)
  {
    boolean wasAdded = false;
    if(day.contains(aDay))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDay()) { index = numberOfDay() - 1; }
      day.remove(aDay);
      day.add(index, aDay);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDayAt(aDay, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=day.size(); i > 0; i--)
    {
      Day aDay = day.get(i - 1);
      aDay.delete();
    }
    Business existingBusiness = business;
    business = null;
    if (existingBusiness != null)
    {
      existingBusiness.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "openTime" + "=" + (getOpenTime() != null ? !getOpenTime().equals(this)  ? getOpenTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "lunchBreakStart" + "=" + (getLunchBreakStart() != null ? !getLunchBreakStart().equals(this)  ? getLunchBreakStart().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "lunchBreakEnd" + "=" + (getLunchBreakEnd() != null ? !getLunchBreakEnd().equals(this)  ? getLunchBreakEnd().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "business = "+(getBusiness()!=null?Integer.toHexString(System.identityHashCode(getBusiness())):"null");
  }
}