package org.example;


import jakarta.persistence.*;

/**
 * customer table
 */
@Entity
@Table(name = "customers")
@NamedQueries({
        //gives customers
        @NamedQuery(
                name = "get_customers",
                query = "SELECT c FROM Customer c "
        )
})
public class Customer {


    @Id
    @Column(name = "customer_id", nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerID;

    @Column(name = "name", nullable=false)
    private String name;

    @Column(name = "email", unique=true)
    private String email;

    @Column(name = "phone", nullable=false)
    private String phone;


    public Customer(Long customerID, String name, String email, String phone) {
        this.customerID = customerID;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Customer() {

    }


    public Long getCustomerID() {
        return customerID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerID=" + customerID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
