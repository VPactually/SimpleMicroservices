package com.vpactually.services;

import com.vpactually.entities.Order;

public interface DeliveryDomainService {

     void process (Order order) ;

     void rollback (Order backupOrder) ;
}
