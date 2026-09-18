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

class ParkingSlot {

    String slotNo;
    int capacity;
    int occupiedCount;

    ParkingSlot(String slotNo, int capacity, int occupiedCount) {
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }

    void allot(String vehicleNo) {
        if (occupiedCount < capacity) {
            occupiedCount++;
        }
    }

    static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {

        for (ParkingSlot slot : slots) {
            if (slot.occupiedCount < slot.capacity) {
                return slot;
            }
        }

        return null;
    }

    static ParkingSlot safeAllot(
            ParkingSlot[] slots, String vehicleNo) {

        ParkingSlot slot = findAvailableSlot(slots);

        if (slot != null) {
            slot.allot(vehicleNo);
            return slot;
        }

        return null;
    }
}

class CompanyEmployeeRecord {

    String name;
    String empId;
    Employee employee;
    ParkingSlot slot;

    static int totalRecords = 0;

    CompanyEmployeeRecord(String name, String empId,
                           Employee employee,
                           ParkingSlot slot) {

        this.name = name;
        this.empId = empId;
        this.employee = employee;
        this.slot = slot;

        totalRecords++;
    }

    String fullProfile() {

        double pay;

        if (employee instanceof ManagerEmployee) {
            ManagerEmployee manager =
                (ManagerEmployee) employee;

            pay = manager.effectiveSalary();

        } else if (employee instanceof InternEmployee) {
            InternEmployee intern =
                (InternEmployee) employee;

            pay = intern.effectiveSalary();

        } else {
            pay = employee.getSalary();
        }

        String parking;

        if (slot != null) {
            parking = slot.slotNo;
        } else {
            parking = "no parking assigned";
        }

        return name + " | Pay: Rs " + pay
                + " | Slot: " + parking;
    }
}

public class Main {

    public static void main(String[] args) {

        ParkingSlot[] slots = {
            new ParkingSlot("A1", 1, 0),
            new ParkingSlot("A2", 1, 0)
        };

        Employee e1 =
            new ManagerEmployee(101, "Divya",
                                 70000, 8000);

        Employee e2 =
            new Employee(102, "Karan",
                         40000, 0);

        Employee e3 =
            new InternEmployee(103, "Meera",
                               12000, 10000);

        ParkingSlot slot1 =
            ParkingSlot.safeAllot(slots, "CAR101");

        ParkingSlot slot2 =
            ParkingSlot.safeAllot(slots, "CAR102");

        // Third employee intentionally gets no parking.
        ParkingSlot slot3 = null;

        CompanyEmployeeRecord r1 =
            new CompanyEmployeeRecord(
                "Divya", "E101", e1, slot1);

        CompanyEmployeeRecord r2 =
            new CompanyEmployeeRecord(
                "Karan", "E102", e2, slot2);

        CompanyEmployeeRecord r3 =
            new CompanyEmployeeRecord(
                "Meera", "E103", e3, slot3);

        System.out.println(r1.fullProfile());
        System.out.println(r2.fullProfile());
        System.out.println(r3.fullProfile());

        System.out.println(
            "Total records: "
            + CompanyEmployeeRecord.totalRecords
        );
    }
}