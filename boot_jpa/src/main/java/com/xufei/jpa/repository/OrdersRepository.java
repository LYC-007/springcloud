package com.xufei.jpa.repository;



import com.xufei.jpa.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders,String> {
}
