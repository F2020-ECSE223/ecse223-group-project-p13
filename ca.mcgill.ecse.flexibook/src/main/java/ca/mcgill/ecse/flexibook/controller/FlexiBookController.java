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
	 * @author Fiona Ryan
	 * @param customer
	 * @param appointment
	 * @param business
	 * */
	public static void makeAppointment(User customer, Appointment appointment, Business business) throws InvalidInputException {
		FlexiBook flexibook = FlexiBookApplication.getFlexiBook();
		try {
			if (customer.equals(flexibook.getOwner())) {
				throw new InvalidInputException("An owner cannot make an appointment");

			}
			for (BookableService s : flexibook.getBookableServices()) {
				if (s instanceof Service) {

					if (appointment.getTimeSlot().getStartTime() != null) {
						throw new InvalidInputException("slot is occupied by existing appointment");
					}

					if (appointment.getTimeSlot().getStartTime() <= Service.getDowntimeDuration()) {
						throw new InvalidInputException("slot corresponds to down time that is not long enough");
					}

					if (appointment.getTimeSlot().getStartDate() == business.getHolidays()) {
						throw new InvalidInputException("slot is during holiday");
					}

					if (appointment.getTimeSlot().getStartDate() != business.getBusinessHours()) {
						throw new InvalidInputException("slot is not during a business hour (saturday)");
					}

					if (cleanDate(appointment.getTimeSlot().getStartDate()).compareTo(SystemTime.getDate()) <= 0) {
						throw new InvalidInputException("slot is in 2019");
					}
				}
				if (s instanceof ServiceCombo) {
					if (appointment.getTimeSlot().getStartTime() != null) {
						throw new InvalidInputException("slot is occupied by existing appointment");
					}

					if () {
						throw new InvalidInputException("slot corresponds to down time that is not long enough");
					}

					if (appointment.getTimeSlot().getStartDate() == business.getHolidays()) {
						throw new InvalidInputException("slot is during holiday");
					}

					if (appointment.getTimeSlot().getStartDate() != business.getBusinessHours()) {
						throw new InvalidInputException("slot is not during a business hour (saturday)");
					}

					if (cleanDate(appointment.getTimeSlot().getStartDate()).compareTo(SystemTime.getDate()) <= 0) {
						throw new InvalidInputException("slot is in 2019");
					}

				} else {
					flexibook.getAppointments().add(appointment);
					customer = appointment.getCustomer();
					int appointmentNumber = flexibook.numberOfAppointments();
					appointmentNumber += 1;
				}
			}
		catch(RuntimeException e){
				throw new InvalidInputException(e.getMessage());
			}
		}
	}
	/**
	 * @author Fiona Ryan
	 * @param customer
	 * @param appointment
	 * */
	public static void cancelAppointment(User customer, Appointment appointment) throws InvalidInputException {
		FlexiBook flexibook = FlexiBookApplication.getFlexiBook();
		try{
			if(!customer.equals(appointment.getCustomer())){
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
	 * @param appointment
	 * @param business
	 * @param aService
	 * */
	public static void updateAppointment(User customer, Appointment appointment, Business business, ComboItem aService) throws InvalidInputException {
		FlexiBook flexibook = FlexiBookApplication.getFlexiBook();

		try {
			if (appointment.getTimeSlot().getStartDate() == business.getHolidays()) {
				throw new InvalidInputException("slot is a holiday");
			}

			if (appointment.getTimeSlot().getStartDate() != business.getBusinessHours()) {
				throw new InvalidInputException("slot is not a business hour (saturday)");
			}

			if (appointment.getTimeSlot() != null) {
				throw new InvalidInputException("slot is occupied by an existing appointment");
			}

			if (appointment.getTimeSlot().getEndTime() != business.getBusinessHours()) {
				throw new InvalidInputException("endTime of slot is not within business hours");
			}

			for (BookableService s : flexibook.getBookableServices()) {
				if(s instanceof ServiceCombo){
					if (((ServiceCombo) s).getMainService().equals(((ServiceCombo) s).removeService(aService))) {
						throw new InvalidInputException("cannot remove main service");
					}
				}
				if (s instanceof ServiceCombo) {
					if (aService.isMandatory() == true) {
						throw new InvalidInputException("cannot remove mandatory service");
					}
				}
			}

			if (appointment.getTimeSlot().getEndTime()  ) {
				throw new InvalidInputException("additional extentions service does not fit in available slot");
			}

			if (customer.equals(flexibook.getOwner())) {
				throw new InvalidInputException("Error: An owner cannot update a customer's appointment ");
			}

			if (appointment.getCustomer() != customer) {
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
	public static void defineServiceCombo(String name, Service mainService, ArrayList<Service> servicesList, ArrayList<Boolean> isMandatory) throws InvalidInputException {
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
		if(!(FlexiBookApplication.getUser() instanceof Owner)){
			throw new InvalidInputException("You are not authorized to perform this operation");
		}
		//Valid service combo
		if(!(servicesList.contains(mainService))){
			throw new InvalidInputException("Main service must be included in the services");
		}
		if(servicesList.size() < 2){
			throw new InvalidInputException("A Service Combo must contain at least 2 services");
		}
		for(Service s:servicesList){
			if(!(flexiBook.getBookableServices().contains(s))){
				throw new InvalidInputException("Service "+ s.getName()+ " does not exist");
			}
		}
		//Testing for unique service combos
		for(BookableService s: flexiBook.getBookableServices()){
			if(s instanceof ServiceCombo){
				if((((ServiceCombo) s).getMainService().getService().equals(mainService))){
					if(s.getName().equals(name)){
						throw new InvalidInputException("Service combo"+s.getName()+" already exists");
					}
				}
			}
		}

		ServiceCombo serv = new ServiceCombo(name,flexiBook);
		for(int i = 0;i < servicesList.size(); i++){
			serv.addService(isMandatory.get(i),servicesList.get(i));
		}

	}
	public static void updateServiceCombo(){
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();

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
