package tn.esprit.autoloc.calcul;

public enum VehiculeOffre {
    STANDARD("standard"),
    WEEKEND("weekend"),
    LONGUE_DUREE("longueDuree");

    private final String nomBean;

    VehiculeOffre(String nomBean) {
        this.nomBean = nomBean;
    }

    public String getNomBean() {
        return nomBean;
    }
}
