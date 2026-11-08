package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.Student;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {

    private final DataSource dataSource;

    public StudentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Student student) {

        String sql = """
                INSERT INTO students (full_name, age, email)
                VALUES (?, ?, ?)
                """;

        String fullName = student.getFirstName() + " "
                + student.getLastName();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, fullName);
            statement.setInt(2, student.getAge());
            statement.setString(3, student.getEmail());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error saving student", e);
        }
    }
    public List<Student> findAll() {

        List<Student> students = new ArrayList<>();

        String sql = """
                SELECT id, full_name, age, email
                FROM students
                ORDER BY id
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                students.add(mapStudent(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error retrieving students", e);
        }

        return students;
    }
    public Optional<Student> findById(int id) {

        String sql = """
                SELECT id, full_name, age, email
                FROM students
                WHERE id = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return Optional.of(mapStudent(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error finding student", e);
        }

        return Optional.empty();
    }
    private Student mapStudent(ResultSet resultSet)
            throws SQLException {

        String fullName = resultSet.getString("full_name");
        String[] names = fullName.trim().split("\\s+", 2);

        Student student = new Student(
                names[0],
                names.length > 1 ? names[1] : "",
                resultSet.getString("email"),
                resultSet.getInt("age")
        );

        student.setId(resultSet.getInt("id"));

        return student;
    }
}