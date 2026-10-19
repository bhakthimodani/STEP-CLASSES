
import java.util.*;

interface ScoringRule {
    double calculate(double idea, double execution, double presentation);
}

class InnovationTrack implements ScoringRule {
    public double calculate(double idea, double execution, double presentation) {
        return idea * 0.5 + execution * 0.3 + presentation * 0.2;
    }
}

class OpenTrack implements ScoringRule {
    public double calculate(double idea, double execution, double presentation) {
        return (idea + execution + presentation) / 3.0;
    }
}

class Student {
    String name;

    Student(String name) {
        this.name = name;
    }
}

class Team {
    String name;
    List<Student> members;
    ScoringRule rule;
    Project project;

    Team(String name, List<Student> members, ScoringRule rule) {
        this.name = name;
        this.members = members;
        this.rule = rule;
    }
}

class Project {
    String name;
    double score;
    boolean scored = false;

    Project(String name) {
        this.name = name;
    }
}

class Judge {
    void score(Project p, Team t, double idea, double execution,
               double presentation, boolean published) {
        if (published) {
            System.out.println("Rescore rejected: Results have already been published.");
            return;
        }

        if (idea < 0 || idea > 10 || execution < 0 || execution > 10
                || presentation < 0 || presentation > 10) {
            System.out.println("Invalid scores.");
            return;
        }

        p.score = t.rule.calculate(idea, execution, presentation);
        p.scored = true;
        System.out.println("Score recorded for '" + p.name + "'.");
    }
}

class Hackathon {
    List<Team> teams = new ArrayList<>();
    Set<String> registeredStudents = new HashSet<>();
    boolean published = false;

    void register(Team team) {
        if (published || team.members.size() < 2 || team.members.size() > 4) {
            System.out.println("Registration failed: A team must have 2 to 4 members.");
            return;
        }

        for (Student s : team.members) {
            if (registeredStudents.contains(s.name)) {
                System.out.println("Registration failed: Student already belongs to a team.");
                return;
            }
        }

        for (Student s : team.members) {
            registeredStudents.add(s.name);
        }

        teams.add(team);
        System.out.println("Team " + team.name + " registered ("
                + team.members.size() + " members, "
                + (team.rule instanceof InnovationTrack ? "Innovation" : "Open")
                + " track).");
    }

    void submit(Team team, String projectName) {
        if (published || !teams.contains(team)) {
            System.out.println("Submission failed.");
            return;
        }

        if (team.project != null) {
            System.out.println("Submission failed: Team can submit only one project.");
            return;
        }

        team.project = new Project(projectName);
        System.out.println("Project '" + projectName + "' submitted by " + team.name + ".");
    }

    void publish() {
        published = true;
        for (Team t : teams) {
            if (t.project != null && t.project.scored) {
                System.out.printf("Final score: %.2f%n", t.project.score);
            }
        }
        System.out.println("Results published.");
    }
}

public class CodeSprintJudgingDesk {
    public static void main(String[] args) {
        Hackathon h = new Hackathon();

        Team byteBusters = new Team("ByteBusters",
                Arrays.asList(new Student("Asha"), new Student("Ravi"),
                        new Student("Neha")), new InnovationTrack());

        Team soloCoder = new Team("SoloCoder",
                Arrays.asList(new Student("Kiran")), new OpenTrack());

        h.register(byteBusters);
        h.register(soloCoder);

        h.submit(byteBusters, "SmartAttend");

        Judge judge = new Judge();
        judge.score(byteBusters.project, byteBusters, 8, 7, 9, h.published);

        h.publish();

        judge.score(byteBusters.project, byteBusters, 10, 7, 9, h.published);
    }
}
