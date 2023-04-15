package com.excellentbook.excellentbook.service;

import com.excellentbook.excellentbook.dto.delivery.PostOfficeDetailsDto;

public interface DeliveryService {

    PostOfficeDetailsDto getAllAvailablePostOffices(String city, String pageNumber, String pageSize);
}
