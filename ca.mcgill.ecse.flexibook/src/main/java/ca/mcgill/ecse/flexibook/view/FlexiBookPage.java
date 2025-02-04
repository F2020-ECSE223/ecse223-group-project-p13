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
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;


//import java.awt.*;

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
    JFXTextField updateBusName2;
    JFXTextField updateBusEmail2;
    JFXTextField updateBusAdd2;
    JFXTextField updateBusPhone2;
    JFXComboBox<String> addDay;
    JFXComboBox<String> delBH;
    JFXTextField startTime;
    JFXTextField endTime;
    Label businessError;
    Label businessError2;
    Label curBussName;
    Label curBussEmail;
    Label curBussAdd;
    Label curBussPN;
    Label curBussName2;
    Label curBussEmail2;
    Label curBussAdd2;
    Label curBussPN2;
    Label loginError;
    JFXTextField textUserName1;
    JFXPasswordField pf1;
    private GridPane gridP2;
    private GridPane pane;
    private Label errorMsg;
    private GridPane gridP;
    VBox makeAndCancelPopUp;
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
    private HBox addBuss;
    private HBox changeBussCust;
    TableView.TableViewSelectionModel<DayEvent> selectionModel;
    private TilePane appointmentDetails;
    TOAppointmentCalendarItem currentAppointment = null;
    HBox availableServicesPage;
    Label errorMessageAppointmentCalendar;
    int j = 0;
    JFXButton cancelAppt = new JFXButton("Cancel Appointment");
    JFXButton updateAppt = new JFXButton("Update Appointment");
    JFXComboBox<Label> serviceChooser = new JFXComboBox<Label>();
    JFXDatePicker datePicker = new JFXDatePicker();
    JFXTimePicker timePicker1 = new JFXTimePicker();
    JFXComboBox removeAdd = new JFXComboBox();
    Stage changeAppointment = new Stage();
    StackPane layoutChoose = new StackPane();
    HBox timeBox = new HBox(10);
    HBox dateBox = new HBox(10);
    HBox serviceBox = new HBox(10);
    Label appointmentError = new Label("");
    Stage chooseAppointment = new Stage();
    Scene chooseAppt = new Scene(layoutChoose, 500, 200);
    StackPane layoutChange = new StackPane();
    Scene changeAppt = new Scene(layoutChange, 650, 300);
    TableView appointmentTable = new TableView<>();
    //JFXComboBox<Label> services = new JFXComboBox<Label>();

    /**
     * Starts the application and sets up the components
     * @author Tomasz Mroz
     */
    public void start(Stage s){
        mainStage = s;
        mainStage.setResizable(false);
        chooseAppointment.initOwner(mainStage);
        layoutChoose.getChildren().add(appointmentTable);
        chooseAppointment.initModality(Modality.APPLICATION_MODAL);

        changeAppointment.initOwner(mainStage);
        //FlexiBookController.testAppointment();
        mainStage.setTitle("FlexiBook Application");
        renderDate = LocalDate.now();
        initComponents();
        mainStage.show();
    }

    /**
     * Sets up most of the components for the application
     */
    private void initComponents(){
        //Main Screen
        ownerMainScreenBorderPane = new BorderPane();
        Label welcome = new Label("Welcome, Owner");
        HBox top = new HBox();
        ownerMainScreenBorderPane.setTop(top);
        top.setAlignment(Pos.CENTER_RIGHT);
        top.getChildren().add(welcome);
        welcome.getStyleClass().add("user-text");

        /*ImageView imageView = null;
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
        top.getChildren().add(imageView);*/
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
        FontIcon homeIcon = new FontIcon("dashicons-admin-home");

        loginIcon.getStyleClass().add("icon");
        logoutIcon.getStyleClass().add("icon");
        signUp.getStyleClass().add("icon");
        appointmentIcon.getStyleClass().add("icon-main-menu");
        accountIcon.getStyleClass().add("icon-main-menu");
        businessIcon.getStyleClass().add("icon-main-menu");
        serviceIcon.getStyleClass().add("icon-main-menu");
        delete.getStyleClass().add("icon");
        back.getStyleClass().add("icon");
        homeIcon.getStyleClass().add("icon");

        JFXButton backPage = new JFXButton("Back", back);
        JFXButton backPage2 = new JFXButton("Back", back);
        JFXButton backPage3 = new JFXButton("Back", back);

        JFXButton logoutButton1 = new JFXButton("LogOut", logoutIcon);
        logoutButton1.setContentDisplay(ContentDisplay.BOTTOM);
        logoutButton1.getStyleClass().add("main-menu-button");
        logoutButton1.setOnAction(e->logout());
        bottom.getChildren().add(logoutButton1);
        
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
        Label welcome1 = new Label("Welcome,User");
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
        
        JFXButton logoutButton = new JFXButton("LogOut", logoutIcon);
        logoutButton.setContentDisplay(ContentDisplay.BOTTOM);
        logoutButton.getStyleClass().add("main-menu-button");
        logoutButton.setOnAction(e->logout());
  
        
        HBox bottom1 = new HBox();
        customerScreenBorderPane.setBottom(bottom1);
        bottom1.setAlignment(Pos.BASELINE_RIGHT);
        bottom1.getChildren().add(logoutButton);

        HBox buttons1 = new HBox(75);

        buttons1.setAlignment(Pos.CENTER);

        FontIcon appointmentIcon1 = new FontIcon("eli-calendar");
        FontIcon accountIcon1 = new FontIcon("dashicons-businessperson");
        FontIcon businessIcon1 = new FontIcon("icm-briefcase");

        appointmentIcon1.getStyleClass().add("icon-main-menu");
        accountIcon1.getStyleClass().add("icon-main-menu");
        businessIcon1.getStyleClass().add("icon-main-menu");


        JFXButton appointmentButton1 = new JFXButton("Appointments",appointmentIcon1);
        appointmentButton1.setContentDisplay(ContentDisplay.TOP);

        appointmentButton1.getStyleClass().add("main-menu-button");
        buttons1.getChildren().add(appointmentButton1);

        JFXButton accountButton1 = new JFXButton("Account",accountIcon1);
        accountButton1.setContentDisplay(ContentDisplay.TOP);
        accountButton1.setOnAction(e->switchToAccount());
        accountButton1.getStyleClass().add("main-menu-button");
        buttons1.getChildren().add(accountButton1);

        JFXButton businessButton1 = new JFXButton("Business", businessIcon1);
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

        dailyAppointmentTable.getSortOrder().add(column1);

        column3.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        dailyAppointmentTable.getColumns().addAll(column1,column2,column3);
        selectionModel = dailyAppointmentTable.getSelectionModel();

        dailyAppointmentTable.setPlaceholder(new Label("Business Not Open Today"));
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
        startAppointment.setOnAction(this::startAppointmentEvent);
        startAppointment.setDisable(true);
        startAppointment.getStyleClass().add("appointment-start-buttons");
        appointmentButtons.getChildren().add(startAppointment);

        JFXButton endAppointment  = new JFXButton("End Appointment",endAppointmentIcon);
        endAppointment.setContentDisplay(ContentDisplay.TOP);
        endAppointment.getStyleClass().add("appointment-start-buttons");
        endAppointment.setOnAction(this::endAppointmentEvent);
        endAppointment.setDisable(true);
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
        homeButton.setOnAction(event -> {
            mainScene.setRoot(ownerMainScreenBorderPane);
            ownerMainScreenBorderPane.requestFocus();
        });
        appointmentButtons.getChildren().add(homeButton);

        errorMessageAppointmentCalendar = new Label();
        errorMessageAppointmentCalendar.getStyleClass().add("owner-error-message");
        errorMessageAppointmentCalendar.setVisible(true);
        individualAppointment.getChildren().add(errorMessageAppointmentCalendar);
        AnchorPane.setBottomAnchor(errorMessageAppointmentCalendar,2.0);
        AnchorPane.setLeftAnchor(errorMessageAppointmentCalendar,2.0);


        AnchorPane.setTopAnchor(startAppointment,5.0);
        AnchorPane.setTopAnchor(endAppointment,100.0);
        AnchorPane.setTopAnchor(registerNoShow,195.0);
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
                        endAppointment.setDisable(true);
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
                            endAppointment.setDisable(false);
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
        //((Label)appointmentDetails.getChildren().get(8)).setText("Chosen Items: ");



        customerAppointmentCalendar = new HBox();
        customerAppointmentCalendar.getChildren().add(setCalendar(dbvDays,false));
        customerAppointmentCalendar.setStyle("-fx-background-color: #B0DDE4;");


        //Make appointment stuff
        HBox choosingServices = new HBox(10);
        choosingServices.setAlignment(Pos.CENTER);
        //service
        JFXComboBox<Label> services = new JFXComboBox<Label>();

        appointmentButton1.setOnAction(e->{
            services.getItems().clear();
            serviceChooser.getItems().clear();
            for(TOService s: FlexiBookController.getServices()) {
                services.getItems().add(new Label(s.getName()));
                serviceChooser.getItems().add(new Label(s.getName()));
            }
            serviceChooser.setPromptText("Choose Service");
            services.setPromptText("Choose Service");

            switchToCustomerAppointment();
        });
        for(TOService s: FlexiBookController.getServices()) {
            services.getItems().add(new Label(s.getName()));
        }
        services.setPromptText("Choose Service");
        serviceChooser.setPromptText("Choose Service");


        choosingServices.getChildren().add(services);

        // make appt button and date picker
        VBox datePickBox = new VBox(40);

        //label
        Label makeAppointment = new Label("Make an Appointment");
        makeAppointment.setTextFill(Paint.valueOf("#286fb4"));
        datePickBox.getChildren().add(makeAppointment);
        //makeAppointment.setStyle("-fx-background-color: #e2f0f9");
        makeAppointment.setScaleX(2);
        makeAppointment.setScaleY(2);

        //time and date box
        HBox timeDateBox = new HBox(10);

        //date picker
        JFXDatePicker appointmentDatePicker = new JFXDatePicker();
        appointmentDatePicker.setPromptText("Select Date");

        //time picker
        JFXTimePicker makeTimePicker = new JFXTimePicker();
        makeTimePicker.setPromptText("Select Time");

        //add date and time picker to box
        timeDateBox.getChildren().addAll(appointmentDatePicker, makeTimePicker);

        //add to vbox
        datePickBox.getChildren().add(timeDateBox);

        JFXButton makeApptButton = new JFXButton("Make Appointment");
        makeApptButton.setStyle("-fx-background-color: #e2f0f9");
        makeApptButton.setButtonType(JFXButton.ButtonType.RAISED);
        makeApptButton.setOpacity(0.8);
        makeApptButton.setTextFill(Paint.valueOf("#286fb4"));

        makeApptButton.setOnAction(event -> {
            try {
                appointmentError.setText("");
                String s = FlexiBookApplication.getUser().getUsername();
                FlexiBookController.makeAppointment(FlexiBookApplication.getUser().getUsername(), String.valueOf(appointmentDatePicker.getValue()),
                        String.valueOf(makeTimePicker.getValue()), String.valueOf(services.getSelectionModel().getSelectedItem().getText()),
                       null);

                updateDate(dbvDays,calendarYearCustomer,calendarMonthCustomer,false);

            } catch (InvalidInputException e) {
                appointmentError.setText(e.getMessage());
                appointmentError.setVisible(true);
                appointmentError.setStyle("-fx-background-color: #ffcccb");
                datePickBox.getChildren().add(appointmentError);
            }
            catch (NullPointerException e){
                appointmentError.setText("Selected Values cannot be Empty");
                appointmentError.setVisible(true);
                appointmentError.setStyle("-fx-background-color: #ffcccb");
                datePickBox.getChildren().add(appointmentError);
            }
        });
        makeApptButton.setAlignment(Pos.CENTER);

        datePickBox.getChildren().addAll(appointmentDatePicker, makeTimePicker, choosingServices, makeApptButton, appointmentError);
        appointmentError.setStyle("-fx-background-color: #ffcccb");
        datePickBox.setAlignment(Pos.CENTER);


        //appointment info
        JFXButton home = new JFXButton("", homeIcon);
        home.setScaleY(3);
        home.setScaleX(3);

        home.setOnAction(event -> {
            mainScene.setRoot(customerScreenBorderPane);
        });

        //placing home button
        AnchorPane rightPane = new AnchorPane();
        AnchorPane.setBottomAnchor(home, 15.0);
        AnchorPane.setRightAnchor(home, 0.0);
        rightPane.setPrefHeight(300);
        rightPane.getChildren().add(home);


        customerAppointmentCalendar.getChildren().add(rightPane);

        //right side layout

        StackPane rightSide = new StackPane();

        rightSide.getChildren().add(datePickBox);

        rightSide.setAlignment(Pos.CENTER);
        rightSide.setPrefHeight(200);
        rightSide.setPrefWidth(550);

        customerAppointmentCalendar.getChildren().add(rightSide);


        // stuff for cancel/update popup
        //JFXTimePicker timePicker1 = new JFXTimePicker();


        Label apptTime = new Label();
        Label apptDate = new Label();
        Label apptService = new Label();

        timePicker1.setPromptText("Select Time");

        HBox timeBox = new HBox(10);
        Label newTime = new Label(" New Time:");
        newTime.setTextFill(Paint.valueOf("#286fb4"));
        timeBox.getChildren().addAll(apptTime,newTime,timePicker1);
        timeBox.setAlignment(Pos.CENTER);

        //JFXDatePicker datePicker = new JFXDatePicker();
        HBox dateBox = new HBox(10);
        Label date = new Label("Date:");
        Label newDate = new Label(" New Date:");
        newDate.setTextFill(Paint.valueOf("#286fb4"));
        date.setTextFill(Paint.valueOf("#286fb4"));
        dateBox.getChildren().addAll(newDate,datePicker);
        dateBox.setAlignment(Pos.CENTER);

        datePicker.setPromptText("Select Date");



        //Label appointmentService = new Label("Service:");
        Label newService = new Label(" Service:");
        newService.setTextFill(Paint.valueOf("#286fb4"));
        //appointmentService.setTextFill(Paint.valueOf("#286fb4"));

        removeAdd.getItems().add("change");
        removeAdd.setPromptText("Change Service");

        serviceBox.getChildren().addAll(newService,serviceChooser, removeAdd);
        serviceBox.setAlignment(Pos.CENTER);

        HBox apptButtons = new HBox(10);


        updateAppt.setStyle("-fx-background-color: #e2F0F9");
        updateAppt.setButtonType(JFXButton.ButtonType.RAISED);
        updateAppt.setTextFill(Paint.valueOf("#286fb4"));

        apptButtons.setAlignment(Pos.CENTER);

        //cancel appointment
        cancelAppt.setButtonType(JFXButton.ButtonType.RAISED);
        cancelAppt.setStyle("-fx-background-color: #e3f0f9");
        cancelAppt.setTextFill(Paint.valueOf("#286fb4"));
        apptButtons.getChildren().addAll(updateAppt,cancelAppt);

        makeAndCancelPopUp = new VBox(50);
        makeAndCancelPopUp.getChildren().addAll(timeBox,dateBox,serviceBox,apptButtons);

        makeAndCancelPopUp.setAlignment(Pos.CENTER);

        JFXPopup appointmentPopup1 = new JFXPopup();
        appointmentPopup1.setPopupContent(makeAndCancelPopUp);


        // something else

        change2= new HBox();
        change2.setPadding(new Insets(200,200,200,200));
        change2.setStyle("-fx-background-color: #B0DDE4");
        gridP= new GridPane();
        gridP.setHgap(100);
        gridP.setVgap(100);
        loginError= new Label("");
        loginError.getStyleClass().add("error-text");
        loginError.setVisible(false);
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

        gridP2= new GridPane();
        gridP2.setVgap(100);
        gridP2.setHgap(100);

        gridP.add(lblUserName,0,0);
        gridP.add(textUserName,1,0);
        gridP.add(lblPassword,0,1);
        gridP.add(pf,1,1);
        gridP.add(btonLogin, 1,2 );
        gridP.add(lblMessage,1,2);
        gridP.add(loginError, 1,3);
        errorMsg = new Label("");
        gridP.add(errorMsg,1,4);
        gridP.setAlignment(Pos.CENTER_LEFT);


        Label lblUserName1 = new Label("Enter a Username");
        textUserName1= new JFXTextField();
        Label lblPassword1= new Label("Enter a Password");
        pf1=  new JFXPasswordField();
        JFXButton btonLogin1= new JFXButton("SignUp",signUp);
        btonLogin1.setOnAction(e->signUp());

        final Label lblMessage1= new Label();
        gridP2.add(lblUserName1,0,0);
        gridP2.add(textUserName1,1,0);
        gridP2.add(lblPassword1,0,1);
        gridP2.add(pf1,1,1);
        gridP2.add(btonLogin1, 1,2 );
        gridP2.add(lblMessage1,1,2);
        errorMsg = new Label("");
        gridP2.add(errorMsg,1,4);
        gridP2.setAlignment(Pos.CENTER_RIGHT);

        change2.getChildren().add(gridP);
        change2.getChildren().add(temp);
        change2.getChildren().add(gridP2);



                //Account
        changeAcc = new HBox();
        changeAcc.setPadding(new Insets(100,100,100,100));
        changeAcc.setStyle("-fx-background-color: #B0DDE4;");
        pane= new GridPane();
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
        errorMsg = new Label("");
        pane.add(errorMsg,1,4);
        pane.setAlignment(Pos.CENTER_LEFT);

        changeAcc.getChildren().add(pane);

        backPage.setOnAction(e->back());

        //Business Add
        addBuss = new HBox(10);
        addBuss.setPadding(new Insets(100,100,100,100));
        addBuss.setStyle("-fx-background-color: #B0DDE4;");
        GridPane addBusPane = new GridPane();
        addBusPane.setHgap(100);
        addBusPane.setVgap(100);
        JFXButton addBusinessButton = new JFXButton("Add Business", signUp);
        addBusinessButton.setOnAction(e->setBusinessAction());
        JFXButton submit = new JFXButton("Submit");
        submit.setOnAction(e->switchToOwnerMain());

        Label newBussName2 = new Label("Enter your new name!");
        updateBusName2 = new JFXTextField();
        addBuss.getChildren().add(updateBusName2);

        Label newBussEmail2 = new Label("Enter your new email!");
        updateBusEmail2 = new JFXTextField();
        addBuss.getChildren().add(updateBusEmail2);

        Label newBussAdd2 = new Label("Enter your new address!");
        updateBusAdd2 = new JFXTextField();
        addBuss.getChildren().add(updateBusAdd2);

        Label newBussPhone2 = new Label("Enter your new phone number!");
        updateBusPhone2 = new JFXTextField();
        addBuss.getChildren().add(updateBusPhone2);

        businessError2 = new Label("");
        businessError2.getStyleClass().add("error-text");

        addBusPane.add(newBussName2,0,0);
        addBusPane.add(newBussEmail2, 0, 1);
        addBusPane.add(newBussAdd2, 0, 2);
        addBusPane.add(newBussPhone2, 0, 3);
        addBusPane.add(updateBusName2, 1, 0);
        addBusPane.add(updateBusEmail2, 1, 1);
        addBusPane.add(updateBusAdd2, 1, 2);
        addBusPane.add(updateBusPhone2, 1, 3);
        addBusPane.add(addBusinessButton, 0, 4);
        //addBusPane.add(submit, 1, 4);
        addBusPane.add(businessError2, 0, 5);

        addBuss.getChildren().add(addBusPane);


        //Business Add
        /*addBuss = new HBox(10);
        addBuss.setPadding(new Insets(100,100,100,100));
        addBuss.setStyle("-fx-background-color: #B0DDE4;");
        addBusPane.setHgap(100);
        addBusPane.setVgap(100);
        addBusinessButton.setOnAction(e->setBusinessAction());
        submit.setOnAction(e->switchToOwnerMain());*/


        //Business info
        changeBuss = new HBox(10);
        changeBuss.setPadding(new Insets(100,100,100,100));
        changeBuss.setStyle("-fx-background-color: #B0DDE4;");
        GridPane busPane = new GridPane();
        busPane.setHgap(100);
        busPane.setVgap(100);
        JFXButton updateBusinessButton = new JFXButton("Update Business Info",signUp);
        updateBusinessButton.setOnAction(e->updateBusinessAction());
        JFXButton businessHoursButton = new JFXButton("Business Hours", signUp);
        JFXButton updateBusinessHoursButton = new JFXButton("Update Hours", signUp);
        updateBusinessHoursButton.setOnAction(e->updateBusinessHourAction());
        JFXButton addBusinessHoursButton = new JFXButton("Add Hours", signUp);
        addBusinessHoursButton.setOnAction(e->addBusinessHourAction());
        JFXButton deleteBusinessHoursButton = new JFXButton("Delete Hours", signUp);
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

        Label bhLabel = new Label("Business Hours");
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
        //HBox b5 = new HBox(10);
        //b5.getChildren().addAll(updateBusinessButton);
        VBox b6 = new VBox(10);
        b6.getChildren().addAll(dayOfHour, addDay,startLabel, startTime, endLabel, endTime, delBH);



        busPane.add(b1, 0, 0);
        busPane.add(b2, 0, 1);
        busPane.add(b3, 0, 2);
        busPane.add(b4, 0, 3);
        busPane.add(updateBusinessButton, 0, 4);
        busPane.add(bhLabel,2,0);
        busPane.add(delBH, 3, 0);
        busPane.add(startLabel,2,2);
        busPane.add(dayOfHour,2,1);
        busPane.add(addDay,3,1);
        busPane.add(startTime, 3,2);
        busPane.add(endLabel, 2,3);
        busPane.add(endTime, 3, 3);
        busPane.add(addBusinessHoursButton, 1, 4);
        busPane.add(deleteBusinessHoursButton, 2,4);
        busPane.add(updateBusinessHoursButton, 3, 4);
        busPane.add(businessError, 0, 5);

        //busPane.add(addBusinessHoursButton, 1, 5);
        //busPane.add(delBH, 2, 4);
        //busPane.add(deleteBusinessHoursButton, 2, 5);
        busPane.add(curBussName, 1, 0);
        busPane.add(curBussEmail, 1, 1);
        busPane.add(curBussAdd, 1, 2);
        busPane.add(curBussPN, 1, 3);
        busPane.add(backPage2, 4, 0);

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

        mainScene = new Scene(change2,1440,810,colors[3]);
        ownerMainScreenBorderPane.setStyle("-fx-background-color: #B0DDE4;");
        mainStage.setScene(mainScene);
        mainScene.getStylesheets().add(FlexiBookPage.class.getResource("/css/main.css").toExternalForm());
        ownerMainScreenBorderPane.requestFocus();

        mainScene.setRoot(change2);
        customerScreenBorderPane.setStyle("-fx-background-color: #B0DDE4;");
        customerScreenBorderPane.requestFocus();
    }


    /**
     * @author cesar
     * goes to the main menu
     */
    private void back() {
    	if(FlexiBookApplication.getUser().getUsername().equals("owner")) {
    		mainScene.setRoot(ownerMainScreenBorderPane);
    		pane.getChildren().remove(errorMsg);
    	}
    	else {
    		mainScene.setRoot(customerScreenBorderPane);
    		pane.getChildren().remove(errorMsg);
    	}
    }

    /**
     * @author cesar
     * Creates the account
     * If the account cannot be created a message pops up showing what the problem is
     */
    private void signUp() {

     	try{

        	String username = textUserName1.getText();
        	String password = pf1.getText();
            FlexiBookController.customerSignUp(username, password);
            textUserName1.setText("");
            pf1.setText("");
            mainScene.setRoot(customerScreenBorderPane);
            System.out.println("SignUp Successful");
            System.out.println("Username = " + username + "," + "Password = " + password);
        }
        catch(Exception e){
            e.getMessage();
            errorMsg = new Label(e.getMessage());
            errorMsg.setTextFill(Color.RED);
            gridP2.add(errorMsg,1,4);
            textUserName1.setText("");
            pf1.setText("");
        }

    }

    /**
     * @author cesar
     * Updates the account
     * if the account cannot be updated a message pops up showing why
     */
    private void updateAcc() {

        try{
       	String username = FlexiBookApplication.getUser().getUsername();
            FlexiBookController.updateAccount(username, updateUsername.getText(), updatePassword.getText());
            errorMsg = new Label("Account Succesfully updated");
            errorMsg.setTextFill(Color.BLACK);
            pane.add(errorMsg, 1, 4);
            System.out.println(username + "," + updateUsername.getText()  + "," + updatePassword.getText());
            updateUsername.setText("");
            updatePassword.setText("");
        }
        catch(Exception e){
            e.getMessage();
            errorMsg = new Label(e.getMessage());
            errorMsg.setTextFill(Color.RED);
            pane.add(errorMsg,1,4);

        }

    }

    /**
     * @author cesar
     * delete the account
     * If the account cannot be deleted a messages pops up showing why
     */
    private void deleteAcc(){

        try{
        	String username = FlexiBookApplication.getUser().getUsername();
            FlexiBookController.deleteCustomerAccount(username);
            mainScene.setRoot(change2);
            System.out.println("Delete successfull");
        }
        catch(Exception e){
            e.getMessage();
            errorMsg = new Label(e.getMessage());
            errorMsg.setTextFill(Color.RED);
            pane.add(errorMsg,1,4);
        }

    }
