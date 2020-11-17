package ca.mcgill.ecse.flexibook.view;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
import ca.mcgill.ecse.flexibook.controller.TOAppointmentCalendarItem;

import ca.mcgill.ecse.flexibook.controller.TOService;
import ca.mcgill.ecse.flexibook.controller.TOBusinessHour;
import com.jfoenix.controls.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FlexiBookPage extends Application {
    private String error;
    private Color[] colors = {new Color(.886,.941,.976,1.0),new Color(.690,.867,.882,1.0),
            new Color(.157,.435,.706,1.0),Color.WHITE,new Color(.875,.298,.451,1.0)};
    private Stage mainStage;
    Scene mainScene;
    HBox ownerAppointmentCalendar;
    HBox customerAppointmentCalendar;
    BorderPane ownerMainScreenBorderPane;
    BorderPane customerScreenBorderPane;
    JFXTextField textUserName;
    JFXPasswordField pf;
    ArrayList<CalendarEntry> listDays = new ArrayList<>();
    ArrayList<CalendarEntry> dbvDays = new ArrayList<>();
    LocalDate renderDate;
    HBox change2;
    Label calendarMonthOwner;
    Label calendarYearOwner;
    Label calendarMonthCustomer;
    Label calendarYearCustomer;
    TableView<DayEvent> dailyAppointmentTable;
    String username = null;
    JFXTextField updateUsername;
    JFXPasswordField updatePassword;
    JFXTextField updateBusName;
    JFXTextField updateBusEmail;
    JFXTextField updateBusAdd;
    JFXTextField updateBusPhone;
    JFXComboBox<String> addDay;
    JFXComboBox<String> delBH;
    JFXTextField startTime;
    JFXTextField endTime;
    Label businessError;
    Label curBussName;
    Label curBussEmail;
    Label curBussAdd;
    Label curBussPN;
    Label curBussName2;
    Label curBussEmail2;
    Label curBussAdd2;
    Label curBussPN2;
    TextField textUserName1;
    PasswordField pf1;
    private BorderPane mainScreenborderpane;
    HBox servicePage;
    private JFXTextField serviceNameInput;
    private JFXTextField downtimeDurationInput;
    private JFXTextField durationInput;
    private JFXTextField downtimeStartInput;
    private JFXComboBox<String> existingServices;
    private JFXTextField serviceNameInput1;
    private JFXTextField downtimeDurationInput1;
    private JFXTextField durationInput1;
    private JFXTextField downtimeStartInput1;
    private JFXComboBox<String> existingServices1;
    private Label serviceError;
    private HBox changeAcc;
    private HBox changeBuss;
    private HBox changeBussCust;
    TableView.TableViewSelectionModel<DayEvent> selectionModel;
    private TilePane appointmentDetails;
    TOAppointmentCalendarItem currentAppointment = null;
    GridPane gridP;
    HBox availableServicesPage;
    Label errorMessageAppointmentCalendar;




    public void start(Stage s){
        mainStage = s;
        mainStage.setResizable(false);
        //FlexiBookController.testAppointment();
        mainStage.setTitle("FlexiBook Application");
        renderDate = LocalDate.now();
        initComponents();
        mainStage.show();
    }
    public FlexiBookPage(){}
    private void initComponents(){
        //Main Screen
        ownerMainScreenBorderPane = new BorderPane();
        Label welcome = new Label("Welcome, Owner");
        HBox top = new HBox();
        ownerMainScreenBorderPane.setTop(top);
        top.setAlignment(Pos.CENTER_RIGHT);
        top.getChildren().add(welcome);
        welcome.getStyleClass().add("user-text");

        ImageView imageView = null;
        try{
            imageView = new ImageView("/img/" +username+".png");
        }
        catch (IllegalArgumentException e){
            imageView = new ImageView("/img/default.png");
        }
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(60.0);
        final Circle clip = new Circle(25, 25, 25);
        imageView.setClip(clip);
        top.getChildren().add(imageView);
        top.setPadding(new Insets(10,20,0,0));

        HBox bottom = new HBox();
        ownerMainScreenBorderPane.setBottom(bottom);
        bottom.setAlignment(Pos.BASELINE_RIGHT);

        VBox center = new VBox(70);
        ownerMainScreenBorderPane.setCenter(center);
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
        JFXButton backPage2 = new JFXButton("Back", back);
        JFXButton backPage3 = new JFXButton("Back", back);

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
        customerScreenBorderPane = new BorderPane();
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

        appointmentIcon1.getStyleClass().add("icon-main-menu");
        accountIcon1.getStyleClass().add("icon-main-menu");


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

        JFXButton businessButton1 = new JFXButton("Business", businessIcon);
        businessButton1.setContentDisplay(ContentDisplay.TOP);
        businessButton1.setOnAction(e->switchToBusinessCust());
        businessButton1.getStyleClass().add("main-menu-button");
        buttons1.getChildren().add(businessButton1);


        center1.getChildren().add(buttons1);


        //Appointment Calendar
        ownerAppointmentCalendar = new HBox();
        ownerAppointmentCalendar.getChildren().add(setCalendar(listDays,true));
        ownerAppointmentCalendar.setStyle("-fx-background-color: #B0DDE4;");

        VBox appointments = new VBox(20);
        ownerAppointmentCalendar.getChildren().add(appointments);
        appointments.setMinWidth(530);

        //appointments.setPrefWidth(Double.MAX_VALUE);
        dailyAppointmentTable = new TableView<>();
        TableColumn<DayEvent, String> column1 = new TableColumn<>("Start Time");
        column1.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        column1.prefWidthProperty().bind(dailyAppointmentTable.widthProperty().multiply(0.33));
        column1.getStyleClass().add("appointment-table-rows");
        TableColumn<DayEvent, String> column2 = new TableColumn<>("Username");
        column2.getStyleClass().add("appointment-table-rows");
        column2.prefWidthProperty().bind(dailyAppointmentTable.widthProperty().multiply(0.33));
        column2.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<DayEvent, String> column3 = new TableColumn<>("End Time");
        column3.getStyleClass().add("appointment-table-rows");
        column3.prefWidthProperty().bind(dailyAppointmentTable.widthProperty().multiply(0.33));

        column3.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        dailyAppointmentTable.getColumns().addAll(column1,column2,column3);
        selectionModel = dailyAppointmentTable.getSelectionModel();

        dailyAppointmentTable.setPlaceholder(new Label("No Appointments Today"));
        appointments.getChildren().add(dailyAppointmentTable);
        dailyAppointmentTable.getStyleClass().add("daily-appointment-table");
        dailyAppointmentTable.setPadding(new Insets(20,20,20,20));

        AnchorPane individualAppointment = new AnchorPane();
        appointments.getChildren().add(individualAppointment);
        individualAppointment.prefWidthProperty().bind(dailyAppointmentTable.widthProperty());
        VBox appointmentInformation = new VBox();
        individualAppointment.getChildren().add(appointmentInformation);
        appointmentDetails = new TilePane();
        AnchorPane appointmentButtons = new AnchorPane();

        appointmentButtons.prefHeightProperty().bind(dailyAppointmentTable.prefHeightProperty());
        AnchorPane.setTopAnchor(appointmentButtons,2.0);
        AnchorPane.setRightAnchor(appointmentButtons,2.0);

        Label appointmentTitle = new Label("Appointment Info");
        appointmentTitle.getStyleClass().add("user-text");
        AnchorPane.setLeftAnchor(individualAppointment,2.0);
        AnchorPane.setTopAnchor(individualAppointment,2.0);
        appointmentInformation.getChildren().add(appointmentTitle);

        appointmentInformation.getChildren().add(appointmentDetails);
        individualAppointment.getChildren().add(appointmentButtons);
        appointmentInformation.prefWidthProperty().bind(individualAppointment.widthProperty().multiply(0.7));
        appointmentButtons.prefWidthProperty().bind(individualAppointment.widthProperty().multiply(0.3));
        appointmentDetails.setPrefColumns(2);
        appointmentInformation.setVisible(false);

        FontIcon startAppointmentIcon = new FontIcon("eli-play-circle");
        startAppointmentIcon.getStyleClass().add("icon-start-buttons");

        FontIcon endAppointmentIcon = new FontIcon("ri-stop-sign");
        endAppointmentIcon.getStyleClass().add("icon-start-buttons");

        JFXButton startAppointment = new JFXButton("Start Appointment",startAppointmentIcon);
        startAppointment.setContentDisplay(ContentDisplay.TOP);
        startAppointment.getStyleClass().add("appointment-start-buttons");
        startAppointment.setDisable(true);
        startAppointment.setOnAction(this::startAppointmentEvent);
        startAppointment.getStyleClass().add("appointment-start-buttons");
        appointmentButtons.getChildren().add(startAppointment);

        JFXButton endAppointment  = new JFXButton("End Appointment",endAppointmentIcon);
        endAppointment.setContentDisplay(ContentDisplay.TOP);
        endAppointment.getStyleClass().add("appointment-start-buttons");
        endAppointment.setDisable(true);
        endAppointment.setOnAction(this::startAppointmentEvent);
        endAppointment.getStyleClass().add("appointment-start-buttons");
        appointmentButtons.getChildren().add(endAppointment);

        FontIcon registerNoShowIcon = new FontIcon("enty-eye-with-line");
        registerNoShowIcon.getStyleClass().add("icon-start-buttons");
        //registerNoShowIcon.setStyle("-fx-pref-width: 150px;");

        JFXButton registerNoShow = new JFXButton("Register No Show",registerNoShowIcon);
        registerNoShow.setContentDisplay(ContentDisplay.TOP);
        registerNoShow.setOnAction(this::registerNoShowEvent);
        registerNoShow.setDisable(true);
        registerNoShow.getStyleClass().add("appointment-start-buttons");
        appointmentButtons.getChildren().add(registerNoShow);

        FontIcon homeButtonIcon = new FontIcon("dashicons-admin-home");
        homeButtonIcon.getStyleClass().add("icon-calendar");
        JFXButton homeButton = new JFXButton("",homeButtonIcon);
        homeButton.setContentDisplay(ContentDisplay.TOP);
        homeButton.setOnAction(event -> mainScene.setRoot(ownerMainScreenBorderPane));
        appointmentButtons.getChildren().add(homeButton);

        errorMessageAppointmentCalendar = new Label("GANG GANG GANG GANG");
        errorMessageAppointmentCalendar.getStyleClass().add("owner-error-message");
        errorMessageAppointmentCalendar.setVisible(true);
        individualAppointment.getChildren().add(errorMessageAppointmentCalendar);
        AnchorPane.setBottomAnchor(errorMessageAppointmentCalendar,2.0);
        AnchorPane.setLeftAnchor(errorMessageAppointmentCalendar,2.0);


        AnchorPane.setTopAnchor(startAppointment,5.0);
        AnchorPane.setTopAnchor(registerNoShow,100.0);
        AnchorPane.setBottomAnchor(homeButton,0.0);
        AnchorPane.setRightAnchor(homeButton,0.0);


        ObservableList<DayEvent> observableList = selectionModel.getSelectedItems();
        observableList.addListener((ListChangeListener<DayEvent>) c -> {
            while(c.next()) {
                if (!c.wasPermutated()) {
                    for (DayEvent removeitem : c.getRemoved()) {
                        appointmentInformation.setVisible(false);
                        registerNoShow.setDisable(true);
                        startAppointment.setDisable(true);
                        currentAppointment = null;
                    }
                    for (DayEvent additem : c.getAddedSubList()) {
                        if(additem.getAppointment().getDescription().equals("appointment")){
                            currentAppointment = additem.getAppointment();
                            ((Label)appointmentDetails.getChildren().get(1)).setText(additem.getAppointment().getUsername());
                            ((Label)appointmentDetails.getChildren().get(3)).setText(additem.getStartTime());
                            ((Label)appointmentDetails.getChildren().get(5)).setText(additem.getEndTime());
                            ((Label)appointmentDetails.getChildren().get(7)).setText(additem.getAppointment().getMainService());
                            registerNoShow.setDisable(false);
                            startAppointment.setDisable(false);
                            appointmentInformation.setVisible(true);
                        }
                    }
                }
            }
            c.reset();
        });
        for(int i = 0; i< 10;i++){
            Label l = new Label("");
            appointmentDetails.getChildren().add(l);
            l.getStyleClass().add("user-text");
        }
        ((Label)appointmentDetails.getChildren().get(0)).setText("Username: ");
        ((Label)appointmentDetails.getChildren().get(2)).setText("Start Time: ");
        ((Label)appointmentDetails.getChildren().get(4)).setText("End Time: ");
        ((Label)appointmentDetails.getChildren().get(6)).setText("Main Service: ");
        ((Label)appointmentDetails.getChildren().get(8)).setText("Chosen Items: ");



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
        //hour
        JFXComboBox<Label> hourSelection = new JFXComboBox<Label>();

        for(int i = 1; i < 13; i++){
            hourSelection.getItems().add(new Label(String.valueOf(i)));
        }

        hourSelection.setPromptText("Select Hour");

        hourSelection.setStyle("-fx-background-color: #b0dde4");

        //minute
        JFXComboBox<Label> minuteSelection = new JFXComboBox<Label>();

        for(int i = 0; i < 56; i+=5){
            if(i == 0){
                minuteSelection.getItems().add(new Label("00"));
                continue;
            }
            if(i == 5){
                minuteSelection.getItems().add(new Label("05"));
            }
            else{
                minuteSelection.getItems().add(new Label(String.valueOf(i)));
            }
        }

        minuteSelection.setPromptText("Select Minute");

        minuteSelection.setStyle("-fx-background-color: #b0dde4");

        // pm/am
        JFXComboBox<Label> timeOfDay = new JFXComboBox<Label>();

        timeOfDay.getItems().add(new Label("AM"));
        timeOfDay.getItems().add(new Label("PM"));

        timeOfDay.setPromptText("AM/PM");

        timeOfDay.setStyle("-fx-background-color: #b0dde4");

        //service
        JFXComboBox<Label> services = new JFXComboBox<Label>();

        services.getItems().add(new Label("service1"));
        services.getItems().add(new Label("service2"));

        services.setPromptText("Choose Service");

        services.setStyle("-fx-background-color: #b0dde4");



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

        datePickBox.getChildren().add(services);
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
                Scene secondScene = new Scene(secondaryLayout, 510, 250);

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
                Scene secondScene = new Scene(secondaryLayout, 510, 250);

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

        // plus icon
        FontIcon plusIcon = new FontIcon("eli-plus-sign");
        plusIcon.setScaleX(0.5);
        plusIcon.setScaleY(0.5);
        plusIcon.getStyleClass().add("icon-main-menu");

        Label slabel = new Label("",plusIcon);
        slabel.setWrapText(true);

        HBox rightSide = new HBox(10);

        rightSide.setPrefHeight(200);
        rightSide.setPrefWidth(550);
        rightSide.setStyle("-fx-background-color: #b0dde4;");

        customerAppointmentCalendar.getChildren().add(rightSide);

        JFXNodesList nodesList = new JFXNodesList();
        nodesList.setSpacing(10);
        nodesList.addAnimatedNode(slabel);
        nodesList.addAnimatedNode(serviceCombo);
        nodesList.addAnimatedNode(service);


        nodesList.setAlignment(Pos.BOTTOM_RIGHT);
        rightSide.getChildren().add(nodesList);
        rightSide.setAlignment(Pos.BASELINE_RIGHT);

        // stuff for cancel/update popup
        Label time = new Label("Time:");
        time.setStyle("-fx-background-color: #b0dde4");
        time.setTextFill(Paint.valueOf("#286fb4"));
        Label date = new Label("Date:");
        date.setStyle("-fx-background-color: #b0dde4");
        date.setTextFill(Paint.valueOf("#286fb4"));
        Label appointmentService = new Label("Service:");
        appointmentService.setStyle("-fx-background-color: #b0dde4");
        appointmentService.setTextFill(Paint.valueOf("#286fb4"));


        HBox apptButtons = new HBox(10);
        JFXButton updateAppt = new JFXButton("Update Appointment");
        updateAppt.setStyle("-fx-background-color: #b0dde4");
        updateAppt.setButtonType(JFXButton.ButtonType.RAISED);
        updateAppt.setTextFill(Paint.valueOf("#286fb4"));
        JFXButton cancelAppt = new JFXButton("Cancel Appointment");
        cancelAppt.setButtonType(JFXButton.ButtonType.RAISED);
        cancelAppt.setStyle("-fx-background-color: #b0dde4");
        cancelAppt.setTextFill(Paint.valueOf("#286fb4"));
        apptButtons.getChildren().add(updateAppt);
        apptButtons.getChildren().add(cancelAppt);

        VBox makeAndCancelPopUp = new VBox(20);
        makeAndCancelPopUp.getChildren().add(time);
        makeAndCancelPopUp.getChildren().add(date);
        makeAndCancelPopUp.getChildren().add(appointmentService);
        makeAndCancelPopUp.getChildren().add(apptButtons);

        JFXPopup appointmentPopup1 = new JFXPopup();
        appointmentPopup1.setPopupContent(makeAndCancelPopUp);

        serviceCombo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StackPane secondaryLayout = new StackPane();
                Scene changeAppt = new Scene(secondaryLayout, 510, 250);

                secondaryLayout.getChildren().add(makeAndCancelPopUp);

                // New window (Stage)
                Stage newWindow1 = new Stage();
                newWindow1.setTitle("Change Appointment");
                newWindow1.setScene(changeAppt);

                // Specifies the modality for new window.
                newWindow1.initModality(Modality.WINDOW_MODAL);
                // Specifies the owner Window (parent) for new window
                newWindow1.initOwner(mainStage);

                // Set position of second window, related to primary window.
                newWindow1.setX(mainStage.getX() + 500);
                newWindow1.setY(mainStage.getY() + 300);

                newWindow1.show();
            }
        });

        change2= new HBox();
        change2.setPadding(new Insets(200,200,200,200));
        change2.setStyle("-fx-background-color: #B0DDE4");
        GridPane gridP= new GridPane();
        gridP.setHgap(100);
        gridP.setVgap(100);
        Label lblUserName = new Label("Username");
        textUserName= new JFXTextField();
        Label lblPassword= new Label("Password");
        pf=  new JFXPasswordField();
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

        mainScene = new Scene(change2,1440,810,colors[3]);
        ownerMainScreenBorderPane.setStyle("-fx-background-color: #B0DDE4;");
        mainStage.setScene(mainScene);
        mainScene.getStylesheets().add(FlexiBookPage.class.getResource("/css/main.css").toExternalForm());
        ownerMainScreenBorderPane.requestFocus();

        mainScene.setRoot(ownerMainScreenBorderPane);
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

        //Business info
        changeBuss = new HBox(10);
        changeBuss.setPadding(new Insets(100,100,100,100));
        changeBuss.setStyle("-fx-background-color: #B0DDE4;");
        GridPane busPane = new GridPane();
        busPane.setHgap(100);
        busPane.setVgap(100);
        JFXButton updateBusinessButton = new JFXButton("Update Business Info",signUp);
        updateBusinessButton.setOnAction(e->updateBusinessAction());
        JFXButton updateBusinessHoursButton = new JFXButton("Update Business Hours", signUp);
        JFXButton addBusinessHoursButton = new JFXButton("Add Business Hours", signUp);
        addBusinessHoursButton.setOnAction(e->addBusinessHourAction());
        JFXButton deleteBusinessHoursButton = new JFXButton("Delete Business Hours", signUp);
        deleteBusinessHoursButton.setOnAction(e->deleteBusinessHourAction());



        Label newBussName = new Label("Enter your new name!");
        updateBusName = new JFXTextField();
        changeBuss.getChildren().add(updateBusName);

        Label newBussEmail = new Label("Enter your new email!");
        updateBusEmail = new JFXTextField();
        changeBuss.getChildren().add(updateBusEmail);

        Label newBussAdd = new Label("Enter your new address!");
        updateBusAdd = new JFXTextField();
        changeBuss.getChildren().add(updateBusAdd);

        Label newBussPhone = new Label("Enter your new phone number!");
        updateBusPhone = new JFXTextField();
        changeBuss.getChildren().add(updateBusPhone);

        Label dayOfHour = new Label("Day:");
        addDay = new JFXComboBox<String>();
        addDay.getItems().add("Monday");
        addDay.getItems().add("Tuesday");
        addDay.getItems().add("Wednesday");
        addDay.getItems().add("Thursday");
        addDay.getItems().add("Friday");
        addDay.getItems().add("Saturday");
        addDay.getItems().add("Sunday");
        changeBuss.getChildren().add(addDay);

        Label startLabel = new Label("Start Time:");
        startTime = new JFXTextField();
        changeBuss.getChildren().add(startTime);

        Label endLabel = new Label("End Time:");
        endTime = new JFXTextField();
        changeBuss.getChildren().add(endTime);

        delBH = new JFXComboBox<String>();
        for(TOBusinessHour a: FlexiBookController.getBH()){
            delBH.getItems().add(a.getDay() + " " + a.getStartTime().toString() + " " + a.getEndTime().toString());
        }



        businessError = new Label("");
        businessError.getStyleClass().add("error-text");


        curBussName = new Label("Name: " + FlexiBookController.showBI().getName());
        curBussEmail = new Label("Email: " + FlexiBookController.showBI().getEmail());
        curBussAdd = new Label("Address: " + FlexiBookController.showBI().getAddress());
        curBussPN = new Label("Phone: " + FlexiBookController.showBI().getPhoneNumber());

        HBox b1 = new HBox(10);
        b1.getChildren().addAll(newBussName, updateBusName);
        HBox b2 = new HBox(10);
        b2.getChildren().addAll(newBussEmail, updateBusEmail);
        HBox b3 = new HBox(10);
        b3.getChildren().addAll(newBussAdd, updateBusAdd);
        HBox b4 = new HBox(10);
        b4.getChildren().addAll(newBussPhone, updateBusPhone);
        HBox b5 = new HBox(10);
        b5.getChildren().addAll(updateBusinessButton, businessError);
        HBox b6 = new HBox(10);
        b6.getChildren().addAll(dayOfHour, addDay,startLabel, startTime, endLabel, endTime);



        busPane.add(b1, 0, 0);
        busPane.add(b2, 0, 1);
        busPane.add(b3, 0, 2);
        busPane.add(b4, 0, 3);
        busPane.add(b5, 0, 4);
        busPane.add(b6, 0, 5);
        busPane.add(addBusinessHoursButton, 1, 5);
        busPane.add(delBH, 2, 4);
        busPane.add(deleteBusinessHoursButton, 2, 5);
        busPane.add(curBussName, 1, 0);
        busPane.add(curBussEmail, 1, 1);
        busPane.add(curBussAdd, 1, 2);
        busPane.add(curBussPN, 1, 3);
        busPane.add(backPage2, 2, 0);

        busPane.setAlignment(Pos.CENTER_LEFT);

        changeBuss.getChildren().add(busPane);

        backPage2.setOnAction(e->back());

        //cust business info
        changeBussCust = new HBox();
        changeBussCust.setPadding(new Insets(100,100,100,100));
        changeBussCust.setStyle("-fx-background-color: #B0DDE4;");
        GridPane busPane2 = new GridPane();
        curBussName2 = new Label("Name: " + FlexiBookController.showBI().getName());
        curBussEmail2 = new Label("Email: " + FlexiBookController.showBI().getEmail());
        curBussAdd2 = new Label("Address: " + FlexiBookController.showBI().getAddress());
        curBussPN2 = new Label("Phone: " + FlexiBookController.showBI().getPhoneNumber());
        busPane2.setHgap(100);
        busPane2.setVgap(100);
        busPane2.add(backPage3, 4, 0);
        busPane2.add(curBussName2, 3, 0);
        busPane2.add(curBussEmail2, 3, 1);
        busPane2.add(curBussAdd2, 3, 2);
        busPane2.add(curBussPN2, 3, 3);
        busPane2.setAlignment(Pos.CENTER_LEFT);
        changeBussCust.getChildren().add(busPane2);

        backPage3.setOnAction(e->back());
    }

    private void back() {
    	mainScene.setRoot(ownerMainScreenBorderPane);

    }

    private void signUp() {

     	try{
    		
        	String username = textUserName1.getText();
        	String password = pf1.getText();
            FlexiBookController.customerSignUp(username, password);
            mainScene.setRoot(ownerMainScreenBorderPane);
            System.out.println("SignUp Successful");
            System.out.println("Username = " + username + "," + "Password = " + password);
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
            System.out.println(username + "," + newUsername  + "," + newPassword);
        }
        catch(Exception e){
            e.getMessage();
        }

    }

   /* private void updateBusiness() {
        try{
            String newName = updateBusName.getText();
            String newEmail = updateBusEmail.getText();
            String newAddress = updateBusAdd.getText();
            String newPhone = updateBusPhone.getText();
            FlexiBookController.updateBusinessInfo(newName,newAddress, newPhone, newEmail,null,null,null,null,null,null,null,null,null,null,null,true,false,false,false,false,false,false,false,false,false);
            //System.out.println(newName);
        }
        catch(Exception e){
            e.getMessage();
        }
    }*/

    private void deleteAcc(){

        try{
        	String username = FlexiBookApplication.getUser().getUsername();
            FlexiBookController.deleteCustomerAccount(username);
            System.out.println("Delete successfull");
        }
        catch(Exception e){
            e.getMessage();
        }

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
            
            FlexiBookController.login(textUserName.getText(),pf.getText());
            mainScene.setRoot(ownerMainScreenBorderPane);
        }
        catch(InvalidInputException e){
             e.getMessage();
            Label error = new Label("invalid username/password");
            error.setTextFill(Color.RED);
            gridP.add(error,1,4);
            textUserName.setText("");
            pf.setText("");
        }
    }

    private void refreshBusiness(){
        updateBusName.setText("");
        updateBusEmail.setText("");
        updateBusAdd.setText("");
        updateBusPhone.setText("");
        curBussName.setText("Name: " + FlexiBookController.showBI().getName());
        curBussEmail.setText("Email: " + FlexiBookController.showBI().getEmail());
        curBussAdd.setText("Address: " + FlexiBookController.showBI().getAddress());
        curBussPN.setText("Phone: " + FlexiBookController.showBI().getPhoneNumber());
        startTime.setText("");
        endTime.setText("");
        delBH.getItems().clear();
        for(TOBusinessHour a: FlexiBookController.getBH()){
            delBH.getItems().add(a.getDay() + " " + a.getStartTime().toString() + " " + a.getEndTime().toString());
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
        }
        calendarTop.getChildren().add(rightArrowButton);
        List<String> list = javafx.scene.text.Font.getFamilies();
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
                if(owner){
                    calendarEntry.setOnAction(this::updateDailySchedule);
                }
                else{
                    calendarEntry.setOnAction(this::customerDailySchedule);
                }
                entry.add(calendarEntry);
                days.add(calendarEntry,j,i);
            }
        }
        calendarMain.getChildren().add(days);
    return calendar;
    }
    private void startAppointmentEvent(ActionEvent event){
        error = null;
        try{
            FlexiBookController.startAppointment(currentAppointment);
        }
        catch (InvalidInputException e){
            errorMessageAppointmentCalendar.setText(e.getMessage());
        }

    }
    private void endAppointmentEvent(ActionEvent event){
        error = null;
        try{
            FlexiBookController.endAppointment(currentAppointment);
        }
        catch (InvalidInputException e){
            errorMessageAppointmentCalendar.setText(e.getMessage());
        }
    }
    private void registerNoShowEvent(ActionEvent event){
        error =null;
        try{
            FlexiBookController.registerNoShow(currentAppointment);
            updateDate(listDays,calendarYearOwner,calendarMonthOwner);
            refreshDailyAppointments(FlexiBookController.getAppointmentCalendar(generateLocalDate(renderDate)));
        }
        catch (InvalidInputException e){
            errorMessageAppointmentCalendar.setText(e.getMessage());
        }
    }
    private void switchToOwnerAppointment(){
        mainScene.setRoot(ownerAppointmentCalendar);
        updateDate(listDays,calendarYearOwner,calendarMonthOwner);
    }

    private void switchToCustomerAppointment(){
        mainScene.setRoot(customerAppointmentCalendar);
        updateDate(listDays,calendarYearCustomer,calendarMonthCustomer);
    }

    private void switchToBusinessCust(){
        mainScene.setRoot(changeBussCust);

    }

    private void switchToBusiness(){
        //setUpBusinessPage();
       mainScene.setRoot(changeBuss);
    }
    private void switchToServices(){
        setUpServicePage();
        mainScene.setRoot(servicePage);
    }
    private void updateDailySchedule(ActionEvent event){
        if(event.getTarget() instanceof CalendarEntry){
            LocalDate date = ((CalendarEntry) event.getTarget()).getDate();
            try{
                refreshDailyAppointments(FlexiBookController.getAppointmentCalendar(generateLocalDate(date)));
            }
            catch (InvalidInputException e){
                error = e.getMessage();
            }
        }
    }

    private List<TOAppointmentCalendarItem> customerDailySchedule(ActionEvent e){
        if(e.getTarget() instanceof CalendarEntry){
            LocalDate date = ((CalendarEntry) e.getTarget()).getDate();
            try{
                refreshDailyAppointments(FlexiBookController.getAppointmentCalendar(generateLocalDate(date)));
            }
            catch (InvalidInputException errr){
                error = errr.getMessage();
            }
        }
        return null;
    }

    private void switchToHomeScreen(){
        mainScene.setRoot(change2);
    }
    private void switchToAccount(){
        mainScene.setRoot(changeAcc);
    }

    private void switchToCustomerAccount(){}
    private void refreshDailyAppointments(List<TOAppointmentCalendarItem> calendarItems){
        dailyAppointmentTable.getItems().clear();
        if(calendarItems != null ){
            for(TOAppointmentCalendarItem item:calendarItems){
                dailyAppointmentTable.getItems().add(new DayEvent(item));
            }
        }
    }
    private void updateDate(ArrayList<CalendarEntry> list,Label year,Label month){
        list.get(15).getStyleClass().add("calendar-holiday");
        year.setText(String.valueOf(renderDate.getYear()));
        month.setText(String.valueOf(renderDate.getMonth()));
        LocalDate calendarDate = LocalDate.of(renderDate.getYear(), renderDate.getMonthValue(), 1);
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }
        for(CalendarEntry c:list){
            c.setStyle(" -fx-background-color: #E2F0F9");
            c.setDate(calendarDate);
            c.setText(String.valueOf(calendarDate.getDayOfMonth()));
            c.getStyleClass().removeAll("calendar-appointment-present","not-in-month");
            try{
                List<TOAppointmentCalendarItem> list1 = FlexiBookController.getAppointmentCalendar(generateLocalDate(calendarDate));
                if(list1.size() >0){
                    c.setStyle("-fx-background-color: #03c04A80");
                }
            }
            catch (InvalidInputException e){
                error = e.getMessage();
            }
            calendarDate = calendarDate.plusDays(1);
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
            updateDate(dbvDays,calendarYearCustomer,calendarMonthCustomer);
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

    private void setUpBusinessPage(){

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
        serviceNameInput = new JFXTextField();
        downtimeDurationInput = new JFXTextField();
        Label duration = new Label("Duration:");
        duration.getStyleClass().add("service-text");
        duration.setAlignment(Pos.CENTER_RIGHT);
        Label downtimeStart = new Label("Downtime Start:");
        downtimeStart.getStyleClass().add("service-text");
        downtimeStart.setAlignment(Pos.CENTER_RIGHT);
        durationInput = new JFXTextField();
        downtimeStartInput = new JFXTextField();
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
        existingServices = new JFXComboBox<String>();
        existingServices.getStyleClass().add("combo-box");
        serviceNameInput1 = new JFXTextField();
        downtimeDurationInput1 = new JFXTextField();
        durationInput1 = new JFXTextField();
        downtimeStartInput1 = new JFXTextField();
        Button updateServiceButton = new Button("Update Service");
        updateServiceButton.getStyleClass().add("service-button");
        updateServiceButton.setOnAction(e->updateServiceAction());
        Label serviceToDelete = new Label("Service:");
        serviceToDelete.getStyleClass().add("service-text");
        serviceToDelete.setAlignment(Pos.CENTER_RIGHT);
        existingServices1 = new JFXComboBox<String>();
        Button deleteServiceButton = new Button("Delete Service");
        deleteServiceButton.getStyleClass().add("service-button");
        deleteServiceButton.setOnAction(e->deleteServiceAction());
        Label spacing = new Label(" ");
        spacing.getStyleClass().add("service-text");
        serviceError = new Label("");
        serviceError.getStyleClass().add("error-text");
        Button viewServices = new Button("View Available Services");
        viewServices.getStyleClass().add("service-button");
        viewServices.setOnAction(e->switchToAvailableServices());

        FontIcon back = new FontIcon("dashicons-arrow-left-alt");
        back.getStyleClass().add("icon");

        JFXButton backButton = new JFXButton("Back", back);
        backButton.setContentDisplay(ContentDisplay.TOP);
        backButton.getStyleClass().add("main-menu-button");
        backButton.setOnAction(e->back());

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
        HBox row8 = new HBox(10);
        row8.getChildren().add(viewServices);
        row8.setAlignment(Pos.CENTER);
        row8.setTranslateX(75);

        VBox space = new VBox(20);
        space.setAlignment(Pos.CENTER);
        space.getChildren().addAll(row4, row1, row5, row2, row6, row3, row7, row8);

        ImageView view1 = new ImageView(image);

        HBox topRight = new HBox();
        topRight.setAlignment(Pos.TOP_LEFT);
        topRight.getChildren().add(view1);

        HBox topLeft = new HBox();
        topLeft.setAlignment(Pos.TOP_CENTER);
        topLeft.getChildren().add(backButton);
        topLeft.setTranslateX(-140);

        servicePage.getChildren().addAll(topLeft, space, topRight);
        refreshData();
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
                FlexiBookController.addService("owner", serviceNameInput.getText(), Integer.parseInt(durationInput.getText()),
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
                FlexiBookController.updateService("owner", existingServices.getValue(), serviceNameInput1.getText(), Integer.parseInt(durationInput1.getText()),
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
                FlexiBookController.deleteService("owner", existingServices1.getValue());
            } catch (InvalidInputException e) {
                serviceError.setText(e.getMessage());
            }
        }
        refreshData();
    }
    
    private void switchToAvailableServices(){
        setAvailableServices();
        mainScene.setRoot(availableServicesPage);
    }

    private void setAvailableServices(){
        availableServicesPage = new HBox();
        availableServicesPage.setAlignment(Pos.CENTER);
        availableServicesPage.setStyle("-fx-background-color: #B0DDE4;");

        Label availableServices = new Label("Available Services");
        availableServices.getStyleClass().add("service-heading");
        availableServices.setAlignment(Pos.CENTER);

        FontIcon back = new FontIcon("dashicons-arrow-left-alt");
        back.getStyleClass().add("icon");

        JFXButton backButton = new JFXButton("Back", back);
        backButton.setContentDisplay(ContentDisplay.TOP);
        backButton.getStyleClass().add("main-menu-button");
        backButton.setOnAction(e->backToServices());

        TableView<TOService> serviceTable;

        //Columns
        TableColumn<TOService, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(200);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<TOService, Integer> durationCol = new TableColumn<>("Duration");
        durationCol.setMinWidth(100);
        durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));

        TableColumn<TOService, Integer> downtimeDurationCol = new TableColumn<>("Downtime Duration");
        downtimeDurationCol.setMinWidth(200);
        downtimeDurationCol.setCellValueFactory(new PropertyValueFactory<>("downtimeDuration"));

        TableColumn<TOService, Integer> downtimeStartCol = new TableColumn<>("Downtime Start");
        downtimeStartCol.setMinWidth(100);
        downtimeStartCol.setCellValueFactory(new PropertyValueFactory<>("downtimeStart"));

        serviceTable = new TableView<>();
        serviceTable.setItems(getService());
        serviceTable.setMinWidth(495);
        serviceTable.setMinHeight(700);
        serviceTable.getColumns().addAll(nameCol, durationCol, downtimeDurationCol, downtimeStartCol);

        HBox top = new HBox();
        top.getChildren().addAll(backButton, availableServices);

        VBox row = new VBox();
        row.setAlignment(Pos.TOP_CENTER);
        backButton.setTranslateX(-475);
        availableServices.setTranslateX(-20);
        row.getChildren().addAll(top, serviceTable);

        availableServicesPage.getChildren().add(row);
    }

    private ObservableList<TOService> getService() {
        ObservableList<TOService> services = FXCollections.observableArrayList();

        for (TOService s : FlexiBookController.getServices()){
            services.add(s);
        }
        return services;
    }

    private void backToServices(){
        mainScene.setRoot(servicePage);
    }

    private void updateBusinessAction(){
        businessError.setText("");
        if(updateBusName.getText().length() == 0){
            businessError.setText("All fields must be completed!");
        }
        if(updateBusEmail.getText().length() == 0) {
            businessError.setText("All fields must be completed!");
        }
        if(updateBusAdd.getText().length() == 0) {
            businessError.setText("All fields must be completed!");
        }
        if(updateBusPhone.getText().length() == 0) {
            businessError.setText("All fields must be completed!");
        }
        if(businessError.getText().length() == 0){
            try{
                String name = updateBusName.getText();
                String email = updateBusEmail.getText();
                String address = updateBusAdd.getText();
                String pn = updateBusPhone.getText();
                FlexiBookController.updateBusinessInfo(name, address, pn, email, null, null, null, null, null,  null, null, null, null, null, null, true,false,false,false,false,false,false,false,false,false);
            }
            catch(InvalidInputException e){
                businessError.setText(e.getMessage());
            }
        }
        refreshBusiness();

    }
     private void addBusinessHourAction(){
        businessError.setText("");
        if(!startTime.getText().matches("^([0-2][0-9]):[0-5][0-9]$")){
            businessError.setText("Please Enter 24 Hour Time, ex: 06:00");
        }if(!endTime.getText().matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")) {
            businessError.setText("Please Enter 24 Hour Time, ex: 06:00");
        }
        if(addDay.getItems().size()==0){
            businessError.setText("Please select a day");
        }
        if(businessError.getText().length()==0) {
            try {
                String day = addDay.getValue();
                String st = startTime.getText();
                String et = endTime.getText();
                FlexiBookController.setUpBusinessHour(day, st, et);
            } catch (InvalidInputException e) {
                businessError.setText(e.getMessage());
            }
        }
        refreshBusiness();

    }

    private void deleteBusinessHourAction(){

        businessError.setText("");
        if(delBH.getItems().size() == 0){
            businessError.setText("No Hours Selected");
        }
        if(businessError.getText().length() ==0){
            String delims = "[ ]+";
            String[] tokens = delBH.getValue().split(delims);

            FlexiBookController.deleteBusinessHours(tokens[0], tokens[1]);
        }
        refreshBusiness();

    }
}
