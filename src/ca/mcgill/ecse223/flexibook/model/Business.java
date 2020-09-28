/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.sql.Time;
import java.util.*;

// line 47 "../../../../../DomainModel.ump"
// line 132 "../../../../../DomainModel.ump"
public class Business
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Business Attributes
  private String name;
  private String address;
  private String phoneNumber;
  private String emailAddress;

  //Business Associations
  private DailyHour timeTable;
  private OwnerAccount owner;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Business(String aName, String aAddress, String aPhoneNumber, String aEmailAddress, DailyHour aTimeTable, OwnerAccount aOwner)
  {
    name = aName;
    address = aAddress;
    phoneNumber = aPhoneNumber;
    emailAddress = aEmailAddress;
    if (aTimeTable == null || aTimeTable.getBusiness() != null)
    {
      throw new RuntimeException("Unable to create Business due to aTimeTable. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    timeTable = aTimeTable;
    if (aOwner == null || aOwner.getBusiness() != null)
    {
      throw new RuntimeException("Unable to create Business due to aOwner. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    owner = aOwner;
  }

  public Business(String aName, String aAddress, String aPhoneNumber, String aEmailAddress, Time aOpenTimeForTimeTable, Time aEndTimeForTimeTable, Time aLunchBreakStartForTimeTable, Time aLunchBreakEndForTimeTable, String aUsernameForOwner, String aPasswordForOwner)
  {
    name = aName;
    address = aAddress;
    phoneNumber = aPhoneNumber;
    emailAddress = aEmailAddress;
    timeTable = new DailyHour(aOpenTimeForTimeTable, aEndTimeForTimeTable, aLunchBreakStartForTimeTable, aLunchBreakEndForTimeTable, this);
    owner = new OwnerAccount(aUsernameForOwner, aPasswordForOwner, this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmailAddress(String aEmailAddress)
  {
    boolean wasSet = false;
    emailAddress = aEmailAddress;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getAddress()
  {
    return address;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public String getEmailAddress()
  {
    return emailAddress;
  }
  /* Code from template association_GetOne */
  public DailyHour getTimeTable()
  {
    return timeTable;
  }
  /* Code from template association_GetOne */
  public OwnerAccount getOwner()
  {
    return owner;
  }

  public void delete()
  {
    DailyHour existingTimeTable = timeTable;
    timeTable = null;
    if (existingTimeTable != null)
    {
      existingTimeTable.delete();
    }
    OwnerAccount existingOwner = owner;
    owner = null;
    if (existingOwner != null)
    {
      existingOwner.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "address" + ":" + getAddress()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "," +
            "emailAddress" + ":" + getEmailAddress()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "timeTable = "+(getTimeTable()!=null?Integer.toHexString(System.identityHashCode(getTimeTable())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "owner = "+(getOwner()!=null?Integer.toHexString(System.identityHashCode(getOwner())):"null");
  }
}