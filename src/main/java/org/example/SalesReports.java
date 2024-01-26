package org.example;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.List;

/**
 *  class generates reports about books
 */
public class SalesReports {

    //gives the list of books that are sold
    public static void generateAllBooksSoldReport(Session session) {
        Query<Sales> getBooksSold = session.createNamedQuery("get_books_sold", Sales.class);
        List<Sales> salesList = getBooksSold.getResultList();
        for(Sales s: salesList) {
            System.out.println("Title: " + s.getBook().getTitle() +
                    ", Customer Name: " + s.getCustomer().getName() + ", Date of Sale: " + s.getDateOfSale());
        }
    }


    /**
     * gives revenue report considering genre of the book
     */
    public static void generateRevenueReportByGenre(Session session) {
        Query<Book> getGenre = session.createNamedQuery("get_books_with_diff_genre", Book.class);
        List<Book> bookList = getGenre.getResultList();
        for(Book b: bookList) {
            System.out.println("Total revenue for genre " + b.getGenre() + ": $"
                    + calculateTotalRevenueByGenre(session,b.getGenre()));
        }
    }

    /**
     * calculates total revenue of the book with the given genre
     * @param genre books genre
     * @return total revenue
     */
    public static BigDecimal calculateTotalRevenueByGenre(Session session, String genre) {
        Query<Sales> getRevenue = session.createNamedQuery("get_revenue_by_genre", Sales.class);
        getRevenue.setParameter("genre", genre);
        Sales sales = getRevenue.getSingleResult();
        if(sales == null) {
            System.out.println("No data with the given genre");
            return null;
        }
        return sales.getTotalPrice();
    }
}

