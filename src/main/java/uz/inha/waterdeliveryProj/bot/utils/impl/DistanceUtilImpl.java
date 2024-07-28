package uz.inha.waterdeliveryProj.bot.utils.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.inha.waterdeliveryProj.bot.model.Location;
import uz.inha.waterdeliveryProj.bot.utils.DistanceUtil;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DistanceUtilImpl implements DistanceUtil {
    private static final String API_KEY = "AIzaSyBW2VQF8bZ1r6IpZ4Imtqaydl4ohyyrOsQ";

    @Override
    public String getOptimizedRoute(List<Location> locations, Location from) {
        RestTemplate restTemplate = new RestTemplate();
        String url = buildDirectionsApiUrl(locations, from);
        return restTemplate.getForObject(url, String.class);
    }

    @Override
    public String buildDirectionsApiUrl(List<Location> locations, Location locationFrom) {
        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        url.append("origin=").append(locationFrom.toString());
        url.append("&destination=").append(locationFrom.toString());
        url.append("&waypoints=optimize:true");

        for (Location location : locations) {
            url.append("|").append(location.toString());
        }

        url.append("&key=").append(API_KEY);
        return url.toString();
    }

    @Override
    public long getTotalTime(String jsonResponse) {
        long totalTime = 0;
        try {
            String[] legs = jsonResponse.split("\"legs\"");
            for (String leg : legs) {
                String[] durations = leg.split("\"duration\"");
                for (String duration : durations) {
                    String[] values = duration.split("\"value\"");
                    if (values.length > 1) {
                        String value = values[1].split(",")[0].replaceAll("[^0-9]", "").trim();
                        totalTime += Long.parseLong(value);
                    }
                }
            }
        } catch (NumberFormatException e) {
            log.error("Number format exception while duration value ", e);
        } catch (Exception e) {
            log.error("Exception while parsing JSON response", e);
        }
        return totalTime;
    }

    @Override
    public int[] getOptimizedOrder(String jsonResponse) {
        try {
            String[] wayPointOrderPart = jsonResponse.split("\"waypoint_order\"\\s*:\\s*\\[")[1].split("]")[0].split(",");
            int[] wayPointOrder = new int[wayPointOrderPart.length];
            for (int i = 0; i < wayPointOrderPart.length; i++) {
                wayPointOrder[i] = Integer.parseInt(wayPointOrderPart[i].trim());
            }
            return wayPointOrder;
        } catch (Exception e) {
            log.error("Exception while parsing waypoint order", e);
        }
        return new int[0];
    }

}
