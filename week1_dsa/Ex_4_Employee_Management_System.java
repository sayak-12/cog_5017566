class Employee {
    private String employeeId;
    private String name;
    private String position;
    private double salary;

    // Constructor
    public Employee(String employeeId, String name, String position, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    // Getters
    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Employee [employeeId=" + employeeId + ", name=" + name + ", position=" + position + ", salary=" + salary + "]";
    }
}
class EmployeeManager {
    private Employee[] employees;
    private int size;

    // Constructor
    public EmployeeManager(int capacity) {
        employees = new Employee[capacity];
        size = 0;
    }

    // Add an employee
    public void addEmployee(Employee employee) {
        if (size < employees.length) {
            employees[size++] = employee;
        } else {
            System.out.println("Array is full. Cannot add more employees.");
        }
    }

    // Search for an employee by ID
    public Employee searchEmployeeById(String employeeId) {
        for (Employee emp : employees) {
            if (emp != null && emp.getEmployeeId().equals(employeeId)) {
                return emp;
            }
        }
        return null;
    }

    // Traverse and display all employees
    public void traverseEmployees() {
        for (Employee emp : employees) {
            if (emp != null) {
                System.out.println(emp);
            }
        }
    }

    // Delete an employee by ID
    public boolean deleteEmployeeById(String employeeId) {
        for (int i = 0; i < size; i++) {
            if (employees[i].getEmployeeId().equals(employeeId)) {
                // Shift elements to the left
                for (int j = i; j < size - 1; j++) {
                    employees[j] = employees[j + 1];
                }
                employees[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }
}
public class Ex_4_Employee_Management_System{
    public static void main(String[] args) {
        EmployeeManager manager = new EmployeeManager(5);

        // Adding employees
        manager.addEmployee(new Employee("E001", "Alice", "Developer", 75000));
        manager.addEmployee(new Employee("E002", "Bob", "Designer", 65000));
        manager.addEmployee(new Employee("E003", "Charlie", "Manager", 85000));

        // Traversing employees
        System.out.println("All Employees:");
        manager.traverseEmployees();

        // Searching for an employee
        System.out.println("\nSearching for employee with ID E002:");
        Employee employee = manager.searchEmployeeById("E002");
        if (employee != null) {
            System.out.println(employee);
        } else {
            System.out.println("Employee not found.");
        }

        // Deleting an employee
        System.out.println("\nDeleting employee with ID E002:");
        boolean deleted = manager.deleteEmployeeById("E002");
        if (deleted) {
            System.out.println("Employee deleted.");
        } else {
            System.out.println("Employee not found.");
        }

        // Traversing employees after deletion
        System.out.println("\nAll Employees after deletion:");
        manager.traverseEmployees();
    }
}
