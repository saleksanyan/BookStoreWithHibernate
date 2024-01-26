package org.example;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * this class indicates the sale process
 */
public class SalesProcessing {

    /**
     * builds a module to handle new sales, ensuring stock quantities and sales data are updated correctly.
     * @param bookId book's id
     * @param customerId customer's id
     * @param price book's price
     * @param quantity book's quantity
     */
    public static void processNewSale(Session session, Long bookId, Long customerId, BigDecimal price, Integer quantity) {
        Query<Sales> getByCustomerIDAndBookID = session.createNamedQuery("get_by_customerID_and_bookID", Sales.class);
        getByCustomerIDAndBookID.setParameter("customerID", customerId);
        getByCustomerIDAndBookID.setParameter("bookID", bookId);
        Sales sales = getByCustomerIDAndBookID.getSingleResultOrNull();
        if(sales == null){
            Sales newSales = new Sales(BookManagement.getById(session,bookId), CustomerManagement.getById(session,customerId),
                    LocalDate.now(), quantity, price);
        }else{
            sales.setCustomer(CustomerManagement.getById(session,customerId));
            sales.setBook(BookManagement.getById(session,bookId));
            sales.setTotalPrice(price);
            sales.setQuantitySold(quantity);
            sales.setDateOfSale(LocalDate.now());
        }

    }

}
