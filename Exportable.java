// Exportable.java
interface Exportable {
    String exportData();
}

// ExportCounter.java
class ExportCounter {
    static int totalExports = 0;
}

// ReportGenerator.java
class ReportGenerator implements Exportable {
    private String reportName;

    public ReportGenerator(String reportName) {
        this.reportName = reportName;
    }

    public String exportData() {
        ExportCounter.totalExports++;
        return "Exported report: " + reportName;
    }

    public static int getTotalExports() {
        return ExportCounter.totalExports;
    }

    public static void exportAll(Exportable[] items) {
        for (Exportable item : items) {
            System.out.println(item.exportData());
        }
    }
}

// UserProfile.java
class UserProfile implements Exportable {
    private String username;

    public UserProfile(String username) {
        this.username = username;
    }

    public String exportData() {
        ExportCounter.totalExports++;
        return "Exported profile: " + username;
    }
}

// Main.java
class Main {
    public static void main(String[] args) {
        ReportGenerator r = new ReportGenerator("Sales Q1");
        UserProfile u = new UserProfile("jane_doe");

        Exportable[] items = {r, u};

        ReportGenerator.exportAll(items);

        System.out.println(ReportGenerator.getTotalExports());
    }
}