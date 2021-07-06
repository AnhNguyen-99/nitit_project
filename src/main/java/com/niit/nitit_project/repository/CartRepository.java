package com.niit.nitit_project.repository;

import com.niit.nitit_project.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    //Lấy ra list trong trường hợp db lỗi
    @Query(value = "SELECT * FROM cart WHERE status = 0 AND id_user = ?1 ", nativeQuery = true)
    List<Cart> findByIdUser(Integer idUser);
}
