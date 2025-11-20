public class Book implements Comparable<Book> {
    //data book
    private final String title;
    private final String author;

    // Constructor
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    //get title method
    public String getTitle() {
        return this.title;
    }

    // print book info
    @Override
    public String toString() 
    {
        return " Title: " + this.title + " Author: " + this.author;
    }

    @Override
    public int compareTo(Book other) {
        return this.title.compareTo(other.title);
    }
}