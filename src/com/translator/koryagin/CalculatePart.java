package com.translator.koryagin;

/*
    CalculatePart consist of Brackets, Variable, MInteger
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CalculatePart extends Leksem {
    private String calcPart;

    public CalculatePart() {};
    public CalculatePart(String cp) { calcPart = cp; }
    public void setCalcPartString(String cp) { calcPart = cp; }

    private boolean isUnarySign(char s){
        return s == '-' || s == '!';
    }

    private int posOfBinarySign(String s){
        ArrayList<Integer> positions = new ArrayList<Integer>();

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
        int closeBracketPos = calcPart.length();
        for (int i=0; i < calcPart.length(); i++){
            // поиск самой правой открывающейся скобки
            if (calcPart.charAt(i) == '(' && i > openBracketPos) { openBracketPos = i; }
            if (calcPart.charAt(i) == ')' && closeBracketPos > i ) {
                closeBracketPos = i;
                break;
            }
        }
        if (openBracketPos != -1 && closeBracketPos != -1){
            expression = calcPart.substring(openBracketPos + 1, closeBracketPos);
        } else expression = calcPart;

        // replace expression on 1
        String replaced = "(" + expression + ")";
        calcPart = calcPart.replace(replaced, "1");
        return expression;
    }

    private void checkBlock(String block){
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
            System.out.println("Ошибка в блоке: " + block);
        }
    }

    @Override
    protected void check() throws LeksemException{
        System.out.println("Calculate part: " + calcPart);

        if (calcPart.charAt(calcPart.length() - 1) != ';'){
            System.out.println("Ошибка. В конце ожидалось ;");
            return;
        }
        calcPart = calcPart.substring(0, calcPart.length() - 1);
        String exp;

        // цикл проверки выражения в скобках
        do {
            exp = getRightestExpressionInBracket();
            checkBlock(exp);
        }while (!calcPart.equals(exp));
    }
}
