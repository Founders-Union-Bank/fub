package org.fub.repository;

import org.fub.model.CrewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<CrewModel,Long> {
    @Query("SELECT c FROM crew c WHERE UPPER(c.crewName) LIKE UPPER(CONCAT('%', :name, '%'))")
    List<CrewModel> findByCrewNameLikeIgnoreCase(@Param("name") String name);
}
