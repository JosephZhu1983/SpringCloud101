package me.josephzhu.springcloud101.projectservice.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.josephzhu.springcloud101.projectservice.api.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Sink.class)
@Slf4j
public class ProjectServiceListener {
    @Autowired
    RemoteUserService remoteUserService;
    @Autowired
    RemoteProjectService remoteProjectService;
    @Autowired
    RemoteInvestService remoteInvestService;

    static ObjectMapper objectMapper = new ObjectMapper();

    @StreamListener(Sink.INPUT)
    public void handleProject(Project project) {
        try {
            log.info("收到消息: " + project);
            if (project.getStatus() == 2) {
                remoteInvestService.getOrders(project.getId())
                        .forEach(invest -> {
                            try {
                                remoteUserService.lendpayMoney(invest.getInvestorId(), invest.getBorrowerId(), invest.getAmount());
                            } catch (Exception ex) {
                                try {
                                    log.error("处理放款的时候遇到异常：" + objectMapper.writeValueAsString(invest), ex);
                                } catch (JsonProcessingException e) {

                                }
                            }
                        });
                remoteProjectService.lendpay(project.getId());
            }
        } catch (Exception ex) {
            log.error("处理消息出现异常",ex);
        }
    }
}
