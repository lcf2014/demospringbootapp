package com.luafaria.demorestapi.controller;

import com.luafaria.demorestapi.model.Statistics;
import com.luafaria.demorestapi.model.Transaction;
import com.luafaria.demorestapi.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;

    @PostMapping("/transactions")
    public ResponseEntity<Transaction> insertTransaction(@RequestBody Transaction transaction) {
        return new ResponseEntity<Transaction>(statisticsService.insertTransaction(transaction), HttpStatus.CREATED);
    }

    @GetMapping("/statistics")
    public ResponseEntity<Statistics> getStatistics() {
        return new ResponseEntity<Statistics>(statisticsService.getStatistics(), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public HttpStatus deleteTransactions(@RequestParam String inputUserId) {
        statisticsService.deleteTransactions();
        return HttpStatus.OK;
    }

}
