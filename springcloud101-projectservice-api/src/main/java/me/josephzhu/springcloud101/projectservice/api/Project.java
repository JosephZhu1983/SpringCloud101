package me.josephzhu.springcloud101.projectservice.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private Long id;
    private BigDecimal totalAmount;
    private BigDecimal remainAmount;
    private String name;
    private String reason;
    private long borrowerId;
    private String borrowerName;
    private int status;
    private Date createdAt;
}
