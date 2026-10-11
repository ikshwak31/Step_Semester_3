
public class GymMember {

    protected String memberId;
    protected int monthlyFee;

    private int sessionsAttended = 0;
    private int[] lateFeeHistory = new int[10];
    private int lateFeeCount = 0;
    private int totalLateFees = 0;

    // Problem 5: Unique membership number
    private static int memberCounter = 2000;
    private final String membershipNumber;

    private int feesPaid = 0;
    private String lastPaymentMode = "Not specified";

    public GymMember(String memberId, int monthlyFee) {
        if (memberId == null || memberId.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid member ID");
        }

        if (monthlyFee <= 0) {
            throw new IllegalArgumentException(
                    "Monthly fee must be positive");
        }

        this.memberId = memberId;
        this.monthlyFee = monthlyFee;

        memberCounter++;
        this.membershipNumber = "GYM-" + memberCounter;
    }

    // Problem 5: Constructor without member ID
    public GymMember(int monthlyFee) {
        this("AUTO" + (memberCounter + 1), monthlyFee);
    }

    public String getMembershipNumber() {
        return membershipNumber;
    }

    public static int getMembersEnrolled() {
        return memberCounter - 2000;
    }

    public void attendSession() {
        sessionsAttended++;
    }

    public int getSessionsAttended() {
        return sessionsAttended;
    }

    // Problem 3: Late-fee recording
    protected void chargeLateFee(int amount) {
        if (lateFeeCount < lateFeeHistory.length) {
            lateFeeHistory[lateFeeCount] = amount;
            lateFeeCount++;
            totalLateFees += amount;
        }
    }

    public int[] getLateFeeHistory() {
        int[] copy = new int[lateFeeCount];

        for (int i = 0; i < lateFeeCount; i++) {
            copy[i] = lateFeeHistory[i];
        }

        return copy;
    }

    public int getTotalLateFees() {
        return totalLateFees;
    }

    // Problem 5: Overloaded payment methods
    public void payFee(int amount) {
        if (amount > 0) {
            feesPaid += amount;
        }
    }

    public void payFee(int amount, String mode) {
        lastPaymentMode = mode;
        payFee(amount);
    }

    public int getFeesPaid() {
        return feesPaid;
    }

    public String getLastPaymentMode() {
        return lastPaymentMode;
    }

    // Problem 5: Referral-code validation without regex
    public static boolean isValidReferralCode(String code) {
        if (code == null || code.length() != 4) {
            return false;
        }

        if (code.charAt(0) != 'G') {
            return false;
        }

        if (!Character.isDigit(code.charAt(1))
                || !Character.isDigit(code.charAt(2))) {
            return false;
        }

        return Character.isUpperCase(code.charAt(3));
    }

    public void displayInfo() {
        System.out.println("Standard Member | Sessions: "
                + sessionsAttended);
    }

