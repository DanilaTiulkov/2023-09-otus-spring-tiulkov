package ru.otus.example.model;

import ru.otus.example.model.enums.CaterpillarStatus;

import java.util.Objects;

public class Caterpillar {

    private String color;

    private CaterpillarStatus status;

    public Caterpillar(String color, CaterpillarStatus status) {
        this.color = color;
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public CaterpillarStatus getStatus() {
        return status;
    }

    public void setStatus(CaterpillarStatus status) {
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
        Caterpillar that = (Caterpillar) o;
        return Objects.equals(color, that.color) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, status);
    }

    @Override
    public String toString() {
        return "Caterpillar{" +
                "color='" + color + '\'' +
                ", status=" + status +
                '}';
    }
}
