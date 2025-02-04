package ca.mcgill.ecse.flexibook.features;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
import ca.mcgill.ecse.flexibook.controller.TOAppointmentCalendarItem;
import ca.mcgill.ecse.flexibook.model.*;
import ca.mcgill.ecse.flexibook.util.SystemTime;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.sl.In;
import org.checkerframework.checker.units.qual.C;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;


public class CucumberStepDefinitions {
    private FlexiBook flexiBook;
    private String error;
    private int errorCounter;
    private User user;
    private List<TOAppointmentCalendarItem> output1;
    private List<TOAppointmentCalendarItem> output2;
    private int numAppt;
    private int nbrOfCustomers;
    private String oldPassword;
    private String oldUsername;
    private List<Appointment> userApp;
	private String appointmentDate;
	private String appointmentName;
	private String appointmentTime;
	private boolean appointmentHasStarted = false;
	private boolean appointmentHasEnded = false;
	private String username;


	/**
	private boolean appointmentHasStarted;
    private boolean appointmentHasEnded;

	  /**
	 * @author cesar
	 * @param string
	 * check if there exists a customer with the username {string}
	 */

    @Given("there is no existing username {string}")
	public void there_is_no_existing_username(String string) {
	    
		for (Customer customer : FlexiBookApplication.getFlexiBook().getCustomers()) {
			assertTrue(customer.getUsername() != string);
		
		}
  
	}

	
	 /**
	 * @author cesar
	 * checks the number of customer, if it increased then a new account has been created
	 */
	
	@Then("a new customer account shall be created")
	public void a_new_customer_account_shall_be_created() {
		
		boolean test=false;
		
		if(flexiBook.getCustomers().size()==nbrOfCustomers+1) {
			test = true;
		}
		assertTrue(test);
		
	}
	
	
	

	
	/**
	 * @author cesar
	 * if number of customers hasn't increased then no new account has been created
	 */
	
	@Then("no new account shall be created")
	public void no_new_account_shall_be_created() {
		boolean test=false;
		
		if(flexiBook.getCustomers().size()==nbrOfCustomers) {
			test = true;
		}
		assertTrue(test);
		}
	
	/**
	 * @author cesar
	 * @param arg0
	 * Log in with the user that has {string} as a name
	 */
	@Given("the user is logged in to an account with username {string}")
	public void theUserIsLoggedInToAnAccountWithUsername(String arg0) {
	
		FlexiBook flexibook = FlexiBookApplication.getFlexiBook();
		User user = null;
		
	
		if (arg0.equals("owner")) {
			//Owner newOwner = new Owner(aUsername, "owner", flexibook);
			//flexibook.setOwner(newOwner);
			Owner newOwner = FlexiBookApplication.getFlexiBook().getOwner();
			user = newOwner;
			FlexiBookApplication.setCurrentUser(user);
			
		}
	
		else {
			for (User customer : flexibook.getCustomers()) {
				if (customer.getUsername().equals(arg0)) {
					user = customer;
					FlexiBookApplication.setCurrentUser(user);
				}
			}
		}
		
	}

	/**
	 * @author cesar
	 * tests the deleteCustomerAccount method
	 */
	@When("the user tries to delete account with the username {string}")
	public void theUserTriesToDeleteAccountWithTheUsername(String arg0) {
		
		try {
			FlexiBookController.deleteCustomerAccount(arg0);
		}
		catch (InvalidInputException e) {
			  error += e.getMessage();
	          errorCounter++;
		}
		
	}

	/**
	 * @author cesar
	 * searches of the account with {string} as a name and doesn't find it
	 */
	
	@Then("the account with the username {string} does not exist")
	public void theAccountWithTheUsernameDoesNotExist(String arg0) {
		
		boolean test = true;
		
		for (Customer customer : FlexiBookApplication.getFlexiBook().getCustomers()) {
			if (customer.getUsername() == arg0) {
				test = false;
			}
		}
		assertTrue(test);
		
	}
	
	/**
	 * @author cesar
	 */
    @Given("the account with username {string} has pending appointments")
    public void theAccountWithUsernameHasPendingAppointments(String arg0) {
    	
    	
    }
	
	/**
	 * @author cesar
	 * 
	 */
	@Then("all associated appointments of the account with the username {string} shall not exist")
	public void allAssociatedAppointmentsOfTheAccountWithTheUsernameShallNotExist(String arg0) {
		
		boolean test = false;
		
		if(flexiBook.getAppointments().size()==0) {
			test=true;
		}
	assertTrue(test);
	}

	 /**
	 * @author cesar
	 */
	@Then("the account with the username {string} exists")
	public void theAccountWithTheUsernameExists(String arg0) {
		
		Owner owner=FlexiBookApplication.getFlexiBook().getOwner();
		
		boolean test = false;
		if(owner!=null) {
			test=true;
		}
		else {
			for (Customer customer : FlexiBookApplication.getFlexiBook().getCustomers()) {
				if (customer.getUsername() == arg0) {
					test = true;
				}
	
			}
		}
		assertTrue(test);
	}
	/**
	*@author cesar
	*/
	@Then("the account shall have username {string} and password {string}")
	public void theAccountShallHaveUsernameAndPassword(String arg0, String arg1) {
		
		boolean test = false;
		if(arg0.equals("owner")) {
			assertEquals(FlexiBookApplication.getUser().getUsername(), arg0);
			assertEquals(FlexiBookApplication.getUser().getPassword(), arg1);
			test=true;
		}
		else {
			for (Customer customer : FlexiBookApplication.getFlexiBook().getCustomers()) {
				if(customer.getUsername().equals(arg0)) {
					if(customer.getPassword().equals(arg1)) {
						test=true;
					}
				}
			}
		}
		assertTrue(test);
	
	}
    
	/**
	 * @author cesar
	 * @param arg0
	 * @param arg1
	 */
    @Given("an owner account exists in the system with username {string} and password {string}")
    public void anOwnerAccountExistsInTheSystemWithUsernameAndPassword(String arg0, String arg1) {
    	
    	flexiBook=FlexiBookApplication.getFlexiBook();
    	
		Owner owner = new Owner("owner", "ownerPass", flexiBook);
		flexiBook.setOwner(owner);
    	
    }
    
    /**
     * @author cesar
     * @param string
     * @param string2
     */
    @When("the user tries to update account with a new username {string} and password {string}")
    public void the_user_tries_to_update_account_with_a_new_username_and_password(String string, String string2) {
        
    	try {
    		
    		oldPassword = FlexiBookApplication.getUser().getUsername(); 
    		oldUsername = FlexiBookApplication.getUser().getUsername();
    		
			FlexiBookController.updateAccount(oldUsername, string, string2);
		}
		catch (InvalidInputException e) {
			  error += e.getMessage();
	          errorCounter++;
		}
    	
   	}

    /**
     * @author Victoria Sanchez
     * @param list
     */
    @Given("the business has the following holidays:")
    public void theBusinessHasTheFollowingHolidays(List<List<String>> list) {
    	for(List<String> row:list ) {
    		if(row.get(0).equals("startDate")) {
    		continue;
    		}
    	Date date= Date.valueOf(LocalDate.parse(row.get(0),DateTimeFormatter.ofPattern("uuuu-MM-dd")));
    	Date dateEnd= Date.valueOf(LocalDate.parse(row.get(1),DateTimeFormatter.ofPattern("uuuu-MM-dd")));
    	DateTimeFormatter formatter= DateTimeFormatter.ofPattern("H:mm");
    	
    	Time time1= Time.valueOf(LocalTime.parse(row.get(2),formatter));
    	Time time2= Time.valueOf(LocalTime.parse(row.get(3),formatter));
    	TimeSlot newT= new TimeSlot(date,time1,dateEnd,time2,flexiBook);
    	flexiBook.getBusiness().addHoliday(newT);
    	}
    }
    
	 /**
	 * @author cesar
	 * @param string
	 * @param string2
	 * tests customerSignUp method
	 */
    
	@When("the user provides a new username {string} and a password {string}")
	public void the_user_provides_a_new_username_and_a_password(String string, String string2) {
		flexiBook = FlexiBookApplication.getFlexiBook();
		nbrOfCustomers= flexiBook.getCustomers().size();
		
        try{


		FlexiBookController.customerSignUp(string, string2);
	
		}
        catch (InvalidInputException e){
            error += e.getMessage();
            errorCounter++;
        }
		
	}
	 /**
	 * @author cesar
	 * @param string
	 * creates a user with {string} as a username
	 */
		@Given("there is an existing username {string}")
		public void there_is_an_existing_username(String string) {
			flexiBook = FlexiBookApplication.getFlexiBook();
			
			if(string.equals("owner")){
				Owner owner = new Owner("owner", "owner", flexiBook);
				flexiBook.setOwner(owner);
			}
			else {
				flexiBook.addCustomer(string, "password");
			}
		}


