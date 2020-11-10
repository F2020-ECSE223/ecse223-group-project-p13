package ca.mcgill.ecse.flexibook.view;

import javax.swing.*;
import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
import ca.mcgill.ecse.flexibook.controller.TOAppointmentCalendarItem;
import ca.mcgill.ecse.flexibook.controller.TOBusinessInfo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;

public class FlexiBookPage extends JFrame {
    private String error;
    private Color[] colors = {new Color(226,240,249),new Color(176,221,228),new Color(40,111,180)
            ,Color.WHITE,new Color(223,76,115)};
    private JButton startButton;
    private JButton endButton;
    private JPanel alt;
    private JPanel main;
    private CardLayout mainLayout;


    public FlexiBookPage(){
        initComponents();
        refreshData();
    }
    private void initComponents(){
        setTitle("FlexiBook Application");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        main = new JPanel();
        mainLayout = new CardLayout();
        main.setLayout(mainLayout);
        JPanel appointment = new JPanel();
        main.add(appointment,"appointment");
        appointment.setPreferredSize(new Dimension(960, 540));
        appointment.setBackground(colors[1]);
        setContentPane(main);
        main.setVisible(true);
        main.setFocusable(true);

        startButton = new JButton("Start Appointment");
        startButton.setForeground(colors[2]);
        startButton.setBackground(colors[0]);
        startButton.setFont(new Font(Font.MONOSPACED,Font.BOLD,14));
        startButton.setBounds(0,50,200,100);
        startButton.addActionListener(this::startAppointmentEvent);
        appointment.add(startButton);

        alt = new JPanel();
        alt.setBackground(colors[1]);

        endButton = new JButton("End Appointment");
        endButton.setForeground(colors[2]);
        endButton.setBackground(colors[0]);
        endButton.setFont(new Font(Font.MONOSPACED,Font.BOLD,14));
        endButton.setBounds(0,50,200,100);
        endButton.addActionListener(this::endAppointmentEvent);
        alt.add(endButton);
        main.add(alt,"alt");
        mainLayout.show(main,"appointment");
        pack();
    }

    private void refreshData(){

    }
    private void startAppointmentEvent(ActionEvent event){
        error = null;
        mainLayout.show(main,"alt");
        pack();

        try{
            FlexiBookController.startAppointment(new TOAppointmentCalendarItem("appointment",new Date(2135435),new Time(23435435),new Time(23435435),true));
        }
        catch (InvalidInputException e){
            error = e.getMessage();
        }
    }
    private void endAppointmentEvent(ActionEvent event){
        error = null;
        mainLayout.show(main,"appointment");
        pack();

        try{
            FlexiBookController.startAppointment(new TOAppointmentCalendarItem("appointment",new Date(2135435),new Time(23435435),new Time(23435435),true));
        }
        catch (InvalidInputException e){
            error = e.getMessage();
        }
    }
}
