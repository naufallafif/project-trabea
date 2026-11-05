package com.example.demo.auth.dto;
 
import lombok.Data;
@Data
public class LoginRequest {

    private String workEmail;
    private String password;
    private String roleName;
}
