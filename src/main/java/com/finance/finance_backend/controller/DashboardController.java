package com.finance.finance_backend.controller;

import com.finance.finance_backend.model.FinancialRecord;
import com.finance.finance_backend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        return ResponseEntity.ok(dashboardService.getSummary());
    }

    @GetMapping("/category-totals")
    public ResponseEntity<Map<String, Double>> getCategoryTotals() {
        return ResponseEntity.ok(dashboardService.getCategoryWiseTotals());
    }

    @GetMapping("/monthly-trends")
    public ResponseEntity<Map<String, Double>> getMonthlyTrends() {
        return ResponseEntity.ok(dashboardService.getMonthlyTrends());
    }

    @GetMapping("/recent-activity")
    public ResponseEntity<List<FinancialRecord>> getRecentActivity() {
        return ResponseEntity.ok(dashboardService.getRecentActivity());
    }
}