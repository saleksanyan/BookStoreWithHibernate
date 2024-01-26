package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
//        Book book = new Book("title", "Author", "genre 1",
//                BigDecimal.ONE, 123);
//        Session session = SESSION_FACTORY.openSession();
//        Transaction transaction = session.beginTransaction();
//
//
//        session.persist(book);
//        session.flush();
//
//        Book book2 = BookManagement.getById(session, (123));
//
//        book2.setAuthor("Tumanyan Vardan");
//        session.persist(book2);
//
//        transaction.commit();
//        session.close();
        Console console = new Console();
        console.startApp();
    }
}