package uz.inha.waterdeliveryProj.bot.utils;

import uz.inha.waterdeliveryProj.bot.model.Location;

import java.util.List;

public interface DistanceUtil {
    String buildDirectionsApiUrl(List<Location> locations, Location locationFrom);

    long getTotalTime(String jsonResponse);

    int[] getOptimizedOrder(String jsonResponse);
}