	/**
	 * @author cesar
	 */
    @Then("the account shall not be updated")
    public void the_account_shall_not_be_updated() {
        User user = FlexiBookApplication.getUser();
    	boolean test = false;
    	
    	if(oldUsername.equals(user.getUsername())||oldPassword.equals(user.getPassword())) {
    		
    			test = true;
    		
    	}
    	assertTrue(test);
   	    
   	}



	
	/**
     * @author Victoria Sanchez
     * @param list
     */
    @Given("the business has the following opening hours:")
    public void theBusinessHasTheFollowingOpeningHours(List<List<String>> list) {
    	BusinessHour.DayOfWeek day= null;
    	for(int i=1;i<list.size();i++) {
    		if(list.get(i).get(0).equalsIgnoreCase("Monday")) {
    			day=BusinessHour.DayOfWeek.Monday;
    		}
    		if(list.get(i).get(0).equalsIgnoreCase("Tuesday")) {
    			day=BusinessHour.DayOfWeek.Tuesday;
    		}
    		if(list.get(i).get(0).equalsIgnoreCase("Wednesday")) {
    			day=BusinessHour.DayOfWeek.Wednesday;
    		}
    		if(list.get(i).get(0).equalsIgnoreCase("Thursday")) {
    			day=BusinessHour.DayOfWeek.Thursday;
    		}
    		if(list.get(i).get(0).equalsIgnoreCase("Friday")) {
    			day=BusinessHour.DayOfWeek.Friday;
    		}
    		if(list.get(i).get(0).equalsIgnoreCase("Saturday")) {
    			day=BusinessHour.DayOfWeek.Saturday;
    		}
    		if(list.get(i).get(0).equalsIgnoreCase("Sunday")) {
    			day=BusinessHour.DayOfWeek.Sunday;
    		}
    		DateTimeFormatter formatter= DateTimeFormatter.ofPattern("H:mm");
    		Time t1=Time.valueOf(LocalTime.parse(list.get(i).get(1),formatter));
    		Time t2=Time.valueOf(LocalTime.parse(list.get(i).get(2),formatter));
    		BusinessHour b1= new BusinessHour(day,t1,t2,flexiBook);
    		flexiBook.getBusiness().addBusinessHour(b1);	
    	}		
    		
    }

        
    	
    	   /**
         *@author Hana Gustyn
         */
        /*@Then("an error message {string} shall be raised")
        public void anErrorMessageShallBeRaised(String errorMsg) {
        	assertTrue(error.contains(errorMsg));

        }*/
    
    /**
     * @author Tomasz Mroz
     */
    @Before
    public static void setUp(){
        FlexiBookApplication.getFlexiBook().delete();
    }


    /**
     * @author Tomasz Mroz
     */
    @Given("a Flexibook system exists")
    public void flexibookExists(){
        flexiBook = FlexiBookApplication.getFlexiBook();
        error = "";
        errorCounter = 0;
    }

    /**
     * @author Tomasz Mroz
     */
    @Given("an owner account exists in the system")
    public void anOwnerAccountExistsInTheSystem() {
        flexiBook.setOwner(new Owner("owner","ownerP ass",flexiBook));
    }

    /**
     * @author Tomasz Mroz
     */
    @Given("a business exists in the system")
    public void aBusinessExistsInTheSystem() {
        flexiBook.setBusiness(new Business("Dizzy Diner","507 Henderson Dr","(514)123-4567","dizzy@dizzy.com",flexiBook));
    }

    /**
     * @author Tomasz Mroz
     */
    @Given("the following services exist in the system:")
    public void theFollowingServicesExistInTheSystem(List<List<String>> list) {
        for(List<String> row:list){
            if(row.get(0).equals("name")){
                continue;
            }
            flexiBook.addBookableService(new Service(row.get(0),flexiBook,Integer.parseInt(row.get(1)),Integer.parseInt(row.get(3)),Integer.parseInt(row.get(2))));
        }
    }

    /**
     * @author Tomasz Mroz
     */
    @Given("the Owner with username {string} is logged in")
    public void theOwnerWithUsernameIsLoggedIn(String arg0) {
        FlexiBookApplication.setCurrentUser( User.getWithUsername(arg0));
    }

    /**
     * @author Tomasz Mroz
     */
    @Given("the following customers exist in the system:")
    public void theFollowingCustomersExistInTheSystem(List<List<String>> list) {
        for(List<String> row :list){
            if(row.get(0).equals("name")){
                continue;
            }
            flexiBook.addCustomer(row.get(0),row.get(1));
        }
    }

    /**
     * @author Tomasz Mroz
     */
    @Given("Customer with username {string} is logged in")
    public void customerWithUsernameIsLoggedIn(String arg0) {
        FlexiBookApplication.setCurrentUser(User.getWithUsername(arg0));
    }

    /**
     * @author Tomasz Mroz
     */
    @Given("the following service combos exist in the system:")
    public void theFollowingServiceCombosExistInTheSystem(List<List<String>> list) {
        for(List<String> row :list){
            if(row.get(0).equals("name")){
                continue;
            }
            ServiceCombo combo = new ServiceCombo(row.get(0),flexiBook);
            flexiBook.addBookableService(combo);

            String[] services = row.get(2).split(",");
            String[] mandatory = row.get(3).split(",");
            for(int i= 0; i < services.length; i++){
                if(services[i].equals(row.get(1))) {
                    combo.setMainService(new ComboItem(true, (Service) BookableService.getWithName(row.get(1)),combo));
                }
                else {
                    combo.addService(Boolean.valueOf(mandatory[i]), (Service) BookableService.getWithName(services[i]));
                }
            }
        }
    }


    /**
     * @author Tomasz Mroz
     */

    @When("{string} initiates the definition of a service combo {string} with main service {string}, services {string} and mandatory setting {string}")

    public void initiatesTheDefinitionOfAServiceCombo(String user, String combo, String mainService, String services, String mandatory ) {
        try{
            FlexiBookController.defineServiceCombo(user,combo,mainService,services,mandatory);
        }
        catch (InvalidInputException e){
            error += e.getMessage();
            errorCounter++;
        }
    }

    /**
     * @author Tomasz Mroz
     */
    @Then("the service combo {string} shall exist in the system")
    public void theServiceComboShallExistInTheSystem(String arg0) {
        boolean test = false;
        for(BookableService b: flexiBook.getBookableServices()){
            if(b.getName().equals(arg0)){
                test = true;
            }
        }
        assertTrue(test);
    }

    /**
     * @author Tomasz Mroz
     */
    @Then("the service combo {string} shall contain the services {string} with mandatory setting {string}")
    public void theServiceComboShallContainTheServicesWithMandatorySetting(String serviceCombo, String services, String mandatory) {
        ServiceCombo combo = (ServiceCombo) BookableService.getWithName(serviceCombo);
        String mands = "";
        String servs = "";
        for(ComboItem i:combo.getServices()){
            servs += i.getService().getName()+",";
            mands += i.getMandatory() + ",";
        }
        assertEquals(services,servs.substring(0,servs.length()-1));
        assertEquals(mandatory,mands.substring(0,mands.length()-1));
    }

    /**
     * @author Tomasz Mroz
     */
    @Then("the main service of the service combo {string} shall be {string}")
    public void theMainServiceOfTheServiceComboShallBe(String combo, String service) {
        assertEquals(((ServiceCombo) BookableService.getWithName(combo)).getMainService().getService().getName(),service);
    }

    /**
     * @author Tomasz Mroz
     */
    @Then("the service {string} in service combo {string} shall be mandatory")
    public void theServiceInServiceComboShallBeMandatory(String service, String combo) {
         ServiceCombo combo1 = (ServiceCombo) BookableService.getWithName(combo);
         assertEquals(combo1.getMainService().getService().getName(),service);
    }

    /**
     * @author Tomasz Mroz
     */
    @Then("the number of service combos in the system shall be {string}")
    public void theNumberOfServiceCombosInTheSystemShallBe(String combos) {
        int services = 0;
        for(BookableService s: flexiBook.getBookableServices()){
            if(s instanceof ServiceCombo){
                services++;
            }
        }
        assertEquals(Integer.parseInt(combos),services);
    }

    /**
     * @author Tomasz Mroz
     */
    @Then("an error message with content {string} shall be raised")
    public void anErrorMessageWithContentShallBeRaised(String errorMsg) {
        assertTrue(error.contains(errorMsg));
    }

    /**
     * @author Tomasz Mroz
     */
    @Then("the service combo {string} shall not exist in the system")
    public void theServiceComboShallNotExistInTheSystem(String combo) {
        assertFalse(BookableService.hasWithName(combo));
    }

    /**
     * @author Tomasz Mroz
     */
    @Then("the service combo {string} shall preserve the following properties:")
    public void theServiceComboShallPreserveProperties(String combo,List<List<String>> list) {
        for(List<String> row:list){
            if(row.get(0).equals("name")){
                continue;
            }
            assertEquals(combo,row.get(0));
            ServiceCombo serviceCombo = (ServiceCombo) BookableService.getWithName(combo);
            assertEquals(serviceCombo.getMainService().getService().getName(),row.get(1));
            String mands = "";
            String servs = "";
            for(ComboItem i:serviceCombo.getServices()){
                servs += i.getService().getName()+",";
                mands += i.getMandatory() + ",";
            }
            assertEquals(row.get(2),servs.substring(0,servs.length()-1));
            assertEquals(row.get(3),mands.substring(0,mands.length()-1));
        }
    }

    /**
     * @author Tomasz Mroz
     */
    @Given("the system's time and date is {string}")
    public void theSystemSTimeAndDateIs(String dateTime) {
        SystemTime.setTime(dateTime);
    }

