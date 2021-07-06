package com.niit.nitit_project.repository;

import com.niit.nitit_project.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = "SELECT * FROM orders WHERE id_user = ?1 ORDER BY id DESC", nativeQuery = true)
    List<Order> findByIdUser(Integer idUser);
}
