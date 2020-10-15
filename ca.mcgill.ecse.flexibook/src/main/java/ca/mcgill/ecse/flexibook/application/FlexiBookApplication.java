/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ca.mcgill.ecse.flexibook.application;

import ca.mcgill.ecse.flexibook.model.FlexiBook;

public class FlexiBookApplication {
	private static FlexiBook flexibook ;
	
    public static void main(String[] args) {
        System.out.println(new FlexiBookApplication().getGreeting());
    }
    	
    	
    public static FlexiBook getFlexiBook() {
    	if (flexibook == null) {
    		// for now, we are just creating an empty BTMS
    		flexibook = new flexibook();
    	}
     	return flexibook;
    }
    	
}