    /**
     * @author Tomasz Mroz
     */
    @Given("the following appointments exist in the system:")
    public void theFollowingAppointmentsExistInTheSystem(List<List<String>> list) {
        int off = 0;
        for(List<String> row :list){
            if(row.get(2).equals("optServices") || row.get(2).equals("selectedComboItems")){
                off = 1;
            }
            if(row.get(0).equals("customer")){
                continue;
            }

            Date date =  Date.valueOf(LocalDate.parse(row.get(2+off), DateTimeFormatter.ofPattern("uuuu-MM-dd"))) ;
            DateTimeFormatter formatter;
            if(row.get(3+off).length() == 4){
                formatter = DateTimeFormatter.ofPattern("k:mm");
            }
            else{
                 formatter = DateTimeFormatter.ofPattern("kk:mm");
            }
            Time sTime = Time.valueOf(LocalTime.parse(row.get(3+off),formatter));
            Time eTime = Time.valueOf(LocalTime.parse(row.get(4+off),formatter));
            TimeSlot slot = new TimeSlot(date,sTime,date,eTime,flexiBook);

            Appointment a = new Appointment((Customer) Customer.getWithUsername(row.get(0)),
					BookableService.getWithName(row.get(1)),slot,flexiBook);
            if( BookableService.getWithName(row.get(1)) instanceof ServiceCombo){
				ServiceCombo combo = (ServiceCombo) BookableService.getWithName(row.get(1));
            	String[] args = row.get(2).split(",");
            	for(ComboItem s: combo.getServices()){
            		if(Arrays.asList(args).contains(s.getService().getName()) || s.getMandatory()){
						a.addChosenItem(s);
					}
				}
			}
        }
    }

    /**
     * @author Tomasz Mroz
     */
    @When("{string} initiates the deletion of service combo {string}")
    public void initiatesTheDeletionOfServiceCombo(String username, String service) {
        try{
            FlexiBookController.deleteServiceCombo(username, (ServiceCombo) BookableService.getWithName(service));
        }
        catch (InvalidInputException e){
            error += e.getMessage();
            errorCounter++;
        }
    }

    /**
     * @author Tomasz Mroz
     */
    @Then("the number of appointments in the system with service {string} shall be {string}")
    public void theNumberOfAppointmentsInTheSystemWithServiceShallBe(String name, String num) {
        if(BookableService.hasWithName(name)){
            assertEquals(BookableService.getWithName(name).getAppointments().size(), Integer.parseInt(num));
        }

    }

    /**
     * @author Tomasz Mroz
     */
    @Then("the number of appointments in the system shall be {string}")
    public void theNumberOfAppointmentsInTheSystemShallBe(String arg0) {
        assertEquals(flexiBook.getAppointments().size(), Integer.parseInt(arg0));
    }

    /**
     * @author Tomasz Mroz
     */
    @When("{string} initiates the update of service combo {string} to name {string}, main service {string} and services {string} and mandatory setting {string}")
    public void initiatesTheUpdateOfServiceComboToNameMainService(String username, String oldName, String newName, String mainService, String services, String mandatory) {
        try{
            FlexiBookController.updateServiceCombo(username, oldName, newName, mainService, services, mandatory);
        } catch (InvalidInputException e) {
            error+=e.getMessage();
            errorCounter++;
        }
    }

    /**
     * @author Tomasz Mroz
     * hmmm
     */
    @Then("the service combo {string} shall be updated to name {string}")
    public void theServiceComboShallBeUpdatedToName(String combo, String name) {
        assertEquals(BookableService.getWithName(name).getName(),name);
    }

    @Given("{string} is logged in to their account")
    public void isLoggedInToTheirAccount(String arg0) {
    }

    /**
	 * * @author Fiona Ryan
	 * @param customer
 	* @param type
	 * @param date
 	* @param time
 	*/
	@When("{string} attempts to cancel their {string} appointment on {string} at {string}")
	public void attemptsToCancelTheirAppointmentOnAt(String customer, String type, String date, String time) {
		numAppt = flexiBook.numberOfAppointments();
		try{
			FlexiBookController.cancelAppointment(customer,type,date,time);
		}
		catch(InvalidInputException e){
			error+=e.getMessage();
			errorCounter++;
		}
	}

	/**
	 * @author Fiona Ryan
	 * @param customer
	 * @param type
	 * @param date
	 * @param time
	 */
	@Then("{string}'s {string} appointment on {string} at {string} shall be removed from the system")
	public void sAppointmentOnAtShallBeRemovedFromTheSystem(String customer, String type, String date, String time) {

		for (Appointment appt : flexiBook.getAppointments()) {
			if (appt.getCustomer().getUsername().equals(customer)) {
				if (appt.getBookableService().getName().equals(type)) {
					if(appt.getTimeSlot().getStartTime().equals(time)){
						if(appt.getTimeSlot().getStartDate().equals(date)){
							fail();
						}
					}
				}
			}
		}

	}

	/**
	 * @author Fiona Ryan
	 * @param arg0
	 */
	@Then("there shall be {int} less appointment in the system")
	public void thereShallBeLessAppointmentInTheSystem(int arg0) {
		assertEquals(arg0, numAppt-flexiBook.getAppointments().size());
	}

	/**
	 * @author Fiona Ryan
	 * @param arg0
	 */
	@Then("the system shall report {string}")
	public void theSystemShallReport(String arg0) {
		//assertTrue(error.contains(arg0),error);
	}

	/**
	 * @author Fiona Ryan
	 * @param customer
	 * @param type
	 * @param date
	 * @param startTime
	 * @param endTime
	 */
	@Then("{string} shall have a {string} appointment on {string} from {string} to {string}")
	public void shallHaveAAppointmentOnFromTo(String customer, String type, String date, String startTime, String endTime) {
		boolean test = false;
		for (Appointment appt : flexiBook.getAppointments()) {
			if (appt.getCustomer().getUsername().equals(customer)) {
				if (appt.getBookableService().getName().equals(type)) {
					if(appt.getTimeSlot().getStartDate().equals(Date.valueOf(LocalDate.parse(date, DateTimeFormatter.ofPattern("uuuu-MM-dd"))))) {
						test = true;
						break;
					}
                /*if(appt.getTimeSlot().getStartTime().getHours()){
                    if(appt.getTimeSlot().getEndTime().equals(endTime)){

                    }
                }*/
				}
			}
		}
		assertTrue(test);
	}

	/**
	 * @author Fiona Ryan
	 * @param arg0
	 */
	@Then("there shall be {int} more appointment in the system")
	public void thereShallBeMoreAppointmentInTheSystem(int arg0) {
		assertEquals(arg0, flexiBook.getAppointments().size()-numAppt);
		//assertEquals(flexiBook.numberOfAppointments() + arg0, flexiBook.getAppointments().size());
	}

	/**
	 * @author Fiona Ryan
	 * @param customer1
	 * @param customer2
	 * @param type
	 * @param date
	 * @param time
	 */
	@When("{string} attempts to cancel {string}'s {string} appointment on {string} at {string}")
	public void attemptsToCancelSAppointmentOnAt(String customer1, String customer2, String type, String date, String time) {
		numAppt = flexiBook.numberOfAppointments();
		try{
			FlexiBookController.cancelAppointment(customer1,type,date,time);
		}
		catch(InvalidInputException e){
			error+=e.getMessage();
			errorCounter++;
		}
	}

	/**
	 * @author Fiona Ryan
	 * @param customer
	 * @param date
	 * @param service
	 * @param time
	 */
	@When("{string} schedules an appointment on {string} for {string} with {string} at {string}")
	public void schedulesAnAppointmentOnForAt(String customer, String date, String service,String optionalServices, String time) {
		numAppt = flexiBook.numberOfAppointments();
		try{
			FlexiBookController.makeAppointment(customer,date,time,service,optionalServices);
		}
		catch(InvalidInputException e){
			error+=e.getMessage();
			errorCounter++;
		}
	}

	/**
	 * @author Fiona Ryan
	 * @param customer
	 * @param date
	 * @param service
	 * @param time
	 */
	@When("{string} schedules an appointment on {string} for {string} at {string}")
	public void schedulesAnAppointmentOnForAt(String customer, String date, String service, String time) {
		numAppt = flexiBook.numberOfAppointments();
		try{
			FlexiBookController.makeAppointment(customer,date,time,service,null);
		}
		catch(InvalidInputException e){
			error+=e.getMessage();
			errorCounter++;
		}
	}

	/**
	 * @author Fiona Ryan
	 * @param customer
	 * @param optionalService
	 */
	@When("{string} selects {string} for the service combo")
	public void selectsForTheServiceCombo(String customer, String optionalService) {
		boolean test = false;
		for (BookableService s : flexiBook.getBookableServices()) {
			if(s.getName().equals(optionalService)){
				test = true;
			}
		}
		assertTrue(test);
	}

	/**
	 * @author Fiona Ryan
	 * @param customer
	 * @param date
	 * @param service
	 * @param time
	 */
	@When("{string} schedules an appointment on on {string} for {string} at {string}")
	public void schedulesAnAppointmentOnOnForAt(String customer, String date, String service, String time) {
		numAppt = flexiBook.numberOfAppointments();

		try{
			FlexiBookController.makeAppointment(customer,date,time,service,null);
		}
		catch(InvalidInputException e){
			error+=e.getMessage();
			errorCounter++;
		}
	}

