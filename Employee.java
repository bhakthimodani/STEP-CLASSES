class Employee {
    private int empId;
    private String empName;
    private double salary;

    Employee(int empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    double getSalary() {
        return salary;
    }
}

class ManagerEmployee extends Employee {
    private double teamBonus;

    ManagerEmployee(int empId, String empName,
                    double salary, double teamBonus) {
        super(empId, empName, salary);
        this.teamBonus = teamBonus;
    }

    double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}

class InternEmployee extends Employee {
    private double stipendCap;

    InternEmployee(int empId, String empName,
                   double salary, double stipendCap) {
        super(empId, empName, salary);
        this.stipendCap = stipendCap;
    }

    double effectiveSalary() {
        return Math.min(getSalary(), stipendCap);
    }
}

public class Main {
    public static void main(String[] args) {

        Employee plain = new Employee(101, "Aditi", 40000);

        Employee manager =
            new ManagerEmployee(102, "Rohan", 70000, 8000);

        Employee intern =
            new InternEmployee(103, "Meera", 12000, 10000);

        Employee[] employees = {plain, manager, intern};

        for (Employee emp : employees) {

            if (emp instanceof ManagerEmployee) {
                ManagerEmployee m = (ManagerEmployee) emp;
                System.out.println(
                    "Manager effective pay: Rs "
                    + m.effectiveSalary()
                );

            } else if (emp instanceof InternEmployee) {
                InternEmployee i = (InternEmployee) emp;
                System.out.println(
                    "Intern effective pay: Rs "
                    + i.effectiveSalary()
                );

            } else {
                System.out.println(
                    "Plain employee pay: Rs "
                    + emp.getSalary()
                );
            }
        }
    }
}