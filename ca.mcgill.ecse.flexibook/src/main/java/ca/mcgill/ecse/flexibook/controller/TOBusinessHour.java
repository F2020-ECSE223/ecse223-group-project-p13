/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.flexibook.controller;
import java.sql.Time;
// line 25 "../../../../../TransferObjects.ump"
public class TOBusinessHour
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //TOService Attributes
    private String day;
    private Time st;
    private Time et;


    //------------------------
    // CONSTRUCTOR
    //------------------------

    public TOBusinessHour(String dayA, Time stA, Time etA)
    {
        day = dayA;
        st = stA;
        et = etA;
    }

    //------------------------
    // INTERFACE
    //------------------------

   /* public boolean setName(String aName)
    {
        boolean wasSet = false;
        name = aName;
        wasSet = true;
        return wasSet;
    }

    public boolean setDuration(int aDuration)
    {
        boolean wasSet = false;
        duration = aDuration;
        wasSet = true;
        return wasSet;
    }

    public boolean setDowntimeDuration(int aDowntimeDuration)
    {
        boolean wasSet = false;
        downtimeDuration = aDowntimeDuration;
        wasSet = true;
        return wasSet;
    }

    public boolean setDowntimeStart(int aDowntimeStart)
    {
        boolean wasSet = false;
        downtimeStart = aDowntimeStart;
        wasSet = true;
        return wasSet;
    }
*/
    public String getDay()
    {
        return day;
    }

    public Time getStartTime()
    {
        return st;
    }

    public Time getEndTime()
    {
        return et;
    }



    public void delete()
    {}


  /*  public String toString()
    {
        return super.toString() + "["+
                "name" + ":" + getName()+ "," +
                "duration" + ":" + getDuration()+ "," +
                "downtimeDuration" + ":" + getDowntimeDuration()+ "," +
                "downtimeStart" + ":" + getDowntimeStart()+ "]";
    }*/
}
