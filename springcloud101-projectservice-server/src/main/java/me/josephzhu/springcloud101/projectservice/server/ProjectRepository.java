package me.josephzhu.springcloud101.projectservice.server;

import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {
}
