package com.niit.nitit_project.repository;

import com.niit.nitit_project.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    @Query(value = "SELECT * from image where id_watch = ? ORDER BY id ASC",nativeQuery = true)
    List<Image> getListByIdWatch(Integer idWatch);

    List<Image> findByIdWatch(Integer idWatch);
    void deleteByIdWatch(Integer idWatch);
}
