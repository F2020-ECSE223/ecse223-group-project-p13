/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;

// line 56 "../../../../../DomainModel.ump"
// line 144 "../../../../../DomainModel.ump"
public class Day
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Day Attributes
  private boolean isClosed;

  //Day Associations
  private Calendar calendar;
  private DailyHour availability;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Day(boolean aIsClosed, Calendar aCalendar, DailyHour aAvailability)
  {
    isClosed = aIsClosed;
    boolean didAddCalendar = setCalendar(aCalendar);
    if (!didAddCalendar)
    {
      throw new RuntimeException("Unable to create day due to calendar. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddAvailability = setAvailability(aAvailability);
    if (!didAddAvailability)
    {
      throw new RuntimeException("Unable to create day due to availability. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsClosed(boolean aIsClosed)
  {
    boolean wasSet = false;
    isClosed = aIsClosed;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsClosed()
  {
    return isClosed;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsClosed()
  {
    return isClosed;
  }
  /* Code from template association_GetOne */
  public Calendar getCalendar()
  {
    return calendar;
  }
  /* Code from template association_GetOne */
  public DailyHour getAvailability()
  {
    return availability;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCalendar(Calendar aCalendar)
  {
    boolean wasSet = false;
    if (aCalendar == null)
    {
      return wasSet;
    }

    Calendar existingCalendar = calendar;
    calendar = aCalendar;
    if (existingCalendar != null && !existingCalendar.equals(aCalendar))
    {
      existingCalendar.removeDay(this);
    }
    calendar.addDay(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAvailability(DailyHour aAvailability)
  {
    boolean wasSet = false;
    if (aAvailability == null)
    {
      return wasSet;
    }

    DailyHour existingAvailability = availability;
    availability = aAvailability;
    if (existingAvailability != null && !existingAvailability.equals(aAvailability))
    {
      existingAvailability.removeDay(this);
    }
    availability.addDay(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Calendar placeholderCalendar = calendar;
    this.calendar = null;
    if(placeholderCalendar != null)
    {
      placeholderCalendar.removeDay(this);
    }
    DailyHour placeholderAvailability = availability;
    this.availability = null;
    if(placeholderAvailability != null)
    {
      placeholderAvailability.removeDay(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isClosed" + ":" + getIsClosed()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "calendar = "+(getCalendar()!=null?Integer.toHexString(System.identityHashCode(getCalendar())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "availability = "+(getAvailability()!=null?Integer.toHexString(System.identityHashCode(getAvailability())):"null");
  }
}