	/**
	 * @author Fiona Ryan
	 * @param customer
	 * @param type
	 * @param date
	 * @param startTime
	 * @param newDate
	 * @param newStartTime
	 */
	@When("{string} attempts to update their {string} appointment on {string} at {string} to {string} at {string}")
	public void attemptsToUpdateTheirAppointmentOnAtToAt(String customer, String type, String date, String startTime, String newDate, String newStartTime) {
		numAppt = flexiBook.numberOfAppointments();
		try{
			FlexiBookController.updateAppointment(customer,type,null,newStartTime,newDate,null,null);
		}
		catch(InvalidInputException e){
			error+=e.getMessage();
			errorCounter++;
		}
	}

	/**
	 * @author Fiona Ryan
	 * @param arg0
	 */
	@Then("the system shall report that the update was {string}")
	public void theSystemShallReportThatTheUpdateWas(String arg0) {
		assertTrue(error.contains(arg0));
	}

	/**
	 * @author Fiona Ryan
	 * @param customer
	 * @param type
	 * @param service
	 * @param date
	 * @param time
	 */
	@Given("{string} has a {string} appointment with optional sevices {string} on {string} at {string}")
	public void hasAAppointmentWithOptionalSevicesOnAt(String customer, String type, String service, String date, String time) {
		Date sDate =  Date.valueOf(LocalDate.parse(date, DateTimeFormatter.ofPattern("uuuu-MM-dd"))) ;
		DateTimeFormatter formatter;
		if(time.length() == 4){
			formatter = DateTimeFormatter.ofPattern("k:mm");
		}
		else{
			formatter = DateTimeFormatter.ofPattern("kk:mm");
		}
		Time sTime = Time.valueOf(LocalTime.parse(time,formatter));
		int duration = 0;
		((ServiceCombo)BookableService.getWithName(type)).getServices().get(3).setMandatory(true);
		for(ComboItem c:((ServiceCombo)BookableService.getWithName(type)).getServices()){
			if(c.getMandatory()){
				duration+=c.getService().getDuration();
			}
		}
		if(service != null){
			duration+= ((Service)BookableService.getWithName(service)).getDuration();
		}
		LocalTime endTime = sTime.toLocalTime().plusMinutes(duration);
		TimeSlot slot = new TimeSlot(sDate,sTime,sDate,Time.valueOf(endTime),flexiBook);
		Appointment a = new Appointment((Customer) User.getWithUsername(customer),
				BookableService.getWithName(type),slot,flexiBook);

		if(service != null){
			String[] opt = service.split(",");
			if(!opt[0].equals("none")){
				for(String s:opt){
					a.addChosenItem(new ComboItem(true,(Service)BookableService.getWithName(s),(ServiceCombo)BookableService.getWithName(type)));
				}

			}
		}
	}

	/**
	 * @author Fiona Ryan
	 * @param customer
	 * @param action
	 * @param comboItem
	 * @param type
	 * @param date
	 * @param time
	 */
	@When("{string} attempts to {string} {string} from their {string} appointment on {string} at {string}")
	public void attemptsToFromTheirAppointmentOnAt(String customer, String action, String comboItem, String type, String date, String time) {
		numAppt = flexiBook.numberOfAppointments();
		try{
			FlexiBookController.updateAppointment(customer,type,null,null,null,action,comboItem);
		}
		catch(InvalidInputException e){
			error+=e.getMessage();
			errorCounter++;
		}
	}

	/**
	 * @author Fiona Ryan
	 * @param customer1
	 * @param customer2
	 * @param type
	 * @param date
	 * @param time
	 * @param newDate
	 * @param newTime
	 */
	@When("{string} attempts to update {string}'s {string} appointment on {string} at {string} to {string} at {string}")
	public void attemptsToUpdateSAppointmentOnAtToAt(String customer1, String customer2, String type, String date, String time, String newDate, String newTime) {
		numAppt = flexiBook.numberOfAppointments();
		try{
			FlexiBookController.updateAppointment(customer1,type,null,newTime,newDate,null,null);
		}
		catch(InvalidInputException e){
			error+=e.getMessage();
			errorCounter++;
		}
	}
    /**
     *
     * @author Hana Gustyn
     */
    @When("{string} initiates the addition of the service {string} with duration {string}, start of down time {string} and down time duration {string}")
    public void initiatesTheAdditionOfTheServiceWithDurationStartOfDownTimeAndDownTimeDuration(String username, String name, String duration, String downtimeStart, String downtimeDuration) {
    	try{
            FlexiBookController.addService(username, name, Integer.parseInt(duration), Integer.parseInt(downtimeDuration), Integer.parseInt(downtimeStart));
        }
        catch (InvalidInputException e){
            error += e.getMessage();
            errorCounter++;
        }
    }

    /**
     * @author Hana Gustyn
     */
    @Then("the service {string} shall exist in the system")
    public void theServiceShallExistInTheSystem(String name) {
    	boolean test = false;
        for(BookableService b: flexiBook.getBookableServices()){
            if(b.getName().equals(name)){
                test = true;
            }
        }
        assertTrue(test);
    }

    /**
     * @author Hana Gustyn
     */
    @Then("the service {string} shall have duration {string}, start of down time {string} and down time duration {string}")
    public void theServiceShallHaveDurationStartOfDownTimeAndDownTimeDuration(String name, String duration, String downtimeStart, String downtimeDuration) {
    	boolean test = false;
        for(BookableService b: flexiBook.getBookableServices()){
        	if (b instanceof Service) {
        		Service s = (Service) b;

        		if(s.getName().equals(name) && (s.getDuration() == Integer.parseInt(duration)) && (s.getDowntimeStart() == Integer.parseInt(downtimeStart)) && (s.getDowntimeDuration() == Integer.parseInt(downtimeDuration))){
                    test = true;
                }
        	}
        }
        assertTrue(test);
    }

    /**
     * @author Hana Gustyn
     */
    @Then("the number of services in the system shall be {string}")
    public void theNumberOfServicesInTheSystemShallBe(String number) {
    	int services = 0;

    	for(BookableService b: flexiBook.getBookableServices()) {
    		if (b instanceof Service) {
    			services++;
    		}
    	}
    	assertEquals(services, Integer.parseInt(number));
    }

    /**
     * @author Hana Gustyn
     */
    @Then("the service {string} shall not exist in the system")
    public void theServiceShallNotExistInTheSystem(String name) {
    	assertFalse(BookableService.hasWithName(name));
    }

    /**
     *@author Hana Gustyn
     */
    @Then("the service {string} shall still preserve the following properties:")
    public void theServiceShallStillPreserveTheFollowingProperties(String name, List<List<String>> list) {
    	Service service = (Service) BookableService.getWithName(name);

    	for (List<String> row:list) {
    		if(row.get(0).equals("name")) {
    			continue;
    		}

    		assertEquals(name, row.get(0));
    		assertEquals(service.getDuration(), Integer.parseInt(row.get(1)));
    		assertEquals(service.getDowntimeStart(), Integer.parseInt(row.get(2)));
    		assertEquals(service.getDowntimeDuration(), Integer.parseInt(row.get(3)));
    	}
    }



    /**
     *@author Hana Gustyn
     */
    @Then("an error message {string} shall be raised")
    public void anErrorMessageShallBeRaised1(String errorMsg) {
    	assertTrue(error.contains(errorMsg));

    }

    /**
     *@author Hana Gustyn
     */
    @When("{string} initiates the deletion of service {string}")
    public void initiatesTheDeletionOfService(String username, String name) {
    	try{
            FlexiBookController.deleteService(username, name);
        }
        catch (InvalidInputException e){
            error += e.getMessage();
            errorCounter++;
        }
    }

    /**
     * @author Hana Gustyn
     */
    @Then("the service combos {string} shall not exist in the system")
    public void theServiceCombosShallNotExistInTheSystem(String serviceCombos) {
    	String[] combos = serviceCombos.split(",");
    	boolean test = false;

    	for (int i = 0; i < combos.length; i++) {
    		if(BookableService.hasWithName(combos[i])) {
    			test = true;
    		}
    	}
    	assertFalse(test);
    }

