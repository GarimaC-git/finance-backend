package com.finance.finance_backend.service;

import com.finance.finance_backend.model.FinancialRecord;
import com.finance.finance_backend.repository.FinancialRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final FinancialRecordRepository recordRepository;

    public Map<String, Object> getSummary() {
        List<FinancialRecord> all = recordRepository.findAll();

        double totalIncome = all.stream()
                .filter(r -> r.getType().equals("INCOME"))
                .mapToDouble(FinancialRecord::getAmount)
                .sum();

        double totalExpense = all.stream()
                .filter(r -> r.getType().equals("EXPENSE"))
                .mapToDouble(FinancialRecord::getAmount)
                .sum();

        double netBalance = totalIncome - totalExpense;

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalIncome", totalIncome);
        summary.put("totalExpense", totalExpense);
        summary.put("netBalance", netBalance);
        summary.put("totalRecords", all.size());
        return summary;
    }

    public Map<String, Double> getCategoryWiseTotals() {
        List<FinancialRecord> all = recordRepository.findAll();
        Map<String, Double> categoryTotals = new HashMap<>();
        for (FinancialRecord record : all) {
            categoryTotals.merge(record.getCategory(), record.getAmount(), Double::sum);
        }
        return categoryTotals;
    }

    public Map<String, Double> getMonthlyTrends() {
        List<FinancialRecord> all = recordRepository.findAll();
        Map<String, Double> monthlyTotals = new HashMap<>();
        for (FinancialRecord record : all) {
            String month = record.getDate().getYear() + "-" + record.getDate().getMonthValue();
            monthlyTotals.merge(month, record.getAmount(), Double::sum);
        }
        return monthlyTotals;
    }

    public List<FinancialRecord> getRecentActivity() {
        List<FinancialRecord> all = recordRepository.findAll();
        return all.stream()
                .sorted(Comparator.comparing(FinancialRecord::getDate).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}