package com.challenge.cities.database.repository;

import com.challenge.cities.database.domain.CityDomain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CityRepository extends MongoRepository<CityDomain, String > {

    List<CityDomain> findAll();
    List<CityDomain> findByCapitalOrderByName(Boolean capital);
    Integer countByUf(String uf);
    List<CityDomain> findByUf(String uf);
    CityDomain findByIbgeId(String ibgeId);

    @Query(value="{ '?0': {$regex : ?1, $options: 'i'} }")
    List<CityDomain> findColumnByFilter(String column, String filter);

}
