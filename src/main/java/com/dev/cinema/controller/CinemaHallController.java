package com.dev.cinema.controller;

import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.dto.CinemaHallRequestDto;
import com.dev.cinema.model.dto.CinemaHallResponseDto;
import com.dev.cinema.service.CinemaHallService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hall")
public class CinemaHallController {

    private CinemaHallService cinemaHallService;

    public CinemaHallController(CinemaHallService cinemaHallService) {
        this.cinemaHallService = cinemaHallService;
    }

    @PostMapping(value = "/add")
    public CinemaHall addCinemaHall(@RequestBody CinemaHallRequestDto cinemaHallRequestDto) {
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(cinemaHallRequestDto.getCapacity());
        cinemaHall.setDescription(cinemaHallRequestDto.getDescription());
        return cinemaHallService.add(cinemaHall);
    }

    @GetMapping(value = "/all")
    public List<CinemaHallResponseDto> getAllMovies() {
        return cinemaHallService.getAll().stream()
                .map(this::transferCinemaHallToDto)
                .collect(Collectors.toList());
    }

    private CinemaHallResponseDto transferCinemaHallToDto(CinemaHall cinemaHall) {
        CinemaHallResponseDto cinemaHallDto = new CinemaHallResponseDto();
        cinemaHallDto.setDescription(cinemaHall.getDescription());
        cinemaHallDto.setCapacity(cinemaHall.getCapacity());
        return cinemaHallDto;
    }
}
