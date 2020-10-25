package ca.mcgill.ecse.flexibook.controller;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
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
import ca.mcgill.ecse.flexibook.persistence.FlexiBookPersistence;
import ca.mcgill.ecse.flexibook.util.SystemTime;


public class FlexiBookController {
	public static void login(String username, String password) throws InvalidInputException {
		FlexiBook flexibook = FlexiBookApplication.getFlexiBook();
		boolean found = false;
		boolean correctPassword=false;

		if (username.length() == 0 || password.length() == 0) {
			throw new InvalidInputException("invalid entry");
		}
		if (flexibook.getOwner() == null) {
			if (username.equals("owner") && password.equals("owner")) {
				correctPassword=true;
				found=true;
				Owner newOwner = new Owner(username, password, flexibook);
				flexibook.setOwner(newOwner);
				FlexiBookApplication.setCurrentUser(flexibook.getOwner());
			}

		}

		if (flexibook.getOwner() != null) {
			if (username == flexibook.getOwner().getUsername()) {
				if (!(password.equals(flexibook.getOwner().getPassword()))) {
					throw new InvalidInputException("Username/password not found");
				} else {
					correctPassword=true;
					found=true;
					FlexiBookApplication.setCurrentUser(flexibook.getOwner());

				}
			}
		}
		

		for (User currentUser : flexibook.getCustomers()) {
			if (username.equals(currentUser.getUsername())) {
				found = true;
				if (password.equals(currentUser.getPassword())) {
					correctPassword=true;
					FlexiBookApplication.setCurrentUser(currentUser);	
					}
				}
			}
		if (!found || !correctPassword) {
			FlexiBookApplication.setCurrentUser(null);
			throw new InvalidInputException("Username/password not found");
		}
	}


	/**
	 * @throws InvalidInputException
	 * @author Victoria Sanchez
	 */
	public static void logout() throws Exception {
		if (FlexiBookApplication.getUser() == null) {
			throw new Exception("The user is already logged out");
		} else {
			FlexiBookApplication.setCurrentUser(null);
		}

	}

	/**
	 * @param d1
	 * @throws InvalidInputException
	 * @author Victoria Sanchez
	 */

	public static void checkDate(LocalDate d1) throws InvalidInputException {
		if (d1.getMonthValue() <= 0 || d1.getMonthValue() > 12) {
			throw new InvalidInputException("invalid date");
		}
		if (d1.getDayOfMonth() <= 0 || d1.getDayOfMonth() > 31) {
			throw new InvalidInputException("invalid date");
		}
		if (d1.getMonthValue() == 4 || d1.getMonthValue() == 6 || d1.getMonthValue() == 9 || d1.getMonthValue() == 11) {
			if (d1.getDayOfMonth() > 30) {
				throw new InvalidInputException("invalid date");
			}
		}
		if (d1.getMonthValue() == 2) {
			if (d1.getDayOfMonth() > 28) {
				throw new InvalidInputException("invalid date");
			}
		}
	}

	/**
	 * @param  date
	 * @return List<TOAppointmentCalendarItem>
	 * @throws ParseException
	 * @throws InvalidInputException
	 * @author Victoria Sanchez
	 */
	public static List<TOAppointmentCalendarItem> getAvailableAppointmentCalendarWeek(String date) throws ParseException, InvalidInputException {
		List<TOAppointmentCalendarItem> t1 = getAvailableAppointmentCalendarDay(date);
		SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d1;
		d1 = s1.parse(date);
		java.sql.Date sqlDate = new Date(d1.getTime());
		Calendar newC = Calendar.getInstance();
		newC.setTime(s1.parse(date));
		newC.add(Calendar.DAY_OF_MONTH, 1);
		String Date2 = s1.format(newC.getTime());
		List<TOAppointmentCalendarItem> t2 = getAvailableAppointmentCalendarDay(Date2);


		Calendar newC2 = Calendar.getInstance();
		newC2.setTime(s1.parse(date));
		newC2.add(Calendar.DAY_OF_MONTH, 2);
		String Date3 = s1.format(newC2.getTime()); //day 3
		List<TOAppointmentCalendarItem> t3 = getAvailableAppointmentCalendarDay(Date3);


		Calendar newC3 = Calendar.getInstance();
		newC2.setTime(s1.parse(date));
		newC2.add(Calendar.DAY_OF_MONTH, 3);
		String Date4 = s1.format(newC2.getTime());
		List<TOAppointmentCalendarItem> t4 = getAvailableAppointmentCalendarDay(Date4);


		Calendar newC4 = Calendar.getInstance();
		newC2.setTime(s1.parse(date));
		newC2.add(Calendar.DAY_OF_MONTH, 4);
		String Date5 = s1.format(newC2.getTime());  //day5
		List<TOAppointmentCalendarItem> t5 = getAvailableAppointmentCalendarDay(Date5);


		Calendar newC5 = Calendar.getInstance();
		newC2.setTime(s1.parse(date));
		newC2.add(Calendar.DAY_OF_MONTH, 5);
		String Date6 = s1.format(newC2.getTime());
		List<TOAppointmentCalendarItem> t6 = getAvailableAppointmentCalendarDay(Date6);

		Calendar newC6 = Calendar.getInstance();
		newC2.setTime(s1.parse(date));
		newC2.add(Calendar.DAY_OF_MONTH, 6);
		String Date7 = s1.format(newC2.getTime());  //day7
		List<TOAppointmentCalendarItem> t7 = getAvailableAppointmentCalendarDay(Date7);


		t1.addAll(t2);
		t1.addAll(t3);
		t1.addAll(t4);
		t1.addAll(t5);
		t1.addAll(t6);
		t1.addAll(t7);

		return t1;

	}

	/**
	 * @param  date
	 * @return List<TOAppointmentCalendarItem>
	 * @throws ParseException
	 * @throws InvalidInputException
	 * @author Victoria Sanchez
	 */
	public static List<TOAppointmentCalendarItem> getUnavailableAppointmentCalendarWeek(String date) throws ParseException, InvalidInputException {
		List<TOAppointmentCalendarItem> t1 = getUnavailableAppointmentCalendar(date);
		SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d1;
		d1 = s1.parse(date);
		java.sql.Date sqlDate = new Date(d1.getTime());
		Calendar newC = Calendar.getInstance();
		newC.setTime(s1.parse(date));
		newC.add(Calendar.DAY_OF_MONTH, 1);
		String Date2 = s1.format(newC.getTime());
		List<TOAppointmentCalendarItem> t2 = getUnavailableAppointmentCalendar(Date2);


		Calendar newC2 = Calendar.getInstance();
		newC2.setTime(s1.parse(date));
		newC2.add(Calendar.DAY_OF_MONTH, 2);
		String Date3 = s1.format(newC2.getTime()); //day 3
		List<TOAppointmentCalendarItem> t3 = getUnavailableAppointmentCalendar(Date3);


		Calendar newC3 = Calendar.getInstance();
		newC2.setTime(s1.parse(date));
		newC2.add(Calendar.DAY_OF_MONTH, 3);
		String Date4 = s1.format(newC2.getTime());
		List<TOAppointmentCalendarItem> t4 = getUnavailableAppointmentCalendar(Date4);


		Calendar newC4 = Calendar.getInstance();
		newC2.setTime(s1.parse(date));
		newC2.add(Calendar.DAY_OF_MONTH, 4);
		String Date5 = s1.format(newC2.getTime());  //day5
		List<TOAppointmentCalendarItem> t5 = getUnavailableAppointmentCalendar(Date5);

		Calendar newC5 = Calendar.getInstance();
		newC2.setTime(s1.parse(date));
		newC2.add(Calendar.DAY_OF_MONTH, 5);
		String Date6 = s1.format(newC2.getTime());
		List<TOAppointmentCalendarItem> t6 = getUnavailableAppointmentCalendar(Date6);//day6

		Calendar newC6 = Calendar.getInstance();
		newC2.setTime(s1.parse(date));
		newC2.add(Calendar.DAY_OF_MONTH, 6);
		String Date7 = s1.format(newC2.getTime());  //day7
		List<TOAppointmentCalendarItem> t7 = getUnavailableAppointmentCalendar(Date7);

		t1.addAll(t2);
		t1.addAll(t3);
		t1.addAll(t4);
		t1.addAll(t5);
		t1.addAll(t6);
		t1.addAll(t7);
		return t1;
	}
			/**
		 * @author cesar
		 * @param aUsername
		 * @param aPassword
		 * @throw InvalidInputException
		 * checks if there are any faults in the username or password, if there isn't a new account is created 
		 * and added to the list of customers
		 */

