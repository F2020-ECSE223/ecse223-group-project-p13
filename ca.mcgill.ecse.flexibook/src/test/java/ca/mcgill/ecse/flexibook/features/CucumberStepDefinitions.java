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

import static org.junit.jupiter.api.Assertions.*;


public class CucumberStepDefinitions {
    private FlexiBook flexiBook;
    private String error;
    private int errorCounter;
    	
   

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
