import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    private final Queue<Order> orderQueue = new LinkedList<>();

    private final Stack<String> actionHistory = new Stack<>();

    private final List<Order> processedOrders = new ArrayList<>();
    
    private final Scanner scanner = new Scanner(System.in);

    private final List<Book> bookCatalog = new ArrayList<>();

    public Main() {
        initializeCatalog();
    }

    private void initializeCatalog() {
        bookCatalog.add(new Book("Cinderella", "Brothers Grimm"));
        bookCatalog.add(new Book("Beauty and the Beast", "Jeanne-Marie Leprince de Beaumont"));
        bookCatalog.add(new Book("A Brief History of Time", "Stephen Hawking"));
        bookCatalog.add(new Book("Guns, Germs, and Steel", "Jared Diamond"));
        bookCatalog.add(new Book("The Soul of an Octopus", "Sy Montgomery"));
        bookCatalog.add(new Book("Aladdin and the Magic Lamp", "Various authors (One Thousand and One Nights)"));
        bookCatalog.add(new Book("The Ugly Duckling", "Hans Christian Andersen"));
    }
    
            // SELECTION SORT
    public void selectionSort(List<Book> books) {
        int n = books.size(); 
        for (int i = 0; i < n - 1; i++) {
            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                if (books.get(j).compareTo(books.get(min_idx)) < 0) {
                    min_idx = j;
                }
            }
            Book temp = books.get(min_idx);      
            books.set(min_idx, books.get(i));  
            books.set(i, temp);                  
        }
    }

            // LINEAR SEARCH
    public Order linearSearch(int idToFind) {
        for (Order order : processedOrders) {
            if (order.getOrderId() == idToFind) {
                return order; 
            }
        }
        return null; 
    }

        // Menu
    public void run() {
        System.out.println("WELCOME TO THE ONLINE BOOKSTORE SYSTEM");
        
        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Add new order (Use QUEUE)");
            System.out.println("2. Process order (Use SELECTION SORT)");
            System.out.println("3. Search orders (Use LINEAR SEARCH)");
            System.out.println("4. View action history (Use STACK)");
            System.out.println("5. View all orders");
            System.out.println("0. Exit");
            System.out.print("Please choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addNewOrder();
                    break;
                case 2:
                    processOrder();
                    break;
                case 3:
                    searchOrder();
                    break;
                case 4:
                    viewActionHistory();
                    break;
                case 5:
                    viewAllOrders();
                    break;
                case 0:
                    System.out.println("Thank you for using the system!");
                    return; 
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

        // Function 1 - Add New Order
    private void addNewOrder() {
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter delivery address: ");
        String address = scanner.nextLine();

        Order newOrder = new Order(customerName, address);
        System.out.println("Created order for: " + customerName + ". Please select books:");

        while (true) {
            System.out.println("\n--- Available Book Catalog ---");
            for (int i = 0; i < bookCatalog.size(); i++) {
                System.out.println((i + 1) + ". " + bookCatalog.get(i).getTitle());
            }
            System.out.println("0. Finish this order");
            System.out.print("Choose a book to add (enter number): ");

            int bookChoice = scanner.nextInt();
            scanner.nextLine(); 

            if (bookChoice == 0) {
                System.out.println("Finished selecting books for order #" + newOrder.getOrderId());
                break; 
            } else if (bookChoice > 0 && bookChoice <= bookCatalog.size()) {
                Book selectedBook = bookCatalog.get(bookChoice - 1);
                newOrder.addBook(selectedBook);
                System.out.println("-> Added '" + selectedBook.getTitle() + "' to the order.");
            } else {
                System.out.println("INVALID CHOICE, please choose again.");
            }
        }

        if (newOrder.getBooks().isEmpty()) {
            System.out.println("NOTICE: Empty order, order cancelled.");
            actionHistory.push("Cancelled empty order for customer: " + customerName);
            return; 
        }

        orderQueue.offer(newOrder);
        actionHistory.push("Added order #" + newOrder.getOrderId() + " for customer: " + customerName);
        System.out.println("SUCCESS: Added order #" + newOrder.getOrderId() + " to the queue.");
    }

        // Function 2 - Process Order
    private void processOrder() {
        if (orderQueue.isEmpty()) {
            System.out.println("NOTICE: There are no orders in the queue.");
            return;
        }

        Order orderToProcess = orderQueue.poll();
        System.out.println("PROCESSING: Order #" + orderToProcess.getOrderId());
        System.out.println("Address: " + orderToProcess.getAddress());

        selectionSort(orderToProcess.getBooks());

        System.out.println("... Book list sorting (by Title):");
        orderToProcess.printBooks();

        processedOrders.add(orderToProcess);
        actionHistory.push("Processed and sorted order #" + orderToProcess.getOrderId());
        System.out.println("SUCCESS: Order #" + orderToProcess.getOrderId() + " has been processed.");
    }

    // Function 3 - Search Order
    private void searchOrder() {
        System.out.print("Enter the Order ID you want to find: ");
        int idToFind = scanner.nextInt();
        scanner.nextLine(); 

        Order foundOrder = linearSearch(idToFind);

        if (foundOrder != null) { 
            System.out.println("FOUND: " + foundOrder);
            foundOrder.printBooks();
        } else { 
            System.out.println("NOT FOUND: No order with ID #" + idToFind);
        }

        actionHistory.push("Searched for order ID #" + idToFind);
    }

    // Function 4 - View Action History
    private void viewActionHistory() {
        if (actionHistory.isEmpty()) {
            System.out.println("NOTICE: There is no history yet.");
            return;
        }

        System.out.println("--- ACTION HISTORY ---");
        
        Stack<String> tempStack = new Stack<>();
        tempStack.addAll(actionHistory);
        
        while (!tempStack.isEmpty()) {
            System.out.println(tempStack.pop()); 
        }
        System.out.println("-----------------------------------------------------");
    }

    // Function 5 - View All Orders
    private void viewAllOrders() {
        if (processedOrders.isEmpty()) {
            System.out.println("NOTICE: There are no processed orders yet.");
            return;
        }

        System.out.println("--- ALL PROCESSED ORDERS ---");
        for (Order order : processedOrders) {
            System.out.println(order);
        }
        System.out.println("----------------------------");
    }

    // start main
    public static void main(String[] args) {
        Main app = new Main();
        
        app.run();
    }
}