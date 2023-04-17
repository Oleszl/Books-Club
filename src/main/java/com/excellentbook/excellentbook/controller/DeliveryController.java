package com.excellentbook.excellentbook.controller;

import com.excellentbook.excellentbook.dto.delivery.PostOfficeDetailsDto;
import com.excellentbook.excellentbook.service.DeliveryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/delivery")
public class DeliveryController {
    private DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping
    public PostOfficeDetailsDto getAllAvailablePostOffices(@RequestParam String city,
                                                           @RequestParam(defaultValue = "0") String pageNumber,
                                                           @RequestParam(defaultValue = "25") String pageSize) {
        return deliveryService.getAllAvailablePostOffices(city,pageNumber,pageSize);
    }
}
