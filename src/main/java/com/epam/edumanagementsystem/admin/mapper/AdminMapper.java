package com.epam.edumanagementsystem.admin.mapper;

import com.epam.edumanagementsystem.admin.model.dto.AdminDto;
import com.epam.edumanagementsystem.admin.model.entity.Admin;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class AdminMapper {
    @Lazy
    private static UserService userService;

    public AdminMapper(UserService userService) {
        this.userService = userService;
    }
    public static AdminDto toDto(Admin admin) {
        AdminDto adminDtoDto = new AdminDto();
        adminDtoDto.setId(admin.getId());
        adminDtoDto.setUsername(admin.getUsername());
        adminDtoDto.setSurname(admin.getSurname());
        adminDtoDto.setEmail(admin.getUser().getEmail());
        adminDtoDto.setRole(admin.getUser().getRole());
        adminDtoDto.setPassword(admin.getPassword());
        return adminDtoDto;
    }
    public static List<AdminDto> adminDTOConvert(List<Admin> admins) {
        List<AdminDto> adminDto = new ArrayList<>();
        for (Admin admin : admins) {
            adminDto.add(toDto(admin));
        }
        return adminDto;
    }

    public static Admin toAdmin(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setId(adminDto.getId());
        admin.setUsername(adminDto.getUsername());
        admin.setSurname(adminDto.getSurname());
        admin.setPassword(adminDto.getPassword());

        return admin;
    }

    public static Admin adminDTOConvertWithoutSavingUser(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setUsername(adminDto.getUsername());
        admin.setSurname(adminDto.getSurname());
        admin.setPassword(adminDto.getPassword());

        return admin;
    }
}
