public class ExamHallTicket {

    String studentName;
    int registerNumber;
    String examHall;

    // Constructor
    public ExamHallTicket(String studentName, int registerNumber, String examHall) {
        this.studentName = studentName;
        this.registerNumber = registerNumber;
        this.examHall = examHall;
    }

    // Print ticket details
    public void printTicket() {
        System.out.println(
                "Student: " + studentName
                        + " | Register No: " + registerNumber
                        + " | Exam Hall: " + examHall
        );
    }

    public static void main(String[] args) {

        // Create one shared ticket object
        ExamHallTicket ticket =
                new ExamHallTicket("Nikhil", 101, "Hall A");

        // Two reference variables pointing to the same object
        ExamHallTicket reference1 = ticket;
        ExamHallTicket reference2 = ticket;

        System.out.println("Reference 1:");
        reference1.printTicket();

        System.out.println("Reference 2:");
        reference2.printTicket();

        // Modify using reference1
        reference1.examHall = "Hall B";

        System.out.println("\nAfter changing exam hall using reference1:");

        System.out.println("Reference 1:");
        reference1.printTicket();

        System.out.println("Reference 2:");
        reference2.printTicket();
    }
}