import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Student {
    private String name;
    private int group;
    private int course;
    private int[] marks;
    private float averageScore;

    public static void inputStudent(List<Student> students, int countStudents) {
        Scanner scanner = new Scanner(System.in);
        for(int i = 0; i < countStudents; i++) {
            Student student = new Student();
            System.out.print("Name: ");
            student.name = scanner.next();
            System.out.print("Group: ");
            student.group = scanner.nextInt();
            System.out.print("Course: ");
            student.course = scanner.nextInt();
            System.out.println("Marks:");
            String ch = scanner.next();
            String[] marks = ch.split(",");
            student.marks = new int[marks.length];
            int summa = 0;
            for(int t = 0; t < marks.length; t++) {
                student.marks[t] = parseInt(marks[t]);
                summa += student.marks[t];
            }
            student.averageScore = (float)(summa) / (float)(marks.length);
            students.add(i, student);
        }
    }

    public static void sortStudent(List<Student> students) {
        Iterator<Student> studentIterator = students.iterator();
        while(studentIterator.hasNext()) {
            Student student = studentIterator.next();
            if(student.averageScore < 3.0) {
                studentIterator.remove();
            }
            else {
                student.course++;
            }
        }
    }

    public static void printStudents(List<Student> students, int course) {
        for(Student student: students) {
            if(student.course == course) {
                System.out.println(student.name);
            }
        }
    }
}
