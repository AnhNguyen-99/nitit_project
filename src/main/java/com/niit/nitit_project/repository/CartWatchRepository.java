package com.niit.nitit_project.repository;

import com.niit.nitit_project.entity.CartWatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartWatchRepository extends JpaRepository<CartWatch, Integer> {
    @Query(value = "SELECT * FROM cart_watch WHERE id_cart = ?1 AND id_watch = ?2 ", nativeQuery = true)
    Optional<CartWatch> findByIdCartAndIdWatch(Integer idCart, Integer idWatch);

    @Query(value = "SELECT * FROM cart_watch WHERE id_cart= ?1", nativeQuery = true)
    List<CartWatch> findByIdCart(Integer idCart);

    List<CartWatch> findByIdWatch(Integer idWatch);
}
