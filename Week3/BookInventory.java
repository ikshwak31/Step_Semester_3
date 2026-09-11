public class BookInventory {

    String title;
    String author;
    int copiesAvailable;

    // Constructor
    BookInventory(String title, String author, int copiesAvailable) {
        this.title = title;
        this.author = author;
        this.copiesAvailable = copiesAvailable;
    }

    // Instance method
    void printEntry() {
        System.out.println(title + " by " + author + " - "
                + copiesAvailable + " copies available");
    }

    public static void main(String[] args) {

        // Create four BookInventory objects
        BookInventory book1 = new BookInventory(
                "Clean Code", "Robert C. Martin", 3);

        BookInventory book2 = new BookInventory(
                "Effective Java", "Joshua Bloch", 5);

        BookInventory book3 = new BookInventory(
                "Refactoring", "Martin Fowler", 0);

        BookInventory book4 = new BookInventory(
                "Design Patterns", "GoF", 2);

        // Store objects in an array
        BookInventory[] books = {book1, book2, book3, book4};

        // Print each book
        for (BookInventory book : books) {
            book.printEntry();
        }
    }
}