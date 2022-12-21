package com.epam.edumanagementsystem.parent.rest.service;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ParentService {

    Parent findById(Long id);

    Parent save(ParentDto parentDto);

    List<Parent> findAll();

    Parent findByUserId(Long userId);

    Parent remove(Long id);

    Parent update(ParentDto parentDto);

    void addImage(Parent parent, MultipartFile multipartFile);

    void removeImage(Long id);
}
