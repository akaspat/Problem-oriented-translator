package com.translator.koryagin;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Gui {
    private JFrame frame;
    private JTextArea inputAreaText;
    private JTextArea outputAreaText;
    private JButton buildBtn;

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
        frame.setSize(1400, 1000);

        // INPUT AREA
        // create and place input area on the left side
        JPanel inputArea = new JPanel();
        inputAreaText = new JTextArea(frame.getHeight() / 16, frame.getWidth() / 30);

        // word wrapping to a new line
        inputAreaText.setLineWrap(true);
        inputAreaText.setWrapStyleWord(true);
        inputAreaText.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        inputArea.add(new JScrollPane(inputAreaText));
        frame.getContentPane().add(BorderLayout.WEST, inputArea);

        // OUTPUT AREA
        // create and place input area on the right side
        JPanel outputArea = new JPanel();
        outputAreaText = new JTextArea(frame.getHeight() / 16, frame.getWidth() / 30);
        outputAreaText.setFont(new Font("Times New Roman", Font.PLAIN, 14));


        // word wrapping to a new line
        outputAreaText.setLineWrap(true);
        outputAreaText.setWrapStyleWord(true);

        outputArea.add(new JScrollPane(outputAreaText));
        frame.getContentPane().add(BorderLayout.EAST, outputArea);

        // BUILD BTN
        buildBtn = new JButton("Build");
        buildBtn.setFont(new Font("serif", Font.BOLD, 40));
        JPanel buildArea = new JPanel();
        buildArea.add(buildBtn);
        frame.getContentPane().add(BorderLayout.CENTER, buildArea);
    }

    public void setInputAreaText(List<String> lines, boolean clearPrevious){
        if (!clearPrevious) inputAreaText.removeAll();
        for (String line : lines){
            inputAreaText.append(line);
            inputAreaText.append("\n");
        }
    }
}
