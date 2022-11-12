package com.epam.edumanagementsystem.parent.impl;

import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.parent.rest.mapper.ParentMapper;
import com.epam.edumanagementsystem.parent.rest.repository.ParentRepository;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParentServiceImplTest {

    @Mock
    private ParentRepository parentRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ParentServiceImpl parentService;

    private Parent parent;
    private User user;

    @BeforeEach
    private void setUp() {
        user = new User("parent@mail.com", "PARENT");
        parent = new Parent(1L, "Parent", "Parentyan", user, "password");
    }

    @Test
    void findByIdIsPresent() {
        when(parentRepository.findById(1L)).thenReturn(Optional.of(parent));
        Optional<Parent> returnedParent = parentService.findById(1L);

        Assertions.assertTrue(returnedParent.isPresent(), "Parent was not found");
        Assertions.assertSame(returnedParent.get(), parent, "The returned parent was not the same as the mock");
    }

    @Test
    void findByIdWithNull() {
        Assertions.assertThrows(NullPointerException.class, () -> parentService.findById(null));
    }

    @Test
    void findByIdNotFound() {
        lenient().when(parentRepository.findById(1L)).thenReturn(Optional.empty());
    }

    @Test
    void saveWithNullFieldDto() {
        Assertions.assertThrows(NullPointerException.class, () -> parentService.save(ParentMapper.toParentDto(new Parent(null,
                null))));
    }

    @Test
    void savePositiveAndEmailCheck() {
        when(userService.save(any())).thenReturn(user);
        when(parentRepository.save(any())).thenReturn(parent);

        Parent savedParent = parentService.save(ParentMapper.toParentDto(parent));

        Assertions.assertNotNull(savedParent, "The savedParent should not be null");
        Assertions.assertEquals("parent@mail.com", savedParent.getUser().getEmail());
    }

    @Test
    void saveWithNull() {
        Assertions.assertThrows(NullPointerException.class, () -> parentService.save(null));
    }

    @Test
    void findAllNotNullAndSizePositiveCase() {
        when(parentRepository.findAll()).thenReturn(List.of(parent));

        List<Parent> all = parentService.findAll();

        Assertions.assertNotNull(all);
        Assertions.assertEquals(1, all.size());
    }

    @Test
    void findAllEmptyCase() {
        List<Parent> all = parentService.findAll();
        Assertions.assertEquals(0, all.size());
    }

    @Test
    void findByUserIdPositiveCase() {
        when(parentRepository.findByUserId(1L)).thenReturn(parent);
        Parent returnedParent = parentService.findByUserId(1L);

        Assertions.assertNotNull(returnedParent, "Parent was null");
        Assertions.assertSame(returnedParent, parent, "The returned parent was not the same as the mock");
    }

    @Test
    void findByUserIdWithNull() {
        Assertions.assertThrows(NullPointerException.class, () -> parentService.findByUserId(null));
    }

    @Test
    void findByUserIdNotFound() {
        lenient().when(parentRepository.findByUserId(1L)).thenReturn(null);
    }

    @Test
    void deleteByIdVerifyAndCheckIfDeleted() {
        when(userService.save(any())).thenReturn(user);
        when(parentRepository.save(any())).thenReturn(parent);
        Parent savedParent = parentService.save(ParentMapper.toParentDto(parent));

        parentService.deleteById(savedParent.getId());
        verify(parentRepository, times(1)).deleteById(savedParent.getId());
        Assertions.assertEquals(parentService.findById(savedParent.getId()), Optional.empty());
    }

    @Test
    void deleteByIdWithNull() {
        Assertions.assertThrows(NullPointerException.class, () -> parentService.deleteById(null));
    }

    @Test
    void updateParentNameAndSurnameByIdVerifiesOK(){
        String name = "NewParent";
        String surname = "NewParent";
        Long id = parent.getId();
        doNothing().when(parentRepository).updateParentNameAndSurnameById(name,surname,id);

        parentService.updateParentNameAndSurnameById(name,surname,id);

        verify(parentRepository, times(1)).updateParentNameAndSurnameById(name,surname,id);
    }

}