		public static void customerSignUp (String aUsername, String aPassword) throws
		InvalidInputException {

			FlexiBook flexibook = FlexiBookApplication.getFlexiBook();
			User user = findUser(aUsername);
			User owner = findUser("owner");
			
			
			try {

				if (user != null) {
					
					throw new InvalidInputException("The username already exists");
				}

				else if (aUsername == null || aUsername.equals("") ) {
					throw new InvalidInputException("The user name cannot be empty");
				}
				else if(aPassword.equals("") || aPassword == null){
					throw new InvalidInputException("The password cannot be empty");
				}
				else if (FlexiBookApplication.getUser()==owner && owner!=null) {

					throw new InvalidInputException("You must log out of the owner account before creating a customer account");

				}
				else {
					flexibook.addCustomer(aUsername, aPassword);
					//User user = findUser(aUsername);
					//FlexiBookApplication.setCurrentUser(user);
				}
			} 
			catch (RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}

		}

		/**
		 * @author cesar
		 * @param oldUsername
		 * @param newUsername
		 * @param newPassword
		 * @throw InvalidInputException
		 * checks if there are any faults in the new username and password chosen, then changes the username and
		 * password for the customer and only the password for the owner
		 */

		public static void updateAccount (String oldUsername, String newUsername, String newPassword) throws
		InvalidInputException {

			FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
			Owner owner = flexiBook.getOwner();
			
			try {

				if (newPassword.equals("") || newPassword == null) {
					throw new InvalidInputException("The password cannot be empty");
				}
				if(newUsername == null || newUsername.equals("")) {
					throw new InvalidInputException("The user name cannot be empty");
				}
				
				
				User user = findUser(oldUsername);
				
				if(owner!=null) {
					if(oldUsername.equals(owner.getUsername())) {
						if(oldUsername.equals(newUsername)) {
							owner.setPassword(newPassword);
							return;
						}
						else {
							throw new InvalidInputException("Changing username of owner is not allowed");
						}
					}

				}
				
				
				for (User customer : flexiBook.getCustomers()) {
					if (customer.getUsername().equals(newUsername)) {
						throw new InvalidInputException("Username not available");
					}
				}
					
				

				if (user == null) {
					throw new InvalidInputException("No account with this username can be found");
				}
				
				if (user.equals(FlexiBookApplication.getUser())) {
					if (user.getUsername() == oldUsername) {
	
						user.setUsername(newUsername);
						user.setPassword(newPassword);
	
					}
				}
				else {
					throw new InvalidInputException("You have to be logged in to the corresponding account to update it");
				}

			} catch (RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}

		}


		/**
		 * @author cesar
		 * @param aUsername
		 * searches the costomer list to find a customer with the name we are looking for
		 * if we are searching for the owner then it returns the owner of the flexiBook
		 */

