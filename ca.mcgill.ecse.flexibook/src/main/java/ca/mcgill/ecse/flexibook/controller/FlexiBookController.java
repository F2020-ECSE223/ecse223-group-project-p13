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
	/*public static void login(String username, String password) throws InvalidInputException {
		FlexiBook flexibook =FlexiBookApplication.getFlexiBook();

		try{ if(username.length()==0 || password.length()==0) {
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
		} catch (RuntimeException e){
			e.printStackTrace();
		}
		
		
	}*/
	/**
	 * @author Victoria Sanchez
	 * @return boolean
	 * @throws InvalidInputException
	 */
	/*public static void logout() throws Exception {
		try{
		if(FlexiBookApplication.getUser()==null) {
			throw new Exception("the user has already been logged out");
		} else {
			FlexiBookApplication.setCurrentUser(null);
		}
		} catch(RuntimeException e){
			e.printStackTrace();
		}
		
	}*/
	/**
	 * @author Victoria Sanchez
	 //* @param date
	 * //@param isWeek
	 * @return appointments 
	 */

	
	/*public static List<TOAppointment> getAppointmentCalendar(int day,int month, int year, Boolean isWeek) throws InvalidInputException {
		Date date = null;
		try{
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
		} catch(RuntimeException e){
		e.printStackTrace();
		}
	}*/


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
			ServiceCombo oldCombo = (ServiceCombo) BookableService.getWithName(oldName);
			ServiceCombo combo = new ServiceCombo(name,flexiBook);
			combo.setName(name);
			//combo.setMainService(new ComboItem(true, (Service) BookableService.getWithName(mainService),combo));
			for(int i= 0; i < services.length; i++){
				if(services[i].equals(mainService)) {
					combo.setMainService(new ComboItem(true, (Service) BookableService.getWithName(mainService),combo));
				}
				else {
					combo.addService(Boolean.valueOf(mandatory[i]), (Service) BookableService.getWithName(services[i]));
				}
			}
			flexiBook.removeBookableService(oldCombo);
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
		try{
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
		catch(RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}

	}

	/**
	 * @author Tomasz Mroz
	 * Turns an java.sql.date object to a java.time.localdatetime object set at midnight
	 */
	private static LocalDateTime cleanDate(Date date) {
		return LocalDate.parse(date.toString()).atStartOfDay();
	}
	
	/**
	 * @author Hana Gustyn
	 * @param username
	 * @param name
	 * @param duration
	 * @param downtimeDuration
	 * @param downtimeStart
	 * @throws InvalidInputException
	 */
	public static void addService(String username, String name, int duration, int downtimeDuration, int downtimeStart) throws InvalidInputException {
		FlexiBook flexibook = FlexiBookApplication.getFlexiBook();
		
		try {
			if (checkIfServiceExists(name, duration, downtimeDuration, downtimeStart, flexibook)) {
				throw new InvalidInputException("Service " + name + " already exists");
			}
			
			checkServiceParameters(name, duration, downtimeDuration, downtimeStart, flexibook);
			
			if (!(username.equals("owner"))) {
				throw new InvalidInputException("You are not authorized to perform this operation");
			}
			
			else {
				flexibook.addService(name, duration, downtimeDuration, downtimeStart);
			}
			
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	/**
	 * @author Hana Gustyn
	 * @param username
	 * @param currentName
	 * @param name
	 * @param duration
	 * @param downtimeDuration
	 * @param downtimeStart
	 * @throws InvalidInputException
	 */
	public static void updateService(String username, String currentName, String name, int duration, int downtimeDuration, int downtimeStart) throws InvalidInputException {
		FlexiBook flexibook = FlexiBookApplication.getFlexiBook();
		
		try {
			checkServiceParameters(name, duration, downtimeDuration, downtimeStart, flexibook);
			
			if (currentName.equals(name)) {
				throw new InvalidInputException("Service " + name + " already exists");
			}
			
			if (!(username.equals("owner"))) {
				throw new InvalidInputException("You are not authorized to perform this operation");
			}
			
			else {
				Service s = (Service) BookableService.getWithName(currentName);
				
				s.setName(name);
				s.setDuration(duration);
				s.setDowntimeDuration(downtimeDuration);
				s.setDowntimeStart(downtimeStart);
			}
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	/**
	 * @author Hana Gustyn
	 * @param username
	 * @param service
	 * @throws InvalidInputException
	 */
	public static void deleteService(String username, Service service) throws InvalidInputException {
		FlexiBook flexibook = FlexiBookApplication.getFlexiBook();
		
		try {
			List<Appointment> appointments = flexibook.getAppointments();
			for (Appointment a:appointments) {
				if(cleanDate(a.getTimeSlot().getStartDate()).compareTo(SystemTime.getDate()) >= 0) {
					throw new InvalidInputException("The service contains future appointments");
				}
			}
			
			List<BookableService> bookableServices = flexibook.getBookableServices();
			for (BookableService b:bookableServices) {
				if (b instanceof ServiceCombo) {
					
					ServiceCombo combo = (ServiceCombo) b;
					
					if (combo.getMainService().getService().equals(service)) {
						deleteServiceCombo(username, combo);
						continue;
					}
					
					List<ComboItem> items = combo.getServices();
					for (ComboItem i:items) {
						if (i.getService().equals(service)) {
							i.getService().delete();
						}
					}
				}
			}
			
			if (!(username.equals("owner"))) {
				throw new InvalidInputException("You are not authorized to perform this operation");
			}
			
			
			service.delete();
			
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	/**
	 * @author Hana Gustyn
	 * @param name
	 * @param duration
	 * @param downtimeDuration
	 * @param downtimeStart
	 * @param flexibook
	 * @return boolean
	 */
	private static boolean checkIfServiceExists(String name, int duration, int downtimeDuration, int downtimeStart, FlexiBook flexibook) {
		List<BookableService> services = flexibook.getBookableServices();
		for (BookableService b:services) {
			if (b instanceof Service) {
				Service service = (Service) b;
				
				if ((service.getName() == name) && (service.getDuration() == duration) && (service.getDowntimeDuration() == downtimeDuration) && (service.getDowntimeStart() == downtimeStart)) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * @author Hana Gustyn
	 * @param name
	 * @param duration
	 * @param downtimeDuration
	 * @param downtimeStart
	 * @param flexibook
	 * @throws InvalidInputException
	 */
	private static void checkServiceParameters(String name, int duration, int downtimeDuration, int downtimeStart, FlexiBook flexibook) throws InvalidInputException {
		if (duration <= 0) {
			throw new InvalidInputException("Duration must be positive");
		}
		
		if ((downtimeDuration < 0) && (downtimeStart != 0)) {
			throw new InvalidInputException("Downtime duration must be positive");
		}
		
		if ((downtimeDuration > 0) && (downtimeStart == 0)) {
			throw new InvalidInputException("Downtime must not start at the beginning of the service");
		}
		
		if ((downtimeDuration == 0) && (downtimeStart != 0)) {
			throw new InvalidInputException("The start of the service downtime must be 0 when the downtime duration is 0.");
		}
		
		if ((downtimeStart == 0) && (downtimeDuration < 0)) {
			throw new InvalidInputException("Downtime duration must be 0");
		}
		
		if (downtimeStart < 0) {
			throw new InvalidInputException("Downtime must not start before the beginning of the service");
		}
		
		if (downtimeStart > duration) {
			throw new InvalidInputException("Downtime must not start after the end of the service");
		}
		
		if ((downtimeStart + downtimeDuration) <= duration) {
			throw new InvalidInputException("Downtime must not end after the service");
		}
	}
}
