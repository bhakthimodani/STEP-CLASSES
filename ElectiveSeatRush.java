
import java.util.*;

interface CreditPolicy {
    int getLimit();
    String getType();
}

class RegularPolicy implements CreditPolicy {
    public int getLimit() { return 24; }
    public String getType() { return "Regular"; }
}

class HonorsPolicy implements CreditPolicy {
    public int getLimit() { return 28; }
    public String getType() { return "Honors"; }
}

class ExchangePolicy implements CreditPolicy {
    public int getLimit() { return 20; }
    public String getType() { return "Exchange"; }
}

class Student {
    String name;
    CreditPolicy policy;
    int currentCredits;

    Student(String name, CreditPolicy policy, int credits) {
        this.name = name;
        this.policy = policy;
        this.currentCredits = credits;
    }
}

class Elective {
    String name;
    int credits;
    int capacity;
    List<Student> enrolled = new ArrayList<>();
    Queue<Student> waitlist = new LinkedList<>();

    Elective(String name, int credits, int capacity) {
        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
    }

    boolean isEnrolled(Student s) {
        return enrolled.contains(s);
    }

    boolean isWaiting(Student s) {
        return waitlist.contains(s);
    }

    void enroll(Student s) {
        if (isEnrolled(s) || isWaiting(s)) {
            System.out.println("Enrollment failed: " + s.name
                    + " is already enrolled or waitlisted.");
            return;
        }

        if (s.currentCredits + credits > s.policy.getLimit()) {
            System.out.println("Enrollment failed: " + s.name
                    + " would exceed the " + s.policy.getType()
                    + " credit limit (" + (s.currentCredits + credits)
                    + "/" + s.policy.getLimit() + ").");
            return;
        }

        if (enrolled.size() >= capacity) {
            waitlist.offer(s);
            System.out.println(name + " is full.");
            System.out.println(s.name + " added to waitlist (position "
                    + waitlist.size() + ").");
            return;
        }

        enrolled.add(s);
        s.currentCredits += credits;

        System.out.println(s.name + " enrolled in " + name
                + " (credits: " + s.currentCredits + "/"
                + s.policy.getLimit() + ").");
    }

    void drop(Student s) {
        if (!enrolled.remove(s)) {
            System.out.println("Drop failed: " + s.name
                    + " is not enrolled.");
            return;
        }

        s.currentCredits -= credits;

        System.out.println(s.name + " dropped " + name
                + " (credits: " + s.currentCredits + "/"
                + s.policy.getLimit() + ").");

        promoteNext();
    }

    void promoteNext() {
        Iterator<Student> iterator = waitlist.iterator();

        while (iterator.hasNext()) {
            Student s = iterator.next();

            if (s.currentCredits + credits <= s.policy.getLimit()) {
                iterator.remove();
                enrolled.add(s);
                s.currentCredits += credits;

                System.out.println(s.name
                        + " promoted from waitlist and enrolled in "
                        + name + " (credits: " + s.currentCredits + "/"
                        + s.policy.getLimit() + ").");
                return;
            }

            System.out.println("Promotion skipped: " + s.name
                    + " would exceed the "
                    + s.policy.getType() + " credit limit.");
            iterator.remove();
        }
    }
}

public class ElectiveSeatRush {
    public static void main(String[] args) {
        Elective e = new Elective("Cloud Computing", 4, 2);

        Student asha = new Student("Asha", new RegularPolicy(), 20);
        Student ravi = new Student("Ravi", new HonorsPolicy(), 22);
        Student neha = new Student("Neha", new ExchangePolicy(), 12);
        Student kiran = new Student("Kiran", new RegularPolicy(), 22);

        e.enroll(asha);
        e.enroll(ravi);
        e.enroll(neha);
        e.enroll(kiran);

        e.drop(asha);
    }
}
