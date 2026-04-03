package com.finance.finance_backend.service;

import com.finance.finance_backend.dto.FinancialRecordRequest;
import com.finance.finance_backend.model.FinancialRecord;
import com.finance.finance_backend.model.User;
import com.finance.finance_backend.repository.FinancialRecordRepository;
import com.finance.finance_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialRecordService {

    private final FinancialRecordRepository recordRepository;
    private final UserRepository userRepository;

    public FinancialRecord createRecord(FinancialRecordRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        FinancialRecord record = new FinancialRecord();
        record.setAmount(request.getAmount());
        record.setType(request.getType().toUpperCase());
        record.setCategory(request.getCategory());
        record.setDate(request.getDate());
        record.setNotes(request.getNotes());
        record.setCreatedBy(user);
        return recordRepository.save(record);
    }

    public List<FinancialRecord> getAllRecords() {
        return recordRepository.findAll();
    }

    public FinancialRecord getRecordById(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
    }

    public FinancialRecord updateRecord(Long id, FinancialRecordRequest request) {
        FinancialRecord record = getRecordById(id);
        record.setAmount(request.getAmount());
        record.setType(request.getType().toUpperCase());
        record.setCategory(request.getCategory());
        record.setDate(request.getDate());
        record.setNotes(request.getNotes());
        return recordRepository.save(record);
    }

    public void deleteRecord(Long id) {
        FinancialRecord record = getRecordById(id);
        recordRepository.delete(record);
    }

    public List<FinancialRecord> filterRecords(String type, String category,
                                                LocalDate startDate, LocalDate endDate) {
        if (type != null && category != null) {
            return recordRepository.findByTypeAndCategory(type.toUpperCase(), category);
        } else if (type != null) {
            return recordRepository.findByType(type.toUpperCase());
        } else if (category != null) {
            return recordRepository.findByCategory(category);
        } else if (startDate != null && endDate != null) {
            return recordRepository.findByDateBetween(startDate, endDate);
        }
        return recordRepository.findAll();
    }
}