package com.translator.koryagin;

/*
    Знак = Пер ! Цел
    Sign = Variable ! MInteger
 */

public class Sign extends Leksem {
    private String sign;

    public Sign() {};
    public Sign(String s) { sign = s; }

    public void setSignString(String s) { sign = s; }

    @Override
    protected void check() throws LeksemException{
        Variable var = new Variable(sign);
        MInteger m_int = new MInteger(sign);

        try {
            var.check();
            /*
            if (!var.isCorrect(true) && !m_int.isCorrect(true)) {
                String error_text = String.format("[Знак] ожидалась переменная или целое вместо %s", sign);
                throw new LeksemException(error_text);
            }
            */
        } catch (Exception ex){
            try {
                m_int.check();
            } catch (Exception ex2){
                String error_text = String.format("[Знак] ожидалась переменная или целое вместо %s", sign);
                throw new LeksemException(error_text);
            }
        }
    }
}
