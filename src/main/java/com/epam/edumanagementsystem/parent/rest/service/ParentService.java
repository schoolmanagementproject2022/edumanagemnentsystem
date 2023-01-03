package com.epam.edumanagementsystem.parent.rest.service;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.dto.ParentEditDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ParentService {

    ParentDto findById(Long id);

    ParentEditDto findParentEditById(Long id);

    ParentDto save(ParentDto parentDto);

    List<ParentDto> findAll();

    ParentDto findByUserId(Long userId);

    Parent update(ParentEditDto parentDto);

    void addImage(ParentDto parentDto, MultipartFile multipartFile);

    void removeImage(Long parentId);
}
