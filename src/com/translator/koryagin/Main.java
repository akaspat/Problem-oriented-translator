package com.translator.koryagin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<String> program;

    public static void main(String[] args) {
        Gui g = new Gui();
        g.parseProgramTextToPath(true);
    }
}
