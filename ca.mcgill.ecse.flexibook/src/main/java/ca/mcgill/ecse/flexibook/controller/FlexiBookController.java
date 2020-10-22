package ca.mcgill.ecse.flexibook.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.time.LocalDateTime;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.model.Appointment;
import ca.mcgill.ecse.flexibook.model.BookableService;
import ca.mcgill.ecse.flexibook.model.Business;
import ca.mcgill.ecse.flexibook.model.BusinessHour;
import ca.mcgill.ecse.flexibook.model.ComboItem;
import ca.mcgill.ecse.flexibook.model.Customer;
import ca.mcgill.ecse.flexibook.model.FlexiBook;
import ca.mcgill.ecse.flexibook.model.Owner;
import ca.mcgill.ecse.flexibook.model.Service;
import ca.mcgill.ecse.flexibook.model.ServiceCombo;
import ca.mcgill.ecse.flexibook.model.TimeSlot;
import ca.mcgill.ecse.flexibook.model.User;
import ca.mcgill.ecse.flexibook.util.SystemTime;


public class FlexiBookController {

	/**
	 * @author cesar
	 * @param aUsername
	 * @param aPassword
	 * @throw InvalidInputException
	 */
	
	public static void customerSignUp(String aUsername, String aPassword/**, FlexiBook aFlexiBook**/) throws InvalidInputException {
		
		FlexiBook flexibook = 	FlexiBookApplication.getFlexiBook();
		//String message = null;
		
		try {
			
			if (findUser(aUsername)!=null) {
			//	message = "An account with this username already exists";
				throw new InvalidInputException ("An account with this username already exists");
			}
			
			if(aUsername == null || aPassword == null) {
				//message = "The username/password cannot be empty";
				throw new InvalidInputException ("The username/password cannot be empty");
			}
			if(findUser(aUsername)!=null && FlexiBookApplication.getUser() == findUser(aUsername)) {
				
				throw new InvalidInputException("You must log out of owner account before creating a new account");
			
			}
			
			flexibook.addCustomer(aUsername, aPassword);
			
			
		}
				
		catch (RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}	
		
	}
	
	/**
	 * @author cesar
	 * @param oldUsername
	 * @param newUsername
	 * @param newPassword
	 * @throw InvalidInputException
	 */
	
	public static void updateAccount(String oldUsername, String newUsername, String newPassword) throws InvalidInputException {
		
		try {
			
			if(newUsername == null || newPassword == null) {
				throw new InvalidInputException ("The username/password cannot be empty");
			}
			
			User user = findUser(oldUsername);
			//Owner owner = FlexiBookApplication.getFlexiBook().getOwner();
			
			if(user == null) {
				throw new InvalidInputException ("No account with this username can be found");
			}
			
			if (user.getUsername() == oldUsername) {
				
				user.setUsername(newUsername);
				user.setPassword(newPassword);
				
			}
		
		} 
				
		catch (RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}	 
		
	}
	
	
	/**
	 * @author cesar
	 * @param aUsername
	 */
	
	private static User findUser(String aUsername) {
		
		User user = null;
		
		if (FlexiBookApplication.getFlexiBook().getOwner()!=null) {
			if (FlexiBookApplication.getFlexiBook().getOwner().getUsername().equals(aUsername)) {
				user = (Owner) FlexiBookApplication.getFlexiBook().getOwner();
				return user;
			}
		}
		
		for (Customer customer : FlexiBookApplication.getFlexiBook().getCustomers()) {
			if(customer.getUsername() == aUsername) {
				user = customer;
				return user;
			}
		
		}
		return user;
	}
	
	/**
	 * @author cesar
	 * @param aUsername
	 * @throw InvalidInputException
	 */
	
