package com.epam.edumanagementsystem.parent.impl;

import com.epam.edumanagementsystem.exception.EntityNotFoundException;
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

@Service
@Transactional
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;
    private final UserService userService;
    private final ImageService imageService;

    public ParentServiceImpl(ParentRepository parentRepository, UserService userService,
                             ImageService imageService) {
        this.parentRepository = parentRepository;
        this.userService = userService;
        this.imageService = imageService;
    }

    @Override
    public Parent findById(Long id) {
        return parentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Parent save(ParentDto parentDto) {
        Parent parent = ParentMapper.toParent(parentDto);
        parent.setUser(userService.save(new User(parentDto.getEmail(), parentDto.getRole())));
        return parentRepository.save(parent);
    }

    @Override
    public List<Parent> findAll() {
        return parentRepository.findAll();
    }

    @Override
    public Parent findByUserId(Long userId) {
        return parentRepository.findByUserId(userId);
    }

    @Override
    public Parent update(ParentDto parentDto) {
        Parent parent = parentRepository.findById(parentDto.getId()).orElseThrow(EntityNotFoundException::new);
        parent.setName(parentDto.getName());
        parent.setSurname(parentDto.getSurname());
        parent.getUser().setEmail(parentDto.getEmail());
        return parentRepository.save(parent);
    }

    @Override
    public void addImage(Parent parent, MultipartFile multipartFile) {
        parent.setPicUrl(imageService.saveImage(multipartFile));
        parentRepository.save(parent);
    }

    @Override
    public void removeImage(Long id) {
        parentRepository.updateImageUrl(id);
    }

}