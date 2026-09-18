class FeeAccount {
    private String regNo;
    private double totalFee;
    private double amountPaid;

    FeeAccount(String regNo, double totalFee, double amountPaid) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = amountPaid;
    }

    void pay(double amount) {
        if (amount > 0) {
            amountPaid += amount;
        } else {
            System.out.println("Payment rejected");
        }
    }

    double getDue() {
        return totalFee - amountPaid;
    }
}

class HostelFeeAccount extends FeeAccount {

    HostelFeeAccount(String regNo, double totalFee, double amountPaid) {
        super(regNo, totalFee, amountPaid);
    }

    void payInTwoInstallments(double amount) {
        pay(amount);
        pay(amount);
    }
}

class HostelRoom {
    String roomNo;
    int beds;
    int occupied;

    HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    void allot(String name) {
        if (occupied < beds) {
            occupied++;
        }
    }

    static HostelRoom findAvailableRoom(HostelRoom[] rooms) {
        for (HostelRoom room : rooms) {
            if (room.occupied < room.beds) {
                return room;
            }
        }
        return null;
    }

    static void safeAllot(HostelRoom[] rooms,
                          SrmStudent student) {
        HostelRoom room = findAvailableRoom(rooms);

        if (room != null) {
            room.allot(student.name);
            student.room = room;
        }
    }
}

class SrmStudent {
    String name;
    String regNo;
    HostelFeeAccount feeAccount;
    HostelRoom room;

    static int totalStudents = 0;

    SrmStudent(String name, String regNo,
               HostelFeeAccount feeAccount) {
        this.name = name;
        this.regNo = regNo;
        this.feeAccount = feeAccount;
        this.room = null;

        totalStudents++;
    }

    String fullStatus() {
        String roomNumber;

        if (room == null) {
            roomNumber = "unallotted";
        } else {
            roomNumber = room.roomNo;
        }

        return name + " | Due: Rs " + feeAccount.getDue()
                + " | Room: " + roomNumber;
    }
}

public class Main {
    public static void main(String[] args) {

        HostelFeeAccount fee1 =
                new HostelFeeAccount("RA001", 200000, 60000);

        HostelFeeAccount fee2 =
                new HostelFeeAccount("RA002", 200000, 20000);

        HostelFeeAccount fee3 =
                new HostelFeeAccount("RA003", 200000, 0);

        SrmStudent ravi =
                new SrmStudent("Ravi", "RA001", fee1);

        SrmStudent anitha =
                new SrmStudent("Anitha", "RA002", fee2);

        SrmStudent karthik =
                new SrmStudent("Karthik", "RA003", fee3);

        HostelRoom[] rooms = {
            new HostelRoom("C-214", 1, 0),
            new HostelRoom("C-507", 1, 0)
        };

        HostelRoom.safeAllot(rooms, ravi);
        HostelRoom.safeAllot(rooms, anitha);

        fee1.pay(-5000);     // rejected
        fee2.pay(0);         // rejected
        fee3.pay(0);         // rejected

        System.out.println(ravi.fullStatus());
        System.out.println(anitha.fullStatus());
        System.out.println(karthik.fullStatus());

        System.out.println("Total students: "
                + SrmStudent.totalStudents);
    }
}