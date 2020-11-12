package ca.mcgill.ecse.flexibook.view;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
import ca.mcgill.ecse.flexibook.controller.TOAppointmentCalendarItem;
import com.jfoenix.controls.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.kordamp.ikonli.javafx.FontIcon;

//import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;

public class FlexiBookPage extends Application {
    private String error;
    private Color[] colors = {new Color(.886,.941,.976,1.0),new Color(.690,.867,.882,1.0),
            new Color(.157,.435,.706,1.0),Color.WHITE,new Color(.875,.298,.451,1.0)};

    JFXButton startButton;
    //private CardLayout mainLayout;
    private Stage mainStage;
    Scene ownerHomeScreen;
    HBox ownerAppointmentCalendar;
    TextField textUserName;
    PasswordField pf;
    JFXTextField updateUsername;
    JFXPasswordField updatePassword;
    TextField textUserName1;
    PasswordField pf1;
    /*private Scene appointmentCalendar = new Scene(new HBox(),1440,810,colors[3]);
    private Scene businessInfo;
    private Scene Services;
    private Scene Account;
    private Scene makeAppointment;*/
    ArrayList<CalendarEntry> listDays = new ArrayList<>();
    LocalDate renderDate;
	private HBox change2;
	private HBox changeAcc;



    public void start(Stage s){
        mainStage = s;
        mainStage.setTitle("FlexiBook Application");
        initComponents();
        refreshData();
        mainStage.show();
    }
    private void initComponents(){
        /*startButton = new JFXButton("Start Appointment");
        startButton.getStyleClass().add("button-raised");
        startButton.setOnAction(this::startAppointmentEvent);
        Region padderRegion = new Region();
        padderRegion.prefWidthProperty().setValue(250);*/
        renderDate = LocalDate.now();
        //Main Screen
        BorderPane mainScreenBorderPane = new BorderPane();
        Label welcome = new Label("Welcome, User");
        HBox top = new HBox();
        mainScreenBorderPane.setTop(top);
        top.setAlignment(Pos.BASELINE_RIGHT);
        top.getChildren().add(welcome);
        welcome.getStyleClass().add("user-text");
        
        HBox bottom = new HBox();
        mainScreenBorderPane.setBottom(bottom);
        bottom.setAlignment(Pos.BASELINE_RIGHT);

        VBox center = new VBox(70);
        mainScreenBorderPane.setCenter(center);
        center.setAlignment(Pos.TOP_CENTER);
        Image image = new Image("/img/newLogo.png");
        ImageView view = new ImageView(image);
        view.setPreserveRatio(true);
        view.setFitHeight(200);
        center.getChildren().add(view);

        HBox buttons = new HBox(50);

        buttons.setAlignment(Pos.CENTER);

        FontIcon appointmentIcon = new FontIcon("eli-calendar");
        FontIcon accountIcon = new FontIcon("dashicons-businessperson");
        FontIcon businessIcon = new FontIcon("icm-briefcase");
        FontIcon serviceIcon = new FontIcon("ion4-ios-list-box");
        FontIcon loginIcon = new FontIcon("dashicons-admin-users");
        FontIcon logoutIcon= new FontIcon("dashicons-exit");
        FontIcon signUp= new FontIcon("dashicons-edit");
        FontIcon delete = new FontIcon("dashicons-trash");

        appointmentIcon.getStyleClass().add("icon");
        accountIcon.getStyleClass().add("icon");
        businessIcon.getStyleClass().add("icon");
        serviceIcon.getStyleClass().add("icon");
        loginIcon.getStyleClass().add("icon");
        logoutIcon.getStyleClass().add("icon");
        signUp.getStyleClass().add("icon");
        delete.getStyleClass().add("icon");

        JFXButton logoutButton = new JFXButton("LogOut", logoutIcon);
        logoutButton.setContentDisplay(ContentDisplay.BOTTOM);
        logoutButton.getStyleClass().add("main-menu-button");
        logoutButton.setOnAction(e->logout());
        bottom.getChildren().add(logoutButton);
        
        JFXButton appointmentButton = new JFXButton("Appointments",appointmentIcon);
        appointmentButton.setContentDisplay(ContentDisplay.TOP);
        appointmentButton.setOnAction(e->switchToAppointment());
        appointmentButton.getStyleClass().add("main-menu-button");
        buttons.getChildren().add(appointmentButton);
        
        JFXButton loginButton = new JFXButton("LogIn", loginIcon);
        loginButton.setContentDisplay(ContentDisplay.TOP);
        loginButton.setOnAction(e->switchToHomeScreen());
        loginButton.getStyleClass().add("main-menu-button");
        buttons.getChildren().add(loginButton);

        JFXButton accountButton = new JFXButton("Account",accountIcon);
        accountButton.setContentDisplay(ContentDisplay.TOP);
        accountButton.setOnAction(e->switchToAccount());
        accountButton.getStyleClass().add("main-menu-button");
        buttons.getChildren().add(accountButton);

        JFXButton businessButton = new JFXButton("Business",businessIcon);
        businessButton.setContentDisplay(ContentDisplay.TOP);
        businessButton.setOnAction(e->switchToBusiness());
        businessButton.getStyleClass().add("main-menu-button");
        buttons.getChildren().add(businessButton);

        JFXButton serviceButton = new JFXButton("Service",serviceIcon);
        serviceButton.setContentDisplay(ContentDisplay.TOP);
        serviceButton.setOnAction(e->switchToServices());
        serviceButton.getStyleClass().add("main-menu-button");
        buttons.getChildren().add(serviceButton);


        center.getChildren().add(buttons);


        //Appointment Calendar
        ownerAppointmentCalendar = new HBox();
        ownerAppointmentCalendar.getChildren().add(setCalendar());
        ownerAppointmentCalendar.setStyle("-fx-background-color: #B0DDE4;");

        //Box hBox = new HBox(50);
        /*//hBox.getChildren().add(padderRegion);
        hBox.getChildren().add(startButton);
        //hBox.getChildren().add(vBox);
        mainScreenBorderPane.setTop(hBox);
        mainScreenBorderPane.setCenter(vBox);

        JFXTextField username = new JFXTextField();
        username.setLabelFloat(true);
        username.setPromptText("Username");
        vBox.getChildren().add(username);

        JFXPasswordField password = new JFXPasswordField();
        password.setLabelFloat(true);
        password.setPromptText("Password");
        vBox.getChildren().add(password);*/

        /*HBox tilePane = new HBox();
        mainScreenBorderPane.setBottom(tilePane);
        JFXDatePicker datePicker = new JFXDatePicker();
        datePicker.setOnAction(ActionEvent-> {
            LocalDate date = datePicker.getValue();
            System.out.println("Selected date: " + date);

        });
        datePicker.setDefaultColor(Color.valueOf("#286FB4"));
        tilePane.getChildren().add(datePicker);

        JFXComboBox<Label> jfxCombo = new JFXComboBox<Label>();
        jfxCombo.getItems().add(new Label("service 1"));
        jfxCombo.getItems().add(new Label("service 2"));
        jfxCombo.getItems().add(new Label("service 3"));
        jfxCombo.getItems().add(new Label("service 4"));
        jfxCombo.setPromptText("Select Service");
        mainScreenBorderPane.setRight(jfxCombo);*/
        change2= new HBox();
        change2.setPadding(new Insets(200,200,200,200));
        change2.setStyle("-fx-background-color: #B0DDE4");
        GridPane gridP= new GridPane();
        gridP.setHgap(100);
        gridP.setVgap(100);
        Label lblUserName = new Label("Username");
        textUserName= new TextField();
        Label lblPassword= new Label("Password");
        pf=  new PasswordField();
        JFXButton btonLogin= new JFXButton("Login",loginIcon);
        btonLogin.setOnAction(e->login());
        final Label lblMessage= new Label();

        GridPane temp= new GridPane();
        temp.setAlignment(Pos.CENTER);
        temp.setPadding(new Insets(100,100,100,100));

        gridP.add(lblUserName,0,0);
        gridP.add(textUserName,1,0);
        gridP.add(lblPassword,0,1);
        gridP.add(pf,1,1);
        gridP.add(btonLogin, 1,2 );
        gridP.add(lblMessage,1,2);
        gridP.setAlignment(Pos.CENTER_LEFT);

        GridPane gridP2= new GridPane();
        gridP2.setVgap(100);
        gridP2.setHgap(100);
        Label lblUserName1 = new Label("Enter a Username");
        textUserName1= new TextField();
        Label lblPassword1= new Label("Enter a Password");
        pf1=  new PasswordField();
        JFXButton btonLogin1= new JFXButton("SignUp",signUp);
        btonLogin1.setOnAction(e->signUp());
        final Label lblMessage1= new Label();
        gridP2.add(lblUserName1,0,0);
        gridP2.add(textUserName1,1,0);
        gridP2.add(lblPassword1,0,1);
        gridP2.add(pf1,1,1);
        gridP2.add(btonLogin1, 1,2 );
        gridP2.add(lblMessage1,1,2);
        gridP2.setAlignment(Pos.CENTER_RIGHT);

        change2.getChildren().add(gridP);
        change2.getChildren().add(temp);
        change2.getChildren().add(gridP2);

        ownerHomeScreen = new Scene(mainScreenBorderPane,1440,810,colors[3]);
        mainScreenBorderPane.setStyle("-fx-background-color: #B0DDE4;");
        mainStage.setScene(ownerHomeScreen);
        ownerHomeScreen.getStylesheets().add(FlexiBookPage.class.getResource("/css/main.css").toExternalForm());
        mainScreenBorderPane.requestFocus();
        
        
        //Account
        changeAcc = new HBox();
        changeAcc.setPadding(new Insets(200,200,200,200));
        changeAcc.setStyle("-fx-background-color: #B0DDE4;");
        GridPane pane= new GridPane();
        pane.setHgap(100);
        pane.setVgap(100);
        JFXButton updateButton = new JFXButton("Update Account",signUp);
        updateButton.setOnAction(e->updateAcc());
        JFXButton deleteAcc = new JFXButton("Delete Account", delete);
        deleteAcc.setOnAction(e->deleteAcc());
        
        Label newUsername = new Label("Enter your new username!");
        updateUsername = new JFXTextField(); 
        changeAcc.getChildren().add(updateUsername);
        
        Label newPassword = new Label("Enter your new password!");
        updatePassword = new JFXPasswordField();
        changeAcc.getChildren().add(updatePassword);
       

        pane.add(newUsername,0,0);
        pane.add(updateUsername,1,0);
        pane.add(newPassword,0,1);
        pane.add(updatePassword,1,1);
        pane.add(updateButton, 1, 2);
        pane.add(deleteAcc, 2, 2);
        pane.setAlignment(Pos.CENTER_LEFT);
        
        changeAcc.getChildren().add(pane);
        
        
        
    }
    
