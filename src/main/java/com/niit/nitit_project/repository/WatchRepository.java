package com.niit.nitit_project.repository;

import com.niit.nitit_project.entity.Watch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WatchRepository extends JpaRepository<Watch, Integer> {
    @Query(value = "SELECT * FROM watch WHERE status = ?2 order by id DESC LIMIT ?1",nativeQuery = true)
    List<Watch> getListNew(Integer limit, int status);

    @Query(value =
                "SELECT w.*, (w.price -w.sale_price) as sale FROM watch w " +
                "where w.status = ?2 and w.sale_price != price order by sale DESC LIMIT ?1",
            nativeQuery = true)
    List<Watch> getListOnSale(Integer limit, int status);

    Page<Watch> findByIdBrand(Integer idBrand, Pageable pageable);

    @Query(value =
                "SELECT w.* " +
                " FROM watch as w  INNER JOIN brand as b" +
                " ON w.id_brand = b.id" +
                " WHERE UPPER(w.name) LIKE CONCAT('%', UPPER( ?1 ) , '%') " +
                "OR UPPER(b.name) LIKE CONCAT('%', UPPER( ?1 ) , '%')",
            countQuery =
                "SELECT COUNT(w.id) " +
                " FROM watch as w  INNER JOIN brand as b" +
                " ON w.id_brand = b.id" +
                " WHERE UPPER(w.name) LIKE CONCAT('%', UPPER( ?1 ) , '%') " +
                "OR UPPER(b.name) LIKE CONCAT('%', UPPER( ?1 ) , '%')",
            nativeQuery = true)
    Page<Watch> findByKeyWord(String keyword, Pageable pageable);

    @Query(value =
                "SELECT a.* " +
                " FROM watch as a  INNER JOIN wishlist_watch as b" +
                " ON a.id = b.id_watch " +
                "WHERE b.id_user = ?1",
            countQuery =
                "SELECT COUNT(a.id) " +
                " FROM watch as a  INNER JOIN wishlist_watch as b" +
                " ON a.id = b.id_watch " +
                "WHERE b.id_user = ?1",
            nativeQuery = true)
    Page<Watch> findWishlistByIdUser(Integer idUser, Pageable pageable);

    @Query(value =
                "SELECT a.* " +
                " FROM watch as a  INNER JOIN wishlist_watch as b" +
                " ON a.id = b.id_watch " +
                "WHERE b.id_user = ?1 AND a.id_brand = ?2",
            countQuery =
                "SELECT COUNT(a.id) " +
                " FROM watch as a  INNER JOIN wishlist_watch as b" +
                " ON a.id = b.id_watch " +
                "WHERE b.id_user = ?1 AND a.id_brand = ?2",
            nativeQuery = true)
    Page<Watch> findWishlistOfUserByIdBrand(Integer idUser, Integer idBrand, Pageable pageable);

    Optional<Watch> findByCode(String code);
}
