package me.josephzhu.springcloud101.projectservice.server;

import lombok.extern.slf4j.Slf4j;
import me.josephzhu.springcloud101.projectservice.api.Project;
import me.josephzhu.springcloud101.projectservice.api.ProjectService;
import me.josephzhu.springcloud101.userservice.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Slf4j
@EnableBinding(Source.class)
public class ProjectServiceController implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    RemoteUserService remoteUserService;

    @Override
    public Project getProject(long id) throws Exception {
        ProjectEntity projectEntity = projectRepository.findById(id).orElse(null);
        if (projectEntity == null) return null;
        User borrower = remoteUserService.getUser(projectEntity.getBorrowerId());
        if (borrower == null) return null;

        return Project.builder()
                .id(projectEntity.getId())
                .borrowerId(borrower.getId())
                .borrowerName(borrower.getName())
                .name(projectEntity.getName())
                .reason(projectEntity.getReason())
                .status(projectEntity.getStatus())
                .totalAmount(projectEntity.getTotalAmount())
                .remainAmount(projectEntity.getRemainAmount())
                .createdAt(projectEntity.getCreatedAt())
                .build();
    }

    @Override
    public BigDecimal gotInvested(long id, BigDecimal amount) throws Exception {
        ProjectEntity projectEntity = projectRepository.findById(id).orElse(null);
        if (projectEntity != null && projectEntity.getRemainAmount().compareTo(amount)>=0) {
            projectEntity.setRemainAmount(projectEntity.getRemainAmount().subtract(amount));
            projectRepository.save(projectEntity);

            if (projectEntity.getRemainAmount().compareTo(new BigDecimal("0"))==0) {
                User borrower = remoteUserService.getUser(projectEntity.getBorrowerId());
                if (borrower != null) {
                    projectEntity.setStatus(2);
                    projectRepository.save(projectEntity);
                    projectStatusChanged(Project.builder()
                            .id(projectEntity.getId())
                            .borrowerId(borrower.getId())
                            .borrowerName(borrower.getName())
                            .name(projectEntity.getName())
                            .reason(projectEntity.getReason())
                            .status(projectEntity.getStatus())
                            .totalAmount(projectEntity.getTotalAmount())
                            .remainAmount(projectEntity.getRemainAmount())
                            .createdAt(projectEntity.getCreatedAt())
                            .build());
                }
                return amount;
            }
            return amount;
        }
        return null;
    }

    @Override
    public BigDecimal lendpay(long id) throws Exception {
        Thread.sleep(5000);
        ProjectEntity project = projectRepository.findById(id).orElse(null);
        if (project != null) {
            project.setStatus(3);
            projectRepository.save(project);
            return project.getTotalAmount();
        }
        return null;
    }

    @Autowired
    Source source;

    private void projectStatusChanged(Project project){
        if (project.getStatus() == 2)
        try {
            source.output().send(MessageBuilder.withPayload(project).build());
        } catch (Exception ex) {
            log.error("发送MQ失败", ex);
        }
    }
}
