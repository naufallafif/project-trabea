package com.example.demo.partime;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.partime.dto.PartTimeResponse;

@RestController
public class PartTimeController {
    private final PartTimeService partTimeService;

    public PartTimeController(PartTimeService partTimeService) {
        this.partTimeService = partTimeService;
    }

    @GetMapping("/api/show-all-part-time")
    public ResponseEntity<List<PartTimeResponse>> getAllPartTime() {
        List<PartTimeResponse> partTimeResponses = partTimeService.getAllPartTimeEmployees();
        return ResponseEntity.ok(partTimeResponses);
    }
}
