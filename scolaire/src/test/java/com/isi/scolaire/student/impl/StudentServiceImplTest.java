package com.isi.scolaire.student.impl;

import com.isi.scolaire.exception.EntityExistsException;
import com.isi.scolaire.exception.EntityNotFoundException;
import com.isi.scolaire.student.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;
    private StudentRequest studentRequest;
    private StudentResponse studentResponse;

    @BeforeEach
    void setUp() {
        studentRequest = new StudentRequest();
        studentRequest.setId(1L);
        studentRequest.setFirstName("John");
        studentRequest.setLastName("Doe");
        studentRequest.setEmailPro("john.doe@school.com");
        studentRequest.setEmailPerso("john.doe@gmail.com");
        studentRequest.setPhoneNumber("123456789");
        studentRequest.setArchive(false);

        student = new Student();
        student.setId(1L);
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmailPro("john.doe@school.com");
        student.setEmailPerso("john.doe@gmail.com");
        student.setPhoneNumber("123456789");
        student.setRegistrationNu("ST123");
        student.setArchive(false);

        studentResponse = new StudentResponse();
        studentResponse.setId(1L);
        studentResponse.setFirstName("John");
        studentResponse.setLastName("Doe");
    }

    @Test
    void shouldAddStudentSuccessfully() {
        when(studentRepository.findByEmailPerso(studentRequest.getEmailPerso())).thenReturn(Optional.empty());
        when(studentRepository.findByEmailPro(studentRequest.getEmailPro())).thenReturn(Optional.empty());
        when(studentRepository.findByPhoneNumber(studentRequest.getPhoneNumber())).thenReturn(Optional.empty());
        //when(studentRepository.findByRegistrationNu(studentRequest.getRegistrationNu())).thenReturn(Optional.empty());
        when(studentMapper.toStudent(studentRequest)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(student);
        when(studentMapper.toStudentResponse(student)).thenReturn(studentResponse);

        StudentResponse response = studentService.addStudent(studentRequest);
        assertNotNull(response);
        assertEquals("John", response.getFirstName());
    }

    @Test
    void shouldThrowExceptionWhenAddingExistingStudent() {
        when(studentRepository.findByEmailPerso(studentRequest.getEmailPerso())).thenReturn(Optional.of(student));
        when(messageSource.getMessage(eq("emailPerso.exists"), any(), any(Locale.class))).thenReturn("Email personnel déjà utilisé");

        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> studentService.addStudent(studentRequest));
        assertEquals("Email personnel déjà utilisé", exception.getMessage());
    }

    @Test
    void shouldGetStudentByIdSuccessfully() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentMapper.toStudentResponse(student)).thenReturn(studentResponse);

        StudentResponse response = studentService.getStudentById(1L);
        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("student.notfound"), any(), any(Locale.class))).thenReturn("Étudiant non trouvé");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> studentService.getStudentById(1L));
        assertEquals("Étudiant non trouvé", exception.getMessage());
    }

    @Test
    void shouldGetAllStudentsSuccessfully() {
        when(studentRepository.findAll()).thenReturn(List.of(student));
        when(studentMapper.toStudentResponseList(anyList())).thenReturn(List.of(studentResponse));

        List<StudentResponse> students = studentService.getAllStudents();
        assertFalse(students.isEmpty());
        assertEquals(1, students.size());
    }

    @Test
    void shouldDeleteStudentByIdSuccessfully() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(student);

        assertDoesNotThrow(() -> studentService.deleteStudentById(1L));
        verify(studentRepository, times(1)).delete(student);
    }
}