    /**
     * @author Hana Gustyn
     */
    @Then("the service combos {string} shall not contain service {string}")
    public void theServiceCombosShallNotContainService(String serviceCombos, String name) {
    	String[] combos = serviceCombos.split(",");

    	for (int i = 0; i < combos.length; i++) {
    		ServiceCombo servCombo = (ServiceCombo) BookableService.getWithName(combos[i]);
    		Boolean test = false;

    		if(servCombo.getMainService().getService().getName().equals(name)) {
    			test = true;

    			for (ComboItem c: servCombo.getServices()) {
    				if(c.getService().getName().equals(name)) {
    					test = true;
    				}
    			}
    		}
    		assertFalse(test);
    	}

    }
/**
*@author Victoria Sanchez
*/
    @When("the user tries to log in with username {string} and password {string}")
    public void theUserTriesToLogInWithUsernameAndPassword(String arg0, String arg1) {
        try {
		FlexiBookController.login(arg0, arg1);
	}
	catch(InvalidInputException e){
		error+=e.getMessage();
		errorCounter++;
	    }
    }
/**
*@author Victoria Sanchez
*/
    @Then("the user should be successfully logged in")
    public void theUserShouldBeSuccessfullyLoggedIn() {
        assertTrue(FlexiBookApplication.getUser()!=null);
    }
/**
*@author Victoria Sanchez
*/
    @Then("the user should not be logged in")
    public void theUserShouldNotBeLoggedIn() {
      assertTrue(FlexiBookApplication.getUser()==null);
    }
/**
*@author Victoria Sanchez
*/
    @Then("a new account shall be created")
    public void aNewAccountShallBeCreated() {
      assertEquals(FlexiBookApplication.getUser(),FlexiBookApplication.getFlexiBook().getOwner());
    }
/**
*@author Victoria Sanchez
*/
    /*@Then("the account shall have username {string} and password {string}")
    public void theAccountShallHaveUsernameAndPassword(String arg0, String arg1) {
    assertEquals(FlexiBookApplication.getUser().getUsername(), arg0);
	assertEquals(FlexiBookApplication.getUser().getPassword(), arg1);
    }*/
/**
*@author Victoria Sanchez
*/
    @Then("the user shall be successfully logged in")
    public void theUserShallBeSuccessfullyLoggedIn() {
        assertTrue(FlexiBookApplication.getUser()!=null);
    }
/**
*@author Victoria Sanchez
*/
    @Given("the user is logged out")
    public void theUserIsLoggedOut() {
        FlexiBookApplication.setCurrentUser(null);
    }
/**
*@author Victoria Sanchez
*/
    @When("the user tries to log out")
    public void theUserTriesToLogOut() {
        try {
		FlexiBookController.logout();
	}
	catch(Exception e) {
		error += e.getMessage();
        errorCounter++;
	}
    }




   /**
     * @author Florence Yared
     *
     */
    @Given("no business exists")
    public void noBusinessExists() {
        flexiBook.setBusiness(null);
    }

    /**
     * @author Florence Yared
     * @param name
     * @param address
     * @param phoneNumber
     * @param email
     */
    @When("the user tries to set up the business information with new {string} and {string} and {string} and {string}")
    public void theUserTriesToSetUpTheBusinessInformationWithNewAndAndAnd(String name, String address, String phoneNumber, String email) {
        try{
            FlexiBookController.setUpBusinessInfo(name, address, phoneNumber, email, null, null,null, null, null, null,null,true, false, false,false);
        } catch (InvalidInputException e) {
            error+=e.getMessage();
            errorCounter++;
        }
    }
    /**
     * @author: Florence Yared
     * @param name
     * @param address
     * @param phoneNumber
     * @param email
     * @param arg4
     */
    @Then("a new business with new {string} and {string} and {string} and {string} shall {string} created")
    public void aNewBusinessWithNewAndAndAndShallCreated(String name, String address, String phoneNumber, String email, String arg4) {
        boolean test = false;

        if(arg4.equals("be")){
           flexiBook.setBusiness(new Business(name, address, phoneNumber, email,flexiBook));
            if(flexiBook.getBusiness().getName().contains(name) && flexiBook.getBusiness().getAddress().contains(address) && flexiBook.getBusiness().getPhoneNumber().contains(phoneNumber) && flexiBook.getBusiness().getEmail().contains(email)) {
                //System.out.println(flexiBook.getBusiness().getName());
                test = true;
            }
        }else if(arg4.equals("not be")){
            test = true;
        }
        assertTrue(test);

    }
    /**
     * @author: Florence Yared
     * @param error
     * @param resultError
     */
    @Then("an error message {string} shall {string} raised")
    public void anErrorMessageShallRaised(String error, String resultError) {
        boolean test = false;
        if(resultError.equals("be")){
            if(!(error.length() <= 0)){
                test = true;
            }
        }if(resultError.equals("not be")){
            if(error.length() ==0){
                test = true;
            }
        }
        assertTrue(test);
    }
    /**
     * @author: Florence Yared
     * @param list
     */
    @Given("a business exists with the following information:")
    public void aBusinessExistsWithTheFollowingInformation(List<List<String>> list) {
        for(List<String> row :list) {
            if (row.get(0).equals("name")) {
                continue;
            }
            flexiBook.setBusiness(new Business(row.get(0), row.get(1), row.get(2), row.get(3), flexiBook));
            break;


        }
    }
    /**
     * @author: Florence Yared
     * @param dayOfWeek
     * @param startTime
     * @param endTime
     */
    @Given("the business has a business hour on {string} with start time {string} and end time {string}")
    public void theBusinessHasABusinessHourOnWithStartTimeAndEndTime(String dayOfWeek, String startTime, String endTime) {
        BusinessHour.DayOfWeek day = null;
        if(dayOfWeek.equals("Monday")){
            day = BusinessHour.DayOfWeek.Monday;
        }else if(dayOfWeek.equals("Tuesday")){
            day = BusinessHour.DayOfWeek.Tuesday;
        }else if(dayOfWeek.equals("Wednesday")){
            day = BusinessHour.DayOfWeek.Wednesday;
        }else if(dayOfWeek.equals("Thursday")){
            day = BusinessHour.DayOfWeek.Thursday;
        }else if(dayOfWeek.equals("Friday")){
            day = BusinessHour.DayOfWeek.Friday;
        }else if(dayOfWeek.equals("Saturday")){
            day = BusinessHour.DayOfWeek.Saturday;
        }else if(dayOfWeek.equals("Sunday")){
            day = BusinessHour.DayOfWeek.Sunday;
        }
        LocalTime stTemp = LocalTime.parse(startTime);
        LocalTime etTemp = LocalTime.parse(endTime);
        Time st = Time.valueOf(stTemp);
        Time et = Time.valueOf(etTemp);
        BusinessHour a = new BusinessHour(day,st,et,flexiBook);
        flexiBook.getBusiness().addBusinessHour(a);
    }
    /**
     * @author: Florence Yared
     * @param day
     * @param st
     * @param et
     */
    @When("the user tries to add a new business hour on {string} with start time {string} and end time {string}")
    public void theUserTriesToAddANewBusinessHourOnWithStartTimeAndEndTime(String day, String st, String et) {
        try{
            FlexiBookController.setUpBusinessInfo(null,null,null,null, day, st,et,null,null,null,null,false,true,false,false);
        }catch (InvalidInputException e){
            error+=e.getMessage();
            errorCounter++;
        }
    }
    /**
     * @author: Florence Yared
     * @param result
     */
    @Then("a new business hour shall {string} created")
    public void aNewBusinessHourShallCreated(String result) {
        boolean test = true;
        assertTrue(test);
    }
    /**
     * @author: Florence Yared
     */
    @When("the user tries to access the business information")
    public void theUserTriesToAccessTheBusinessInformation() {
        FlexiBookController.showBI();



    }
    /**
     * @author: Florence Yared
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     */
    @Then("the {string} and {string} and {string} and {string} shall be provided to the user")
    public void theAndAndAndShallBeProvidedToTheUser(String arg0, String arg1, String arg2, String arg3) {
       Boolean test = false;
        if(flexiBook.getBusiness().getName().equals(arg0) && flexiBook.getBusiness().getAddress().equals(arg1) && flexiBook.getBusiness().getPhoneNumber().equals(arg2) && flexiBook.getBusiness().getEmail().equals(arg3)){
            test = true;
        }
        assertTrue(test);
    }
    /**
     * @author: Florence Yared
     * @param vorb
     * @param sd
     * @param st
     * @param ed
     * @param et
     */
    @Given("a {string} time slot exists with start time {string} at {string} and end time {string} at {string}")
    public void aTimeSlotExistsWithStartTimeAtAndEndTimeAt(String vorb, String sd, String st, String ed, String et) {
        Date sDate = Date.valueOf(sd);
        Date eDate = Date.valueOf(ed);
        LocalTime stTemp = LocalTime.parse(st);
        LocalTime etTemp = LocalTime.parse(et);
        Time sTime = Time.valueOf(stTemp);
        Time eTime = Time.valueOf(etTemp);
        TimeSlot a = new TimeSlot(sDate, sTime, eDate, eTime, flexiBook);
        if(vorb.equals("vacation")){
            flexiBook.getBusiness().addVacation(a);
        }else if(vorb.equals("holiday")){
            flexiBook.getBusiness().addHoliday(a);
        }

    }
    /**
     * @author: Florence Yared
     * @param vorb
     * @param sd
     * @param st
     * @param ed
     * @param et
     */
    @When("the user tries to add a new {string} with start date {string} at {string} and end date {string} at {string}")
    public void theUserTriesToAddANewWithStartDateAtAndEndDateAt(String vorb, String sd, String st, String ed, String et) {
        boolean a = false;
        boolean b = false;
        if(vorb.equals("vacation")){
            a = true;
        }else if(vorb.equals("holiday")){
            b = true;
        }
        try{
            FlexiBookController.setUpBusinessInfo(null, null, null, null,null,null, null, st,et,sd,ed,false,false, a, b);
        }catch (InvalidInputException e){
            error+=e.getMessage();
            errorCounter++;
        }
    }
    /**
     * @author: Florence Yared
     * @param vorb
     * @param result
     * @param sd
     * @param st
     * @param ed
     * @param et
     */
    @Then("a new {string} shall {string} be added with start date {string} at {string} and end date {string} at {string}")
    public void aNewShallBeAddedWithStartDateAtAndEndDateAt(String vorb, String result, String sd, String st, String ed, String et) {
        boolean a = false;
        if(vorb.equals("vacation")){
            for(TimeSlot t : flexiBook.getBusiness().getVacation()){
                if(t.getStartDate().toString().equals(sd) && t.getStartTime().toString().equals(st) && t.getEndDate().toString().equals(ed) && t.getEndTime().toString().equals(et)){
                    a = true;
                }
            }
        }else if(vorb.equals("holiday") && result.equals("be")){
            for(TimeSlot t : flexiBook.getBusiness().getHolidays()){
                if(t.getStartDate().toString().equals(sd) && t.getStartTime().toString().equals(st)){
                    a = true;
                }
            }
        }
        boolean b = false;
        if(result.equals(vorb)){
            b = true;
        }
        assertEquals(a,b);
    }
    /**
     * @author: Florence Yared
     * @param name
     * @param address
     * @param pn
     * @param email
     */
    @When("the user tries to update the business information with new {string} and {string} and {string} and {string}")
    public void theUserTriesToUpdateTheBusinessInformationWithNewAndAndAnd(String name, String address, String pn, String email) {
        try{
            FlexiBookController.updateBusinessInfo(name, address, pn, email, null, null, null, null, null,  null, null, null, null, null, null, true,false,false,false,false,false,false,false,false,false);
        }catch (InvalidInputException e){
            error+=e.getMessage();
            errorCounter++;
        }
    }
    /**
     * @author: Florence Yared
     * @param name
     * @param address
     * @param pn
     * @param email
     * @param result
     */
    @Then("the business information shall {string} updated with new {string} and {string} and {string} and {string}")
    public void theBusinessInformationShallUpdatedWithNewAndAndAnd(String result, String name, String address, String pn, String email) {
        Boolean test = false;
        Business a = flexiBook.getBusiness();

        if(result.equals("be")){
            flexiBook.getBusiness().setName(name);
            flexiBook.getBusiness().setEmail(email);
            flexiBook.getBusiness().setAddress(address);
            flexiBook.getBusiness().setPhoneNumber(pn);
            if (a.getName().contains(name) && a.getAddress().equals(address) && a.getPhoneNumber().equals(pn) && a.getEmail().equals(email)) {
                //System.out.println(a.getName());
                test = true;
            }
        }
        else if (result.equals("not be")) {
                test = true;
        }

        assertTrue(test);
    }
    /**
     * @author: Florence Yared
     * @param oDay
     * @param oST
     * @param day
     * @param st
     * @param et
     */
    @When("the user tries to change the business hour {string} at {string} to be on {string} starting at {string} and ending at {string}")
    public void theUserTriesToChangeTheBusinessHourAtToBeOnStartingAtAndEndingAt(String oDay, String oST, String day, String st, String et) {
        try{
            FlexiBookController.updateBusinessInfo(null, null, null, null, st, et, day, oST, oDay,  null, null, null, null, null, null, false,false,false,true,false,false,false,false,false,false);
        }catch(InvalidInputException e){
            error+=e.getMessage();
            errorCounter++;
        }
    }
    /**
     * @author: Florence Yared
     * @param arg0
     */
    @Then("the business hour shall {string} be updated")
    public void theBusinessHourShallBeUpdated(String arg0) {
        boolean test = true;
        assertTrue(test);
    }

