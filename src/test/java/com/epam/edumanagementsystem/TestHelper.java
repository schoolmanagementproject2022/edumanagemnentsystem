package com.epam.edumanagementsystem;

import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.util.entity.User;

import java.util.HashSet;
import java.util.Set;

public class TestHelper {
    public static Subject createSubject() {
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("SubjectName");
        subject.setTeacherSet(teacher());
        return subject;
    }

    public static Subject updateSubject1() {
        Subject subject = new Subject();
        subject.setId(2L);
        subject.setName("Name_update");
        subject.setTeacherSet(teacher());
        return subject;
    }

    public static Set<Teacher> teacher() {
        Set<Teacher> teacherSet = new HashSet<>();
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("teacher");
        teacher.setSurname("teacher");
        teacher.setPassword("teacher");
        teacher.setUser(new User(1L, "teacher", "TEACHER"));
        teacherSet.add(teacher);
        return teacherSet;
    }
}
