package ca.mcgill.ecse.flexibook.view;

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
    HBox change;
    Scene customerHomeScreen;
    HBox ownerAppointmentCalendar;
    HBox customerAppointmentCalendar;
    /*private Scene appointmentCalendar = new Scene(new HBox(),1440,810,colors[3]);
    private Scene businessInfo;
    private Scene Services;
    private Scene Account;
    private Scene makeAppointment;*/
    Label calendarMonth;
    ArrayList<CalendarEntry> listDays = new ArrayList<>();



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

        /*
        //Main Screen
        BorderPane mainScreenBorderPane = new BorderPane();
        Label welcome = new Label("Welcome, User");
        HBox top = new HBox();
        mainScreenBorderPane.setTop(top);
        top.setAlignment(Pos.BASELINE_RIGHT);
        top.getChildren().add(welcome);
        welcome.getStyleClass().add("user-text");

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

        appointmentIcon.getStyleClass().add("icon");
        accountIcon.getStyleClass().add("icon");
        businessIcon.getStyleClass().add("icon");
        serviceIcon.getStyleClass().add("icon");


        JFXButton appointmentButton = new JFXButton("Appointments",appointmentIcon);
        appointmentButton.setContentDisplay(ContentDisplay.TOP);
        appointmentButton.setOnAction(e->switchToAppointment());
        appointmentButton.getStyleClass().add("main-menu-button");
        buttons.getChildren().add(appointmentButton);

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
        */


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
        ownerAppointmentCalendar.getChildren().add(setCalendar());
        ownerAppointmentCalendar.setStyle("-fx-background-color: #B0DDE4;");
        customerAppointmentCalendar = new HBox();
        customerAppointmentCalendar.getChildren().add(setCalendar());
        customerAppointmentCalendar.setStyle("-fx-background-color: #B0DDE4;");

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


        //ownerHomeScreen = new Scene(mainScreenBorderPane,1440,810,colors[3]);
        //mainScreenBorderPane.setStyle("-fx-background-color: #B0DDE4;");
        //mainStage.setScene(ownerHomeScreen);
        //ownerHomeScreen.getStylesheets().add(FlexiBookPage.class.getResource("/css/main.css").toExternalForm());
        //mainScreenBorderPane.requestFocus();

        customerHomeScreen = new Scene(customerScreenBorderPane,1440,810,colors[3]);
        customerScreenBorderPane.setStyle("-fx-background-color: #B0DDE4;");
        mainStage.setScene(customerHomeScreen);
        customerHomeScreen.getStylesheets().add(FlexiBookPage.class.getResource("/css/main.css").toExternalForm());
        customerScreenBorderPane.requestFocus();
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

        calendarMonth = new Label("November");
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
        updateDate(LocalDate.now());
    }

    private void switchToCustomerAppointment(){
        customerHomeScreen.setRoot(customerAppointmentCalendar);
        updateDate(LocalDate.now());
    }

    private void switchToBusiness(){
        //mainStage.setScene(businessInfo);
    }
    private void switchToServices(){

    }
    private void switchToAccount(){}
    private void updateDate(LocalDate date){

        LocalDate calendarDate = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }
        for(CalendarEntry c:listDays){
            c.setDate(calendarDate);
            c.setText(String.valueOf(calendarDate.getDayOfMonth()));
            calendarDate = calendarDate.plusDays(1);
            if(!c.getDate().getMonth().equals(date.getMonth())){
                c.getStyleClass().add("not-in-month");
            }
        }
    }
    private void switchMonth(ActionEvent e){
        if(e.getTarget() instanceof  JFXButton){
            String message = ((JFXButton)e.getTarget()).getText();
            switch (message) {
                case "January":
                    calendarMonth.setText("January");
                    break;
                case "February":
                    calendarMonth.setText("February");
                    break;
                case "March":
                    calendarMonth.setText("March");
                    break;
                case "April":
                    calendarMonth.setText("April");
                    break;
                case "May":
                    calendarMonth.setText("May");
                    break;
                case "June":
                    calendarMonth.setText("June");
                    break;
                case "July":
                    calendarMonth.setText("July");
                    break;
                case "August":
                    calendarMonth.setText("August");
                    break;
                case "September":
                    calendarMonth.setText("September");
                    break;
                case "October":
                    calendarMonth.setText("October");
                    break;
                case "November":
                    calendarMonth.setText("November");
                    break;
                case "December":
                    calendarMonth.setText("December");
                    break;
            }
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