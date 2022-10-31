package com.epam.edumanagementsystem.parent.rest.service;

import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.parent.rest.mapper.ParentMapper;
import com.epam.edumanagementsystem.parent.rest.repository.ParentRepository;
import com.epam.edumanagementsystem.util.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.lenient;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ParentServiceTest {

    @Mock
    private ParentService parentService;

    @Test
    void findByIdIsPresent() {
        User user = new User(1L, "parent@mail.com", "PARENT");
        Parent parent = new Parent(1L, "Parent", "Parentyan", user, "password");

        lenient().when(parentService.findById(1L)).thenReturn(Optional.of(parent));
        Optional<Parent> returnedParent = parentService.findById(1L);

        Assertions.assertTrue(returnedParent.isPresent(), "Parent was not found");
        Assertions.assertSame(returnedParent.get(), parent, "The returned parent was not the same as the mock");
    }

    @Test
    void findByIdWithNull() {
        lenient().when(parentService.findById(null)).thenThrow(NullPointerException.class);
    }

    @Test
    void findByIdNotFound() {
        lenient().when(parentService.findById(1L)).thenReturn(Optional.empty());
    }

    @Test
    void saveWithNullFieldDto() {
        Assertions.assertThrows(NullPointerException.class, () -> parentService.save(ParentMapper.toParentDto(new Parent(null,
                null))));
    }

    @Test
    void savePositiveAndEmailCheck() {
        User user = new User(1L, "parent@mail.com", "PARENT");
        Parent parent = new Parent(1L, "Parent", "Parentyan", user, "password");
        lenient().when(parentService.save(any())).thenReturn(Optional.of(parent));

        Optional<Parent> savedParent = parentService.save(ParentMapper.toParentDto(parent));

        Assertions.assertNotNull(savedParent, "The savedParent should not be null");
        Assertions.assertEquals("parent@mail.com", savedParent.get().getUser().getEmail());
    }

    @Test
    void saveWithNull() {
        lenient().when(parentService.save(null)).thenThrow(NullPointerException.class);
    }

    @Test
    void findAllNotNullAndSizePositiveCase() {
        User firstUser = new User(1L, "parent1@mail.com", "PARENT");
        Parent firstParent = new Parent(1L, "ParentOne", "ParentOneyan", firstUser, "password");

        User secondUser = new User(1L, "parent2@mail.com", "PARENT");
        Parent secondParent = new Parent(1L, "ParentTwo", "ParentTwoyan", secondUser, "password");

        lenient().when(parentService.findAll()).thenReturn(List.of(firstParent,secondParent));

        List<Parent> all = parentService.findAll();

        Assertions.assertNotNull(all);
        Assertions.assertEquals(2, all.size());
    }

    @Test
    void findAllEmptyCase() {
        List<Parent> all = parentService.findAll();
        Assertions.assertEquals(0, all.size());
    }

    @Test
    void findByUserIdPositiveCase() {
        User user = new User(1L, "parent@mail.com", "PARENT");
        Parent parent = new Parent(1L, "Parent", "Parentyan", user, "password");

        lenient().when(parentService.findByUserId(1L)).thenReturn(parent);
        Parent returnedParent = parentService.findByUserId(1L);

        Assertions.assertNotNull(returnedParent, "Parent was null");
        Assertions.assertSame(returnedParent, parent, "The returned parent was not the same as the mock");
    }

    @Test
    void findByUserIdWithNull() {
        lenient().when(parentService.findByUserId(null)).thenThrow(NullPointerException.class);
    }

    @Test
    void findByUserIdNotFound() {
        lenient().when(parentService.findByUserId(1L)).thenReturn(null);
    }

}