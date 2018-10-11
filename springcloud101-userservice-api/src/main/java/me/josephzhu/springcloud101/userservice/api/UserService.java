package me.josephzhu.springcloud101.userservice.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

public interface UserService {
    @GetMapping("getUser")
    User getUser(@RequestParam("id") long id) throws Exception;
    @PostMapping("consumeMoney")
    BigDecimal consumeMoney(@RequestParam("investorId") long investorId,
                            @RequestParam("amount") BigDecimal amount) throws Exception;
    @PostMapping("lendpayMoney")
    BigDecimal lendpayMoney(@RequestParam("investorId") long investorId,
                            @RequestParam("borrowerId") long borrowerId,
                            @RequestParam("amount") BigDecimal amount) throws Exception;
}
