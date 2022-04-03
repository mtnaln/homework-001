package com.metin.homework.pinsoft.unit;

import com.metin.homework.pinsoft.exception.InconsistentColumnNumberException;
import com.metin.homework.pinsoft.service.MineSweeperService;
import com.metin.homework.pinsoft.service.MineSweeperServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class MineSweeperServiceTests {

    MineSweeperService mineSweeperService = new MineSweeperServiceImpl();

    @Test
    void multiDimensions() {
        final String testInput = "**...,.....,.*...";
        List<char[]> items = Arrays.stream(testInput.split(","))
                .map(t -> t.toCharArray())
                .collect(Collectors.toList());

        ResponseEntity<List<char[]>> mines = mineSweeperService.findMines(items);

        Assertions.assertEquals(HttpStatus.OK, mines.getStatusCode());

        final int EXPECTED_ROW_COUNT = 3;
        Assertions.assertEquals(EXPECTED_ROW_COUNT, mines.getBody().size());

        final int EXPECTED_COLUMN_COUNT = 5;
        Assertions.assertEquals(EXPECTED_COLUMN_COUNT, mines.getBody().get(0).length);
        Assertions.assertEquals(EXPECTED_COLUMN_COUNT, mines.getBody().get(1).length);
        Assertions.assertEquals(EXPECTED_COLUMN_COUNT, mines.getBody().get(2).length);

        // Expected Result Values: ["**100","33200","1*100"]
        Assertions.assertEquals('*', mines.getBody().get(0)[0]);
        Assertions.assertEquals('*', mines.getBody().get(0)[1]);
        Assertions.assertEquals('1', mines.getBody().get(0)[2]);
        Assertions.assertEquals('0', mines.getBody().get(0)[3]);
        Assertions.assertEquals('0', mines.getBody().get(0)[4]);

        Assertions.assertEquals('3', mines.getBody().get(1)[0]);
        Assertions.assertEquals('3', mines.getBody().get(1)[1]);
        Assertions.assertEquals('2', mines.getBody().get(1)[2]);
        Assertions.assertEquals('0', mines.getBody().get(1)[3]);
        Assertions.assertEquals('0', mines.getBody().get(1)[4]);

        Assertions.assertEquals('1', mines.getBody().get(2)[0]);
        Assertions.assertEquals('*', mines.getBody().get(2)[1]);
        Assertions.assertEquals('1', mines.getBody().get(2)[2]);
        Assertions.assertEquals('0', mines.getBody().get(2)[3]);
        Assertions.assertEquals('0', mines.getBody().get(2)[4]);
    }

    @Test
    void singleDimension() {
        final String testInput = "**...";
        List<char[]> items = Arrays.stream(testInput.split(","))
                .map(t -> t.toCharArray())
                .collect(Collectors.toList());

        ResponseEntity<List<char[]>> mines = mineSweeperService.findMines(items);

        Assertions.assertEquals(HttpStatus.OK, mines.getStatusCode());

        final int EXPECTED_ROW_COUNT = 1;
        Assertions.assertEquals(EXPECTED_ROW_COUNT, mines.getBody().size());

        final int EXPECTED_COLUMN_COUNT = 5;
        Assertions.assertEquals(EXPECTED_COLUMN_COUNT, mines.getBody().get(0).length);

        // Result Values: ["**100"]
        Assertions.assertEquals('*', mines.getBody().get(0)[0]);
        Assertions.assertEquals('*', mines.getBody().get(0)[1]);
        Assertions.assertEquals('1', mines.getBody().get(0)[2]);
        Assertions.assertEquals('0', mines.getBody().get(0)[3]);
        Assertions.assertEquals('0', mines.getBody().get(0)[4]);
    }

    @Test
    void inconsistentColumnNumber() {
        final String testInput = "**...,...,.*..";
        List<char[]> items = Arrays.stream(testInput.split(","))
                .map(t -> t.toCharArray())
                .collect(Collectors.toList());

        InconsistentColumnNumberException exception =
                Assertions.assertThrows(InconsistentColumnNumberException.class, () -> mineSweeperService.findMines(items));

        Assertions.assertEquals("Inconsistent Column Number", exception.getMessage());
    }
}
