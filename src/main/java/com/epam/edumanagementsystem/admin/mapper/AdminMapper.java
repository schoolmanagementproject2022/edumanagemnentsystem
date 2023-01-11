package com.epam.edumanagementsystem.admin.mapper;

import com.epam.edumanagementsystem.admin.model.dto.AdminDto;
import com.epam.edumanagementsystem.admin.model.entity.Admin;

import java.util.List;
import java.util.stream.Collectors;

public class AdminMapper {

    private AdminMapper() {
        throw new IllegalStateException();
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

    public static List<AdminDto> adminDTOConvert(List<Admin> adminList) {
        return adminList.stream()
                .map(AdminMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Admin toAdmin(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setId(adminDto.getId());
        admin.setUsername(adminDto.getUsername());
        admin.setSurname(adminDto.getSurname());
        admin.setPassword(adminDto.getPassword());
        return admin;
    }

}
