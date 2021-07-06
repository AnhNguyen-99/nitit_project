package com.niit.nitit_project.repository;

import com.niit.nitit_project.entity.WishlistWatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WishlistWatchRepository extends JpaRepository<WishlistWatch, Integer> {
    @Query(value = "SELECT * FROM wishlist_watch WHERE id_user = ?1 AND id_watch = ?2", nativeQuery = true)
    Optional<WishlistWatch> findByIdUserAndIdWatch(Integer idUser, Integer idWatch);
}