    private void signUp() {
    	
    	try{
        	String username = textUserName1.getText();
        	String password = pf1.getText();
            FlexiBookController.customerSignUp(username, password);
        }
        catch(Exception e){
            e.getMessage();
        }
    	
    }
    
    private void updateAcc() {
    	
        try{
        	String username = FlexiBookApplication.getUser().getUsername();
        	String newUsername = updateUsername.getText();
        	String newPassword = updatePassword.getText();
            FlexiBookController.updateAccount(username, newUsername, newPassword);
        }
        catch(Exception e){
            e.getMessage();
        }
    	
    }
    
    private void deleteAcc(){
    	
        try{
        	String username = FlexiBookApplication.getUser().getUsername();
            FlexiBookController.deleteCustomerAccount(username);
        }
        catch(Exception e){
            e.getMessage();
        }
    	
    }
    
    private void switchToAccount(){
    	
    	ownerHomeScreen.setRoot(changeAcc);
    	
    }
    
    private void logout() {
        try{
            FlexiBookController.logout();
            ownerHomeScreen.setRoot(change2);
        }
        catch(Exception e){
            e.getMessage();
        }
    }
     private void login() {
        try{
            FlexiBookController.login(textUserName.getText(),pf.getText());
            mainStage.setScene(ownerHomeScreen);
        }
        catch(InvalidInputException e){
            e.getMessage();
        }
    }

