package com.rideLink.app.RideLink.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {
    private String name;
    private String email;
    private String password;
}
