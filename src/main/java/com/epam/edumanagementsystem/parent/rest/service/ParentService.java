package com.epam.edumanagementsystem.parent.rest.service;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.util.service.UserService;

import java.util.List;
import java.util.Optional;

public interface ParentService {

    Parent findById(Long id);

    void save(ParentDto parent, UserService userService);

    List<Parent> findAll();

    Optional<Parent> findByEmail(String email);

}
