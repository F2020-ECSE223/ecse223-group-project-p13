package ca.mcgill.ecse.flexibook.controller;

public class DisplayedBusinessInfo {
    String name;
    String address;
    String phoneNumber;
    String email;
    public DisplayedBusinessInfo(String n,String a, String pn, String e){
        this.name = n;
        this.address = a;
        this.phoneNumber = pn;
        this.email = e;
    }

    public String getName(){
        return name;
    }
    public String getAddress(){
        return address;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getEmail(){
        return getEmail();
    }
}
