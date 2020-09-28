/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.util.*;

// line 69 "../../../../../DomainModel.ump"
// line 164 "../../../../../DomainModel.ump"
public class CustomerAccount extends Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CustomerAccount Associations
  private List<Appointment> appointments;
  private List<Appointment> currentAppointment;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CustomerAccount(String aUsername, String aPassword)
  {
    super(aUsername, aPassword);
    appointments = new ArrayList<Appointment>();
    currentAppointment = new ArrayList<Appointment>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Appointment getAppointment(int index)
  {
    Appointment aAppointment = appointments.get(index);
    return aAppointment;
  }

  public List<Appointment> getAppointments()
  {
    List<Appointment> newAppointments = Collections.unmodifiableList(appointments);
    return newAppointments;
  }

  public int numberOfAppointments()
  {
    int number = appointments.size();
    return number;
  }

  public boolean hasAppointments()
  {
    boolean has = appointments.size() > 0;
    return has;
  }

  public int indexOfAppointment(Appointment aAppointment)
  {
    int index = appointments.indexOf(aAppointment);
    return index;
  }
  /* Code from template association_GetMany */
  public Appointment getCurrentAppointment(int index)
  {
    Appointment aCurrentAppointment = currentAppointment.get(index);
    return aCurrentAppointment;
  }

  public List<Appointment> getCurrentAppointment()
  {
    List<Appointment> newCurrentAppointment = Collections.unmodifiableList(currentAppointment);
    return newCurrentAppointment;
  }

  public int numberOfCurrentAppointment()
  {
    int number = currentAppointment.size();
    return number;
  }

  public boolean hasCurrentAppointment()
  {
    boolean has = currentAppointment.size() > 0;
    return has;
  }

  public int indexOfCurrentAppointment(Appointment aCurrentAppointment)
  {
    int index = currentAppointment.indexOf(aCurrentAppointment);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAppointments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Appointment addAppointment(TimeSlot aTimeSlot)
  {
    return new Appointment(aTimeSlot, this);
  }

  public boolean addAppointment(Appointment aAppointment)
  {
    boolean wasAdded = false;
    if (appointments.contains(aAppointment)) { return false; }
    CustomerAccount existingCustomerAccount = aAppointment.getCustomerAccount();
    boolean isNewCustomerAccount = existingCustomerAccount != null && !this.equals(existingCustomerAccount);
    if (isNewCustomerAccount)
    {
      aAppointment.setCustomerAccount(this);
    }
    else
    {
      appointments.add(aAppointment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAppointment(Appointment aAppointment)
  {
    boolean wasRemoved = false;
    //Unable to remove aAppointment, as it must always have a customerAccount
    if (!this.equals(aAppointment.getCustomerAccount()))
    {
      appointments.remove(aAppointment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAppointmentAt(Appointment aAppointment, int index)
  {  
    boolean wasAdded = false;
    if(addAppointment(aAppointment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppointments()) { index = numberOfAppointments() - 1; }
      appointments.remove(aAppointment);
      appointments.add(index, aAppointment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAppointmentAt(Appointment aAppointment, int index)
  {
    boolean wasAdded = false;
    if(appointments.contains(aAppointment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppointments()) { index = numberOfAppointments() - 1; }
      appointments.remove(aAppointment);
      appointments.add(index, aAppointment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAppointmentAt(aAppointment, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCurrentAppointment()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addCurrentAppointment(Appointment aCurrentAppointment)
  {
    boolean wasAdded = false;
    if (currentAppointment.contains(aCurrentAppointment)) { return false; }
    currentAppointment.add(aCurrentAppointment);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCurrentAppointment(Appointment aCurrentAppointment)
  {
    boolean wasRemoved = false;
    if (currentAppointment.contains(aCurrentAppointment))
    {
      currentAppointment.remove(aCurrentAppointment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCurrentAppointmentAt(Appointment aCurrentAppointment, int index)
  {  
    boolean wasAdded = false;
    if(addCurrentAppointment(aCurrentAppointment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCurrentAppointment()) { index = numberOfCurrentAppointment() - 1; }
      currentAppointment.remove(aCurrentAppointment);
      currentAppointment.add(index, aCurrentAppointment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCurrentAppointmentAt(Appointment aCurrentAppointment, int index)
  {
    boolean wasAdded = false;
    if(currentAppointment.contains(aCurrentAppointment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCurrentAppointment()) { index = numberOfCurrentAppointment() - 1; }
      currentAppointment.remove(aCurrentAppointment);
      currentAppointment.add(index, aCurrentAppointment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCurrentAppointmentAt(aCurrentAppointment, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (appointments.size() > 0)
    {
      Appointment aAppointment = appointments.get(appointments.size() - 1);
      aAppointment.delete();
      appointments.remove(aAppointment);
    }
    
    currentAppointment.clear();
    super.delete();
  }

}