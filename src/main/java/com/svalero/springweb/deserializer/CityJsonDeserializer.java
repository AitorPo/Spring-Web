package com.svalero.springweb.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.svalero.springweb.domain.City;
import com.svalero.springweb.repository.CityRepository;

import java.io.IOException;

public class CityJsonDeserializer extends JsonDeserializer<City> {

    private CityRepository cityRepository;

    public CityJsonDeserializer(CityRepository cityRepository){
        this.cityRepository = cityRepository;
    }

    @Override
    public City deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (jsonParser == null) return null;

        long id = Long.parseLong(jsonParser.getText());
        City city = cityRepository.getCity(id);
        return city;
    }
}
