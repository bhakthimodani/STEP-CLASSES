
public class StudentMarksGrid {
    public static void main(String[] args) {
        String[] students = {"Anu", "Ravi", "Meena"};
        String[] subjects = {"Math", "Sci", "Eng"};

        int[][] marks = {
            {80, 90, 70},
            {60, 85, 0},
            {95, 75, 88}
        };

        for (int i = 0; i < students.length; i++) {
            int total = 0;

            for (int j = 0; j < subjects.length; j++) {
                total += marks[i][j];
            }

            System.out.println(
                "Total " + students[i] + " " + total
            );
        }

        for (int j = 0; j < subjects.length; j++) {
            int max = -1;
            int topper = 0;

            for (int i = 0; i < students.length; i++) {
                if (marks[i][j] > max) {
                    max = marks[i][j];
                    topper = i;
                }
            }

            System.out.println(
                "Topper " + subjects[j] + " " +
                students[topper] + " " + max
            );
        }
    }
}
