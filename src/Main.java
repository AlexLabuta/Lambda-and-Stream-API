import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private static final String staffFile = "data/staff.txt";
    private static final String dateFormat = "dd.MM.yyyy";

    public static void main(String[] args) throws ParseException {
        ArrayList<Employee> staff = loadStaffFromFile();

        Collections.sort(staff, (o1, o2) -> {
            if (o1.getSalary().equals(o2.getSalary())) return o1.getName().compareTo(o2.getName());
            else return o1.getSalary().compareTo(o2.getSalary());
        });
        staff.forEach(System.out::println);

        DateFormat format = new SimpleDateFormat(dateFormat);
        Date dateStart = format.parse("31.12.2016");
        Date dateFinish = format.parse("01.01.2018");
        System.out.println("\nМаксимальная зарплата среди всех сотрудников пришедших в 2017 году");
        staff.stream().
                filter(e -> e.getWorkStart().after(dateStart) && e.getWorkStart().before(dateFinish)).
                max(Comparator.comparing(Employee::getSalary)).
                ifPresent(System.out::println);

    }

    private static ArrayList<Employee> loadStaffFromFile() {
        ArrayList<Employee> staff = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(staffFile));
            for (String line : lines) {
                String[] fragments = line.split("\t");
                if (fragments.length != 3) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                staff.add(new Employee(
                        fragments[0],
                        Integer.parseInt(fragments[1]),
                        (new SimpleDateFormat(dateFormat)).parse(fragments[2])
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return staff;
    }
}