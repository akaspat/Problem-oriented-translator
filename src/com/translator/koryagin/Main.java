package com.translator.koryagin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Gui g = new Gui();

        // show program text on gui
        Path pathInputProgram = Path.of("files/program");
        List<String> program = null;
        try {
            program = Files.readAllLines(pathInputProgram);
            g.setInputAreaText(program, true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        /*
         check every line
         see program struct in files/program
        */

        // check start
        String start = program.get(0);
        if (!start.equals("\"Программа\"")){
            System.out.println("Ошибка. В начале ожидалось \"Программа\"");
        }
        program.remove(0);

        // check header
        String header = program.get(0);
        String[] header_elements = header.split(" ");
        if (!header_elements[0].equals("\"Метки\"")){
            System.out.println("Ошибка. Ожидалось слово \"Метки\"");
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
        int index = 0;
        String oper = program.get(index);
        while ( !oper.equals("\"Конец программы\"") ){
            operators.add(oper);
            index++;
            oper = program.get(index);
        }

        // TODO: check operators
        for (String _operator : operators) {
            String[] elements = _operator.split(" ");

            // get MInteger (метка = цел)
            MInteger metka = new MInteger(elements[0]);
            metka.isCorrect(true);

            // get ":"
            if (!elements[1].equals(":")){
                System.out.println(String.format("Ошибка. Ожидалось двоеточние вместо %s", elements[1]));
            }

            // get Variable (пер)
            Variable var = new Variable(elements[2]);
            var.isCorrect(true);

            // get "="
            if (!elements[3].equals("=")){
                System.out.println(String.format("Ошибка. Ожидался \"=\" вместо %s", elements[3]));
            }

            // get CalculatePart (прав.часть)
            String calPratString = "";
            for (int i=4; i < elements.length; i++) { calPratString += elements[i]; }
            CalculatePart cp = new CalculatePart(calPratString);
            cp.isCorrect(true);
        }

        // check ends
        // get index of last element
        int end = program.size() - 1;
        String end_str = program.get(end);
        if (!end_str.equals("\"Конец программы\"")){
            System.out.println("Ошибка. В конце ожидалось \"Конец программы\"");
        }
        program.remove(end);

        if (!program.isEmpty()){
            System.out.println("Откуда о_О");
        }
    }
}
