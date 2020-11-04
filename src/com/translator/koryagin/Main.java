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

        // check every line
        // see program struct in files/program

        // check start
        String start = program.get(0);
        if (!start.equals("\"Программа\"")){
            System.out.println("Ошибка. В начале ожидалось \"Программа\"");
        }
        program.remove(0);

        // TODO: check header
        String header = program.get(0);
        String[] header_elements = header.split(" ");

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
