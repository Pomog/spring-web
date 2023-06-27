package com.yp.springweb.web.controller;

import com.yp.springweb.biz.model.Person;
import com.yp.springweb.data.PersonRepository;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/people")
@Log4j2
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
    public String savePerson(@Valid Person person, Errors errors, @RequestParam MultipartFile photoFileName){
        log.info("File name : " + photoFileName.getOriginalFilename());
        log.info("File size : " + photoFileName.getSize());
        log.info("Errors : " + errors);
        if (!errors.hasErrors()) {
            personRepository.save(person);
            return "redirect:people";
        }
        return "people";
    }

    @PostMapping(params = "delete=true")
    public String deletePeople(@RequestParam Optional<List<Long>> selections){
        selections.ifPresent(longs -> personRepository.deleteAllById(longs));
        return "redirect:people";
    }

    @PostMapping(params = "edit=true")
    public String updatePeople(@RequestParam Optional<List<Long>> selections, Model model){
        log.info(selections);
        selections.ifPresent(longs -> {
            Optional<Person> personToUpdate = personRepository.findById(longs.get(0));
            model.addAttribute("person", personToUpdate);
        });

        return "people";
    }
}
