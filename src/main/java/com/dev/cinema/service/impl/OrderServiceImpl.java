package com.dev.cinema.service.impl;

import com.dev.cinema.dao.OrderDao;
import com.dev.cinema.lib.Inject;
import com.dev.cinema.lib.Service;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private static OrderDao orderDao;
    @Inject
    private static ShoppingCartService shoppingCartService;

    @Override
    public Order completeOrder(User user) {
        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        Order order = new Order();
        order.setTickets(shoppingCart.getTickets());
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        shoppingCartService.clear(shoppingCart);
        return orderDao.add(order);
    }

    @Override
    public List<Order> getOrderHistory(User user) {
        return orderDao.getUserOrders(user);
    }
}
