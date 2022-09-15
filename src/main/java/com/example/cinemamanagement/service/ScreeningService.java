package com.example.cinemamanagement.service;

import com.example.cinemamanagement.model.Screening;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreeningService {

    Long createScreening(String name, LocalDateTime startTime, List<Long> movies);

    Optional<Screening> getScreening(Long marathonId);

    List<Screening> getAllScreenings();

    void removeScreening(Long screeningId);
}
