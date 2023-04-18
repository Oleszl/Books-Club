package com.excellentbook.excellentbook.service.impl;

import com.excellentbook.excellentbook.dto.order.OrderDetailsDto;
import com.excellentbook.excellentbook.entity.Book;
import com.excellentbook.excellentbook.entity.Order;
import com.excellentbook.excellentbook.entity.User;
import com.excellentbook.excellentbook.repository.OrderRepository;
import com.excellentbook.excellentbook.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order addDetailsToOrder(Book book, User buyer) {
        Order order = new Order();
        order.setBook(book);
        order.setBuyer(buyer);
        order.setOwner(book.getOwner());
        return orderRepository.save(order);
    }

}
