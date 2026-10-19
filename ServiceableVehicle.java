// ServiceableVehicle.java
abstract class ServiceableVehicle {
    private double mileage;

    public abstract String performMaintenance();

    public double getMileage() {
        return mileage;
    }

    public void addMileage(double km) {
        if (km < 0) {
            throw new IllegalArgumentException(
                "Distance cannot be negative"
            );
        }
        mileage += km;
    }
}

// Insurable.java
interface Insurable {
    String getInsuranceInfo();
}

// Forklift.java
class Forklift extends ServiceableVehicle
        implements Insurable {
    protected String assetTag;

    public Forklift(String assetTag) {
        this.assetTag = assetTag;
    }

    public String performMaintenance() {
        return "Forklift " + assetTag
            + ": hydraulic and fork inspection complete";
    }

    public String getInsuranceInfo() {
        return "Insured under fleet policy Asset " + assetTag;
    }
}

// HeavyDutyForklift.java
class HeavyDutyForklift extends Forklift {
    public HeavyDutyForklift(String assetTag) {
        super(assetTag);
    }

    public String performMaintenance() {
        return super.performMaintenance()
            + " | high-pressure hydraulic check complete";
    }
}

// Main.java
class Main {
    public static String getInsuranceIfApplicable(
            ServiceableVehicle v) {
        if (v instanceof Insurable) {
            Insurable i = (Insurable) v;
            return i.getInsuranceInfo();
        }
        return "No insurance record exists";
    }

    public static void main(String[] args) {
        Forklift f = new Forklift("FL-22");
        f.addMileage(120);

        System.out.println(f.getMileage());
        System.out.println(f.performMaintenance());
        System.out.println(getInsuranceIfApplicable(f));

        HeavyDutyForklift hd =
            new HeavyDutyForklift("HD-9");

        System.out.println(hd.performMaintenance());
    }
}