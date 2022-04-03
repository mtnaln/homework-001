package com.metin.homework.pinsoft.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MineSweeperService {
    ResponseEntity<List<char[]>> findMines(List<char[]> items);
}
