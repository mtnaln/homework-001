package com.metin.homework.pinsoft.rest;

import com.metin.homework.pinsoft.service.RPNCalculatorService;
import com.metin.homework.pinsoft.service.RPNCalculatorServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/calculation")
public class RPNCalculatorController {

    private final RPNCalculatorService rpnCalculatorService;

    public RPNCalculatorController(RPNCalculatorServiceImpl rpnCalculatorService) {
        this.rpnCalculatorService = rpnCalculatorService;
    }

    @GetMapping
    public ResponseEntity<Double> calculate(@RequestParam String expression) {
        List<String> items = Arrays.stream(expression.split(" "))
                .filter(t -> !t.isBlank()).collect(Collectors.toList());

        return rpnCalculatorService.calculate(items);
    }
}
