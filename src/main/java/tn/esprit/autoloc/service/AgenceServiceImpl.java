package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;

@Service
@Primary
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
        return entity != null ? entity.getIdAgence() : null;
    }

    @Override
    protected void setFields(Agence source, Agence target) {
        target.setNom(source.getNom());
        target.setAdresse(source.getAdresse());
        target.setTelephone(source.getTelephone());
        target.setVille(source.getVille());
    }

    @Override
    public Agence create(Agence agence) {
        if (agence != null) {
            agence.setIdAgence(null);
        }
        return super.create(agence);
    }

    @Override
    public Agence update(Long id, Agence agence) {
        if (agence == null) {
            throw new IllegalArgumentException("Données invalides pour la mise à jour.");
        }

        agence.setIdAgence(id);

        if (!check(agence, false)) {
            throw new IllegalArgumentException("Données invalides pour la mise à jour.");
        }

        Agence existingAgence = agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", String.valueOf(id)));

        existingAgence.setNom(agence.getNom());
        existingAgence.setAdresse(agence.getAdresse());
        existingAgence.setTelephone(agence.getTelephone());
        existingAgence.setVille(agence.getVille());

        return agenceRepository.save(existingAgence);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Agence agence = agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", String.valueOf(id)));

        // Grâce à la configuration SQL (ON DELETE SET NULL),
        // MySQL détache automatiquement les véhicules et employés liés lors de la suppression.
        agenceRepository.delete(agence);
    }

    @Override
    public boolean check(Agence entity, boolean isNew) {
        if (entity == null) return false;

        if (entity.getNom() == null || entity.getNom().trim().isEmpty()) return false;
        if (entity.getAdresse() == null || entity.getAdresse().trim().isEmpty()) return false;
        if (entity.getTelephone() == null || entity.getTelephone().trim().isEmpty()) return false;
        if (entity.getVille() == null || entity.getVille().trim().isEmpty()) return false;

        return true;
    }

    @Override
    public boolean hasVehicules(Long id) {
        Agence agence = findById(id);
        return agence != null && agence.getVehicules() != null && !agence.getVehicules().isEmpty();
    }
}