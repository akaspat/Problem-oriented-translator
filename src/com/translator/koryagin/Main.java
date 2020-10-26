package com.translator.koryagin;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Gui g = new Gui();

        // show program text on gui
        Path pathInputProgram = Path.of("files/program");
        try {
            List<String> program = Files.readAllLines(pathInputProgram);
            g.setInputAreaText(program, true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }
}
