package de.dpunkt.myaktion.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@NamedQueries({
        @NamedQuery(name = Organizer.findByEmail, query = "SELECT o FROM Organizer o WHERE o.email = :email")
})
@Entity
public class Organizer {
    public static final String findByEmail = "Organizer.findByEmail";

    @NotNull
    @Size(min = 3, max = 20, message = "{organizer.firstName.size}")
    private String firstName;
    @NotNull
    @Size(min = 3, max = 30, message = "{organizer.lastName.size}")
    private String lastName;
    @Id
    @Pattern(regexp = ".+@.+", message = "{organizer.email.pattern}")
    private String email;
    @NotNull
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
