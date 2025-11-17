package com.backend_tpi.ms_locations.services;

import com.backend_tpi.ms_locations.dtos.responses.RutaAlternativaDtoRes;
import com.backend_tpi.ms_locations.dtos.responses.RutaDtoRes;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CacheRutaService {

  private final Map<String, CachedRouteQuery> cache = new ConcurrentHashMap<>();
  private final AtomicLong contadorIdConsultas = new AtomicLong(1);

  private static final long CACHE_TTL_MINUTES = 30;

  public String getNextIdConsulta() {
    return "rutas-" + this.contadorIdConsultas.getAndIncrement();
  }

  public void storeRouteQuery(String consultaId, RutaDtoRes response) {
    CachedRouteQuery cached = new CachedRouteQuery(response, LocalDateTime.now());
    cache.put(consultaId, cached);

    cleanExpiredEntries();
  }

  public Optional<RutaAlternativaDtoRes> getSpecificRoute(String consultaId, String routeId) {
    CachedRouteQuery cached = cache.get(consultaId);

    RutaDtoRes response = cached.response();

    if (response.getRutaDirecta().getIdRuta().equals(routeId)) {
      return Optional.of(response.getRutaDirecta());
    }

    return response.getRutasAlternativas().stream()
        .filter(route -> route.getIdRuta().equals(routeId))
        .findFirst();
  }

  public Optional<RutaDtoRes> getFullQuery(String consultaId) {
    CachedRouteQuery cached = cache.get(consultaId);

    if (cached == null || cached.isExpired()) {
      cache.remove(consultaId);
      return Optional.empty();
    }

    return Optional.of(cached.response());
  }

  private void cleanExpiredEntries() {
    cache.entrySet().removeIf(entry -> entry.getValue().isExpired());
  }

  private record CachedRouteQuery(RutaDtoRes response, LocalDateTime timestamp) {

    public RutaDtoRes getRuta() {
      return response;
    }

    public boolean isExpired() {
      return LocalDateTime.now().isAfter(timestamp.plusMinutes(CACHE_TTL_MINUTES));
    }
  }
}
