package hu.unideb.webdev.repository.util;

import org.apache.logging.log4j.util.Strings;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Converter
public class StringCollectionConverter implements AttributeConverter<Collection<String>,String> {

    @Override
    public String convertToDatabaseColumn(Collection<String> strings) {
        return Strings.join(strings,',');
    }

    @Override
    public Collection<String> convertToEntityAttribute(String s) {
        return Arrays.stream(s.split(",")).collect(Collectors.toUnmodifiableList());
    }
}
