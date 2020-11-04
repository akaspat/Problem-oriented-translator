package com.translator.koryagin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        if (program.get(0).equals("\"\"Программа\"\"")){
            System.out.println("Ошибка. В начале ожидалось \"Программа\"");
        }
        program.remove(0);

        // TODO: check header

        // TODO; collect operators

        // TODO: check operators

        // check ends
        // get index of last element
        int end = program.size() - 1;
        if (program.get(end).equals("\"\"Конец программы\"\"")){
            System.out.println("Ошибка. В конце ожидалось \"Конец программы\"");
        }
        program.remove(end);

        if (!program.isEmpty()){
            System.out.println("Откуда о_О");
        }
    }
}
