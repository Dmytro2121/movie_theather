package com.dev.cinema;

import com.dev.cinema.exceptions.AuthenticationException;
import com.dev.cinema.exceptions.DataProcessingException;
import com.dev.cinema.lib.Injector;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.User;
import com.dev.cinema.service.AuthenticationService;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    private static Injector injector = Injector.getInstance("com.dev.cinema");

    public static void main(String[] args)
            throws DataProcessingException, AuthenticationException {
        Movie movie = new Movie();
        movie.setTitle("Fast and Furious 9");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(movie);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(100);
        cinemaHall.setDescription("Premium Hall");
        cinemaHallService.add(cinemaHall);

        MovieSession movieSession = new MovieSession();
        movieSession.setShowTime(LocalDateTime.now());
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setMovie(movie);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.findAvailableSessions(movie.getId(), LocalDate.now())
                .forEach(System.out::println);
        User user = new User();
        AuthenticationService authenticationService = (AuthenticationService)
                injector.getInstance(AuthenticationService.class);
        authenticationService.register("ab@gmail.com", "123");
        authenticationService.register("ab@gmail1.com", "123");
        User user1 = authenticationService.login("ab@gmail1.com", "123");
        System.out.println(user1.getEmail());
    }
}
