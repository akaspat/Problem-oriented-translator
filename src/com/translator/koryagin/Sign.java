package com.translator.koryagin;

/*
    Знак = Пер ! Цел
    Sign = Variable ! MInteger
 */

public class Sign extends Leksem {
    private String sign;

    public Sign(String s) { sign = s; }

    @Override
    protected void check() throws LeksemException{
        Variable var = new Variable(sign);
        MInteger m_int = new MInteger(sign);

        if (!var.isCorrect(false) && !m_int.isCorrect(false)){
            String error_text = String.format("[Знак] ожидалась переменная или целое вместо %s", sign);
            throw new LeksemException(error_text);
        }
    }
}
