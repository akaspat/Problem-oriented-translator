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
    protected void check() throws MIntegerException {
        if (integer.length() == 0) { return; }

        for (int i=0; i < integer.length(); i++){
            char symbol = integer.charAt(i);
            if (!isDigit(symbol)){
                String error_text = "[Integer " + integer + " ]"
                        + " ожидалась цифра вместо " + symbol;
                throw new MIntegerException(error_text);
            }
        }
    }

    @Override
    public void isCorrect(){
        try{
            check();
        }catch (MIntegerException ex){
            System.out.println(ex.getMessage());
        }
    }

    private class MIntegerException extends Exception {
        private String message;
        public MIntegerException(String m) { message = m; }
        public String getMessage() {return message; }
    }
}
