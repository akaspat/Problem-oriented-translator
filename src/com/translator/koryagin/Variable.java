package com.translator.koryagin;

/*
    Пер = бук </симв … симв />
    Симв = бук ! циф
    бук = "А" ! … ! "Я" ! "а" ! … ! "я" : (letter)
    циф = 0 ! … ! 9 : (digit)
 */

public class Variable {
    private String var;

    public Variable(String _var) {
        var = _var;
    }

    public void setVarString(String _var){
        var = _var;
    }

    private void check() throws VariableException {
        if (var.length() == 0) { return; }

        // first symbol must be letter
        char symbol = var.charAt(0);
        if (!isLetter(symbol)) {
            String error_text = "[Переменная " + var + " ] в качестве первого символа ожидалась буква вместо '" + symbol + "'";
            throw new VariableException(error_text);
        }

        // next must be symbol (letter or digit)
        for (int i=1; i < var.length(); i++){
            symbol = var.charAt(i);
            if (!isDigit(symbol) && !isLetter(symbol)){
                String error_text = "[Переменная " + var + " ] в качестве символа ожидалась буква или цифра вместо '" + symbol + "'";
                throw new VariableException(error_text);
            }
        }
    }

    public void isCorrect(){
        try{
            check();
        } catch (VariableException ex){
            System.out.println(ex.getMessage());
        }
    }

    private boolean isLetter(char s){
        /*
        UNICODE
        Aа - Яя (04 10 - 04 4F hex code)
        (1040 - 1103 dec code)
        */
        return (s >= 1040 && s <= 1103);
    }

    private boolean isDigit(char s){
        /*
        UNICODE
        0 - 9   (00 30 - 00 39 hex code)
                (48 - 57 dec code)
         */
        return (s >= 48 && s <= 57);
    }

    private static class VariableException extends Exception{
        String message;
        public VariableException(String m) { message = m; }
        public String getMessage(){
            return message;
        }
    }
}