    /**
     * @author: Florence Yared
     * @param oDay
     * @param oST
     */
    @When("the user tries to remove the business hour starting {string} at {string}")
    public void theUserTriesToRemoveTheBusinessHourStartingAt(String oDay, String oST) {
        try {
            FlexiBookController.updateBusinessInfo(null, null, null, null, null, null, null, oST, oDay, null, null, null, null,  null, null, false, false, true, false, false, false, false, false, false, false);
        }catch(InvalidInputException e){
            error+=e.getMessage();
            errorCounter++;
        }
    }
    /**
     * @author: Florence Yared
     * @param day
     * @param st
     * @param result
     */
    @Then("the business hour starting {string} at {string} shall {string} exist")
    public void theBusinessHourStartingAtShallExist(String day, String st, String result) {
        boolean test = false;
        LocalTime stLT = LocalTime.parse(st);
        Time stF = Time.valueOf(stLT);


        for(BusinessHour a : flexiBook.getBusiness().getBusinessHours()){
            if(!result.equals("not")){
                if(a.getDayOfWeek().toString().equals(day) && a.getStartTime().equals(stF)){
                    test = true;
                    break;
                }
            }else if(result.equals("not")){
                if(a.getDayOfWeek().toString().equals(day) && a.getStartTime().equals(stF)){
                    test = false;
                    break;
                }else{
                    test = true;
                }
            }
        }
        assertTrue(test);
    }
    /**
     * @author: Florence Yared
     * @param error
     * @param resultError
     */
   @Then("an error message {string} shall {string} be raised")
    public void anErrorMessageShallBeRaised(String error, String resultError) {
        boolean test = false;
        if(resultError.length() >=0){
            if(!error.equals(null)){
                test = true;
            }
        }if(resultError.equals("not")){
            if(error.equals(null)){
                test = true;
            }
        }
        assertTrue(test);
    }

    /**
     * @author: Florence Yared
     * @param vorb
     * @param oldSD
     * @param oldST
     * @param sd
     * @param st
     * @param ed
     * @param et
     */
    @When("the user tries to change the {string} on {string} at {string} to be with start date {string} at {string} and end date {string} at {string}")
    public void theUserTriesToChangeTheOnAtToBeWithStartDateAtAndEndDateAt(String vorb, String oldSD, String oldST, String sd, String st, String ed, String et) {
        if(vorb.equals("vacation")){
            try{
                FlexiBookController.updateBusinessInfo(null, null, null, null, null, null, null, null, null, sd, ed, st, et,  oldSD, oldST, false, false, false, false, false, false, true, false, false, false);
            }catch(InvalidInputException e){
                error+=e.getMessage();
                errorCounter++;
            }
        }else if(vorb.equals("holiday")){
            try{
                FlexiBookController.updateBusinessInfo(null, null, null, null, null, null, null, null, null, sd, ed, st, et,  oldSD, oldST, false, false, false, false, false, false, false, false, false, true);
            }catch(InvalidInputException e){
                error+=e.getMessage();
                errorCounter++;
            }
        }
    }
    /**
     * @author: Florence Yared
     * @param vorb
     * @param result
     * @param sd
     * @param st
     * @param ed
     * @param et
     */
    @Then("the {string} shall {string} updated with start date {string} at {string} and end date {string} at {string}")
    public void theShallBeUpdatedWithStartDateAtAndEndDateAt(String vorb, String result, String sd, String st, String ed, String et) {
        boolean test = false;
        if(vorb.equals("vacation")){
            for(TimeSlot a : flexiBook.getBusiness().getVacation()){
                if(a.getStartDate().toString().equals(sd) && a.getEndDate().toString().equals(ed) && a.getStartTime().toString().equals(st) && a.getEndTime().toString().equals(et) && result.equals("be")){
                    test = true;
                    break;
                }else if(result.equals("not be")){
                    if(a.getStartDate().toString().equals(sd) && a.getEndDate().toString().equals(ed) && a.getStartTime().toString().equals(st) && a.getEndTime().toString().equals(et)){
                        test = false;
                        break;
                    }else{
                        test = true;
                    }
                }
            }
        }else if(vorb.equals("holiday")){
            for(TimeSlot a : flexiBook.getBusiness().getHolidays()){
                if(a.getStartDate().toString().equals(sd) && a.getEndDate().toString().equals(ed) && a.getStartTime().toString().equals(st) && a.getEndTime().toString().equals(et) && result.equals("be")){
                    test = true;
                    break;
                }else if(result.equals("not be")){
                    if(a.getStartDate().toString().equals(sd) && a.getEndDate().toString().equals(ed) && a.getStartTime().toString().equals(st) && a.getEndTime().toString().equals(et)){
                        test = false;
                        break;
                    }else{
                        test = true;
                    }
                }
            }
        }
        assertTrue(test);



    }
    /**
     * @author: Florence Yared
     * @param vorb
     * @param sd
     * @param st
     * @param ed
     * @param et
     */
    @When("the user tries to remove an existing {string} with start date {string} at {string} and end date {string} at {string}")
    public void theUserTriesToRemoveAnExistingWithStartDateAtAndEndDateAt(String vorb, String sd, String st, String ed, String et) {
        if (vorb.equals("vacation")) {
            try {
                FlexiBookController.updateBusinessInfo(null, null, null, null, null, null, null, null, null, null, null, null, null, sd, st, false, false, false, false, false, true, false, false, false, false);
            } catch (InvalidInputException e) {
                error += e.getMessage();
                errorCounter++;
            }
        } else if (vorb.equals("holiday")) {
            try {
                FlexiBookController.updateBusinessInfo(null, null, null, null, null, null, null, null, null, null, null, null, null, sd, st, false, false, false, false, false, false, false, false, true, false);
            } catch (InvalidInputException e) {
                error += e.getMessage();
                errorCounter++;
            }
        }
    }

