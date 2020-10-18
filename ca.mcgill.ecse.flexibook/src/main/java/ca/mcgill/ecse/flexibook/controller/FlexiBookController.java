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
	 */
	
	public static void customerSignUp(String aUsername, String aPassword/**, FlexiBook aFlexiBook**/) throws InvalidInputException {
		
		User user = FlexiBookApplication.getUser();
		
		try {
			
			user.setUsername(aUsername);
			user.setPassword(aPassword);
			
			
		}
				
		catch (RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}	
		
	}
	
	/**
	 * @author cesar
	 * @param oldUsername
	 * @param oldPassword
	 * @param newUsername
	 * @param newPassword
	 * @param index
	 */
	
	public static void updateAccount(String oldUsername, String oldPassword, int index, String newUsername, String newPassword) throws InvalidInputException {
		
		try {
			
			Customer customer = FlexiBookApplication.getFlexiBook().getCustomer(index);
			Owner owner = FlexiBookApplication.getFlexiBook().getOwner();
			
			if (customer.getUsername().equals(oldUsername)) {
				
				customer.setUsername(newUsername);
				customer.setPassword(newPassword);
				
			}
			
			else if (oldUsername.equals(owner.getUsername())) {
				
				owner.setUsername(newUsername);
				owner.setPassword(newPassword);
				
			}
			
			else {
				throw new InvalidInputException("No user found");
			}
		
		}
				
		catch (RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}	 
		
	}
	
	/**public static void updateOwnerAccount(String aUsername, String aPassword) throws InvalidInputException {
		
		try {
			
		Owner owner = FlexiBookApplication.getFlexiBook().getOwner();
		
		owner.setUsername(aUsername);
		owner.setPassword(aPassword);
		
		}
				
		catch (RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}	
		
	}**/
	
	/**
	 * @author cesar
	 * @param index
	 */
	
	public static void deleteCustomerAccount(int index) throws InvalidInputException {
		
		Customer customer = FlexiBookApplication.getFlexiBook().getCustomer(index);
		
		try {
			if(customer != null) {
				customer.delete();
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
