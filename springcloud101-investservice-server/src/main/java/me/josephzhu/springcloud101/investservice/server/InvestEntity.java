package me.josephzhu.springcloud101.investservice.server;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "invest")
@EntityListeners(AuditingEntityListener.class)
public class InvestEntity {
    @Id
    @GeneratedValue
    private Long id;
    private long investorId;
    private long borrowerId;
    private long projectId;
    private String investorName;
    private String borrowerName;
    private String projectName;
    private BigDecimal amount;
    private int status;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
}
