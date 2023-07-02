package com.yp.springweb.data;

import com.yp.springweb.biz.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PersonRepository extends CrudRepository <Person, Long> {

    @Query(nativeQuery = true, value = "SELECT PHOTO_FILE_NAME FROM PERSON WHERE ID IN :ids")
    public Set<String > findFileNamesByIds (@Param("ids") Iterable<Long> ids);
}
