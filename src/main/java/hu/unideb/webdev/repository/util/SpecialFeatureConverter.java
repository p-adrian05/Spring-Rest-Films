package hu.unideb.webdev.repository.util;

import org.apache.logging.log4j.util.Strings;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Converter
public class SpecialFeatureConverter implements AttributeConverter<Collection<SpecialFeature>,String> {

    @Override
    public String convertToDatabaseColumn(Collection<SpecialFeature> strings) {
        return Strings.join(strings, ',');
    }

    @Override
    public Collection<SpecialFeature> convertToEntityAttribute(String s) {
        String[] stringValues = s.split(",");
        List<SpecialFeature> specialFeatureList = new LinkedList<>();
        for (String stringValue : stringValues) {
            specialFeatureList.add(SpecialFeature.valueOf(stringValue.chars()
                    .mapToObj(c -> String.valueOf((char) c)).map(c -> c.equals(" ")? c = "_": c)
                    .collect(Collectors.joining()).toUpperCase()));
        }
        return specialFeatureList;
    }
}