package com.epam.edumanagementsystem.parent.rest.service;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;

import java.util.List;
import java.util.Optional;

public interface ParentService {

    Parent findById(Long id);

    void save(ParentDto parent);

    List<Parent> parents();

    Optional<Parent> findByEmail(String email);

}
