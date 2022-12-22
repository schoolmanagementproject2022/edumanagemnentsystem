package com.epam.edumanagementsystem.parent.rest.mapper;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;

import java.util.List;
import java.util.stream.Collectors;

public class ParentMapper {

    private ParentMapper() {
    }

    public static Parent toParent(ParentDto parentDto) {
        Parent parent = new Parent();
        parent.setId(parentDto.getId());
        parent.setName(parentDto.getName());
        parent.setSurname(parentDto.getSurname());
        parent.setPicUrl(parentDto.getPicUrl());
        parent.setPassword(parentDto.getPassword());
        return parent;
    }

    public static ParentDto toParentDto(Parent parent) {
        ParentDto parentDto = new ParentDto();
        parentDto.setId(parent.getId());
        parentDto.setName(parent.getName());
        parentDto.setSurname(parent.getSurname());
        parentDto.setEmail(parent.getUser().getEmail());
        parentDto.setRole(parent.getUser().getRole());
        parentDto.setPassword(parent.getPassword());
        parentDto.setPicUrl(parent.getPicUrl());
        return parentDto;
    }


    public static List<Parent> toParentList(List<ParentDto> parentDtoList) {
        return parentDtoList.stream()
                .map(ParentMapper::toParent)
                .collect(Collectors.toList());
    }

    public static List<ParentDto> toParentDtoList(List<Parent> parentList) {
        return parentList.stream()
                .map(ParentMapper::toParentDto)
                .collect(Collectors.toList());
    }
}