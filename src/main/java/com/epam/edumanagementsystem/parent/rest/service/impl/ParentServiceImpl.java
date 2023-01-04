package com.epam.edumanagementsystem.parent.rest.service.impl;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.dto.ParentEditDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.parent.rest.mapper.ParentMapper;
import com.epam.edumanagementsystem.parent.rest.repository.ParentRepository;
import com.epam.edumanagementsystem.parent.rest.service.ParentService;
import com.epam.edumanagementsystem.util.UserDataValidation;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.imageUtil.rest.service.ImageService;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
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
        return ParentMapper.mapToParentDto(parentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parent not found by id: " + id)));
    }

    @Override
    public ParentDto findByUserId(Long userId) {
        return ParentMapper.mapToParentDto(parentRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Parent not found by user id: " + userId)));
    }

    @Override
    public ParentEditDto findParentEditById(Long id) {
        return ParentMapper.mapToParentEditDto(parentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parent not found by id: " + id)));
    }

    @Override
    public List<ParentDto> findAll() {
        return ParentMapper.mapToParentDtoList(parentRepository.findAll());
    }

    @Override
    public ParentDto save(ParentDto parentDto) {
        Parent parent = ParentMapper.mapToParent(parentDto);
        parent.setUser(userService.save(new User(parentDto.getEmail(), parentDto.getRole())));
        parent.setPassword(encoder.encode(parentDto.getPassword()));
        return ParentMapper.mapToParentDto(parentRepository.save(parent));
    }


    @Override
    public ParentDto update(ParentEditDto parentDto) {
        Parent parent = parentRepository.findById(parentDto.getId()).get();
        parent.setName(parentDto.getName());
        parent.setSurname(parentDto.getSurname());
        parent.getUser().setEmail(parentDto.getEmail());
        return ParentMapper.mapToParentDto(parentRepository.save(parent));
    }

    @Override
    public void addImage(ParentDto parentDto, MultipartFile multipartFile) {
        parentDto.setPicUrl(imageService.saveImage(multipartFile));
        parentRepository.save(ParentMapper.mapToParent(parentDto));
    }

    @Override
    public void removeImage(Long id) {
        parentRepository.updateImageUrl(id);
    }

    //Should have I left this in controller as a private method?
    public void checkEmailForCreate(ParentDto parentDto, BindingResult bindingResult, Model model) {
        if (UserDataValidation.existsEmail(parentDto.getEmail())) {
            bindingResult.addError(new ObjectError("parentDto", "Duplicate email"));
            model.addAttribute("duplicated", "A user with the specified email already exists");
        }
    }

    public void checkEmailForEdit(ParentEditDto parentDto, BindingResult bindingResult,
                                  Long id, Model model) {
        if (!parentDto.getEmail().equalsIgnoreCase(findById(id).getEmail()) &&
                UserDataValidation.existsEmail(parentDto.getEmail())) {
            bindingResult.addError(new ObjectError("parentDto", "Duplicate email"));
            model.addAttribute("duplicated", "A user with the specified email already exists");
        }
    }
}