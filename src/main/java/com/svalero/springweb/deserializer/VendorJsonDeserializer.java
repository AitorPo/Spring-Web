package com.svalero.springweb.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.svalero.springweb.domain.Vendor;
import com.svalero.springweb.repository.VendorRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;

@Schema(name = "VendorJsonDeserializer", description = "Permite asignar un objeto Vendor solo con el id")
public class VendorJsonDeserializer extends JsonDeserializer<Vendor> {

    private VendorRepository vendorRepository;

    public VendorJsonDeserializer(VendorRepository vendorRepository){
        this.vendorRepository = vendorRepository;
    }

    @Override
    public Vendor deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (jsonParser == null) return null;

        long id = Long.parseLong(jsonParser.getText());
        Vendor vendor = vendorRepository.getVendor(id);
        return vendor;
    }
}
