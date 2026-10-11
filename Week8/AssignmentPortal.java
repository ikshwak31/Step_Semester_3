
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

abstract class Assignment {
    protected String title;
    protected int maxMarks;
    protected LocalDate dueDate;

    public Assignment(String title, int maxMarks, LocalDate dueDate) {
        this.title = title;
        this.maxMarks = maxMarks;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    public long getLateDays(LocalDate submissionDate) {
        long days = ChronoUnit.DAYS.between(dueDate, submissionDate);
        return Math.max(0, days);
    }

    public abstract int applyPenalty(int awardedMarks, long lateDays);
}

class CodingAssignment extends Assignment {

    public CodingAssignment(String title, int maxMarks,
                            LocalDate dueDate) {
        super(title, maxMarks, dueDate);
    }

    @Override
    public int applyPenalty(int awardedMarks, long lateDays) {
        double penalty = lateDays * 0.10;
        return Math.max(0, (int) Math.round(
                awardedMarks * (1 - penalty)));
    }
}

class WrittenAssignment extends Assignment {

    public WrittenAssignment(String title, int maxMarks,
                             LocalDate dueDate) {
        super(title, maxMarks, dueDate);
    }

    @Override
    public int applyPenalty(int awardedMarks, long lateDays) {
        double penalty = lateDays * 0.20;
        return Math.max(0, (int) Math.round(
                awardedMarks * (1 - penalty)));
    }
}

class Submission {
    private Student student;
    private Assignment assignment;
    private LocalDate submissionDate;
    private String status;
    private int finalMarks;

    public Submission(Student student, Assignment assignment,
                      LocalDate submissionDate) {
        this.student = student;
        this.assignment = assignment;
        this.submissionDate = submissionDate;
        this.status = "Submitted";
    }

    public String submit() {
        long lateDays = assignment.getLateDays(submissionDate);

        if (lateDays == 0) {
            return student.getName() + "'s submission for '"
                    + assignment.getTitle()
                    + "' received (on time). Status: " + status + ".";
        }

        return student.getName() + "'s submission for '"
                + assignment.getTitle()
                + "' received (" + lateDays
                + " days late). Status: " + status + ".";
    }

    public String grade(int awardedMarks) {
        if (!status.equals("Submitted")) {
            return "Cannot grade: submission is already graded.";
        }

        if (awardedMarks < 0 || awardedMarks > assignment.getMaxMarks()) {
            return "Invalid marks.";
        }

        long lateDays = assignment.getLateDays(submissionDate);
        finalMarks = assignment.applyPenalty(awardedMarks, lateDays);
        status = "Graded";

        String result = student.getName() + " graded: "
                + finalMarks + "/" + assignment.getMaxMarks();

        if (lateDays > 0) {
            long penaltyPercent = lateDays
                    * (assignment instanceof CodingAssignment ? 10 : 20);

            result += " after " + penaltyPercent + "% late penalty";
        }

        return result + ". Status: " + status + ".";
    }

    public String resubmit(LocalDate newDate) {
        if (status.equals("Graded")) {
            return "Cannot resubmit: '" + assignment.getTitle()
                    + "' has already been graded.";
        }

        submissionDate = newDate;
        return student.getName() + " resubmitted '"
                + assignment.getTitle() + "'.";
    }
}

public class AssignmentPortal {

    public static void main(String[] args) {
        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");

        Assignment coding = new CodingAssignment(
                "Linked List Lab", 50,
                LocalDate.of(2026, 3, 10));

        Assignment written = new WrittenAssignment(
                "Design Essay", 50,
                LocalDate.of(2026, 3, 12));

        Submission s1 = new Submission(
                asha, coding, LocalDate.of(2026, 3, 10));

        Submission s2 = new Submission(
                ravi, written, LocalDate.of(2026, 3, 14));

        System.out.println(s1.submit());
        System.out.println(s2.submit());

        System.out.println(s1.grade(45));
        System.out.println(s2.grade(40));

        System.out.println(s1.resubmit(
                LocalDate.of(2026, 3, 11)));
    }
}