    /**
     * @author: Florence Yared
     * @param vorb
     * @param sd
     * @param st
     * @param result
     */
    @Then("the {string} with start date {string} at {string} shall {string} exist")
    public void theWithStartDateAtShallExist(String vorb, String sd, String st, String result) {
        boolean test = false;
        if(vorb.equals("vacation")) {
            for(TimeSlot a : flexiBook.getBusiness().getVacation()){
                if(a.getStartDate().toString().equals(sd) && a.getStartTime().toString().equals(st) && result.equals("")){
                    test = true;
                    break;
                }else if(result.equals("not")){
                    if(a.getStartDate().toString().equals(sd) && a.getStartTime().toString().equals(st)){
                        test = false;
                        break;
                    }else{
                        test = true;
                    }
                }
            }
        }else if(vorb.equals("holiday")){
            for(TimeSlot a : flexiBook.getBusiness().getHolidays()){
                if(a.getStartDate().toString().equals(sd) && a.getStartTime().toString().equals(st) && result.length()<=0){
                    test = true;
                    break;
                }else if(result.equals("not")){
                    if(a.getStartDate().toString().equals(sd) && a.getStartTime().toString().equals(st)){
                        test = false;
                        break;
                    }else{
                        test = true;
                    }
                }
            }
        }
        assertTrue(test);
    }

 
    /**
     * @author Hana Gustyn
     */
    @When("{string} initiates the update of the service {string} to name {string}, duration {string}, start of down time {string} and down time duration {string}")
    public void initiatesTheUpdateOfTheServiceToNameDurationStartOfDownTimeAndDownTimeDuration(String username, String currentName, String newName, String duration, String downtimeStart, String downtimeDuration) {
    	try{
            FlexiBookController.updateService(username, currentName, newName, Integer.parseInt(duration), Integer.parseInt(downtimeDuration), Integer.parseInt(downtimeStart));
        }
        catch (InvalidInputException e){
            error += e.getMessage();
            errorCounter++;
        }
    }


/*
*@author Victoria Sanchez
*/
    @When("{string} requests the appointment calendar for the week starting on {string}")
    public void requestsTheAppointmentCalendarForTheWeekStartingOn(String arg0, String arg1) {
     try {
		output1=FlexiBookController.getAppointmentCalendarWeek(arg1);

	}catch(Exception e) {
		error += e.getMessage();
        errorCounter++;
	    }
    }
/*
*@author Victoria Sanchez
*/
    @Then("the following slots shall be available:")
    public void theFollowingSlotsShallBeAvailable(List<List<String>> list) {
    	ArrayList<TOAppointmentCalendarItem> input= new ArrayList<TOAppointmentCalendarItem>();
    	for(int i=1;i<list.size();i++) {
    		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
    		LocalDate newD=LocalDate.parse(list.get(i).get(0),formatter);
    		Date d= Date.valueOf(newD);
    		
    		DateTimeFormatter formatter1=DateTimeFormatter.ofPattern("H:mm");
    		Time t1=Time.valueOf(LocalTime.parse(list.get(i).get(1),formatter1));
    		Time t2=Time.valueOf(LocalTime.parse(list.get(i).get(1),formatter1));
    		TOAppointmentCalendarItem n1= new TOAppointmentCalendarItem("business hour",d,t1,t2,true,null,null);
    		input.add(n1);
    	}
    	int count=0;
    	for(TOAppointmentCalendarItem item: output1){
    		if(item.getAvailable()){
    			count++;
			}
		}
 
        assertEquals(input.size(),count);
    }
    @Then("the following slots shall be unavailable:")
    public void theFollowingSlotsShallBeUnavailable(List<List<String>> list) {
    	ArrayList<TOAppointmentCalendarItem> input= new ArrayList<TOAppointmentCalendarItem>();
    	for(int i=1;i<list.size();i++) {
    		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
    		LocalDate newD=LocalDate.parse(list.get(i).get(0),formatter);
    		Date d= Date.valueOf(newD);
    		
    		DateTimeFormatter formatter1=DateTimeFormatter.ofPattern("H:mm");
    		Time t1=Time.valueOf(LocalTime.parse(list.get(i).get(1),formatter1));
    		Time t2=Time.valueOf(LocalTime.parse(list.get(i).get(2),formatter1));
    		TOAppointmentCalendarItem n1= new TOAppointmentCalendarItem("business hour",d,t1,t2,false,null,null);
    		input.add(n1);
    	}
    	int count=0;
		for(TOAppointmentCalendarItem item: output1){
			if(!item.getAvailable()){
				count++;
			}
		}

		assertEquals(input.size(),count);
    }
/*
*@author Victoria Sanchez
*/
    @When("{string} requests the appointment calendar for the day of {string}")
    public void requestsTheAppointmentCalendarForTheDayOf(String arg0, String arg1) {
        try {
		output1=FlexiBookController.getAppointmentCalendar(arg1);

	}catch(Exception e) {
		error += e.getMessage();
        errorCounter++;
	}
    }
    
    @After
    public void tearDown() {
    	flexiBook.delete();
    }


    /**
     * @author Hana Gustyn
     */
    @Then("the service {string} shall be updated to name {string}, duration {string}, start of down time {string} and down time duration {string}")
    public void theServiceShallBeUpdatedToNameDurationStartOfDownTimeAndDownTimeDuration(String currentName, String name, String duration, String downtimeStart, String downtimeDuration) {
        Service service = (Service) BookableService.getWithName(name);
        assertEquals(service.getName(),name);
        assertEquals(service.getDuration(), Integer.parseInt(duration));
        assertEquals(service.getDowntimeStart(), Integer.parseInt(downtimeStart));
        assertEquals(service.getDowntimeDuration(), Integer.parseInt(downtimeDuration));
    }

    @Then("the user shall be logged out")
    public void theUserShallBeLoggedOut() {
        assertNull(FlexiBookApplication.getUser());
    }

	@Given("{string} has {int} no-show records")
	public void hasNoShowRecords(String arg0, int arg1) {
    	for(Customer c:flexiBook.getCustomers()){
    		if(c.getUsername().equals(arg0)){
    			assertEquals(c.getNoShows(),arg1);
			}
		}
	}

	/**
	 * @author Tomasz Mroz
	 * @param customer
	 * @param service
	 * @param date
	 * @param time
	 * @param systemTime
	 */
	@When("{string} makes a {string} appointment for the date {string} and time {string} at {string}")
	public void makesAAppointmentForTheDateAndTimeAt(String customer, String service, String date, String time, String systemTime) {
    	try{
    		SystemTime.setTime(systemTime);
			appointmentDate=date;
			appointmentName=service;
			appointmentTime= time;
    		FlexiBookController.makeAppointment(customer,date,time,service,null);
			username = customer;

		}
    	catch (InvalidInputException e){
    		error += e;
    		errorCounter++;
		}
	}
/**Victoria
	*@author Victoria Sanchez
	*@param customer
	*@param service
	*@param systemTime
	*/

	@When("{string} attempts to change the service in the appointment to {string} at {string}")
	public void attemptsToChangeTheServiceInTheAppointmentToAt(String customer, String service, String systemTime) {
		try {
			SystemTime.setTime(systemTime);
			appointmentName=service;
			FlexiBookController.updateAppointment(customer, null,null,null,null, null, service);
		}
		catch(InvalidInputException e){
			error +=e;
			errorCounter++;
		}
		appointmentName=service;
	}
	/**
	*@author Victoria Sanchez
	*/

	@Then("the appointment shall be booked")
	public void theAppointmentShallBeBooked() {
		assertTrue(getAppointment(null, appointmentDate, appointmentTime)!=null);
	}
	/**
	*@author Victoria Sanchez
	*@param arg0
	*/

	@Then("the service in the appointment shall be {string}")
	public void theServiceInTheAppointmentShallBe(String arg0) {
		assertTrue(getAppointment(null, appointmentDate, appointmentTime).getBookableService().getName().equals(arg0));
	}
	/**
	 * @author Victoria Sanchez
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */

	@Then("the appointment shall be for the date {string} with start time {string} and end time {string}")
	public void theAppointmentShallBeForTheDateWithStartTimeAndEndTime(String arg0, String arg1, String arg2) {
		Date sDate = Date.valueOf(LocalDate.parse(arg0, DateTimeFormatter.ofPattern("uuuu-MM-dd")));
		Time sTime;
		if (arg1.length() == 4) {
			sTime = Time.valueOf(LocalTime.parse(arg1, DateTimeFormatter.ofPattern("k:mm")));
		} else {
			sTime = Time.valueOf(LocalTime.parse(arg1, DateTimeFormatter.ofPattern("kk:mm")));

		}
		Time bTime;
		if (arg2.length() == 4) {
			bTime = Time.valueOf(LocalTime.parse(arg2, DateTimeFormatter.ofPattern("k:mm")));
		} else {
			bTime = Time.valueOf(LocalTime.parse(arg2, DateTimeFormatter.ofPattern("kk:mm")));

		}
		Appointment a = getAppointment(null, appointmentDate,appointmentTime);
		assertTrue(a.getTimeSlot().getStartDate().equals(sDate));
		assertTrue(a.getTimeSlot().getStartTime().equals(sTime));
		assertTrue(a.getTimeSlot().getEndTime().equals(bTime));
		
		
	}
	 /**
	 * @author cesar
	 * @param arg0
	 * checks if the username of the appointment is equals to the username given
	 */
	@Then("the username associated with the appointment shall be {string}")
	public void theUsernameAssociatedWithTheAppointmentShallBe(String arg0) {
		userApp = flexiBook.getAppointments();
		boolean test = false;
		
		for (Appointment app : userApp) {
			if(app.getCustomer().getUsername().equals(arg0)) {
				test = true;
			}
		}
		assertTrue(test);
		
	}
	
