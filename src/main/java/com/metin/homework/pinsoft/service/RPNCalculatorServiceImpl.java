package com.metin.homework.pinsoft.service;

import com.metin.homework.pinsoft.exception.InvalidInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Stack;

@Service
public class RPNCalculatorServiceImpl implements RPNCalculatorService {

    public ResponseEntity<Double> calculate(List<String> items) {
        boolean isFirstOperationDone = false; // need two elements to operate
        double result = -1;
        Stack<String> elements = new Stack<>();
        for (String item : items) {
            boolean isItemValid = chechIfItemIsValid(item);
            if (!isItemValid) {
                throw new InvalidInputException("Invalid Input");
            }

            if ("/".equals(item) || "+".equals(item) || "-".equals(item) || "*".equals(item)) {
                double value = Double.valueOf(elements.pop());
                if (isFirstOperationDone) {
                    result = operate(item, result, value);
                } else {
                    double value2 = Double.valueOf(elements.pop());
                    isFirstOperationDone = true;
                    result = firstOperate(item, value, value2);
                }
            } else {
                elements.push(item);
            }
        }

        return ResponseEntity.ok(result);
    }

    private static boolean chechIfItemIsValid(String item) {
        final String validSpecialCharacters = "/+-*";

        if (!item.chars().allMatch(Character::isDigit) && !validSpecialCharacters.contains(item)) {
            return false;
        }

        return true;
    }

    private double operate(String item, double result, double value) {
        if ("/".equals(item)) {
            result = result / value;
        } else if ("+".equals(item)) {
            result = result + value;
        } else if ("*".equals(item)) {
            result = result * value;
        } else if ("-".equals(item)) {
            result = result - value;
        }

        return result;
    }

    // This method runs once
    private double firstOperate(String item, double value, double value2) {
        double result = -1;
        if ("/".equals(item)) {
            result = value2 / value;
        } else if ("+".equals(item)) {
            result = value2 + value;
        } else if ("*".equals(item)) {
            result = value2 * value;
        } else if ("-".equals(item)) {
            result = value2 - value;
        }

        return result;
    }
}