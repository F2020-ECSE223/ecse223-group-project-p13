package ca.mcgill.ecse.flexibook.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
	 * @author Fiona Ryan
	 * @param customer
	 * @param date
	 * @param serviceName
	 * @param newTime
	 * @throws InvalidInputException
	 * */
	public static void makeAppointment(User customer, String date, String newTime, String serviceName) throws InvalidInputException {
		FlexiBook flexibook = FlexiBookApplication.getFlexiBook();
		try {
			Appointment appt = null;
			for (Appointment a : flexibook.getAppointments()) {
				if (a.getCustomer().getUsername().equals(customer)) {
					if (a.getBookableService().getName().equals(serviceName)) {
						appt = a;
					}
				}
			}

			if (customer.equals(flexibook.getOwner())) {
				throw new InvalidInputException("An owner cannot make an appointment");

			}

			for (BookableService s : flexibook.getBookableServices()) {
				if (s instanceof Service) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("kk:mm");
					Time sTime = Time.valueOf(LocalTime.parse(newTime, formatter));
					if (appt.getTimeSlot(). != null) {
						throw new InvalidInputException("slot is occupied by existing appointment");

					}

					if (((Service) s).getDowntimeDuration() < ((Service) BookableService.getWithName(serviceName)).getDuration()) {
						throw new InvalidInputException("slot corresponds to down time that is not long enough");

					} else if (appt.getTimeSlot().getStartDate() != null) {
						if (flexibook.getBusiness().hasHolidays()) {
							throw new InvalidInputException("slot is during holiday");
						}
						if (flexibook.getBusiness().hasBusinessHours()) {
							throw new InvalidInputException("slot is not during a business hour (saturday)");
						}

					} else if (cleanDate(appt.getTimeSlot().getStartDate()).compareTo(SystemTime.getDate()) <= 0) {
						throw new InvalidInputException("slot is in 2019");

					} else if (flexibook.getAppointment(newTime) == null) {
						flexibook.addAppointment(appt);

					} else if (((Service) s).getDowntimeDuration() > ((Service) BookableService.getWithName(serviceName)).getDuration()) {
						flexibook.addAppointment(appt);
					}
				}
					if (s instanceof ServiceCombo) {
						if (appt.getTimeSlot().getStartTime() != null) {
							throw new InvalidInputException("slot is occupied by existing appointment");

						} else if (((Service) s).getDowntimeDuration() < ((Service) BookableService.getWithName(serviceName)).getDuration()) {
							throw new InvalidInputException("slot corresponds to down time that is not long enough");

						} else if (appt.getTimeSlot().getStartDate() != null) {
							if (flexibook.getBusiness().hasHolidays()) {
								throw new InvalidInputException("slot is during holiday");
							}
							if (flexibook.getBusiness().hasBusinessHours()) {
								throw new InvalidInputException("slot is not during a business hour (saturday)");
							}

						} else if (cleanDate(appt.getTimeSlot().getStartDate()).compareTo(SystemTime.getDate()) <= 0) {
							throw new InvalidInputException("slot is in 2019");

						} else if (flexibook.getAppointment(newTime) == null) {
							flexibook.addAppointment(appt);

						} else if (((Service) s).getDowntimeDuration() > ((Service) BookableService.getWithName(serviceName)).getDuration()) {
							flexibook.getAppointments().add(appt);
						}
					}
				}

		}
 		catch(RuntimeException e){
				throw new InvalidInputException(e.getMessage());
		}

	}

	/**
	 * @autho Fiona Ryan
	 * @param customer
	 * @param appointment
	 * @throws InvalidInputException
	 */
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
	/**
	 * @author Fiona Ryan
	 * @param customer
	 * @param aService
	 * @param date
	 * @param newAction
	 * @param newComboItem
	 * @param newTime
	 * @param newDate
	 * @param serviceType
	 * @param time
	 * @throws InvalidInputException
	 * */
	public static void updateAppointment(String customer, String date, String time, String serviceType, String aService,
										 String newTime, String newDate, String newAction, String newComboItem ) throws InvalidInputException {
		FlexiBook flexibook = FlexiBookApplication.getFlexiBook();

		try {
			Appointment appt = null;
			for (Appointment a : flexibook.getAppointments()) {
				if (a.getCustomer().getUsername().equals(customer)) {
					if (a.getBookableService().getName().equals(serviceType)) {
						appt = a;
					}
				}
			}

			if(newAction == null && newComboItem == null) {
				if (flexibook.getBusiness().getHolidays().contains(appt.getTimeSlot().getStartDate())) {
					throw new InvalidInputException("slot is a holiday");
				}
				if (appt.getTimeSlot().getStartDate() != null) {
					if((flexibook.getBusiness().hasHolidays())){
						throw new InvalidInputException("slot is not a business hour (saturday)");
					}
				}
				if (appt.getTimeSlot() != null) {
					throw new InvalidInputException("slot is occupied by an existing appointment");
				}
				if (appt.getTimeSlot().getEndTime() != null) {
					if((flexibook.getBusiness().hasBusinessHours())) {
						throw new InvalidInputException("endTime of slot is not within business hours");
					}
				}

			}
			else if(newTime == null && newDate == null){
				if(newAction.equals("add")) {
					ServiceCombo name = (ServiceCombo) appt.getBookableService();
					int counter = 0;
					for (ComboItem c : name.getServices()) {
						counter += c.getService().getDowntimeDuration();
					}
					if (counter < ((Service) BookableService.getWithName(newComboItem)).getDuration()) {
						throw new InvalidInputException("additional extentions service does not fit in available slot");
					}

					for (int i = 0; i < name.getServices().size();i++) {
						counter += name.getServices().get(i).getService().getDowntimeDuration();
						if(((Service) BookableService.getWithName(newComboItem)).getDuration() < counter){
							((ServiceCombo) BookableService.getWithName(serviceType)).addServiceAt(new ComboItem(false,
									((Service)BookableService.getWithName(newComboItem)),(ServiceCombo) BookableService.getWithName(serviceType)),i);
						}
					}

				}
			}
				if(newAction.equals("remove")){
					for (BookableService s : flexibook.getBookableServices()) {
						if (s instanceof ServiceCombo) {
							if (((ServiceCombo) s).getMainService().equals(((Service) BookableService.getWithName(newComboItem)))){
								throw new InvalidInputException("cannot remove main service");
							}
						}
						if (s instanceof ServiceCombo) {
							if (((new ComboItem(false, ((Service) BookableService.getWithName(newComboItem)),
									(ServiceCombo) BookableService.getWithName(serviceType)))).isMandatory()) {
								throw new InvalidInputException("cannot remove mandatory service");
							}
						}
						if (s instanceof ServiceCombo) {
							((ServiceCombo) s).removeService((( new ComboItem(false,((Service) BookableService.getWithName(newComboItem)),
									(ServiceCombo) BookableService.getWithName(serviceType)))));
						}
						}
					}

			if (customer.equals(flexibook.getOwner().getUsername())) {
				throw new InvalidInputException("Error: An owner cannot update a customer's appointment ");
			}

			if (!appt.getCustomer().getUsername().equals(customer)) {
				throw new InvalidInputException("Error: A customer can only update their own appointments");
			}
		}
		catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

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
