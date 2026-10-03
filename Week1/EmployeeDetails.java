public class EmployeeDetails {

    // Instance variables
    String name;
    double salary;

    // Static variable
    static String company = "ABC Technologies";

    // Constructor
    public EmployeeDetails(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    // Instance method
    public void printEmployee() {
        System.out.println(
                "Name: " + name +
                        " | Salary: Rs " + salary +
                        " | Company: " + company
        );
    }

    // Static method
    public static void changeCompany(String newCompany) {
        company = newCompany;
    }

    public static void main(String[] args) {

        EmployeeDetails employee1 =
                new EmployeeDetails("Rahul", 50000);

        EmployeeDetails employee2 =
                new EmployeeDetails("Priya", 60000);

        System.out.println("Before company change:");
        employee1.printEmployee();
        employee2.printEmployee();

        // Change shared static value
        EmployeeDetails.changeCompany("XYZ Technologies");

        System.out.println("\nAfter company change:");
        employee1.printEmployee();
        employee2.printEmployee();
    }
}