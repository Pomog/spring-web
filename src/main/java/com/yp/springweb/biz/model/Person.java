package com.yp.springweb.biz.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "First name can not be empty")
    private String firstName;

    @NotEmpty(message = "Last name can not be empty")
    private String lastName;

    @PastOrPresent(message = "Date of birth must be in the past")
    @NotNull(message = "Date od birth must be specified")
    private LocalDate dob;

    @DecimalMin(value = "0", message = "Salary must be at least 0")
    @NotNull(message = "Salary can not be empty")
    private BigDecimal salary;

    @Email(message = "Email must be valid")
    @NotEmpty(message = "Email can not be empty")
    private String email;

    private String photoFileName;

    public static Person parse(String csvLine) {
        String[] fields = csvLine.split(",\\s*");
        LocalDate dob = LocalDate.parse(fields[10], DateTimeFormatter.ofPattern("M/d/yyyy"));
        return new Person(null, fields[2], fields[4], dob, new BigDecimal(fields[25]), fields[6], null);
    }
}
