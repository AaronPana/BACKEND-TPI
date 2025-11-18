package com.backend_tpi.ms_locations.services;

import com.backend_tpi.ms_locations.dtos.responses.RutaAlternativaDtoRes;
import com.backend_tpi.ms_locations.dtos.responses.RutaDtoRes;
import com.backend_tpi.ms_locations.exceptions.BaseException;
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
    return String.valueOf(this.contadorIdConsultas.getAndIncrement());
  }

  public void storeRouteQuery(String idConsulta, RutaDtoRes response) {
    CachedRouteQuery cached = new CachedRouteQuery(response, LocalDateTime.now());
    cache.put(idConsulta, cached);

    cleanExpiredEntries();
  }

  public Optional<RutaAlternativaDtoRes> getSpecificRoute(String idConsulta, String idRuta) {
    CachedRouteQuery cached = cache.get(idConsulta);

    if (cached == null) throw BaseException.notFoundById("Consulta", idConsulta);

    RutaDtoRes response = cached.response();

    if (response.getRutaDirecta().getIdRuta().equals(idRuta)) {
      return Optional.of(response.getRutaDirecta());
    }

    return Optional.ofNullable(response.getRutasAlternativas().stream()
        .filter(route -> route.getIdRuta().equals(idRuta))
        .findFirst()
        .orElseThrow(
            () -> BaseException.notFoundById("Alternativa", idRuta)
        ));
  }

  public Optional<RutaDtoRes> getFullQuery(String idConsulta) {
    CachedRouteQuery cached = cache.get(idConsulta);

    if (cached == null || cached.isExpired()) {
      cache.remove(idConsulta);
      throw BaseException.notFoundById("Consulta", idConsulta);
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
