package com.epam.edumanagementsystem.parent.rest.service;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.util.service.UserService;

import java.util.List;

public interface ParentService {

    Parent findById(Long id);

    void save(ParentDto parent, UserService userService);

    List<Parent> findAll();

}
