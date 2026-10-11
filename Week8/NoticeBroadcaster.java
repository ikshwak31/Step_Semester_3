
import java.util.*;

class NoticeStudent {
    String name;
    String department;
    List<NotificationChannel> channels;

    NoticeStudent(String name, String department,
                  List<NotificationChannel> channels) {
        this.name = name;
        this.department = department;
        this.channels = channels;
    }
}

class CollegeNotice {
    String title;
    Set<String> departments;

    CollegeNotice(String title, Set<String> departments) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Notice title cannot be empty."
            );
        }

        if (departments == null || departments.isEmpty()) {
            throw new IllegalArgumentException(
                    "At least one target department is required."
            );
        }

        this.title = title;
        this.departments = new LinkedHashSet<>(departments);
    }
}

interface NotificationChannel {
    void send(NoticeStudent student, CollegeNotice notice);
}

class EmailNotification implements NotificationChannel {
    public void send(NoticeStudent student, CollegeNotice notice) {
        System.out.println("[Email -> " + student.name + "] "
                + notice.title);
    }
}

class SmsNotification implements NotificationChannel {
    public void send(NoticeStudent student, CollegeNotice notice) {
        System.out.println("[SMS -> " + student.name + "] "
                + notice.title);
    }
}

class AppNotification implements NotificationChannel {
    public void send(NoticeStudent student, CollegeNotice notice) {
        System.out.println("[App -> " + student.name + "] "
                + notice.title);
    }
}

class NoticeBoard {
    List<NoticeStudent> students = new ArrayList<>();

    void addStudent(NoticeStudent student) {
        students.add(student);
    }

    void postNotice(CollegeNotice notice) {
        System.out.println("\nNotice '" + notice.title
                + "' posted to "
                + String.join(", ", notice.departments)
                + ".");

        for (NoticeStudent student : students) {
            if (notice.departments.contains(student.department)) {
                for (NotificationChannel channel : student.channels) {
                    channel.send(student, notice);
                }
            }
        }
    }
}

public class NoticeBroadcaster {
    public static void main(String[] args) {
        NoticeBoard board = new NoticeBoard();

        NoticeStudent asha = new NoticeStudent(
                "Asha",
                "CSE",
                Arrays.asList(
                        new EmailNotification(),
                        new AppNotification()
                )
        );

        NoticeStudent ravi = new NoticeStudent(
                "Ravi",
                "ECE",
                Arrays.asList(new SmsNotification())
        );

        board.addStudent(asha);
        board.addStudent(ravi);

        CollegeNotice notice1 = new CollegeNotice(
                "Lab Closed Tomorrow",
                new LinkedHashSet<>(Arrays.asList("CSE"))
        );

        board.postNotice(notice1);

        CollegeNotice notice2 = new CollegeNotice(
                "Fee Deadline Extended",
                new LinkedHashSet<>(Arrays.asList("CSE", "ECE"))
        );

        board.postNotice(notice2);

        try {
            CollegeNotice notice3 = new CollegeNotice(
                    "Sports Day",
                    new LinkedHashSet<>()
            );

            board.postNotice(notice3);
        } catch (IllegalArgumentException e) {
            System.out.println("\nCannot post notice: "
                    + e.getMessage());
        }
    }
}
