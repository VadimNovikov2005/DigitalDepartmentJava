import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Main {

    public static void inputStudent(List<Student> students, int countStudents) {
        Scanner scanner = new Scanner(System.in);
        for(int i = 0; i < countStudents; i++) {
            Student student = new Student();
            System.out.print("Name: ");
            student.setName(scanner.next());
            System.out.print("Group: ");
            student.setGroup(scanner.nextInt());
            System.out.print("Course: ");
            student.setCourse(scanner.nextInt());
            System.out.println("Marks:");
            String ch = scanner.next();
            String[] marksLine = ch.split(",");
            int[] marks = new int[marksLine.length];
            int summa = 0;
            for(int t = 0; t < marks.length; t++) {
                marks[t] = parseInt(marksLine[t]);
                summa += marks[t];
            }
            student.setMarks(marks);
            student.setAverageScore((float)(summa) / (float)(marks.length));
            students.add(i, student);
        }
    }

    public static void sortStudent(List<Student> students) {

        Iterator<Student> studentIterator = students.iterator();
        while(studentIterator.hasNext()) {
            Student student = studentIterator.next();
            if(student.getAverageScore() < 3.0) {
                studentIterator.remove();
            }
            else {
                student.setCourse(student.getCourse() + 1);
            }
        }
    }

    public static void printStudents(List<Student> students, int course) {
        for(Student student: students) {
            if(student.getCourse() == course) {
                System.out.println(student.getName());
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int countStudents = scanner.nextInt();
        List<Student> students = new ArrayList<>(countStudents);
        inputStudent(students, countStudents);
        sortStudent(students);
        printStudents(students, 2);
    }
}
