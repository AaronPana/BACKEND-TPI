package com.backend_tpi.ms_rutas.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;
import java.util.Map;

import com.backend_tpi.ms_rutas.dtos.responses.RutaAlternativaDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OsrmService {

    private final String OSRM_BASE_URL = "http://localhost:5000";

    public List<RutaAlternativaDTO> obtenerRutasAlternativas(double origenLat, double origenLon, double destinoLat, double destinoLon) {

        RestTemplate restTemplate = new RestTemplate();

        String url = UriComponentsBuilder.fromHttpUrl(OSRM_BASE_URL + "/route/v1/driving/" +
                        origenLon + "," + origenLat + ";" + destinoLon + "," + destinoLat)
                .queryParam("alternatives", "true")
                .queryParam("overview", "full")
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> routes = (List<Map<String, Object>>) response.get("routes");
        List<RutaAlternativaDTO> alternativas = new ArrayList<>();

        int i = 1;
        for (Map<String, Object> route : routes) {
            RutaAlternativaDTO dto = new RutaAlternativaDTO();
            dto.setOpcion(i++);
            dto.setDistancia(((Number) route.get("distance")).doubleValue());
            dto.setDuracion(((Number) route.get("duration")).doubleValue());
            alternativas.add(dto);
        }

        return alternativas;
    }
}
