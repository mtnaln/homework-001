package com.metin.homework.pinsoft.service;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RPNCalculatorService {
    ResponseEntity<Double> calculate(List<String> items);
}
