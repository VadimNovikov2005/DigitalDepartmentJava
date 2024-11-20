import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void SortLastName(List<User> users) {
        Comparator<User> comparator = Comparator.comparing(User::getLastName);
        List<User> sortLastName = users.stream().sorted(comparator).toList();
        sortLastName.forEach(x -> System.out.println(x.getFirstName() + " " + x.getLastName()));
    }

    public static void SortAge(List<User> users) {
        Comparator<User> comparator = Comparator.comparing(User::getAge);
        List<User> sort = users.stream().sorted(comparator).toList();
        sort.forEach(x -> System.out.println(x.getFirstName() + " " + x.getLastName()));
    }

    public static boolean IsAge(List<User> users) {
        return users.stream().allMatch(x -> x.getAge() > 7);
    }

    public static float AverageAge(List<User> users) {
        int sum = users.stream().mapToInt(User::getAge).sum();
        return (float) sum / (float) users.size();
    }

    public static int CountOfDifferentCountry(List<User> users) {
        return users.stream().map(User::getCountry).distinct().toList().size();
    }

    public static void InputUsers(List<User> users) {
        Scanner scanner = new Scanner(System.in);
        int countUsers = scanner.nextInt();
        for (int i = 0; i < countUsers; i++) {
            User user = new User();
            System.out.println("Id");
            user.setId(scanner.nextInt());
            System.out.println("First name");
            user.setFirstName(scanner.next());
            System.out.println("Last name");
            user.setLastName(scanner.next());
            System.out.println("Age");
            user.setAge(scanner.nextInt());
            System.out.println("Country");
            user.setCountry(scanner.next());
            users.add(user);
        }
    }

    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        InputUsers(users);

        SortLastName(users);
        SortAge(users);
        System.out.println(IsAge(users));
        System.out.println(AverageAge(users));
        System.out.println(CountOfDifferentCountry(users));

    }
}