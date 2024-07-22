package uz.inha.waterdeliveryProj.bot.utils.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.inha.waterdeliveryProj.bot.model.Location;
import uz.inha.waterdeliveryProj.bot.utils.DistrictUtil;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class DistrictUtilImpl implements DistrictUtil {
    private String apiKey = "AIzaSyBW2VQF8bZ1r6IpZ4Imtqaydl4ohyyrOsQ";
    private final RestTemplate restTemplate;

    @Override
    public String getDistrictName(Location location) {
        String url = String.format(
                "https://maps.googleapis.com/maps/api/geocode/json?latlng=%s,%s&key=%s",
                location.getLatitude(), location.getLongitude(), apiKey
        );

        String response = restTemplate.getForObject(url, String.class);
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            JsonNode resultsNode = rootNode.path("results");

            for (JsonNode result : resultsNode) {
                JsonNode addressComponentsNode = result.path("address_components");
                for (JsonNode component : addressComponentsNode) {
                    JsonNode typesNode = component.path("types");
                    if (typesNode.isArray()) {
                        for (JsonNode type : typesNode) {
                            if (type.asText().equals("sublocality_level_1")) {
                                return component.path("long_name").asText();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "District not found";
    }
}
