package edu.espe.springlab.repository;

import edu.espe.springlab.domain.Student;
import edu.espe.springlab.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDate;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository repository; // Se asume que el nombre completo es StudentRepository

    @Test
    void shouldSaveAndFindStudentByEmail() {
        Student s = new Student();
        s.setFullName("test User");
        s.setEmail("test@email.com"); // El email parece ser 'test@e' pero se completa
        s.setBirthDate(LocalDate.of(2000, 10,10));
        s.setActive(true);

        //repository.save(s);
        repository.save(s);

        var result = repository.findByEmail("test@example.com");
        assertThat(result).isPresent();
        assertThat(result.get().getFullName()).isEqualTo("test user");
    }
}
