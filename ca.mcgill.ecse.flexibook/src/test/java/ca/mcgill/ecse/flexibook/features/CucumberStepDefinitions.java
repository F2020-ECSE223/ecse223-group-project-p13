package ca.mcgill.ecse.flexibook.features;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
import ca.mcgill.ecse.flexibook.model.*;
import ca.mcgill.ecse.flexibook.util.SystemTime;
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

            String[] services = row.get(2).split(",");
            String[] mandatory = row.get(3).split(",");
            for(int i= 0; i < services.length; i++){
                if(services[i].equals(row.get(1))) {
                    combo.setMainService(new ComboItem(true, (Service) BookableService.getWithName(row.get(1)),combo));
                }
                else {
                    combo.addService(Boolean.getBoolean(mandatory[i]), (Service) BookableService.getWithName(services[i]));
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
        for(List<String> row :list){
            if(row.get(0).equals("customer")){
                continue;
            }
            Date date =  Date.valueOf(LocalDate.parse(row.get(3), DateTimeFormatter.ofPattern("uuuu-MM-dd"))) ;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("kk:mm");
            Time sTime = Time.valueOf(LocalTime.parse(row.get(4),formatter));
            Time eTime = Time.valueOf(LocalTime.parse(row.get(5),formatter));
            TimeSlot slot = new TimeSlot(date,sTime,date,eTime,flexiBook);
            flexiBook.addAppointment(new Appointment((Customer) Customer.getWithUsername(row.get(0)),
                    BookableService.getWithName(row.get(1)),slot,flexiBook));
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

    @When("{string} attempts to cancel their {string} appointment on {string} at {string}")
    public void attemptsToCancelTheirAppointmentOnAt(String arg0, String arg1, String arg2, String arg3) {
    }

    @Then("{string}'s {string} appointment on {string} at {string} shall be removed from the system")
    public void sAppointmentOnAtShallBeRemovedFromTheSystem(String arg0, String arg1, String arg2, String arg3) {
    }

    @Then("there shall be {int} less appointment in the system")
    public void thereShallBeLessAppointmentInTheSystem(int arg0) {
    }

    @Then("the system shall report {string}")
    public void theSystemShallReport(String arg0) {
    }

    @Then("{string} shall have a {string} appointment on {string} from {string} to {string}")
    public void shallHaveAAppointmentOnFromTo(String arg0, String arg1, String arg2, String arg3, String arg4) {
    }

    @Then("there shall be {int} more appointment in the system")
    public void thereShallBeMoreAppointmentInTheSystem(int arg0) {
    }

    @When("{string} attempts to cancel {string}'s {string} appointment on {string} at {string}")
    public void attemptsToCancelSAppointmentOnAt(String arg0, String arg1, String arg2, String arg3, String arg4) {
    }

    @Given("the business has the following opening hours")
    public void theBusinessHasTheFollowingOpeningHours() {
    }

    @Given("the business has the following holidays")
    public void theBusinessHasTheFollowingHolidays() {
    }

    @When("{string} schedules an appointment on {string} for {string} at {string}")
    public void schedulesAnAppointmentOnForAt(String arg0, String arg1, String arg2, String arg3) {
    }

    @When("{string} selects {string} for the service combo")
    public void selectsForTheServiceCombo(String arg0, String arg1) {
    }

    @When("{string} schedules an appointment on on {string} for {string} at {string}")
    public void schedulesAnAppointmentOnOnForAt(String arg0, String arg1, String arg2, String arg3) {
    }

    @When("{string} attempts to update their {string} appointment on {string} at {string} to {string} at {string}")
    public void attemptsToUpdateTheirAppointmentOnAtToAt(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
    }

    @Then("the system shall report that the update was {string}")
    public void theSystemShallReportThatTheUpdateWas(String arg0) {
    }

    @Given("{string} has a {string} appointment with optional sevices {string} on {string} at {string}")
    public void hasAAppointmentWithOptionalSevicesOnAt(String arg0, String arg1, String arg2, String arg3, String arg4) {
    }

    @When("{string} attempts to {string} {string} from their {string} appointment on {string} at {string}")
    public void attemptsToFromTheirAppointmentOnAt(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
    }

    @When("{string} attempts to update {string}'s {string} appointment on {string} at {string} to {string} at {string}")
    public void attemptsToUpdateSAppointmentOnAtToAt(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
    }

    @When("{string} initiates the addition of the service {string} with duration {string}, start of down time {string} and down time duration {string}")
    public void initiatesTheAdditionOfTheServiceWithDurationStartOfDownTimeAndDownTimeDuration(String arg0, String arg1, String arg2, String arg3, String arg4) {
    }

    @Then("the service {string} shall exist in the system")
    public void theServiceShallExistInTheSystem(String arg0) {
    }

    @Then("the service {string} shall have duration {string}, start of down time {string} and down time duration {string}")
    public void theServiceShallHaveDurationStartOfDownTimeAndDownTimeDuration(String arg0, String arg1, String arg2, String arg3) {
    }

    @Then("the number of services in the system shall be {string}")
    public void theNumberOfServicesInTheSystemShallBe(String arg0) {
    }

    @Then("the service {string} shall not exist in the system")
    public void theServiceShallNotExistInTheSystem(String arg0) {
    }

    @Then("the service {string} shall still preserve the following properties:")
    public void theServiceShallStillPreserveTheFollowingProperties(String arg0) {
    }

    @Given("an owner account exists in the system with username {string} and password {string}")
    public void anOwnerAccountExistsInTheSystemWithUsernameAndPassword(String arg0, String arg1) {
    }

    @Given("the account with username {string} has pending appointments")
    public void theAccountWithUsernameHasPendingAppointments(String arg0) {
    }

    @Given("the user is logged in to an account with username {string}")
    public void theUserIsLoggedInToAnAccountWithUsername(String arg0) {
    }

    @When("the user tries to delete account with the username {string}")
    public void theUserTriesToDeleteAccountWithTheUsername(String arg0) {
    }

    @Then("the account with the username {string} does not exist")
    public void theAccountWithTheUsernameDoesNotExist(String arg0) {
    }

    @Then("all associated appointments of the account with the username {string} shall not exist")
    public void allAssociatedAppointmentsOfTheAccountWithTheUsernameShallNotExist(String arg0) {
    }

    @Then("the user shall be logged out")
    public void theUserShallBeLoggedOut() {
    }

    @Then("the account with the username {string} exists")
    public void theAccountWithTheUsernameExists(String arg0) {
    }

    @Then("an error message {string} shall be raised")
    public void anErrorMessageShallBeRaised(String arg0) {
    }

    @When("{string} initiates the deletion of service {string}")
    public void initiatesTheDeletionOfService(String arg0, String arg1) {
    }

    @Then("the service combos {string} shall not exist in the system")
    public void theServiceCombosShallNotExistInTheSystem(String arg0) {
    }

    @Then("the service combos {string} shall not contain service {string}")
    public void theServiceCombosShallNotContainService(String arg0, String arg1) {
    }

    @When("the user tries to log in with username {string} and password {string}")
    public void theUserTriesToLogInWithUsernameAndPassword(String arg0, String arg1) {
    }

    @Then("the user should be successfully logged in")
    public void theUserShouldBeSuccessfullyLoggedIn() {
    }

    @Then("the user should not be logged in")
    public void theUserShouldNotBeLoggedIn() {
    }

    @Then("a new account shall be created")
    public void aNewAccountShallBeCreated() {
    }

    @Then("the account shall have username {string} and password {string}")
    public void theAccountShallHaveUsernameAndPassword(String arg0, String arg1) {
    }

    @Then("the user shall be successfully logged in")
    public void theUserShallBeSuccessfullyLoggedIn() {
    }

    @Given("the user is logged out")
    public void theUserIsLoggedOut() {
    }

    @When("the user tries to log out")
    public void theUserTriesToLogOut() {
    }

    @Given("no business exists")
    public void noBusinessExists() {
    }

    @When("the user tries to set up the business information with new {string} and {string} and {string} and {string}")
    public void theUserTriesToSetUpTheBusinessInformationWithNewAndAndAnd(String arg0, String arg1, String arg2, String arg3) {
    }

    @Then("a new business with new {string} and {string} and {string} and {string} shall {string} created")
    public void aNewBusinessWithNewAndAndAndShallCreated(String arg0, String arg1, String arg2, String arg3, String arg4) {
    }

    @Then("an error message {string} shall {string} raised")
    public void anErrorMessageShallRaised(String arg0, String arg1) {
    }

    @Given("a business exists with the following information:")
    public void aBusinessExistsWithTheFollowingInformation() {
    }

    @Given("the business has a business hour on {string} with start time {string} and end time {string}")
    public void theBusinessHasABusinessHourOnWithStartTimeAndEndTime(String arg0, String arg1, String arg2) {
    }

    @When("the user tries to add a new business hour on {string} with start time {string} and end time {string}")
    public void theUserTriesToAddANewBusinessHourOnWithStartTimeAndEndTime(String arg0, String arg1, String arg2) {
    }

    @Then("a new business hour shall {string} created")
    public void aNewBusinessHourShallCreated(String arg0) {
    }

    @When("the user tries to access the business information")
    public void theUserTriesToAccessTheBusinessInformation() {
    }

    @Then("the {string} and {string} and {string} and {string} shall be provided to the user")
    public void theAndAndAndShallBeProvidedToTheUser(String arg0, String arg1, String arg2, String arg3) {
    }

    @Given("a {string} time slot exists with start time {string} at {string} and end time {string} at {string}")
    public void aTimeSlotExistsWithStartTimeAtAndEndTimeAt(String arg0, String arg1, String arg2, String arg3, String arg4) {
    }

    @When("the user tries to add a new {string} with start date {string} at {string} and end date {string} at {string}")
    public void theUserTriesToAddANewWithStartDateAtAndEndDateAt(String arg0, String arg1, String arg2, String arg3, String arg4) {
    }

    @Then("a new {string} shall {string} be added with start date {string} at {string} and end date {string} at {string}")
    public void aNewShallBeAddedWithStartDateAtAndEndDateAt(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
    }

    @Given("there is no existing username {string}")
    public void thereIsNoExistingUsername(String arg0) {
    }

    @When("the user provides a new username {string} and a password {string}")
    public void theUserProvidesANewUsernameAndAPassword(String arg0, String arg1) {
    }

    @Then("a new customer account shall be created")
    public void aNewCustomerAccountShallBeCreated() {
    }

    @Then("no new account shall be created")
    public void noNewAccountShallBeCreated() {
    }

    @Given("there is an existing username {string}")
    public void thereIsAnExistingUsername(String arg0) {
    }

    @When("the user tries to update account with a new username {string} and password {string}")
    public void theUserTriesToUpdateAccountWithANewUsernameAndPassword(String arg0, String arg1) {
    }

    @Then("the account shall not be updated")
    public void theAccountShallNotBeUpdated() {
    }

    @When("the user tries to update the business information with new {string} and {string} and {string} and {string}")
    public void theUserTriesToUpdateTheBusinessInformationWithNewAndAndAnd(String arg0, String arg1, String arg2, String arg3) {
    }

    @Then("the business information shall {string} updated with new {string} and {string} and {string} and {string}")
    public void theBusinessInformationShallUpdatedWithNewAndAndAnd(String arg0, String arg1, String arg2, String arg3, String arg4) {
    }

    @When("the user tries to change the business hour {string} at {string} to be on {string} starting at {string} and ending at {string}")
    public void theUserTriesToChangeTheBusinessHourAtToBeOnStartingAtAndEndingAt(String arg0, String arg1, String arg2, String arg3, String arg4) {
    }

    @Then("the business hour shall {string} be updated")
    public void theBusinessHourShallBeUpdated(String arg0) {
    }

    @When("the user tries to remove the business hour starting {string} at {string}")
    public void theUserTriesToRemoveTheBusinessHourStartingAt(String arg0, String arg1) {
    }

    @Then("the business hour starting {string} at {string} shall {string} exist")
    public void theBusinessHourStartingAtShallExist(String arg0, String arg1, String arg2) {
    }

    @Then("an error message {string} shall {string} be raised")
    public void anErrorMessageShallBeRaised(String arg0, String arg1) {
    }

    @When("the user tries to change the {string} on {string} at {string} to be with start date {string} at {string} and end date {string} at {string}")
    public void theUserTriesToChangeTheOnAtToBeWithStartDateAtAndEndDateAt(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
    }

    @Then("the {string} shall {string} be updated with start date {string} at {string} and end date {string} at {string}")
    public void theShallBeUpdatedWithStartDateAtAndEndDateAt(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
    }

    @When("the user tries to remove an existing {string} with start date {string} at {string} and end date {string} at {string}")
    public void theUserTriesToRemoveAnExistingWithStartDateAtAndEndDateAt(String arg0, String arg1, String arg2, String arg3, String arg4) {
    }

    @Then("the {string} with start date {string} at {string} shall {string} exist")
    public void theWithStartDateAtShallExist(String arg0, String arg1, String arg2, String arg3) {
    }

    @When("{string} initiates the update of the service {string} to name {string}, duration {string}, start of down time {string} and down time duration {string}")
    public void initiatesTheUpdateOfTheServiceToNameDurationStartOfDownTimeAndDownTimeDuration(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
    }

    @Then("the service {string} shall be updated to name {string}, duration {string}, start of down time {string} and down time duration {string}")
    public void theServiceShallBeUpdatedToNameDurationStartOfDownTimeAndDownTimeDuration(String arg0, String arg1, String arg2, String arg3, String arg4) {
    }


    @When("{string} requests the appointment calendar for the week starting on {string}")
    public void requestsTheAppointmentCalendarForTheWeekStartingOn(String arg0, String arg1) {
    }

    @Then("the following slots shall be unavailable:")
    public void theFollowingSlotsShallBeUnavailable() {
    }

    @Then("the following slots shall be available:")
    public void theFollowingSlotsShallBeAvailable() {
    }

    @When("{string} requests the appointment calendar for the day of {string}")
    public void requestsTheAppointmentCalendarForTheDayOf(String arg0, String arg1) {
    }
}
