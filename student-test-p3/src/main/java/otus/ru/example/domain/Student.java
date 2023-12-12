package otus.ru.example.domain;

public record Student(String firstname, String lastname) {
    public String getFullName() {
        return String.format("%s %s", firstname, lastname);
    }
}
