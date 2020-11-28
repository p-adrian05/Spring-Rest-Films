package hu.unideb.webdev.repository.util;

import com.mysql.cj.xdevapi.Collection;
import hu.unideb.webdev.exceptions.UnknownSpecialFeatureException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public enum SpecialFeature {
    TRAILERS("Trailers"),
    COMMENTARIES("Commentaries"),
    DELETED_SCENES("Deleted Scenes"),
    BEHIND_THE_SCENES("Behind the scenes");

    private String value;
    SpecialFeature(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static SpecialFeature convertStringToSpecialFeature(String s) throws UnknownSpecialFeatureException {
        try{
           return SpecialFeature.valueOf(s.chars()
                    .mapToObj(c -> String.valueOf((char) c)).map(c -> c.equals(" ")? c = "_": c)
                    .collect(Collectors.joining()).toUpperCase());
        }catch (Exception e){
            throw new UnknownSpecialFeatureException(
                    String.format("No Special feature found: %s Possible values: %s",s,
                            Arrays.toString(SpecialFeature.values())));
        }
    }

}
