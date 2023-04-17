package com.excellentbook.excellentbook.repository;

import com.excellentbook.excellentbook.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
