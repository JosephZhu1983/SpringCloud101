package me.josephzhu.springcloud101.projectservice.listener;

import me.josephzhu.springcloud101.investservice.api.InvestService;
import me.josephzhu.springcloud101.projectservice.api.Project;
import me.josephzhu.springcloud101.projectservice.api.ProjectService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@FeignClient(value = "investservice")
public interface RemoteInvestService extends InvestService {
}
