package com.dev.cinema.controller;

import com.dev.cinema.model.Order;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import com.dev.cinema.model.dto.TicketResponseDto;
import com.dev.cinema.model.dto.UserRequestDto;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {
    private OrderService orderService;
    private UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping(value = "/complete")
    public Order completeOrder(@RequestBody UserRequestDto userDto) {
        User user = userService.findByEmail(userDto.getEmail());
        return orderService.completeOrder(user);
    }

    @GetMapping(value = "/all")
    public List<TicketResponseDto> getAllOrders() {
        return orderService.getAll().stream()
                .flatMap(order -> order.getTickets().stream())
                .map(this::transferTicketToDto)
                .collect(Collectors.toList());
    }

    private TicketResponseDto transferTicketToDto(Ticket ticket) {
        TicketResponseDto ticketDto = new TicketResponseDto();
        ticketDto.setMovieTitle(ticket.getMovie().getTitle());
        ticketDto.setCinemaHallDescription(ticket.getCinemaHall().getDescription());
        ticketDto.setShowTime(ticket.getShowTime().toString());
        return ticketDto;
    }
}
