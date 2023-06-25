package com.yp.springweb.data;

import com.yp.springweb.biz.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository <Person, Long> {
}
