package ca.mcgill.ecse.flexibook.view;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
import ca.mcgill.ecse.flexibook.controller.TOAppointmentCalendarItem;

import ca.mcgill.ecse.flexibook.controller.TOService;
import com.jfoenix.controls.*;
import javafx.application.Application;
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
//import sun.plugin2.util.ColorUtil;


//import java.awt.*;
import javax.swing.*;
import javax.xml.soap.Text;
import java.sql.Date;
import java.sql.Time;
import java.text.CollationElementIterator;

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
    TextField textUserName;
    PasswordField pf;
    ArrayList<CalendarEntry> listDays = new ArrayList<>();
    ArrayList<CalendarEntry> dbvDays = new ArrayList<>();
    LocalDate renderDate;
    HBox change2;
    Label calendarMonthOwner;
    Label calendarYearOwner;
    Label calendarMonthCustomer;
    Label calendarYearCustomer;
    //String username = "f10na_ryan";
    TableView<DayEvent> dailyAppointmentTable;
    String username = null;
    JFXTextField updateUsername;
    JFXPasswordField updatePassword;
    TextField textUserName1;
    PasswordField pf1;
    VBox makeAndCancelPopUp;
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
    private HBox changeAcc;
    TableView.TableViewSelectionModel<DayEvent> selectionModel;
    private TilePane appointmentDetails;
    TOAppointmentCalendarItem currentAppointment = null;
    boolean notStarted = true;
    FontIcon startAppointmentIcon;
    FontIcon endAppointmentIcon;
    JFXButton startAppointment;




    public void start(Stage s){
        mainStage = s;
        mainStage.setTitle("FlexiBook Application");
        renderDate = LocalDate.now();
        FlexiBookController.testAppointment();
        initComponents();
        mainStage.show();
    }
    public FlexiBookPage(){}
    private void initComponents(){
        //Main Screen
        ownerMainScreenBorderPane = new BorderPane();
        Label welcome = new Label("Welcome, User");
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

        HBox individualAppointment = new HBox();
        appointments.getChildren().add(individualAppointment);
        individualAppointment.prefWidthProperty().bind(dailyAppointmentTable.widthProperty());
        appointmentDetails = new TilePane();
        VBox appointmentButtons = new VBox();

        individualAppointment.getChildren().add(appointmentDetails);
        individualAppointment.getChildren().add(appointmentButtons);
        appointmentDetails.prefWidthProperty().bind(individualAppointment.widthProperty().multiply(0.7));
        appointmentButtons.prefWidthProperty().bind(individualAppointment.widthProperty().multiply(0.3));
        appointmentDetails.setPrefColumns(2);
        appointmentDetails.setVisible(false);

        startAppointmentIcon = new FontIcon("eli-play-circle");
        startAppointmentIcon.getStyleClass().add("icon-start-buttons");

        endAppointmentIcon = new FontIcon("ri-stop-sign");
        endAppointmentIcon.getStyleClass().add("icon-start-buttons");

        startAppointment = new JFXButton("Start Appointment",startAppointmentIcon);
        startAppointment.setContentDisplay(ContentDisplay.TOP);
        startAppointment.getStyleClass().add("appointment-start-buttons");
        startAppointment.setDisable(true);
        startAppointment.setOnAction(this::startAppointmentEvent);
        startAppointment.getStyleClass().add("appointment-start-buttons");
        appointmentButtons.getChildren().add(startAppointment);

        FontIcon registerNoShowIcon = new FontIcon("enty-eye-with-line");
        registerNoShowIcon.getStyleClass().add("icon-start-buttons");
        //registerNoShowIcon.setStyle("-fx-pref-width: 150px;");

        JFXButton registerNoShow = new JFXButton("Register No Show",registerNoShowIcon);
        registerNoShow.setContentDisplay(ContentDisplay.TOP);
        registerNoShow.setOnAction(this::registerNoShowEvent);
        registerNoShow.setDisable(true);
        registerNoShow.getStyleClass().add("appointment-start-buttons");
        appointmentButtons.getChildren().add(registerNoShow);

        ObservableList<DayEvent> observableList = selectionModel.getSelectedItems();
        observableList.addListener((ListChangeListener<DayEvent>) c -> {
            while(c.next()) {
                if (!c.wasPermutated()) {
                    for (DayEvent removeitem : c.getRemoved()) {
                        appointmentDetails.setVisible(false);
                        registerNoShow.setDisable(true);
                        startAppointment.setDisable(true);
                        currentAppointment = null;
                    }
                    for (DayEvent additem : c.getAddedSubList()) {
                        currentAppointment = additem.getAppointment();
                        ((Label)appointmentDetails.getChildren().get(1)).setText(additem.getAppointment().getUsername());
                        ((Label)appointmentDetails.getChildren().get(3)).setText(additem.getStartTime());
                        ((Label)appointmentDetails.getChildren().get(5)).setText(additem.getEndTime());
                        ((Label)appointmentDetails.getChildren().get(7)).setText(additem.getAppointment().getMainService());
                        registerNoShow.setDisable(false);
                        startAppointment.setDisable(false);
                        appointmentDetails.setVisible(true);
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
        makeApptButton.setStyle("-fx-background-color: #e2f0f9");
        makeApptButton.setButtonType(JFXButton.ButtonType.RAISED);
        makeApptButton.setOpacity(0.8);
        makeApptButton.setTextFill(Paint.valueOf("#286fb4"));

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
                secondaryLayout.setStyle("-fx-background-color: #b0dde4;");
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

        //appointment info

        HBox rightSide = new HBox(200);

        VBox appointmentLabels = new VBox(10);
        Label apptInfo = new Label("Appointment Information");
        apptInfo.setTextFill(Paint.valueOf("#286fb4"));
        apptInfo.setScaleX(1.5);
        apptInfo.setScaleY(1.5);
        apptInfo.setStyle("-fx-text-color: #286fb4");
        Label timeLabel = new Label("Time:");
        timeLabel.setTextFill(Paint.valueOf("#286fb4"));
        Label dateLabel = new Label("Date:");
        dateLabel.setTextFill(Paint.valueOf("#286fb4"));
        Label serviceLabel = new Label("Service:");
        serviceLabel.setTextFill(Paint.valueOf("#286fb4"));
        appointmentLabels.setMinWidth(200);

        appointmentLabels.getChildren().addAll(apptInfo,timeLabel,dateLabel,serviceLabel);

        appointmentLabels.setAlignment(Pos.CENTER_LEFT);
        rightSide.getChildren().add(appointmentLabels);

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


        HBox timeBox = new HBox(10);
        Label time = new Label("Time:");
        time.setTextFill(Paint.valueOf("#286fb4"));
        TextField timeField = new TextField();
        timeBox.getChildren().addAll(time,timeField);
        timeBox.setAlignment(Pos.CENTER);

        HBox dateBox = new HBox(10);
        Label date = new Label("Date:");
        date.setTextFill(Paint.valueOf("#286fb4"));
        TextField dateField = new TextField();
        dateBox.getChildren().addAll(date,dateField);
        dateBox.setAlignment(Pos.CENTER);

        HBox serviceBox = new HBox(10);
        Label appointmentService = new Label("Service:");
        appointmentService.setTextFill(Paint.valueOf("#286fb4"));
        TextField serviceField = new TextField();
        serviceBox.getChildren().addAll(appointmentService,serviceField);
        serviceBox.setAlignment(Pos.CENTER);

        HBox apptButtons = new HBox(10);
        JFXButton updateAppt = new JFXButton("Update Appointment");
        updateAppt.setStyle("-fx-background-color: #e2F0F9");
        updateAppt.setButtonType(JFXButton.ButtonType.RAISED);
        updateAppt.setTextFill(Paint.valueOf("#286fb4"));

        apptButtons.setAlignment(Pos.CENTER);


        JFXButton cancelAppt = new JFXButton("Cancel Appointment");
        cancelAppt.setButtonType(JFXButton.ButtonType.RAISED);
        cancelAppt.setStyle("-fx-background-color: #e3f0f9");
        cancelAppt.setTextFill(Paint.valueOf("#286fb4"));
        apptButtons.getChildren().addAll(updateAppt,cancelAppt);

        makeAndCancelPopUp = new VBox(20);
        makeAndCancelPopUp.getChildren().addAll(timeBox,dateBox,serviceBox,apptButtons);

        makeAndCancelPopUp.setAlignment(Pos.CENTER);

        JFXPopup appointmentPopup1 = new JFXPopup();
        appointmentPopup1.setPopupContent(makeAndCancelPopUp);



        // something else

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
            mainScene.setRoot(customerScreenBorderPane);
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
        ownerMainScreenBorderPane.setStyle("-fx-background-color: #B0DDE4;");
        mainStage.setScene(mainScene);
        mainScene.getStylesheets().add(FlexiBookPage.class.getResource("/css/main.css").toExternalForm());
        ownerMainScreenBorderPane.requestFocus();

        mainScene.setRoot(customerScreenBorderPane);
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
    	mainScene.setRoot(ownerMainScreenBorderPane);

    }

    private void signUp() {

        mainScene.setRoot(ownerMainScreenBorderPane);

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
                    calendarEntry.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            StackPane secondaryLayout = new StackPane();
                            Scene changeAppt = new Scene(secondaryLayout, 510, 250);

                            secondaryLayout.setStyle("-fx-background-color: #b0dde4;");
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
            if(notStarted){
                FlexiBookController.startAppointment(currentAppointment);
                notStarted = false;
                startAppointment.setGraphic(endAppointmentIcon);
                startAppointment.setText("End Appointment");
            }
            else{
                FlexiBookController.endAppointment(currentAppointment);
                notStarted = true;
                startAppointment.setGraphic(startAppointmentIcon);
                startAppointment.setText("Start Appointment");
            }

        }
        catch (InvalidInputException e){
            error = e.getMessage();
        }

    }
    private void registerNoShowEvent(ActionEvent event){
        error =null;
        try{
            FlexiBookController.registerNoShow(null,null);
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
        updateDate(dbvDays,calendarYearCustomer,calendarMonthCustomer);
    }

    private void switchToBusiness(){
        //mainStage.setScene(businessInfo);
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
        if(calendarItems == null ){

        }
        else{
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
        homeButton.setOnAction(e->back());

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
}
