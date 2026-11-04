package com.example.demo.partime;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.partime.dto.PartTimeRequest;
import com.example.demo.partime.dto.PartTimeResponse;
import com.example.demo.partime.dto.PartTimeUpdateRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
public class PartTimeController {
    private final PartTimeService partTimeService;

    public PartTimeController(PartTimeService partTimeService) {
        this.partTimeService = partTimeService;
    }

    @Operation(summary = "Access Administrator")
    @GetMapping("/api/show-all-part-time")
    public ResponseEntity<List<PartTimeResponse>> getAllPartTime() {
        List<PartTimeResponse> partTimeResponses = partTimeService.getAllPartTimeEmployees();
        return ResponseEntity.ok(partTimeResponses);
    }

    @Operation(summary = "Access Administrator")
    @GetMapping("/api/show-all-part-time/{id}")
    public ResponseEntity<PartTimeResponse> getPartTimeEmployeeById(@PathVariable Integer id) {
        return partTimeService.getPartTimeEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Access Administrator")
    @PostMapping("/api/register-part-time")
    public ResponseEntity<PartTimeResponse> registerPartTimeEmployee(
            @RequestBody @Valid PartTimeRequest partTimeRequest) {
        PartTimeResponse response = partTimeService.registerPartTimeEmployee(partTimeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Access Administrator")
    @PutMapping("/api/update-part-time/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<PartTimeResponse> updatePartTimeEmployee(
            @RequestBody @Valid PartTimeUpdateRequest partTimeUpdateRequest,
            @PathVariable Integer id) {
        PartTimeResponse response = partTimeService.updPartTimeResponse(partTimeUpdateRequest, id);
        return ResponseEntity.ok(response);
    }
}
