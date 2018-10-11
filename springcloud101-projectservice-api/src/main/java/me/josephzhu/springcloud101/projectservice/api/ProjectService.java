package me.josephzhu.springcloud101.projectservice.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

public interface ProjectService {
    @GetMapping("getProject")
    Project getProject(@RequestParam("id") long id) throws Exception;
    @PostMapping("gotInvested")
    BigDecimal gotInvested(@RequestParam("id") long id,
                            @RequestParam("amount") BigDecimal amount) throws Exception;
    @PostMapping("lendpay")
    BigDecimal lendpay(@RequestParam("id") long id) throws Exception;
}