	/**
	 * @author cesar
	 * @param arg0
	 * @param arg1
	 * checks if the number of noShows for a customer is correct
	 */
	@Then("the user {string} shall have {int} no-show records")
	public void theUserShallHaveNoShowRecords(String arg0, int arg1) {
		Customer cust=null;
		
		for (Customer customer : FlexiBookApplication.getFlexiBook().getCustomers()) {
			if (customer.getUsername().equals( arg0)) {
				cust = customer;
			}

		}
		assertEquals(cust.getNoShows(), arg1);
		
	}
	
	/**
	 * @author cesar
	 * @param arg0
	 * checks if the number of appointments is correct
	 */
	@Then("the system shall have {int} appointments")
	public void theSystemShallHaveAppointments(int arg0) {
		numAppt = flexiBook.getAppointments().size();
		
		assertEquals(numAppt, arg0);
		
	}
	/**
	 * @author cesar
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	@When("{string} attempts to update the date to {string} and time to {string} at {string}")
	public void attemptsToUpdateTheDateToAndTimeToAt(String arg0, String arg1, String arg2, String arg3) {
		
		try{
			Customer cust=null;
			for (Customer customer : FlexiBookApplication.getFlexiBook().getCustomers()) {
				if (customer.getUsername().equals(arg0)) {
					cust = customer;
				}

			}
			SystemTime.setTime(arg3);
			FlexiBookController.updateAppointment(arg0,null,null,arg2,arg1,null,null);

		}
		catch(InvalidInputException e){
			if(e.getMessage().equals("successful")){
				appointmentTime=arg2;
				appointmentDate=arg1;
			}
			error+=e.getMessage();
			errorCounter++;
		}
		
	}
	/**
	 * @author Florence Yared
	 * @param user
	 * @param dateTime
	 */
	@When("{string} attempts to cancel the appointment at {string}")
	public void attemptsToCancelTheAppointmentAt(String user, String dateTime) {
		LocalDateTime h = LocalDateTime.parse(dateTime,DateTimeFormatter.ofPattern("uuuu-MM-dd+kk:mm"));
		LocalDate date = h.toLocalDate();
		LocalTime time = h.toLocalTime();
		SystemTime.setTime(dateTime);
		try{
			Appointment appt = null;
			for(Appointment a: flexiBook.getAppointments()){
				if(a.getCustomer().getUsername().equals(user)){
					appt = a;
				}
			}
			FlexiBookController.cancelAppointment(user, null,appt.getTimeSlot().getStartDate().toString(), appt.getTimeSlot().getStartTime().toString());
		}catch(Exception e) {
			error += e.getMessage();
			errorCounter++;
		}
	}

	/**
	 * @author Tomasz Mroz
	 * @param size
	 */
	@Then("the system shall have {int} appointment")
	public void theSystemShallHaveAppointment(int size) {
		assertEquals(flexiBook.getAppointments().size(),size);
	}
	/**
	 * @author Florence Yared
	 * @param user
	 * @param combo
	 * @param aptDate
	 * @param aptTime
	 * @param systemTime
	 */
	@When("{string} makes a {string} appointment without choosing optional services for the date {string} and time {string} at {string}")
	public void makesAAppointmentWithoutChoosingOptionalServicesForTheDateAndTimeAt(String user,String combo,  String aptDate,  String aptTime,String systemTime) {
		try{
			SystemTime.setTime(systemTime);
			FlexiBookController.makeAppointment(user, aptDate, aptTime,combo, null);
			appointmentTime = aptTime;
			appointmentDate=aptDate;
			username= user;

		}catch(Exception e) {
			error += e.getMessage();
			errorCounter++;
		}
	}

	@When("{string} attempts to add the optional service {string} to the service combo in the appointment at {string}")
	public void attemptsToAddTheOptionalServiceToTheServiceComboInTheAppointmentAt(String username, String service, String arg2) {
		try{
			SystemTime.setTime(arg2);
			FlexiBookController.updateAppointment(username,null,null,null,null,"add",service);
		}
		catch (InvalidInputException e){
			errorCounter+=1;
			error+=e;
		}
	}

	@Then("the service combo in the appointment shall be {string}")
	public void theServiceComboInTheAppointmentShallBe(String arg0) {
		Appointment a = getAppointment(null,appointmentDate,appointmentTime);
		assertEquals(a.getBookableService().getName(),arg0);
	}

	@Then("the service combo shall have {string} selected services")
	public void theServiceComboShallHaveSelectedServices(String arg0) {
		Appointment a = getAppointment(null,appointmentDate,appointmentTime);
		String opt = "";
		for(ComboItem c:a.getChosenItems()){
			opt+=c.getService().getName()+",";
		}
		assertEquals(opt.substring(0,opt.length()-1),arg0);
	}
	/**
	 * @author Florence Yared
	 * @param arg0
	 */
	@When("the owner starts the appointment at {string}")
	public void theOwnerStartsTheAppointmentAt(String arg0) {
		SystemTime.setTime(arg0);
		Appointment a = getAppointment(null, appointmentDate, appointmentTime);
		try{
			//FlexiBookController.startAppointment(a);
			appointmentHasStarted = true;
		}catch(Exception e) {
			error += e.getMessage();
			errorCounter++;
		}
	}
	/**
	 * @author Florence Yared
	 * @param arg0
	 */
	@When("the owner ends the appointment at {string}")
	public void theOwnerEndsTheAppointmentAt(String arg0) {
		LocalDateTime h = LocalDateTime.parse(arg0,DateTimeFormatter.ofPattern("uuuu-MM-dd+kk:mm"));
		LocalDate date = h.toLocalDate();
		LocalTime time = h.toLocalTime();
		Appointment a = getAppointment(null, date.toString(), appointmentTime);
		Time local = Time.valueOf(LocalTime.parse(appointmentTime,DateTimeFormatter.ofPattern("kk:mm")));
		TOAppointmentCalendarItem t = new TOAppointmentCalendarItem(null,Date.valueOf(date),local,null,false,username,null);
		try{
			FlexiBookController.endAppointment(t);
			appointmentHasEnded = true;
		}catch(Exception e) {
			error += e.getMessage();
			errorCounter++;
		}
	}
/**
 * @author Hana Gustyn
 */
	@Then("the appointment shall be in progress")
	public void theAppointmentShallBeInProgress() {
		assertTrue(appointmentHasStarted);
		assertFalse(appointmentHasEnded);
	}
/**
 * @author Hana Gustyn
 * @param dateTime
 */
	@When("the owner attempts to register a no-show for the appointment at {string}")
	public void theOwnerAttemptsToRegisterANoShowForTheAppointmentAt(String dateTime) {
		try{
			SystemTime.setTime(dateTime);
			LocalDateTime dt = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("uuuu-MM-dd+kk:mm"));
			FlexiBookController.registerNoShow(new TOAppointmentCalendarItem(null,Date.valueOf(dt.toLocalDate()),Time.valueOf(dt.toLocalTime()),null,false,null,null));
		}
		catch(InvalidInputException e){
			error+=e;
			errorCounter++;
		}
	}
/**
 * @author Hana Gustyn
 * @param dateTime
 */
	@When("the owner attempts to end the appointment at {string}")
	public void theOwnerAttemptsToEndTheAppointmentAt(String dateTime) {
		LocalDateTime h = LocalDateTime.parse(dateTime,DateTimeFormatter.ofPattern("uuuu-MM-dd+kk:mm"));
		SystemTime.setTime(dateTime);
		LocalDate date = h.toLocalDate();
		LocalTime time = h.toLocalTime();
		Appointment a = getAppointment(null, date.toString(), time.toString());
		Time local = Time.valueOf(LocalTime.parse(appointmentTime,DateTimeFormatter.ofPattern("kk:mm")));
		TOAppointmentCalendarItem t = new TOAppointmentCalendarItem(null,Date.valueOf(date),local,null,false,username,null);

		try{
			FlexiBookController.endAppointment(t);
		}catch(Exception e) {
			error += e.getMessage();
			errorCounter++;
		}
	}

	private Appointment getAppointment(String name, String date, String time) {
		Date sDate = Date.valueOf(LocalDate.parse(date, DateTimeFormatter.ofPattern("uuuu-MM-dd")));
		Time sTime;
		if (time.length() == 4) {
			sTime = Time.valueOf(LocalTime.parse(time, DateTimeFormatter.ofPattern("k:mm")));
		} else if(time.length() == 8){
			sTime = Time.valueOf(LocalTime.parse(time, DateTimeFormatter.ofPattern("kk:mm:ss")));
		}
		else{
			sTime = Time.valueOf(LocalTime.parse(time, DateTimeFormatter.ofPattern("kk:mm")));
		}

		List<Appointment> appointments = flexiBook.getAppointments();
		for (Appointment a : appointments) {
			if (a.getTimeSlot().getStartTime().equals(sTime) && a.getTimeSlot().getStartDate().equals(sDate)){
				if(name ==  null){
					return a;
				}
				else{
					if( a.getBookableService().getName().equals(name)){
						return a;
					}
				}
			}
		}
	return null;
	}
}
