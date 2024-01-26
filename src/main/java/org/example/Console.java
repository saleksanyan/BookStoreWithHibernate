package org.example;

import org.hibernate.*;

import java.math.BigDecimal;
import java.util.Scanner;

import static org.example.BookManagement.*;
import static org.example.CustomerManagement.viewCustomerPurchaseHistory;
import static org.example.SalesProcessing.*;
import static org.example.SalesReports.*;

/**
 * this class creates user-friendly interface
 */
public class Console {
    private static final String EXIT_CODE = "0";
    private static final String UPDATE_BOOK_CODE = "1";
    private static final String GET_BY_AUTHOR_OR_GENRE_CODE = "2";
    private static final String UPDATE_CUSTOMER_CODE = "3";
    private static final String VIEW_PURCHASE_CODE = "4";
    private static final String PROCESS_SALE_CODE = "5";
    private static final String CALCULATE_REVENUE_CODE = "6";
    private static final String SOLD_REPORT_CODE = "7";
    private static final String REVENUE_REPORT_CODE = "8";
    private static final SessionFactory SESSION_FACTORY =
            HibernateConfig.getSessionFactory();

    public void startApp(){
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = session.beginTransaction();
        Book book = new Book();
        book.setTitle("Sample Book");
        book.setAuthor("John Doe");
        book.setGenre("Fiction");
        book.setPrice(BigDecimal.valueOf(29.99));
        book.setQuantityInStock(100);
        Customer c = new Customer();
        c.setName("Emma");
        c.setPhone("091777777");
        c.setEmail("emma@gmail.com");
        session.persist(book);
        session.persist(c);
        session.flush();

        Scanner scanner = new Scanner(System.in);
        menu();
        String choice = scanner.nextLine().trim();
        while (!choice.equals(EXIT_CODE)) {
            if(choice.equals(UPDATE_BOOK_CODE)){

                updateBookDetails(scanner, session);
            }else if(choice.equals(GET_BY_AUTHOR_OR_GENRE_CODE)){
                System.out.println("Enter the author or genre");
                String authorOrGenre = scanner.nextLine().trim();
                BookManagement.listBooksByGenreOrAuthor(session, authorOrGenre);
            }else if(choice.equals(UPDATE_CUSTOMER_CODE)){
                updateCustomerInformation(scanner, session);
            }else if(choice.equals(VIEW_PURCHASE_CODE)){
                String id = getCustomerId(session, scanner);
                viewCustomerPurchaseHistory(session, Long.parseLong(id));
            }else if(choice.equals(PROCESS_SALE_CODE)){
                processNewSale(scanner, session);
            }else if(choice.equals(CALCULATE_REVENUE_CODE)){
                calculateTotalRevenue(scanner, session);
            }else if(choice.equals(SOLD_REPORT_CODE)){
                generateAllBooksSoldReport(session);
            }else if(choice.equals(REVENUE_REPORT_CODE)){
                generateRevenueReportByGenre(session);
            }
            System.out.println();
            menu();
            choice = scanner.nextLine().trim();
        }
        transaction.commit();
        session.close();
        HibernateConfig.shutdown();
    }

    //gets customers id
    private static String getCustomerId(Session session, Scanner scanner) {
        CustomerManagement.getCustomers(session);
        System.out.println("Enter Customer ID: ");
        String id = scanner.nextLine().trim();
        while(!id.matches("\\d+") || CustomerManagement.getById(session, Long.parseLong(id))==null){
            System.out.println("Please enter valid ID");
            id = scanner.nextLine().trim();
        }
        return id;
    }

    //calculates total revenue
    private static void calculateTotalRevenue(Scanner scanner, Session session) {
        System.out.println();
        System.out.println("Enter genre: ");
        String genre = scanner.nextLine().trim();
        while(BookManagement.containsGenre(session,genre)){
            System.out.println("Please enter valid genre: ");
            genre = scanner.nextLine().trim();
        }
        System.out.println("Total revenue by genre is "+ calculateTotalRevenueByGenre(session,genre));
    }


