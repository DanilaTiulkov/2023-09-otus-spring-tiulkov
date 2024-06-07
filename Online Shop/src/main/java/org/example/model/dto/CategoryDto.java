package org.example.model.dto;


import java.util.Objects;

public class CategoryDto {


    private long categoryId;

    private String name;

    public CategoryDto(long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CategoryDto that = (CategoryDto) o;
        return categoryId == that.categoryId && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, name);
    }

    @Override
    public String toString() {
        return "CategoryDto{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                '}';
    }
}
