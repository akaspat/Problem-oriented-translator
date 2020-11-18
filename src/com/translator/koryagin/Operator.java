package com.translator.koryagin;

/*
    Опер = Метка ":" пер "=" прав.часть
    Метка = цел

    Operator = MInteger ":" Variable "=" CalculatePart
 */

public class Operator extends Leksem {
    private String oper;

    public Operator() {};
    public Operator(String _operator) { oper = _operator; }
    public void setOperator(String _operator) { oper = _operator; }

    @Override
    protected void check() throws LeksemException {
        String tmp_oper = oper;
        String[] elements = tmp_oper.split(":");
        if (elements.length == 1) {
            String message_error = "Ошибка. Ожидался знак двоеточия после метки в операторе " + oper;
            throw new LeksemException(message_error);
        }

        // get MInteger (метка = цел)
        MInteger metka = new MInteger(elements[0]);
        metka.isCorrect(true);

        // remove correct substring
        tmp_oper = tmp_oper.replaceFirst(elements[0]+":", "");


        elements = tmp_oper.split("=");
        if (elements.length == 1) {
            String message_error = "Ошибка. Ожидался знак равенства после переменной в операторе " + oper;
            throw new LeksemException(message_error);
        }

        // get Variable (пер)
        Variable var = new Variable(elements[0]);
        var.isCorrect(true);

        // remove correct substring
        tmp_oper = tmp_oper.replaceFirst(elements[0]+"=", "");
        tmp_oper = tmp_oper.replaceAll(" ", "#");

        // get CalculatePart (прав.часть)
        String calPratString = tmp_oper;
        CalculatePart cp = new CalculatePart(calPratString);
        cp.isCorrect(true);
    }
}
