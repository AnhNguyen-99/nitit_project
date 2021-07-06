package com.niit.nitit_project.repository;

import com.niit.nitit_project.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query(value = "SELECT * FROM brand WHERE upper(name) like concat('%', upper(?1) ,'%') ORDER BY id DESC",
            countQuery = "SELECT COUNT(id) FROM brand WHERE upper(name) like concat('%', upper(?1) ,'%')",
            nativeQuery = true)
    Page<Brand> findByKeyWord(String name, Pageable pageable);

    Optional<Brand> findByName(String name);
}
