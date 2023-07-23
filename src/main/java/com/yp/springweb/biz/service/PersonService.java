package com.yp.springweb.biz.service;

import com.yp.springweb.biz.model.Person;
import com.yp.springweb.data.FileStorageRepository;
import com.yp.springweb.data.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.zip.ZipInputStream;


@Log4j2
@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final FileStorageRepository fileStorageRepository;

    public PersonService(PersonRepository personRepository, FileStorageRepository fileStorageRepository) {
        this.personRepository = personRepository;
        this.fileStorageRepository = fileStorageRepository;
    }

    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }
    public Page<Person> findAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Transactional
    public Person save(Person person, InputStream inputStream) {
        fileStorageRepository.save(person.getPhotoFileName(), inputStream);
        return personRepository.save(person);
    }

    public Optional<Person> findById(Long aLong) {
        return personRepository.findById(aLong);
    }

    public void deleteAllById(Iterable<Long> ids)  {
        log.info("PersonService METHOD deleteAllById INVOKED");
        log.info("for ids : " + ids);
        Iterable<Person> peopleToDelete = personRepository.findAllById(ids);
        log.info("peopleToDelete : " + peopleToDelete);
        Stream<Person> peopleStream = StreamSupport.stream(peopleToDelete.spliterator(), false);
        log.info("peopleStream : " + peopleStream);

        long count = peopleStream.count();
        log.info("Number of elements in peopleStream: " + count);

        peopleStream = StreamSupport.stream(peopleToDelete.spliterator(), false);

        Set<String> fileNames = peopleStream
                .map(Person::getPhotoFileName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        log.info("file to delete : " + fileNames);
        log.info("fileNames.isEmpty() : " + fileNames.isEmpty());
        personRepository.deleteAllById(ids);
        if (!fileNames.isEmpty()) {
            fileStorageRepository.deleteAllByName(fileNames);
        }
    }

    public void deleteAllByIdCustomQuery(Iterable<Long> ids)  {
        log.info("PersonService METHOD deleteAllByIdCustomQuery INVOKED");
        Set<String> fileNames = personRepository.findFileNamesByIds(ids);
        personRepository.deleteAllById(ids);
        if (!fileNames.isEmpty()) {
            fileStorageRepository.deleteAllByName(fileNames);
        }
    }

    public void importCSV(InputStream csvFileStream) {
        try {
            ZipInputStream zipInputStream = new ZipInputStream(csvFileStream);
            zipInputStream.getNextEntry();
            InputStreamReader inputStreamReader = new InputStreamReader(zipInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            bufferedReader.lines()
                    .skip(1)
                    .limit(50)
                    .map(Person::parse)
                    .forEach(personRepository::save);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
