class SrmStudent {
    String name;
    String regNo;
    int attendance;

    SrmStudent(String name, String regNo, int attendance) {
        this.name = name;
        this.regNo = regNo;
        this.attendance = attendance;
    }

    void addAttendanceUpdate(int newAttendance) {
        attendance = newAttendance;
    }

    boolean isEligible() {
        return attendance >= 75;
    }

    // static because classAverage works on the whole array, not one student.
    static double classAverage(SrmStudent[] students) {
        int sum = 0;

        for (SrmStudent s : students) {
            sum += s.attendance;
        }

        return (double) sum / students.length;
    }

    public static void main(String[] args) {
        SrmStudent[] students = {
            new SrmStudent("Ravi", "RA001", 82),
            new SrmStudent("Anitha", "RA002", 68),
            new SrmStudent("Karthik", "RA003", 91),
            new SrmStudent("Meera", "RA004", 74),
            new SrmStudent("Suresh", "RA005", 60)
        };

        for (SrmStudent s : students) {
            System.out.println(s.name + " - " + s.attendance + "% - "
                    + (s.isEligible() ? "Eligible" : "Detained"));
        }

        System.out.println("Class average: "
                + classAverage(students) + "%");
    }
}