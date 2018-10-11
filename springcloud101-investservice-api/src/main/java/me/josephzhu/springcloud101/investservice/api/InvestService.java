package me.josephzhu.springcloud101.investservice.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

public interface InvestService {
    @PostMapping("createInvest")
    Invest createOrder(@RequestParam("userId") long userId,
                     @RequestParam("projectId") long projectId,
                     @RequestParam("amount") BigDecimal amount) throws Exception;
    @GetMapping("getOrders")
    List<Invest> getOrders(@RequestParam("projectId") long projectId) throws Exception;
}
