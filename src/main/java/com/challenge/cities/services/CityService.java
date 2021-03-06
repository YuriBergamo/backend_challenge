package com.challenge.cities.services;

import com.challenge.cities.database.domain.CityDomain;
import com.challenge.cities.database.repository.CityRepository;
import com.challenge.cities.util.DTO.DistanceInfo;
import com.challenge.cities.util.DTO.ImportDTO;
import com.challenge.cities.util.DTO.StateInfo;
import com.challenge.cities.util.ImportationUtil;
import com.challenge.cities.util.FileConverter;
import com.challenge.cities.util.ServiceUtil;
import com.google.gson.Gson;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping( "/cities" )
public class CityService {

    private static final Logger LOGGER = LogManager.getLogger( CityService.class );

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(method = {RequestMethod.GET})
    public void findAll(){
        System.out.println("CITIES");
    }

    @RequestMapping(value="/import", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity importCSV(@RequestBody ImportDTO dto){
        try {
            List<String[]> fileCSV = FileConverter.convertCSVToList(dto.getPath());
            List<CityDomain> cities = ImportationUtil.getDomainByCSV(fileCSV);
            cityRepository.save(cities);
            return new ResponseEntity(HttpStatus.CREATED);

        }catch (FileNotFoundException fileException){
            //erro ao abrir arquivo csv
            fileException.printStackTrace();
            LOGGER.error( "Erro ao abrir arquivo csv - " + fileException.getMessage() );
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        }catch (IOException ioExeption){
            //erro ao ler o arquivo csv
            ioExeption.printStackTrace();
            LOGGER.error( "Erro ao ler arquivo csv - " + ioExeption.getMessage() );
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            //erro na conversão
            e.printStackTrace();
            LOGGER.error( "Erro ao importar o arquivo csv para o banco de dados - " + e.getMessage() );
        }

        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    //TODO metodo de export para csv

    @RequestMapping(value="/capital", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findByCapital(){
        try{
            List<CityDomain> cityDomainList = cityRepository.findByCapitalOrderByName(true);
            return new ResponseEntity(new Gson().toJson(cityDomainList), HttpStatus.OK);
        }catch (Exception e){
            LOGGER.error( "Erro ao buscar capitais - " + e.getMessage() );
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/max/uf", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity biggerState(){
        try{
            List<CityDomain> cityDomainList = cityRepository.findByCapitalOrderByName(true);
            List<StateInfo> states = ServiceUtil.getCountOfCitiesByState(cityDomainList, cityRepository);
            if(states != null && states.size() >0){
                StateInfo stateMaxCity = states.stream().max((s1, s2) -> Integer.compare(s1.getCities(), s2.getCities())).get();
                return new ResponseEntity(new Gson().toJson(stateMaxCity), HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            LOGGER.error( "Erro ao buscar o maior estado - " + e.getMessage() );
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/min/uf", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity smallerState(){
        try{
            List<CityDomain> cityDomainList = cityRepository.findByCapitalOrderByName(true);
            List<StateInfo> states = ServiceUtil.getCountOfCitiesByState(cityDomainList, cityRepository);
            if(states != null && states.size() >0){
                StateInfo stateMinCity = states.stream().min((s1, s2) -> Integer.compare(s1.getCities(), s2.getCities())).get();
                return new ResponseEntity(new Gson().toJson(stateMinCity), HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            LOGGER.error( "Erro ao buscar o menor estado - " + e.getMessage() );
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/count/uf", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity countByUf(){
        try{
            List<CityDomain> cityDomainList = cityRepository.findByCapitalOrderByName(true);
            List<StateInfo> states = ServiceUtil.getCountOfCitiesByState(cityDomainList, cityRepository);
            return new ResponseEntity(new Gson().toJson(states), HttpStatus.OK);
        }catch (Exception e){
            LOGGER.error( "Erro ao buscar o estados - " + e.getMessage() );
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/count/uf/{uf}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity countByState(@PathVariable(value = "uf") String uf){
        try{
            Integer citiesByState = cityRepository.countByUf(uf);
            StateInfo retorno = new StateInfo(uf, citiesByState);
            return new ResponseEntity(new Gson().toJson(retorno), HttpStatus.OK);
        }catch (Exception e){
            LOGGER.error( "Erro ao buscar o estados - " + e.getMessage() );
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/{ibgeId}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getById(@PathVariable(value = "ibgeId") String ibgeId){
        try{
            CityDomain cityDomain = cityRepository.findByIbgeId(ibgeId);
            return new ResponseEntity(new Gson().toJson(cityDomain), HttpStatus.OK);
        }catch (Exception e){
            LOGGER.error( "Erro ao buscar a cidade pelo id - " + e.getMessage() );
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/name/uf/{uf}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity nameByUf(@PathVariable(value = "uf") String uf){
        try{
            List<CityDomain> cities = cityRepository.findByUf(uf);
            return new ResponseEntity(new Gson().toJson(cities.stream().map(CityDomain::getName).collect(Collectors.toList())), HttpStatus.OK);
        }catch (Exception e){
            LOGGER.error( "Erro ao buscar o estados - " + e.getMessage() );
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody CityDomain cityDomain){
        try{
            cityRepository.save(cityDomain);
            return new ResponseEntity(new Gson().toJson(cityDomain), HttpStatus.OK);
        }catch (Exception e){
            LOGGER.error( "Erro ao buscar o estados - " + e.getMessage() );
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/{ibgeId}", method = {RequestMethod.DELETE}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable(value = "ibgeId") String ibgeId){
        try{
            cityRepository.delete(cityRepository.findByIbgeId(ibgeId));
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            LOGGER.error( "Erro ao buscar o estados - " + e.getMessage() );
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/column/{column}/filter/{filter}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity filterColumn(@PathVariable(value = "column") String column, @PathVariable(value = "filter") String filter){
        try{
            List<CityDomain> cityDomainList = cityRepository.findColumnByFilter(column, filter);
            return new ResponseEntity(new Gson().toJson(cityDomainList), HttpStatus.OK);
        }catch (Exception e){
            LOGGER.error( "Erro ao buscar o estados - " + e.getMessage() );
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/count/column/{column}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity countColumn(@PathVariable(value = "column") String column){
        try{
            List<CityDomain> allRecords =  mongoTemplate.getCollection("cities").distinct(column);
            return new ResponseEntity(new Gson().toJson(allRecords.size()), HttpStatus.OK);
        }catch (Exception e){
            LOGGER.error( "Erro ao buscar o estados - " + e.getMessage() );
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/count/all", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity countAll(){
        try{
            Integer countByColumn = cityRepository.findAll().size();
            return new ResponseEntity(new Gson().toJson(countByColumn), HttpStatus.OK);
        }catch (Exception e){
            LOGGER.error( "Erro ao buscar o estados - " + e.getMessage() );
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/distance/max", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity distanceMax(){
        try{
            List<CityDomain> cityDomainList = cityRepository.findAll();
            Double maxDistance = 0.0;
            Map<String, String> citiesWithMoreDistance = new HashMap<>();
            CityDomain firstCityToCompare = null;
            for(CityDomain city : cityDomainList){
                if(firstCityToCompare == null){
                    firstCityToCompare = city;
                }else{
                    Double distance = ServiceUtil.distanceBetwenTwoCities(firstCityToCompare, city);
                    if(distance > maxDistance){
                        maxDistance = distance;
                        citiesWithMoreDistance.put("CIDADE_1", firstCityToCompare.getName());
                        citiesWithMoreDistance.put("CIDADE_2", city.getName());
                    }
                }
            }
            DistanceInfo distanceInfo = new DistanceInfo(citiesWithMoreDistance.get("CIDADE_1"), citiesWithMoreDistance.get("CIDADE_2"), maxDistance);
            return new ResponseEntity(new Gson().toJson(distanceInfo), HttpStatus.OK);
        }catch (Exception e){
            LOGGER.error( "Erro ao buscar o estados - " + e.getMessage() );
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }



}
