package ru.otus.example.model;

import ru.otus.example.model.enums.ButterflyStatus;

import java.util.Objects;

public class Butterfly {

    private String color;

    private ButterflyStatus status;

    public Butterfly(String color, ButterflyStatus status) {
        this.color = color;
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ButterflyStatus getStatus() {
        return status;
    }

    public void setStatus(ButterflyStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Butterfly butterfly = (Butterfly) o;
        return Objects.equals(color, butterfly.color) && status == butterfly.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, status);
    }

    @Override
    public String toString() {
        return "Butterfly{" +
                "color='" + color + '\'' +
                ", status=" + status +
                '}';
    }
}
