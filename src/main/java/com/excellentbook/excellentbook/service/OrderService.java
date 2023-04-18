package com.excellentbook.excellentbook.service;

import com.excellentbook.excellentbook.dto.order.OrderDetailsDto;
import com.excellentbook.excellentbook.entity.Book;
import com.excellentbook.excellentbook.entity.Order;
import com.excellentbook.excellentbook.entity.User;

public interface OrderService {
    Order addDetailsToOrder(Book book, User buyer);

}
