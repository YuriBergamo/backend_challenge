package com.challenge.cities.util;

import com.challenge.cities.database.domain.CityDomain;

import java.util.ArrayList;
import java.util.List;

public class ImportationUtil {

    public static List<CityDomain> getDomainByCSV(List<String[]> citiesCSV) throws Exception{
        List<CityDomain> retorno = new ArrayList<>();
        if(citiesCSV != null){
            for(String[] line : citiesCSV){
                if(citiesCSV.indexOf(line) != 0) {
                    CityDomain cityDomain = new CityDomain();
                    cityDomain.setIbgeId(line[0]);
                    cityDomain.setUf(line[1]);
                    cityDomain.setName(line[2]);
                    cityDomain.setCapital(Boolean.valueOf(line[3]));
                    cityDomain.setLon(Double.valueOf(line[4]));
                    cityDomain.setLat(Double.valueOf(line[5]));
                    cityDomain.setNoAccents(line[6]);
                    cityDomain.setAlternativeNames(line[7]);
                    cityDomain.setMicroRegion(line[8]);
                    cityDomain.setMesoRegion(line[9]);
                    retorno.add(cityDomain);
                }
            }
        }
        return retorno;
    }
}
