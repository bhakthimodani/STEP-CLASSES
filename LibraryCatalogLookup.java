
import java.util.Scanner;

public class LibraryCatalogLookup {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] isbn = {
            "0001112223",
            "0002223334",
            "0003334445",
            "0004445556",
            "0005556667"
        };

        String[] title = {
            "Introduction to Algebra",
            "Beginning Python",
            "Classic Mythology",
            "Data and Society",
            "European History"
        };

        String target = sc.next();

        int left = 0;
        int right = isbn.length - 1;
        boolean found = false;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = isbn[mid].compareTo(target);

            if (comparison == 0) {
                System.out.println(title[mid]);
                found = true;
                break;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        if (!found) {
            System.out.println("Not Found");
        }

        sc.close();
    }
}
