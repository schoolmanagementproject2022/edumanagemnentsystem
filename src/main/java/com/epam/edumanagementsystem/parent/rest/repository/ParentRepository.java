package com.epam.edumanagementsystem.parent.rest.repository;

import com.epam.edumanagementsystem.parent.model.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

    Optional<Parent> findByUserId(Long userId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE parent SET pic_url = NULL WHERE id =(?1);")
    void updateImageUrl(Long parentId);

}
