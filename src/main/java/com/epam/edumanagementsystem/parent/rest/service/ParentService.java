package com.epam.edumanagementsystem.parent.rest.service;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.parent.rest.mapper.ParentMapper;
import com.epam.edumanagementsystem.parent.rest.repository.ParentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParentService {

    private final ParentRepository parentRepository;

    public ParentService(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    public Parent findById(Long id) {
        Optional<Parent> byId = parentRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }

    public void save(ParentDto parent) {
        Parent savedParent = ParentMapper.toParent(parent);
        parentRepository.save(savedParent);
    }

    public List<Parent> parents() {
        return parentRepository.findAll();
    }

    public Optional<Parent> findByEmail(String email) {
        return parentRepository.findByEmail(email);
    }

}
