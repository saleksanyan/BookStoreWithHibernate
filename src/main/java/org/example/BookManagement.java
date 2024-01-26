package org.example;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.List;

/**
 * class that manages books
 */
public class BookManagement {

    //updates price

    public static void updatePrice(Session session, long bookID, BigDecimal price){
        getById(session,bookID).setPrice(price);
    }

    //updates title

    public static void updateTitle(Session session, long bookID, String title){
        getById( session,bookID).setTitle(title);
    }

    //updates genre
    public static void updateGenre(Session session,long bookID, String genre){
        getById(session,bookID).setGenre(genre);
    }

    //checks if given genre is in the database
    public static boolean containsGenre(Session session, String genre){
        Query<Book> getGenre = session.createNamedQuery("get_genre", Book.class);
        return getGenre.getResultList().contains(genre);
    }

    //gives book that have the given id
    public static Book getById(Session session,long id) {
        return session.get(Book.class, id);
    }

    /**
     * lists books considering their genre or author
     * @param session current session
     * @param genreOrAuthor the info that the user gives
     */

    public static void listBooksByGenreOrAuthor(Session session, String genreOrAuthor) {
        Query<Book> getByGenreOrAuthor = session.createNamedQuery("get_by_genre_or_author", Book.class);
        getByGenreOrAuthor.setParameter("g", genreOrAuthor);
        getByGenreOrAuthor.setParameter("a", genreOrAuthor);

        for (Book book : getByGenreOrAuthor.getResultList()) {
            System.out.println("------------------------------");
            System.out.println("BookID: " + book.getBookId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Genre: " + book.getGenre());
            System.out.println("Price: $" + book.getPrice());
            System.out.println("Quantity in Stock: " + book.getQuantityInStock());
            System.out.println("------------------------------");

        }
    }

    //updates books data in the database
    public static void updateBookDetails(Session session,Long bookId, String newTitle, String newAuthor,
                                         String newGenre,
                                         BigDecimal newPrice,
                                         Integer newQuantityInStock) {
        Book book = getById(session,bookId);
        book.setGenre(newGenre);
        book.setTitle(newTitle);
        book.setAuthor(newAuthor);
        book.setPrice(newPrice);
        book.setQuantityInStock(newQuantityInStock);
        System.out.println("Book's information updated successfully.");
    }


    //lists books

    public static void getBooks(Session session){
        Query<Book> getBooks = session.createNamedQuery("get_books", Book.class);
        List<Book> books = getBooks.getResultList();
        for(Book b: books){
            System.out.println("Book ID: " + b.getBookId());
            System.out.println("Title: " + b.getTitle());
            System.out.println("Genre: " + b.getGenre());
            System.out.println("Author: " + b.getAuthor());
            System.out.println("Price: " + b.getPrice());
            System.out.println("Quantity: " + b.getQuantityInStock());
            System.out.println("------------------------------");
        }
    }

}
