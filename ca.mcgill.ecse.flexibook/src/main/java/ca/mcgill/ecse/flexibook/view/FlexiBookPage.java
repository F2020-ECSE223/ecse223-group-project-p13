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
import org.kordamp.ikonli.javafx.FontIcon;

//import java.awt.*;
import java.awt.event.MouseEvent;
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
    ArrayList<CalendarEntry> listDays = new ArrayList<>();
    ArrayList<CalendarEntry> listDays2 = new ArrayList<>();
    LocalDate renderDate;



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
                listDays2.add(calendarEntry);
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

    private void switchToCustomerAppointment(){
        customerHomeScreen.setRoot(customerAppointmentCalendar);
        updateDate();
    }

    private void switchToBusiness(){
        //mainStage.setScene(businessInfo);
    }
    private void switchToServices(){}

    private void switchToAccount(){}

    private void switchToCustomerAccount(){}

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