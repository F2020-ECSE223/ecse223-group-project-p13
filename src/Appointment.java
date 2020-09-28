/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/


import java.util.*;
import java.sql.Time;

// line 12 "Untitled.ump"
// line 100 "Untitled.ump"
public class Appointment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Appointment Associations
  private TimeSlot timeSlot;
  private List<Service> currentService;
  private List<Service> Services;
  private CustomerAccount customerAccount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Appointment(TimeSlot aTimeSlot, CustomerAccount aCustomerAccount)
  {
    if (aTimeSlot == null || aTimeSlot.getAppointment() != null)
    {
      throw new RuntimeException("Unable to create Appointment due to aTimeSlot. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    timeSlot = aTimeSlot;
    currentService = new ArrayList<Service>();
    Services = new ArrayList<Service>();
    boolean didAddCustomerAccount = setCustomerAccount(aCustomerAccount);
    if (!didAddCustomerAccount)
    {
      throw new RuntimeException("Unable to create appointment due to customerAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Appointment(Time aStartTimeForTimeSlot, Time aEndTimeForTimeSlot, CustomerAccount aCustomerAccount)
  {
    timeSlot = new TimeSlot(aStartTimeForTimeSlot, aEndTimeForTimeSlot, this);
    currentService = new ArrayList<Service>();
    Services = new ArrayList<Service>();
    boolean didAddCustomerAccount = setCustomerAccount(aCustomerAccount);
    if (!didAddCustomerAccount)
    {
      throw new RuntimeException("Unable to create appointment due to customerAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public TimeSlot getTimeSlot()
  {
    return timeSlot;
  }
  /* Code from template association_GetMany */
  public Service getCurrentService(int index)
  {
    Service aCurrentService = currentService.get(index);
    return aCurrentService;
  }

  public List<Service> getCurrentService()
  {
    List<Service> newCurrentService = Collections.unmodifiableList(currentService);
    return newCurrentService;
  }

  public int numberOfCurrentService()
  {
    int number = currentService.size();
    return number;
  }

  public boolean hasCurrentService()
  {
    boolean has = currentService.size() > 0;
    return has;
  }

  public int indexOfCurrentService(Service aCurrentService)
  {
    int index = currentService.indexOf(aCurrentService);
    return index;
  }
  /* Code from template association_GetMany */
  public Service getService(int index)
  {
    Service aService = Services.get(index);
    return aService;
  }

  public List<Service> getServices()
  {
    List<Service> newServices = Collections.unmodifiableList(Services);
    return newServices;
  }

  public int numberOfServices()
  {
    int number = Services.size();
    return number;
  }

  public boolean hasServices()
  {
    boolean has = Services.size() > 0;
    return has;
  }

  public int indexOfService(Service aService)
  {
    int index = Services.indexOf(aService);
    return index;
  }
  /* Code from template association_GetOne */
  public CustomerAccount getCustomerAccount()
  {
    return customerAccount;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCurrentService()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addCurrentService(Service aCurrentService)
  {
    boolean wasAdded = false;
    if (currentService.contains(aCurrentService)) { return false; }
    currentService.add(aCurrentService);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCurrentService(Service aCurrentService)
  {
    boolean wasRemoved = false;
    if (currentService.contains(aCurrentService))
    {
      currentService.remove(aCurrentService);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCurrentServiceAt(Service aCurrentService, int index)
  {  
    boolean wasAdded = false;
    if(addCurrentService(aCurrentService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCurrentService()) { index = numberOfCurrentService() - 1; }
      currentService.remove(aCurrentService);
      currentService.add(index, aCurrentService);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCurrentServiceAt(Service aCurrentService, int index)
  {
    boolean wasAdded = false;
    if(currentService.contains(aCurrentService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCurrentService()) { index = numberOfCurrentService() - 1; }
      currentService.remove(aCurrentService);
      currentService.add(index, aCurrentService);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCurrentServiceAt(aCurrentService, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServices()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addService(Service aService)
  {
    boolean wasAdded = false;
    if (Services.contains(aService)) { return false; }
    Services.add(aService);
    if (aService.indexOfAppointment(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aService.addAppointment(this);
      if (!wasAdded)
      {
        Services.remove(aService);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeService(Service aService)
  {
    boolean wasRemoved = false;
    if (!Services.contains(aService))
    {
      return wasRemoved;
    }

    int oldIndex = Services.indexOf(aService);
    Services.remove(oldIndex);
    if (aService.indexOfAppointment(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aService.removeAppointment(this);
      if (!wasRemoved)
      {
        Services.add(oldIndex,aService);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addServiceAt(Service aService, int index)
  {  
    boolean wasAdded = false;
    if(addService(aService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServices()) { index = numberOfServices() - 1; }
      Services.remove(aService);
      Services.add(index, aService);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveServiceAt(Service aService, int index)
  {
    boolean wasAdded = false;
    if(Services.contains(aService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServices()) { index = numberOfServices() - 1; }
      Services.remove(aService);
      Services.add(index, aService);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addServiceAt(aService, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCustomerAccount(CustomerAccount aCustomerAccount)
  {
    boolean wasSet = false;
    if (aCustomerAccount == null)
    {
      return wasSet;
    }

    CustomerAccount existingCustomerAccount = customerAccount;
    customerAccount = aCustomerAccount;
    if (existingCustomerAccount != null && !existingCustomerAccount.equals(aCustomerAccount))
    {
      existingCustomerAccount.removeAppointment(this);
    }
    customerAccount.addAppointment(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    TimeSlot existingTimeSlot = timeSlot;
    timeSlot = null;
    if (existingTimeSlot != null)
    {
      existingTimeSlot.delete();
    }
    currentService.clear();
    ArrayList<Service> copyOfServices = new ArrayList<Service>(Services);
    Services.clear();
    for(Service aService : copyOfServices)
    {
      aService.removeAppointment(this);
    }
    CustomerAccount placeholderCustomerAccount = customerAccount;
    this.customerAccount = null;
    if(placeholderCustomerAccount != null)
    {
      placeholderCustomerAccount.removeAppointment(this);
    }
  }

}