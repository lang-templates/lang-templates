import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDemo {
    public static void main(String[] args) {
        // Employeeのメンバは、String id, String nameです
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("1", "Yamada"));
        employeeList.add(new Employee("2", "Takeda"));
        employeeList.add(new Employee("3", "Tanaka"));
        employeeList.add(new Employee("4", "Yoshida"));
        employeeList.add(new Employee("5", "Sawada"));

        List<String> foundList = employeeList.stream()
                .filter(i -> i.getId().equals("5"))
                .map(Employee::getName)
                .collect(Collectors.toList());

        if (foundList.stream().count() == 1) {
            String employeeName = foundList.get(0);
            System.out.println(employeeName);
        }
    }

    public static class Employee {
        private String id;
        private String name;
        public Employee(String id, String name) {
            this.id = id;
            this.name = name;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}
