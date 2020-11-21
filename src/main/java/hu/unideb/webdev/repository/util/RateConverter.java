package hu.unideb.webdev.repository.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RateConverter implements AttributeConverter<Rate,String> {

    @Override
    public String convertToDatabaseColumn(Rate rating) {
        return rating.toString();
    }

    @Override
    public Rate convertToEntityAttribute(String s) {
        if(s.equals(Rate.NC17.toString())){
            return Rate.NC17;
        }else if(s.equals(Rate.PG13.toString())){
            return Rate.PG13;
        }
        return Rate.valueOf(s);
    }
}
