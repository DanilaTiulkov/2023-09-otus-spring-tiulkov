package org.example.model.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;

public class OrderNewDto {

    @NotEmpty
    private String clientName;

    @NotEmpty
    private String phone;

    @NotEmpty
    private String email;

    @NotEmpty
    private String address;

    @NotEmpty
    private String[] cookies;

    @Min(1)
    private Integer price;

    private Date orderDate;


    public OrderNewDto(String clientName, String phone, String email, String address,
                       String[] cookies, Integer price, Date orderDate) {
        this.clientName = clientName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.cookies = cookies;
        this.price = price;
        this.orderDate = orderDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String[] getCookies() {
        return cookies;
    }

    public void setCookies(String[] cookies) {
        this.cookies = cookies;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderNewDto that = (OrderNewDto) o;
        return price == that.price && Objects.equals(clientName, that.clientName) && Objects.equals(phone, that.phone)
                && Objects.equals(email, that.email) && Objects.equals(address, that.address)
                && Objects.deepEquals(cookies, that.cookies) && Objects.equals(orderDate, that.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientName, phone, email, address, Arrays.hashCode(cookies), price, orderDate);
    }

    @Override
    public String toString() {
        return "OrderNewDto{" +
                "clientName='" + clientName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", cookies=" + Arrays.toString(cookies) +
                ", price=" + price +
                ", orderDate=" + orderDate +
                '}';
    }
}