    //processes new sales
    private static void processNewSale(Scanner scanner, Session session) {

        String bookId = getBookId(scanner, session);
        String customerId = getCustomerId(session,scanner);
        BigDecimal price = BookManagement.getById(session, Long.parseLong(bookId)).getPrice();
        Integer quantity = BookManagement.getById(session, Long.parseLong(bookId)).getQuantityInStock();
        SalesProcessing.processNewSale(session, Long.valueOf(bookId), Long.valueOf(customerId),price,quantity);
    }

    //gets the id of the book
    private static String getBookId(Scanner scanner, Session session) {
        BookManagement.getBooks(session);
        System.out.println("Enter book ID: ");
        String bookId = scanner.nextLine().trim();
        while (!bookId.matches("\\d+") || BookManagement.getById(session, Long.parseLong(bookId)) == null) {
            System.out.println("Please enter valid book ID: ");
            bookId = scanner.nextLine().trim();

        }
        return bookId;
    }

    private static void updateCustomerInformation(Scanner scanner, Session session) {
        CustomerManagement.getCustomers(session);
        String regexMail = "^(.+)@(.+)$";
        String regexPhone = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$";
        String id = getCustomerId(session,scanner);
        System.out.println("Enter customer name:");
        String name = scanner.nextLine().trim();
        System.out.println("Enter customer phone number:");
        String phone = scanner.nextLine().trim();
        while(!phone.matches(regexPhone)){
            System.out.println("Please enter valid phone number");
            phone = scanner.nextLine().trim();
        }
        System.out.println("Enter customer email:");
        String email = scanner.nextLine().trim();
        while(!CustomerManagement.containsEmail(session, email) || !email.matches(regexMail)){
            System.out.println("Please enter valid mail");
            email = scanner.nextLine().trim();
        }
        CustomerManagement.updateCustomerInformation(session,Long.parseLong(id),name,email,phone);
    }

    //updating book details
    private static void updateBookDetails(Scanner scanner, Session session) {
        String id = getBookId(scanner,session);
        System.out.println("Enter title: ");
        String newTitle = scanner.nextLine().trim();
        System.out.println("Enter author: ");
        String newAuthor = scanner.nextLine().trim();
        System.out.println("Enter genre: ");
        String newGenre = scanner.nextLine().trim();
        System.out.println("Enter price: ");
        String newPrice = scanner.nextLine().trim();
        while (true) {
            boolean isNumber = newPrice.matches("\\d+");
            if (isNumber) break;
            System.out.println("Please enter valid price");
            newPrice = scanner.nextLine().trim();
        }
        System.out.println("Enter quantity in stock: ");
        String newQuantityInStock = scanner.next().trim();
        var isValidCount =  newPrice.matches("\\d+");
        while (!isValidCount) {
            System.out.println("Please enter valid price");
            newQuantityInStock = scanner.nextLine().trim();
             isValidCount =  newPrice.matches("^[1-9]\\d*$\n");
        }
        BookManagement.updateBookDetails(session, Long.parseLong(id),
                newTitle, newAuthor,
                newGenre,
                new BigDecimal(newPrice),
                Integer.parseInt(newQuantityInStock));
    }

    //the menu
    private static void menu() {
        System.out.println("1. Update Book Details");
        System.out.println("2. List Books by Genre or Author");
        System.out.println("3. Update Customer Information");
        System.out.println("4. View Customer Purchase History");
        System.out.println("5. Process New Sale");
        System.out.println("6. Calculate Total Revenue by Genre");
        System.out.println("7. Generate All Books Sold Report");
        System.out.println("8. Generate Revenue by Genre Report");
        System.out.println("0. Exit");
        System.out.println();
        System.out.print("Enter your choice: ");

    }

}
