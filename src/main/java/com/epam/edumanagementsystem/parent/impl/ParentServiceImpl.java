package com.epam.edumanagementsystem.parent.impl;

import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.parent.rest.repository.ParentRepository;
import com.epam.edumanagementsystem.parent.rest.service.ParentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;

    public ParentServiceImpl(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    @Override
    public Parent findById(Long id) {
        Optional<Parent> byId = parentRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }

    @Override
    public void save(Parent parent) {
        parentRepository.save(parent);
    }

    @Override
    public List<Parent> parents() {
        return parentRepository.findAll();
    }

    @Override
    public Optional<Parent> findByEmail(String email) {
        return parentRepository.findByEmail(email);
    }
}
