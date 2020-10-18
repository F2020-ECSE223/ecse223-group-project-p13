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
	 * @author Victoria Sanchez
	 * @param username
	 * @param password
	 * @throws InvalidInputException
	 */
	public static void login(String username, String password) throws InvalidInputException {
		FlexiBook flexibook =FlexiBookApplication.getFlexiBook();

		if(username.length()==0 || password.length()==0) {
			throw new InvalidInputException("invalid entry");
		}
		if(flexibook.getOwner()==null) {
			if(username.equals("owner")&& password.equals("owner")){
				//create account
				FlexiBookApplication.setCurrentUser(flexibook.getOwner());
			}
			
		}
		if(username==flexibook.getOwner().getUsername()) {
			if(password!=flexibook.getOwner().getPassword()) {
				throw new InvalidInputException("username/password not found");
			}
			else {
				FlexiBookApplication.setCurrentUser(flexibook.getOwner());
				
			}
		}else {
			boolean found=false;
			for(User user: flexibook.getCustomers()) {
			if(username==user.getUsername()) {
				found=true;
				if(password!=user.getPassword()) {
					throw new InvalidInputException("username/password not found");
				
				} else {
					FlexiBookApplication.setCurrentUser(user);
					}
				}
			if(!found) {
				throw new InvalidInputException("username/password not found");
		
				}
			} 
		}
		
	}
	/**
	 * @author Victoria Sanchez
	 * @return boolean
	 * @throws InvalidInputException
	 */
	public static void logout() throws Exception {
		if(FlexiBookApplication.getUser()==null) {
			throw new Exception("the user has already been logged out");
		} else {
			FlexiBookApplication.setCurrentUser(null);
		}
		
	}
	/**
	 * @author Victoria Sanchez
	 * @param date
	 * @param isWeek
	 * @return appointments 
	 */
	
	public static List<TOAppointment> getAppointmentCalendar(int day,int month, int year, Boolean isWeek) throws InvalidInputException {
		Date date = null;
		if(month>0&&month<=12) {
			switch (month) {
			case 1, 3, 5, 7,9,11:
				if(day>=0 && day<=31) {
					date= new Date(year,month,day);
				} else {
					throw new InvalidInputException("invalid day");
				}
				break;
			case 4,6,8,10:
				if(day>=0 && day<=30) {
					date= new Date(year,month,day);
				} else {
					throw new InvalidInputException("invalid day");
				}
				break;
			}
		} else {
			throw new InvalidInputException("invalid month");
		}
		FlexiBook flexibook= FlexiBookApplication.getFlexiBook();
		ArrayList<TOAppointment> appointments= new ArrayList<TOAppointment>();
		if(!isWeek) {
		for(Appointment appointment: flexibook.getAppointments()) {
				if(appointment.getTimeSlot().getStartDate().equals(date)) {
				TOAppointment toAppointment=  new TOAppointment(appointment.getTimeSlot().getStartDate(),appointment.getTimeSlot().getStartTime(),appointment.getTimeSlot().getEndTime());
				appointments.add(toAppointment);
				}	
			}
		
		} else {
			for(Appointment appointment: flexibook.getAppointments()) {
				if(appointment.getTimeSlot().getStartDate().compareTo(date)<=7) {
					TOAppointment toAppointment= new TOAppointment(appointment.getTimeSlot().getStartDate(),appointment.getTimeSlot().getStartTime(),appointment.getTimeSlot().getEndTime());
					appointments.add(toAppointment);
				}
			}
		}
		return appointments;
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
		try {
			if (!(flexiBook.getOwner().getUsername().equals(username))) {
				throw new InvalidInputException("You are not authorized to perform this operation");
			}
			if (!(BookableService.hasWithName(mainService))) {
				throw new InvalidInputException("Service " + mainService + " does not exist");
			}
			String[] services = servicesList.split(",");
			String[] mandatory = isMandatory.split(",");
			for (String s : services) {
				if (!(BookableService.hasWithName(s))) {
					throw new InvalidInputException("Service " + s + " does not exist");
				}
			}
			//Valid service combo
			if (!(servicesList.contains(mainService))) {
				throw new InvalidInputException("Main service must be included in the services");
			}
			if (services.length < 2) {
				throw new InvalidInputException("A service Combo must contain at least 2 services");
			}
			for (int i = 0; i < services.length; i++) {
				if (services[i].equals(mainService)) {
					if (!Boolean.valueOf(mandatory[i])) {
						throw new InvalidInputException("Main service must be mandatory");
					}
				}
			}
			//Testing for unique service combos
			for (BookableService s : flexiBook.getBookableServices()) {
				if (s instanceof ServiceCombo) {
					if (s.getName().equals(name)) {
						throw new InvalidInputException("Service combo " + name + " already exists");
					}
				}
			}
			ServiceCombo serv = new ServiceCombo(name, flexiBook);
			for (int i = 0; i < services.length; i++) {
				if (services[i].equals(mainService)) {
					serv.setMainService(new ComboItem(true, (Service) BookableService.getWithName(mainService), serv));
				} else {
					serv.addService(Boolean.valueOf(mandatory[i]), (Service) BookableService.getWithName(services[i]));
				}
			}
		}
		catch (RuntimeException e) {
			e.printStackTrace();
		}

	}
	/**
	 * @author Tomasz Mroz
	 */
	public static void updateServiceCombo(String username,String oldName,String name, String mainService, String servicesList, String isMandatory) throws InvalidInputException {
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();

		try {
			if (!(flexiBook.getOwner().getUsername().equals(username))) {
				throw new InvalidInputException("You are not authorized to perform this operation");
			}
			/*if(oldName.equals(name)){
				throw new InvalidInputException("Service combo Wash-Dry already exists");
			}*/

			if (!(BookableService.hasWithName(mainService))) {
				throw new InvalidInputException("Service " + mainService + " does not exist");
			}
			if (!(servicesList.contains(mainService))) {
				throw new InvalidInputException("Main service must be included in the services");
			}
			String[] services = servicesList.split(",");
			String[] mandatory = isMandatory.split(",");
			for (String s : services) {
				if (!(BookableService.hasWithName(s))) {
					throw new InvalidInputException("Service " + s + " does not exist");
				}
			}
			//Valid service combo

			if (services.length < 2) {
				throw new InvalidInputException("A service Combo must have at least 2 services");
			}
			for (int i = 0; i < services.length; i++) {
				if (services[i].equals(mainService)) {
					if (!Boolean.valueOf(mandatory[i])) {
						throw new InvalidInputException("Main service must be mandatory");
					}
				}
			}
			//Testing for unique service combos
			for (BookableService s : flexiBook.getBookableServices()) {
				if (s instanceof ServiceCombo) {
					if (s.getName().equals(name)) {
						throw new InvalidInputException("Service combo " + name + " already exists");
					}
				}
			}
			ServiceCombo combo = (ServiceCombo) BookableService.getWithName(oldName);
			combo.setName(name);
			combo.setMainService(new ComboItem(true, (Service) BookableService.getWithName(mainService),combo));
			/*if(services.length < combo.getServices().size()){

			}
			else if(services.length < combo.getServices().size()){

			}
			else{
				for(int i = 0; i < services.length;i++){
				}
			}*/
		}
		catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Tomasz Mroz
	 * @param combo
	 * @throws InvalidInputException
	 */
	public static void deleteServiceCombo(String username,ServiceCombo combo) throws InvalidInputException{
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
		if(!(username.equals("owner"))){
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
