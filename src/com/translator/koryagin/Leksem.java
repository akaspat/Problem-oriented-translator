package com.translator.koryagin;

public abstract class Leksem {

    protected abstract void check() throws LeksemException;

    public boolean isCorrect(boolean showError) {
        try{
            check();
        } catch (LeksemException ex){
            if (showError) {
                Gui.showError(ex.getMessage());
            }
            return false;
        }
        return true;
    }

    protected boolean isLetter(char s){
        /*
        UNICODE
        Aа - Яя (04 10 - 04 4F hex code)
        (1040 - 1103 dec code)
        */
        return (s >= 1040 && s <= 1103);
    }

    protected boolean isDigit(char s){
        /*
        UNICODE
        0 - 9   (00 30 - 00 39 hex code)
                (48 - 57 dec code)
         */
        return (s >= 48 && s <= 57);
    }

    protected class LeksemException extends Exception{
        protected String message;
        public LeksemException(String m) { message = m; }
        public String getMessage(){
            return message;
        }
    }
}