    // Problem 1: Batch sign-up
    public static String signUpBatch(
            String[] memberIds, int monthlyFee) {
        int signedUp = 0;
        int rejected = 0;

        for (String id : memberIds) {
            try {
                new GymMember(id, monthlyFee);
                signedUp++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Signed Up: " + signedUp
                + " | Rejected: " + rejected;
    }

    // Problem 2: Inheritance classification
    public static String classifyGeneration(GymMember member) {
        if (member instanceof EliteMember) {
            return "Multilevel descendant (3 generations deep)";
        } else if (member instanceof GroupClassMember) {
            return "Hierarchical sibling (independent branch)";
        } else if (member instanceof PremiumMember) {
            return "Direct subclass";
        } else {
            return "Base class";
        }
    }

    public static int getTotalSessionsAttended(
            GymMember[] members) {
        int total = 0;

        for (GymMember member : members) {
            total += member.getSessionsAttended();
        }

        return total;
    }

    // Problem 4: Monthly attendance announcement
    public static String batchPrint(GymMember[] members) {
        StringBuilder report = new StringBuilder();

        for (GymMember member : members) {
            if (member instanceof PremiumMember) {
                PremiumMember premium = (PremiumMember) member;

                report.append("Premium | Trainer: ")
                        .append(premium.trainerName)
                        .append(" | Sessions: ")
                        .append(member.getSessionsAttended())
                        .append(" [Trainer via downcast: ")
                        .append(premium.trainerName)
                        .append("] | ");
            } else {
                report.append("Standard | Sessions: ")
                        .append(member.getSessionsAttended())
                        .append(" | ");
            }
        }

        return report.toString();
    }

    // Problem 5: Weekly check-in settlement
    public static String processWeeklyCheckIn(
            GymMember[] members) {
        int processed = 0;
        int nullSkipped = 0;
        int groupCount = 0;
        int individualCount = 0;

        for (GymMember member : members) {
            if (member == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (member instanceof GroupClassMember) {
                groupCount++;
            } else {
                individualCount++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + groupCount + " group | "
                + individualCount + " individual";
    }

    public static void main(String[] args) {

        // Problem 1
        try {
            new GymMember("GM1", 1000);
        } catch (IllegalArgumentException e) {
            System.out.println("GM1 construction rejected");
        }

        PremiumMember p =
                new PremiumMember("MEM01", 2000, "Coach Riya");

        p.attendSession();
        p.attendSession();

        System.out.println(p.getSessionsAttended());

        String[] ids = {"MEM1", "GM1", "MEM2", " ", "MEM3"};
        System.out.println(signUpBatch(ids, 1000));

        // Problem 2
        GymMember standard = new GymMember("MEM10", 1000);

        PremiumMember premium =
                new PremiumMember("MEM11", 2000, "Coach Riya");

        EliteMember elite =
                new EliteMember("MEM12", 3000, "Coach Arjun", "L12");

        GroupClassMember group =
                new GroupClassMember("MEM13", 1500, "Zumba");

        premium.attendSession();
        premium.attendSession();
        premium.attendSession();

        elite.attendSession();
        elite.attendSession();

        group.attendSession();
        group.attendSession();
        group.attendSession();
        group.attendSession();

        standard.displayInfo();
        premium.displayInfo();
        elite.displayInfo();
        group.displayInfo();

        System.out.println(classifyGeneration(elite));
        System.out.println(classifyGeneration(group));

        GymMember[] members = {premium, elite, group};

        System.out.println("Total sessions: "
                + getTotalSessionsAttended(members));

        // Problem 3
        PremiumMember feeTest =
                new PremiumMember("MEM20", 2000, "Coach Riya");

        feeTest.chargeLateFee(200);

        System.out.println("Premium late fees: "
                + feeTest.getTotalLateFees());

        int[] history = feeTest.getLateFeeHistory();
        history[0] = 999;

        System.out.println("Fee history after modifying copy: "
                + feeTest.getLateFeeHistory()[0]);

        // Problem 4
        GymMember[] reportMembers = {
                new GymMember("MEM30", 1000),
                new PremiumMember("MEM31", 2000, "Coach Riya")
        };

        System.out.println(batchPrint(reportMembers));

        // Problem 5: Membership numbers
        GymMember m1 = new GymMember(1000);

        System.out.println("Membership number: "
                + m1.getMembershipNumber());

        System.out.println("Members enrolled: "
                + getMembersEnrolled());

        // Problem 5: Referral codes
        System.out.println("G45B valid: "
                + isValidReferralCode("G45B"));

        System.out.println("G4B valid: "
                + isValidReferralCode("G4B"));

        System.out.println("X45B valid: "
                + isValidReferralCode("X45B"));

        // Problem 5: Payments
        m1.payFee(500);
        m1.payFee(500, "UPI");

        System.out.println("Fees paid: " + m1.getFeesPaid());
        System.out.println("Payment mode: "
                + m1.getLastPaymentMode());

        // Problem 5: Weekly check-in
        GymMember[] weeklyMembers = {
                new GroupClassMember(1500, "Zumba"),
                null,
                new GymMember(1000)
        };

        System.out.println(
                processWeeklyCheckIn(weeklyMembers));
    }
}

class PremiumMember extends GymMember {

    protected String trainerName;

    public PremiumMember(
            String memberId, int monthlyFee, String trainerName) {
        super(memberId, monthlyFee);
        this.trainerName = trainerName;
    }

    @Override
    protected void chargeLateFee(int amount) {
        super.chargeLateFee(amount / 2);
    }

    @Override
    public void displayInfo() {
        System.out.println("Premium Member | Trainer: "
                + trainerName + " | Sessions: "
                + getSessionsAttended());
    }
}

class EliteMember extends PremiumMember {

    private String lockerNumber;

    public EliteMember(String memberId, int monthlyFee,
                       String trainerName, String lockerNumber) {
        super(memberId, monthlyFee, trainerName);
        this.lockerNumber = lockerNumber;
    }

    // Problem 5: Overloaded constructor
    public EliteMember(int monthlyFee, String lockerNumber) {
        super("AUTOE" + (getMembersEnrolled() + 1),
                monthlyFee, "Not assigned");
        this.lockerNumber = lockerNumber;
    }

    @Override
    public void displayInfo() {
        System.out.println("Elite Member | Trainer: "
                + trainerName + " | Locker: " + lockerNumber
                + " | Sessions: " + getSessionsAttended());
    }
}

class GroupClassMember extends GymMember {

    private String className;

    public GroupClassMember(
            String memberId, int monthlyFee, String className) {
        super(memberId, monthlyFee);
        this.className = className;
    }

    // Problem 5: Constructor without member ID
    public GroupClassMember(int monthlyFee, String className) {
        super(monthlyFee);
        this.className = className;
    }

    @Override
    public void displayInfo() {
        System.out.println("Group Class Member | Class: "
                + className + " | Sessions: "
                + getSessionsAttended());
    }
}
