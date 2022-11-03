package com.epam.edumanagementsystem;

import com.epam.edumanagementsystem.admin.model.dto.AdminDto;
import com.epam.edumanagementsystem.admin.model.dto.SubjectDto;
import com.epam.edumanagementsystem.admin.model.entity.Admin;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.util.entity.User;

import java.util.HashSet;
import java.util.Set;

public class TestHelper {
    Subject subject = new Subject();
    SubjectDto subjectDto = new SubjectDto();
    Admin admin = new Admin();
    AdminDto adminDto = new AdminDto();
    User user = new User();

    protected SubjectDto returnedSubject() {
        subjectDto.setId(1L);
        subjectDto.setName("SubjectName");
        subjectDto.setTeacherSet(teacher());
        return subjectDto;
    }

    protected Subject createSubject() {
        subject.setId(1L);
        subject.setName("SubjectName");
        subject.setTeacherSet(teacher());
        return subject;
    }

    protected Subject updateSubject() {
        subject.setId(2L);
        subject.setName("Name_update");
        subject.setTeacherSet(teacher());
        return subject;
    }

    protected Set<Teacher> teacher() {
        Set<Teacher> teacherSet = new HashSet<>();
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("teacher");
        teacher.setSurname("teacher");
        teacher.setPassword("teacher");
        teacher.setUser(user());
        teacherSet.add(teacher);
        return teacherSet;
    }

    protected Admin input() {
        admin.setId(1L);
        admin.setUsername("admin");
        admin.setSurname("adminyan");
        admin.setPassword("admin");
        admin.setUser(user());
        return admin;

    }

    protected Admin returned() {
        admin.setId(1L);
        admin.setUsername("admin");
        admin.setSurname("adminyan");
        admin.setPassword("admin");
        admin.setUser(user());
        return admin;

    }

    protected AdminDto createAdmin() {
        adminDto.setId(1L);
        adminDto.setUsername("admin");
        adminDto.setSurname("adminyan");
        adminDto.setPassword("admin");
        adminDto.setEmail("email@email.ru");
        adminDto.setRole("ADMIN");
        return adminDto;

    }

    protected User user() {
        user.setId(1L);
        user.setEmail("email@email.ru");
        user.setRole("ADMIN");
        return user;
    }
}
