package ca.mcgill.ecse.flexibook.features;

import java.io.File;
import java.util.List;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
import ca.mcgill.ecse.flexibook.model.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;


public class CucumberStepDefinitions {
    private FlexiBook flexiBook;
    private String error;
    private int errorCounter;
    private User user;
    	
    
    	/**
    	 * @author cesar
    	 */

        @Given("there is no existing username {string}")
    	public void there_is_no_existing_username(String string) {
    	    
    		for (Customer customer : FlexiBookApplication.getFlexiBook().getCustomers()) {
    			assertTrue(customer.getUsername() != string);
    		
    		}
        	
        	
    	    //throw new io.cucumber.java.PendingException();
    	}
        
    	/**
    	 * @author cesar
    	 */
        
    	@When("the user provides a new username {string} and a password {string}")
    	public void the_user_provides_a_new_username_and_a_password(String string, String string2) {
    		
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
    	 */
    	
    	@Then("a new customer account shall be created")
    	public void a_new_customer_account_shall_be_created() {
    			
    	}
    	
    	/**
    	 * @author cesar
    	 */
    	
    	@Then("the account shall have username {string} and password {string}")
    	public void the_account_shall_have_username_and_password(String string, String string2) {
    		
    		boolean test = false;
    		
    		for (Customer customer : FlexiBookApplication.getFlexiBook().getCustomers()) {
    			if(customer.getUsername().equals(string)) {
    				if(customer.getPassword().equals(string2)) {
    					test=true;
    				}
    			}
    		}
    		assertTrue(test);
    	}

    	/**
    	 * @author cesar
    	 */
    	
    	/*@When("the user provides a new username {string} and a password {string}")
    	public void the_user_provides_a_new_username_and_a_password1(String string, String string2) {
   		
    			boolean test = false;
    		
                if(string == null || string2 == null) {
                	test = true;
                }
            
                assertTrue(test);

    		
    	}*/
    	
    	/**
    	 * @author cesar
    	 */
    	
    	@Then("no new account shall be created")
    	public void no_new_account_shall_be_created() {
   		    // Write code here that turns the phrase above into concrete actions
   		 //   throw new io.cucumber.java.PendingException();
   		}
    	
    	/**
    	 * @author cesar
    	 */
    	
    	@Then("an error message {string} shall be raised")
    	public void an_error_message_shall_be_raised(String string) {
   		   	
    		assertTrue(string, false);
    		
   		}


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
        flexiBook.setOwner(new Owner("owner","ownerPass",flexiBook));
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
            flexiBook.addBookableService(new Service(row.get(0),flexiBook,Integer.parseInt(row.get(1)),Integer.parseInt(row.get(2)),Integer.parseInt(row.get(3))));
        }
        /*flexiBook.addBookableService(new Service("wash",flexiBook,100,0,0));
        flexiBook.addBookableService(new Service("extensions",flexiBook,50,0,0));
        flexiBook.addBookableService(new Service("color",flexiBook,75,45,30));
        flexiBook.addBookableService(new Service("highlights",flexiBook,90,50,40));
        flexiBook.addBookableService(new Service("cut",flexiBook,20,0,0));
        flexiBook.addBookableService(new Service("dry",flexiBook,10,0,0));*/
    }

    /**
     * @author Tomasz Mroz
     */
    @Given("the Owner with username {string} is logged in")
    public void theOwnerWithUsernameIsLoggedIn(String arg0) {
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
            combo.setMainService(new ComboItem(true, (Service) BookableService.getWithName(row.get(1)),combo));
            String[] services = row.get(2).split(",");
            String[] mandatory = row.get(3).split(",");
            for(int i= 0; i < services.length; i++){
                combo.addService(Boolean.getBoolean(mandatory[i]) ,(Service) BookableService.getWithName(row.get(1)));
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
        }
    }

    /**
     * @author Tomasz Mroz
     */
    @Given("the system's time and date is {string}")
    public void theSystemSTimeAndDateIs(String arg0) {
    }

    /**
     * @author Tomasz Mroz
     */
    @Given("the following appointments exist in the system:")
    public void theFollowingAppointmentsExistInTheSystem() {
    }

    /**
     * @author Tomasz Mroz
     */
    @When("{string} initiates the deletion of service combo {string}")
    public void initiatesTheDeletionOfServiceCombo(String arg0, String arg1) {
    }

    /**
     * @author Tomasz Mroz
     */
    @Then("the number of appointments in the system with service {string} shall be {string}")
    public void theNumberOfAppointmentsInTheSystemWithServiceShallBe(String arg0, String arg1) {
    }

    /**
     * @author Tomasz Mroz
     */
    @Then("the number of appointments in the system shall be {string}")
    public void theNumberOfAppointmentsInTheSystemShallBe(String arg0) {
    }

    /**
     * @author Tomasz Mroz
     */
    @When("{string} initiates the update of service combo {string} to name {string}, main service {string} and services {string} and mandatory setting {string}")
    public void initiatesTheUpdateOfServiceComboToNameMainServiceAndServicesAndMandatorySetting(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
    }

    /**
     * @author Tomasz Mroz
     */
    @Then("the service combo {string} shall be updated to name {string}")
    public void theServiceComboShallBeUpdatedToName(String arg0, String arg1) {
    }
}