		private static User findUser (String aUsername){
			FlexiBook flexibook = FlexiBookApplication.getFlexiBook();
			User user = null;
			

			if (aUsername.equals("owner")) {
				//Owner newOwner = new Owner(aUsername, "owner", flexibook);
				//flexibook.setOwner(newOwner);
				Owner newOwner = FlexiBookApplication.getFlexiBook().getOwner();
				user = newOwner;
				return user;
			}

			
			for (User customer : flexibook.getCustomers()) {
				if (customer.getUsername().equals(aUsername)) {
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
		 * Verifies that the account that is going to be deleted is the account logged in, can't delete the 
		 * owner account. 
		 */

		public static void deleteCustomerAccount (String aUsername) throws InvalidInputException {

			
			User user = findUser(aUsername);
			Owner owner = FlexiBookApplication.getFlexiBook().getOwner();
			FlexiBook flexibook = FlexiBookApplication.getFlexiBook();
			


			try {

				if (/*user.equals(owner)||*/aUsername.equals("owner")) {
					throw new InvalidInputException("You do not have permission to delete this account");

				}

				if (user.equals(FlexiBookApplication.getUser())) {
					Customer customer=(Customer) user;
					List<Appointment> userApps = customer.getAppointments();
					for(Appointment userApp : userApps) {
						flexibook.removeAppointment(userApp);
						userApp.delete();
					}
					
					flexibook.removeCustomer(customer);
					user.delete();
					
				} else {
					throw new InvalidInputException("You do not have permission to delete this account");
				}
				FlexiBookApplication.setCurrentUser(null);

			} catch (RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}
		}
	
			/**
		 * this method returns the available timeslots for a given day
		 * @author Victoria Sanchez
		 * @param date
		 * @return appointments
		 * @throws ParseException
		 */

		
		public static List<TOAppointmentCalendarItem> getAvailableAppointmentCalendarDay (String date) throws
		InvalidInputException, ParseException {
			SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date d1;
			d1 = s1.parse(date);
			java.sql.Date sqlDate = new Date(d1.getTime());
			LocalDate date1 = LocalDate.parse(date);
			checkDate(date1);
			DayOfWeek dayOfWeek = date1.getDayOfWeek();
			FlexiBook flexibook = FlexiBookApplication.getFlexiBook();
			ArrayList<TOAppointmentCalendarItem> calendar = new ArrayList<TOAppointmentCalendarItem>();
			int count = 0;
			for (BusinessHour b : flexibook.getBusiness().getBusinessHours()) {
				if (b.getDayOfWeek().toString().equalsIgnoreCase(dayOfWeek.toString())) {
					count++;
				}
			}
			if (count == 0) {
				return calendar;
			}


			ArrayList<Appointment> DayAppointments = new ArrayList<Appointment>();
			for (Appointment a : flexibook.getAppointments()) {
				if (a.getTimeSlot().getStartDate().equals(sqlDate)) {
					DayAppointments.add(a);
				}
			}
			ArrayList<TimeSlot> holidaySlots = new ArrayList<TimeSlot>();
			for (TimeSlot t : flexibook.getBusiness().getHolidays()) {
				if (t.getStartDate().equals(sqlDate) || t.getEndDate().equals(sqlDate)) {
					holidaySlots.add(t);
				}
			}
			for (TimeSlot t : flexibook.getBusiness().getVacation()) {
				if (t.getStartDate().equals(sqlDate) || t.getEndDate().equals(sqlDate)) {
					holidaySlots.add(t);
				}
			}

			if (DayAppointments.isEmpty() && holidaySlots.isEmpty()) {
				for (BusinessHour b1 : flexibook.getBusiness().getBusinessHours()) {
					if (b1.getDayOfWeek().toString().equalsIgnoreCase(dayOfWeek.toString())) {
						TOAppointmentCalendarItem newT = new TOAppointmentCalendarItem(sqlDate, b1.getStartTime(), b1.getEndTime());
						calendar.add(newT);
						return calendar;
					}

				}
			}

			sortDailyAppointments(DayAppointments);

			for (BusinessHour b1 : flexibook.getBusiness().getBusinessHours()) {
				if (b1.getDayOfWeek().toString().equalsIgnoreCase(dayOfWeek.toString())) {
					for (Appointment a : DayAppointments) { //going through appointments
						for (ComboItem s : a.getChosenItems()) {
							if (s.getService().getDowntimeDuration() != 0) {
								Calendar newC = Calendar.getInstance();
								newC.setTime(a.getTimeSlot().getStartTime());
								newC.add(Calendar.MINUTE, s.getService().getDowntimeStart());
								Time newT1 = (Time) newC.getTime();
								Calendar newCEnd = Calendar.getInstance();
								newCEnd.setTime(a.getTimeSlot().getStartTime());
								newCEnd.add(Calendar.MINUTE, s.getService().getDowntimeStart() + s.getService().getDowntimeDuration());
								Time newT2 = (Time) newCEnd.getTime();
								TOAppointmentCalendarItem newT = new TOAppointmentCalendarItem(sqlDate, newT1, newT2);
								calendar.add(newT);
							}
						}
						int indexA = DayAppointments.indexOf(a);
						if (indexA == 0) {
							if (!a.getTimeSlot().getStartTime().equals(b1.getStartTime())) {
								TOAppointmentCalendarItem newT = new TOAppointmentCalendarItem(sqlDate, b1.getStartTime(), a.getTimeSlot().getStartTime());
								TOAppointmentCalendarItem newT2 = new TOAppointmentCalendarItem(sqlDate, a.getTimeSlot().getEndTime(), DayAppointments.get(indexA + 1).getTimeSlot().getStartTime());
								calendar.add(newT);
								calendar.add(newT2);
							if(DayAppointments.size()==1) {
								TOAppointmentCalendarItem availableTime1= new TOAppointmentCalendarItem(sqlDate,b1.getStartTime(),a.getTimeSlot().getStartTime());
								TOAppointmentCalendarItem availableTime= new TOAppointmentCalendarItem(sqlDate,a.getTimeSlot().getStartTime(),b1.getEndTime());
								calendar.add(availableTime1);
								calendar.add(availableTime);
							}
							}if(DayAppointments.size()==1) {
								TOAppointmentCalendarItem app2= new TOAppointmentCalendarItem(sqlDate,a.getTimeSlot().getEndTime(),b1.getEndTime());
							}
							else {
								TOAppointmentCalendarItem newT = new TOAppointmentCalendarItem(sqlDate, a.getTimeSlot().getEndTime(), DayAppointments.get(indexA + 1).getTimeSlot().getStartTime());
								calendar.add(newT);
							}
						}
						if (indexA + 1 == DayAppointments.size()) {
							if (a.getTimeSlot().getEndTime().equals(b1.getEndTime())) {
								return calendar;
							} else {
								TOAppointmentCalendarItem newT = new TOAppointmentCalendarItem(sqlDate, a.getTimeSlot().getEndTime(), b1.getEndTime());
								calendar.add(newT);
							}
						} else {
							TOAppointmentCalendarItem newT = new TOAppointmentCalendarItem(sqlDate, a.getTimeSlot().getEndTime(), DayAppointments.get(indexA + 1).getTimeSlot().getStartTime());
							calendar.add(newT);
						}
					}
				}
			}
			return calendar;
		}

		/**
		 * this method returns unavailable timeslots for a given day
		 * @param date
		 * @return List<TOAppointmentCalendarItem>
		 * @throws InvalidInputException
		 * @throws ParseException
		 */
		public static List<TOAppointmentCalendarItem> getUnavailableAppointmentCalendar (String date) throws
			InvalidInputException, ParseException, ParseException {
			SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date d1;
			d1 = s1.parse(date);
			java.sql.Date sqlDate = new Date(d1.getTime());
			LocalDate date1 = LocalDate.parse(date);
			checkDate(date1);
			DayOfWeek dayOfWeek = date1.getDayOfWeek();
			FlexiBook flexibook = FlexiBookApplication.getFlexiBook();
			ArrayList<TOAppointmentCalendarItem> calendar = new ArrayList<TOAppointmentCalendarItem>();
			int count = 0;
			for (BusinessHour b : flexibook.getBusiness().getBusinessHours()) {
				if (b.getDayOfWeek().toString().equalsIgnoreCase(dayOfWeek.toString())) {
					count++;
				}
			}
			if (count == 0) {
				return calendar;
			}


			ArrayList<Appointment> DayAppointments = new ArrayList<Appointment>();
			for (Appointment a : flexibook.getAppointments()) {
				if (a.getTimeSlot().getStartDate().equals(sqlDate)) {
					DayAppointments.add(a);
				}
			}
			ArrayList<TimeSlot> holidaySlots = new ArrayList<TimeSlot>();
			for (TimeSlot t : flexibook.getBusiness().getHolidays()) {
				if (t.getStartDate().equals(sqlDate) || t.getEndDate().equals(sqlDate)) {
					holidaySlots.add(t);
				}
			}
			for (TimeSlot t : flexibook.getBusiness().getVacation()) {
				if (t.getStartDate().equals(sqlDate) || t.getEndDate().equals(sqlDate)) {
					holidaySlots.add(t);
				}
			}

			if (!holidaySlots.isEmpty()) {
				for (BusinessHour b1 : flexibook.getBusiness().getBusinessHours()) {
					if (b1.getDayOfWeek().toString().equalsIgnoreCase(dayOfWeek.toString())) {
						TOAppointmentCalendarItem newT = new TOAppointmentCalendarItem(sqlDate, b1.getStartTime(), b1.getEndTime());
						calendar.add(newT);
						return calendar;
					}

				}
			}

			sortDailyAppointments(DayAppointments);
			boolean added=false;

			for (BusinessHour b1 : flexibook.getBusiness().getBusinessHours()) {
				if (b1.getDayOfWeek().toString().equalsIgnoreCase(dayOfWeek.toString())) {
					for (Appointment a : DayAppointments) { //going through appointments
						for (ComboItem s : a.getChosenItems()) { //going through service times
							if (s.getService().getDowntimeDuration() != 0) { //if there is a service time
								Calendar newC = Calendar.getInstance(); //create a calendar instance
								newC.setTime(a.getTimeSlot().getStartTime()); //set the calendar instance the start time of the appointment
								newC.add(Calendar.MINUTE, s.getService().getDowntimeStart()); //
								Time newT1 = (Time) newC.getTime();
								Calendar newCEnd = Calendar.getInstance();
								newCEnd.setTime(a.getTimeSlot().getStartTime());
								newCEnd.add(Calendar.MINUTE, s.getService().getDowntimeStart() + s.getService().getDowntimeDuration());
								Time newT2 = (Time) newCEnd.getTime();

								TOAppointmentCalendarItem newT = new TOAppointmentCalendarItem(sqlDate, a.getTimeSlot().getStartTime(), newT1);
								TOAppointmentCalendarItem newT3= new TOAppointmentCalendarItem(sqlDate, newT2, a.getTimeSlot().getEndTime());
								calendar.add(newT);
								calendar.add(newT3);
								added=true;
							} 
							
						} 
						if(!added) {
						TOAppointmentCalendarItem app= new TOAppointmentCalendarItem(sqlDate,a.getTimeSlot().getStartTime(),a.getTimeSlot().getEndTime());
						calendar.add(app);
						}
					}
					
				}
			}
			return calendar;
		}
		/**
		 * @author Victoria Sanchez
		 * @param list
		 * @return List<Appointment>
		 */

		public static List<Appointment> sortDailyAppointments (List < Appointment > list) {
			list.sort((Appointment a1, Appointment a2) -> a1.getTimeSlot().getStartTime().compareTo(a2.getTimeSlot().getStartTime()));
			return list;

		}

		/**
		 * @author Fiona Ryan
		 * Makes an appointment using inputs
		 * @param customer
		 * @param date
		 * @param serviceName
		 * @param newTime
		 * @throws InvalidInputException
		 * */
		public static void makeAppointment (String customer, String date, String newTime, String serviceName, String optionalServices) throws
		InvalidInputException {
				FlexiBook flexibook = FlexiBookApplication.getFlexiBook();
				try {
					Date sDate = Date.valueOf(LocalDate.parse(date, DateTimeFormatter.ofPattern("uuuu-MM-dd")));
					Time sTime;
					if (newTime.length() == 4) {
						sTime = Time.valueOf(LocalTime.parse(newTime, DateTimeFormatter.ofPattern("k:mm")));
					} else {
						sTime = Time.valueOf(LocalTime.parse(newTime, DateTimeFormatter.ofPattern("kk:mm")));

					}

					if (customer.equals(flexibook.getOwner().getUsername())) {
						throw new InvalidInputException("An owner cannot make an appointment");
					}

					if (BookableService.getWithName(serviceName) instanceof Service) {
						for (Appointment a : flexibook.getAppointments()) {
							//slot in 2019
							if (cleanDate(sDate).getYear() < 2020) {
								throw new InvalidInputException("There are no available slots for " + serviceName + " on " + date + " at " + newTime);
							}

							if (a.getTimeSlot().getStartDate().equals(sDate)) {
								if (a.getTimeSlot().getStartTime().compareTo(sTime) < 0 && a.getTimeSlot().getEndTime().compareTo(sTime) > 0) {
									if (a.getBookableService() instanceof ServiceCombo) {
										ServiceCombo s = (ServiceCombo) a.getBookableService();
										int duration = 0;
										int start = 0;
										for (ComboItem c : s.getServices()) {
											duration += c.getService().getDowntimeDuration();
											if (c.getService().getDowntimeStart() != 0) {
												start = c.getService().getDowntimeStart();
											}
										}

										Time startTime = a.getTimeSlot().getStartTime();

										LocalTime startOfDurationTime = startTime.toLocalTime().plusMinutes(start);

										LocalTime endOfDurationTime = startOfDurationTime.plusMinutes(duration);

										LocalTime endOfServiceTime = startOfDurationTime.plusMinutes(((Service) BookableService.getWithName(serviceName)).getDuration());

										if ((Time.valueOf(startOfDurationTime)).compareTo(sTime) < 0 && (Time.valueOf(endOfDurationTime)).compareTo(Time.valueOf(endOfServiceTime)) > 0) {
											Appointment appointment = new Appointment((Customer) User.getWithUsername(customer), BookableService.getWithName(serviceName),
													new TimeSlot(sDate, Time.valueOf(startOfDurationTime), sDate, Time.valueOf(endOfServiceTime), flexibook), flexibook);


										}

										//slot corresponds to down time that is not long enough
										if (duration < ((Service) BookableService.getWithName(serviceName)).getDuration()) {
											throw new InvalidInputException("There are no available slots for " + serviceName + " on " + date + " at " + newTime);
										}


									}
								}
							}
						}



						//slot is during holiday
						for (TimeSlot t : flexibook.getBusiness().getHolidays()) {
							if (t.getStartDate().compareTo(sDate) <= 0 && t.getEndDate().compareTo(sDate) >= 0) {
								throw new InvalidInputException("There are no available slots for " + serviceName + " on " + date + " at " + newTime);
							}
						}
						//slot during vacation
						for (TimeSlot e : flexibook.getBusiness().getVacation()) {
							if (e.getStartDate().compareTo(sDate) < 0 && e.getEndDate().compareTo(sDate) > 0) {
								throw new InvalidInputException("There are no available slots for " + serviceName + " on " + date + " at " + newTime);
							}
						}

						//slot is not during a business hour (saturday)
						if (cleanDate(sDate).toLocalDate().getDayOfWeek().equals(DayOfWeek.SATURDAY) || cleanDate(sDate).toLocalDate().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
							throw new InvalidInputException("unsuccessful");
						}

						//slot is occupied by existing appointment
						for (Appointment r : flexibook.getAppointments()) {
							if (r.getTimeSlot().getStartTime().equals(sTime)) {
								throw new InvalidInputException("unsuccessful");
							}
						}


						LocalTime endTime = sTime.toLocalTime().plusMinutes(((Service) BookableService.getWithName(serviceName)).getDuration());
						Appointment appointment = new Appointment((Customer) User.getWithUsername(customer), BookableService.getWithName(serviceName),
								new TimeSlot(sDate, sTime, sDate, Time.valueOf(endTime), flexibook), flexibook);


					}
					//SERVICE COMBO
					else {
						for (Appointment a : flexibook.getAppointments()) {

							//slot in 2019
							if (cleanDate(sDate).getYear() < 2020) {
								throw new InvalidInputException("There are no available slots for " + serviceName + " on " + date + " at " + newTime);
							}
							if (a.getTimeSlot().getStartDate().equals(sDate)) {
								if (a.getTimeSlot().getStartTime().compareTo(sTime) < 0 && a.getTimeSlot().getEndTime().compareTo(sTime) > 0) {
									if (a.getBookableService() instanceof ServiceCombo) {
										ServiceCombo s = (ServiceCombo) BookableService.getWithName(serviceName);
										int duration = 0;
										int start = 0;
										int time = 0;
										int mainServiceTime = s.getMainService().getService().getDuration();
										int optionalServiceTime = 0;
										String[] services = optionalServices.split(",");
										for(String string: services){
											optionalServiceTime += ((Service)BookableService.getWithName(string)).getDuration();

										}
										for (ComboItem c : ((ServiceCombo)a.getBookableService()).getServices()) {
											duration += c.getService().getDowntimeDuration();
											if (c.getService().getDowntimeStart() != 0) {
												start += c.getService().getDowntimeStart();
												break;
											}
											else{
												start+=c.getService().getDuration();
											}
										}

										time = mainServiceTime + optionalServiceTime;

										Time startTime = a.getTimeSlot().getStartTime();

										LocalTime startOfDowntime = startTime.toLocalTime().plusMinutes(start);

										LocalTime endOfDowntime = startOfDowntime.plusMinutes(duration);

										LocalTime endOfServiceTime = sTime.toLocalTime().plusMinutes(time);

										if ((Time.valueOf(startOfDowntime)).compareTo(sTime) <= 0 && (Time.valueOf(endOfDowntime)).compareTo(Time.valueOf(endOfServiceTime)) >= 0) {
											Appointment appointment = new Appointment((Customer) User.getWithUsername(customer), BookableService.getWithName(serviceName),
													new TimeSlot(sDate, Time.valueOf(startOfDowntime), sDate, Time.valueOf(endOfServiceTime), flexibook), flexibook);

										}

										//slot corresponds to down time that is not long enough
										if (duration < ((Service) BookableService.getWithName(serviceName)).getDuration()) {
											throw new InvalidInputException("There are no available slots for " + serviceName + " on " + date + " at " + newTime);
										}


									}
								}
							}
						}

						//slot is during holiday
						for (TimeSlot t : flexibook.getBusiness().getHolidays()) {
							if (t.getStartDate().compareTo(sDate) <= 0 && t.getEndDate().compareTo(sDate) >= 0) {
								throw new InvalidInputException("There are no available slots for " + serviceName + " on " + date + " at " + newTime);
							}
						}

						//slot during vacation
						for (TimeSlot e : flexibook.getBusiness().getVacation()) {
							if (e.getStartDate().compareTo(sDate) < 0 && e.getEndDate().compareTo(sDate) > 0) {
								throw new InvalidInputException("There are no available slots for " + serviceName + " on " + date + " at " + newTime);
							}
						}

						//slot is not during a business hour (saturday)
						if(cleanDate(sDate).toLocalDate().getDayOfWeek().equals(DayOfWeek.SATURDAY) || cleanDate(sDate).toLocalDate().getDayOfWeek().equals(DayOfWeek.SUNDAY)){
							throw new InvalidInputException("unsuccessful");
						}

						//slot is occupied by existing appointment
						for (Appointment r : flexibook.getAppointments()) {
							if(r.getTimeSlot().getStartTime().equals(sTime)){
								throw new InvalidInputException("unsuccessful");
							}
						}

						int duration = 0;
						for(ComboItem c: ((ServiceCombo)BookableService.getWithName(serviceName)).getServices()){
							duration += c.getService().getDuration();
						}
						LocalTime endTime = sTime.toLocalTime().plusMinutes(duration);
						Appointment appointment = new Appointment((Customer) User.getWithUsername(customer), BookableService.getWithName(serviceName),
								new TimeSlot(sDate,sTime,sDate,Time.valueOf(endTime),flexibook), flexibook);
						String[] services = optionalServices.split(",");
						for(String s: services){
							appointment.addChosenItem(new ComboItem(true,(Service)BookableService.getWithName(s),(ServiceCombo)BookableService.getWithName(serviceName)));
						}

						flexibook.addAppointment(appointment);

					}

				} catch (RuntimeException e) {
					throw new InvalidInputException(e.getMessage());
				}

			}

		/**
		 * @autho Fiona Ryan
		 * Cancels appointment
		 * @param customer
		 * @param apptType
		 * @param time
		 * @param date
		 * @throws InvalidInputException
		 */
		public static void cancelAppointment (String customer, String apptType, String date, String time) throws InvalidInputException {
			FlexiBook flexibook = FlexiBookApplication.getFlexiBook();
			try {
				Date sDate = Date.valueOf(LocalDate.parse(date, DateTimeFormatter.ofPattern("uuuu-MM-dd")));
				if (customer.equals(flexibook.getOwner().getUsername())) {
					throw new InvalidInputException("An owner cannot cancel an appointment");
				}
				for (Appointment a : flexibook.getAppointments()) {
					if (!a.getCustomer().getUsername().equals(customer)) {
						throw new InvalidInputException("A customer can only cancel their own appointments");

					}
					if (a.getTimeSlot().getStartDate().equals(Date.valueOf(SystemTime.getDate().toLocalDate()))) {
						throw new InvalidInputException("Cannot cancel an appointment on the appointment date");
					}
				}
				for (Appointment a : flexibook.getAppointments()) {
					if (a.getCustomer().getUsername().equals(customer)) {
						a.delete();
					}
				}
			} catch (RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}
		}
		/**
		 * @author Fiona Ryan
		 * Updates appointment time/date or add/removes comboItem
		 * @param customer
		 * @param aService
		 * @param date
		 * @param action
		 * @param newComboItem
		 * @param newTime
		 * @param newDate
		 * @param serviceType
		 * @param time
		 * @throws InvalidInputException
		 * */
		public static void updateAppointment (String customer, String serviceType, String date, String time, String
				aService, String newTime, String newDate, String action, String newComboItem) throws InvalidInputException {

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
				if (customer.equals(flexibook.getOwner().getUsername())) {
					throw new InvalidInputException("Error: An owner cannot update a customer's appointment ");
				}

				if (!appt.getCustomer().getUsername().equals(customer)) {
					throw new InvalidInputException("Error: A customer can only update their own appointments");
				}

				//updating time and date
				if (action == null && newComboItem == null) {
					Date sDate = Date.valueOf(LocalDate.parse(newDate, DateTimeFormatter.ofPattern("uuuu-MM-dd")));
					Time sTime;
					if (newTime.length() == 4) {
						sTime = Time.valueOf(LocalTime.parse(newTime, DateTimeFormatter.ofPattern("k:mm")));
					} else {
						sTime = Time.valueOf(LocalTime.parse(newTime, DateTimeFormatter.ofPattern("kk:mm")));
					}

					//slot is a holiday
					for (TimeSlot t : flexibook.getBusiness().getHolidays()) {
						if(t.getStartDate().equals(sDate) || t.getEndDate().equals(sDate)){
							throw new InvalidInputException("unsuccessful");
						}

					}
					//slot is a vacation
					for (TimeSlot e : flexibook.getBusiness().getVacation()) {
						if (e.getStartDate().compareTo(sDate) < 0 && e.getEndDate().compareTo(sDate) > 0) {
							throw new InvalidInputException("unsuccessful");
						}
					}

					//slot is not a business hour (saturday)
					if(cleanDate(sDate).toLocalDate().getDayOfWeek().equals(DayOfWeek.SATURDAY) || cleanDate(sDate).toLocalDate().getDayOfWeek().equals(DayOfWeek.SUNDAY)){
						throw new InvalidInputException("unsuccessful");
					}

					//endTime of slot is not within business hours
					int dur = 0;
					BookableService s =  appt.getBookableService();
					if(s instanceof Service){
						dur = ((Service) s).getDuration();
					}
					else{
						for(ComboItem c: ((ServiceCombo)s).getServices()){
							dur += c.getService().getDuration();
						}
					}

					Time eTime = Time.valueOf(sTime.toLocalTime().plusMinutes(dur));
					for (BusinessHour f : flexibook.getHours()) {
						if(DayOfWeek.valueOf(f.getDayOfWeek().toString().toUpperCase()).equals(cleanDate(sDate).toLocalDate().getDayOfWeek()) ){
							if(f.getEndTime().compareTo(eTime) <0 ){
								throw new InvalidInputException("unsuccessful");
							}
						}

					}

					//slot is occupied by an existing appointment
					for (Appointment r : flexibook.getAppointments()) {
						if(r.getTimeSlot().getStartTime().equals(sTime)){
							throw new InvalidInputException("unsuccessful");
						}
					}


					//Updating appointment

					Appointment newAppt = new Appointment((Customer) User.getWithUsername(customer), BookableService.getWithName(serviceType),
							new TimeSlot(sDate,sTime,sDate,eTime,flexibook), flexibook);

					flexibook.removeAppointment(appt);
					appt.delete();

					throw new InvalidInputException("successful");


					//updating action or comboItem
				} else if (newTime == null && newDate == null) {
					//if action is add
					if (action.equals("add")) {
						ServiceCombo name = (ServiceCombo) appt.getBookableService();
						int counter = 0;
						for (ComboItem c : name.getServices()) {
							counter += c.getService().getDowntimeDuration();
						}

						//additional extensions service does not fit in available slot
						int cycle = name.getServices().size();
						for (int i = 0; i < cycle; i++) {
							counter += name.getServices().get(i).getService().getDuration();
							if (((Service) BookableService.getWithName(newComboItem)).getDuration() < counter) {
								throw new InvalidInputException("unsuccessful");
							}
						}
						//add chosenItem
						appt.addChosenItem(new ComboItem(false,((Service)BookableService.getWithName(newComboItem)),name));

						throw new InvalidInputException("successful");

					}

					//if action is remove
					else if (action.equals("remove")) {
						//cannot remove main service
						ServiceCombo s = (ServiceCombo) appt.getBookableService();
						if(s.getMainService().getService().getName().equals(newComboItem)){
							throw new InvalidInputException("unsuccessful");
						}
						//cannot remove mandatory service
						for(ComboItem c:s.getServices()){
							if(c.getService().getName().equals(newComboItem)){
								if(c.getMandatory()){
									throw new InvalidInputException("unsuccessful");
								}
							}
						}
						//remove chosenItem
						appt.removeChosenItem(new ComboItem(false,((Service)BookableService.getWithName(newComboItem)),s));
						throw new InvalidInputException("successful");
					}

				}

			} catch (RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}
		}

		/**
		 * @author Tomasz Mroz
		 * Creates a service combo from inputs
		 * @param name name of service combo
		 * @param mainService
		 * @param servicesList String list of services seperated by commas
		 * @param isMandatory String list of whether services are mandatory
		 * @throws InvalidInputException
		 */
		public static void defineServiceCombo (String username, String name, String mainService, String
		servicesList, String isMandatory) throws InvalidInputException {
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
				FlexiBookPersistence.save(flexiBook);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}

		}

	/**
	 * @author Tomasz Mroz
	 * Updates service combo
	 * @param username user
	 * @param oldName
	 * @param name
	 * @param mainService
	 * @param servicesList Comma seperated list of services
	 * @param isMandatory comma seperated list of mandatory values
	 * @throws InvalidInputException
	 */
		public static void updateServiceCombo (String username, String oldName, String name, String mainService, String
		servicesList, String isMandatory) throws InvalidInputException {
			FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
			try {
				if (!(flexiBook.getOwner().getUsername().equals(username))) {
					throw new InvalidInputException("You are not authorized to perform this operation");
				}

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
						if (s.getName().equals(name) && !oldName.equals(name)) {
							throw new InvalidInputException("Service combo " + name + " already exists");
							/*if (((ServiceCombo) s).getMainService().getService().getName().equals(mainService)) {
								int counter = 0;
								boolean test = false;
								for(ComboItem c:((ServiceCombo) s).getServices()){
									if(!(c.getService().getName().equals(services[counter]) &&
											(c.getMandatory() == Boolean.valueOf(mandatory[counter])))){
										test = true;
									}
									counter++;
								}
								if(!test ){
									if(!s.getName().equals(oldName)){

									}
								}
							}*/

						}
					}
				}
				ServiceCombo oldCombo = (ServiceCombo) BookableService.getWithName(oldName);
				flexiBook.removeBookableService(oldCombo);
				oldCombo.delete();
				ServiceCombo combo = new ServiceCombo(name, flexiBook);
				combo.setName(name);
				//combo.setMainService(new ComboItem(true, (Service) BookableService.getWithName(mainService),combo));
				for (int i = 0; i < services.length; i++) {
					if (services[i].equals(mainService)) {
						combo.setMainService(new ComboItem(true, (Service) BookableService.getWithName(mainService), combo));
					} else {
						combo.addService(Boolean.valueOf(mandatory[i]), (Service) BookableService.getWithName(services[i]));
					}
				}
				FlexiBookPersistence.save(flexiBook);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}

