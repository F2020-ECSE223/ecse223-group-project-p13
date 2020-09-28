/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.util.*;

// line 9 "../../../../../DomainModel.ump"
// line 97 "../../../../../DomainModel.ump"
public class OwnerAccount extends Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //OwnerAccount Associations
  private Business business;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public OwnerAccount(String aUsername, String aPassword, Business aBusiness)
  {
    super(aUsername, aPassword);
    if (aBusiness == null || aBusiness.getOwner() != null)
    {
      throw new RuntimeException("Unable to create OwnerAccount due to aBusiness. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    business = aBusiness;
  }

  public OwnerAccount(String aUsername, String aPassword, String aNameForBusiness, String aAddressForBusiness, String aPhoneNumberForBusiness, String aEmailAddressForBusiness, DailyHour aTimeTableForBusiness)
  {
    super(aUsername, aPassword);
    business = new Business(aNameForBusiness, aAddressForBusiness, aPhoneNumberForBusiness, aEmailAddressForBusiness, aTimeTableForBusiness, this);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Business getBusiness()
  {
    return business;
  }

  public void delete()
  {
    Business existingBusiness = business;
    business = null;
    if (existingBusiness != null)
    {
      existingBusiness.delete();
    }
    super.delete();
  }

}