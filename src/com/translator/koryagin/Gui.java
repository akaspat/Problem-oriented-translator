package com.translator.koryagin;

import javax.swing.*;
import java.awt.*;

public class Gui {
    private JFrame frame;
    private JTextArea inputAreaText;
    private JTextArea outputAreaText;

    public Gui() {
        frame = new JFrame();
        buildWindow();
        frame.setVisible(true);
    }

    /*
    Configure window - add main elements
    on window
    And configure their location
     */
    private void buildWindow(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);

        // INPUT AREA
        // create and place input area on the left side
        JPanel inputArea = new JPanel();
        inputAreaText = new JTextArea(frame.getHeight() / 16, frame.getWidth() / 30);

        // word wrapping to a new line
        inputAreaText.setLineWrap(true);
        inputAreaText.setWrapStyleWord(true);

        inputArea.add(new JScrollPane(inputAreaText));
        frame.getContentPane().add(BorderLayout.WEST, inputArea);

        // OUTPUT AREA
        // create and place input area on the right side
        JPanel outputArea = new JPanel();
        outputAreaText = new JTextArea(frame.getHeight() / 16, frame.getWidth() / 30);

        // word wrapping to a new line
        outputAreaText.setLineWrap(true);
        outputAreaText.setWrapStyleWord(true);

        outputArea.add(new JScrollPane(outputAreaText));
        frame.getContentPane().add(BorderLayout.EAST, outputArea);


    }
}