		/**
		 * @author Tomasz Mroz
		 * Removes service combo
		 * @param combo combo to be removed
		 * @param username user
		 * @throws InvalidInputException
		 */
		public static void deleteServiceCombo (String username, ServiceCombo combo) throws InvalidInputException {
			FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
			try {
				if (!(username.equals("owner"))) {
					throw new InvalidInputException("You are not authorized to perform this operation");
				} else {
					List<Appointment> appointments = flexiBook.getAppointments();
					for (Appointment a : appointments) {
						if (a.getBookableService().equals(combo)) {
							if (cleanDate(a.getTimeSlot().getStartDate()).compareTo(SystemTime.getDate()) >= 0) {
								throw new InvalidInputException("Service combo " + combo.getName() + " has future appointments");
							}
						}

					}
					combo.delete();
					FlexiBookPersistence.save(flexiBook);
				}
			} catch (RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}

		}

		/**
		 * @author Tomasz Mroz
		 * Turns an java.sql.date object to a java.time.localdatetime object set at midnight
		 */
		private static LocalDateTime cleanDate(Date date){
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
		public static void addService (String username, String name,int duration, int downtimeDuration,
		int downtimeStart) throws InvalidInputException {
			FlexiBook flexibook = FlexiBookApplication.getFlexiBook();

			try {
			/*if (checkIfServiceExists(name, flexibook)) {
				throw new InvalidInputException("Service " + name + " already exists");
			}*/

				if (BookableService.hasWithName(name)) {
					throw new InvalidInputException("Service " + name + " already exists");
				}

				checkServiceParameters(name, duration, downtimeDuration, downtimeStart, flexibook);

				if (!(username.equals("owner"))) {
					throw new InvalidInputException("You are not authorized to perform this operation");
				} else {
					new Service(name, flexibook, duration, downtimeDuration, downtimeStart);
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
			public static void updateService (String username, String currentName, String name,int duration,
				int downtimeDuration, int downtimeStart) throws InvalidInputException {
				FlexiBook flexibook = FlexiBookApplication.getFlexiBook();

				try {
					checkServiceParameters(name, duration, downtimeDuration, downtimeStart, flexibook);

					if (!(username.equals("owner"))) {
						throw new InvalidInputException("You are not authorized to perform this operation");
					}

					if (BookableService.hasWithName(name) && (!(currentName.equals(name)))) {
						throw new InvalidInputException("Service " + name + " already exists");
					} else {
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
		 * @param name
		 * @throws InvalidInputException
		 */
		public static void deleteService (String username, String name) throws InvalidInputException {
			FlexiBook flexibook = FlexiBookApplication.getFlexiBook();

			try {
				Service service = (Service) BookableService.getWithName(name);

				List<Appointment> appointments = flexibook.getAppointments();
				for (Appointment a : appointments) {
					if (a.getBookableService().equals(service)) {
						if (cleanDate(a.getTimeSlot().getStartDate()).compareTo(SystemTime.getDate()) >= 0) {
							throw new InvalidInputException("The service contains future appointments");
						}
					}

				}

				if (!(username.equals("owner"))) {
					throw new InvalidInputException("You are not authorized to perform this operation");
				}

				List<BookableService> bookableServices = flexibook.getBookableServices();
				List<BookableService> servicesToDelete = new ArrayList();
				
				for (int i = 0; i < bookableServices.size(); i++) {
					if (bookableServices.get(i) instanceof ServiceCombo) {

						ServiceCombo combo = (ServiceCombo) bookableServices.get(i);

						if (combo.getMainService().getService().equals(service)) {
							servicesToDelete.add(combo);
							
						} else {
							List<ComboItem> items = combo.getServices();
							for (ComboItem item : items) {
								if (item.getService().equals(service)) {
									item.delete();
									break;
								}
							}
						}
					}
				}
				
				int size = servicesToDelete.size();
				for (int i = 0; i < size; i++) {
					ServiceCombo serviceCombo = (ServiceCombo) servicesToDelete.get(i);
					deleteServiceCombo(username, serviceCombo);
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
		 * @throws InvalidInputException
		 */
		private static void checkServiceParameters (String name,int duration, int downtimeDuration,
		int downtimeStart, FlexiBook flexibook) throws InvalidInputException {
			if (duration <= 0) {
				throw new InvalidInputException("Duration must be positive");
			}

			if ((downtimeDuration <= 0) && (downtimeStart != 0)) {
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

			if (((downtimeStart + downtimeDuration) > duration) && (downtimeStart < duration)) {
				throw new InvalidInputException("Downtime must not end after the service");
			}

			if (downtimeStart > duration) {
				throw new InvalidInputException("Downtime must not start after the end of the service");
			}
		}

		/**
		 * @author: Florence
		 */
		public static TOBusinessInfo showBI(){
			FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
			String name = flexiBook.getBusiness().getName();
			String address = flexiBook.getBusiness().getAddress();
			String phoneNumber = flexiBook.getBusiness().getPhoneNumber();
			String email = flexiBook.getBusiness().getEmail();
			TOBusinessInfo disp = new TOBusinessInfo(name, address, phoneNumber, email);
			return disp;
		}


		/**
		 * @author: Florence
		 * @param name
		 * @param address
		 * @param phoneNumber
		 * @param email
		 * @param dayOfWeek
		 * @param bHstart
		 * @param bHend
		 * @param startT
		 * @param endT
		 * @param startD
		 * @param endD
		 * @param addB
		 * @param addBusinessH
		 * @param addV
		 * @param addH
		 * @throws InvalidInputException
		 *
		 */
		public static void setUpBusinessInfo (String name, String address, String phoneNumber, String email, String
		dayOfWeek, String bHstart, String bHend, String startT, String endT, String startD, String endD, Boolean
		addB, Boolean addBusinessH, Boolean addV, Boolean addH) throws InvalidInputException {
			FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
			Business biz = flexiBook.getBusiness();
			try {


				if (!(FlexiBookApplication.getUser() instanceof Owner)) {
					throw new InvalidInputException("No permission to set up business information");
				}
				if (!(email.matches("\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b"))) {
					throw new InvalidInputException("Invalid email");
				}

				if (addB) {

					//biz = new Business(name, address, phoneNumber, email, flexiBook);
					flexiBook.setBusiness(new Business(name, address, phoneNumber, email, flexiBook));
					//flexiBook.getBusiness().setName(name);
					//flexiBook.getBusiness().setAddress(address);
					//flexiBook.getBusiness().setPhoneNumber(phoneNumber);
					//flexiBook.getBusiness().setEmail(email);
				}
				LocalTime bhStart = LocalTime.parse(bHstart);
				LocalTime bhEnd = LocalTime.parse(bHend);


				if (!(bhStart.isBefore(bhEnd)) && addBusinessH) {
					throw new InvalidInputException("Start time must be before end time");
				}

				for (int i = 0; i < flexiBook.getBusiness().numberOfBusinessHours(); i++) {
					BusinessHour a = flexiBook.getBusiness().getBusinessHour(i);
					if (addBusinessH && a.getDayOfWeek().toString().equals(dayOfWeek) && a.getStartTime().toLocalTime().isBefore(bhEnd) && bhStart.isBefore(a.getEndTime().toLocalTime())) {
						throw new InvalidInputException("The business hours cannot overlap");
					}

				}

				LocalTime hStartTime = LocalTime.parse(startT);
				LocalTime hEndTime = LocalTime.parse(endT);
				LocalDate hStartDate = LocalDate.parse(startD);
				LocalDate hEndDate = LocalDate.parse(endD);
				LocalDateTime hStart = LocalDateTime.of(hStartDate, hStartTime);
				LocalDateTime hEnd = LocalDateTime.of(hEndDate, hEndTime);


				if ((addH || addV) && !(hStart.isBefore(hEnd))) {
					throw new InvalidInputException("Start time must be before end time");
				}


				for (TimeSlot a : flexiBook.getBusiness().getHolidays()) {
					LocalTime aStartTime = a.getStartTime().toLocalTime();
					LocalTime aEndTime = a.getEndTime().toLocalTime();
					LocalDate aStartDate = a.getStartDate().toLocalDate();
					LocalDate aEndDate = a.getEndDate().toLocalDate();
					LocalDateTime aStart = LocalDateTime.of(aStartDate, aStartTime);
					LocalDateTime aEnd = LocalDateTime.of(aEndDate, aEndTime);
					if (aStart.isBefore(hEnd) && hStart.isBefore(aEnd) && addV) {
						throw new InvalidInputException("Holiday and vacation times cannot overlap ");
					}
					if (aStart.isBefore(hEnd) && hStart.isBefore(aEnd) && addH) {
						throw new InvalidInputException("Holiday times cannot overlap ");
					}
				}

				for (TimeSlot a : flexiBook.getBusiness().getVacation()) {
					LocalTime aStartTime = a.getStartTime().toLocalTime();
					LocalTime aEndTime = a.getEndTime().toLocalTime();
					LocalDate aStartDate = a.getStartDate().toLocalDate();
					LocalDate aEndDate = a.getEndDate().toLocalDate();
					LocalDateTime aStart = LocalDateTime.of(aStartDate, aStartTime);
					LocalDateTime aEnd = LocalDateTime.of(aEndDate, aEndTime);

					if (aStart.isBefore(hEnd) && hStart.isBefore(aEnd) && addH) {
						throw new InvalidInputException("Holiday and vacation times cannot overlap ");
					}
					if (aStart.isBefore(hEnd) && hStart.isBefore(aEnd) && addV) {
						throw new InvalidInputException("Vacation times cannot overlap ");
					}
				}
				LocalDateTime aNow = LocalDateTime.now();
				if (hStart.isBefore(aNow) && addV) {
					throw new InvalidInputException("Holiday cannot start in past");
				}
				if (hStart.isBefore(aNow) && addH) {
					throw new InvalidInputException("Vacation cannot start in past");
				}


				if (addBusinessH) {
					Time a = Time.valueOf(bHstart);
					Time b = Time.valueOf(bHend);
					BusinessHour.DayOfWeek day = null;
					if (dayOfWeek.equals("Monday")) {
						day = BusinessHour.DayOfWeek.Monday;
					} else if (dayOfWeek.equals("Tuesday")) {
						day = BusinessHour.DayOfWeek.Tuesday;
					} else if (dayOfWeek.equals("Wednesday")) {
						day = BusinessHour.DayOfWeek.Wednesday;
					} else if (dayOfWeek.equals("Thursday")) {
						day = BusinessHour.DayOfWeek.Thursday;
					} else if (dayOfWeek.equals("Friday")) {
						day = BusinessHour.DayOfWeek.Friday;
					} else if (dayOfWeek.equals("Saturday")) {
						day = BusinessHour.DayOfWeek.Saturday;
					} else if (dayOfWeek.equals("Sunday")) {
						day = BusinessHour.DayOfWeek.Sunday;
					}
					BusinessHour newBH = new BusinessHour(day, a, b, flexiBook);
					flexiBook.addHour(newBH);
					flexiBook.getBusiness().addBusinessHour(newBH);
				}
				if (addH) {
					Time a = Time.valueOf(hStartTime);
					Time b = Time.valueOf(hEndTime);
					Date c = Date.valueOf(hStartDate);
					Date d = Date.valueOf(hEndDate);
					TimeSlot holiday = new TimeSlot(c, a, d, b, flexiBook);
					flexiBook.getBusiness().addHoliday(holiday);


				}
				if (addV) {
					Time a = Time.valueOf(hStartTime);
					Time b = Time.valueOf(hEndTime);
					Date c = Date.valueOf(hStartDate);
					Date d = Date.valueOf(hEndDate);
					TimeSlot vacation = new TimeSlot(c, a, d, b, flexiBook);
					flexiBook.getBusiness().addVacation(vacation);
				}
			}catch (RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}

		}

		/**
		 * @author: Florence
		 * @param name
		 * @param address
		 * @param phoneNumber
		 * @param email
		 * @param dayOfWeek
		 * @param oldBHStart
		 * @param oldBHDay
		 * @param startT
		 * @param endT
		 * @param startD
		 * @param endD
		 * @param oldStartD
		 * @param oldStartT
		 * @param basic
		 * @param addBusinessH
		 * @param addV
		 * @param addH
		 * @param updateBH
		 * @param updateH
		 * @param updateV
		 * @param removeBH
		 * @param removeH
		 * @param removeV
		 * @throws InvalidInputException
		 *
		 */
		public static void updateBusinessInfo (String name, String address, String phoneNumber, String email, String
		startBH, String endBH, String dayOfWeek, String oldBHStart, String oldBHDay, String startD, String endD, String
		startT, String endT, String oldStartD, String oldStartT, Boolean basic, Boolean addBusinessH, Boolean
		removeBH, Boolean updateBH, Boolean addV, Boolean removeV, Boolean updateV, Boolean addH, Boolean
		removeH, Boolean updateH) throws InvalidInputException {
			FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
			Business business = flexiBook.getBusiness();
			try {

				if (!(FlexiBookApplication.getUser() instanceof Owner)) {
					throw new InvalidInputException("No permission to set up business information");
				}
				if (basic) {
					if (!(email.matches("\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b"))) {
						throw new InvalidInputException("Invalid email");

					}else {
						flexiBook.getBusiness().setName(name);
						flexiBook.getBusiness().setEmail(email);
						flexiBook.getBusiness().setAddress(address);
						flexiBook.getBusiness().setPhoneNumber(phoneNumber);
					}

				}
				LocalTime bhStart = LocalTime.parse(startBH);
				LocalTime bhEnd = LocalTime.parse(endBH);
				if (addBusinessH || updateBH) {

					if (!(bhStart.isBefore(bhEnd))) {
						throw new InvalidInputException("Start time must be before end time");
					}
					for (BusinessHour a : business.getBusinessHours()) {
						if (a.getDayOfWeek().toString().equals(oldBHDay) && a.getStartTime().toString().equals(oldBHStart)) {
							continue;
						}
						if (addBusinessH && a.getDayOfWeek().toString().equals(dayOfWeek) && a.getStartTime().toLocalTime().isBefore(bhEnd) && bhStart.isBefore(a.getEndTime().toLocalTime())) {
							throw new InvalidInputException("The business hours cannot overlap");
						}
					}
				}

				LocalTime hStartTime = LocalTime.parse(startT);
				LocalTime hEndTime = LocalTime.parse(endT);
				LocalDate hStartDate = LocalDate.parse(startD);
				LocalDate hEndDate = LocalDate.parse(endD);
				LocalDateTime hStart = LocalDateTime.of(hStartDate, hStartTime);
				LocalDateTime hEnd = LocalDateTime.of(hEndDate, hEndTime);
				LocalTime hStartTimeOld = LocalTime.parse(oldStartT);
				LocalDate hStartDateOld = LocalDate.parse(oldStartD);
				if (addH || addV || updateH || updateV) {

					if ((addH || updateH || addV || updateV) && !(hStart.isBefore(hEnd))) {
						throw new InvalidInputException("Start time must be before end time");
					}

					for (TimeSlot a : business.getHolidays()) {
						if (a.getStartDate().toString().equals(oldStartD) && a.getStartTime().toString().equals(oldStartT)) {
							continue;
						}
						LocalTime aStartTime = a.getStartTime().toLocalTime();
						LocalTime aEndTime = a.getEndTime().toLocalTime();
						LocalDate aStartDate = a.getStartDate().toLocalDate();
						LocalDate aEndDate = a.getEndDate().toLocalDate();
						LocalDateTime aStart = LocalDateTime.of(aStartDate, aStartTime);
						LocalDateTime aEnd = LocalDateTime.of(aEndDate, aEndTime);

						if (aStart.isBefore(hEnd) && hStart.isBefore(aEnd) && (addV || updateV)) {
							throw new InvalidInputException("Holiday and vacation times cannot overlap ");
						}
						if (aStart.isBefore(hEnd) && hStart.isBefore(aEnd) && (addH || updateH)) {
							throw new InvalidInputException("Holiday times cannot overlap ");
						}
					}
					for (TimeSlot a : business.getVacation()) {
						if (a.getStartDate().toString().equals(oldStartD) && a.getStartTime().toString().equals(oldStartT)) {
							continue;
						}
						LocalTime aStartTime = a.getStartTime().toLocalTime();
						LocalTime aEndTime = a.getEndTime().toLocalTime();
						LocalDate aStartDate = a.getStartDate().toLocalDate();
						LocalDate aEndDate = a.getEndDate().toLocalDate();
						LocalDateTime aStart = LocalDateTime.of(aStartDate, aStartTime);
						LocalDateTime aEnd = LocalDateTime.of(aEndDate, aEndTime);

						if (aStart.isBefore(hEnd) && hStart.isBefore(aEnd) && (addH || updateH)) {
							throw new InvalidInputException("Holiday and vacation times cannot overlap ");
						}
						if (aStart.isBefore(hEnd) && hStart.isBefore(aEnd) && (addV || updateV)) {
							throw new InvalidInputException("Vacation times cannot overlap ");
						}

					}
					LocalDateTime aNow = LocalDateTime.now();
					if (hStart.isBefore(aNow) && (addV || updateV)) {
						throw new InvalidInputException("Holiday cannot start in past");
					}
					if (hStart.isBefore(aNow) && (addH || updateH)) {
						throw new InvalidInputException("Vacation cannot start in past");
					}

				}
				if (addBusinessH) {
					Time x = Time.valueOf(bhStart);
					Time y = Time.valueOf(bhEnd);
					BusinessHour.DayOfWeek day = null;
					if (dayOfWeek.equals("Monday")) {
						day = BusinessHour.DayOfWeek.Monday;
					} else if (dayOfWeek.equals("Tuesday")) {
						day = BusinessHour.DayOfWeek.Tuesday;
					} else if (dayOfWeek.equals("Wednesday")) {
						day = BusinessHour.DayOfWeek.Wednesday;
					} else if (dayOfWeek.equals("Thursday")) {
						day = BusinessHour.DayOfWeek.Thursday;
					} else if (dayOfWeek.equals("Friday")) {
						day = BusinessHour.DayOfWeek.Friday;
					} else if (dayOfWeek.equals("Saturday")) {
						day = BusinessHour.DayOfWeek.Saturday;
					} else if (dayOfWeek.equals("Sunday")) {
						day = BusinessHour.DayOfWeek.Sunday;
					}

					BusinessHour bh = new BusinessHour(day, x, y, flexiBook);
					business.addBusinessHour(bh);
				}
				if (updateBH) {
					for (BusinessHour a : business.getBusinessHours()) {
						if (a.getDayOfWeek().toString().equals(oldBHDay) && a.getStartTime().toString().equals(oldBHStart)) {
							business.removeBusinessHour(a);
							Time x = Time.valueOf(bhStart);
							Time y = Time.valueOf(bhEnd);
							BusinessHour.DayOfWeek day = null;
							if (dayOfWeek.equals("Monday")) {
								day = BusinessHour.DayOfWeek.Monday;
							} else if (dayOfWeek.equals("Tuesday")) {
								day = BusinessHour.DayOfWeek.Tuesday;
							} else if (dayOfWeek.equals("Wednesday")) {
								day = BusinessHour.DayOfWeek.Wednesday;
							} else if (dayOfWeek.equals("Thursday")) {
								day = BusinessHour.DayOfWeek.Thursday;
							} else if (dayOfWeek.equals("Friday")) {
								day = BusinessHour.DayOfWeek.Friday;
							} else if (dayOfWeek.equals("Saturday")) {
								day = BusinessHour.DayOfWeek.Saturday;
							} else if (dayOfWeek.equals("Sunday")) {
								day = BusinessHour.DayOfWeek.Sunday;
							}
							BusinessHour bh = new BusinessHour(day, x, y, flexiBook);
							business.addBusinessHour(bh);
						}
					}
				}
				if (removeBH) {
					BusinessHour b = null;
					for (BusinessHour a : business.getBusinessHours()) {
						LocalTime oldStT = LocalTime.parse(oldBHStart);
						Time oldT = Time.valueOf(oldStT);
						if (a.getDayOfWeek().toString().equals(oldBHDay) && a.getStartTime().equals(oldT)) {
							b = a;
						}
					}
					b.delete();
				}
				if (addH) {
					Time a = Time.valueOf(hStartTime);
					Time b = Time.valueOf(hEndTime);
					Date c = Date.valueOf(hStartDate);
					Date d = Date.valueOf(hEndDate);
					TimeSlot holiday = new TimeSlot(c, a, d, b, flexiBook);
					flexiBook.addTimeSlot(holiday);
					business.addHoliday(holiday);
				}
				if (addV) {
					Time a = Time.valueOf(hStartTime);
					Time b = Time.valueOf(hEndTime);
					Date c = Date.valueOf(hStartDate);
					Date d = Date.valueOf(hEndDate);
					TimeSlot vacation = new TimeSlot(c, a, d, b, flexiBook);
					flexiBook.addTimeSlot(vacation);
					business.addVacation(vacation);

				}

				if (updateH) {
					TimeSlot temp = null;
					for (TimeSlot a : business.getHolidays()) {
						if (a.getStartDate().toString().equals(oldStartD) && a.getStartTime().toString().equals(oldStartT)) {
							temp=a;
						}
					}
					Time x = Time.valueOf(hStartTime);
					Time b = Time.valueOf(hEndTime);
					Date c = Date.valueOf(hStartDate);
					Date d = Date.valueOf(hEndDate);
					temp.setStartDate(c);
					temp.setStartTime(x);
					temp.setEndDate(d);
					temp.setEndTime(b);
				}
				if (updateV) {
					TimeSlot temp = null;
					for (TimeSlot a : business.getVacation()) {
						if (a.getStartDate().toString().equals(oldStartD) && a.getStartTime().toString().equals(oldStartT)) {
							temp=a;
							break;
						}
					}
					Time x = Time.valueOf(hStartTime);
					Time b = Time.valueOf(hEndTime);
					Date c = Date.valueOf(hStartDate);
					Date d = Date.valueOf(hEndDate);
					temp.setStartDate(c);
					temp.setStartTime(x);
					temp.setEndDate(d);
					temp.setEndTime(b);
				}
				if (removeH) {
					TimeSlot temp = null;
					for (TimeSlot a : business.getHolidays()) {
						if (a.getStartDate().toString().equals(oldStartD) && a.getStartTime().toString().equals(oldStartT)) {
							temp = a;
							break;
						}
					}
					business.removeHoliday(temp);
					temp.delete();
				}

				if (removeV) {
					TimeSlot temp = null;
					for (TimeSlot a : business.getVacation()) {
						if (a.getStartDate().toString().equals(oldStartD) && a.getStartTime().toString().equals(oldStartT)) {
							temp = a;
							break;
						}
					}
					business.removeHoliday(temp);
					temp.delete();
				}
			}catch (RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}


		}
	}







