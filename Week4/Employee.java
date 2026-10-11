
public class Employee {

    String employeeId;
    double salary;

    Employee(String employeeId, double salary) {
        this.employeeId = employeeId;
        this.salary = salary;
    }

    void raiseSalary(double salary) {
        this.salary = this.salary + salary;
    }

    public static void main(String[] args) {

        Employee[] employees = {
                new Employee("E-101", 40000),
                new Employee("E-102", 55000),
                new Employee("E-103", 62000),
                new Employee("E-104", 48000)
        };

        for (Employee employee : employees) {
            employee.raiseSalary(5000);
        }

        for (Employee employee : employees) {
            System.out.println(employee.employeeId
                    + " | Final Salary: Rs "
                    + employee.salary);
        }
    }
}
