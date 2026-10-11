
public class LateFeeAccount {

    String regNo;
    double totalFee;

    LateFeeAccount(String regNo, double totalFee) {
        this.regNo = regNo;
        this.totalFee = totalFee;
    }

    final double calculateLateFee(int daysLate) {
        return totalFee * daysLate * 0.01;
    }

    final void printSummary(int daysLate) {
        if (daysLate <= 0) {
            System.out.println(regNo + " - On time, no late fee");
        } else {
            System.out.println(regNo + " | Total Fee: Rs "
                    + totalFee + " | Late Fee: Rs "
                    + calculateLateFee(daysLate));
        }
    }

    public static void main(String[] args) {

        String[] regNos = {"RA001", "RA002", "RA003", "RA004"};

        double[] totalFees = {200000, 150000, 180000, 220000};

        int[] daysLate = {10, 0, -2, 5};

        LateFeeAccount[] accounts =
                new LateFeeAccount[regNos.length];

        for (int i = 0; i < regNos.length; i++) {
            accounts[i] = new LateFeeAccount(regNos[i], totalFees[i]);
            accounts[i].printSummary(daysLate[i]);
        }
    }
}
