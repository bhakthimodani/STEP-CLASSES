
interface Dimmable {
    void dim(int level);
}

interface Schedulable {
    void schedule(String time);
}

interface EnergyMonitor {
    void showEnergy();
}

abstract class Device {
    String id;
    boolean isOn = false;

    Device(String id) {
        this.id = id;
    }

    void turnOn() {
        isOn = true;
        System.out.println(id + " is ON");
    }
}

class Light extends Device implements Dimmable, Schedulable {
    Light(String id) {
        super(id);
    }

    public void dim(int level) {
        System.out.println(id + " dimmed to " + level);
    }

    public void schedule(String time) {
        System.out.println(id + " scheduled " + time);
    }
}

class Fan extends Device implements Schedulable {
    Fan(String id) {
        super(id);
    }

    public void schedule(String time) {
        System.out.println(id + " scheduled " + time);
    }
}

class Plug extends Device implements EnergyMonitor {
    int energy;

    Plug(String id, int energy) {
        super(id);
        this.energy = energy;
    }

    public void showEnergy() {
        System.out.println(id + " energy " + energy + " kWh");
    }
}

public class SmartHomePlatform {
    public static void main(String[] args) {
        Light l1 = new Light("L1");
        Fan f1 = new Fan("F1");
        Plug p1 = new Plug("P1", 12);

        l1.turnOn();
        l1.dim(40);

        if (f1 instanceof Dimmable) {
            f1.dim(30);
        } else {
            System.out.println("F1 rejected: DIM unsupported");
        }

        f1.schedule("22:00");
        p1.showEnergy();

        if (l1 instanceof EnergyMonitor) {
            l1.showEnergy();
        } else {
            System.out.println(
                "L1 rejected: ENERGY unsupported"
            );
        }
    }
}
