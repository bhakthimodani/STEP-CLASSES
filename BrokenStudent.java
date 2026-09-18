class BrokenStudent {
    static String name;
    static String regNo;
    static int attendance;

    BrokenStudent(String name, String regNo, int attendance) {
        BrokenStudent.name = name;
        BrokenStudent.regNo = regNo;
        BrokenStudent.attendance = attendance;
    }
}

class SrmStudent {
    String name;
    String regNo;
    int attendance;

    static String university = "SRMIST";
    static int admissionCount = 0;

    SrmStudent(String name, int attendance) {
        this.name = name;
        this.attendance = attendance;

        admissionCount++;
        this.regNo = "RA2311003010"
                + String.format("%02d", admissionCount);
    }

    void printIdCard() {
        System.out.println(name + " | " + regNo);
    }

    static void printTotalAdmissions() {
        System.out.println("Students admitted so far: "
                + admissionCount);
    }
}

public class Main {
    public static void main(String[] args) {

        // BROKEN VERSION
        BrokenStudent s1 =
                new BrokenStudent("Ravi", "RA001", 82);

        BrokenStudent s2 =
                new BrokenStudent("Meera", "RA002", 74);

        System.out.println("Broken version:");
        System.out.println(s1.name);
        System.out.println(s2.name);

        /*
        static name is wrong because all students share one name.
        static regNo is wrong because all students share one regNo.
        static attendance is wrong because all students share one attendance.
        Creating a second object overwrites the first object's data.
        */

        // FIXED VERSION
        SrmStudent student1 =
                new SrmStudent("Ravi", 82);

        SrmStudent student2 =
                new SrmStudent("Meera", 74);

        System.out.println("\nFixed version:");
        student1.printIdCard();
        student2.printIdCard();

        SrmStudent.printTotalAdmissions();
    }
}