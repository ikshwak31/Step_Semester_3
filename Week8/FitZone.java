
interface MembershipPlan {
    double calculateFee();
    String getPlanName();
    int getDurationMonths();
}

class MonthlyPlan implements MembershipPlan {

    public double calculateFee() {
        return 1000;
    }

    public String getPlanName() {
        return "Monthly";
    }

    public int getDurationMonths() {
        return 1;
    }
}

class QuarterlyPlan implements MembershipPlan {

    public double calculateFee() {
        return 3 * 1000 * 0.90;
    }

    public String getPlanName() {
        return "Quarterly";
    }

    public int getDurationMonths() {
        return 3;
    }
}

class AnnualPlan implements MembershipPlan {

    public double calculateFee() {
        return 12 * 1000 * 0.75;
    }

    public String getPlanName() {
        return "Annual";
    }

    public int getDurationMonths() {
        return 12;
    }
}

class Membership {

    private String memberName;
    private MembershipPlan plan;
    private String status;
    private int checkIns = 0;

    public Membership(String memberName, MembershipPlan plan) {
        this.memberName = memberName;
        this.plan = plan;
        this.status = "Active";
    }

    public double getFee() {
        return plan.calculateFee();
    }

    public String getStatus() {
        return status;
    }

    public String buyMembership() {
        return plan.getPlanName() + " membership created for "
                + memberName + ". Fee: ₹"
                + String.format("%.2f", getFee())
                + ". Status: " + status + ".";
    }

    public String checkIn() {
        if (!status.equals("Active")) {
            return "Check-in denied: " + memberName
                    + "'s membership is " + status + ".";
        }

        checkIns++;
        return memberName + " checked in successfully.";
    }

    public String freeze() {
        if (status.equals("Expired")) {
            return "Cannot freeze an Expired membership.";
        }

        if (status.equals("Frozen")) {
            return memberName + "'s membership is already Frozen.";
        }

        status = "Frozen";
        return memberName + "'s membership frozen. Status: "
                + status + ".";
    }

    public String unfreeze() {
        if (status.equals("Expired")) {
            return "Cannot unfreeze an Expired membership.";
        }

        if (status.equals("Active")) {
            return memberName + "'s membership is already Active.";
        }

        status = "Active";
        return memberName + "'s membership unfrozen. Status: "
                + status + ".";
    }

    public String expire() {
        status = "Expired";
        return memberName + "'s membership expired. Status: "
                + status + ".";
    }
}

public class FitZone {

    public static void main(String[] args) {

        Membership asha =
                new Membership("Asha", new QuarterlyPlan());

        Membership ravi =
                new Membership("Ravi", new MonthlyPlan());

        System.out.println(asha.buyMembership());
        System.out.println(ravi.buyMembership());

        System.out.println(asha.checkIn());
        System.out.println(asha.freeze());

        System.out.println(asha.checkIn());

        System.out.println(ravi.expire());
        System.out.println(ravi.freeze());
    }
}
