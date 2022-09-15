package com.example.cinemamanagement.service;

import com.example.cinemamanagement.model.Screening;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreeningService {

    Long createMarathon(String name, LocalDateTime startDate, List<Long> movies);

    Optional<Screening> getMarathon(Long marathonId);

    List<Screening> getAllMarathons();

    void removeMarathon(Long marathonId);
}
