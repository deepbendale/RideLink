package com.rideLink.app.RideLink.strategies;

import com.rideLink.app.RideLink.strategies.impl.DriverMatchingHighestRatedDriverStrategy;
import com.rideLink.app.RideLink.strategies.impl.DriverMatchingNearestDriverStrategy;
import com.rideLink.app.RideLink.strategies.impl.RideFareDefaultFareCalculationStrategy;
import com.rideLink.app.RideLink.strategies.impl.RideFareSurgePricingFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {

    private final DriverMatchingHighestRatedDriverStrategy highestRatedDriverStrategy;
    private final DriverMatchingNearestDriverStrategy nearestDriverStrategy;
    private final RideFareSurgePricingFareCalculationStrategy surgePricingFareCalculationStrategy;
    private final RideFareDefaultFareCalculationStrategy defaultFareCalculationStrategy;

    public DriverMatchingStrategy driverMatchingStrategy(double riderRating){
        if(riderRating >=4.8){
            return highestRatedDriverStrategy;
        } else {
            return nearestDriverStrategy;
        }

    }

    public RideFareCalculationStrategy rideFareCalculationStrategy(){
        //6PM TO 9PM
        LocalTime surgeStartTime = LocalTime.of(18, 0);
        LocalTime surgeEndTime = LocalTime.of(21, 0);
        LocalTime currentTime = LocalTime.now();
        boolean isSurgeTime = currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);

        if(isSurgeTime){
            return surgePricingFareCalculationStrategy;
        } else {
            return defaultFareCalculationStrategy;
        }


    }

}
