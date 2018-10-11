package me.josephzhu.springcloud101.investservice.server;

import lombok.extern.slf4j.Slf4j;
import me.josephzhu.springcloud101.investservice.api.Invest;
import me.josephzhu.springcloud101.investservice.api.InvestService;
import me.josephzhu.springcloud101.projectservice.api.Project;
import me.josephzhu.springcloud101.userservice.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class InvestServiceController implements InvestService {
    @Autowired
    InvestRepository investRepository;
    @Autowired
    RemoteUserService remoteUserService;
    @Autowired
    RemoteProjectService remoteProjectService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Invest createOrder(long userId, long projectId, BigDecimal amount) throws Exception {
        User investor = remoteUserService.getUser(userId);
        if (investor == null) throw new Exception("无效用户ID");
        if (amount.compareTo(investor.getAvailableBalance()) > 0) throw new Exception("用户余额不足");

        Project project = remoteProjectService.getProject(projectId);
        if (project == null) throw new Exception("无效项目ID");
        if (amount.compareTo(project.getRemainAmount()) > 0) throw new Exception("项目余额不足");

        InvestEntity investEntity = new InvestEntity();
        investEntity.setInvestorId(investor.getId());
        investEntity.setInvestorName(investor.getName());
        investEntity.setAmount(amount);
        investEntity.setBorrowerId(project.getBorrowerId());
        investEntity.setBorrowerName(project.getBorrowerName());
        investEntity.setProjectId(project.getId());
        investEntity.setProjectName(project.getName());
        investEntity.setStatus(1);
        investRepository.save(investEntity);

        if (remoteUserService.consumeMoney(userId, amount) == null) throw new Exception("用户消费失败");
        if (remoteProjectService.gotInvested(projectId, amount) == null) throw new Exception("项目投资失败");

        return Invest.builder()
                .id(investEntity.getId())
                .amount(investEntity.getAmount())
                .borrowerId(investEntity.getBorrowerId())
                .investorId(investEntity.getInvestorId())
                .projectId(investEntity.getProjectId())
                .status(investEntity.getStatus())
                .createdAt(investEntity.getCreatedAt())
                .updatedAt(investEntity.getUpdatedAt())
                .build();
    }

    @Override
    public List<Invest> getOrders(long projectId) throws Exception {
        return investRepository.findByProjectIdAndStatus(projectId,1).stream()
                .map(investEntity -> Invest.builder()
                        .id(investEntity.getId())
                        .amount(investEntity.getAmount())
                        .borrowerId(investEntity.getBorrowerId())
                        .investorId(investEntity.getInvestorId())
                        .projectId(investEntity.getProjectId())
                        .status(investEntity.getStatus())
                        .createdAt(investEntity.getCreatedAt())
                        .updatedAt(investEntity.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
