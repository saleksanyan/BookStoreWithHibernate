package org.example;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * book table
 */
@Entity
@Table(name = "books")
@NamedQueries({
        //query that gives books considering its genre or author
        @NamedQuery(
                name = "get_by_genre_or_author",
                query = "SELECT b FROM Book b WHERE b.genre = :g OR b.author = :a"
        ),
        //query that gives  genres
        @NamedQuery(
                name = "get_genre",
                query = "SELECT DISTINCT genre FROM Book"
        ),
        //query that gives books that have different genres
        @NamedQuery(
                name = "get_books_with_diff_genre",
                query = "SELECT b FROM Book b WHERE genre IN ( SELECT DISTINCT genre FROM Book GROUP BY genre HAVING COUNT(DISTINCT bookID) > 1)"
        ),
        //query that gives books
        @NamedQuery(
                name = "get_books",
                query = "SELECT b FROM Book b"
        )

})
public class Book {
    @Id
    @Column(name = "book_id", nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookID;

    @Column(name = "title", nullable=false)
    private String title;

    @Column(name = "author", nullable=false)

    private String author;

    @Column(name = "genre", nullable=false)

    private String genre;

    @Column(name = "price", nullable=false)

    private BigDecimal price;

    @Column(name = "quantity_in_stock", nullable=false)

    private Integer quantityInStock;

    public Book() {
    }

    public Book(String title, String author, String genre, BigDecimal price, Integer quantityInStock) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public Long getBookId() {
        return bookID;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public BigDecimal getPrice() {
        return price;
    }


    //sets the price after checking the input
    public void setPrice(BigDecimal price) {
        if (price != null && price.compareTo(BigDecimal.valueOf(0)) > 0) {
            this.price = price;
        } else {
            throw new IllegalArgumentException("price must be a non-negative value.");
        }
    }


    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    //sets the quantity after checking the input
    public void setQuantityInStock(Integer quantityInStock) {
        if (quantityInStock != null && quantityInStock >= 0) {
            this.quantityInStock = quantityInStock;
        } else {
            throw new IllegalArgumentException("Quantity in stock must be a non-negative value.");
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookID=" + bookID +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", price=" + price +
                ", quantityInStock=" + quantityInStock +
                '}';
    }
}
