package org.fub.repository;

import org.fub.model.CrewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<CrewModel,Long> {
}
