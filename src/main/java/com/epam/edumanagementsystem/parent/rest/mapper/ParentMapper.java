package com.epam.edumanagementsystem.parent.rest.mapper;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParentMapper {

    @Lazy
    private static UserService userService;

    public ParentMapper(UserService userService) {
        ParentMapper.userService = userService;
    }

    public static Parent toParent(ParentDto parentDto) {
        Parent parent = new Parent();
        parent.setId(parentDto.getId());
        parent.setName(parentDto.getName());
        parent.setSurname(parentDto.getSurname());
        parent.setPassword(parentDto.getPassword());
        return parent;
    }

    public static Parent toParentWithoutSaveUser(ParentDto parentDto) {
        Parent parent = new Parent();
        User user = new User();
        parent.setId(parentDto.getId());
        parent.setName(parentDto.getName());
        parent.setSurname(parentDto.getSurname());
        user.setEmail(parentDto.getEmail());
        user.setRole(parentDto.getRole());
        parent.setPassword(parentDto.getPassword());
        parent.setUser(userService.findByEmail(user.getEmail()));
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
        return parentDto;
    }

    public static List<Parent> toParentList(List<ParentDto> parentDtoList, UserService userService) {
        List<Parent> parentList = new ArrayList<>();
        for (ParentDto parentDto : parentDtoList) {
            parentList.add(toParent(parentDto));
        }

        return parentList;
    }

    public static List<Parent> toParentListWithoutSaveUser(List<ParentDto> parentDtoList) {
        List<Parent> parentList = new ArrayList<>();
        for (ParentDto parentDto : parentDtoList) {
            parentList.add(toParentWithoutSaveUser(parentDto));
        }

        return parentList;
    }

    public static List<ParentDto> toParentDtoList(List<Parent> parentList) {
        return parentList.stream()
                .map(ParentMapper::toParentDto)
                .collect(Collectors.toList());
    }

}
