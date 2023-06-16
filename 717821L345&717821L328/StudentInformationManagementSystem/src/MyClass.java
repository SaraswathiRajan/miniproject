
import java.sql.*;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

public class MyClass {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root");

            while (true) {
                System.out.println("----- Student Information System -----");
                System.out.println("1. Add Student");
                System.out.println("2. View Student");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline character

                switch (choice) {
                    case 1:
                        System.out.print("Enter student name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter student ID: ");
                        int studentId = scanner.nextInt();
                        System.out.print("Enter student grade: ");
                        double grade = scanner.nextDouble();
                        scanner.nextLine();  // Consume newline character
                        addStudent(connection, name, studentId, grade);
                        break;
                    case 2:
                        System.out.print("Enter student ID: ");
                        int viewStudentId = scanner.nextInt();
                        scanner.nextLine();  // Consume newline character
                        viewStudent(connection, viewStudentId);
                        break;
                    case 3:
                        System.out.print("Enter student ID: ");
                        int updateStudentId = scanner.nextInt();
                        System.out.print("Enter new grade: ");
                        double newGrade = scanner.nextDouble();
                        scanner.nextLine();  // Consume newline character
                        updateStudent(connection, updateStudentId, newGrade);
                        break;
                    case 4:
                        System.out.print("Enter student ID: ");
                        int deleteStudentId = scanner.nextInt();
                        scanner.nextLine();  // Consume newline character
                        deleteStudent(connection, deleteStudentId);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        connection.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addStudent(Connection connection, String name, int studentId, double grade) throws SQLException {
        String query = "INSERT INTO students (name, student_id, grade) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, name);
        statement.setInt(2, studentId);
        statement.setDouble(3, grade);
        statement.executeUpdate();
        System.out.println("Student added successfully!");
    }

    public static void viewStudent(Connection connection, int studentId) throws SQLException {
        String query = "SELECT * FROM students WHERE student_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, studentId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            double grade = resultSet.getDouble("grade");
            System.out.println("Name: " + name + ", Student ID: " + studentId + ", Grade: " + grade);
        } else {
            System.out.println("Student not found!");
        }
    }

    public static void updateStudent(Connection connection, int studentId, double newGrade) throws SQLException {
        String query = "UPDATE students SET grade = ? WHERE student_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setDouble(1, newGrade);
        statement.setInt(2, studentId);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Student not found!");
        }
    }

    public static void deleteStudent(Connection connection, int studentId) throws SQLException {
        String query = "DELETE FROM students WHERE student_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, studentId);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found!");
        }
    }
}