	public static void deleteCustomerAccount(String aUsername) throws InvalidInputException {
		
		//Customer customer = FlexiBookApplication.getFlexiBook().getCustomer(index);
		User user = findUser(aUsername);
		Owner owner = FlexiBookApplication.getFlexiBook().getOwner();
		FlexiBook flexibook = 	FlexiBookApplication.getFlexiBook();
		
		
		try {
			
			if(user.equals(owner) ) {
				throw new InvalidInputException ("Cannot delete owner account");
				
			}
			
			if(user != null) {
				flexibook.getCustomers().remove(user);
				user.delete();
			}
		}
		
		catch (RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	
	public static void makeAppointment(){ }
	public static void cancelAppointment(User customer, Appointment appointment) throws InvalidInputException {
		FlexiBook flexibook = FlexiBookApplication.getFlexiBook();
		try{
			if(customer.equals(appointment.getCustomer())){
				throw new InvalidInputException("A customer can only cancel their own appointments");
			}
			if(customer.equals(flexibook.getOwner())){
				throw new InvalidInputException("An owner cannot cancel an appointment");
			}
			if(cleanDate(appointment.getTimeSlot().getStartDate()).compareTo(SystemTime.getDate()) >= 0){
				throw new InvalidInputException("Cannot cancel an appointment on the appointment date");
			}
			else{
				appointment.delete();
			}
		}
		catch (RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}
	}
	public static void updateAppointment(){}

	/**
	 * @author Tomasz Mroz
	 * @param name
	 * @param mainService
	 * @param servicesList
	 * @param isMandatory
	 * @throws InvalidInputException
	 */
	public static void defineServiceCombo(String username,String name, String mainService, String servicesList, String isMandatory) throws InvalidInputException {
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
		try{
			if(!(flexiBook.getOwner().getUsername().equals(username))){
				throw new InvalidInputException("You are not authorized to perform this operation");
			}
			if(!(BookableService.hasWithName(mainService))){
				throw new InvalidInputException("Service "+ mainService + " does not exist");
			}
			String[] services = servicesList.split(",");
			String[] mandatory = isMandatory.split(",");
			for(String s:services){
				if(!(BookableService.hasWithName(s))){
					throw new InvalidInputException("Service "+ s + " does not exist");
				}
			}
			//Valid service combo
			if(!(servicesList.contains(mainService))){
				throw new InvalidInputException("Main service must be included in the services");
			}


			if(services.length < 2){
				throw new InvalidInputException("A service Combo must contain at least 2 services");
			}
			for(int i = 0; i < services.length;i++){
				if(services[i].equals(mainService)){
					//System.out.println(Boolean.valueOf(mandatory[i]));
					if(!Boolean.valueOf(mandatory[i])){

						throw new InvalidInputException("Main service must be mandatory");
					}
				}
			}

			//Testing for unique service combos
			for(BookableService s: flexiBook.getBookableServices()){
				if(s instanceof ServiceCombo){
					if(s.getName().equals(name)){
						throw new InvalidInputException("Service combo "+ name+ " already exists");
					}
				}
			}

			ServiceCombo serv = new ServiceCombo(name,flexiBook);
			serv.setMainService(new ComboItem(true, (Service) BookableService.getWithName(mainService),serv));
			for(int i = 0;i < services.length; i++){
				serv.addService(Boolean.getBoolean( mandatory[i]), (Service) BookableService.getWithName(services[i]));
			}
			flexiBook.addBookableService(serv);
		}
		catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Tomasz Mroz
	 */
	public static void updateServiceCombo(String owner,String prevCombo, String newCombo,String mainService, String services,String mandatory) throws InvalidInputException {
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
		if(flexiBook.getOwner().getUsername().equals(owner)){
			throw new InvalidInputException("You are not authorized to perform this operation");
		}
		else{

		}

	}

	/**
	 * @author Tomasz Mroz
	 * @param combo
	 * @throws InvalidInputException
	 */
	public static void deleteServiceCombo(ServiceCombo combo) throws InvalidInputException{
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
		if(!(FlexiBookApplication.getUser() instanceof Owner)){
			throw new InvalidInputException("You are not authorized to perform this operation");
		}
		else {
			List<Appointment> appointments = flexiBook.getAppointments();
			for(Appointment a:appointments){
				if(a.getBookableService().equals(combo)){
					if(cleanDate(a.getTimeSlot().getStartDate()).compareTo(SystemTime.getDate()) >= 0){
						throw new InvalidInputException("Service combo "+ combo.getName()+" has future appointments");
					}
				}

			}
			combo.delete();
		}
	}

	/**
	 * @author Tomasz Mroz
	 * Turns an java.sql.date object to a java.time.localdatetime object set at midnight
	 */
	private static LocalDateTime cleanDate(Date date) {
		return LocalDate.parse(date.toString()).atStartOfDay();
	}
}
