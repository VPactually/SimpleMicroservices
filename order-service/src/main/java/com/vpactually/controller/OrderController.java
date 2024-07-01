package com.vpactually.controller;

import com.vpactually.dto.CreateOrderDTO;
import com.vpactually.dto.OrderDTO;
import com.vpactually.services.OrderApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderApplicationService businessService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO get(@PathVariable String id) {
        log.info("Getting order in Order Service{}", id);
        return businessService.find(Integer.parseInt(id));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO post(@RequestBody CreateOrderDTO createOrderDTO) {
        return businessService.create(createOrderDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        businessService.destroy(Integer.parseInt(id));
    }
}
