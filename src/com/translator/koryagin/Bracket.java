package com.translator.koryagin;

public class Bracket extends Leksem {
    private char bracket;
    public static int openBracketCounter = 0;
    public static int closeBracketCounter = 0;

    public Bracket(char _bracket) {
        bracket = _bracket;
    }

    public void setBracketCharacter(char b) { bracket = b; }

    @Override
    protected void check() throws Leksem.LeksemException {
        if (!(bracket == '(' || bracket == ')')){
            throw new Leksem.LeksemException("[Скобки] ожидался символ скобки вместо '" + bracket + "'");
        }

        if (bracket == '(') openBracketCounter++;
        else closeBracketCounter++;

        if (openBracketCounter != closeBracketCounter){
            throw new Leksem.LeksemException("[Скобки] неравное число открывающих и закрывающих скобок");
        }
    }
}
