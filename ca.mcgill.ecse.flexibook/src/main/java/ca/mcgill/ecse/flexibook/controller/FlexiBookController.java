package ca.mcgill.ecse.flexibook.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

	public static void makeAppointment(){
		FlexiBook flexibook = FlexiBookApplication.getFlexiBook();
	}
	public static void cancelAppointment(User customer, Appointment appointment) throws InvalidInputException {
		FlexiBook flexibook = FlexiBookApplication.getFlexiBook();
		try{
			if(customer.equals(appointment.getCustomer())){
				throw new InvalidInputException("A customer can only cancel their own appointments");
			}
			if(customer.equals(flexibook.getOwner())){
				throw new InvalidInputException("An owner cannot cancel an appointment");
			}
			if(appointment.getTimeSlot().getStartDate().compareTo(getCurrentDate()) >= 0){
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
	public static void updateServiceCombo(){}
	public static void deleteServiceCombo(ServiceCombo combo) throws InvalidInputException{
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
		if(!(FlexiBookApplication.getUser() instanceof Owner)){
			throw new InvalidInputException("You are not authorized to perform this operation");
		}
		else {
			List<Appointment> appointments = flexiBook.getAppointments();
			for(Appointment a:appointments){
				if(a.getBookableService().equals(combo)){
					if(a.getTimeSlot().getStartDate().compareTo(SystemTime.getDate()) >= 0){
						throw new InvalidInputException("Service combo "+ combo.getName()+" has future appointments");
					}
				}

			}
			combo.delete();
		}
	}

	private static Date cleanDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		java.util.Date tempCleanedDate = cal.getTime();
		java.sql.Date cleanedDate = new java.sql.Date(tempCleanedDate.getTime());
		return cleanedDate;
	}
	private static java.util.Date getCurrentDate(){
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		java.util.Date date = cal.getTime();
		return date;
	}
}
