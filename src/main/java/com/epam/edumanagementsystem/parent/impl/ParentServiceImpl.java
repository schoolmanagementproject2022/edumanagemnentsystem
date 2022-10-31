package com.epam.edumanagementsystem.parent.impl;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.parent.rest.mapper.ParentMapper;
import com.epam.edumanagementsystem.parent.rest.repository.ParentRepository;
import com.epam.edumanagementsystem.parent.rest.service.ParentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;

    public ParentServiceImpl(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    @Override
    public Optional<Parent> findById(Long id) {
        if (id == null) {
            throw new NullPointerException("The given id must not be null!");
        }
        return parentRepository.findById(id);
    }

    @Override
    public Optional<Parent> save(ParentDto parentDto) {
        if (parentDto == null) {
            throw new NullPointerException("The given parent must not be null!");
        }
        return Optional.of(parentRepository.save(ParentMapper.toParent(parentDto)));
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
}
