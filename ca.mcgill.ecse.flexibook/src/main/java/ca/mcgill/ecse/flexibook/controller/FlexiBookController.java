package ca.mcgill.ecse.flexibook.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
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
	public static void login(String username, String password) throws InvalidInputException {
		FlexiBook flexibook = FlexiBookApplication.getFlexiBook();

		if(username.length()==0 || password.length()==0) {
			throw new InvalidInputException("invalid entry");
		}
		if(flexibook.getOwner()==null) {
			if(username.equals("owner")&& password.equals("owner")){
				Owner newOwner = new Owner(username,password,flexibook);
				flexibook.setOwner(newOwner);
				FlexiBookApplication.setCurrentUser(flexibook.getOwner());
			}
			
		}
		
		if (flexibook.getOwner()!=null) {
			if(username==flexibook.getOwner().getUsername()) {
				if(!(password.equals(flexibook.getOwner().getPassword()))) {
					throw new InvalidInputException("username/password not found");
				}
				else {
					FlexiBookApplication.setCurrentUser(flexibook.getOwner());
					
				}
			}
		}
		
		boolean found=false;
		for(User user: flexibook.getCustomers()) {
		if(username.equals(user.getUsername())) {
			found=true;
			if(!(password.equals(user.getPassword()))) {
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
	/**
	 * @author Victoria Sanchez
	 * @throws InvalidInputException
	 */
	public static void logout() throws Exception {
		if(FlexiBookApplication.getUser()==null) {
			throw new Exception("The user is already logged out");
		} else {
			FlexiBookApplication.setCurrentUser(null);
		}
		
	}/**
	*@author Victoria Sanchez
	*@param d1
	*@throws InvalidInputException
	*/
	
	public static void checkDate(LocalDate d1) throws InvalidInputException {
		if(d1.getMonthValue()<=0 || d1.getMonthValue()>12){
			throw new InvalidInputException("invalid date");
		}
		if(d1.getDayOfMonth()<=0|| d1.getDayOfMonth()>31) {
			throw new InvalidInputException("invalid date");
		}
		if(d1.getMonthValue()==4||d1.getMonthValue()==6||d1.getMonthValue()==9||d1.getMonthValue()==11) {
			if(d1.getDayOfMonth()>30) {
				throw new InvalidInputException("invalid date");
			}
		} if(d1.getMonthValue()==2) {
			if(d1.getDayOfMonth()>28) {
				throw new InvalidInputException("invalid date");
					}
				}
	}
	/**
	 *@author Victoria Sanchez
	*@param String date
	*@return List<TOAppointmentCalendarItem>
	*@throws ParseException
	*@throws InvalidInputException
	 */
	public static List<TOAppointmentCalendarItem> getAvailableAppointmentCalendarWeek(String date) throws ParseException, InvalidInputException{
		List<TOAppointmentCalendarItem> t1= getAvailableAppointmentCalendarDay(date);
		SimpleDateFormat s1= new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d1;
		d1=s1.parse(date);
		java.sql.Date sqlDate=new Date(d1.getTime());
		Calendar newC= Calendar.getInstance();
		newC.setTime(s1.parse(date));
		newC.add(Calendar.DAY_OF_MONTH, 1);
		String Date2=s1.format(newC.getTime());
		List<TOAppointmentCalendarItem> t2=getAvailableAppointmentCalendarDay(Date2);

		
		Calendar newC2=Calendar.getInstance();
		newC2.setTime(s1.parse(date));
		newC2.add(Calendar.DAY_OF_MONTH, 2);
		String Date3=s1.format(newC2.getTime()); //day 3
		List<TOAppointmentCalendarItem> t3=getAvailableAppointmentCalendarDay(Date3);
		
		
		
		Calendar newC3=Calendar.getInstance();
		newC2.setTime(s1.parse(date));
		newC2.add(Calendar.DAY_OF_MONTH, 3);
		String Date4=s1.format(newC2.getTime()); 
		List<TOAppointmentCalendarItem> t4=getAvailableAppointmentCalendarDay(Date4);
		
		
		
		Calendar newC4=Calendar.getInstance();
		newC2.setTime(s1.parse(date));
		newC2.add(Calendar.DAY_OF_MONTH, 4);
		String Date5=s1.format(newC2.getTime());  //day5
		List<TOAppointmentCalendarItem> t5=getAvailableAppointmentCalendarDay(Date5);
		
		
		Calendar newC5=Calendar.getInstance();
		newC2.setTime(s1.parse(date));
		newC2.add(Calendar.DAY_OF_MONTH, 5);
		String Date6=s1.format(newC2.getTime()); 
		List<TOAppointmentCalendarItem> t6=getAvailableAppointmentCalendarDay(Date6);
		
		Calendar newC6=Calendar.getInstance();
		newC2.setTime(s1.parse(date));
		newC2.add(Calendar.DAY_OF_MONTH, 6);
		String Date7=s1.format(newC2.getTime());  //day7
		List<TOAppointmentCalendarItem> t7=getAvailableAppointmentCalendarDay(Date7);
		
		
		t1.addAll(t2);
		t1.addAll(t3);
		t1.addAll(t4);
		t1.addAll(t5);
		t1.addAll(t6);
		t1.addAll(t7);
		
		return t1;
			
	}
	/**
	*@author Victoria Sanchez
	*@param String date
	*@return List<TOAppointmentCalendarItem>
	*@throws ParseException
	*@throws InvalidInputException
	*/
	public static List<TOAppointmentCalendarItem> getUnavailableAppointmentCalendarWeek(String date) throws ParseException, InvalidInputException{
		List<TOAppointmentCalendarItem> t1=getUnavailableAppointmentCalendar(date);
		SimpleDateFormat s1= new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d1;
		d1=s1.parse(date);
		java.sql.Date sqlDate=new Date(d1.getTime());
		Calendar newC= Calendar.getInstance();
		newC.setTime(s1.parse(date));
		newC.add(Calendar.DAY_OF_MONTH, 1);
		String Date2=s1.format(newC.getTime());
		List<TOAppointmentCalendarItem> t2=getUnavailableAppointmentCalendar(Date2);
	
		
		Calendar newC2=Calendar.getInstance();
		newC2.setTime(s1.parse(date));
		newC2.add(Calendar.DAY_OF_MONTH, 2);
		String Date3=s1.format(newC2.getTime()); //day 3
		List<TOAppointmentCalendarItem> t3=getUnavailableAppointmentCalendar(Date3);
		
		
		Calendar newC3=Calendar.getInstance();
		newC2.setTime(s1.parse(date));
		newC2.add(Calendar.DAY_OF_MONTH, 3);
		String Date4=s1.format(newC2.getTime()); 
		List<TOAppointmentCalendarItem> t4=getUnavailableAppointmentCalendar(Date4);
		
		
		Calendar newC4=Calendar.getInstance();
		newC2.setTime(s1.parse(date));
		newC2.add(Calendar.DAY_OF_MONTH, 4);
		String Date5=s1.format(newC2.getTime());  //day5
		List<TOAppointmentCalendarItem> t5=getUnavailableAppointmentCalendar(Date5);
		
		Calendar newC5=Calendar.getInstance();
		newC2.setTime(s1.parse(date));
		newC2.add(Calendar.DAY_OF_MONTH, 5);
		String Date6=s1.format(newC2.getTime()); 
		List<TOAppointmentCalendarItem> t6=getUnavailableAppointmentCalendar(Date6);//day6
		
		Calendar newC6=Calendar.getInstance();
		newC2.setTime(s1.parse(date));
		newC2.add(Calendar.DAY_OF_MONTH, 6);
		String Date7=s1.format(newC2.getTime());  //day7
		List<TOAppointmentCalendarItem> t7=getUnavailableAppointmentCalendar(Date7);
		
		t1.addAll(t2);
		t1.addAll(t3);
		t1.addAll(t4);
		t1.addAll(t5);
		t1.addAll(t6);
		t1.addAll(t7);
		
		return t1;
			
	}
	
	
	/**
	 * this method returns the available timeslots for a given day
	 * @author Victoria Sanchez
	 * @param day
	 * @param month
	 * @param year 
	 * @param isWeek
	 * @return appointments 
	 * @throws ParseException 
	 */
	
	public static List<TOAppointmentCalendarItem> getAvailableAppointmentCalendarDay(String date) throws InvalidInputException, ParseException {
		SimpleDateFormat s1= new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d1;
		d1 = s1.parse(date);
		java.sql.Date sqlDate= new Date(d1.getTime());
		LocalDate date1=LocalDate.parse(date);
		checkDate(date1);
		DayOfWeek dayOfWeek=date1.getDayOfWeek();
		FlexiBook flexibook= FlexiBookApplication.getFlexiBook();
		ArrayList<TOAppointmentCalendarItem> calendar= new ArrayList<TOAppointmentCalendarItem>();
		int count=0;
		for(BusinessHour b: flexibook.getBusiness().getBusinessHours()) {
			if(b.getDayOfWeek().toString().equalsIgnoreCase(dayOfWeek.toString())) {
				count++;
			}
		}
		if(count==0) {
			return calendar;
		}
	

		ArrayList<Appointment> DayAppointments= new ArrayList<Appointment>();
		for(Appointment a: flexibook.getAppointments()) {
			if(a.getTimeSlot().getStartDate().equals(sqlDate)) {
				DayAppointments.add(a);
			}
		}
		ArrayList<TimeSlot> holidaySlots= new ArrayList<TimeSlot>();
		for(TimeSlot t: flexibook.getBusiness().getHolidays()) {
			if(t.getStartDate().equals(sqlDate)|| t.getEndDate().equals(sqlDate)) {
				holidaySlots.add(t);
			}
		}
		for(TimeSlot t: flexibook.getBusiness().getVacation()) {
			if(t.getStartDate().equals(sqlDate)|| t.getEndDate().equals(sqlDate)){
				holidaySlots.add(t);
			}
		}
		
		if(DayAppointments.isEmpty()&&holidaySlots.isEmpty()) {
			for(BusinessHour b1: flexibook.getBusiness().getBusinessHours()) {
				if(b1.getDayOfWeek().toString().equalsIgnoreCase(dayOfWeek.toString())) {
					TOAppointmentCalendarItem newT= new TOAppointmentCalendarItem(sqlDate, b1.getStartTime(),b1.getEndTime());
					calendar.add(newT);
					return calendar;
			}
			
			}
		}
		
		sortDailyAppointments(DayAppointments);
		
		for(BusinessHour b1: flexibook.getBusiness().getBusinessHours()) {
			if(b1.getDayOfWeek().toString().equalsIgnoreCase(dayOfWeek.toString())) {
				for(Appointment a: DayAppointments) { //going through appointments
					for(ComboItem s: a.getChosenItems()) {
						if(s.getService().getDowntimeDuration()!=0){
							Calendar newC= Calendar.getInstance();
							newC.setTime(a.getTimeSlot().getStartTime());
							newC.add(Calendar.MINUTE,s.getService().getDowntimeStart());
							Time newT1=(Time) newC.getTime();
							Calendar newCEnd=Calendar.getInstance();
							newCEnd.setTime(a.getTimeSlot().getStartTime());
							newCEnd.add(Calendar.MINUTE, s.getService().getDowntimeStart()+s.getService().getDowntimeDuration());
							Time newT2= (Time) newCEnd.getTime();
							
							TOAppointmentCalendarItem newT=new TOAppointmentCalendarItem(sqlDate,newT1,newT2);
							calendar.add(newT);
						}
					}
					int indexA=DayAppointments.indexOf(a);
					if(indexA==0) {
						if(!a.getTimeSlot().getStartTime().equals(b1.getStartTime())) {
							TOAppointmentCalendarItem newT= new TOAppointmentCalendarItem(sqlDate,b1.getStartTime(),a.getTimeSlot().getStartTime());
							TOAppointmentCalendarItem newT2= new TOAppointmentCalendarItem(sqlDate, a.getTimeSlot().getEndTime(),DayAppointments.get(indexA+1).getTimeSlot().getStartTime());
							calendar.add(newT);
							calendar.add(newT2);
						} else {
							TOAppointmentCalendarItem newT= new TOAppointmentCalendarItem(sqlDate, a.getTimeSlot().getEndTime(),DayAppointments.get(indexA+1).getTimeSlot().getStartTime());
							calendar.add(newT);
						}
					}
					if(indexA+1==DayAppointments.size()) {
						if(a.getTimeSlot().getEndTime().equals(b1.getEndTime())) {
							return calendar;
						} else {
						TOAppointmentCalendarItem newT= new TOAppointmentCalendarItem(sqlDate,a.getTimeSlot().getEndTime(),b1.getEndTime());
						calendar.add(newT);
						}
					}
					else{
						TOAppointmentCalendarItem newT= new TOAppointmentCalendarItem(sqlDate, a.getTimeSlot().getEndTime(),DayAppointments.get(indexA+1).getTimeSlot().getStartTime());
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
	 * @param isWeek
	 * @return List<TOAppointmentCalendarItem>
	 * @throws InvalidInputException
	 * @throws ParseException
	 */
	public static List<TOAppointmentCalendarItem> getUnavailableAppointmentCalendar(String date) throws InvalidInputException, ParseException {
		SimpleDateFormat s1= new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d1;
		d1 = s1.parse(date);
		java.sql.Date sqlDate= new Date(d1.getTime());
		LocalDate date1=LocalDate.parse(date);
		checkDate(date1);
		DayOfWeek dayOfWeek=date1.getDayOfWeek();
		FlexiBook flexibook= FlexiBookApplication.getFlexiBook();
		
		ArrayList<TOAppointmentCalendarItem> calendar= new ArrayList<TOAppointmentCalendarItem>();
		ArrayList<Appointment> DayAppointments= new ArrayList<Appointment>();
		for(Appointment a: flexibook.getAppointments()) {
			if(a.getTimeSlot().getStartDate().equals(sqlDate)) {
				DayAppointments.add(a);
			}
		}
		ArrayList<TimeSlot> holidaySlots= new ArrayList<TimeSlot>();
		for(TimeSlot t: flexibook.getBusiness().getHolidays()) {
			if(t.getStartDate().equals(sqlDate)|| t.getEndDate().equals(sqlDate)) {
				holidaySlots.add(t);
			}
		}
		for(TimeSlot t: flexibook.getBusiness().getVacation()) {
			if(t.getStartDate().equals(sqlDate)|| t.getEndDate().equals(sqlDate)){
				holidaySlots.add(t);
			}
		}
		if(!holidaySlots.isEmpty()) {
			for(BusinessHour b1: flexibook.getBusiness().getBusinessHours()) {
				if(b1.getDayOfWeek().toString().equalsIgnoreCase(dayOfWeek.toString())) {
					TOAppointmentCalendarItem newT= new TOAppointmentCalendarItem(sqlDate, b1.getStartTime(),b1.getEndTime());
					calendar.add(newT);
					return calendar;
				}
			}
		}
		sortDailyAppointments(DayAppointments);
		for(BusinessHour b1: flexibook.getBusiness().getBusinessHours()) {
			if(b1.getDayOfWeek().toString().equalsIgnoreCase(dayOfWeek.toString())) {
				for(Appointment a: DayAppointments) {
					for(ComboItem c: a.getChosenItems()) {
						if(c.getService().getDowntimeDuration()!=0) {
						Calendar newC= Calendar.getInstance();
						newC.setTime(a.getTimeSlot().getStartTime());
						newC.add(Calendar.MINUTE,c.getService().getDowntimeStart());
						Time newT1=(Time) newC.getTime();
						Calendar newCEnd=Calendar.getInstance();
						newCEnd.setTime(a.getTimeSlot().getStartTime());
						newCEnd.add(Calendar.MINUTE, c.getService().getDowntimeStart()+c.getService().getDowntimeDuration());
						Time newT2= (Time) newCEnd.getTime();
						
						TOAppointmentCalendarItem newT= new TOAppointmentCalendarItem(sqlDate,a.getTimeSlot().getStartTime(),newT1);
						TOAppointmentCalendarItem T2= new TOAppointmentCalendarItem(sqlDate,newT2,a.getTimeSlot().getEndTime());
						calendar.add(newT);
						calendar.add(T2);
							
						} else {
						TOAppointmentCalendarItem newT= new TOAppointmentCalendarItem(sqlDate,a.getTimeSlot().getStartTime(),a.getTimeSlot().getEndTime());
						calendar.add(newT);
								}
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
	
	public static List<Appointment> sortDailyAppointments(List<Appointment> list){
		list.sort((Appointment a1, Appointment a2)->a1.getTimeSlot().getStartTime().compareTo(a2.getTimeSlot().getStartTime()));
		return list;
		
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
			/*if (checkIfServiceExists(name, flexibook)) {
				throw new InvalidInputException("Service " + name + " already exists");
			}*/
			
			if (BookableService.hasWithName(name)) {
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
			
			if (!(username.equals("owner"))) {
				throw new InvalidInputException("You are not authorized to perform this operation");
			}
			
			if(BookableService.hasWithName(name) && (!(currentName.equals(name)))) {
			throw new InvalidInputException("Service " + name + " already exists");
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
	public static void deleteService(String username, String name) throws InvalidInputException {
		FlexiBook flexibook = FlexiBookApplication.getFlexiBook();
		
		try {
			Service service = (Service) BookableService.getWithName(name);
			
			List<Appointment> appointments = flexibook.getAppointments();
			for(Appointment a:appointments){
				if(a.getBookableService().equals(service)){
					if(cleanDate(a.getTimeSlot().getStartDate()).compareTo(SystemTime.getDate()) >= 0){
						throw new InvalidInputException("The service contains future appointments");
					}
				}

			}
			
			if (!(username.equals("owner"))) {
				throw new InvalidInputException("You are not authorized to perform this operation");
			}
			
			service.delete();
			
			List<BookableService> bookableServices = flexibook.getBookableServices();
			for (BookableService b:bookableServices) {
				if (b instanceof ServiceCombo) {
					
					ServiceCombo combo = (ServiceCombo) b;
					
					if (combo.getMainService().getService().getName().equals(name)) {
						combo.delete();
						//deleteServiceCombo(username, combo);
					}
					
					else {
						List<ComboItem> items = combo.getServices();
						for (ComboItem i:items) {
							if (i.getService().getName().equals(name)) {
								i.delete();
							}
						}
					}
				}
			}
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
	private static void checkServiceParameters(String name, int duration, int downtimeDuration, int downtimeStart, FlexiBook flexibook) throws InvalidInputException {
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
	
	public static void setUpBusinessInfo(String name, String address, String phoneNumber, String email, String dayOfWeek, String bHstart, String bHend, String startT, String endT, String startD, String endD, Boolean addB, Boolean addBusinessH, Boolean addV, Boolean addH) throws InvalidInputException{
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();

		if(!(FlexiBookApplication.getUser() instanceof Owner)){
			throw new InvalidInputException("No permission to set up business information");
		}
		if(!(email.matches("\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b"))){
			throw new  InvalidInputException("Invalid email");
		}

		if(addB) {

			Business newBusiness = new Business(name, address, phoneNumber, email, flexiBook);
			flexiBook.setBusiness(newBusiness);
		}
		LocalTime bhStart = LocalTime.parse(bHstart);
		LocalTime bhEnd = LocalTime.parse(bHend);


		if (!(bhStart.isBefore(bhEnd)) && addBusinessH) {
			throw new InvalidInputException("Start time must be before end time");
		}

		for(int i = 0; i < flexiBook.getBusiness().numberOfBusinessHours(); i++){
			BusinessHour a = flexiBook.getBusiness().getBusinessHour(i);
			if(addBusinessH && a.getDayOfWeek().toString().equals(dayOfWeek) && a.getStartTime().toLocalTime().isBefore(bhEnd) &&  bhStart.isBefore(a.getEndTime().toLocalTime())){
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



		for(TimeSlot a : flexiBook.getBusiness().getHolidays()){
			LocalTime aStartTime = a.getStartTime().toLocalTime();
			LocalTime aEndTime = a.getEndTime().toLocalTime();
			LocalDate aStartDate = a.getStartDate().toLocalDate();
			LocalDate aEndDate = a.getEndDate().toLocalDate();
			LocalDateTime aStart = LocalDateTime.of(aStartDate, aStartTime);
			LocalDateTime aEnd = LocalDateTime.of(aEndDate, aEndTime);
			if(aStart.isBefore(hEnd) && hStart.isBefore(aEnd) && addV){
				throw new InvalidInputException("Holiday and vacation times cannot overlap ");
			}
			if (aStart.isBefore(hEnd) && hStart.isBefore(aEnd) && addH) {
				throw new InvalidInputException("Holiday times cannot overlap ");
			}
		}

		for(TimeSlot a : flexiBook.getBusiness().getVacation()) {
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
		if(hStart.isBefore(aNow) && addV){
			throw new InvalidInputException("Holiday cannot start in past");
		}
		if(hStart.isBefore(aNow) && addH){
			throw new InvalidInputException("Vacation cannot start in past");
		}


		if(addBusinessH){
			Time a = Time.valueOf(bHstart);
			Time b = Time.valueOf(bHend);
			BusinessHour.DayOfWeek day = null;
			if(dayOfWeek.equals("Monday")){
				day = BusinessHour.DayOfWeek.Monday;
			}else if(dayOfWeek.equals("Tuesday")){
				day = BusinessHour.DayOfWeek.Tuesday;
			}else if(dayOfWeek.equals("Wednesday")){
				day = BusinessHour.DayOfWeek.Wednesday;
			}else if(dayOfWeek.equals("Thursday")){
				day = BusinessHour.DayOfWeek.Thursday;
			}else if(dayOfWeek.equals("Friday")){
				day = BusinessHour.DayOfWeek.Friday;
			}else if(dayOfWeek.equals("Saturday")){
				day = BusinessHour.DayOfWeek.Saturday;
			}else if(dayOfWeek.equals("Sunday")){
				day = BusinessHour.DayOfWeek.Sunday;
			}
			BusinessHour newBH = new BusinessHour(day, a, b, flexiBook);


			flexiBook.getBusiness().addBusinessHour(newBH);
		}
		if(addH){
			Time a = Time.valueOf(hStartTime);
			Time b = Time.valueOf(hEndTime);
			Date c = Date.valueOf(hStartDate);
			Date d = Date.valueOf(hEndDate);
			TimeSlot holiday = new TimeSlot(c, a, d, b, flexiBook);
			flexiBook.getBusiness().addHoliday(holiday);
		}
		if(addV){
			Time a = Time.valueOf(hStartTime);
			Time b = Time.valueOf(hEndTime);
			Date c = Date.valueOf(hStartDate);
			Date d = Date.valueOf(hEndDate);
			TimeSlot vacation = new TimeSlot(c, a, d, b, flexiBook);
			flexiBook.getBusiness().addVacation(vacation);
		}

	}

	public static void updateBusinessInfo(String name, String address, String phoneNumber, String email, String startBH, String endBH, String dayOfWeek, String oldBHStart, String oldBHDay, String startD, String endD, String startT, String endT, String oldStartD, String oldStartT, Boolean basic, Boolean addBusinessH, Boolean removeBH, Boolean updateBH, Boolean addV, Boolean removeV, Boolean updateV, Boolean addH, Boolean removeH, Boolean updateH) throws InvalidInputException{
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
		Business business = flexiBook.getBusiness();
		if(!(FlexiBookApplication.getUser() instanceof Owner)){
			throw new InvalidInputException("No permission to set up business information");
		}
		if(basic){
			if(!(email.matches("\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b"))){
				throw new  InvalidInputException("Invalid email");
			}else{
				business.setName(name);
				business.setEmail(email);
				business.setAddress(address);
				business.setPhoneNumber(phoneNumber);
			}
		}
		LocalTime bhStart = LocalTime.parse(startBH);
		LocalTime bhEnd = LocalTime.parse(endBH);
		if(addBusinessH || updateBH){

			if (!(bhStart.isBefore(bhEnd))) {
				throw new InvalidInputException("Start time must be before end time");
			}
			for(BusinessHour a : business.getBusinessHours()){
				if(a.getDayOfWeek().toString().equals(oldBHDay) && a.getStartTime().toString().equals(oldBHStart)){
					continue;
				}
				if(addBusinessH && a.getDayOfWeek().toString().equals(dayOfWeek) && a.getStartTime().toLocalTime().isBefore(bhEnd) &&  bhStart.isBefore(a.getEndTime().toLocalTime())){
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
		if(addH || addV || updateH || updateV){

			if ((addH || updateH || addV || updateV) && !(hStart.isBefore(hEnd))) {
				throw new InvalidInputException("Start time must be before end time");
			}

			for(TimeSlot a : business.getHolidays()){
				if(a.getStartDate().toString().equals(oldStartD) && a.getStartTime().toString().equals(oldStartT)){
					continue;
				}
				LocalTime aStartTime = a.getStartTime().toLocalTime();
				LocalTime aEndTime = a.getEndTime().toLocalTime();
				LocalDate aStartDate = a.getStartDate().toLocalDate();
				LocalDate aEndDate = a.getEndDate().toLocalDate();
				LocalDateTime aStart = LocalDateTime.of(aStartDate, aStartTime);
				LocalDateTime aEnd = LocalDateTime.of(aEndDate, aEndTime);

				if(aStart.isBefore(hEnd) && hStart.isBefore(aEnd) && (addV || updateV)){
					throw new InvalidInputException("Holiday and vacation times cannot overlap ");
				}
				if (aStart.isBefore(hEnd) && hStart.isBefore(aEnd) && (addH || updateH)){
					throw new InvalidInputException("Holiday times cannot overlap ");
				}
			}
			for(TimeSlot a : business.getVacation()) {
				if(a.getStartDate().toString().equals(oldStartD) && a.getStartTime().toString().equals(oldStartT)){
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
			if(hStart.isBefore(aNow) && (addV || updateV)){
				throw new InvalidInputException("Holiday cannot start in past");
			}
			if(hStart.isBefore(aNow) && (addH || updateH)){
				throw new InvalidInputException("Vacation cannot start in past");
			}

		}
		if(addBusinessH){
			Time x = Time.valueOf(bhStart);
			Time y = Time.valueOf(bhEnd);
			BusinessHour.DayOfWeek day = null;
			if(dayOfWeek.equals("Monday")){
				day = BusinessHour.DayOfWeek.Monday;
			}else if(dayOfWeek.equals("Tuesday")){
				day = BusinessHour.DayOfWeek.Tuesday;
			}else if(dayOfWeek.equals("Wednesday")){
				day = BusinessHour.DayOfWeek.Wednesday;
			}else if(dayOfWeek.equals("Thursday")){
				day = BusinessHour.DayOfWeek.Thursday;
			}else if(dayOfWeek.equals("Friday")){
				day = BusinessHour.DayOfWeek.Friday;
			}else if(dayOfWeek.equals("Saturday")){
				day = BusinessHour.DayOfWeek.Saturday;
			}else if(dayOfWeek.equals("Sunday")){
				day = BusinessHour.DayOfWeek.Sunday;
			}

			BusinessHour bh = new BusinessHour(day,x,y,flexiBook);
		}
		if(updateBH){
			for(BusinessHour a : business.getBusinessHours()) {
				if (a.getDayOfWeek().toString().equals(oldBHDay) && a.getStartTime().toString().equals(oldBHStart)) {
					business.removeBusinessHour(a);
					Time x = Time.valueOf(bhStart);
					Time y = Time.valueOf(bhEnd);
					BusinessHour.DayOfWeek day = null;
					if(dayOfWeek.equals("Monday")){
						day = BusinessHour.DayOfWeek.Monday;
					}else if(dayOfWeek.equals("Tuesday")){
						day = BusinessHour.DayOfWeek.Tuesday;
					}else if(dayOfWeek.equals("Wednesday")){
						day = BusinessHour.DayOfWeek.Wednesday;
					}else if(dayOfWeek.equals("Thursday")){
						day = BusinessHour.DayOfWeek.Thursday;
					}else if(dayOfWeek.equals("Friday")){
						day = BusinessHour.DayOfWeek.Friday;
					}else if(dayOfWeek.equals("Saturday")){
						day = BusinessHour.DayOfWeek.Saturday;
					}else if(dayOfWeek.equals("Sunday")){
						day = BusinessHour.DayOfWeek.Sunday;
					}
					BusinessHour bh = new BusinessHour(day,x,y,flexiBook);
				}
			}
		}
		if(removeBH){
			for(BusinessHour a : business.getBusinessHours()) {
				if (a.getDayOfWeek().toString().equals(oldBHDay) && a.getStartTime().toString().equals(oldBHStart)) {
					business.removeBusinessHour(a);
				}
			}
		}
		if(addH){
			Time a = Time.valueOf(hStartTime);
			Time b = Time.valueOf(hEndTime);
			Date c = Date.valueOf(hStartDate);
			Date d = Date.valueOf(hEndDate);
			TimeSlot holiday = new TimeSlot(c, a, d, b, flexiBook);
			business.addHoliday(holiday);
		}
		if(addV){
			Time a = Time.valueOf(hStartTime);
			Time b = Time.valueOf(hEndTime);
			Date c = Date.valueOf(hStartDate);
			Date d = Date.valueOf(hEndDate);
			TimeSlot vacation = new TimeSlot(c, a, d, b, flexiBook);
			business.addVacation(vacation);
		}

		if(updateH){
			for(TimeSlot a : business.getHolidays()) {
				if (a.getStartDate().toString().equals(oldStartD) && a.getStartTime().toString().equals(oldStartT)) {
					business.removeHoliday(a);
					Time x = Time.valueOf(hStartTime);
					Time b = Time.valueOf(hEndTime);
					Date c = Date.valueOf(hStartDate);
					Date d = Date.valueOf(hEndDate);
					TimeSlot holiday = new TimeSlot(c, x, d, b, flexiBook);
					business.addHoliday(holiday);
				}
			}
		}
		if(updateV){
			for(TimeSlot a : business.getVacation()){
				if(a.getStartDate().toString().equals(oldStartD) && a.getStartTime().toString().equals(oldStartT)){
					business.removeVacation(a);
					Time x = Time.valueOf(hStartTime);
					Time b = Time.valueOf(hEndTime);
					Date c = Date.valueOf(hStartDate);
					Date d = Date.valueOf(hEndDate);
					TimeSlot vacation = new TimeSlot(c, x, d, b, flexiBook);
					business.addHoliday(vacation);
				}
			}
		}
		if(removeH){
			for(TimeSlot a : business.getHolidays()){
				if(a.getStartDate().toString().equals(oldStartD) && a.getStartTime().toString().equals(oldStartT)){
					business.removeHoliday(a);
				}
			}
		}

		if(removeV){
			for(TimeSlot a : business.getVacation()){
				if(a.getStartDate().toString().equals(oldStartD) && a.getStartTime().toString().equals(oldStartT)){
					business.removeVacation(a);
				}
			}
		}




	}
}






