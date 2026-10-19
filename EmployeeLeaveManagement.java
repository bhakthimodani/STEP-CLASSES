import java.time.LocalDate;

abstract class Employee {
    protected String name;

    public Employee(String name) {
        this.name = name;
    }

    public abstract int getMaximumLeaveDays();

    public String getName() {
        return name;
    }
}

class FullTimeEmployee extends Employee {
    public FullTimeEmployee(String name) {
        super(name);
    }

    public int getMaximumLeaveDays() {
        return 30;
    }
}

class PartTimeEmployee extends Employee {
    public PartTimeEmployee(String name) {
        super(name);
    }

    public int getMaximumLeaveDays() {
        return 15;
    }
}

class ContractEmployee extends Employee {
    public ContractEmployee(String name) {
        super(name);
    }

    public int getMaximumLeaveDays() {
        return 10;
    }
}

class LeaveRequest {
    private Employee employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status = "Pending";

    public LeaveRequest(Employee employee, LocalDate start,
                        LocalDate end) {
        this.employee = employee;
        this.startDate = start;
        this.endDate = end;
    }

    public void updateStatus(String newStatus) {
        if (!status.equals("Pending")) {
            System.out.println("Cannot change status: "
                    + status + " request cannot be changed.");
            return;
        }

        if (!newStatus.equals("Approved")
                && !newStatus.equals("Rejected")) {
            System.out.println("Invalid status transition.");
            return;
        }

        status = newStatus;
        System.out.println("Leave request for "
                + employee.getName() + " " + status.toLowerCase()
                + ". Status: " + status + ".");
    }

    public String getStatus() {
        return status;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}

class LeaveManager {
    public LeaveRequest submitRequest(Employee employee,
                                      LocalDate start,
                                      LocalDate end) {
        if (start.isAfter(end)) {
            System.out.println("Invalid leave dates.");
            return null;
        }

        long days = java.time.temporal.ChronoUnit.DAYS
                .between(start, end) + 1;

        if (days > employee.getMaximumLeaveDays()) {
            System.out.println("Leave request exceeds policy.");
            return null;
        }

        LeaveRequest request =
                new LeaveRequest(employee, start, end);

        System.out.println("Leave request submitted by "
                + employee.getName() + " for "
                + start + " to " + end
                + ". Status: Pending.");

        return request;
    }

    public void reviewRequest(LeaveRequest request,
                              String decision) {
        if (request != null) {
            request.updateStatus(decision);
        }
    }
}

public class EmployeeLeaveManagement {
    public static void main(String[] args) {
        LeaveManager manager = new LeaveManager();

        Employee john = new FullTimeEmployee("John Doe");
        Employee jane = new PartTimeEmployee("Jane Smith");

        LeaveRequest r1 = manager.submitRequest(
                john,
                LocalDate.of(2024, 10, 10),
                LocalDate.of(2024, 10, 12));

        manager.reviewRequest(r1, "Approved");

        manager.submitRequest(
                jane,
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 11, 5));

        manager.reviewRequest(r1, "Pending");
    }
}