package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl extends GenericServiceImpl<Vehicule, Long> implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;

    @Override
    protected JpaRepository<Vehicule, Long> getRepository() {
        return vehiculeRepository;
    }

    @Override
    protected String getEntityName() {
        return "Vehicule";
    }

    @Override
    protected Long getId(Vehicule entity) {
        return entity.getIdVehicule();
    }

    @Override
    protected void setFields(Vehicule source, Vehicule target) {
        target.setImmatriculation(source.getImmatriculation());
        target.setMarque(source.getMarque());
        target.setModele(source.getModele());
        target.setCategorie(source.getCategorie());
        target.setStatut(source.getStatut());
        target.setTarifJournalier(source.getTarifJournalier());
        target.setAgence(source.getAgence());
    }

    @Override
    public boolean check(Vehicule entity, boolean isNew) {
        if (entity == null) return false;
        if (Utils.isNotEmpty(entity.getImmatriculation())) return false;
        if (Utils.isNotEmpty(entity.getMarque())) return false;
        if (entity.getTarifJournalier() == null || entity.getTarifJournalier() <= 0) return false;
        return true;
    }

    @Override
    public double calculTarif(Vehicule vehicule, int nbJours, VehiculeOffre offre) {
        var tarifJournalier = vehicule.getTarifJournalier().doubleValue();

        if (offre == VehiculeOffre.WEEKEND) {
            tarifJournalier *= 0.8;
        }
        if (offre == VehiculeOffre.LONGUE_DUREE) {
            tarifJournalier *= 0.75;
        }

        var total = tarifJournalier * nbJours;

        if (offre == VehiculeOffre.LONGUE_DUREE && total > 2000) {
            total *= 0.95;
        }

        return total;
    }
    }
