package hu.unideb.webdev.repository.util;

import hu.unideb.webdev.exceptions.UnknownRateException;

import java.util.Arrays;

public enum Rate {

    G,PG,PG13("PG-13"),R,NC17("NC-17");

    private String value;
    Rate(String value) {
        this.value = value;
    }
    Rate() {
        this.value = this.name();
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static Rate convertStringToRate(String s) throws UnknownRateException {
        if(s.equals(Rate.NC17.toString())){
            return Rate.NC17;
        }else if(s.equals(Rate.PG13.toString())){
            return Rate.PG13;
        }
        try{
            return Rate.valueOf(s);
        }catch (Exception e){
            throw new UnknownRateException(String.format("No Rate found: %s Possible values: %s",s,
                    Arrays.toString(Rate.values())));
        }
    }

}
