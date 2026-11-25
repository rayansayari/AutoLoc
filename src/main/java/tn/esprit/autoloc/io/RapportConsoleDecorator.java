package tn.esprit.autoloc.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tn.esprit.autoloc.domain.Agence;

public class RapportConsoleDecorator implements Rapport {

    private static final Logger LOGGER = LoggerFactory.getLogger(RapportConsoleDecorator.class);

    private final Rapport rapport;

    public RapportConsoleDecorator(Rapport rapport) {
        this.rapport = rapport;
    }

    @Override
    public String entete(Agence agence) {
        return rapport.entete(agence);
    }

    @Override
    public String generer(Agence agence, double total) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("{}", rapport.entete(agence));   // en-tête écrit dans les logs
        }
        return rapport.generer(agence, total);           // le total est retourné
    }
}