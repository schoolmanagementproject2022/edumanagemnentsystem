package com.epam.edumanagementsystem.security.impl;

import com.epam.edumanagementsystem.admin.model.entity.Admin;
import com.epam.edumanagementsystem.admin.model.entity.SuperAdmin;
import com.epam.edumanagementsystem.admin.rest.repository.AdminRepository;
import com.epam.edumanagementsystem.admin.rest.repository.SuperAdminRepository;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.parent.rest.repository.ParentRepository;
import com.epam.edumanagementsystem.security.model.CurrentUser;
import com.epam.edumanagementsystem.security.model.SecurityUser;
import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.student.rest.repository.StudentRepository;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.teacher.rest.repository.TeacherRepository;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.exceptions.UserNotFoundException;
import com.epam.edumanagementsystem.util.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SuperAdminRepository superAdminRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public SecurityUser loadUserByUsername(String username) {

        if (superAdminRepository.findByEmail(username).isPresent()) {
            SuperAdmin superAdmin = superAdminRepository.findByEmail(username).get();
            CurrentUser user = new CurrentUser();
            user.setEmail(superAdmin.getEmail());
            user.setPassword(superAdmin.getPassword());
            user.setRole("SUPER_ADMIN");
            return new SecurityUser(user);
        }
        if (userRepository.findByEmail(username).isPresent()) {
            User foundUser = userRepository.findByEmail(username).get();
            String foundUserRole = foundUser.getRole();
            Long foundUserId = foundUser.getId();


            if (foundUserRole.equalsIgnoreCase("ADMIN")) {
                Admin admin = adminRepository.findByUserId(foundUserId);

                CurrentUser user = new CurrentUser();
                user.setName(admin.getUsername());
                user.setSurname(admin.getSurname());
                user.setEmail(admin.getUser().getEmail());
                user.setPassword(admin.getPassword());
                user.setRole(admin.getUser().getRole());
                return new SecurityUser(user);

            } else if (foundUserRole.equalsIgnoreCase("TEACHER")) {
                Teacher teacher = teacherRepository.findByUserId(foundUserId);

                CurrentUser user = new CurrentUser();
                user.setName(teacher.getName());
                user.setSurname(teacher.getSurname());
                user.setEmail(teacher.getUser().getEmail());
                user.setPassword(teacher.getPassword());
                user.setRole(teacher.getUser().getRole());
                return new SecurityUser(user);

            } else if (foundUserRole.equalsIgnoreCase("PARENT")) {
                Parent parent = parentRepository.findByUserId(foundUserId).get();

                CurrentUser user = new CurrentUser();
                user.setName(parent.getName());
                user.setSurname(parent.getSurname());
                user.setEmail(parent.getUser().getEmail());
                user.setPassword(parent.getPassword());
                user.setRole(parent.getUser().getRole());
                return new SecurityUser(user);

            } else if (foundUserRole.equalsIgnoreCase("STUDENT")) {
                Student student = studentRepository.findByUserId(foundUserId);

                CurrentUser user = new CurrentUser();
                user.setName(student.getName());
                user.setSurname(student.getSurname());
                user.setEmail(student.getUser().getEmail());
                user.setPassword(student.getPassword());
                user.setRole(student.getUser().getRole());
                return new SecurityUser(user);
            }
        }
        throw new UsernameNotFoundException("User '" + username + "' not found");
    }
}

