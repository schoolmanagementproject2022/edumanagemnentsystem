package com.epam.edumanagementsystem.parent.impl;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.parent.model.dto.ParentEditDto;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.parent.mapper.ParentMapper;
import com.epam.edumanagementsystem.parent.rest.repository.ParentRepository;
import com.epam.edumanagementsystem.parent.rest.service.impl.ParentServiceImpl;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParentServiceImplTest {

    @Mock
    private ParentRepository parentRepository;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ParentServiceImpl parentService;

    private User user;
    private Parent parent;
    private Parent updatedParent;

    @BeforeEach
    void setUp() {
        user = new User("parent@mail.com", "PARENT");

        parent = new Parent();
        parent.setId(1L);
        parent.setName("Parent");
        parent.setSurname("Parentyan");
        parent.setUser(user);
        parent.setPassword("password");

        updatedParent = new Parent();
        updatedParent.setId(1L);
        updatedParent.setName("Parent");
        updatedParent.setSurname("Parentyan");
        updatedParent.setUser(user);
        updatedParent.setPassword("password");
    }

    @Test
    void findByIdReturnsRightEntity() {
        Long id = 1L;
        Parent expectedParent = parent;
        when(parentRepository.findById(id)).thenReturn(Optional.of(parent));

        ParentDto actualParent = parentService.findById(id);

        assertThat(actualParent).isNotNull();
        assertEquals(ParentMapper.mapToParentDto(expectedParent), actualParent);
        assertThat(expectedParent.getUser().getEmail().equalsIgnoreCase(actualParent.getEmail()) &&
                expectedParent.getUser().getRole().equalsIgnoreCase(actualParent.getRole())).isTrue();
    }

    @Test
    void findByIdThrowsEx() {
        assertThrows(EntityNotFoundException.class, () -> parentService.findById(2L));
    }

    @Test
    void findByUserIdPositiveCase() {
        Parent expectedParent = parent;
        when(parentRepository.findByUserId(1L)).thenReturn(Optional.ofNullable(parent));
        ParentDto actualParent = parentService.findByUserId(1L);

        Assertions.assertNotNull(actualParent);
        Assertions.assertEquals(ParentMapper.mapToParentDto(expectedParent), actualParent);
    }

    @Test
    void findByUserIdThrowsEx() {
        assertThrows(EntityNotFoundException.class, () -> parentService.findByUserId(2L));
    }

    @Test
    void findParentEditByIdPositiveCase() {
        Parent expectedParent = parent;
        when(parentRepository.findById(1L)).thenReturn(Optional.ofNullable(parent));
        ParentEditDto actualParent = parentService.findParentEditById(1L);

        Assertions.assertNotNull(actualParent);
        Assertions.assertEquals(ParentMapper.mapToParentEditDto(expectedParent), actualParent);
    }

    @Test
    void findParentEditByIdThrowsEx() {
        assertThrows(EntityNotFoundException.class, () -> parentService.findParentEditById(2L));
    }

    @Test
    void saveReturnsRightParent() {
        String expectedEmail = "parent@mail.com";
        when(userService.save(any())).thenReturn(user);
        when(parentRepository.save(any())).thenReturn(parent);

        ParentDto savedParent = parentService.save(ParentMapper.mapToParentDto(parent));

        Assertions.assertNotNull(savedParent);
        assertEquals(expectedEmail, savedParent.getEmail());
    }

    @Test
    void findAllNotNullAndSizePositiveCase() {
        when(parentRepository.findAll()).thenReturn(List.of(parent));

        List<ParentDto> all = parentService.findAll();

        Assertions.assertNotNull(all);
        Assertions.assertEquals(1, all.size());
    }

    @Test
    void findAllEmptyCase() {
        List<ParentDto> all = parentService.findAll();
        Assertions.assertEquals(0, all.size());
    }

    @Test
    void updateParentWhenNewName() {
        Long id = 1L;
        String name = "testName";
        updatedParent.setName(name);
        when(parentRepository.findById(id)).thenReturn(Optional.of(parent));
        when(parentRepository.save(any())).thenReturn(updatedParent);

        Parent parent = parentRepository.findById(id).get();
        parent.setName(name);
        ParentDto updatedParentDto = parentService.update(ParentMapper.mapToParentEditDto(parent));

        assertThat(updatedParentDto.getName()).isEqualTo(name);
    }

    @Test
    void updateParentWhenNewSurname() {
        Long id = 1L;
        String surname = "testSurname";
        updatedParent.setSurname(surname);
        when(parentRepository.findById(id)).thenReturn(Optional.of(parent));
        when(parentRepository.save(any())).thenReturn(updatedParent);

        Parent parent = parentRepository.findById(id).get();
        parent.setSurname(surname);
        ParentDto updatedParentDto = parentService.update(ParentMapper.mapToParentEditDto(parent));

        assertThat(updatedParentDto.getSurname()).isEqualTo(surname);
    }

    @Test
    void updateParentWhenNewEmail() {
        Long id = 1L;
        String email = "test-email@gmail.com";
        updatedParent.getUser().setEmail(email);
        when(parentRepository.findById(id)).thenReturn(Optional.of(parent));
        when(parentRepository.save(any())).thenReturn(updatedParent);

        Parent parent = parentRepository.findById(id).get();
        parent.getUser().setEmail(email);

        ParentDto updatedParentDto = parentService.update(ParentMapper.mapToParentEditDto(parent));

        assertThat(updatedParentDto.getEmail()).isEqualTo(email);
    }
}