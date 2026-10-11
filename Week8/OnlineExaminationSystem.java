
import java.util.*;

class ExamStudent {
    String name;

    ExamStudent(String name) {
        this.name = name;
    }
}

abstract class Question {
    String text;
    int points;

    Question(String text, int points) {
        this.text = text;
        this.points = points;
    }

    abstract boolean checkAnswer(String answer);
}

class MCQQuestion extends Question {
    String correctAnswer;

    MCQQuestion(String text, int points, String correctAnswer) {
        super(text, points);
        this.correctAnswer = correctAnswer;
    }

    boolean checkAnswer(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

class TrueFalseQuestion extends Question {
    boolean correctAnswer;

    TrueFalseQuestion(String text, int points,
                      boolean correctAnswer) {
        super(text, points);
        this.correctAnswer = correctAnswer;
    }

    boolean checkAnswer(String answer) {
        return Boolean.toString(correctAnswer)
                .equalsIgnoreCase(answer);
    }
}

class Examination {
    String title;
    List<Question> questions = new ArrayList<>();

    Examination(String title) {
        this.title = title;
    }

    void addQuestion(Question question) {
        questions.add(question);
    }
}

class Attempt {
    ExamStudent student;
    Examination exam;
    Map<Question, String> answers = new LinkedHashMap<>();
    boolean submitted = false;

    Attempt(ExamStudent student, Examination exam) {
        this.student = student;
        this.exam = exam;
        System.out.println(exam.title + " started by "
                + student.name + ".");
    }

    void answerQuestion(Question question, String answer) {
        if (submitted) {
            System.out.println(
                    "Cannot change answers for a submitted examination."
            );
            return;
        }

        if (!exam.questions.contains(question)) {
            System.out.println("Question does not belong to this exam.");
            return;
        }

        answers.put(question, answer);
        System.out.println("Answer recorded for " + question.text);
    }

    void submit() {
        if (submitted) {
            System.out.println("Examination already submitted.");
            return;
        }

        submitted = true;
        System.out.println(exam.title + " submitted by "
                + student.name + ".");

        int totalScore = 0;
        int maximumScore = 0;

        for (Question question : exam.questions) {
            maximumScore += question.points;
            String answer = answers.get(question);

            if (answer != null && question.checkAnswer(answer)) {
                totalScore += question.points;
                System.out.println(question.text + ": Correct ("
                        + question.points + " points)");
            } else {
                System.out.println(question.text + ": Incorrect (0 points)");
            }
        }

        System.out.println("Total score: " + totalScore
                + "/" + maximumScore);
    }
}

public class OnlineExaminationSystem {
    public static void main(String[] args) {
        ExamStudent student = new ExamStudent("Student 1");

        Examination exam = new Examination("Exam A");

        Question q1 = new MCQQuestion(
                "Question 1", 5, "C"
        );

        Question q2 = new TrueFalseQuestion(
                "Question 2", 5, false
        );

        exam.addQuestion(q1);
        exam.addQuestion(q2);

        Attempt attempt = new Attempt(student, exam);

        attempt.answerQuestion(q1, "C");
        attempt.answerQuestion(q2, "True");

        attempt.submit();

        attempt.answerQuestion(q1, "B");
    }
}
