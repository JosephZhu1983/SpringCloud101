package me.josephzhu.springcloud101.projectservice.listener;

import me.josephzhu.springcloud101.projectservice.api.Project;
import me.josephzhu.springcloud101.projectservice.api.ProjectService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@FeignClient(value = "projectservice", fallback = RemoteProjectService.Fallback.class)
public interface RemoteProjectService extends ProjectService {
    @Component
    class Fallback implements RemoteProjectService {

        @Override
        public Project getProject(long id) throws Exception {
            return null;
        }

        @Override
        public BigDecimal gotInvested(long id, BigDecimal amount) throws Exception {
            return null;
        }

        @Override
        public BigDecimal lendpay(long id) throws Exception {
            return null;
        }
    }
}
