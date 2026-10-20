package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.repository.IAgenceRepository;

@Service
@Primary // Résolution de l'ambiguïté (Question 19)
@RequiredArgsConstructor
public class AgenceServiceImpl extends GenericServiceImpl<Agence, Long> implements IAgenceService {

    private final IAgenceRepository agenceRepository;

    @Override
    protected JpaRepository<Agence, Long> getRepository() {
        return agenceRepository;
    }

    @Override
    protected String getEntityName() {
        return "Agence";
    }

    @Override
    protected Long getId(Agence entity) {
        return entity.getIdAgence();
    }

    @Override
    protected void setFields(Agence source, Agence target) {
        target.setNom(source.getNom());
        target.setAdresse(source.getAdresse());
        target.setTelephone(source.getTelephone());
        target.setVille(source.getVille());
    }

    @Override
    public boolean check(Agence entity, boolean isNew) {
        if (entity == null) return false;
        if (Utils.isNotEmpty(entity.getNom())) return false;
        if (Utils.isNotEmpty(entity.getAdresse())) return false;
        if (Utils.isNotEmpty(entity.getTelephone())) return false;
        if (Utils.isNotEmpty(entity.getVille())) return false;
        return true;
    }

    @Override
    public boolean hasVehicules(Long id) {
        Agence agence = findById(id);
        return agence.getVehicules() != null && !agence.getVehicules().isEmpty();
    }
}
