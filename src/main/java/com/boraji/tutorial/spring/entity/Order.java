package com.boraji.tutorial.spring.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "address")
    private String address;
    @Column(name = "payment")
    private String payment;

    @OneToOne(cascade = CascadeType.ALL)
    private Code code;

    @OneToOne(cascade = CascadeType.MERGE)
    private Basket basket;

    public Order(long id, String address, String payment, Code code, Basket basket) {
        this.id = id;
        this.address = address;
        this.payment = payment;
        this.code = code;
        this.basket = basket;
    }

    public Order(String address, String payment, Code code, Basket basket) {
        this.address = address;
        this.payment = payment;
        this.code = code;
        this.basket = basket;
    }

    public Order(String address, String payment, Code code) {
        this.address = address;
        this.payment = payment;
        this.code = code;
    }

    public Order() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public double getSum() {
        return basket.getProducts().stream().mapToDouble(Product::getPrice).sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Order order = (Order) o;

        if (getId() != order.getId())
            return false;
        if (getAddress() != null ? !getAddress().equals(order.getAddress()) : order.getAddress() != null)
            return false;
        if (getPayment() != null ? !getPayment().equals(order.getPayment()) : order.getPayment() != null)
            return false;
        if (getCode() != null ? !getCode().equals(order.getCode()) : order.getCode() != null)
            return false;
        return getBasket() != null ? getBasket().equals(order.getBasket()) : order.getBasket() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getPayment() != null ? getPayment().hashCode() : 0);
        result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
        result = 31 * result + (getBasket() != null ? getBasket().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", payment='" + payment + '\'' +
                ", code=" + code +
                ", basket=" + basket +
                '}';
    }
}
