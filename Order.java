import java.util.ArrayList;
import java.util.List;

public class Order {

    private static int idCounter = 0; 
    private final  int orderId;
    private final String customerName;
    private final String address;
    private final List<Book> books; 

    // Constructor
    public Order(String customerName, String address) {
        this.orderId = ++idCounter; 
        this.customerName = customerName;
        this.address = address;
       
        this.books = new ArrayList<>(); 
    }

   
    public void addBook(Book book) {
        this.books.add(book);
    }


    public int getOrderId() {
        return this.orderId;
    }

    public String getAddress() {
        return this.address;
    }

    public List<Book> getBooks() {
        return this.books;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    // print order summary
    @Override
    public String toString() {
        return "Order #" + this.orderId +
                ", Address: " + this.address +
               " (Customer: " + this.customerName +
               ", Books: " + this.books.size() + ")";
    }

    // print detailed books in the order
    public void printBooks() {
        System.out.println("--- Books in Order #" + this.orderId + " ---");
        for (Book book : this.books) {
            System.out.println("    " + book);
        }
        System.out.println("---------------------------------");
    }
}