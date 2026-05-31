package org.example.csaladi_app_kezelo_rendszer.repository;

import org.example.csaladi_app_kezelo_rendszer.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, String> {

}
