package com.epam.edumanagementsystem.parent.rest.service.impl;

import com.epam.edumanagementsystem.exception.EntityNotFoundException;
import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.dto.ParentEditDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.parent.rest.mapper.ParentMapper;
import com.epam.edumanagementsystem.parent.rest.repository.ParentRepository;
import com.epam.edumanagementsystem.parent.rest.service.ParentService;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.imageUtil.rest.service.ImageService;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder encoder;

    public ParentServiceImpl(ParentRepository parentRepository, UserService userService,
                             ImageService imageService, PasswordEncoder encoder) {
        this.parentRepository = parentRepository;
        this.userService = userService;
        this.imageService = imageService;
        this.encoder = encoder;
    }

    @Override
    public ParentDto findById(Long id) {
        if (parentRepository.findById(id).isPresent()) {
            return ParentMapper.toParentDto(parentRepository.findById(id).get());
        }
        throw new EntityNotFoundException("Parent is not found by id: " + id);
    }

    @Override
    public ParentEditDto findParentEditById(Long id) {
        if (parentRepository.findById(id).isPresent()) {
            return ParentMapper.mapToParentEditDto(parentRepository.findById(id).get());
        }
        throw new EntityNotFoundException("Parent is not found by id: " + id);
    }

    @Override
    public ParentDto save(ParentDto parentDto) {
        Parent parent = ParentMapper.toParent(parentDto);
        parent.setUser(userService.save(new User(parentDto.getEmail(), parentDto.getRole())));
        parent.setPassword(encoder.encode(parentDto.getPassword()));
        return ParentMapper.toParentDto(parentRepository.save(parent));
    }

    @Override
    public List<ParentDto> findAll() {
        return ParentMapper.toParentDtoList(parentRepository.findAll());
    }

    @Override
    public ParentDto findByUserId(Long userId) {
        if (parentRepository.findByUserId(userId).isPresent()) {
            return ParentMapper.toParentDto(parentRepository.findByUserId(userId).get());
        }
        throw new EntityNotFoundException("Parent is not found by user id: " + userId);
    }

    @Override
    public Parent update(ParentEditDto parentDto) {
        Parent parent = parentRepository.findById(parentDto.getId()).get();
        parent.setName(parentDto.getName());
        parent.setSurname(parentDto.getSurname());
        parent.getUser().setEmail(parentDto.getEmail());
        return parentRepository.save(parent);
    }

    @Override
    public void addImage(ParentDto parentDto, MultipartFile multipartFile) {
        parentDto.setPicUrl(imageService.saveImage(multipartFile));
        parentRepository.save(ParentMapper.toParent(parentDto));
    }

    @Override
    public void removeImage(Long id) {
        parentRepository.updateImageUrl(id);
    }

}