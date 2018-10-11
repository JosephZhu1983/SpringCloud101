package me.josephzhu.springcloud101.userservice.api;

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
public class User {
    private Long id;
    private String name;
    private BigDecimal availableBalance;
    private BigDecimal frozenBalance;
    private Date createdAt;
}
