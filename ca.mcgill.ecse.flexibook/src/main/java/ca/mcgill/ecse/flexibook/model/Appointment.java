/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.flexibook.model;
import java.io.Serializable;
import java.util.*;

// line 14 "../../../../../FlexiBookPersistence.ump"
// line 1 "../../../../../FlexiBookStates.ump"
// line 88 "../../../../../FlexiBook.ump"
public class Appointment implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Appointment Attributes
  private boolean isDayOf;

  //Appointment State Machines
  public enum ExistStatus { Before, Final, InProgress }
  private ExistStatus existStatus;

  //Appointment Associations
  private Customer customer;
  private BookableService bookableService;
  private List<ComboItem> chosenItems;
  private TimeSlot timeSlot;
  private FlexiBook flexiBook;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Appointment(Customer aCustomer, BookableService aBookableService, TimeSlot aTimeSlot, FlexiBook aFlexiBook)
  {
    isDayOf = false;
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create appointment due to customer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddBookableService = setBookableService(aBookableService);
    if (!didAddBookableService)
    {
      throw new RuntimeException("Unable to create appointment due to bookableService. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    chosenItems = new ArrayList<ComboItem>();
    if (!setTimeSlot(aTimeSlot))
    {
      throw new RuntimeException("Unable to create Appointment due to aTimeSlot. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddFlexiBook = setFlexiBook(aFlexiBook);
    if (!didAddFlexiBook)
    {
      throw new RuntimeException("Unable to create appointment due to flexiBook. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    setExistStatus(ExistStatus.Before);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsDayOf(boolean aIsDayOf)
  {
    boolean wasSet = false;
    isDayOf = aIsDayOf;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsDayOf()
  {
    return isDayOf;
  }

  public String getExistStatusFullName()
  {
    String answer = existStatus.toString();
    return answer;
  }

  public ExistStatus getExistStatus()
  {
    return existStatus;
  }

  public boolean toggleStart()
  {
    boolean wasEventProcessed = false;
    
    ExistStatus aExistStatus = existStatus;
    switch (aExistStatus)
    {
      case Before:
        if (getIsDayOf())
        {
          setExistStatus(ExistStatus.InProgress);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean toggleCancel()
  {
    boolean wasEventProcessed = false;
    
    ExistStatus aExistStatus = existStatus;
    switch (aExistStatus)
    {
      case Before:
        if (!(getIsDayOf()))
        {
          setExistStatus(ExistStatus.Final);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean updateDate(TimeSlot timeslot)
  {
    boolean wasEventProcessed = false;
    
    ExistStatus aExistStatus = existStatus;
    switch (aExistStatus)
    {
      case Before:
        // line 7 "../../../../../FlexiBookStates.ump"
        acceptDateUpdate(timeslot);
        setExistStatus(ExistStatus.Before);
        wasEventProcessed = true;
        break;
      case InProgress:
        // line 19 "../../../../../FlexiBookStates.ump"
        rejectDateUpdate(timeslot);
        setExistStatus(ExistStatus.InProgress);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean updateService(Service service)
  {
    boolean wasEventProcessed = false;
    
    ExistStatus aExistStatus = existStatus;
    switch (aExistStatus)
    {
      case Before:
        // line 10 "../../../../../FlexiBookStates.ump"
        acceptServiceUpdate(service);
        setExistStatus(ExistStatus.Before);
        wasEventProcessed = true;
        break;
      case InProgress:
        // line 22 "../../../../../FlexiBookStates.ump"
        rejectServiceUpdate(service);
        setExistStatus(ExistStatus.InProgress);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean updateNoShow(Customer c)
  {
    boolean wasEventProcessed = false;
    
    ExistStatus aExistStatus = existStatus;
    switch (aExistStatus)
    {
      case Before:
        // line 13 "../../../../../FlexiBookStates.ump"
        acceptNoShow(c);
        setExistStatus(ExistStatus.Final);
        wasEventProcessed = true;
        break;
      case InProgress:
        // line 25 "../../../../../FlexiBookStates.ump"
        rejectNoShow();
        setExistStatus(ExistStatus.InProgress);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean toggleEnded()
  {
    boolean wasEventProcessed = false;
    
    ExistStatus aExistStatus = existStatus;
    switch (aExistStatus)
    {
      case InProgress:
        setExistStatus(ExistStatus.Final);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setExistStatus(ExistStatus aExistStatus)
  {
    existStatus = aExistStatus;

    // entry actions and do activities
    switch(existStatus)
    {
      case Final:
        delete();
        break;
    }
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetOne */
  public BookableService getBookableService()
  {
    return bookableService;
  }
  /* Code from template association_GetMany */
  public ComboItem getChosenItem(int index)
  {
    ComboItem aChosenItem = chosenItems.get(index);
    return aChosenItem;
  }

  public List<ComboItem> getChosenItems()
  {
    List<ComboItem> newChosenItems = Collections.unmodifiableList(chosenItems);
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

  public int indexOfChosenItem(ComboItem aChosenItem)
  {
    int index = chosenItems.indexOf(aChosenItem);
    return index;
  }
  /* Code from template association_GetOne */
  public TimeSlot getTimeSlot()
  {
    return timeSlot;
  }
  /* Code from template association_GetOne */
  public FlexiBook getFlexiBook()
  {
    return flexiBook;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCustomer(Customer aCustomer)
  {
    boolean wasSet = false;
    if (aCustomer == null)
    {
      return wasSet;
    }

    Customer existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      existingCustomer.removeAppointment(this);
    }
    customer.addAppointment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBookableService(BookableService aBookableService)
  {
    boolean wasSet = false;
    if (aBookableService == null)
    {
      return wasSet;
    }

    BookableService existingBookableService = bookableService;
    bookableService = aBookableService;
    if (existingBookableService != null && !existingBookableService.equals(aBookableService))
    {
      existingBookableService.removeAppointment(this);
    }
    bookableService.addAppointment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfChosenItems()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addChosenItem(ComboItem aChosenItem)
  {
    boolean wasAdded = false;
    if (chosenItems.contains(aChosenItem)) { return false; }
    chosenItems.add(aChosenItem);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeChosenItem(ComboItem aChosenItem)
  {
    boolean wasRemoved = false;
    if (chosenItems.contains(aChosenItem))
    {
      chosenItems.remove(aChosenItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addChosenItemAt(ComboItem aChosenItem, int index)
  {  
    boolean wasAdded = false;
    if(addChosenItem(aChosenItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfChosenItems()) { index = numberOfChosenItems() - 1; }
      chosenItems.remove(aChosenItem);
      chosenItems.add(index, aChosenItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveChosenItemAt(ComboItem aChosenItem, int index)
  {
    boolean wasAdded = false;
    if(chosenItems.contains(aChosenItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfChosenItems()) { index = numberOfChosenItems() - 1; }
      chosenItems.remove(aChosenItem);
      chosenItems.add(index, aChosenItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addChosenItemAt(aChosenItem, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setTimeSlot(TimeSlot aNewTimeSlot)
  {
    boolean wasSet = false;
    if (aNewTimeSlot != null)
    {
      timeSlot = aNewTimeSlot;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setFlexiBook(FlexiBook aFlexiBook)
  {
    boolean wasSet = false;
    if (aFlexiBook == null)
    {
      return wasSet;
    }

    FlexiBook existingFlexiBook = flexiBook;
    flexiBook = aFlexiBook;
    if (existingFlexiBook != null && !existingFlexiBook.equals(aFlexiBook))
    {
      existingFlexiBook.removeAppointment(this);
    }
    flexiBook.addAppointment(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeAppointment(this);
    }
    BookableService placeholderBookableService = bookableService;
    this.bookableService = null;
    if(placeholderBookableService != null)
    {
      placeholderBookableService.removeAppointment(this);
    }
    chosenItems.clear();
    timeSlot = null;
    FlexiBook placeholderFlexiBook = flexiBook;
    this.flexiBook = null;
    if(placeholderFlexiBook != null)
    {
      placeholderFlexiBook.removeAppointment(this);
    }
  }

  // line 32 "../../../../../FlexiBookStates.ump"
   private void rejectDateUpdate(TimeSlot timeslot){
    throw new RuntimeException("You cannot update the date if the appointment is in progress");
  }

  // line 36 "../../../../../FlexiBookStates.ump"
   private void rejectServiceUpdate(Service service){
    throw new RuntimeException("You cannot update a service if the appointment is in progress");
  }

  // line 40 "../../../../../FlexiBookStates.ump"
   private void acceptServiceUpdate(Service service){
    if(service != null){
            this.setBookableService(service);
        }
      else{
      	getFlexiBook().addBookableService(service);
      }
  }

  // line 48 "../../../../../FlexiBookStates.ump"
   private void acceptDateUpdate(TimeSlot timeslot){
    if(timeslot != null){
           this.setTimeSlot(timeslot);
       }
       else{
          	getFlexiBook().addTimeSlot(timeslot);
       }
  }

  // line 56 "../../../../../FlexiBookStates.ump"
   private void rejectNoShow(){
    throw new RuntimeException("You cannot register a no-show if the appointment is in progress");
  }

  // line 59 "../../../../../FlexiBookStates.ump"
   private void acceptNoShow(Customer c){
    int i = c.getNoShows();
     c.setNoShows(i + 1);
     this.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "isDayOf" + ":" + getIsDayOf()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "bookableService = "+(getBookableService()!=null?Integer.toHexString(System.identityHashCode(getBookableService())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "timeSlot = "+(getTimeSlot()!=null?Integer.toHexString(System.identityHashCode(getTimeSlot())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "flexiBook = "+(getFlexiBook()!=null?Integer.toHexString(System.identityHashCode(getFlexiBook())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 17 "../../../../../FlexiBookPersistence.ump"
  private static final long serialVersionUID = 2315072607928790501L ;

  
}
