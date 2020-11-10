package ca.mcgill.ecse.flexibook.view;

import javax.swing.*;
import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
import ca.mcgill.ecse.flexibook.controller.TOAppointmentCalendarItem;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

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
        //appointment.setFill(colors[1]);
        /*JPanel appointment = new JPanel();
        main.add(appointment,"appointment");
        appointment.setPreferredSize(new Dimension(960, 540));
        appointment.setBackground(colors[1]);
        //setContentPane(main);
        main.setVisible(true);
        main.setFocusable(true);*/

        startButton = new JFXButton("Start Appointment");
        startButton.getStyleClass().add("button-raised");
        startButton.setOnAction(this::startAppointmentEvent);
        Region padderRegion = new Region();
        padderRegion.prefWidthProperty().setValue(250);


        BorderPane border = new BorderPane();
        VBox vBox = new VBox(100);
        vBox.setPadding(new Insets(100));


        HBox hBox = new HBox(50);
        hBox.getChildren().add(padderRegion);
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
        vBox.getChildren().add(password);



        Scene appointment = new Scene(border,960,540,colors[3]);
        border.setStyle("-fx-background-color: #B0DDE4;");
        mainStage.setScene(appointment);
        appointment.getStylesheets().add(FlexiBookPage.class.getResource("/css/main.css").toExternalForm());

        //startButton.set(colors[2]);
        //startButton.setBackground(colors[0]);
        //startButton.setFont(new Font(Font.MONOSPACED,Font.BOLD,14));
        //startButton.setBounds(0,50,200,100);
        //startButton.addActionListener(this::startAppointmentEvent);

        //endButton = new JButton("End Appointment");
        /*endButton.setForeground(colors[2]);
        endButton.setBackground(colors[0]);
        endButton.setFont(new Font(Font.MONOSPACED,Font.BOLD,14));
        endButton.setBounds(0,50,200,100);
        endButton.addActionListener(this::endAppointmentEvent);*?
        alt.add(endButton);
        main.add(alt,"alt");
        mainLayout.show(main,"appointment");
        //pack();*/
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
}
