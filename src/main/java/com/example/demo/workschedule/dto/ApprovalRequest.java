package com.example.demo.workschedule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApprovalRequest {

    @NotNull(message = "Approval decision is required")
    private Boolean isApproved;

    private String reason;
}
