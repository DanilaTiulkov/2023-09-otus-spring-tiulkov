package org.example.model.dto;

import java.sql.Date;
import java.util.Objects;

public class OrderDto {

    private long orderId;

    private String clientName;

    private String phone;

    private String email;

    private String address;

    private Date orderDate;

    public OrderDto(long orderId, String clientName, String phone, String email, String address, Date orderDate) {
        this.orderId = orderId;
        this.clientName = clientName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.orderDate = orderDate;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
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
        OrderDto orderDto = (OrderDto) o;
        return orderId == orderDto.orderId && Objects.equals(clientName, orderDto.clientName)
                && Objects.equals(phone, orderDto.phone)
                && Objects.equals(email, orderDto.email) && Objects.equals(address, orderDto.address)
                && Objects.equals(orderDate, orderDto.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, clientName, phone, email, address, orderDate);
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "orderId=" + orderId +
                ", clientName='" + clientName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", orderDate=" + orderDate +
                '}';
    }
}
