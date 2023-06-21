package com.yp.springweb.web.controller;

import com.yp.springweb.biz.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    @GetMapping
    public String getPeople (Model model){

        Person person1 = new Person(1L, "John", "Doe", LocalDate.of(1990, 5, 15), BigDecimal.valueOf(50000));
        Person person2 = new Person(2L, "Jane", "Smith", LocalDate.of(1985, 10, 25), BigDecimal.valueOf(60000));
        Person person3 = new Person(3L, "Mike", "Johnson", LocalDate.of(1992, 3, 8), BigDecimal.valueOf(55000));
        Person person4 = new Person(4L, "Sarah", "Williams", LocalDate.of(1988, 7, 12), BigDecimal.valueOf(52000));
        Person person5 = new Person(5L, "David", "Brown", LocalDate.of(1995, 9, 3), BigDecimal.valueOf(58000));
        Person person6 = new Person(6L, "Emily", "Davis", LocalDate.of(1991, 2, 28), BigDecimal.valueOf(51000));
        Person person7 = new Person(7L, "Michael", "Taylor", LocalDate.of(1987, 6, 6), BigDecimal.valueOf(53000));
        Person person8 = new Person(8L, "Jessica", "Anderson", LocalDate.of(1994, 4, 19), BigDecimal.valueOf(54000));
        Person person9 = new Person(9L, "Christopher", "Martinez", LocalDate.of(1993, 1, 10), BigDecimal.valueOf(57000));
        Person person10 = new Person(10L, "Olivia", "Thomas", LocalDate.of(1989, 8, 22), BigDecimal.valueOf(56000));

        List<Person> people = List.of(person1, person2, person3, person4, person5, person6, person7, person8, person9, person10);
        model.addAttribute("people", people);
        return "people";
    }
}
