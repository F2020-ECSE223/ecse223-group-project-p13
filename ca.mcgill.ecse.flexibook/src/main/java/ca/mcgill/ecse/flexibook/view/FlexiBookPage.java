package ca.mcgill.ecse.flexibook.view;

import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
import ca.mcgill.ecse.flexibook.controller.TOAppointmentCalendarItem;

import com.jfoenix.controls.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.kordamp.ikonli.javafx.FontIcon;

//import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FlexiBookPage extends Application {
    private String error;
    private Color[] colors = {new Color(.886,.941,.976,1.0),new Color(.690,.867,.882,1.0),
            new Color(.157,.435,.706,1.0),Color.WHITE,new Color(.875,.298,.451,1.0)};

    JFXButton startButton;
    //private CardLayout mainLayout;
    private Stage mainStage;
    Scene mainScene;
    HBox ownerAppointmentCalendar;
    HBox customerAppointmentCalendar;
    TextField textUserName;
    PasswordField pf;
    ArrayList<CalendarEntry> listDays = new ArrayList<>();
    ArrayList<CalendarEntry> dbvDays = new ArrayList<>();
    LocalDate renderDate;
    List<TOAppointmentCalendarItem> items;
    HBox change2;
    Label calendarMonthOwner;
    Label calendarYearOwner;
    Label calendarMonthCustomer;
    Label calendarYearCustomer;
    JFXTextField updateUsername;
    JFXPasswordField updatePassword;
    TextField textUserName1;
    PasswordField pf1;
    private BorderPane mainScreenborderpane;
    HBox servicePage;
    private TextField serviceNameInput;
    private TextField downtimeDurationInput;
    private TextField durationInput;
    private TextField downtimeStartInput;
    private ComboBox<String> existingServices;
    private TextField serviceNameInput1;
    private TextField downtimeDurationInput1;
    private TextField durationInput1;
    private TextField downtimeStartInput1;
    private ComboBox<String> existingServices1;
    private Label serviceError;



    public void start(Stage s){
        mainStage = s;
        mainStage.setTitle("FlexiBook Application");
        renderDate = LocalDate.now();
        FlexiBookController.testAppointment();
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


        //Main Screen
        mainScreenBorderPane = new BorderPane();
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
        FontIcon back = new FontIcon("dashicons-arrow-left-alt");

        appointmentIcon.getStyleClass().add("icon");
        accountIcon.getStyleClass().add("icon");
        businessIcon.getStyleClass().add("icon");
        serviceIcon.getStyleClass().add("icon");
        loginIcon.getStyleClass().add("icon");
        logoutIcon.getStyleClass().add("icon");
        signUp.getStyleClass().add("icon");
        appointmentIcon.getStyleClass().add("icon-main-menu");
        accountIcon.getStyleClass().add("icon-main-menu");
        businessIcon.getStyleClass().add("icon-main-menu");
        serviceIcon.getStyleClass().add("icon-main-menu");
        delete.getStyleClass().add("icon");
        back.getStyleClass().add("icon");
            
        JFXButton backPage = new JFXButton("Back", back);
        
        JFXButton logoutButton = new JFXButton("LogOut", logoutIcon);
        logoutButton.setContentDisplay(ContentDisplay.BOTTOM);
        logoutButton.getStyleClass().add("main-menu-button");
        logoutButton.setOnAction(e->logout());
        bottom.getChildren().add(logoutButton);
        JFXButton appointmentButton = new JFXButton("Appointments",appointmentIcon);
        appointmentButton.setContentDisplay(ContentDisplay.TOP);
        appointmentButton.setOnAction(e-> switchToOwnerAppointment());
        appointmentButton.getStyleClass().add("main-menu-button");
        buttons.getChildren().add(appointmentButton);

        /*JFXButton loginButton = new JFXButton("LogIn", loginIcon);
        loginButton.setContentDisplay(ContentDisplay.TOP);
        loginButton.setOnAction(e->switchToHomeScreen());
        loginButton.getStyleClass().add("main-menu-button");
        buttons.getChildren().add(loginButton);*/

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



        //Customer Home Page
        BorderPane customerScreenBorderPane = new BorderPane();
        Label welcome1 = new Label("Welcome, User");
        HBox top1 = new HBox();
        customerScreenBorderPane.setTop(top1);
        top1.setAlignment(Pos.BASELINE_RIGHT);
        top1.getChildren().add(welcome1);
        welcome1.getStyleClass().add("user-text");

        VBox center1 = new VBox(120);
        customerScreenBorderPane.setCenter(center1);
        center1.setAlignment(Pos.TOP_CENTER);
        Image image1 = new Image("/img/newLogo.png");
        ImageView view1 = new ImageView(image1);
        view1.setPreserveRatio(true);
        view1.setFitHeight(200);
        center1.getChildren().add(view1);

        HBox buttons1 = new HBox(75);

        buttons1.setAlignment(Pos.CENTER);

        FontIcon appointmentIcon1 = new FontIcon("eli-calendar");
        FontIcon accountIcon1 = new FontIcon("dashicons-businessperson");

        appointmentIcon1.getStyleClass().add("icon");
        accountIcon1.getStyleClass().add("icon");


        JFXButton appointmentButton1 = new JFXButton("Appointments",appointmentIcon1);
        appointmentButton1.setContentDisplay(ContentDisplay.TOP);
        appointmentButton1.setOnAction(e->switchToCustomerAppointment());
        appointmentButton1.getStyleClass().add("main-menu-button");
        buttons1.getChildren().add(appointmentButton1);

        JFXButton accountButton1 = new JFXButton("Account",accountIcon1);
        accountButton1.setContentDisplay(ContentDisplay.TOP);
        accountButton1.setOnAction(e->switchToAccount());
        accountButton1.getStyleClass().add("main-menu-button");
        buttons1.getChildren().add(accountButton1);


        center1.getChildren().add(buttons1);


        //Appointment Calendar
        ownerAppointmentCalendar = new HBox();
        ownerAppointmentCalendar.getChildren().add(setCalendar(listDays,true));
        ownerAppointmentCalendar.setStyle("-fx-background-color: #B0DDE4;");

        customerAppointmentCalendar = new HBox();
        customerAppointmentCalendar.getChildren().add(setCalendar(dbvDays,false));
        customerAppointmentCalendar.setStyle("-fx-background-color: #B0DDE4;");


        //Pop-up window
        // make appt button
        JFXButton makeApptButton = new JFXButton("Make Appointment");
        makeApptButton.setStyle("-fx-background-color: #286fb4");
        makeApptButton.setButtonType(JFXButton.ButtonType.RAISED);
        makeApptButton.setOpacity(0.8);
        makeApptButton.setTextFill(Paint.valueOf("#ffffff"));

        //time combo boxes
        JFXComboBox<Label> hourSelection = new JFXComboBox<Label>();

        hourSelection.getItems().add(new Label("1"));
        hourSelection.getItems().add(new Label("2"));
        hourSelection.getItems().add(new Label("3"));
        hourSelection.getItems().add(new Label("4"));
        hourSelection.getItems().add(new Label("5"));
        hourSelection.getItems().add(new Label("6"));
        hourSelection.getItems().add(new Label("7"));
        hourSelection.getItems().add(new Label("8"));
        hourSelection.getItems().add(new Label("9"));
        hourSelection.getItems().add(new Label("10"));
        hourSelection.getItems().add(new Label("11"));
        hourSelection.getItems().add(new Label("12"));

        hourSelection.setPromptText("Select Hour");

        hourSelection.setStyle("-fx-background-color: #b0dde4");

        JFXComboBox<Label> minuteSelection = new JFXComboBox<Label>();

        minuteSelection.getItems().add(new Label("00"));
        minuteSelection.getItems().add(new Label("05"));
        minuteSelection.getItems().add(new Label("10"));
        minuteSelection.getItems().add(new Label("15"));
        minuteSelection.getItems().add(new Label("20"));
        minuteSelection.getItems().add(new Label("25"));
        minuteSelection.getItems().add(new Label("30"));
        minuteSelection.getItems().add(new Label("35"));
        minuteSelection.getItems().add(new Label("40"));
        minuteSelection.getItems().add(new Label("45"));
        minuteSelection.getItems().add(new Label("50"));
        minuteSelection.getItems().add(new Label("55"));

        minuteSelection.setPromptText("Select Minute");

        minuteSelection.setStyle("-fx-background-color: #b0dde4");

        JFXComboBox<Label> timeOfDay = new JFXComboBox<Label>();

        timeOfDay.getItems().add(new Label("AM"));
        timeOfDay.getItems().add(new Label("PM"));

        timeOfDay.setPromptText("AM/PM");

        timeOfDay.setStyle("-fx-background-color: #b0dde4");

        // make appt button and date picker for pop up
        VBox datePickBox = new VBox(40);
        JFXDatePicker appointmentDatePicker = new JFXDatePicker();
        appointmentDatePicker.setPromptText("Select Date");

        datePickBox.getChildren().add(appointmentDatePicker);


        HBox timePicker = new HBox(40);
        timePicker.getChildren().add(hourSelection);
        timePicker.getChildren().add(minuteSelection);
        timePicker.getChildren().add(timeOfDay);
        timePicker.setAlignment(Pos.CENTER);
        datePickBox.getChildren().add(timePicker);

        makeApptButton.setAlignment(Pos.CENTER);
        datePickBox.getChildren().add(makeApptButton);

        JFXPopup appointmentPopup = new JFXPopup();
        appointmentDatePicker.setLayoutX(200);
        appointmentDatePicker.setLayoutY(200);
        datePickBox.setAlignment(Pos.TOP_CENTER);
        appointmentPopup.setPopupContent(datePickBox);


        //NodeList in Make Appointment

        JFXButton serviceCombo = new JFXButton("Service Combo");
        serviceCombo.setStyle("-fx-background-color: #e2f0f9;");
        serviceCombo.setButtonType(JFXButton.ButtonType.RAISED);
        serviceCombo.setOpacity(0.8);
        serviceCombo.setTextFill(Paint.valueOf("#286fb4"));
        serviceCombo.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                StackPane secondaryLayout = new StackPane();
                Scene secondScene = new Scene(secondaryLayout, 500, 200);

                secondaryLayout.getChildren().add(datePickBox);

                // New window (Stage)
                Stage newWindow = new Stage();
                newWindow.setTitle("Make Appointment");
                newWindow.setScene(secondScene);

                // Specifies the modality for new window.
                newWindow.initModality(Modality.WINDOW_MODAL);
                // Specifies the owner Window (parent) for new window
                newWindow.initOwner(mainStage);

                // Set position of second window, related to primary window.
                newWindow.setX(mainStage.getX() + 500);
                newWindow.setY(mainStage.getY() + 300);

                newWindow.show();
            }
        });


        JFXButton service = new JFXButton("Service");
        service.setStyle("-fx-background-color: #e2f0f9;");
        service.setButtonType(JFXButton.ButtonType.RAISED);
        service.setOpacity(0.8);
        service.setTextFill(Paint.valueOf("#286fb4"));

        service.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                StackPane secondaryLayout = new StackPane();
                Scene secondScene = new Scene(secondaryLayout, 500, 200);

                secondaryLayout.getChildren().add(datePickBox);

                // New window (Stage)
                Stage newWindow = new Stage();
                newWindow.setTitle("Make Appointment");
                newWindow.setScene(secondScene);

                // Specifies the modality for new window.
                newWindow.initModality(Modality.WINDOW_MODAL);
                // Specifies the owner Window (parent) for new window
                newWindow.initOwner(mainStage);

                // Set position of second window, related to primary window.
                newWindow.setX(mainStage.getX() + 500);
                newWindow.setY(mainStage.getY() + 300);

                newWindow.show();
            }
        });

        FontIcon plusIcon = new FontIcon("eli-plus-sign");
        plusIcon.setScaleX(0.5);
        plusIcon.setScaleY(0.5);
        plusIcon.getStyleClass().add("icon");


        Label slabel = new Label("",plusIcon);
        slabel.setWrapText(true);

        HBox rightSide = new HBox();

        rightSide.maxHeight(700);
        rightSide.maxWidth(400);
        JFXNodesList nodesList = new JFXNodesList();
        nodesList.setSpacing(10);
        nodesList.addAnimatedNode(slabel);
        nodesList.addAnimatedNode(serviceCombo);
        nodesList.addAnimatedNode(service);

        nodesList.setAlignment(Pos.BOTTOM_RIGHT);
        rightSide.getChildren().add(nodesList);
        rightSide.setAlignment(Pos.CENTER_RIGHT);
        customerAppointmentCalendar.getChildren().add(rightSide);


        VBox appointments = new VBox(20);
        ownerAppointmentCalendar.getChildren().add(appointments);
        //appointments.setPrefWidth(Double.MAX_VALUE);
        appointments.setStyle("-fx-background-color: #ffffff;");

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
        final TextField textUserName1= new TextField();
        Label lblPassword1= new Label("Enter a Password");
        final PasswordField pf1=  new PasswordField();
        JFXButton btonLogin1= new JFXButton("SignUp",signUp);
        btonLogin1.setOnAction(e->{
            mainScene.setRoot(mainScreenBorderPane);
        });
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

        mainScene = new Scene(change2,1440,810,colors[3]);
        mainScreenBorderPane.setStyle("-fx-background-color: #B0DDE4;");
        mainStage.setScene(mainScene);
        mainScene.getStylesheets().add(FlexiBookPage.class.getResource("/css/main.css").toExternalForm());
        mainScreenBorderPane.requestFocus();

        mainScene.setRoot(change2);
        customerScreenBorderPane.setStyle("-fx-background-color: #B0DDE4;");
        customerScreenBorderPane.requestFocus();
        
                //Account
        changeAcc = new HBox();
        changeAcc.setPadding(new Insets(100,100,100,100));
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
        pane.add(backPage, 3, 0);
        pane.setAlignment(Pos.CENTER_LEFT);
        
        changeAcc.getChildren().add(pane);
        
        backPage.setOnAction(e->back());
        
    }
    
    private void back() {
    	
    	mainScene.setRoot(mainScreenBorderPane);
    	
    }
    
    private void signUp() {
    	
        mainScene.setRoot(mainScreenBorderPane);
        
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
            mainScene.setRoot(change2);
        }
        catch(Exception e){
            e.getMessage();
        }
    }
     private void login() {
        try{
            mainStage.setScene(mainScene);
            FlexiBookController.login(textUserName.getText(),pf.getText());
        }
        catch(InvalidInputException e){
            e.getMessage();
        }
    }

    private void refreshData(){
        int index = 0;
        serviceNameInput.setText("");
        downtimeDurationInput.setText("");
        durationInput.setText("");
        downtimeStartInput.setText("");
        serviceNameInput1.setText("");
        downtimeDurationInput1.setText("");
        durationInput1.setText("");
        downtimeStartInput1.setText("");

        existingServices.getItems().clear();
        existingServices1.getItems().clear();
        for (TOService s : FlexiBookController.getServices()){
            existingServices.getItems().add(s.getName());
            existingServices1.getItems().add(s.getName());
            index++;
        }
    }
    private HBox setCalendar(ArrayList<CalendarEntry> entry,boolean owner){
        HBox  calendar =new HBox();

        VBox months = new VBox(20);
        months.setAlignment(Pos.CENTER);
        months.setStyle("-fx-background-color: #E2F0F9");
        months.setPadding(new Insets(20));
        calendar.getChildren().add(months);
        months.setPrefWidth(200);

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

        FontIcon leftArrow = new FontIcon("fth-arrow-left-circle");
        leftArrow.getStyleClass().add("icon-calendar");
        FontIcon rightArrow = new FontIcon("fth-arrow-right-circle");
        rightArrow.getStyleClass().add("icon-calendar");

        JFXButton leftArrowButton = new JFXButton("",leftArrow);
        leftArrowButton.setContentDisplay(ContentDisplay.TOP);
        leftArrowButton.setOnAction(e-> {
            renderDate= renderDate.minusYears(1);
            updateDate(listDays,calendarYearOwner,calendarMonthOwner);
        });
        leftArrowButton.getStyleClass().add("icon-calendar-button");
        Region space = new Region();
        space.setMinWidth(350);

        JFXButton rightArrowButton = new JFXButton("",rightArrow);
        rightArrowButton.setContentDisplay(ContentDisplay.TOP);
        rightArrowButton.setOnAction(e-> {
            renderDate= renderDate.plusYears(1);
            updateDate(listDays,calendarYearOwner,calendarMonthOwner);
        });
        rightArrowButton.getStyleClass().add("icon-calendar-button");
        calendarMain.setPrefSize(500,100);

        if(owner){
            calendarMonthOwner =  new Label(renderDate.getMonth().toString());
            calendarMonthOwner.getStyleClass().add("month-year");
            calendarMonthOwner.setPrefWidth(150);
            calendarTop.getChildren().add(calendarMonthOwner);
            calendarTop.getChildren().add(space);
            calendarTop.getChildren().add(leftArrowButton);
            calendarYearOwner = new Label(String.valueOf(renderDate.getYear()));
            calendarYearOwner.getStyleClass().add("month-year");
            calendarTop.getChildren().add(calendarYearOwner);
            calendarTop.getChildren().add(rightArrowButton);
        }
        else{
            calendarMonthCustomer =  new Label(renderDate.getMonth().toString());
            calendarMonthCustomer.getStyleClass().add("month-year");
            calendarTop.getChildren().add(calendarMonthCustomer);
            calendarTop.getChildren().add(space);
            calendarTop.getChildren().add(leftArrowButton);
            calendarYearCustomer = new Label(String.valueOf(renderDate.getYear()));
            calendarYearCustomer.getStyleClass().add("month-year");
            calendarTop.getChildren().add(calendarYearCustomer);
            calendarTop.getChildren().add(rightArrowButton);
        }




        calendarMain.getChildren().add(calendarTop);
        AnchorPane.setRightAnchor(rightArrow,0.0);

        calendarMain.setAlignment(Pos.CENTER);
        GridPane days = new GridPane();

        HBox calendarDays = new HBox(45);
        calendarDays.setAlignment(Pos.CENTER);
        Label sundayLabel = new Label("Sun");
        sundayLabel.getStyleClass().add("month-year");
        calendarDays.getChildren().add(sundayLabel);
        Label mondayLabel = new Label("Mon");
        mondayLabel.getStyleClass().add("month-year");
        calendarDays.getChildren().add(mondayLabel);
        Label tuesdayLabel = new Label("Tue");
        tuesdayLabel.getStyleClass().add("month-year");
        calendarDays.getChildren().add(tuesdayLabel);
        Label wednesdayLabel = new Label("Wed");
        wednesdayLabel.getStyleClass().add("month-year");
        calendarDays.getChildren().add(wednesdayLabel);
        Label thursdayLabel = new Label("Thu");
        thursdayLabel.getStyleClass().add("month-year");
        calendarDays.getChildren().add(thursdayLabel);
        Label fridayLabel = new Label("Fri");
        fridayLabel.getStyleClass().add("month-year");
        calendarDays.getChildren().add(fridayLabel);
        Label saturdayLabel = new Label("Sat");
        saturdayLabel.getStyleClass().add("month-year");
        calendarDays.getChildren().add(saturdayLabel);
        calendarMain.getChildren().add(calendarDays);
        for(int i =1; i<7;i++){
            for(int j = 1; j< 8; j++){
                CalendarEntry calendarEntry = new CalendarEntry("1");
                calendarEntry.setDate(LocalDate.now());
                calendarEntry.setPrefSize(50,50);
                calendarEntry.setMinWidth(100);
                calendarEntry.setMinHeight(100);
                calendarEntry.setStyle("-fx-background-color: #FFFFFF");
                calendarEntry.getStyleClass().add("calendar-cell");
                calendarEntry.setAlignment(Pos.TOP_LEFT);
                calendarEntry.setOnAction(this::updateDailySchedule);
                entry.add(calendarEntry);
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
    private void switchToOwnerAppointment(){
        mainScene.setRoot(ownerAppointmentCalendar);
        updateDate(listDays,calendarYearOwner,calendarMonthOwner);
    }

    private void switchToCustomerAppointment(){
        mainScene.setRoot(customerAppointmentCalendar);
        updateDate(listDays,null,null);
    }

    private void switchToBusiness(){
        //mainStage.setScene(businessInfo);
    }
    private void switchToServices(){
        setUpServicePage();
        mainScene.setRoot(servicePage);
    }
    private List<TOAppointmentCalendarItem> updateDailySchedule(ActionEvent event){
        if(event.getTarget() instanceof CalendarEntry){
            LocalDate date = ((CalendarEntry) event.getTarget()).getDate();
            try{
                items = FlexiBookController.getAppointmentCalendar(generateLocalDate(date));
            }
            catch (InvalidInputException e){
                error = e.getMessage();
            }
        }
        return null;
    }
    private void switchToHomeScreen(){
        mainScene.setRoot(change2);
    }

    private void switchToCustomerAccount(){}

    private void updateDate(ArrayList<CalendarEntry> list,Label year,Label month){
        list.get(15).getStyleClass().add("calendar-holiday");
        list.get(15).setStyle("-fx-background-color: #e0163e");
        year.setText(String.valueOf(renderDate.getYear()));
        month.setText(String.valueOf(renderDate.getMonth()));
        LocalDate calendarDate = LocalDate.of(renderDate.getYear(), renderDate.getMonthValue(), 1);
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }
        for(CalendarEntry c:list){
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
            updateDate(listDays,calendarYearOwner,calendarMonthOwner);
        }
    }
    private String generateLocalDate(LocalDate date){
        String s = ""+ date.getYear() + "-";
        if(date.getMonthValue() <10){
            s+="0";
        }
        s+=date.getMonthValue()+"-";
        if(date.getDayOfMonth() <10){
            s+="0";
        }
        s+=date.getDayOfMonth();
        return s;
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

private void setUpServicePage() {
    	servicePage = new HBox();
    	servicePage.setAlignment(Pos.CENTER);
        servicePage.setStyle("-fx-background-color: #B0DDE4;");
        
        Image image = new Image("/img/newLogo.png");
        
        Label serviceName = new Label("Service Name: ");
        serviceName.getStyleClass().add("service-text");
        serviceName.setAlignment(Pos.CENTER_RIGHT);
        Label downtimeDuration = new Label("Downtime Duration:");
        downtimeDuration.getStyleClass().add("service-text");
        downtimeDuration.setAlignment(Pos.CENTER_RIGHT);
        serviceNameInput = new TextField();
        downtimeDurationInput = new TextField();
        Label duration = new Label("Duration:");
        duration.getStyleClass().add("service-text");
        duration.setAlignment(Pos.CENTER_RIGHT);
        Label downtimeStart = new Label("Downtime Start:");
        downtimeStart.getStyleClass().add("service-text");
        downtimeStart.setAlignment(Pos.CENTER_RIGHT);
        durationInput = new TextField();
        downtimeStartInput = new TextField();
        Button addServiceButton = new Button("Add Service");
        addServiceButton.getStyleClass().add("service-button");
        addServiceButton.setOnAction(e->addServiceAction());
        Label serviceName1 = new Label("Service:");
        serviceName1.getStyleClass().add("service-text");
        serviceName1.setAlignment(Pos.CENTER_RIGHT);
        Label newServiceName = new Label("New Service Name: ");
        newServiceName.getStyleClass().add("service-text");
        newServiceName.setAlignment(Pos.CENTER_RIGHT);
        Label newDowntimeDuration = new Label("Downtime Duration:");
        newDowntimeDuration.getStyleClass().add("service-text");
        newDowntimeDuration.setAlignment(Pos.CENTER_RIGHT);
        Label newDuration = new Label("Duration:");
        newDuration.getStyleClass().add("service-text");
        newDuration.setAlignment(Pos.CENTER_RIGHT);
        Label newDowntimeStart = new Label("Downtime Start:");
        newDowntimeStart.getStyleClass().add("service-text");
        newDowntimeStart.setAlignment(Pos.CENTER_RIGHT);
        existingServices = new ComboBox<String>();
        existingServices.getStyleClass().add("combo-box");
        serviceNameInput1 = new TextField();
        downtimeDurationInput1 = new TextField();
        durationInput1 = new TextField();
        downtimeStartInput1 = new TextField();
        Button updateServiceButton = new Button("Update Service");
        updateServiceButton.getStyleClass().add("service-button");
        updateServiceButton.setOnAction(e->updateServiceAction());
        Label serviceToDelete = new Label("Service:");
        serviceToDelete.getStyleClass().add("service-text");
        serviceToDelete.setAlignment(Pos.CENTER_RIGHT);
        existingServices1 = new ComboBox<String>();
        Button deleteServiceButton = new Button("Delete Service");
        deleteServiceButton.getStyleClass().add("service-button");
        deleteServiceButton.setOnAction(e->deleteServiceAction());
        Label spacing = new Label(" ");
        spacing.getStyleClass().add("service-text");
        serviceError = new Label("");
        serviceError.getStyleClass().add("error-text");

        FontIcon homeIcon = new FontIcon("dashicons-admin-home");
        homeIcon.getStyleClass().add("icon");

        JFXButton homeButton = new JFXButton("Home", homeIcon);
        homeButton.setContentDisplay(ContentDisplay.TOP);
        homeButton.getStyleClass().add("main-menu-button");

        VBox col1 = new VBox(20);
        col1.getChildren().addAll(serviceName, downtimeDuration);
        VBox col2 = new VBox(20);
        col2.getChildren().addAll(serviceNameInput, downtimeDurationInput);
        VBox col3 = new VBox(20);
        col3.getChildren().addAll(duration, downtimeStart);
        VBox col4 = new VBox(20);
        col4.getChildren().addAll(durationInput, downtimeStartInput, addServiceButton);

        VBox col11 = new VBox(20);
        col11.getChildren().addAll(serviceName1, newDuration, newDowntimeStart);
        VBox col21 = new VBox(20);
        col21.getChildren().addAll(existingServices, durationInput1, downtimeStartInput1);
        VBox col31 = new VBox(20);
        col31.getChildren().addAll(newServiceName, newDowntimeDuration);
        VBox col41 = new VBox(20);
        col41.getChildren().addAll(serviceNameInput1, downtimeDurationInput1, updateServiceButton);

        VBox col12 = new VBox(20);
        col12.getChildren().add(serviceToDelete);
        VBox col22 = new VBox(20);
        col22.getChildren().add(existingServices1);
        VBox col32 = new VBox(20);
        col32.getChildren().add(spacing);
        VBox col42 = new VBox(20);
        col42.getChildren().add(deleteServiceButton);

        Label addService = new Label("Add Service");
        addService.getStyleClass().add("service-heading");
        addService.setAlignment(Pos.CENTER);

        Label updateService = new Label("Update Service");
        updateService.getStyleClass().add("service-heading");
        updateService.setAlignment(Pos.CENTER);

        Label deleteService = new Label("Delete Service");
        deleteService.getStyleClass().add("service-heading");
        deleteService.setAlignment(Pos.CENTER);

        HBox row1 = new HBox(10);
        row1.getChildren().addAll(col1, col2, col3, col4);
        HBox row2 = new HBox(10);
        row2.getChildren().addAll(col11, col21, col31, col41);
        HBox row3 = new HBox(10);
        row3.getChildren().addAll(col12, col22, col32, col42);
        HBox row4 = new HBox(10);
        row4.getChildren().add(addService);
        row4.setAlignment(Pos.CENTER);
        row4.setTranslateX(75);
        HBox row5 = new HBox(10);
        row5.getChildren().add(updateService);
        row5.setAlignment(Pos.CENTER);
        row5.setTranslateX(75);
        HBox row6 = new HBox(10);
        row6.getChildren().add(deleteService);
        row6.setAlignment(Pos.CENTER);
        row6.setTranslateX(75);
        HBox row7 = new HBox(10);
        row7.getChildren().add(serviceError);
        row7.setAlignment(Pos.CENTER);
        row7.setTranslateX(75);

        VBox space = new VBox(20);
        space.setAlignment(Pos.CENTER);
        space.getChildren().addAll(row4, row1, row5, row2, row6, row3, row7);

        ImageView view1 = new ImageView(image);

        HBox topRight = new HBox();
        topRight.setAlignment(Pos.TOP_LEFT);
        topRight.getChildren().add(view1);

        HBox topLeft = new HBox();
        topLeft.setAlignment(Pos.TOP_CENTER);
        topLeft.getChildren().add(homeButton);
        topLeft.setTranslateX(-140);

        servicePage.getChildren().addAll(topLeft, space, topRight);
    }
    
    private void addServiceAction() {
        serviceError.setText("");
        if (serviceNameInput.getText().equals("")){
            serviceError.setText("A service must have a name!");
        }
        else if (durationInput.getText().equals("")){
            serviceError.setText("A service must have a duration!");
        }
        else if(downtimeDurationInput.getText().equals("")){
            serviceError.setText("A service must have a downtime duration!");
        }
        else if(downtimeStartInput.getText().equals("")){
            serviceError.setText("A service must have a downtime start!");
        }

        if(serviceError.getText().length() == 0) {
            try {
                FlexiBookController.addService(serviceNameInput.getText(), Integer.parseInt(durationInput.getText()),
                        Integer.parseInt(downtimeDurationInput.getText()), Integer.parseInt(downtimeStartInput.getText()));
            } catch (InvalidInputException e) {
                serviceError.setText(e.getMessage());
            }
        }
        refreshData();
    }

    private void updateServiceAction(){
        serviceError.setText("");

        if (existingServices.getItems().size() == 0){
            serviceError.setText("An existing service must be selected!");
        }
        else if (serviceNameInput1.getText().equals("")){
            serviceError.setText("A service must have a name!");
        }
        else if (durationInput1.getText().equals("")){
            serviceError.setText("A service must have a duration!");
        }
        else if(downtimeDurationInput1.getText().equals("")){
            serviceError.setText("A service must have a downtime duration!");
        }
        else if(downtimeStartInput1.getText().equals("")){
            serviceError.setText("A service must have a downtime start!");
        }
        if (serviceError.getText().length() == 0) {
            try {
                FlexiBookController.updateService(existingServices.getValue(), serviceNameInput1.getText(), Integer.parseInt(durationInput1.getText()),
                        Integer.parseInt(downtimeDurationInput1.getText()), Integer.parseInt(downtimeStartInput1.getText()));
            } catch (InvalidInputException e) {
                serviceError.setText(e.getMessage());
            }
        }
        refreshData();
    }

    private void deleteServiceAction(){
        serviceError.setText("");

        if (existingServices1.getItems().size() == 0){
            serviceError.setText("An existing service must be selected!");
        }
        if (serviceError.getText().length() == 0) {
            try {
                FlexiBookController.deleteService(existingServices1.getValue());
            } catch (InvalidInputException e) {
                serviceError.setText(e.getMessage());
            }
        }
        refreshData();
    }
}
