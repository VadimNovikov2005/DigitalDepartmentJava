import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int countStudents = scanner.nextInt();
        List<Student> students = new ArrayList<>(countStudents);
        Student.inputStudent(students, countStudents);
        Student.sortStudent(students);
        Student.printStudents(students, 2);
    }
}
