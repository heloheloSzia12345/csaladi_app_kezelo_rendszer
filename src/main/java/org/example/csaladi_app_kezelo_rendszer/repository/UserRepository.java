package org.example.csaladi_app_kezelo_rendszer.repository;

import org.example.csaladi_app_kezelo_rendszer.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {

}
