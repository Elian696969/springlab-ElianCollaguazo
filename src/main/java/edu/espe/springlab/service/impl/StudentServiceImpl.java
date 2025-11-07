package edu.espe.springlab.service.impl;
import edu.espe.springlab.domain.Student;
import edu.espe.springlab.dto.StudentRequestData;
import edu.espe.springlab.dto.StudentResponse;
import edu.espe.springlab.dto.StudentUpdateData;
import edu.espe.springlab.repository.StudentRepository;
import edu.espe.springlab.service.StudentService;
import edu.espe.springlab.web.advice.ConflictException;
import edu.espe.springlab.web.advice.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repo;

    public StudentServiceImpl(StudentRepository repo) {
        this.repo = repo;
    }


    @Override
    public StudentResponse create(StudentRequestData request) {
        if(repo.existsByEmail(request.getEmail())){
            throw new ConflictException("El correo ya esta registrado");


        }

        Student student = new Student();
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setBirthDate(request.getBirthDate());
        student.setActive(true);

        Student saved = repo.save(student);
        return toResponse(saved) ;
    }


    @Override
    public StudentResponse getById(Long id) {
        Student student = repo.findById(id).orElseThrow(()->new NotFoundException("Estudiante no encontrado"));
        return toResponse(student);
    }

    @Override
    public List<StudentResponse> list() {
        return repo.findAll().stream().map(this :: toResponse).toList();
    }

    @Override
    public StudentResponse desactivate(Long id) {
        Student student = repo.findById(id).orElseThrow(()->new NotFoundException("Estudiante no encontrado"));

        student.setActive(false);

        return toResponse(repo.save(student));
    }

    private StudentResponse toResponse(Student student){
        StudentResponse r = new StudentResponse();
        r.setId(student.getId());
        r.setFullName(student.getFullName());
        r.setEmail(student.getEmail());
        r.setDateOfBirth(student.getBirthDate());
        r.setActive(student.getActive());
        return r;

    }

    @Override
    public Page<StudentResponse> getAllStudents(Pageable pageable) {
        return repo.findAll(pageable)
                .map(this::toResponse);
    }

    @Override
    public Page<StudentResponse> searchByName(String name, Pageable pageable) {
        return repo.findByFullNameContainingIgnoreCase(name, pageable)
                .map(this::toResponse);
    }
    @Override
    public StudentResponse update(Long id, StudentUpdateData request) {
        Student student = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));

        // Validación de correo duplicado
        if (repo.existsByEmail(request.getEmail()) && !student.getEmail().equals(request.getEmail())) {
            throw new ConflictException("El correo ya está registrado por otro estudiante");
        }

        // Actualizar los campos permitidos
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setBirthDate(request.getBirthDate());

        if (request.getActive() != null) {
            student.setActive(request.getActive());
        }

        Student updated = repo.save(student);
        return toResponse(updated);
    }


}
