package me.josephzhu.springcloud101.projectservice.listener;

import me.josephzhu.springcloud101.investservice.api.InvestService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "investservice")
public interface RemoteInvestService extends InvestService {
}
