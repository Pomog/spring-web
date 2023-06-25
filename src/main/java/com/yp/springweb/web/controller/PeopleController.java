package com.yp.springweb.web.controller;

import com.yp.springweb.biz.model.Person;
import com.yp.springweb.data.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private PersonRepository personRepository;

    public PeopleController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @ModelAttribute ("people")
    public Iterable<Person> getPeople(){
        return personRepository.findAll();
    }

    @ModelAttribute
    public Person getPerson(){
        return new Person();
    }
    @GetMapping
    public String showPeoplePage (){
        return "people";
    }

    @PostMapping
    public String savePerson(Person person){
        System.out.println(person);
        personRepository.save(person);
        return "redirect:people";
    }
}
