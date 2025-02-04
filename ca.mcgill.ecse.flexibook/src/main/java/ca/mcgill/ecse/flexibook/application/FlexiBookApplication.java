/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ca.mcgill.ecse.flexibook.application;

import ca.mcgill.ecse.flexibook.model.FlexiBook;
import ca.mcgill.ecse.flexibook.model.User;
import ca.mcgill.ecse.flexibook.persistence.FlexiBookPersistence;
import ca.mcgill.ecse.flexibook.view.FlexiBookPage;
import javafx.application.Application;

public class FlexiBookApplication {
	private static FlexiBook flexibook;
	private static User currentUser;
    public String getGreeting() {
        return "Hello world.";
    }
	
    public static void main(String[] args) {
        // start UI
        Application.launch(FlexiBookPage.class,args);
    }
    	

    public static FlexiBook getFlexiBook() {
    	if (flexibook == null) {
    		flexibook = FlexiBookPersistence.load();
    	}
     	return flexibook;
    }
    public static User getUser(){
        return currentUser;
    }
	public static void setCurrentUser(User user) {
    	currentUser=user;
	}
    
    	
}
