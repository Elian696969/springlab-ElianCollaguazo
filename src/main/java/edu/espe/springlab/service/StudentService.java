package edu.espe.springlab.service;

import edu.espe.springlab.dto.StudentRequestData;
import edu.espe.springlab.dto.StudentResponse;
import edu.espe.springlab.domain.Student;
import edu.espe.springlab.dto.StudentUpdateData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {

    // Crear un estudiante a partir del DTO validado
    StudentResponse create(StudentRequestData request);

    // Búsqueda por ID
    StudentResponse getById(Long id);

    // Listar todos los estudiantes
    List<StudentResponse> list();

    // Cambiar estado del estudiante
    StudentResponse desactivate(Long id);

    // paginación
    Page<StudentResponse> getAllStudents(Pageable pageable);

    //búsqueda por nombre
    Page<StudentResponse> searchByName(String name, Pageable pageable);

    StudentResponse update(Long id, StudentUpdateData request);


}
