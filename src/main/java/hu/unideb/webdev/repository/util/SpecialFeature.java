package hu.unideb.webdev.repository.util;

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

}
