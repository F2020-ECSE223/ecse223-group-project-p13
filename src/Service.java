/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/


import java.util.*;

// line 32 "Untitled.ump"
// line 123 "Untitled.ump"
public class Service
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Service Attributes
  private String name;
  private int duration;

  //Service Associations
  private List<Appointment> appointments;
  private Combo serviceCombo;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Service(String aName, int aDuration, Combo aServiceCombo)
  {
    name = aName;
    duration = aDuration;
    appointments = new ArrayList<Appointment>();
    boolean didAddServiceCombo = setServiceCombo(aServiceCombo);
    if (!didAddServiceCombo)
    {
      throw new RuntimeException("Unable to create service due to serviceCombo. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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

  public boolean setDuration(int aDuration)
  {
    boolean wasSet = false;
    duration = aDuration;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getDuration()
  {
    return duration;
  }
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
  /* Code from template association_GetOne */
  public Combo getServiceCombo()
  {
    return serviceCombo;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAppointments()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addAppointment(Appointment aAppointment)
  {
    boolean wasAdded = false;
    if (appointments.contains(aAppointment)) { return false; }
    if (appointments.contains(aAppointment)) { return false; }
    appointments.add(aAppointment);
    if (aAppointment.indexOfService(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aAppointment.addService(this);
      if (!wasAdded)
      {
        appointments.remove(aAppointment);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeAppointment(Appointment aAppointment)
  {
    boolean wasRemoved = false;
    if (!appointments.contains(aAppointment))
    {
      return wasRemoved;
    }

    int oldIndex = appointments.indexOf(aAppointment);
    appointments.remove(oldIndex);
    if (aAppointment.indexOfService(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aAppointment.removeService(this);
      if (!wasRemoved)
      {
        appointments.add(oldIndex,aAppointment);
      }
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
  /* Code from template association_SetOneToMany */
  public boolean setServiceCombo(Combo aServiceCombo)
  {
    boolean wasSet = false;
    if (aServiceCombo == null)
    {
      return wasSet;
    }

    Combo existingServiceCombo = serviceCombo;
    serviceCombo = aServiceCombo;
    if (existingServiceCombo != null && !existingServiceCombo.equals(aServiceCombo))
    {
      existingServiceCombo.removeService(this);
    }
    serviceCombo.addService(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ArrayList<Appointment> copyOfAppointments = new ArrayList<Appointment>(appointments);
    appointments.clear();
    for(Appointment aAppointment : copyOfAppointments)
    {
      aAppointment.removeService(this);
    }
    Combo placeholderServiceCombo = serviceCombo;
    this.serviceCombo = null;
    if(placeholderServiceCombo != null)
    {
      placeholderServiceCombo.removeService(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "duration" + ":" + getDuration()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "serviceCombo = "+(getServiceCombo()!=null?Integer.toHexString(System.identityHashCode(getServiceCombo())):"null");
  }
}