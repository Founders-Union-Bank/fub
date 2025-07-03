package org.fub.repository;

import org.fub.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel,String> {

    Optional<UserModel> findByEmail(String email);

    List<UserModel> findAllByGroupId(Long groupId);
}
