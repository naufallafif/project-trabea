package com.example.demo.partime;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.partime.dto.PartTimeRequest;
import com.example.demo.partime.dto.PartTimeResponse;

import jakarta.validation.Valid;

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

    @PostMapping("/api/register")
    public ResponseEntity<PartTimeResponse> registerPartTimeEmployee(
            @RequestBody @Valid PartTimeRequest partTimeRequest) {
        PartTimeResponse response = partTimeService.registerPartTimeEmployee(partTimeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
