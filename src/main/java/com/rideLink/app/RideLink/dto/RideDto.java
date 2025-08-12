package com.rideLink.app.RideLink.dto;

import com.rideLink.app.RideLink.entities.Driver;
import com.rideLink.app.RideLink.entities.Rider;
import com.rideLink.app.RideLink.entities.enums.PaymentMethod;
import com.rideLink.app.RideLink.entities.enums.RideStatus;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

public class RideDto {
    private Long id;
    private Point pickupLocation;
    private Point dropOffLocation;
    private LocalDateTime createdTime;
    private RiderDto rider;
    private DriverDto driver;
    private PaymentMethod paymentMethod;
    private RideStatus rideStatus;
    private Double fair;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
