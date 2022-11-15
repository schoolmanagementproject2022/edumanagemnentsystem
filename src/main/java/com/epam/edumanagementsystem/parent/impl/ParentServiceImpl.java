package com.epam.edumanagementsystem.parent.impl;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.parent.rest.mapper.ParentMapper;
import com.epam.edumanagementsystem.parent.rest.repository.ParentRepository;
import com.epam.edumanagementsystem.parent.rest.service.ParentService;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.imageUtil.rest.service.ImageService;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ParentServiceImpl implements ParentService {

    private String upload = System.getProperty("user.dir") + "/src/main/resources/static/img";
    private final ParentRepository parentRepository;
    private final UserService userService;
    private final ImageService imageService;

    public ParentServiceImpl(ParentRepository parentRepository, UserService userService, ImageService imageService) {
        this.parentRepository = parentRepository;
        this.userService = userService;
        this.imageService = imageService;
    }


    @Override
    public Optional<Parent> findById(Long id) {
        if (id == null) {
            throw new NullPointerException("The given id must not be null!");
        }
        return parentRepository.findById(id);
    }

    @Override
    public Parent save(ParentDto parentDto) {
        if (parentDto == null) {
            throw new NullPointerException("The given parent must not be null!");
        }

        User user = new User();
        user.setEmail(parentDto.getEmail());
        user.setRole(parentDto.getRole());
        User savedUser = userService.save(user);
        Parent parent = ParentMapper.toParent(parentDto);
        parent.setUser(savedUser);
        return parentRepository.save(parent);
    }

    @Override
    public List<Parent> findAll() {
        return parentRepository.findAll();
    }

    @Override
    public Parent findByUserId(Long id) {
        if (id == null) {
            throw new NullPointerException("The given id must not be null!");
        }
        return parentRepository.findByUserId(id);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new NullPointerException("The given id must not be null!");
        }
        parentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateParentNameAndSurnameById(String name, String surname, Long id) {
        parentRepository.updateParentNameAndSurnameById(name, surname, id);
    }

    @Override
    public void addProfilePicture(Parent parent, MultipartFile multipartFile) {
        parent.setPicUrl(imageService.saveImage(multipartFile));
        parentRepository.save(parent);
    }

    @Transactional
    @Override
    public void deletePictureById(Long id) {
        parentRepository.updateParentPicUrl(id);
    }

}