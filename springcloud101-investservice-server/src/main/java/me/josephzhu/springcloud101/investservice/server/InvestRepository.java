package me.josephzhu.springcloud101.investservice.server;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvestRepository extends CrudRepository<InvestEntity, Long> {
    List<InvestEntity> findByProjectIdAndStatus(long projectId, int status);
}
