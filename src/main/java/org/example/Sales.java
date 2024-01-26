package org.example;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * sales table
 */
@Entity
@Table(name = "sales")
@NamedQueries({
        //gives the sales instance considering the customer ID
        @NamedQuery(
                name = "get_by_customerID",
                query = "SELECT s FROM Sales s WHERE s.customer.customerID = :customerID"
        ),
        //gives the sales instance considering the customer ID and book ID
        @NamedQuery(
                name = "get_by_customerID_and_bookID",
                query = "SELECT s FROM Sales s WHERE s.customer.customerID = :customerID and s.book.bookID = :bookID"
        ),

        //gives the books that are sold
        @NamedQuery(
                name = "get_books_sold",
                query = "SELECT s FROM Sales s"
        ),

        //gives revenue considering books genre
        @NamedQuery(
                name = "get_revenue_by_genre",
                query = "SELECT SUM(s.totalPrice) FROM Sales s JOIN s.book b WHERE b.genre = :genre"
        )
})

public class Sales {
    @Id
    @Column(name = "sale_id", nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long saleID;

    @ManyToOne
    @JoinColumn(name = "bookID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "customerID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer customer;

    @Column(name="date_of_sale")
    private LocalDate dateOfSale;

    @Column(name = "quantity_sold", nullable=false)

    private Integer quantitySold;


    @Column(name = "total_price", nullable=false)

        private BigDecimal totalPrice;

    public Sales() {

    }

    public Sales(Book book, Customer customer, LocalDate dateOfSale, Integer quantitySold, BigDecimal totalPrice) {
        this.book = book;
        this.customer = customer;
        this.dateOfSale = dateOfSale;
        this.quantitySold = quantitySold;
        this.totalPrice = totalPrice;
    }


    public Long getSaleID() {
        return saleID;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(LocalDate dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public Integer getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(Integer quantitySold) {
        if (quantitySold != null && quantitySold >= 0) {
            this.quantitySold = quantitySold;
        } else {
            throw new IllegalArgumentException("Quantity sold must be a non-negative value.");
        }
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        if (totalPrice != null && totalPrice.compareTo(BigDecimal.valueOf(0)) > 0) {
            this.totalPrice = totalPrice;
        } else {
            throw new IllegalArgumentException("total price must be a non-negative value.");
        }
    }

    @Override
    public String toString() {
        return "Sales{" +
                "saleID=" + saleID +
                ", book=" + book +
                ", customer=" + customer +
                ", dateOfSale=" + dateOfSale +
                ", quantitySold=" + quantitySold +
                ", totalPrice=" + totalPrice +
                '}';
    }
}