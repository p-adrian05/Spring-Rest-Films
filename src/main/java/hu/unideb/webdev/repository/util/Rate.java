package hu.unideb.webdev.repository.util;

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
}
