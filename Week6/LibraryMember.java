
public class LibraryMember {

    protected String memberId;
    protected int borrowLimit;

    private int booksBorrowed;
    private int[] fineHistory = new int[10];
    private int fineCount = 0;
    private int totalFine = 0;

    // Problem 5: Unique membership numbers
    private static int memberCounter = 100;
    private final String memberNumber;

    // Problem 5: Record borrowed genres
    private String[] borrowedGenres = new String[100];
    private int genreCount = 0;

    public LibraryMember(String memberId, int borrowLimit) {
        if (memberId == null || memberId.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid member ID");
        }

        if (borrowLimit <= 0) {
            throw new IllegalArgumentException(
                    "Borrow limit must be positive");
        }

        this.memberId = memberId;
        this.borrowLimit = borrowLimit;
        this.booksBorrowed = 0;

        memberCounter++;
        this.memberNumber = "LIB-" + memberCounter;
    }

    // Overloaded constructor for Problem 5
    public LibraryMember(int borrowLimit) {
        this("AUTO" + (memberCounter + 1), borrowLimit);
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public static int getMembersEnrolled() {
        return memberCounter - 100;
    }

    public void borrowBook() {
        if (booksBorrowed < borrowLimit) {
            booksBorrowed++;
        }
    }

    // Problem 5: Overloaded borrowing method
    public void borrowBook(String genre) {
        if (booksBorrowed < borrowLimit) {
            if (genreCount < borrowedGenres.length) {
                borrowedGenres[genreCount] = genre;
                genreCount++;
            }

            borrowBook();
        }
    }

    public String[] getBorrowedGenres() {
        String[] copy = new String[genreCount];

        for (int i = 0; i < genreCount; i++) {
            copy[i] = borrowedGenres[i];
        }

        return copy;
    }

    public int getBooksBorrowed() {
        return booksBorrowed;
    }

    protected void chargeFine(int amount) {
        if (fineCount < fineHistory.length) {
            fineHistory[fineCount] = amount;
            fineCount++;
            totalFine += amount;
        }
    }

    public int[] getFineHistory() {
        int[] copy = new int[fineCount];

        for (int i = 0; i < fineCount; i++) {
            copy[i] = fineHistory[i];
        }

        return copy;
    }

    public int getTotalFine() {
        return totalFine;
    }

    // Problem 5: Renewal code validation without regex
    public static boolean isValidRenewalCode(String code) {
        if (code == null || code.length() != 4) {
            return false;
        }

        if (code.charAt(0) != 'R') {
            return false;
        }

        if (!Character.isDigit(code.charAt(1))
                || !Character.isDigit(code.charAt(2))) {
            return false;
        }

        return Character.isUpperCase(code.charAt(3));
    }

    public void displayInfo() {
        System.out.println("General Member | Books Borrowed: "
                + booksBorrowed);
    }

    public static String enrollBatch(String[] memberIds,
                                     int borrowLimit) {
        int enrolled = 0;
        int rejected = 0;

        for (String id : memberIds) {
            try {
                new LibraryMember(id, borrowLimit);
                enrolled++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Enrolled: " + enrolled
                + " | Rejected: " + rejected;
    }

    public static String classifyGeneration(LibraryMember member) {
        if (member instanceof HonorsStudentMember) {
            return "Multilevel descendant (3 generations deep)";
        } else if (member instanceof FacultyMember) {
            return "Hierarchical sibling (independent branch)";
        } else if (member instanceof StudentMember) {
            return "Direct subclass";
        } else {
            return "Base class";
        }
    }

    public static int getTotalBooksBorrowed(
            LibraryMember[] members) {
        int total = 0;

        for (LibraryMember member : members) {
            if (member != null) {
                total += member.getBooksBorrowed();
            }
        }

        return total;
    }

    // Problem 4: Weekly Circulation Report
    public static String batchPrint(LibraryMember[] members) {
        StringBuilder report = new StringBuilder();

        for (LibraryMember member : members) {
            if (member instanceof StudentMember) {
                StudentMember student = (StudentMember) member;

                report.append("Student | Course: ")
                        .append(student.course)
                        .append(" | Books: ")
                        .append(student.getBooksBorrowed())
                        .append(" [Course via downcast: ")
                        .append(student.course)
                        .append("] | ");
            } else if (member != null) {
                report.append("General | Books: ")
                        .append(member.getBooksBorrowed())
                        .append(" | ");
            }
        }

        return report.toString();
    }

    // Problem 5: Null-safe nightly circulation audit
    public static String processNightlyAudit(
            LibraryMember[] members) {

        int processed = 0;
        int nullSkipped = 0;
        int facultyCount = 0;
        int regularCount = 0;

        for (LibraryMember member : members) {
            if (member == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (member instanceof FacultyMember) {
                facultyCount++;
            } else {
                regularCount++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + facultyCount + " faculty | "
                + regularCount + " regular";
    }

    public static void main(String[] args) {

        // Problem 1: Validation and batch enrollment
        try {
            new LibraryMember("LB1", 3);
        } catch (IllegalArgumentException e) {
            System.out.println("LB1 construction rejected");
        }

        StudentMember student =
                new StudentMember("STU10", 3, "CSE");

        student.borrowBook();
        student.borrowBook();

        System.out.println(student.getBooksBorrowed());

        String[] ids = {
                "STU1", "LB1", "STU2", " ", "STU3"
        };

        System.out.println(enrollBatch(ids, 3));

        // Problem 2: Inheritance and polymorphism
        HonorsStudentMember honors =
                new HonorsStudentMember("STU3", 5, "ECE", 2);

        FacultyMember faculty =
                new FacultyMember("FAC1", 5, "Physics");

        honors.borrowBook();

        faculty.borrowBook();
        faculty.borrowBook();
        faculty.borrowBook();

        student.displayInfo();
        honors.displayInfo();
        faculty.displayInfo();

        System.out.println(classifyGeneration(honors));
        System.out.println(classifyGeneration(faculty));

        LibraryMember[] members = {student, honors, faculty};

        System.out.println("Total books borrowed: "
                + getTotalBooksBorrowed(members));

        // Problem 3: Fine history and defensive copying
        student.chargeFine(100);

        System.out.println("Student total fine: "
                + student.getTotalFine());

        int[] history = student.getFineHistory();
        history[0] = 999;

        System.out.println("Fine history after modifying copy: "
                + student.getFineHistory()[0]);

        // Problem 4: Weekly circulation report
        LibraryMember[] reportMembers = {
                new LibraryMember("LB5", 3),
                new StudentMember("STU6", 3, "ECE")
        };

        System.out.println(batchPrint(reportMembers));

        // Problem 5: Unique membership numbers
        LibraryMember member1 = new LibraryMember(3);
        LibraryMember member2 = new LibraryMember(5);

        System.out.println("Member 1 number: "
                + member1.getMemberNumber());

        System.out.println("Member 2 number: "
                + member2.getMemberNumber());

        System.out.println("Total members enrolled: "
                + getMembersEnrolled());

        // Problem 5: Renewal code validation
        System.out.println("R12A valid: "
                + isValidRenewalCode("R12A"));

        System.out.println("R1AA valid: "
                + isValidRenewalCode("R1AA"));

        // Problem 5: Genre-based borrowing
        member1.borrowBook("Science");
        member1.borrowBook("History");

        System.out.println("Books borrowed: "
                + member1.getBooksBorrowed());

        System.out.println("Genres borrowed: ");

        for (String genre : member1.getBorrowedGenres()) {
            System.out.println(genre);
        }

        // Problem 5: Nightly circulation audit
        LibraryMember[] auditMembers = {
                new FacultyMember(5, "Physics"),
                null,
                new LibraryMember(3)
        };

        System.out.println(processNightlyAudit(auditMembers));
    }
}

class StudentMember extends LibraryMember {

    protected String course;

    public StudentMember(String memberId, int borrowLimit,
                         String course) {
        super(memberId, borrowLimit);
        this.course = course;
    }

    @Override
    protected void chargeFine(int amount) {
        super.chargeFine(amount / 2);
    }

    @Override
    public void displayInfo() {
        System.out.println("Student Member | Course: " + course
                + " | Books Borrowed: " + getBooksBorrowed());
    }
}

class HonorsStudentMember extends StudentMember {

    private int bonusLimit;

    public HonorsStudentMember(String memberId, int borrowLimit,
                               String course, int bonusLimit) {
        super(memberId, borrowLimit, course);
        this.bonusLimit = bonusLimit;
    }

    @Override
    public void displayInfo() {
        System.out.println("Honors Student Member | Course: "
                + course + " | Bonus Limit: " + bonusLimit
                + " | Books Borrowed: " + getBooksBorrowed());
    }
}

class FacultyMember extends LibraryMember {

    private String department;

    public FacultyMember(String memberId, int borrowLimit,
                         String department) {
        super(memberId, borrowLimit);
        this.department = department;
    }

    // Overloaded constructor for Problem 5
    public FacultyMember(int borrowLimit, String department) {
        super(borrowLimit);
        this.department = department;
    }

    @Override
    public void displayInfo() {
        System.out.println("Faculty Member | Department: "
                + department + " | Books Borrowed: "
                + getBooksBorrowed());
    }
}
