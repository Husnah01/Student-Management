package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.StudentService;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {

        model.addAttribute("student", new Student());

        return "register";
    }

    @PostMapping("/register")
    public String registerStudent(
            @Valid @ModelAttribute("student") Student student,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "register";
        }

        studentService.registerStudent(student);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Student registered successfully!"
        );

        return "redirect:/student/all";
    }

    @GetMapping("/all")
    public String showAllStudents(Model model) {

        model.addAttribute(
                "students",
                studentService.getAllStudents()
        );

        return "students";
    }
    @GetMapping("/{id}")
    public String getStudentById(
            @PathVariable("id") int id,
            Model model) {

        Optional<Student> student =
                studentService.getStudentById(id);

        if (student.isPresent()) {

            model.addAttribute(
                    "students",
                    List.of(student.get())
            );

        } else {

            model.addAttribute("students", List.of());

            model.addAttribute(
                    "notFoundMessage",
                    "No student found with ID: " + id
            );
        }

        return "students";
    }
}