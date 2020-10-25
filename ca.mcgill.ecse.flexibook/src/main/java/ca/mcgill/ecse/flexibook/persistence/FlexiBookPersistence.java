package ca.mcgill.ecse.flexibook.persistence;

import  ca.mcgill.ecse.flexibook.model.FlexiBook;

public class FlexiBookPersistence {

    private static String filename = "data.flexibook";
    public static void setFilename(String filename) {
        FlexiBookPersistence.filename = filename;
    }
    public static void save(FlexiBook flexiBook){
        PersistenceObjectStream.serialize(flexiBook);
    }
    public static FlexiBook load(){
        PersistenceObjectStream.setFilename(filename);
        FlexiBook flexiBook = (FlexiBook) PersistenceObjectStream.deserialize();
        if(flexiBook == null){
            flexiBook = new FlexiBook();
        }
        else{
            //flexiBook.reinitialize();
        }
        return flexiBook;
    }
}
