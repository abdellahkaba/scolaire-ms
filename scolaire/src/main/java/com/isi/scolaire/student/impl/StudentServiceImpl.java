package com.isi.scolaire.student.impl;

import com.isi.scolaire.common.PageResponse;
import com.isi.scolaire.exception.EntityExistsException;
import com.isi.scolaire.exception.EntityNotFoundException;
import com.isi.scolaire.student.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Setter
@Getter
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final MessageSource messageSource;
    @Override
    public StudentResponse addStudent(StudentRequest request) {
        if (studentRepository.findByEmailPerso(request.getEmailPerso()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("emailPerso.exists", new
                    Object[]{request.getEmailPerso()}, Locale.getDefault()));
        }
        if (studentRepository.findByEmailPro(request.getEmailPro()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("emailPro.exists", new
                    Object[]{request.getEmailPerso()}, Locale.getDefault()));
        }
        if (studentRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("phoneNumber.exists", new
                    Object[]{request.getPhoneNumber()}, Locale.getDefault()));
        }
//        if (studentRepository.findByRegistrationNu(request.getRegistrationNu()).isPresent()) {
//            throw new EntityExistsException(messageSource.getMessage("matricule.exists", new
//                    Object[]{request.getRegistrationNu()}, Locale.getDefault()));
//        }
        Student student = studentMapper.toStudent(request);
        var savedStudent = studentRepository.save(student);
        return studentMapper.toStudentResponse(savedStudent);
    }

    @Override
    public StudentResponse getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(studentMapper::toStudentResponse)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("student.notfound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return studentMapper.toStudentResponseList(students);
    }

    @Override
    public StudentResponse updateStudent(StudentRequest request) {
//        studentRepository.findByRegistrationNu(request.getRegistrationNu())
//                .ifPresent(existingNum -> {
//                    if (!existingNum.getId().equals(request.getId())) {
//                        throw new EntityExistsException(messageSource.getMessage("matricule.exists", new Object[]{existingNum}, Locale.getDefault() ));
//                    }
//                });
        studentRepository.findByEmailPerso(request.getEmailPerso())
                .ifPresent(existingEmail -> {
                    if (!existingEmail.getId().equals(request.getId())) {
                        throw new EntityExistsException(messageSource.getMessage("emailPerso.exists", new Object[]{existingEmail}, Locale.getDefault() ));
                    }
                });
        studentRepository.findByEmailPro(request.getEmailPro())
                .ifPresent(existingEmail -> {
                    if (!existingEmail.getId().equals(request.getId())) {
                        throw new EntityExistsException(messageSource.getMessage("emailPro.exists", new Object[]{existingEmail}, Locale.getDefault() ));
                    }
                });
        studentRepository.findByPhoneNumber(request.getPhoneNumber())
                .ifPresent(existingPhone -> {
                    if (!existingPhone.getId().equals(request.getId())) {
                        throw new EntityExistsException(messageSource.getMessage("phoneNumber.exists", new Object[]{existingPhone}, Locale.getDefault() ));
                    }
                });
        var student = studentRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("student.notfound", new  Object[]{""}, Locale.getDefault() )));
//        student.setRegistrationNu(request.getRegistrationNu());
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmailPro(request.getEmailPro());
        student.setEmailPerso(request.getEmailPerso());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setAddress(request.getAddress());
        student.setArchive(request.isArchive());
        var updatedStudent = studentRepository.save(student);
        return studentMapper.toStudentResponse(updatedStudent);
    }


    @Override
    public void deleteStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("student.notfound", new Object[]{id}, Locale.getDefault() )));
        studentRepository.delete(student);
    }
    @Override
    public PageResponse<StudentResponse> listAllStudents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> students = studentRepository.findAll(pageable);
        List<StudentResponse> responses = students.stream()
                .map(studentMapper::toStudentResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(
                responses,
                students.getNumber(),
                students.getSize(),
                students.getTotalElements(),
                students.getTotalPages(),
                students.isFirst(),
                students.isLast()
        );
    }
}
