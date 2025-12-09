package com.rideLink.app.RideLink.services.impl;

import com.rideLink.app.RideLink.services.DistanceService;
import lombok.Data;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class DistanceServiceOSRMImpl implements DistanceService {

    private static final String OSRM_API_BASE_URL = "https://router.project-osrm.org/route/v1/driving/";
    private final WebClient webClient = WebClient.create(OSRM_API_BASE_URL);

    @Override
    public double calculateDistance(Point src, Point dest) {
        try {
            // osrm expects lon,lat pairs
            String uri = src.getX() + "," + src.getY() + ";" + dest.getX() + "," + dest.getY() + "?overview=false";
            OSRMResponseDto responseDto = webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(OSRMResponseDto.class)
                    .block();

            if (responseDto == null || responseDto.getRoutes() == null || responseDto.getRoutes().isEmpty()) {
                throw new RuntimeException("OSRM returned no routes");
            }

            return responseDto.getRoutes().get(0).getDistance() / 1000.0;
        } catch (Exception e) {
            throw new RuntimeException("Error getting data from OSRM: " + e.getMessage(), e);
        }
    }
}

@Data
class OSRMResponseDto {
    private List<OSRMRoute> routes;
}

@Data
class OSRMRoute {
    private Double distance;
}
