package com.metin.homework.pinsoft.unit;

import com.metin.homework.pinsoft.exception.InvalidInputException;
import com.metin.homework.pinsoft.service.RPNCalculatorService;
import com.metin.homework.pinsoft.service.RPNCalculatorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

class RPNCalculatorServiceTests {

    RPNCalculatorService rpnCalculatorService = new RPNCalculatorServiceImpl();

    @Test
    void onlyOneOperation() {
        List<String> items = Arrays.asList("20", "5", "/");
        ResponseEntity<Double> actualValue = rpnCalculatorService.calculate(items);
        Assertions.assertEquals(HttpStatus.OK, actualValue.getStatusCode());

        final double EXPECTED_VALUE = 4;
        Assertions.assertEquals(EXPECTED_VALUE, actualValue.getBody());
    }

    @Test
    void onlyOneOperationWithMinues() {
        List<String> items = Arrays.asList("20", "5", "-");
        ResponseEntity<Double> actualValue = rpnCalculatorService.calculate(items);
        Assertions.assertEquals(HttpStatus.OK, actualValue.getStatusCode());

        final double EXPECTED_VALUE = 15;
        Assertions.assertEquals(EXPECTED_VALUE, actualValue.getBody());
    }

    @Test
    void onlyOneOperationWithPlus() {
        List<String> items = Arrays.asList("20", "5", "+");
        ResponseEntity<Double> actualValue = rpnCalculatorService.calculate(items);
        Assertions.assertEquals(HttpStatus.OK, actualValue.getStatusCode());

        final double EXPECTED_VALUE = 25;
        Assertions.assertEquals(EXPECTED_VALUE, actualValue.getBody());
    }

    @Test
    void multipleOperation() {
        List<String> items = Arrays.asList("3", "5", "8", "*", "7", "+", "*");
        ResponseEntity<Double> actualValue = rpnCalculatorService.calculate(items);
        Assertions.assertEquals(HttpStatus.OK, actualValue.getStatusCode());

        final double EXPECTED_VALUE = 141;
        Assertions.assertEquals(EXPECTED_VALUE, actualValue.getBody());
    }

    @Test
    void multipleOperationWithMinus() {
        List<String> items = Arrays.asList("3", "5", "8", "*", "7", "+", "-");
        ResponseEntity<Double> actualValue = rpnCalculatorService.calculate(items);
        Assertions.assertEquals(HttpStatus.OK, actualValue.getStatusCode());

        final double EXPECTED_VALUE = 44;
        Assertions.assertEquals(EXPECTED_VALUE, actualValue.getBody());
    }

    @Test
    void multipleOperationWithDivide() {
        List<String> items = Arrays.asList("10", "20", "10", "*", "5", "/", "-");
        ResponseEntity<Double> actualValue = rpnCalculatorService.calculate(items);
        Assertions.assertEquals(HttpStatus.OK, actualValue.getStatusCode());

        final double EXPECTED_VALUE = 30;
        Assertions.assertEquals(EXPECTED_VALUE, actualValue.getBody());
    }

    @Test
    void invalidSpecialCharacter() {
        List<String> items = Arrays.asList("20", "5", "%");

        InvalidInputException exception =
                Assertions.assertThrows(InvalidInputException.class, () -> rpnCalculatorService.calculate(items));

        Assertions.assertEquals("Invalid Input", exception.getMessage());
    }

    @Test
    void invalidNumberFormat() {
        List<String> items = Arrays.asList("2u", "5", "/");

        InvalidInputException exception =
                Assertions.assertThrows(InvalidInputException.class, () -> rpnCalculatorService.calculate(items));

        Assertions.assertEquals("Invalid Input", exception.getMessage());
    }
}
