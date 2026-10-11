
import java.util.ArrayList;
import java.util.List;

class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

interface WashType {
    int getDuration();
    double getCharge();
    String getName();
}

class QuickWash implements WashType {
    public int getDuration() {
        return 30;
    }

    public double getCharge() {
        return 20;
    }

    public String getName() {
        return "Quick";
    }
}

class NormalWash implements WashType {
    public int getDuration() {
        return 45;
    }

    public double getCharge() {
        return 30;
    }

    public String getName() {
        return "Normal";
    }
}

class HeavyWash implements WashType {
    public int getDuration() {
        return 60;
    }

    public double getCharge() {
        return 45;
    }

    public String getName() {
        return "Heavy";
    }
}

class WashCycle {
    private Student student;
    private WashingMachine machine;
    private WashType washType;

    public WashCycle(Student student, WashingMachine machine,
                     WashType washType) {
        this.student = student;
        this.machine = machine;
        this.washType = washType;
    }

    public Student getStudent() {
        return student;
    }

    public WashingMachine getMachine() {
        return machine;
    }

    public WashType getWashType() {
        return washType;
    }
}

class WashingMachine {
    private String machineId;
    private WashCycle currentCycle;

    public WashingMachine(String machineId) {
        this.machineId = machineId;
    }

    public boolean isFree() {
        return currentCycle == null;
    }

    public String getMachineId() {
        return machineId;
    }

    public void startWash(Student student, WashType washType) {
        if (!isFree()) {
            System.out.println(
                    "Machine " + machineId + " is currently busy.");
            return;
        }

        currentCycle = new WashCycle(student, this, washType);

        System.out.printf(
                "%s wash started on %s for %s (%d min). Charge: ₹%.2f.%n",
                washType.getName(),
                machineId,
                student.getName(),
                washType.getDuration(),
                washType.getCharge());
    }

    public void completeWash() {
        if (isFree()) {
            System.out.println(
                    "Machine " + machineId + " has no active cycle.");
            return;
        }

        System.out.println(machineId + " cycle completed.");
        currentCycle = null;
        System.out.println(machineId + " is now free.");
    }
}

public class LaundrySystem {

    public static void main(String[] args) {
        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");

        WashingMachine m1 = new WashingMachine("M1");
        WashingMachine m2 = new WashingMachine("M2");

        m1.startWash(asha, new QuickWash());
        m1.startWash(ravi, new HeavyWash());

        m2.startWash(ravi, new HeavyWash());

        m1.completeWash();

        m1.startWash(neha, new NormalWash());
    }
}
