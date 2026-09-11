import java.util.Scanner;

public class LibraryISBNNormalizerValidator {

    static String normalizeCode(String raw) {
        String code = raw.trim();

        String publisher = code.substring(0, 3).toUpperCase();
        String rest = code.substring(3);

        return publisher + rest;
    }

    static String validateAndFormat(String code) {

        if (code.length() != 13) {
            return "Invalid: wrong length";
        }

        String publisher = code.substring(0, 3);

        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(publisher.charAt(i))) {
                return "Invalid: publisher code must be 3 letters";
            }
        }

        for (int i = 3; i < 13; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return "Invalid: body must contain only digits";
            }
        }

        String year = code.substring(3, 7);
        String catalog = code.substring(7);

        StringBuilder result = new StringBuilder();
        result.append("[")
                .append(publisher)
                .append(" YEAR: ")
                .append(year)
                .append(" | CATALOG: ")
                .append(catalog)
                .append("]");

        return result.toString();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter ISBN-style code: ");
        String raw = sc.nextLine();

        String normalized = normalizeCode(raw);
        String result = validateAndFormat(normalized);

        System.out.println(result);

        sc.close();
    }
}