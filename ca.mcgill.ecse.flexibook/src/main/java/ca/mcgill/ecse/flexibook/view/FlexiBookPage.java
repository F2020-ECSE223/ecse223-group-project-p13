package ca.mcgill.ecse.flexibook.view;

import javax.swing.*;
import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.TOAppointmentCalendarItem;
import ca.mcgill.ecse.flexibook.controller.TOBusinessInfo;

import java.awt.*;

public class FlexiBookPage extends JFrame {
    private Color[] colors = {new Color(226,240,249),new Color(176,221,228),new Color(40,111,180)
            ,Color.WHITE,new Color(223,76,115)};



    public FlexiBookPage(){
        initComponents();
        refreshData();
    }
    private void initComponents(){
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //Define central panel of the frame, contains all parts of the calculator
        JPanel main = new JPanel();
        main.setPreferredSize(new Dimension(450, 600));
        main.setBackground(colors[1]);
        setContentPane(main);
        main.setVisible(true);
        main.setLayout(null);
        main.setFocusable(true);

        //Contains the input and output text boxes
       /* JPanel topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setBounds(5,10,440,85);
        main.add(topPanel);
        //Defines the user input box
        JLabel inputText = new JLabel("",SwingConstants.RIGHT);
        inputText.setText("0");
        //3*5/2*(6.32-4.85)
        inputText.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        inputText.setForeground(Color.BLACK);
        inputText.setBackground(colors[2]);
        topPanel.add(inputText);
        inputText.setBounds(0,0,440,40);*/

        /*//Defines the answer output box
        JLabel outputText = new JLabel("",SwingConstants.RIGHT);
        outputText.setText("0");
        outputText.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        outputText.setForeground(Color.BLACK);
        outputText.setBackground(colors[2]);
        outputText.setBounds(0,45,440,40);
        topPanel.add(outputText);*/
        pack();
    }

    private void refreshData(){

    }
}
