package com.metin.homework.pinsoft.service;

import com.metin.homework.pinsoft.exception.InconsistentColumnNumberException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MineSweeperServiceImpl implements MineSweeperService {

    @Override
    public ResponseEntity<List<char[]>> findMines(List<char[]> items) {
        if (!isColumnNumbersAreEqual(items)) {
            throw new InconsistentColumnNumberException("Inconsistent Column Number");
        }

        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < items.get(i).length; j++) {
                if (items.get(i)[j] == '.') {
                    int minesCount = calculateMinesForOnePoint(i, j, items);
                    items.get(i)[j] = String.valueOf(minesCount).toCharArray()[0];
                }
            }
        }

        return ResponseEntity.ok(items);
    }

    private boolean isColumnNumbersAreEqual(List<char[]> items) {
        int firstRowColumnNumber = items.get(0).length;
        boolean isEqual = true;
        if (items.size() > 0) {
            for (int i = 1; i < items.size(); i++) {
                if (items.get(i).length != firstRowColumnNumber) {
                    isEqual = false;
                }
            }
        }

        return isEqual;
    }

    private int calculateMinesForOnePoint(int row, int column, List<char[]> items) {
        int count = 0;
        if (row - 1 >= 0 && items.get(row - 1)[column] == '*') { // NORTH
            count++;
        }
        if (row + 1 < items.size() && items.get(row + 1)[column] == '*') { // SOUTH
            count++;
        }
        if (column + 1 < items.get(row).length && items.get(row)[column + 1] == '*') { // EAST
            count++;
        }
        if (column - 1 >= 0 && items.get(row)[column - 1] == '*') { // WEST
            count++;
        }
        if (row - 1 >= 0 && column + 1 < items.get(row).length && items.get(row - 1)[column + 1] == '*') { // NORTH-EAST
            count++;
        }
        if (row - 1 >= 0 && column - 1 >= 0 && items.get(row - 1)[column - 1] == '*') { // NORTH-WEST
            count++;
        }
        if (row + 1 < items.size() && column + 1 < items.get(row).length && items.get(row + 1)[column + 1] == '*') { // SOUTH-EAST
            count++;
        }
        if (row + 1 < items.size() && column - 1 >= 0 && items.get(row + 1)[column - 1] == '*') { // SOUTH-WEST
            count++;
        }

        return count;
    }
}
