package com.epam.edumanagementsystem.parent.rest.repository;

import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.util.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ParentRepositoryTest {

    private Long parentId;

    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setup() {
        User user = new User();
        user.setRole("PARENT");
        user.setEmail("testParent@mail.ru");
        entityManager.persist(user);

        Parent parent = new Parent();
        parent.setName("John");
        parent.setSurname("Doe");
        parent.setUser(user);
        parent.setPassword("Parent123+");

        parentId = parentRepository.save(parent).getId();

    }

    @Test
    void testUpdateParentNameAndSurnameByIdIsOk() {
        String name = "NewParent";
        String surname = "NewParent";

        parentRepository.updateParentNameAndSurnameById(name, surname, parentId);

        Optional<Parent> updatedParent = parentRepository.findById(parentId);
        assertThat(updatedParent.isPresent());
        assertThat(updatedParent.get().getName().equalsIgnoreCase(name) &&
                updatedParent.get().getSurname().equalsIgnoreCase(surname));
    }
}