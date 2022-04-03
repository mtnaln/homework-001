package com.metin.homework.pinsoft.rest;

import com.metin.homework.pinsoft.service.MineSweeperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mine-sweeper")
public class MineSweeperController {

    private final MineSweeperService mineSweeperService;

    public MineSweeperController(MineSweeperService mineSweeperService) {
        this.mineSweeperService = mineSweeperService;
    }

    @GetMapping
    public ResponseEntity<List<char[]>> findMines(@RequestParam String expression) {
        List<char[]> items = Arrays.stream(expression.split(","))
                .map(t -> t.toCharArray())
                .collect(Collectors.toList());

        return mineSweeperService.findMines(items);
    }
}
