package com.epam.edumanagementsystem.admin.rest.repository;

import com.epam.edumanagementsystem.admin.model.entity.SuperAdmin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"compile('org.flywaydb:flyway-core')"})
class SuperAdminRepositoryTest {

    @Autowired
    private SuperAdminRepository superAdminRepository;

    @Test
    void testFindByEmailIsPresentWithRightEmail() {
        superAdminRepository.save(new SuperAdmin(1, "super-ADMIN@gmail.COM", "password"));
        assertTrue(superAdminRepository.findByEmail("SUPER-adMin@GmaiL.coM").isPresent());
    }

    @Test
    void testFindByEmailIsEmptyWithWrongEmail() {
        superAdminRepository.save(new SuperAdmin(1, "super-ADMIN@gmail.COM", "password"));
        assertTrue(superAdminRepository.findByEmail("adDDDMin@maiL.co").isEmpty());
    }
}