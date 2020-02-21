package com.dev.cinema.controller;

import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.dto.MovieSessionRequestDto;
import com.dev.cinema.model.dto.MovieSessionResponseDto;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moviesessions")
public class MovieSessionController {
    private final MovieSessionService movieSessionService;
    private final MovieService movieService;
    private final CinemaHallService cinemaHallService;

    public MovieSessionController(
            MovieSessionService movieSessionService, MovieService movieService,
            CinemaHallService cinemaHallService) {
        this.movieSessionService = movieSessionService;
        this.movieService = movieService;
        this.cinemaHallService = cinemaHallService;
    }

    @GetMapping(value = "/all")
    public List<MovieSessionResponseDto> getAllMovieSessions(
            @RequestParam Long movieId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        return movieSessionService.findAvailableSessions(movieId, localDate).stream()
                .map(this::transferMovieSessionToDto)
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/add")
    public MovieSession addMovieSessions(@RequestBody MovieSessionRequestDto
                                                     movieSessionRequestDto) {
        return movieSessionService.add(
                transferDtoToMovieSession(movieSessionRequestDto));
    }

    private MovieSessionResponseDto transferMovieSessionToDto(MovieSession movieSession) {
        MovieSessionResponseDto movieSessionResponseDto = new MovieSessionResponseDto();
        movieSessionResponseDto.setCinemaHallDescription(
                movieSession.getCinemaHall().getDescription());
        movieSessionResponseDto.setMovieTitle(movieSession.getMovie().getTitle());
        movieSessionResponseDto.setShowTime(movieSession.getShowTime().toString());
        return movieSessionResponseDto;
    }

    private MovieSession transferDtoToMovieSession(MovieSessionRequestDto movieSessionRequestDto) {
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movieService.get(movieSessionRequestDto.getMovieId()));
        movieSession.setCinemaHall(cinemaHallService
                .get(movieSessionRequestDto.getCinemaHallId()));
        LocalDateTime localDateTime = LocalDateTime.parse(movieSessionRequestDto.getShowTime());
        movieSession.setShowTime(localDateTime);
        return movieSession;
    }
}
