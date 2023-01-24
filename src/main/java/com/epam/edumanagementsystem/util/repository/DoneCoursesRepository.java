package com.epam.edumanagementsystem.util.repository;

import com.epam.edumanagementsystem.util.entity.DoneCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoneCoursesRepository extends JpaRepository<DoneCourses, Long> {

}
