package me.josephzhu.springcloud101.investservice.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Invest {
    private Long id;
    private long investorId;
    private long borrowerId;
    private long projectId;
    private int status;
    private BigDecimal amount;
    private Date createdAt;
    private Date updatedAt;
}
