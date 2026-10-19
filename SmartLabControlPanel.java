
import java.util.*;

interface Capability {
    String getName();
    boolean apply(int value, String deviceName);
}

class PowerCapability implements Capability {
    boolean on = false;

    public String getName() {
        return "Power";
    }

    public boolean apply(int value, String deviceName) {
        if (value != 0 && value != 1) {
            System.out.println("Rejected: Power must be 0 or 1.");
            return false;
        }

        on = value == 1;
        System.out.println(deviceName + ": " + (on ? "ON" : "OFF") + ".");
        return true;
    }
}

class BrightnessCapability implements Capability {
    int brightness;

    public String getName() {
        return "Brightness";
    }

    public boolean apply(int value, String deviceName) {
        if (value < 0 || value > 100) {
            System.out.println("Rejected: Brightness must be between 0 and 100%.");
            return false;
        }

        brightness = value;
        System.out.println(deviceName + ": brightness set to " + value + "%.");
        return true;
    }
}

class TemperatureCapability implements Capability {
    int temperature;

    public String getName() {
        return "Temperature";
    }

    public boolean apply(int value, String deviceName) {
        if (value < 16 || value > 30) {
            System.out.println("Rejected: " + deviceName
                    + " temperature must be between 16°C and 30°C.");
            return false;
        }

        temperature = value;
        System.out.println(deviceName + ": temperature set to " + value + "°C.");
        return true;
    }
}

class Device {
    String name;
    Map<String, Capability> capabilities = new LinkedHashMap<>();

    Device(String name) {
        this.name = name;
    }

    void addCapability(Capability capability) {
        capabilities.put(capability.getName(), capability);
    }

    void apply(String capabilityName, int value) {
        Capability c = capabilities.get(capabilityName);

        if (c != null) {
            c.apply(value, name);
        }
    }
}

class SceneStep {
    String capabilityName;
    int value;

    SceneStep(String capabilityName, int value) {
        this.capabilityName = capabilityName;
        this.value = value;
    }
}

class Scene {
    String name;
    List<SceneStep> steps = new ArrayList<>();

    Scene(String name) {
        this.name = name;
    }

    void addStep(String capability, int value) {
        steps.add(new SceneStep(capability, value));
    }

    void execute(List<Device> devices) {
        System.out.println("Scene '" + name + "' started.");
        int actions = 0;

        for (SceneStep step : steps) {
            for (Device d : devices) {
                if (d.capabilities.containsKey(step.capabilityName)) {
                    d.apply(step.capabilityName, step.value);
                    actions++;
                }
            }
        }

        System.out.println("Scene '" + name + "' completed: "
                + actions + " actions applied.");
    }
}

public class SmartLabControlPanel {
    public static void main(String[] args) {
        Device ac = new Device("Lab AC");
        ac.addCapability(new PowerCapability());
        ac.addCapability(new TemperatureCapability());

        Device lights = new Device("Ceiling Lights");
        lights.addCapability(new PowerCapability());
        lights.addCapability(new BrightnessCapability());

        Device projector = new Device("Projector");
        projector.addCapability(new PowerCapability());

        List<Device> devices = Arrays.asList(ac, lights, projector);

        Scene lecture = new Scene("Lecture Mode");
        lecture.addStep("Power", 1);
        lecture.addStep("Brightness", 40);
        lecture.addStep("Temperature", 24);

        lecture.execute(devices);

        ac.apply("Temperature", 12);

        projector.addCapability(new BrightnessCapability());
        System.out.println("Projector: Brightness capability added.");
        projector.apply("Brightness", 70);
    }
}
