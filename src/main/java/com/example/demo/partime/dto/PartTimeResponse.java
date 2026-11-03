
package com.example.demo.partime.dto;

import java.time.LocalDateTime;

public record PartTimeResponse(
        Integer id,
        String fullName,
        String personalEmail,
        String workEmail,
        String personalPhoneNumber,
        LocalDateTime joinDate) {
}