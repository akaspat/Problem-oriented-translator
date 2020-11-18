package com.translator.koryagin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Gui {
    private JFrame frame;
    private JTextArea inputAreaText;
    private static JTextArea outputAreaText;
    private JButton buildBtn;
    private ButtonActionListener btnListener;
    private List<String> program;

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
        frame.setSize(1000, 700);

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
        btnListener = new ButtonActionListener();
        buildBtn.addActionListener(btnListener);
        buildBtn.setFont(new Font("serif", Font.BOLD, 35));
        JPanel buildArea = new JPanel();
        buildArea.add(buildBtn);
        frame.getContentPane().add(BorderLayout.CENTER, buildArea);
    }

    public void setInputAreaText(List<String> lines, boolean clearPrevious){
        if (clearPrevious) inputAreaText.removeAll();
        for (String line : lines){
            inputAreaText.append(line);
            inputAreaText.append("\n");
        }
    }

    public void parseProgramTextToPath(boolean fromFile){
        // get string from file
        if (fromFile) {
            Path pathInputProgram = Path.of("files/program");
            program = null;
            try {
                program = Files.readAllLines(pathInputProgram);
                setInputAreaText(program, true);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        // get string from gui input area
        else {
            String[] lines = inputAreaText.getText().split("\n");
            program.clear();
            for (String line : lines) { program.add(line); }
        }

    }

    public static void showError(String errorMessage){
        if (outputAreaText.getText().length() != 0) { return; }

        outputAreaText.setText(errorMessage);
    }

    public void checkAllProgram() {
        /*
         check every line
         see program struct in files/program
        */

        // check start
        String start = program.get(0);
        if (!start.equals("\"Программа\"")){
            showError("Ошибка. В начале ожидалось \"Программа\"");
        }

        program.remove(0);

        // check header
        String header = program.get(0);
        String[] header_elements = header.split(" ");
        if (!header_elements[0].equals("\"Метки\"")){
            showError("Ошибка. Ожидалось слово \"Метки\"");
        }

        // check every sign in header
        Sign sign_checker = new Sign();
        for (int i=1; i < header_elements.length; i++){
            sign_checker.setSignString(header_elements[i]);
            sign_checker.isCorrect(true);
        }
        program.remove(0);

        // collect operators
        ArrayList<String> operators = new ArrayList<>();
        String oper = program.get(0);
        while ( !oper.equals("\"Конец программы\"") ){
            operators.add(oper);
            program.remove(0);

            oper = program.get(0);
        }

        for (String _operator : operators) {
            Operator operator = new Operator(_operator);
            operator.isCorrect(true);
        }

        // check ends
        // get index of last element
        int end = program.size() - 1;
        String end_str = program.get(end);
        if (!end_str.equals("\"Конец программы\"")){
            showError("Ошибка. В конце ожидалось \"Конец программы\"");
        }
        program.remove(end);

        if (!program.isEmpty()){
            showError(String.format("Ошибка. Программа должна заканчиваться \"Конец программы\", вместо %s", program.get(0)));
        }

        if (outputAreaText.getText().length() == 0){
            showError("Программа корректна");
        }

    }

    public class ButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            outputAreaText.setText(null);
            parseProgramTextToPath(false);
            checkAllProgram();
        }
    }
}
