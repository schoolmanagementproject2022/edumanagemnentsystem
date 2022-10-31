package com.epam.edumanagementsystem.admin.rest.repository;

import com.epam.edumanagementsystem.admin.model.entity.SuperAdmin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SuperAdminRepositoryTest {

    private SuperAdmin superAdmin;

    @Autowired
    private SuperAdminRepository superAdminRepository;

    @BeforeEach
    public void setup() {
        superAdmin = new SuperAdmin(1, "super-ADMIN@gmail.COM", "password");
    }

    @Test
    void testFindByEmailIsPresentWithRightEmail() {
        String email = "SUPER-adMin@GmaiL.coM";
        superAdminRepository.save(superAdmin);
        assertTrue(superAdminRepository.findByEmail(email).isPresent());
    }

    @Test
    void testFindByEmailIsEmptyWithWrongEmail() {
        String email = "adDDDMin@maiL.co";
        superAdminRepository.save(superAdmin);
        assertTrue(superAdminRepository.findByEmail(email).isEmpty());
    }
}