package org.example;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * class that manages customers
 */
public class CustomerManagement {

    //updates customer info
    public static void updateCustomerInformation(Session session, Long customerId, String newName, String newEmail, String newPhone) {

        Customer c = getById(session,customerId);
        c.setName(newName);
        c.setEmail(newEmail);
        c.setPhone(newPhone);
        System.out.println("Customer's information updated successfully.");
    }

    public static Customer getById(Session session,long id) {
        return session.get(Customer.class, id);
    }


    //lists customers

    public static void getCustomers(Session session){
        Query<Customer> getCustomers = session.createNamedQuery("get_customers", Customer.class);
        List<Customer> customers = getCustomers.getResultList();
        for(Customer c: customers){
            System.out.println("Customer ID: " + c.getCustomerID());
            System.out.println("Name: " + c.getName());
            System.out.println("Phone: " + c.getPhone());
            System.out.println("Email: " + c.getEmail());
            System.out.println("------------------------------");
        }
    }

    /**
     *checks if the email is in the database
     */
    public static boolean containsEmail(Session session, String mail){
        Query<Customer> getCustomers = session.createNamedQuery("get_customers", Customer.class);
        List<Customer> customers = getCustomers.getResultList();
        ArrayList<String> mails = new ArrayList<>();
        for(Customer c: customers){
            mails.add(c.getEmail());
        }
        return mails.contains(mail);
    }

    /**
     * gives customer purchase history
     */

    public static void viewCustomerPurchaseHistory(Session session, Long customerId) {

        Query<Sales> getByCustomerID = session.createNamedQuery("get_by_customerID", Sales.class);
        getByCustomerID.setParameter("customerID", customerId);
        List<Sales> resultList = getByCustomerID.getResultList();
        if (resultList == null) {
            System.out.println("No purchase history found for customer with ID: " + customerId);
        } else {
            System.out.println("Purchase history for customer with ID " + customerId + ":");
            for(Sales s: resultList) {
                System.out.println("Date of Sale: " + s.getDateOfSale());
                System.out.println("Book Title: " + s.getBook().getTitle());
                System.out.println("Quantity Sold: " + s.getQuantitySold());
                System.out.println("Total Price: $" + s.getTotalPrice());
                System.out.println("------------------------------");
            }
        }
    }
}


