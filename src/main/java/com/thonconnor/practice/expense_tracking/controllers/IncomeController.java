package com.thonconnor.practice.expense_tracking.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thonconnor.practice.expense_tracking.models.IncomeModel;
import com.thonconnor.practice.expense_tracking.models.IncomesModel;
import com.thonconnor.practice.expense_tracking.models.ResponseResult;
import com.thonconnor.practice.expense_tracking.models.requests.ReadListInput;
import com.thonconnor.practice.expense_tracking.services.IncomeService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
@Slf4j
public class IncomeController {
    private final IncomeService incomeService;

    @GetMapping(path = "/incomes", produces = "application/json")
    public ResponseEntity<ResponseResult<IncomesModel>> readIncomes(@RequestParam String userId,
            @RequestParam @DateTimeFormat(pattern = "MM-dd-yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "MM-dd-yyyy") LocalDate endDate) {
        log.info("read income - start");
        List<IncomeModel> incomeModels = incomeService
                .readIncomes(new ReadListInput(userId, startDate, endDate, null, null));
        log.info("read incomes - end");
        return ResponseEntity.ok()
                .body(ResponseResult.<IncomesModel>builder().data(new IncomesModel(incomeModels)).build());
    }
}
