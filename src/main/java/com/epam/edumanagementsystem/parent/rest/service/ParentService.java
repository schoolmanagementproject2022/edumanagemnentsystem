package com.epam.edumanagementsystem.parent.rest.service;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ParentService {

    Optional<Parent> findById(Long id);

    Parent save(ParentDto parentDto);

    List<Parent> findAll();

    Optional<Parent> findByUserId(Long userId);

    Parent update(ParentDto parentDto);

    void addImage(Parent parent, MultipartFile multipartFile);

    void removeImage(Long parentId);
}
