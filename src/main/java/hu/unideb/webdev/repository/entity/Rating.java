package hu.unideb.webdev.repository.entity;

import lombok.ToString;

public enum Rating {
    G("G"),
    PG("PG"),
    PG_13("PG-13"),
    R("R"),
    NC_17("NC-17");

    private String value;
    private Rating(String value)
    {
        this.value = value;
    }
    public String toString()
    {
        return this.value;
    }
}
