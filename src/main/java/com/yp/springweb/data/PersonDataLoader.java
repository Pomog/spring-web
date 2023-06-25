package com.yp.springweb.data;

import com.yp.springweb.biz.model.Person;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class PersonDataLoader implements ApplicationRunner {
    private PersonRepository personRepository;

    public PersonDataLoader(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (personRepository.count() == 0) {
            Person person1 = new Person(null, "John", "Doe", LocalDate.of(1990, 5, 15), BigDecimal.valueOf(50000));
            Person person2 = new Person(null, "Jane", "Smith", LocalDate.of(1985, 10, 25), BigDecimal.valueOf(60000));
            Person person3 = new Person(null, "Mike", "Johnson", LocalDate.of(1992, 3, 8), BigDecimal.valueOf(55000));
            Person person4 = new Person(null, "Sarah", "Williams", LocalDate.of(1988, 7, 12), BigDecimal.valueOf(52000));
            Person person5 = new Person(null, "David", "Brown", LocalDate.of(1995, 9, 3), BigDecimal.valueOf(58000));
            Person person6 = new Person(null, "Emily", "Davis", LocalDate.of(1991, 2, 28), BigDecimal.valueOf(51000));
            Person person7 = new Person(null, "Michael", "Taylor", LocalDate.of(1987, 6, 6), BigDecimal.valueOf(53000));
            Person person8 = new Person(null, "Jessica", "Anderson", LocalDate.of(1994, 4, 19), BigDecimal.valueOf(54000));
            Person person9 = new Person(null, "Christopher", "Martinez", LocalDate.of(1993, 1, 10), BigDecimal.valueOf(57000));
            Person person10 = new Person(null, "Olivia", "Thomas", LocalDate.of(1989, 8, 22), BigDecimal.valueOf(56000));
            Person person11 = new Person(null, "Daniel", "Wilson", LocalDate.of(1997, 12, 5), BigDecimal.valueOf(59000));
            Person person12 = new Person(null, "Sophia", "Brown", LocalDate.of(1996, 11, 14), BigDecimal.valueOf(62000));
            Person person13 = new Person(null, "Matthew", "Clark", LocalDate.of(1998, 4, 2), BigDecimal.valueOf(58000));
            Person person14 = new Person(null, "Ava", "Garcia", LocalDate.of(1993, 7, 29), BigDecimal.valueOf(60000));
            Person person15 = new Person(null, "Andrew", "Wilson", LocalDate.of(1992, 9, 18), BigDecimal.valueOf(57000));

            List<Person> people = List.of(person1, person2, person3, person4, person5, person6, person7, person8, person9, person10,
                    person11, person12, person13, person14, person15
            );

            personRepository.saveAll(people);
        }
    }
}
