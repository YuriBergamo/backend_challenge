package com.challenge.cities.util;

import com.challenge.cities.database.domain.CityDomain;
import com.challenge.cities.database.repository.CityRepository;
import com.challenge.cities.util.DTO.StateInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class ServiceUtil {

    public static List<StateInfo> getCountOfCitiesByState(List<CityDomain> capitalList, CityRepository cityRepository){
        List<StateInfo> states = new ArrayList<>();
        capitalList.stream().forEach(cityDomain -> {
            Integer coutCities = cityRepository.countByUf(cityDomain.getUf());
            states.add(new StateInfo(cityDomain.getUf(), coutCities));
        });

        return states;
    }

    public static Double distanceBetwenTwoCities(CityDomain cityDomain1, CityDomain cityDomain2) {

        final int R = 6371; // Radius of the earth

        Double latDistance = deg2rad(cityDomain1.getLat() - cityDomain2.getLat());
        Double lonDistance = deg2rad(cityDomain1.getLon() - cityDomain2.getLon());
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(deg2rad(cityDomain1.getLat())) * Math.cos(deg2rad(cityDomain2.getLat()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2) + 0;
        return Math.sqrt(distance);
    }

    private static Double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        final Set<Object> seen = new HashSet<>();
        return t -> seen.add(keyExtractor.apply(t));
    }
}

