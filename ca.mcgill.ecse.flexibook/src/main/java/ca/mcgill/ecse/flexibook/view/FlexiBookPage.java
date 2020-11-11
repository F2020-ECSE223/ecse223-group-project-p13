package ca.mcgill.ecse.flexibook.view;

import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
import ca.mcgill.ecse.flexibook.controller.TOAppointmentCalendarItem;
import com.jfoenix.controls.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
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

public class FlexiBookPage extends Application {
    private String error;
    private Color[] colors = {new Color(.886,.941,.976,1.0),new Color(.690,.867,.882,1.0),
            new Color(.157,.435,.706,1.0),Color.WHITE,new Color(.875,.298,.451,1.0)};

    JFXButton startButton;
    //private CardLayout mainLayout;
    private Stage mainStage;



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


        BorderPane border = new BorderPane();
        //VBox vBox = new VBox(100);
        //vBox.setPadding(new Insets(100));
        Label welcome = new Label("Welcome, User");
        HBox top = new HBox();
        border.setTop(top);
        top.setAlignment(Pos.BASELINE_RIGHT);
        top.getChildren().add(welcome);
        welcome.getStyleClass().add("user-text");

        VBox center = new VBox(70);
        border.setCenter(center);
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



        //Box hBox = new HBox(50);
        /*//hBox.getChildren().add(padderRegion);
        hBox.getChildren().add(startButton);
        //hBox.getChildren().add(vBox);
        border.setTop(hBox);
        border.setCenter(vBox);

        JFXTextField username = new JFXTextField();
        username.setLabelFloat(true);
        username.setPromptText("Username");
        vBox.getChildren().add(username);

        JFXPasswordField password = new JFXPasswordField();
        password.setLabelFloat(true);
        password.setPromptText("Password");
        vBox.getChildren().add(password);*/

        /*HBox tilePane = new HBox();
        border.setBottom(tilePane);
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
        border.setRight(jfxCombo);*/


        Scene ownerHomeScreen = new Scene(border,1440,810,colors[3]);
        border.setStyle("-fx-background-color: #B0DDE4;");
        mainStage.setScene(ownerHomeScreen);
        ownerHomeScreen.getStylesheets().add(FlexiBookPage.class.getResource("/css/main.css").toExternalForm());
        border.requestFocus();
    }

    private void refreshData(){

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
    private void switchToAppointment(){}
    private void switchToBusiness(){}
    private void switchToServices(){}
    private void switchToAccount(){}

}