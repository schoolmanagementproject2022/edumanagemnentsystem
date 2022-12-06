package com.epam.edumanagementsystem.parent.rest.service;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ParentService {

    Optional<Parent> findById(Long id);

    Parent save(ParentDto parent);

    List<Parent> findAll();

    Parent findByUserId(Long id);

    void deleteById(Long id);

    void updateParent(ParentDto parentDto);

    void addProfilePicture(Parent parent, MultipartFile multipartFile);

    void deletePic(Long id);
}
