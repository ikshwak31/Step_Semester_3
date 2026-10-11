
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

abstract class Employee {
    String name;

    Employee(String name) {
        this.name = name;
    }

    abstract boolean canTakeLeave(int days);
}

class FullTimeEmployee extends Employee {
    FullTimeEmployee(String name) {
        super(name);
    }

    boolean canTakeLeave(int days) {
        return days > 0 && days <= 30;
    }
}

class PartTimeEmployee extends Employee {
    PartTimeEmployee(String name) {
        super(name);
    }

    boolean canTakeLeave(int days) {
        return days > 0 && days <= 10;
    }
}

class Contractor extends Employee {
    Contractor(String name) {
        super(name);
    }

    boolean canTakeLeave(int days) {
        return days > 0 && days <= 5;
    }
}

class LeaveRequest {
    Employee employee;
    LocalDate startDate;
    LocalDate endDate;
    String status = "Pending";

    LeaveRequest(Employee employee, LocalDate startDate,
                 LocalDate endDate) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    int getDays() {
        return (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    void submit() {
        if (endDate.isBefore(startDate)) {
            System.out.println("Invalid leave dates.");
            return;
        }

        if (!employee.canTakeLeave(getDays())) {
            System.out.println("Leave request denied for "
                    + employee.name
                    + ": leave policy limit exceeded.");
            return;
        }

        System.out.println("Leave request submitted for "
                + employee.name + " ("
                + startDate + " to " + endDate
                + "). Status: " + status);
    }

    void review(String manager, boolean approved) {
        if (!status.equals("Pending")) {
            System.out.println("Cannot change leave request status from "
                    + status + ".");
            return;
        }

        status = approved ? "Approved" : "Rejected";

        System.out.println(employee.name + "'s leave request ("
                + startDate + " to " + endDate + ") "
                + status.toLowerCase()
                + " by Manager " + manager
                + ". Status: " + status);
    }

    void changeToPending() {
        if (!status.equals("Pending")) {
            System.out.println("Cannot change leave request status from "
                    + status + " to Pending.");
        } else {
            System.out.println("Request is already Pending.");
        }
    }
}

public class EmployeeLeaveSystem {
    public static void main(String[] args) {
        Employee john = new FullTimeEmployee("John");
        Employee jane = new PartTimeEmployee("Jane");

        LeaveRequest request1 = new LeaveRequest(
                john,
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 5)
        );

        request1.submit();
        request1.review("Alice", true);
        request1.changeToPending();

        System.out.println();

        LeaveRequest request2 = new LeaveRequest(
                jane,
                LocalDate.of(2026, 2, 10),
                LocalDate.of(2026, 2, 11)
        );

        request2.submit();
        request2.review("Bob", false);
    }
}