    private void refreshData(){

    }
    private HBox setCalendar(){
        HBox  calendar =new HBox();

        VBox months = new VBox(20);
        months.setAlignment(Pos.CENTER);
        months.setStyle("-fx-background-color: #E2F0F9");
        months.setPadding(new Insets(20));
        calendar.getChildren().add(months);

        JFXButton januaryButton = new JFXButton("January");
        januaryButton.setOnAction(this::switchMonth);
        januaryButton.getStyleClass().add("calendar-button");
        months.getChildren().add(januaryButton);
        JFXButton februaryButton = new JFXButton("February");
        februaryButton.setOnAction(this::switchMonth);
        februaryButton.getStyleClass().add("calendar-button");
        months.getChildren().add(februaryButton);
        JFXButton marchButton = new JFXButton("March");
        marchButton.setOnAction(this::switchMonth);
        marchButton.getStyleClass().add("calendar-button");
        months.getChildren().add(marchButton);
        JFXButton aprilButton = new JFXButton("April");
        aprilButton.setOnAction(this::switchMonth);
        aprilButton.getStyleClass().add("calendar-button");
        months.getChildren().add(aprilButton);
        JFXButton mayButton = new JFXButton("May");
        mayButton.setOnAction(this::switchMonth);
        mayButton.getStyleClass().add("calendar-button");
        months.getChildren().add(mayButton);
        JFXButton juneButton = new JFXButton("June");
        juneButton.setOnAction(this::switchMonth);
        juneButton.getStyleClass().add("calendar-button");
        months.getChildren().add(juneButton);
        JFXButton julyButton = new JFXButton("July");
        julyButton.setOnAction(this::switchMonth);
        julyButton.getStyleClass().add("calendar-button");
        months.getChildren().add(julyButton);
        JFXButton augustButton = new JFXButton("August");
        augustButton.setOnAction(this::switchMonth);
        augustButton.getStyleClass().add("calendar-button");
        months.getChildren().add(augustButton);
        JFXButton septemberButton = new JFXButton("September");
        septemberButton.setOnAction(this::switchMonth);
        septemberButton.getStyleClass().add("calendar-button");
        months.getChildren().add(septemberButton);
        JFXButton octoberButton = new JFXButton("October");
        octoberButton.setOnAction(this::switchMonth);
        octoberButton.getStyleClass().add("calendar-button");
        months.getChildren().add(octoberButton);
        JFXButton novemberButton = new JFXButton("November");
        novemberButton.setOnAction(this::switchMonth);
        novemberButton.getStyleClass().add("calendar-button");
        months.getChildren().add(novemberButton);
        JFXButton decemberButton = new JFXButton("December");
        decemberButton.setOnAction(this::switchMonth);
        decemberButton.getStyleClass().add("calendar-button");
        months.getChildren().add(decemberButton);

        VBox calendarMain = new VBox();
        calendar.getChildren().add(calendarMain);
        HBox calendarTop = new HBox();
        calendarTop.setStyle("-fx-background-color: #E2F0F9");
        HBox.setHgrow(calendarTop, Priority.ALWAYS);

        Label calendarMonth = new Label(renderDate.getMonth().toString());
        calendarMain.setPrefSize(200,200);
        calendarMonth.getStyleClass().add("user-text");
        calendarTop.getChildren().add(calendarMonth);

        Region middle = new Region();
        middle.setMaxWidth(Double.MAX_VALUE);
        calendarTop.getChildren().add(middle);

        Label calendarYear = new Label(String.valueOf(LocalDate.now().getYear()));
        calendarYear.setPrefSize(200,200);
        calendarYear.getStyleClass().add("user-text");
        calendarTop.getChildren().add(calendarYear);



        calendarMain.getChildren().add(calendarTop);


        HBox calendarDays = new HBox();
        calendarDays.setAlignment(Pos.BASELINE_RIGHT);
        Label sundayLabel = new Label("Sun");
        sundayLabel.getStyleClass().add("user-text");
        calendarDays.getChildren().add(sundayLabel);
        Label mondayLabel = new Label("Mon");
        mondayLabel.getStyleClass().add("user-text");
        calendarDays.getChildren().add(mondayLabel);
        Label tuesdayLabel = new Label("Tue");
        tuesdayLabel.getStyleClass().add("user-text");
        calendarDays.getChildren().add(tuesdayLabel);
        Label wednesdayLabel = new Label("Wed");
        wednesdayLabel.getStyleClass().add("user-text");
        calendarDays.getChildren().add(wednesdayLabel);
        Label thursdayLabel = new Label("Thu");
        thursdayLabel.getStyleClass().add("user-text");
        calendarDays.getChildren().add(thursdayLabel);
        Label fridayLabel = new Label("Fri");
        fridayLabel.getStyleClass().add("user-text");
        calendarDays.getChildren().add(fridayLabel);
        Label saturdayLabel = new Label("Sat");
        saturdayLabel.getStyleClass().add("user-text");
        calendarDays.getChildren().add(saturdayLabel);
        calendarMain.getChildren().add(calendarDays);

        calendarMain.setAlignment(Pos.CENTER);
        GridPane days = new GridPane();
        for(int i =0; i<6;i++){
            for(int j = 0; j< 7; j++){
                CalendarEntry calendarEntry = new CalendarEntry("1");
                calendarEntry.setDate(LocalDate.now());
                calendarEntry.setPrefSize(50,50);
                calendarEntry.setMinWidth(100);
                calendarEntry.setMinHeight(100);
                calendarEntry.setStyle("-fx-background-color: #FFFFFF");
                calendarEntry.getStyleClass().add("calendar-cell");
                calendarEntry.setAlignment(Pos.TOP_LEFT);
                listDays.add(calendarEntry);
                days.add(calendarEntry,j,i);
            }
        }
        calendarMain.getChildren().add(days);
    return calendar;
    }
    private void startAppointmentEvent(ActionEvent event){
        error = null;
        startButton.setText("End Appointment");

        try{
            FlexiBookController.startAppointment(new TOAppointmentCalendarItem("appointment",new Date(2135435),new Time(23435435),new Time(23435435),true));
        }
        catch (InvalidInputException e){
            error = e.getMessage();
        }
    }
    private void endAppointmentEvent(ActionEvent event){
        error = null;
        //mainLayout.show(main,"appointment");
        //pack();

        try{
            FlexiBookController.startAppointment(new TOAppointmentCalendarItem("appointment",new Date(2135435),new Time(23435435),new Time(23435435),true));
        }
        catch (InvalidInputException e){
            error = e.getMessage();
        }
    }
    private void switchToAppointment(){
        ownerHomeScreen.setRoot(ownerAppointmentCalendar);
        updateDate();
    }
    private void switchToBusiness(){
        //mainStage.setScene(businessInfo);
    }
    private void switchToServices(){

    }
    private void switchToHomeScreen(){
    	ownerHomeScreen.setRoot(change2);
    }
    private void updateDate(){

        LocalDate calendarDate = LocalDate.of(renderDate.getYear(), renderDate.getMonthValue(), 1);
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }
        for(CalendarEntry c:listDays){
            c.setDate(calendarDate);
            c.setText(String.valueOf(calendarDate.getDayOfMonth()));
            calendarDate = calendarDate.plusDays(1);
            c.getStyleClass().remove("not-in-month");
            if(!c.getDate().getMonth().equals(renderDate.getMonth())){
                c.getStyleClass().add("not-in-month");
            }
        }
    }
    private void switchMonth(ActionEvent e){
        if(e.getTarget() instanceof  JFXButton){
            String message = ((JFXButton)e.getTarget()).getText();
            switch (message) {
                case "January":
                    renderDate = renderDate.withMonth(1);
                    break;
                case "February":
                    renderDate = renderDate.withMonth(2);
                    break;
                case "March":
                    renderDate = renderDate.withMonth(3);
                    break;
                case "April":
                    renderDate = renderDate.withMonth(4);
                    break;
                case "May":
                    renderDate = renderDate.withMonth(5);
                    break;
                case "June":
                    renderDate = renderDate.withMonth(6);
                    break;
                case "July":
                    renderDate = renderDate.withMonth(7);
                    break;
                case "August":
                    renderDate = renderDate.withMonth(8);
                    break;
                case "September":
                    renderDate = renderDate.withMonth(9);
                    break;
                case "October":
                    renderDate = renderDate.withMonth(10);
                    break;
                case "November":
                    renderDate = renderDate.withMonth(11);
                    break;
                case "December":
                    renderDate = renderDate.withMonth(12);
                    break;
            }
            updateDate();
        }
    }

    private class CalendarEntry extends JFXButton{
        LocalDate date;
        CalendarEntry(String date){
            super(date);
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }
    }


}