/**
@author Victoria Sanchez
defines logout action for both customers and owners
*/
    private void logout() {
        try{
            FlexiBookController.logout();
            loginError.setText("");
            pf.setText("");
            textUserName.setText("");
            mainScene.setRoot(change2);
        }
        catch(Exception e){
            e.getMessage();
        }
    }
     private void login() {
        try{
          if(textUserName.getText()==null || pf.getText()==null){
              throw new InvalidInputException("no username/password entered");
          }
          FlexiBookController.login(textUserName.getText(),pf.getText());
         if(FlexiBookApplication.getUser().getUsername().equals("owner")) {
             if(FlexiBookApplication.getFlexiBook().getBusiness() == null){
                 mainScene.setRoot(addBuss);
             }
             else{
                 mainScene.setRoot(ownerMainScreenBorderPane);

             }
            }
          else{
              mainScene.setRoot(customerScreenBorderPane);
          }
        }
        catch(InvalidInputException e){
           loginError.setVisible(true);
          loginError.setText(e.getMessage());
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
    private void refreshBusCust(){
        curBussName2.setText("Name: " + FlexiBookController.showBI().getName());
        curBussEmail2.setText("Email: " + FlexiBookController.showBI().getEmail());
        curBussAdd2.setText("Address: " + FlexiBookController.showBI().getAddress());
        curBussPN2.setText("Phone: " + FlexiBookController.showBI().getPhoneNumber());
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

    /**
     * Designed to set up components for both owner and customer calendars
     * @author Tomasz Mroz
     * @param entry list of days for owner and user
     * @param owner boolean used to differentiate between owner and customer
     * @return HBox containing calendar and associated buttons
     */
    private HBox setCalendar(ArrayList<CalendarEntry> entry,boolean owner){
        HBox calendar =new HBox();

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
        leftArrowButton.setOnAction(e -> {
            renderDate = renderDate.minusYears(1);
            updateDate(listDays, calendarYearOwner, calendarMonthOwner,true);
            updateDate(dbvDays, calendarYearCustomer, calendarMonthCustomer,false);
        });
        leftArrowButton.getStyleClass().add("icon-calendar-button");
        Region space = new Region();
        space.setMinWidth(350);

        JFXButton rightArrowButton = new JFXButton("", rightArrow);
        rightArrowButton.setContentDisplay(ContentDisplay.TOP);
        rightArrowButton.setOnAction(e -> {
            renderDate = renderDate.plusYears(1);
            updateDate(listDays, calendarYearOwner, calendarMonthOwner,true);
            updateDate(dbvDays, calendarYearCustomer, calendarMonthCustomer,false);
        });
        rightArrowButton.getStyleClass().add("icon-calendar-button");
        calendarMain.setPrefSize(500, 100);

        if (owner) {
            calendarMonthOwner = new Label(renderDate.getMonth().toString());
            calendarMonthOwner.getStyleClass().add("month-year");
            calendarMonthOwner.setPrefWidth(150);

            calendarTop.getChildren().add(calendarMonthOwner);
            calendarTop.getChildren().add(space);
            calendarTop.getChildren().add(leftArrowButton);
            calendarYearOwner = new Label(String.valueOf(renderDate.getYear()));
            calendarYearOwner.getStyleClass().add("month-year");
            calendarTop.getChildren().add(calendarYearOwner);
        } else {
            calendarMonthCustomer = new Label(renderDate.getMonth().toString());
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
        AnchorPane.setRightAnchor(rightArrow, 0.0);

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

        //table
        if(!owner){
            TableColumn<DayEvent, String> column1 = new TableColumn<>("Start Time");
            column1.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            column1.prefWidthProperty().bind(appointmentTable.widthProperty().multiply(0.33));
            column1.getStyleClass().add("appointment-table-rows");

            TableColumn<DayEvent, String> column2 = new TableColumn<>("Start Date");
            column2.setCellValueFactory(new PropertyValueFactory<>("date"));
            column2.getStyleClass().add("appointment-table-rows");
            column2.prefWidthProperty().bind(appointmentTable.widthProperty().multiply(0.33));

            TableColumn<DayEvent, String> column3 = new TableColumn<>("Service");
            column3.setCellValueFactory(new PropertyValueFactory<>("service"));
            column3.getStyleClass().add("appointment-table-rows");
            column3.prefWidthProperty().bind(appointmentTable.widthProperty().multiply(0.33));

            appointmentTable.getSortOrder().add(column1);

            appointmentTable.getColumns().addAll(column1,column2,column3);


            ObservableList<DayEvent> observableList = appointmentTable.getSelectionModel().getSelectedItems();
            observableList.addListener((ListChangeListener<DayEvent>) c -> {
                while (c.next()) {
                    if (!c.wasPermutated()) {
                        for (DayEvent additem : c.getAddedSubList()) {
                            if (additem.getAppointment().getDescription().equals("appointment")) {
                                chooseAppointment.close();
                                layoutChange.getChildren().add(makeAndCancelPopUp);
                                changeAppointment.show();

                            }
                        }
                    }
                }
                c.reset();
            });
        }


        for (int i = 1; i < 7; i++) {
            for (int j = 1; j < 8; j++) {
                CalendarEntry calendarEntry = new CalendarEntry(String.valueOf("1"));
                calendarEntry.setDate(LocalDate.now());
                calendarEntry.setPrefSize(50, 50);
                calendarEntry.setMinWidth(100);
                calendarEntry.setMinHeight(100);
                calendarEntry.setStyle("-fx-background-color: #FFFFFF");
                calendarEntry.getStyleClass().add("calendar-cell");
                calendarEntry.setAlignment(Pos.TOP_LEFT);
                if (owner) {
                    calendarEntry.setOnAction(this::updateDailySchedule);
                } else {
                    calendarEntry.setOnAction(this::customerDailySchedule);

                    if (calendarEntry.getStyle().contains("-fx-background-color: #FFFFFF")) {
                        calendarEntry.setOnAction(event -> {
                            //CHOOSE APPT

                            appointmentError.setText("");
                            removeAdd.setPromptText("Change Service");
                            removeAdd.getSelectionModel().clearSelection();

                            serviceChooser.getSelectionModel().clearSelection();


                            layoutChoose.setVisible(true);
                            layoutChoose.setStyle("-fx-background-color: #b0dde4;");

                            chooseAppointment.setTitle("Choose Appointment");
                            chooseAppointment.setScene(chooseAppt);

                            chooseAppointment.setX(mainStage.getX() + 550);
                            chooseAppointment.setY(mainStage.getY() + 300);

                            //UPDATE OR CANCEL
                            layoutChange.setVisible(true);
                            layoutChange.setStyle("-fx-background-color: #b0dde4;");

                            changeAppointment.setTitle("Change Appointment");
                            changeAppointment.setScene(changeAppt);

                            changeAppointment.setX(mainStage.getX() + 400);
                            changeAppointment.setY(mainStage.getY() + 250);


                            LocalDate calendarDate1 = LocalDate.of(renderDate.getYear(), renderDate.getMonthValue(), calendarEntry.getDate().getDayOfMonth());


                            changeAppointment.setResizable(false);
                            chooseAppointment.setResizable(false);

                            try {
                                layoutChange.getChildren().clear();
                                List<TOAppointmentCalendarItem> calendarItems = FlexiBookController.getAppointmentCalendar(localDateToString(calendarDate1));
                                refreshDailyAppointments(calendarItems, false);
                                if(calendarItems.size() == 0){
                                    chooseAppointment.close();
                                    layoutChange.getChildren().clear();
                                    Label noAppointments = new Label("No Appointments on This Day");
                                    layoutChange.getChildren().add(noAppointments);
                                    changeAppointment.show();
                                }
                                else {

                                    chooseAppointment.show();

                                    updateAppt.setOnAction(event4 -> {
                                        try {

                                            appointmentError.setText("");
                                            DayEvent appt = (DayEvent) appointmentTable.getSelectionModel().getSelectedItem();

                                            String time = String.valueOf(timePicker1.getValue());

                                            FlexiBookController.updateAppointment(FlexiBookApplication.getUser().getUsername(),
                                                    ((DayEvent) appointmentTable.getSelectionModel().getSelectedItem()).getService(),
                                                    appt.getStartTime(), String.valueOf(timePicker1.getValue()),
                                                    String.valueOf(datePicker.getValue()), String.valueOf(removeAdd.getValue()),
                                                    serviceChooser.getSelectionModel().getSelectedItem().getText());

                                            updateDate(dbvDays, calendarYearCustomer, calendarMonthCustomer, false);

                                            changeAppointment.close();

                                        } catch (InvalidInputException e) {
                                            appointmentError.setText(e.getMessage());
                                            //layoutChange.getChildren().add(appointmentError);

                                        } catch (NullPointerException e) {
                                            appointmentError.setText("Selected Values cant be null");
                                        }
                                    });


                                    cancelAppt.setOnAction(event3 -> {
                                        try {
                                            appointmentError.setText("");

                                            DayEvent appt = (DayEvent) appointmentTable.getSelectionModel().getSelectedItem();
                                            FlexiBookController.cancelAppointment(FlexiBookApplication.getUser().getUsername(), appt.getService(), appt.getDate(), appt.getStartTime());

                                            updateDate(dbvDays, calendarYearCustomer, calendarMonthCustomer, false);

                                            changeAppointment.close();

                                        } catch (InvalidInputException e) {
                                            appointmentError.setText(e.getMessage());

                                        }
                                    });
                                }

                            } catch (InvalidInputException e) {
                                error = e.getMessage();
                            }
                            appointmentError.setStyle("-fx-background-color: #ffcccb");
                            layoutChange.getChildren().add(appointmentError);



                        });
                    }
                }
                entry.add(calendarEntry);
                days.add(calendarEntry, j, i);
            }
        }
        calendarMain.getChildren().add(days);
        return calendar;
    }

    /**
     * Event handler for start appointment event
     * @author Tomasz Mroz
     * @param event start button being pressed
     */
    private void startAppointmentEvent(ActionEvent event) {
        error = null;
        try {
            FlexiBookController.startAppointment(currentAppointment);
            errorMessageAppointmentCalendar.setText("");
        } catch (InvalidInputException e) {
            errorMessageAppointmentCalendar.setText(e.getMessage());
        }

    }
    /**
     * Event handler for end appointment event
     * @author Tomasz Mroz
     * @param event end button being pressed
     */
    private void endAppointmentEvent(ActionEvent event) {
        error = null;
        try {
            FlexiBookController.endAppointment(currentAppointment);
            errorMessageAppointmentCalendar.setText("");
            updateDate(listDays, calendarYearOwner, calendarMonthOwner,true);
            refreshDailyAppointments(FlexiBookController.getAppointmentCalendar(localDateToString(renderDate)), true);
        } catch (InvalidInputException e) {
            errorMessageAppointmentCalendar.setText(e.getMessage());
        }
    }
    /**
     * Event handler for register no show event
     * @author Tomasz Mroz
     * @param event no show button being pressed
     */
    private void registerNoShowEvent(ActionEvent event) {
        error = null;
        try {
            FlexiBookController.registerNoShow(currentAppointment);
            updateDate(listDays,calendarYearOwner,calendarMonthOwner,true);
            refreshDailyAppointments(FlexiBookController.getAppointmentCalendar(localDateToString(renderDate)), true);
            errorMessageAppointmentCalendar.setText("");
        } catch (InvalidInputException e) {
            errorMessageAppointmentCalendar.setText(e.getMessage());
        }
    }

    /**
     * @author Tomasz Mroz
     * Switches root pane of scene to owner appointment calendar
     */
    private void switchToOwnerAppointment() {
        mainScene.setRoot(ownerAppointmentCalendar);
        updateDate(listDays, calendarYearOwner, calendarMonthOwner,true);
    }

    private void switchToCustomerAppointment() {
        mainScene.setRoot(customerAppointmentCalendar);
        updateDate(dbvDays, calendarYearCustomer, calendarMonthCustomer,false);
    }

    private void switchToBusinessCust(){
        refreshBusCust();
        mainScene.setRoot(changeBussCust);

    }
    private void switchToOwnerMain(){
        mainScene.setRoot(ownerMainScreenBorderPane);
    }

    private void switchToSetUpBusiness(){
        mainScene.setRoot(addBuss);
    }

    private void switchToBusiness(){
        //setUpBusinessPage();
       mainScene.setRoot(changeBuss);
       refreshBusiness();
    }
    private void switchToServices(){
        setUpServicePage();
        mainScene.setRoot(servicePage);
    }

    /**
     * Reacts to any update in the calendar (Month change, Year change or Day change) and updates the daily appointments
     * @param event calendar update event
     */
    private void updateDailySchedule(ActionEvent event){
        if(event.getTarget() instanceof CalendarEntry){
            LocalDate date = ((CalendarEntry) event.getTarget()).getDate();
            try{
                refreshDailyAppointments(FlexiBookController.getAppointmentCalendar(localDateToString(date)), true);
            }
            catch (InvalidInputException e){
                error = e.getMessage();
            }
        }
    }

    private void customerDailySchedule(ActionEvent e) {
        if (e.getTarget() instanceof CalendarEntry) {
            LocalDate date = ((CalendarEntry) e.getTarget()).getDate();
            try {
                refreshDailyAppointments(FlexiBookController.getAppointmentCalendar(localDateToString(date)), false);
            } catch (InvalidInputException errr) {
                error = errr.getMessage();
            }
        }
    }

    private void switchToHomeScreen(){
        mainScene.setRoot(change2);
    }
    private void switchToAccount(){
        mainScene.setRoot(changeAcc);
    }

    private void switchToCustomerAccount(){}

    /**
     * Updates table with daily appointments
     * Sorts and combines redundant appointments
     * @author Tomasz Mroz
     * @param owner offers different services for owner and customer
     */
    private void refreshDailyAppointments(List<TOAppointmentCalendarItem> calendarItems, boolean owner){
        ArrayList<TOAppointmentCalendarItem> addItems = new ArrayList<>();
        ArrayList<TOAppointmentCalendarItem> removeItems = new ArrayList<>();
        for(TOAppointmentCalendarItem item:calendarItems){
            if(!owner){
                if (item.getDescription().equals("business hours")) {
                    removeItems.add(item);
                }
                if (item.getUsername() == null || !item.getUsername().equals(FlexiBookApplication.getUser().getUsername())) {
                    removeItems.add(item);
                }
            }
            else{
                if (!item.getDescription().equals("available") && !item.getDescription().equals("business hours")&& item.getUsername() == null) {
                    removeItems.add(item);
                }
                if(item.getDescription().equals("available")){
                    item.setUsername("available");
                }
                if(item.getDescription().equals("business hours")){
                    item.setUsername("business hours");
                }
            }
        }
        calendarItems.removeAll(removeItems);
        removeItems.clear();
        if(!owner){
            if(calendarItems.size() == 2){
                for (int k = 0; k < calendarItems.size() - 1; k++) {
                    if (calendarItems.get(k).getDescription().equals("appointment") && calendarItems.get(k).getUsername() != null && (calendarItems.get(k).getUsername().equals(FlexiBookApplication.getUser().getUsername()) || owner)) {
                        if (calendarItems.get(k + 1).getDescription().equals("appointment") && calendarItems.get(k+1).getUsername() != null && (calendarItems.get(k+1).getUsername().equals(FlexiBookApplication.getUser().getUsername()) || owner)) {
                            removeItems.add(calendarItems.get(k));
                            removeItems.add(calendarItems.get(k + 1));
                            addItems.add(new TOAppointmentCalendarItem("appointment",
                                    calendarItems.get(k).getDate(),calendarItems.get(k).getStartTime(),calendarItems.get(k+1).getEndTime(),
                                    false,calendarItems.get(k).getUsername(),calendarItems.get(k).getMainService()));
                        }

                    }
                }
            }
            else{
                for (int k = 0; k < calendarItems.size() - 1; k+=2) {
                    if (calendarItems.get(k).getDescription().equals("appointment") && calendarItems.get(k).getUsername() != null && (calendarItems.get(k).getUsername().equals(FlexiBookApplication.getUser().getUsername()) || owner)) {
                        //if (calendarItems.get(k + 1).getDescription().equals("available") && calendarItems.get(k+1).getUsername() != null && (calendarItems.get(k+1).getUsername().equals(FlexiBookApplication.getUser().getUsername()) || owner)) {
                        if (calendarItems.get(k + 1).getDescription().equals("appointment") && calendarItems.get(k+1).getUsername() != null && (calendarItems.get(k+1).getUsername().equals(FlexiBookApplication.getUser().getUsername()) || owner)) {
                            removeItems.add(calendarItems.get(k));
                            removeItems.add(calendarItems.get(k + 1));
                            //removeItems.add(calendarItems.get(k + 2));
                            addItems.add(new TOAppointmentCalendarItem("appointment",
                                    calendarItems.get(k).getDate(),calendarItems.get(k).getStartTime(),calendarItems.get(k+1).getEndTime(),
                                    false,calendarItems.get(k).getUsername(),calendarItems.get(k).getMainService()));
                        }
                        //}
                    }
                }
            }

            calendarItems.removeAll(removeItems);


            //calendarItems.clear();
            calendarItems.addAll(addItems);
            //calendarItems.clear();
            removeItems.clear();
            //addItems.get(0).getMainService().getDuration();
            boolean end = false;
            while(!end){
                for(int a = 0; a<calendarItems.size();a++){
                    for(int b = 0;b<calendarItems.size();b++){
                        if(a!= b){
                            if(calendarItems.get(a).getUsername() != null && calendarItems.get(a).getUsername().equals(calendarItems.get(b).getUsername())){
                                if(calendarItems.get(a).getStartTime().equals(calendarItems.get(b).getStartTime())){
                                    if(calendarItems.get(a).getDate().equals(calendarItems.get(b).getDate())){
                                        removeItems.add(calendarItems.get(a));
                                        end = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if(end){
                        break;
                    }
                }
                end = true;
            }

        }
        calendarItems.removeAll(removeItems);
        if(owner){
            dailyAppointmentTable.getItems().clear();
            for(TOAppointmentCalendarItem item:calendarItems){
                dailyAppointmentTable.getItems().add(new DayEvent(item));
            }
            dailyAppointmentTable.sort();
        }
        else{
            appointmentTable.getItems().clear();
            for(TOAppointmentCalendarItem item:calendarItems){
                appointmentTable.getItems().add(new DayEvent(item));
            }
            appointmentTable.sort();
        }
    }

    /**
     * @author Tomasz Mroz
     * Changes the numbers of the days based on the month/year and colors them if an appointment occurs
     * @param owner checks between owner and customer
     */
    private void updateDate(ArrayList<CalendarEntry> list,Label year,Label month,boolean owner){
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
                List<TOAppointmentCalendarItem> list1 = FlexiBookController.getAppointmentCalendar(localDateToString(calendarDate));
                if(list1.size() >0){
                    for(TOAppointmentCalendarItem item : list1){
                        if(item.getDescription().equals("appointment")){
                            if(owner){
                                c.setStyle("-fx-background-color: #03c04A80");
                            }
                            else{
                                if(item.getUsername() != null && item.getUsername().equals(FlexiBookApplication.getUser().getUsername())){
                                    c.setStyle("-fx-background-color: #03c04A80");
                                }
                            }
                        }

                    }
                    //c.setStyle("-fx-background-color: #03c04A80");
                }
            } catch (InvalidInputException e) {
                error = e.getMessage();
            }
            calendarDate = calendarDate.plusDays(1);
            if(!c.getDate().getMonth().equals(renderDate.getMonth())){
                c.getStyleClass().add("not-in-month");
            }
        }
    }

    /**
     * @author Tomasz Mroz
     * @param e Reacts to all 12 button event month occurring
     */
    private void switchMonth(ActionEvent e) {
        if (e.getTarget() instanceof JFXButton) {
            String message = ((JFXButton) e.getTarget()).getText();
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
            updateDate(listDays, calendarYearOwner, calendarMonthOwner,true);
            updateDate(dbvDays, calendarYearCustomer, calendarMonthCustomer,false);
        }
    }

    /**
     * Helper method which converts local date to string format needed by controller
     * @author Tomasz Mroz
     * @param date date to be converted
     * @return String representation of date
     */
    private String localDateToString(LocalDate date){
        String s = ""+ date.getYear() + "-";
        if(date.getMonthValue() <10){
            s+="0";
        }
        s += date.getMonthValue() + "-";
        if (date.getDayOfMonth() < 10) {
            s += "0";
        }
        s += date.getDayOfMonth();
        return s;
    }

    private void setUpBusinessPage() {
    }


    /**
     * @author Hana Gustyn
     * Sets up the service page.
     */
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

    /**
     * @author Hana Gustyn
     * Calls controller to add a service.
     */
    private void addServiceAction() {
        Integer duration = 0;
        Integer downtimeStart = 0;
        Integer downtimeDuration = 0;
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

        try {
            duration = Integer.parseInt(checkString(durationInput.getText()));
            downtimeDuration = Integer.parseInt(checkString(downtimeDurationInput.getText()));
            downtimeStart = Integer.parseInt(checkString(downtimeStartInput.getText()));
        } catch (NumberFormatException e) {
            serviceError.setText("Incorrect input!");
        }

        if(serviceError.getText().length() == 0) {
            try {
                FlexiBookController.addService(FlexiBookApplication.getUser().getUsername(), serviceNameInput.getText(),
                        duration, downtimeDuration, downtimeStart);
            } catch (InvalidInputException e) {
                serviceError.setText(e.getMessage());
            }
        }
        refreshData();
    }

    /**
     * @author Hana Gustyn
     * Calls controller to update a service.
     */
    private void updateServiceAction(){
        serviceError.setText("");
        Integer duration = 0;
        Integer downtimeStart = 0;
        Integer downtimeDuration = 0;

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

        try {
            duration = Integer.parseInt(checkString(durationInput1.getText()));
            downtimeDuration = Integer.parseInt(checkString(downtimeDurationInput1.getText()));
            downtimeStart = Integer.parseInt(checkString(downtimeStartInput1.getText()));
        } catch (NumberFormatException e) {
            serviceError.setText("Incorrect input!");
        }

        if (serviceError.getText().length() == 0) {
            try {
                FlexiBookController.updateService(FlexiBookApplication.getUser().getUsername(), existingServices.getValue(),
                        serviceNameInput1.getText(), duration, downtimeDuration, downtimeStart);
            } catch (InvalidInputException e) {
                serviceError.setText(e.getMessage());
            }
        }
        refreshData();
    }

    /**
     * @author Hana Gustyn
     * Calls controller to delete a service.
     */
    private void deleteServiceAction(){
        serviceError.setText("");

        if (existingServices1.getItems().size() == 0){
            serviceError.setText("An existing service must be selected!");
        }
        if (serviceError.getText().length() == 0) {
            try {
                FlexiBookController.deleteService(FlexiBookApplication.getUser().getUsername(), existingServices1.getValue());
            } catch (InvalidInputException e) {
                serviceError.setText(e.getMessage());
            }
        }
        refreshData();
    }

    /**
     * @author Hana Gustyn
     * Switches to the available services page.
     */
    private void switchToAvailableServices(){
        setAvailableServices();
        mainScene.setRoot(availableServicesPage);
    }

    /**
     * @author Hana Gustyn
     * Sets up the available services page.
     */
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

    private String checkString(String str) {
        if(str.contains("minutes")){
            String tempStr = " minutes";
            str = str.replace(tempStr, "");
        }
        else if(str.contains("minute")){
            String tempStr = " minute";
            str = str.replace(tempStr, "");
        }
        return str;
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
        businessError.setText("");

    }

    private void setBusinessAction(){
        businessError2.setText("");
        if(updateBusName2.getText().length() == 0){
            businessError2.setText("All fields must be completed!");
        }
        if(updateBusEmail2.getText().length() == 0) {
            businessError2.setText("All fields must be completed!");
        }
        if(updateBusAdd2.getText().length() == 0) {
            businessError2.setText("All fields must be completed!");
        }
        if(updateBusPhone2.getText().length() == 0) {
            businessError2.setText("All fields must be completed!");
        }
        String name = updateBusName2.getText();
        String email = updateBusEmail2.getText();
        String address = updateBusAdd2.getText();
        String pn = updateBusPhone2.getText();
        if(businessError2.getText().length() == 0){
            try{

                FlexiBookController.setUpBusinessInfo(name, address, pn, email, null, null, null, null, null,  null, null, true, false, false, false);

            }
            catch(InvalidInputException e){
                businessError2.setText(e.getMessage());
            }
        }
       if(FlexiBookApplication.getFlexiBook().getBusiness().getName().equals(name)){
           mainScene.setRoot(ownerMainScreenBorderPane);
       }
        //refreshBusiness();

    }




     private void addBusinessHourAction(){
        businessError.setText("");
        if(!startTime.getText().matches("^([0-2][0-9]):[0-5][0-9]$")){
            businessError.setText("Please Enter 24 Hour Time, ex: 06:00");
        }if(!endTime.getText().matches("^([0-2][0-9]):[0-5][0-9]$")) {
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
                if(et.equals("00:00") && st.equals("00:00")){
                    et = "23:59";
                }
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

    public void updateBusinessHourAction(){
        businessError.setText("");
        if(delBH.getItems().size() == 0){
            businessError.setText("No Hours Selected");
        }
        if(!startTime.getText().matches("^([0-2][0-9]):[0-5][0-9]$")){
            businessError.setText("Please Enter 24 Hour Time, ex: 06:00");
        }if(!endTime.getText().matches("^([0-2][0-9]):[0-5][0-9]$")) {
            businessError.setText("Please Enter 24 Hour Time, ex: 06:00");
        }
        if(addDay.getItems().size()==0){
            businessError.setText("Please select a day");
        }
        if(businessError.getText().length()==0) {
            try {
                String delims = "[ ]+";
                String[] tokens = delBH.getValue().split(delims);
                FlexiBookController.deleteBusinessHours(tokens[0], tokens[1]);
                String day = addDay.getValue();
                String st = startTime.getText();
                String et = endTime.getText();
                if(et.equals("00:00") && st.equals("00:00")){
                    et = "23:59";
                }
                FlexiBookController.setUpBusinessHour(day, st, et);
            } catch (InvalidInputException e) {
                businessError.setText(e.getMessage());
            }
        }
       refreshBusiness();
    }

}
