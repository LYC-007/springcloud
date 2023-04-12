package com.xufei.jpa.repository;


import com.xufei.jpa.model.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public interface MenuRepository extends JpaRepository<Menu,Integer> {

    @EntityGraph(value = "menu.findAll", type = EntityGraph.EntityGraphType.FETCH)
    List<Menu> findAllByParentIdIsNull();
}
