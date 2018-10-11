package me.josephzhu.springcloud101.projectservice.server;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "project")
@EntityListeners(AuditingEntityListener.class)
public class ProjectEntity {
    @Id
    @GeneratedValue
    private Long id;
    private BigDecimal totalAmount;
    private BigDecimal remainAmount;
    private String name;
    private String reason;
    private long borrowerId;
    private int status;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
}
