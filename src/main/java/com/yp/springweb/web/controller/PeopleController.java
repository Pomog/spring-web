package com.yp.springweb.web.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.yp.springweb.biz.model.Person;
import com.yp.springweb.biz.service.PersonService;
import com.yp.springweb.data.FileStorageRepository;
import com.yp.springweb.data.PersonRepository;
import com.yp.springweb.exceptions.StorageException;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Controller
@RequestMapping("/people")
@Log4j2
public class PeopleController {
    public static final String DISPOSITION = """
             attachment; filename="%s"
            """;
    private PersonRepository personRepository;
    private FileStorageRepository fileStorageRepository;
    private PersonService personService;

    public PeopleController(PersonRepository personRepository, FileStorageRepository fileStorageRepository, PersonService personService) {
        this.personRepository = personRepository;
        this.fileStorageRepository = fileStorageRepository;
        this.personService = personService;
    }

    @ModelAttribute ("people")
    public Page<Person> getPeople(@PageableDefault(size=3) Pageable page){
        return personService.findAll(page);
    }
    @ModelAttribute
    public Person getPerson(){
        return new Person();
    }
    @GetMapping
    public String showPeoplePage (){
        return "people";
    }
    @GetMapping("/images/{resource}")
    public ResponseEntity<Resource> getResource(@PathVariable() String resource){
        ResponseEntity<Resource> responseEntity = ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, format(DISPOSITION, resource))
                .body(fileStorageRepository.findByName(resource));
        log.info("resource : " + resource);
        log.info("responseEntity : " + responseEntity);
        return responseEntity;
    }
    @PostMapping
    public String savePerson(Model model, @Valid Person person, Errors errors, @RequestParam("photoFileName") MultipartFile photoFile) throws IOException {
        log.info("File name : " + photoFile.getOriginalFilename());
        log.info("File size : " + photoFile.getSize());
        log.info("Errors : " + errors);
        if (!errors.hasErrors()) {
            try {
                personService.save(person, photoFile.getInputStream());
                return "redirect:people";
            } catch (StorageException e) {
                model.addAttribute("errorMsg", "system is currently unable to accept files");
                return "people";
            }
        }
        return "people";
    }

    @PostMapping(params = "delete=true")
    public String deletePeople(@RequestParam Optional<List<Long>> selections){
        log.info("deletePeople : " + selections);
        selections.ifPresent(longs -> {
            personService.deleteAllByIdCustomQuery(longs);
        });
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
