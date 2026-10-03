public class LoanReceipt {

    private final String memberId;
    private final String[] bookIds;

    public LoanReceipt(String memberId, String[] bookIds) {
        this.memberId = memberId;
        this.bookIds = bookIds.clone();
    }

    public String getMemberId() {
        return memberId;
    }

    public String[] getBookIds() {
        return bookIds.clone();
    }

    public LoanReceipt withCorrectedBookId(int index, String newId) {

        String[] correctedIds = bookIds.clone();
        correctedIds[index] = newId;

        return new LoanReceipt(memberId, correctedIds);
    }

    public static void main(String[] args) {

        LoanReceipt r = new LoanReceipt(
                "LIB-8841",
                new String[]{"BK-100", "BK-101"}
        );

        // Test defensive copying
        String[] ids = r.getBookIds();
        ids[0] = "HACKED";

        System.out.println(r.getBookIds()[0]);

        // Test withCorrectedBookId()
        LoanReceipt corrected = r.withCorrectedBookId(1, "BK-102");

        System.out.println(java.util.Arrays.toString(r.getBookIds()));
        System.out.println(java.util.Arrays.toString(corrected.getBookIds()));
    }
}