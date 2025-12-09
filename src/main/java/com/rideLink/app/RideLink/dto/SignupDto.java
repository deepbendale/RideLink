package com.rideLink.app.RideLink.dto;

import com.rideLink.app.RideLink.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignupDto {

    private String name;
    private String email;
    private String password;

    private Set<Role> roles;   // VERY IMPORTANT
}
