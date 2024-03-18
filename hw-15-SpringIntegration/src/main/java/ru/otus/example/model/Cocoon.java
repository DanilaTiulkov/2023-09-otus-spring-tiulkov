package ru.otus.example.model;

import ru.otus.example.model.enums.CocoonStatus;

import java.util.Objects;

public class Cocoon {

    private CocoonStatus status;

    private String color;

    public Cocoon(CocoonStatus status, String color) {
        this.status = status;
        this.color = color;
    }

    public CocoonStatus getStatus() {
        return status;
    }

    public void setStatus(CocoonStatus status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cocoon cocoon = (Cocoon) o;
        return status == cocoon.status && Objects.equals(color, cocoon.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, color);
    }

    @Override
    public String toString() {
        return "Cocoon{" +
                "status=" + status +
                ", color='" + color + '\'' +
                '}';
    }
}
