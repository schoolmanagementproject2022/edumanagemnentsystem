package com.epam.edumanagementsystem.parent.rest.service;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;

import java.util.List;
import java.util.Optional;

public interface ParentService {

    Optional<Parent> findById(Long id);

    Optional<Parent> save(ParentDto parent);

    List<Parent> findAll();

    Parent findByUserId(Long id);

    void deleteById(Long id);

}
