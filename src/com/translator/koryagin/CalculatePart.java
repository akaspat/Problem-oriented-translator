package com.translator.koryagin;

/*
    CalculatePart consist of Brackets, Variable, MInteger
 */

import java.util.ArrayList;
import java.util.Collections;

public class CalculatePart extends Leksem {
    private String calcPart;
    private String tmp_calcPart;

    public CalculatePart() {};
    public CalculatePart(String cp) { calcPart = cp; }
    public void setCalcPartString(String cp) { calcPart = cp; }

    private boolean isUnarySign(char s){
        return s == '-' || s == '!';
    }

    private int posOfBinarySign(String s){
        if (s.equals("")) return -1;
        ArrayList<Integer> positions = new ArrayList<>();

        positions.add( (!s.contains("+")) ? s.length() : s.indexOf("+") );
        positions.add( (!s.contains("-")) ? s.length() : s.indexOf("-") );

        positions.add( (!s.contains("*")) ? s.length() : s.indexOf("*") );
        positions.add( (!s.contains("/")) ? s.length() : s.indexOf("/") );

        positions.add( (!s.contains("|")) ? s.length() : s.indexOf("|") );
        positions.add( (!s.contains("&")) ? s.length() : s.indexOf("&") );

        return Collections.min(positions);
    }

    private String getRightestExpressionInBracket(){
        // выделим выражение в скобках по убыванию глубины
        // от самых глубоких скобок - внаружу
        String expression = "";
        int openBracketPos = -1;
        int closeBracketPos = tmp_calcPart.length();
        for (int i=0; i < tmp_calcPart.length(); i++){
            // поиск самой правой открывающейся скобки
            if (tmp_calcPart.charAt(i) == '(' && i > openBracketPos) { openBracketPos = i; }
            if (tmp_calcPart.charAt(i) == ')' && closeBracketPos > i ) {
                closeBracketPos = i;
                break;
            }
        }
        if (openBracketPos != -1 && closeBracketPos != -1){
            expression = tmp_calcPart.substring(openBracketPos + 1, closeBracketPos);
        } else expression = tmp_calcPart;

        // replace expression on 1
        String replaced = "(" + expression + ")";
        tmp_calcPart = tmp_calcPart.replace(replaced, "1");
        return expression;
    }

    private void checkDigitSign(String block) throws LeksemException{
        String[] elements = block.split("#");
        // если вначале пробел - то знаки должны занимать четные позиции
        if (elements[0].equals(" ") || elements[0].equals("")) {
            for (int i=2; i < elements.length; i+= 2){
                if (posOfBinarySign(elements[i]) != 0){
                    String error_message = "В строке: " + calcPart.replaceAll("#", " ") + "\n";
                    error_message += String.format("Ожидался арифметический знак между %s и %s", elements[i-1], elements[i+1]);
                    throw new LeksemException(error_message);
                }
            }
        }
        // если начинается не с проблемы (а предполагается, что с цифры) , то знаки - на нечет поз.
        else {
            for (int i=1; i < elements.length; i+= 2){
                if (posOfBinarySign(elements[i]) != 0){
                    String error_message = "В строке: " + calcPart.replaceAll("#", " ") + "\n";
                    error_message += "Ожидался арифметический знак: ";
                    for (int j = 0; j <= i; j++) {
                        if (elements[j] == "") { i++; }
                        error_message = error_message + elements[j] + ' ';
                    }
                    throw new LeksemException(error_message);
                }
            }
        }
    }

    private void checkBlock(String block) throws LeksemException {
        block = block.replaceAll("#", "");
        // можем встретить унарные знаки: минус "-" или отрицание "!"
        if (isUnarySign(block.charAt(0))) {
            block = block.substring(1);
        }
        // проверяем остались ли еще бинарные знаки
        int nextSignIndex = posOfBinarySign(block);

        if (nextSignIndex != block.length()) {
            checkBlock(block.substring(nextSignIndex + 1));
        }

        nextSignIndex = posOfBinarySign(block);
        // take part before sign
        if (nextSignIndex != -1) { block = block.substring(0, nextSignIndex); }
        Variable var = new Variable(block);
        MInteger m_int = new MInteger(block);

        if ( !(var.isCorrect(false) || m_int.isCorrect(false)) ){
            String message_error = "Ошибка в правой части: " + calcPart.replaceAll("#", " ") + "\n";
            message_error += "В " + block + " ожидалась переменная или целое";
            throw new LeksemException(message_error);
        }
    }

    @Override
    protected void check() throws LeksemException{
        System.out.println("Calculate part: " + calcPart);

        if (calcPart.charAt(calcPart.length() - 1) != ';'){
            String error_message = String.format("Ошибка в правой части %s. В конце ожидалось ;", calcPart.replaceAll("#", " "));
            throw new LeksemException(error_message);
        }
        calcPart = calcPart.substring(0, calcPart.length() - 1);
        String exp;

        tmp_calcPart = calcPart;
        // цикл проверки выражения в скобках
        do {
            exp = getRightestExpressionInBracket();
            checkDigitSign(exp);
            checkBlock(exp);
        }while (!tmp_calcPart.equals(exp));
    }
}
