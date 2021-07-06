package com.niit.nitit_project.repository;

import com.niit.nitit_project.entity.OrderWatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderWatchRepository extends JpaRepository<OrderWatch, Integer> {
    @Query(value = "SELECT * FROM order_watch WHERE id_order = ?1 ", nativeQuery = true)
    List<OrderWatch> findByIdOrder(Integer idOrder);

    List<OrderWatch> findByIdWatch(Integer idWatch);
}
