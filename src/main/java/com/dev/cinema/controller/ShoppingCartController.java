package com.dev.cinema.controller;

import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import com.dev.cinema.model.dto.MovieSessionRequestDto;
import com.dev.cinema.model.dto.TicketResponseDto;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/shoppingcarts")
public class ShoppingCartController {
    private ShoppingCartService shoppingCartService;
    private UserService userService;
    private MovieService movieService;
    private CinemaHallService cinemaHallService;

    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  UserService userService,
                                  MovieService movieService,
                                  CinemaHallService cinemaHallService) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.movieService = movieService;
        this.cinemaHallService = cinemaHallService;
    }

    @PostMapping(value = "/addmoviesession")
    public ShoppingCart addMovieSession(@RequestBody MovieSessionRequestDto movieSessionDto,
                                        @RequestParam Long userId) {
        User user = userService.get(userId);
        MovieSession movieSession = transferDtoToMovieSession(movieSessionDto);
        shoppingCartService.addSession(movieSession, user);
        return shoppingCartService.getByUser(user);
    }

    @GetMapping(value = "/byuser")
    public List<TicketResponseDto> getByUser(@RequestParam Long userId) {
        return shoppingCartService.getByUser(userService.get(userId)).getTickets().stream()
                .map(this::transferTicketToDto).collect(Collectors.toList());
    }

    private TicketResponseDto transferTicketToDto(Ticket ticket) {
        TicketResponseDto ticketDto = new TicketResponseDto();
        ticketDto.setMovieTitle(ticket.getMovie().getTitle());
        ticketDto.setCinemaHallDescription(ticket.getCinemaHall().getDescription());
        ticketDto.setShowTime(ticket.getShowTime().toString());
        return ticketDto;
    }

    private MovieSession transferDtoToMovieSession(MovieSessionRequestDto movieSessionDto) {
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movieService.get(movieSessionDto.getMovieId()));
        movieSession.setCinemaHall(cinemaHallService
                .get(movieSessionDto.getCinemaHallId()));
        LocalDateTime localDateTime = LocalDateTime.parse(movieSessionDto.getShowTime());
        movieSession.setShowTime(localDateTime);
        return movieSession;
    }
}
