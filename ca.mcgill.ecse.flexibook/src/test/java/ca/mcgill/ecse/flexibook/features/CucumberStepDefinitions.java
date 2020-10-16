package ca.mcgill.ecse.flexibook.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
public class CucumberStepDefinitions {
    // TODO you can add step implementations her
    @Given("a Flexibook system exists")
    public void flexibookExists(){
        FlexiBookApplication.getFlexiBook();
    }

    @Given("an owner account exists in the system")
    public void anOwnerAccountExistsInTheSystem() {

    }

    @Given("a business exists in the system")
    public void aBusinessExistsInTheSystem() {
    }

    @Given("the following services exist in the system:")
    public void theFollowingServicesExistInTheSystem() {
    }

    @Given("the Owner with username {string} is logged in")
    public void theOwnerWithUsernameIsLoggedIn(String arg0) {
    }

    @When("{string} initiates the definition of a service combo {string} with main service {string}, services {string} and mandatory setting {string}")
    public void initiatesTheDefinitionOfAServiceComboWithMainServiceServicesAndMandatorySetting(String arg0, String arg1, String arg2, String arg3, String arg4) {
    }

    @Then("the service combo {string} shall exist in the system")
    public void theServiceComboShallExistInTheSystem(String arg0) {
    }

    @Then("the service combo {string} shall contain the services {string} with mandatory setting {string}")
    public void theServiceComboShallContainTheServicesWithMandatorySetting(String arg0, String arg1, String arg2) {
    }

    @Then("the main service of the service combo {string} shall be {string}")
    public void theMainServiceOfTheServiceComboShallBe(String arg0, String arg1) {
    }

    @Then("the service {string} in service combo {string} shall be mandatory")
    public void theServiceInServiceComboShallBeMandatory(String arg0, String arg1) {
    }

    @Then("the number of service combos in the system shall be {string}")
    public void theNumberOfServiceCombosInTheSystemShallBe(String arg0) {
    }

    @Given("the following service combos exist in the system:")
    public void theFollowingServiceCombosExistInTheSystem() {
    }

    @Then("an error message with content {string} shall be raised")
    public void anErrorMessageWithContentShallBeRaised(String arg0) {
    }

    @Then("the service combo {string} shall not exist in the system")
    public void theServiceComboShallNotExistInTheSystem(String arg0) {
    }

    @Then("the service combo {string} shall preserve the following properties:")
    public void theServiceComboShallPreserveTheFollowingProperties(String arg0) {
    }

    @Given("the following customers exist in the system:")
    public void theFollowingCustomersExistInTheSystem() {
    }

    @Given("Customer with username {string} is logged in")
    public void customerWithUsernameIsLoggedIn(String arg0) {
    }
}
