package com.translator.koryagin;

/*
    цел = циф ... циф
    циф = 0 ! … ! 9 : (digit)
 */

public class MInteger extends Leksem{
    String integer;

    public MInteger(String _int){
        integer = _int;
    }
    public void setIntegerString(String _int){ integer = _int; }

    @Override
    protected void check() throws LeksemException {
        if (integer.length() == 0) { return; }

        for (int i=0; i < integer.length(); i++){
            char symbol = integer.charAt(i);
            if (!isDigit(symbol)){
                String error_text = "[Целое " + integer + " ]"
                        + " ожидалась цифра вместо " + symbol;
                throw new LeksemException(error_text);
            }
        }
    }
}
